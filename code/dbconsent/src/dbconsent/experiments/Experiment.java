package dbconsent.experiments;

import java.io.*;
import java.net.ConnectException;
import java.sql.*;

import org.json.*;

public class Experiment {

    protected Properties properties;
    protected static final int timeout = 3600;
    protected String dbname;

    public Experiment(Properties psqlconnect) {
        this.properties = psqlconnect;
        this.dbname = "dbconsent";
    }

    public Experiment(Properties psqlconnect, String dbname) {
        this.properties = psqlconnect;
        this.dbname = dbname;
    }

    protected ResultSet execQuery(Connection conn, String query) throws SQLException {
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setQueryTimeout(timeout);
        return statement.executeQuery(query);
    }

    public ResultSet execQuery(String query){
        try (Connection conn = properties.connectDB(dbname)) {
            return execQuery(conn, query);
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean checkQuerySameResults(Connection conn, String query1, String query2) throws SQLException {
        ResultSet results1 = conn.createStatement().executeQuery(query1);
        ResultSet results2 = conn.createStatement().executeQuery(query2);

        //If not same width, fail
        if (results1.getMetaData().getColumnCount() != results2.getMetaData().getColumnCount()) return false;

        while(results1.next()){
            //If results2 is shorter than results1, fail
            if(!results2.next()) return false;

            //For each column in each row, if the cell is not equal, fail
            for (int i = 1; i < results1.getMetaData().getColumnCount() + 1; i++ ){
                if(!results1.getObject(i).equals(results2.getObject(i))){
                    return false;
                }
            }
        }
        //If results2 still has entries (ie results1 shorter), fail, otherwise pass
        return !results2.next();
    }

    public void execUpdate(Connection conn, String query) throws SQLException {
        conn.createStatement().executeUpdate(query);
    }

    protected void execShellCommand(String command) throws InterruptedException, IOException {
        Process p = Runtime.getRuntime().exec(command);
        StringBuffer output = new StringBuffer();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";
        while ((line = reader.readLine())!= null) {
            output.append(line + "\n");
        }
        System.out.println(output.toString());
        p.waitFor();
    }

    public void clearPostgresCache(){
        try {
            //This script allows all users to execute it.
            //It clears the postgresql cache
            execShellCommand("sudo /home/dbconsent/code/clearpostgrescache.sh");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Results timeQuery(String query){
        Connection connection = null;
        float planning[] = new float[5];
        float execution[] = new float[5];
        boolean didtimeout[] = new boolean[5];
        for (int i = 0; i < 5; i++){
            if(didtimeout[0] && didtimeout[1]){
                //Give up!
                return new Results(planning, execution);
            }
            clearPostgresCache();
            //Thread.sleep(1000);
            try {
                connection = properties.connectDB(dbname);
                ResultSet result = execQuery(connection, "EXPLAIN (ANALYZE TRUE, FORMAT JSON) " + query);
                result.next();
                JSONArray outerArray = new JSONArray(result.getString(1));
                JSONObject outerObject = outerArray.getJSONObject(0);
                planning[i] = outerObject.getFloat("Planning Time");
                execution[i] = outerObject.getFloat("Execution Time");
                connection.close();
            } catch (SQLTimeoutException e){
                planning[i] = 0;
                execution[i] = timeout * 1000;
                didtimeout[i] = true;
            } catch (ConnectException | SQLException e) {
                e.printStackTrace();
            } finally {
                try { connection.close(); } catch (Exception e) { }
            }
        }
        return new Results(planning, execution);
    }

    public int sizeOfQuery(String query) {
        String countQuery = "SELECT COUNT(*) FROM (" + query + ") as foo;";
        try {
            clearPostgresCache();
            //Thread.sleep(1000);
            Connection connection = properties.connectDB(dbname);
            ResultSet result = execQuery(connection, countQuery);
            result.next();
            return result.getInt(1);
        } catch (ConnectException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Only for dbconsent (TPCH)
    public void setupDatabase(String pathToSchema, String warmupQuery) throws SQLException, IOException, InterruptedException {
        //Wipe database
        Connection baseConnection = properties.connect();
        execUpdate(baseConnection, "DROP DATABASE IF EXISTS DBCONSENT;");
        execUpdate(baseConnection,"CREATE DATABASE DBCONSENT;");
        baseConnection.close();

        //Load in our fresh data
        execShellCommand("psql -U " + properties.getUsername() + " -d dbconsent -f " + pathToSchema);

        //Warm up database
        System.out.println("Warming Up Database");
        warmUpDatabase(warmupQuery);
    }

    protected void warmUpDatabase(String query){
        try {
            Connection connection = properties.connectDB(dbname);
            for (int i = 0; i < 5; i++){
                execQuery(connection, query);
            }
            connection.close();
            clearPostgresCache();
        } catch (ConnectException | SQLException e) {
            e.printStackTrace();
        }
    }

}
