package dbconsent.experiments.experiments;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.PoolOfNames;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.RewritingAlgorithm;
import dbconsent.HeadVariableNotInQueryException;
import dbconsent.experiments.Properties;

import java.io.*;
import java.util.*;

public class TPCHQueriesAndConstraintdRewritingTimeExperiment {

    static Map<Integer, List<DatalogStatement>> constraints = new HashMap<>();
    static List<DatalogStatement> allConstraintsShuffled = new ArrayList<>();

    public static void main(String[] args) {
        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        String folderPath = projectDir + "queries";
        File folder = new File(folderPath);
        ParseDatalog parseDatalog = new ParseDatalog();
        //Load in all the constraints
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
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (fileEntry.getName().equals("shuffledconstraints.dl")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getCanonicalPath()));
                    String line = reader.readLine();
                    while (line != null) {
                        DatalogStatement constraint = parseDatalog.safeParseString(line);
                        constraint.indexAtoms();
                        allConstraintsShuffled.add(constraint);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(projectDir + "experiments/other/rewritingtimes.csv", "UTF-8");
            ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
            for (Integer i : constraints.keySet()) {
                StringBuilder line = new StringBuilder();
                String queryFile = folderPath + "/" + i.toString() + "-clean.sql";

                DatalogStatement query = parsePostgreSQL.parseFile(queryFile);

                for (int j = 0; j < constraints.get(i).size(); j++) {
                    DatalogStatement constraint = constraints.get(i).get(j);
                    line.append("," + "constraint" + j);
                    line.append("," + constraint.numberOfAtoms());

                    long[] rewritings = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), constraint);
                        long afterRewriting = System.nanoTime();
                        rewritings[k] = afterRewriting - beforeRewriting;
                    }
                    DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), constraint);
                    long sum = 0;
                    for (long l : rewritings) {
                        sum += l;
                    }
                    long average = sum / ((long) rewritings.length);

                    line.append("," + rwQuery.getConstraints().size());
                    line.append("," + average);
                }

                int[] sizes = {10, 20, 40, 60, 76};
                for (Integer numberOfConstraints : sizes) {
                    Set<DatalogStatement> someConstraints = new HashSet<>();
                    for (int j = 0; j < numberOfConstraints; j++) {
                        someConstraints.add(allConstraintsShuffled.get(j));
                    }
                    line.append("," + "constraints" + numberOfConstraints);

                    long[] rewritings = new long[11];
                    for (int k = 0; k < 11; k++) {
                        long beforeRewriting = System.nanoTime();
                        DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), someConstraints);
                        long afterRewriting = System.nanoTime();
                        rewritings[k] = afterRewriting - beforeRewriting;
                    }
                    DatalogStatement rwQuery = RewritingAlgorithm.rewriteQuery(new DatalogStatement(query), someConstraints);
                    long sum = 0;
                    for (long l : rewritings) {
                        sum += l;
                    }
                    long average = sum / ((long) rewritings.length);

                    line.append("," + rwQuery.getConstraints().size());
                    line.append("," + average);
                }
                line.deleteCharAt(0);
                writer.write(line + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
