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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FineGrainedOrderExperiment {

    private static List<DatalogStatement> orderConstraints = new ArrayList<>();
    private static List<DatalogStatement> joinedOrderConstraints = new ArrayList<>();

    public static void main(String[] args) {
        runExperiment(true, 7, 500);
    }

    public static void runExperiment(boolean setUpDatabase, int tpchscale, int count) {
        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        ParseDatalog parseDatalog = new ParseDatalog();
        //Load in the constraints
        try {
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/"+count+"-tpch"+tpchscale+"-joined-order-constraint.dl"));
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                joinedOrderConstraints.add(constraint);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/"+count+"-tpch"+tpchscale+"-order-constraint.dl"));
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                orderConstraints.add(constraint);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
        Experiment experiment = new Experiment(connect);

        if (setUpDatabase) {
            try {
                String pathToSchema = projectDir + "tpch-data/scale"+tpchscale+"/schema.sql";
                //Use this to ensure that Postgres is actually up, not to clear cache.
                experiment.clearPostgresCache();
                //Set up database
                experiment.setupDatabase(pathToSchema, "EXPLAIN ANALYSE SELECT * FROM CUSTOMER;");
            } catch (SQLException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Order by fastness
        int[] queries = {4, 13, 21, 7, 10, 5, 3, 12, 18, 8, 9};
        int[] sizesOrders = {100, 250, 500};

        try {
            PrintWriter writerNoJoin = new PrintWriter(new FileWriter(projectDir + "results/finegrain/orders-nojoin.csv"), true);
            writerNoJoin.write("Qid,constraints,Qexecution,Qrows,Runifications,Rrows,Rexecution\n");
            PrintWriter writerJoin = new PrintWriter(new FileWriter(projectDir + "results/finegrain/orders-join.csv"), true);
            writerJoin.write("Qid,constraints,Qexecution,Qrows,Runifications,Rrows,Rexecution\n");
            for (Integer queryID : queries) {

                //Parse Query
                DatalogStatement query = null;
                query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");

                int qRows = experiment.sizeOfQuery(query.toPostgreSQLString());

                float qExecution = experiment.timeQuery(query.toPostgreSQLString()).execution;

                for (Integer size : sizesOrders) {
                    {
                        Set<DatalogStatement> sizeSet = new HashSet<>(orderConstraints.subList(0, size));

                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(query,sizeSet);
                        int numberOfUnifications = rwQuery.getConstraints().size();

                        int rRows = experiment.sizeOfQuery(rwQuery.toPostgreSQLString());

                        float rExecution = experiment.timeQuery(rwQuery.toPostgreSQLString()).execution;

                        String lineNoJoin = queryID + "," + size + "," + qExecution + "," + qRows + "," +
                                numberOfUnifications + "," + rRows + "," + rExecution;
                        writerNoJoin.println(lineNoJoin);
                    }

                    {
                        Set<DatalogStatement> sizeSet = new HashSet<>(joinedOrderConstraints.subList(0, size));

                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(query,sizeSet);

                        int numberOfUnifications = rwQuery.getConstraints().size();

                        int rRows = experiment.sizeOfQuery(rwQuery.toPostgreSQLString());

                        float rExecution = experiment.timeQuery(rwQuery.toPostgreSQLString()).execution;

                        String lineJoin = queryID + "," + size + "," + qExecution + "," + qRows + "," +
                                numberOfUnifications + "," + rRows + "," + rExecution;
                        writerJoin.println(lineJoin);
                    }

                }
            }
            writerNoJoin.flush();
            writerNoJoin.close();
            writerJoin.flush();
            writerJoin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
