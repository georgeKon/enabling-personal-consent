package dbconsent.experiments.experiments;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.DatalogStatement;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.*;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpressivenessExperiment {

    public static void main(String[] args) {
        runExperiment();
    }

    public static void runExperiment() {
        Properties properties = new Properties();
        String projectDir = properties.getProjectDir();
        /*try {
            properties.connectDB("dbconsent");
        }catch (ConnectException e){
            e.printStackTrace();
        }*/
        Experiment experiment = new Experiment(properties);
        ParseDatalog parseDatalog = new ParseDatalog();
        PrintWriter writer_rw = null;
        PrintWriter writer_hi = null;
        try {
            writer_rw = new PrintWriter(projectDir + "results/expressiveness/rewriting.txt", "UTF-8");
            writer_rw.write("queryid,querysize\n");
            writer_hi = new PrintWriter(projectDir + "results/expressiveness/hippo.txt", "UTF-8");
            writer_hi.write("queryid,querysize\n");
        } catch (FileNotFoundException | UnsupportedEncodingException e){
            e.printStackTrace();
        }

        // Queries -
        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();

        ArrayList<Integer> queries = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 21));

        Map<Integer, DatalogStatement> queryMap = new HashMap<>();
        for (Integer queryID : queries) {

            System.out.println("qID: " + queryID);
            DatalogStatement query = null;
            try {
                query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");
                queryMap.put(queryID, query);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int sizeResult = experiment.sizeOfQuery(query.toPostgreSQLString());

            if (writer_rw != null){
                writer_rw.write(queryID + ","  + sizeResult + "\n");
                writer_rw.flush();
            }
        }
            writer_rw.close();

        nullConstraintEntries(experiment, 10, properties);

        for (Integer queryID : queries) {
            System.out.println("qID: " + queryID);

            int sizeResult = experiment.sizeOfQuery(queryMap.get(queryID).toPostgreSQLString());

            if (writer_hi != null){
                writer_hi.write(queryID + "," + sizeResult + "\n");
                writer_hi.flush();
            }
        }
        writer_hi.close();

        resetDB(experiment, properties);

    }

    private static void nullConstraintEntries(Experiment experiment, int rate, Properties properties){

        // 20, 21, 2, 5, 9, 11
        String s_nationkeyNullableUpdate = "ALTER TABLE supplier ALTER COLUMN s_nationkey DROP NOT NULL";
        String supplier_nationkeyUpdate = "UPDATE supplier SET s_nationkey = NULL WHERE id % " + rate + " = 0";

        // 10
        String c_nationkeyNullableUpdate = "ALTER TABLE customer ALTER COLUMN c_nationkey DROP NOT NULL";
        String customer_nationkeyUpdate = "UPDATE customer SET c_nationkey = NULL WHERE id % " + rate + " = 0";

        // 19, 17, 8
        String l_partkeyNullableUpdate = "ALTER TABLE lineitem ALTER COLUMN l_partkey DROP NOT NULL";
        String lineitem_partkeyUpdate = "UPDATE lineitem SET l_partkey = NULL WHERE id % " + rate + " = 0";

        // 3, 13, 7, 18
        String o_custkeyNullableUpdate = "ALTER TABLE orders ALTER COLUMN o_custkey DROP NOT NULL";
        String orders_custkeyUpdate = "UPDATE orders SET o_custkey = NULL WHERE id % " + rate + " = 0";

        // 4 (orderkey), 12 (orderkey), 16 (p_partkey)
        String o_orderkeyUpdate = "ALTER TABLE orders DROP CONSTRAINT orders_pkey cascade;\n" +
                "\tALTER TABLE orders ALTER COLUMN o_orderkey DROP NOT NULL;\n" +
                "\tUPDATE orders SET o_orderkey = NULL WHERE id % 10 = 0;";

        String p_partkeyUpdate = "ALTER TABLE part DROP CONSTRAINT part_pkey cascade;\n" +
                "\tALTER TABLE part ALTER COLUMN p_partkey DROP NOT NULL;\n" +
                "\tUPDATE part SET p_partkey = NULL WHERE id % 10 = 0;";

        String n_nationkeyUpdate = "ALTER TABLE nation DROP CONSTRAINT nation_pkey cascade;\n" +
                "\tALTER TABLE nation ALTER COLUMN n_nationkey DROP NOT NULL;\n" +
                "\tUPDATE nation SET n_nationkey = NULL WHERE id % 10 = 0;";

        String c_custkeyUpdate = "ALTER TABLE customer DROP CONSTRAINT customer_pkey cascade;\n" +
                "\tALTER TABLE customer ALTER COLUMN c_custkey DROP NOT NULL;\n" +
                "\tUPDATE customer SET c_custkey = NULL WHERE id % 10 = 0;";

        try {
            Connection alterConn = properties.connectDB("dbconsent");

            // 20, 21, 2, 5, 9, 11
            experiment.execUpdate(alterConn, s_nationkeyNullableUpdate);
            experiment.execUpdate(alterConn, supplier_nationkeyUpdate);
            // 10
            experiment.execUpdate(alterConn, c_nationkeyNullableUpdate);
            experiment.execUpdate(alterConn, customer_nationkeyUpdate);
            // 19, 17, 8
            experiment.execUpdate(alterConn, l_partkeyNullableUpdate);
            experiment.execUpdate(alterConn, lineitem_partkeyUpdate);
            // 3, 13, 7, 18
            experiment.execUpdate(alterConn, o_custkeyNullableUpdate);
            experiment.execUpdate(alterConn, orders_custkeyUpdate);
            // 4 (orderkey), 12 (orderkey), 16 (p_partkey)

            experiment.execUpdate(alterConn, o_orderkeyUpdate);
            experiment.execUpdate(alterConn, p_partkeyUpdate);
//            experiment.execUpdate(alterConn, nullUpdate3);
//            experiment.execUpdate(alterConn, nullUpdate4);

            alterConn.close();
        } catch (SQLException | ConnectException e){
            e.printStackTrace();
        }
    }

    // Just dbconsent
    private static void resetDB(Experiment experiment, Properties properties){
        try {
            String projectDir = properties.getProjectDir();

            Connection baseConnection = properties.connect();
            experiment.execUpdate(baseConnection, "REVOKE CONNECT ON DATABASE dbconsent FROM public;");
            experiment.execQuery("SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
                    "FROM pg_stat_activity\n" +
                    "WHERE pg_stat_activity.datname = 'dbconsent';");

            //Wipe database
            experiment.execUpdate(baseConnection, "DROP DATABASE IF EXISTS DBCONSENT;");
            experiment.execUpdate(baseConnection,"CREATE DATABASE DBCONSENT;");
            baseConnection.close();

            //Change schema.sql file to copy the data from the appropriate directory
            String sql = readFile(projectDir + "tpch-data/scale001/schema.sql");
            System.out.println(sql);
            sql = sql.replaceAll("/home/enabling-personal-consent/", projectDir);

            File schema_file = new File(projectDir + "tpch-data/schema.sql");
            try{
                FileWriter fw = new FileWriter(schema_file, false);
                fw.write(sql);
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }

            //Load in our fresh data
//            System.out.println("psql -U " + properties.getUsername() + " -d dbconsent -f " + pathToSchema);
            System.out.println(projectDir);
            ProcessBuilder loadData = new ProcessBuilder("/bin/sh", "-c", "psql -U postgres -d dbconsent -f " + projectDir + "tpch-data/schema.sql --host=localhost");
            loadData.environment().put("PGPASSWORD", "password");
            Process ld_p = loadData.start();
            ld_p.waitFor();
            if (ld_p.exitValue() == 0){
                ProcessBuilder addAnnotations = new ProcessBuilder("/bin/sh", "-c", "python " + projectDir + "code/add-annotations/add-annotations.py " + projectDir + "code/add-annotations/database_config.txt");
                Process aa_p = addAnnotations.start();
                aa_p.waitFor();
                System.out.println("Database restored");
            } else{
                System.out.println("Database restoration failed");
                System.out.println(ld_p.getErrorStream());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
