package dbconsent.experiments.experiments;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.InvalidQueryUnfoldingException;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.Propagation;
import dbconsent.RewritingAlgorithm;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class PropagationExperiment {

    // Individual constraints for each query (as in TPCHQueriesAndConstraints).
    private static Map<Integer, List<DatalogStatement>> constraints = new HashMap<>();

    // Auto-generated fine-grained constraints (as in FineGrained[...]Experiment).
//    private static List<DatalogStatement> customerConstraints = new ArrayList<>();
//    private static List<DatalogStatement> joinedCustomerConstraints = new ArrayList<>();

    // Manually created constraints for propagated queries.
    private static List<DatalogStatement> propagationConstraints = new ArrayList<>();

    // All constraints used in the experiment.
    private static Set<DatalogStatement> allConstraints = new HashSet<>();


    public static void main(String[] args) {
        Properties connect = new Properties();
        loadConstraints(7, 500, connect);
        runExperiment(true, 7, connect);
    }

    private static void loadConstraints(int tpchscale, int count, Properties properties) {
        String projectDir = properties.getProjectDir();
        String folderPath = projectDir + "queries";
        File folder = new File(folderPath);
        ParseDatalog parseDatalog = new ParseDatalog();

        // Read in individual constraints for each query.
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.getName().endsWith("-constraints.dl")) {
                Integer queryNumber = Integer.parseInt(fileEntry.getName().replaceFirst("-constraints.dl", ""));
                constraints.put(queryNumber, new ArrayList<>());
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getCanonicalPath()));
                    String line = reader.readLine();
                    while (line != null && !line.trim().equals("")) {
                        DatalogStatement constraint = parseDatalog.safeParseString(line);
                        constraint.indexAtoms();
                        constraints.get(queryNumber).add(constraint);
                        allConstraints.add(constraint);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        // Read in auto-generated fine-grained constraints.
//        try (BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/" + count + "-tpch" + tpchscale + "-joined-customer-constraint.dl"))) {
//            String line = reader.readLine();
//            while (line != null && !line.trim().equals("")) {
//                DatalogStatement constraint = parseDatalog.safeParseString(line);
//                constraint.indexAtoms();
//                joinedCustomerConstraints.add(constraint);
//                line = reader.readLine();
//            }
//            allConstraints.addAll(joinedCustomerConstraints);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/" + count + "-tpch" + tpchscale + "-customer-constraint.dl"))) {
//            String line = reader.readLine();
//            while (line != null && !line.trim().equals("")) {
//                DatalogStatement constraint = parseDatalog.safeParseString(line);
//                constraint.indexAtoms();
//                customerConstraints.add(constraint);
//                line = reader.readLine();
//            }
//            allConstraints.addAll(customerConstraints);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Read in manually created constraints for propagated queries.
        try (BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/custom-constraints-propagated.dl"))) {
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                propagationConstraints.add(constraint);
                line = reader.readLine();
            }
            allConstraints.addAll(propagationConstraints);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runExperiment(boolean setUpDatabase, int tpchscale, Properties connect) {
        String projectDir = connect.getProjectDir();

        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
        ParseDatalog parseDatalog = new ParseDatalog();
        Experiment experiment = new Experiment(connect);

        try (PrintWriter writer = new PrintWriter(projectDir + "results/propagation/propagation-simple.csv", "UTF-8")) {
            if(setUpDatabase) {
                String pathToSchema = projectDir + "tpch-data/scale"+tpchscale+"/schema.sql";
                //Use this to ensure that Postgres is actually up, not to clear cache.
                experiment.clearPostgresCache();
                experiment.setupDatabase(pathToSchema, "EXPLAIN ANALYSE SELECT * FROM CUSTOMER;");
            }

            // RESULTS FORMAT
            // NumPrevQs = number of previous queries used
            // A = completely unfolded query with no constraints applied
            // B = completely unfolded query with RewrittenConstraints from original queries only
            // C = completely unfolded query with new RewrittenConstraints from propagation added
            // D = minimally unfolded query with new RewrittenConstraints that can be executed (ie. uses views as much as possible)
            // Xatoms = number of atoms in the query body
            // Xrows = number of rows in the result of the query
            // Xtime = time taken to execute the query
            // Xunifications = the number of RewrittenConstraints
            writer.write("NumPrevQs,Aatoms,Arows,Atime,Brows,Btime,Bunifications,Crows,Ctime,Cunifications,Datoms,Drows,Dtime,Dunifications\n");
            writer.flush();

            int[] queries = {10, 13, 18, 4, 21, 7, 5, 3};
            Set<DatalogStatement> previousQueries = new HashSet<>();
            Set<DatalogStatement> rewrittenPreviousQueries = new HashSet<>();

            for (int queryID : queries) {
                try {
                    DatalogStatement query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");
                    query.setPredicate("Q" + queryID);
                    previousQueries.add(query);

                    // Parse query again to ensure clean version, then rewrite.
                    query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");
                    query.setPredicate("Q" + queryID);
                    query = RewritingAlgorithm.rewriteQuery(query, allConstraints);
                    rewrittenPreviousQueries.add(query);

                    String materialisedView = "CREATE MATERIALIZED VIEW " + query.getPredicate() + " AS " + query.toPostgreSQLString();
                    experiment.execQuery(materialisedView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            List<DatalogStatement> generatedQueries = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/propagated.dl"))) {
                String line = reader.readLine();
                while (line != null && !line.trim().equals("")) {
                    DatalogStatement query = parseDatalog.safeParseString(line);
                    query.indexAtoms();
                    generatedQueries.add(query);
                    line = reader.readLine();
                }
            }

            try {
                for (DatalogStatement generatedDS : generatedQueries) {
                    // Number of previous queries used in the new query
                    int numPrevQs = Propagation.getUsedPreviousQueries(generatedDS, previousQueries).size();

                    // Query version A (as above)
                    DatalogStatement a = Propagation.unfoldQuery(generatedDS, previousQueries);
                    String aPostgres = a.toPostgreSQLString();
                    int aAtoms = a.getAtoms().size();
                    int aRows = experiment.sizeOfQuery(aPostgres);
                    float aTime = experiment.timeQuery(aPostgres).execution;

                    // Query version B
                    DatalogStatement b = Propagation.unfoldQuery(generatedDS, rewrittenPreviousQueries);
                    String bPostgres = b.toPostgreSQLString();
                    int bRows = experiment.sizeOfQuery(bPostgres);
                    float bTime = experiment.timeQuery(bPostgres).execution;
                    int bUnifications = b.getConstraints().size();

                    // Query version C
                    DatalogStatement c = RewritingAlgorithm.rewriteQuery(Propagation.unfoldQuery(generatedDS, rewrittenPreviousQueries), allConstraints);
                    String cPostgres = c.toPostgreSQLString();
                    int cRows = experiment.sizeOfQuery(cPostgres);
                    float cTime = experiment.timeQuery(cPostgres).execution;
                    int cUnifications = c.getConstraints().size();

                    // Query version D
                    DatalogStatement d = Propagation.minimalUnfoldAndRewriteQuery(generatedDS, rewrittenPreviousQueries, allConstraints);
                    String dPostgres = d.toPostgreSQLString();
                    int dAtoms = d.getAtoms().size();
                    int dRows = experiment.sizeOfQuery(dPostgres);
                    float dTime = experiment.timeQuery(dPostgres).execution;
                    int dUnifications = d.getConstraints().size();

                    writer.write(numPrevQs + "," + aAtoms + "," + aRows + "," + aTime + "," + bRows + "," + bTime +
                            "," + bUnifications + "," + cRows + "," + cTime + "," + cUnifications + "," + dAtoms + "," +
                            dRows + "," + dTime + "," + dUnifications);
                }

            } catch (InvalidQueryUnfoldingException e) {
                e.printStackTrace();
            }

        } catch (IOException | InterruptedException | SQLException e) {
            e.printStackTrace();
        }
    }
}
