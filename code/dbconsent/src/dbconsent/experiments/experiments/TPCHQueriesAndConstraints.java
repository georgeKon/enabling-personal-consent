package dbconsent.experiments.experiments;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.PoolOfNames;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.RewritingAlgorithm;
import dbconsent.HeadVariableNotInQueryException;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

public class TPCHQueriesAndConstraints {

    //Map of query id to constraint
    private static Map<Integer, List<DatalogStatement>> constraints = new HashMap<>();
    private static Map<Integer, List<String>> targetRestrictivities = new HashMap<>();
    private static Set<DatalogStatement> allConstraints = new HashSet<>();

    public static void main(String[] args) {
        Properties connect = new Properties();
        loadConstraints(connect);
        runExperiment(true, 7, true, true, connect);
    }

    public static void loadConstraints (Properties properties) {
        String projectDir = properties.getProjectDir();
        String folderPath = projectDir + "queries";
        File folder = new File(folderPath);
        ParseDatalog parseDatalog = new ParseDatalog();
        //Load in all the constraints
        for (File fileEntry : folder.listFiles()) {
            if(fileEntry.getName().endsWith("-constraints.dl")) {
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
            } else if (fileEntry.getName().endsWith("-restrictivities.txt")) {
                Integer queryNumber = Integer.parseInt(fileEntry.getName().replaceFirst("-restrictivities.txt", ""));
                targetRestrictivities.put(queryNumber, new ArrayList<>());
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getCanonicalPath()));
                    String line = reader.readLine();
                    while (line != null) {
                        targetRestrictivities.get(queryNumber).add(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param setUpDatabase boolean of if we should load in the data to postgresql or not - send `false` if appropriate data already loaded
     * @param runIndividualExperiment boolean of if we should run the experiment of query against each of it's four constraints or not
     * @param runMultiExperiment boolean of if we should run the experiment against all constraints or not
     * @param connect config
     */
    public static void runExperiment(boolean setUpDatabase, int tpchscale, boolean runIndividualExperiment, boolean runMultiExperiment, Properties connect) {
        String projectDir = connect.getProjectDir();
        String folderPath = projectDir + "queries";

        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
        Experiment experiment = new Experiment(connect);
        //Use this to ensure that Postgres is actually up, not to clear cache.
        experiment.clearPostgresCache();
        try {
            //Set up database (ie load in data)
            if(setUpDatabase) {
                String pathToSchema = projectDir + "tpch-data/scale"+tpchscale+"/schema.sql";
                experiment.setupDatabase(pathToSchema, "EXPLAIN ANALYSE SELECT * FROM CUSTOMER;");
            }
            PrintWriter writerIndividual = null;
            PrintWriter writerMulti = null;

            if(runIndividualExperiment) {
                writerIndividual = new PrintWriter(projectDir + "experiments/other/tpch-individual.csv", "UTF-8");
                writerIndividual.write("Qid,Qrows,Qexecution(ms),Cid,C#atoms,Crows,Rrows,Rexecution(ms)\n");
            }
            if(runMultiExperiment) {
                writerMulti = new PrintWriter(projectDir + "experiments/other/tpch-multi.csv", "UTF-8");
                writerMulti.write("Qid,Qrows,Qexecution(ms),R#unifications,Rrows,Rexecution(ms)\n");
            }
            for (Integer qid : constraints.keySet()) {
                String queryFile = folderPath + "/" + qid.toString() + "-clean.sql";
                DatalogStatement query = parsePostgreSQL.parseFile(queryFile);

                int qsize = experiment.sizeOfQuery(query.toPostgreSQLString());

                float qtiming = experiment.timeQuery(query.toPostgreSQLString()).execution;

                if(runIndividualExperiment) {
                    for (int cid = 0; cid < constraints.get(qid).size(); cid++) {
                        DatalogStatement constraint = constraints.get(qid).get(cid);

                        int catoms = constraint.numberOfAtoms();

                        //If we get a -1 here, the constraint timed out
                        int csize = experiment.sizeOfQuery(constraint.toPostgreSQLString());

                        /*
                        float cResults = experiment.timeQuery(constraint.toPostgreSQLString()).execution;
                        */

                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(query, constraint);

                        int rwsize = experiment.sizeOfQuery(rwQuery.toPostgreSQLString());

                        float rwtiming = experiment.timeQuery(rwQuery.toPostgreSQLString()).execution;
                        writerIndividual.write(qid + "," + qsize + "," + qtiming + "," + cid + "," + catoms + "," + csize + "," + rwsize + "," + rwtiming + "\n");

                    }
                }

                if(runMultiExperiment) {
                    DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(query, allConstraints);

                    int numberOfUnifications = rwQuery.getConstraints().size();
                    int rwRows = experiment.sizeOfQuery(rwQuery.toPostgreSQLString());
                    float rwExecution = experiment.timeQuery(rwQuery.toPostgreSQLString()).execution;
                    writerMulti.println(qid + "," + qsize + "," + qtiming + "," + numberOfUnifications + "," + rwRows + "," + rwExecution + "\n");
                }
            }
            if (runIndividualExperiment) {
                writerIndividual.flush();
                writerIndividual.close();
            }
            if (runMultiExperiment) {
                writerMulti.flush();
                writerMulti.close();
            }
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
