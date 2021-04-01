package dbconsent.experiments;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Properties {

    private String HOST = "postgresql://127.0.0.1:5432/";
    private String USERNAME = "postgres";
    private String PASSWORD = "password";
    private String PROJECTDIR = "/home/dbconsent/";

    public Properties() {
        try (InputStream is = new FileInputStream("dbconsent.properties")) {
            java.util.Properties props = new java.util.Properties();
            props.load(is);
            HOST = props.getProperty("host");
            USERNAME = props.getProperty("username");
            PASSWORD = props.getProperty("password");
            PROJECTDIR = props.getProperty("projectdir");
        }
        catch(IOException e) {
            System.out.println("Unable to find the dbconsent.properties file in working directory.");
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getHost() {
        return HOST;
    }

    public String getProjectDir() { return PROJECTDIR; }

    public Connection connect() throws ConnectException {
        return makeConnect("jdbc:" + HOST);
    }

    public Connection connectDB(String dbName) throws ConnectException {
        return makeConnect("jdbc:" + HOST + dbName);
    }

    private Connection makeConnect(String url) {
        Connection connection = null;
        Exception exception = null;
        int retries = 0;
        while(connection == null && retries < 50) {
            try {
                connection = DriverManager.getConnection(url + "?loggerLevel=OFF", USERNAME, PASSWORD);
            } catch (SQLException e) {
                exception = e;
                retries++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (connection == null){
            exception.printStackTrace();
        }
        return connection;
    }

}
