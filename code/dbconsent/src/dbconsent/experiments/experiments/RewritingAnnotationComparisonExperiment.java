package dbconsent.experiments.experiments;

import dbconsent.AnnotationAlgorithm;
import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.RewritingAlgorithm;
import dbconsent.AggregationReapplication;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RewritingAnnotationComparisonExperiment {

    static List<DatalogStatement> orderConstraints = new ArrayList<>();
    static List<DatalogStatement> joinedOrderConstraints = new ArrayList<>();

    public static void main(String[] args) { runExperiment(7,500); }

    public static void runExperiment(int tpchscale, int count) {
        Properties properties = new Properties();
        String projectDir = properties.getProjectDir();
        Experiment experiment = new Experiment(properties);

        ParseDatalog parseDatalog = new ParseDatalog();

        // Load in constraints
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

        int[] queries = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 18, 19, 20, 21};
        int[] no_constraints = {500}; // {100}

        for (Integer size : no_constraints){
/*

            // define constraints
            Set<DatalogStatement> constraints = new HashSet<>();
            Random randomGenerator = new Random();
//            for (int i = 0; i<size; i++){ // while size < 100
            while (constraints.size() < size){
                int index = randomGenerator.nextInt(orderConstraints.size());
                constraints.add(orderConstraints.get(index));
            }

            runOverConstraints(queries, constraints, projectDir + "results/comparison/no-join-order-", projectDir, experiment);
*/

            // re-define constraints with joined
            Set<DatalogStatement> constraints = new HashSet<>();
            Random randomGenerator = new Random();
            while (constraints.size() < size){
                int index = randomGenerator.nextInt(joinedOrderConstraints.size());
                constraints.add(joinedOrderConstraints.get(index));
            }

            runOverConstraints(queries, constraints, projectDir + "results/comparison/join-order-", projectDir, experiment);

        }
    }

    private static void runOverConstraints(int[] queries, Set<DatalogStatement> constraints, String base_filename, String projectDir, Experiment experiment) {

        // Set up writers
        try {
            PrintWriter writer_rw = new PrintWriter(base_filename + "rewritings.txt", "UTF-8");
            writer_rw.write("queryid,numberconstraints,rewritingtimenano\n");
            PrintWriter writer_an = new PrintWriter(base_filename + "annotation.txt", "UTF-8");
            writer_an.write("queryid,numberconstraints,rewritingtimenano\n");
            PrintWriter writer_ba = new PrintWriter(base_filename + "base.txt", "UTF-8");
            writer_ba.write("queryid,numberconstraints,rewritingtimenano\n");
            PrintWriter writer_ag = new PrintWriter(base_filename + "aggregation.txt", "UTF-8");
            writer_ag.write("queryid,numberconstraints,rewritingtimenano\n");
            PrintWriter writer_or = new PrintWriter(base_filename + "original.txt", "UTF-8");
            writer_or.write("queryid,numberconstraints,rewritingtimenano\n");

            ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
            for (Integer queryID : queries) {

                System.out.println("qID: " + queryID);

                DatalogStatement query = null;
                try {
                    query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");
//                    System.out.println(query);
//                    System.out.println(query.toPostgreSQLString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // warm up database
                warmUpDatabase(query.toPostgreSQLString(), experiment);

                // no-join - base query and constraints
                long[] base = new long[11];
                for (int k = 0; k < 5; k++) {
                    long beforeRun = System.nanoTime();
                    ResultSet query_res = experiment.execQuery(query.toPostgreSQLString());
                    for (DatalogStatement constraint : constraints){
                        ResultSet con = experiment.execQuery(constraint.toPostgreSQLString());
                    }
                    long afterRun = System.nanoTime();
                    base[k] = afterRun - beforeRun;
                }

                long average = averageTiming(base);

                writer_ba.write(queryID + "," + constraints.size() + "," + average + "\n");
                writer_ba.flush();

                // no-join - rewriting
                long[] rewritings = new long[11];
                for (int k = 0; k < 5; k++) {
                    long beforeRewriting = System.nanoTime();
                    DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), constraints);
                    ResultSet rw_result = experiment.execQuery(rwQuery.toPostgreSQLString());
                    long afterRewriting = System.nanoTime();
                    rewritings[k] = afterRewriting - beforeRewriting;

                    try {
                        rw_result.last();
                        System.out.println("Rw tuples returned: " + rw_result.getRow());
                    } catch (SQLException e){}
                }

                average = averageTiming(rewritings);

                writer_rw.write(queryID + "," + constraints.size() + "," + average + "\n");
                writer_rw.flush();

                // no-join annotation
                long[] annotations = new long[11];
                for (int k = 0; k < 5; k++) {
                    long beforeAnnotation = System.nanoTime();
//                    System.out.println("before an");
                    ResultSet annotation_result = AnnotationAlgorithm.filterQuerySet(query, experiment, new ArrayList<>(constraints));
                    HashSet<Integer> removed = AnnotationAlgorithm.getTuplesToRemove();
//                    System.out.println("after an");
                    // dummy filtering of removed tuples
                    while (removed.iterator().hasNext()){
                        removed.iterator().next();
                    }
                    long afterAnnotation = System.nanoTime();
                    annotations[k] = afterAnnotation - beforeAnnotation;

//                    System.out.println("no. removed tuples: " + removed.size());
                    try {
                        annotation_result.last();
                        System.out.println("Annotation no. tuples returned: " + (annotation_result.getRow() - removed.size()));
                    } catch (SQLException e){}
                }
                average = averageTiming(annotations);

                writer_an.write(queryID + "," + constraints.size() + "," + average + "\n");
                writer_an.flush();

                // no-join aggregation
                long[] aggregations = new long[11];
                for (int k = 0; k < 5; k++) {

                    String query_SQL = readFile(projectDir + "queries+constraints/tpch/" + queryID + "-initial.sql");

                    long beforeAggregation = System.nanoTime();
                    String rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), constraints).toPostgreSQLString();
                    ArrayList<String> result = AggregationReapplication.convertQuery(query_SQL, rwQuery);

                    String new_query = result.get(1);
                    String before_query = result.get(0);
                    String after_query = result.get(2);

                    ResultSet aggregation_RS;
                    if (before_query != null){
                        experiment.execQuery(before_query);
                        aggregation_RS = experiment.execQuery(new_query);
                        experiment.execQuery(after_query);
                    } else{
                        aggregation_RS = experiment.execQuery(new_query);
                    }
                    long afterAggregation = System.nanoTime();
                    aggregations[k] = afterAggregation - beforeAggregation;
                }

                average = averageTiming(aggregations);

                writer_ag.write(queryID + "," + constraints.size() + "," + average + "\n");
                writer_ag.flush();

                // no-join - original
                long[] original = new long[11];
                for (int k = 0; k < 5; k++) {
                    String original_query = readFile(projectDir + "queries+constraints/tpch/" + queryID + "-initial.sql");
                    String exe_query = "";
                    String before_query = null;
                    String drop_queries = null;

                    // Check if the query uses views and if so determine before_query and the drop query lines
                    if (original_query.split("\ncreate view").length == 1) {
                        exe_query = "select" + original_query.split("\nselect")[1].split("\n:")[0].replaceAll("\r\n", "\n");
                    }else{
                        exe_query = "create view" + original_query.replaceAll(":[os]", "").split("\ncreate view")[1].split("\n:")[0].replaceAll("\r\n", "\n");

                        String [] query_parts = exe_query.split(";\n");

                        before_query = query_parts[0] + ";";

                        // take the query part and remove the double newline
                        exe_query = query_parts[1].replaceAll("\n\n", "") + ";";
                        drop_queries = query_parts[2].trim();
                    }
                    exe_query = "\n" + exe_query.replaceAll("\r\n", "\n").replaceAll(":[os]", "");

                    long beforeRun = System.nanoTime();

                    ResultSet original_RS;
                    if (before_query != null){
                        experiment.execQuery(before_query);
                        original_RS = experiment.execQuery(exe_query);
                        experiment.execQuery(drop_queries);
                    } else{
                        original_RS = experiment.execQuery(exe_query);
                    }
                    long afterRun = System.nanoTime();
                    original[k] = afterRun - beforeRun;
                }
                average = averageTiming(original);

                writer_or.write(queryID + "," + constraints.size() + "," + average + "\n");
                writer_or.flush();

            }
            writer_ba.close();
            writer_rw.close();
            writer_an.close();
            writer_ag.close();
            writer_or.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static long averageTiming(long [] timings){
        // Noting max and min and removing, then averaging between the rest
        long sum = 0;
        long max = 0;
        long min = 0;
        for (long l : timings) {
            if (l > max){max = l;}
            if (l < min){min = l;}
            sum += l;
        }
        sum = sum - min - max;
        return sum / ((long) timings.length - 2 );
    }

    private static void warmUpDatabase(String query, Experiment experiment){
        for (int i = 0; i < 5; i++){
            experiment.execQuery(query);
        }
//        experiment.clearPostgresCache();
    }

    // FOR READING INITIAL QUERIES
    private static String readFile(String filePath) {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}
