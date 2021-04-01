package dbconsent.experiments.experiments;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.PoolOfNames;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.RewritingAlgorithm;
import dbconsent.HeadVariableNotInQueryException;
import dbconsent.experiments.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FineGrainedRewritingExperiment {

    static List<DatalogStatement> customerConstraints = new ArrayList<>();
    static List<DatalogStatement> joinedCustomerConstraints = new ArrayList<>();

    static List<DatalogStatement> orderConstraints = new ArrayList<>();
    static List<DatalogStatement> joinedOrderConstraints = new ArrayList<>();

    public static void main(String[] args) { runExperiment(true,7,500); }

    public static void runExperiment(boolean setUpDatabase, int tpchscale, int count) {
        Properties properties = new Properties();
        String projectDir = properties.getProjectDir();

        ParseDatalog parseDatalog = new ParseDatalog();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries/"+count+"-tpch"+tpchscale+"-joined-customer-constraint.dl"));
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                joinedCustomerConstraints.add(constraint);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries/"+count+"-tpch"+tpchscale+"-customer-constraint.dl"));
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                customerConstraints.add(constraint);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();

        int[] queries = {10, 13, 18};
        int[] sizesCustomer = {10,50,100,250,500};
        try {
            PrintWriter writer = new PrintWriter(projectDir + "experiments/finegrain/customer-rewritings-nojoin.txt", "UTF-8");
            writer.write("queryid,numberconstraints,rewritingtimenano");
            PrintWriter writer2 = new PrintWriter(projectDir + "experiments/finegrain/customer-rewritings-join.txt", "UTF-8");
            writer2.write("queryid,numberconstraints,rewritingtimenano");
            for (Integer queryID : queries) {

                DatalogStatement query = null;
                try {
                    query = parsePostgreSQL.parseFile(projectDir + "queries/" + queryID + "-clean.sql");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Integer size : sizesCustomer) {
                    Set<DatalogStatement> sizeSet = new HashSet<>();
                    sizeSet.addAll(customerConstraints.subList(0, size));


                    long[] rewritings = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), sizeSet);
                        long afterRewriting = System.nanoTime();
                        rewritings[k] = afterRewriting - beforeRewriting;
                    }
                    long sum = 0;
                    for (long l : rewritings) {
                        sum += l;
                    }
                    long average = sum / ((long) rewritings.length);

                    writer.write(queryID + "," + size + "," + average + "\n");
                    writer.flush();

                    sizeSet = new HashSet<>();
                    sizeSet.addAll(joinedCustomerConstraints.subList(0, size));


                    long[] rewritings2 = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), sizeSet);
                        long afterRewriting = System.nanoTime();
                        rewritings2[k] = afterRewriting - beforeRewriting;
                    }
                    long sum2 = 0;
                    for (long l : rewritings2) {
                        sum2 += l;
                    }
                    long average2 = sum2 / ((long) rewritings2.length);

                    writer2.write(queryID + "," + size + "," + average2 + "\n");
                    writer2.flush();
                }
            }
            writer.close();
            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries/500-joined-order-constraint.dl"));
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
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries/500-order-constraint.dl"));
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

        int[] orderQueries = {4, 13, 21, 7, 10, 5, 3, 12};
        int[] sizesOrders = {10,50,100,250,500};
        try {
            PrintWriter writer = new PrintWriter(projectDir + "experiments/finegrain/order-rewritings-nojoin.txt", "UTF-8");
            writer.write("queryid,numberconstraints,rewritingtimenano");
            PrintWriter writer2 = new PrintWriter(projectDir + "experiments/finegrain/order-rewritings-join.txt", "UTF-8");
            writer2.write("queryid,numberconstraints,rewritingtimenano");
            for (Integer queryID : orderQueries) {

                DatalogStatement query = null;
                try {
                    query = parsePostgreSQL.parseFile(projectDir + "queries/" + queryID + "-clean.sql");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                for (Integer size : sizesOrders) {
                    Set<DatalogStatement> sizeSet = new HashSet<>();
                    sizeSet.addAll(orderConstraints.subList(0, size));


                    long[] rewritings = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), sizeSet);
                        long afterRewriting = System.nanoTime();
                        rewritings[k] = afterRewriting - beforeRewriting;
                    }
                    long sum = 0;
                    for (long l : rewritings) {
                        sum += l;
                    }
                    long average = sum / ((long) rewritings.length);

                    writer.write(queryID + "," + size + "," + average + "\n");

                    writer.flush();

                    sizeSet = new HashSet<>();
                    sizeSet.addAll(joinedOrderConstraints.subList(0, size));


                    long[] rewritings2 = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), sizeSet);
                        long afterRewriting = System.nanoTime();
                        rewritings2[k] = afterRewriting - beforeRewriting;
                    }
                    long sum2 = 0;
                    for (long l : rewritings2) {
                        sum2 += l;
                    }
                    long average2 = sum2 / ((long) rewritings2.length);

                    writer2.write(queryID + "," + size + "," + average2 + "\n");
                    writer2.flush();
                }

            }
            writer.close();
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

