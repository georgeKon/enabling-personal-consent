package dbconsent.experiments;

import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;
import dbconsent.experiments.Query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

public class HippocraticExperiment {

    protected String tableName = "CUSTOMER";
    protected String tableKey = "C_CUSTKEY";
    protected List<String> columns;

    public void experiment(String[] projectedColumns, String[] choiceColumns, String filesuffix){
        //Default settings for Customer table from TPCH
        this.columns = new ArrayList<>();
        columns.add("C_CUSTKEY");
        columns.add("C_NAME");
        columns.add("C_ADDRESS");
        columns.add("C_NATIONKEY");
        columns.add("C_PHONE");
        columns.add("C_ACCTBAL");
        columns.add("C_MKTSEGMENT");
        columns.add("C_COMMENT");

        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();
        Experiment experiment = new Experiment(connect);

        int experimentSizes[] = {1, 5, 10, 15};
        int selectivities[] = {100, 90, 50, 10, 1};
        //Map of experiment size to Map
        Map<Integer,Map<Integer,float[]>> csvPrep = new HashMap<>();

        //Get the data
        try {
            for(int i: experimentSizes) {
                Map<Integer,float[]> experimentData = new HashMap<>();
                csvPrep.put(i,experimentData);

                //Use this to ensure that Postgres is actually up, not to clear cache.
                experiment.clearPostgresCache();

                //RUN THE INTERNAL PARTS
                for (int selectivity : selectivities) {
                    float[] values = new float[8];
                    experimentData.put(selectivity,values);
                    //Set up database
                    String pathToSchema = projectDir + "tpch-data/customer" + i +"million/internal"+selectivity+"/schema_internal.sql";
                    experiment.setupDatabase(pathToSchema, "EXPLAIN ANALYSE SELECT * FROM CUSTOMER;");

                    values[1] = experiment.timeQuery(hippocraticInternalPrivacyAwareQuery(selectivity, choiceColumns, projectedColumns)).execution;
                    values[2] = experiment.timeQuery(dbconsentInternalPrivacyAwareQueryBruteForce(selectivity, choiceColumns, projectedColumns)).execution;
                    values[3] = experiment.timeQuery(dbconsentOptimisedInternalPrivacyAwareQuery(selectivity, choiceColumns, projectedColumns)).execution;
                }

                //RUN THE EXTERNAL PARTS
                //We run different external selectivities on same DB to stop us having to load in the data constantly
                String pathToSchema = projectDir + "tpch-data/customer" + i +"million/schema_external.sql";
                //Set up database
                experiment.setupDatabase(pathToSchema, "EXPLAIN ANALYSE SELECT * FROM CUSTOMER;");

                for (int selectivity : selectivities) {
                    float[] values = experimentData.get(selectivity);
                    values[0] = experiment.timeQuery(unmodifiedQuery(projectedColumns)).execution;
                    values[4] = experiment.timeQuery(hippocraticExternalPrivacyAwareQuery(selectivity, choiceColumns, projectedColumns)).execution;
                    values[5] = experiment.timeQuery(dbconsentExternalCompactQueryNotExists(selectivity, choiceColumns, projectedColumns)).execution;
                    values[6] = experiment.timeQuery(dbconsentExternalCompactQueryLeftJoin(selectivity, choiceColumns, projectedColumns)).execution;
                    values[7] = experiment.timeQuery(dbconsentExternalCompactQueryExcept(selectivity, choiceColumns, projectedColumns)).execution;
                    experimentData.put(selectivity,values);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Save the data to a file
        try {
            PrintWriter writer = new PrintWriter(projectDir + "hippocratic/executiontimes" + filesuffix + ".csv","UTF-8");
            writer.write("dbscale(millions),selectivity(%),UM(ms),HIPPO-IN(ms),NEU-IN(ms),NE-IN(ms),HIPPO-EX(ms),NE-EX(ms),LOJ-EX(ms),E-EX(ms)\n");
            for (Integer databasesize : csvPrep.keySet()){
                Map<Integer,float[]> data = csvPrep.get(databasesize);
                for(Integer selectivity : data.keySet()) {
                    float[] executionTimes = data.get(selectivity);
                    writer.write(databasesize+selectivity+","+String.join(",", Arrays.toString(executionTimes).replace("[","").replace("]",""))+"\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String externalExistsBody(String tableName, String columnName, String keyName, int density, String condition){
        String choiceTableName = tableName + "_" + columnName + "_" + density;
        return "SELECT " + keyName + " FROM " + choiceTableName + " WHERE " +
                tableName + "." + keyName + " = " + choiceTableName + "." + keyName + " AND " +
                choiceTableName + "." + columnName + "_CHOICE " + condition;
    }

    private String externalExists(String tableName, String columnName, String keyName, int density, String condition){
        return "EXISTS (" + externalExistsBody(tableName, columnName, keyName, density, condition) + ")";
    }

    private String caseStatement(String condition, String columnName) {
        return "(CASE WHEN " + condition + " THEN " + columnName + " ELSE null END)";
    }

    private String internalCondition(String columnName, int density, String condition) {
        return columnName + "_CHOICE_" + density + condition;
    }

    private String createSelects(String[] selectedAttributes){
        return createSelects(selectedAttributes, "");
    }

    private String createSelects(String[] selectedAttributes, String tableName){
        String crossProduct = "";
        String prepend = tableName.equals("") ? "" : tableName + ".";
        for (String column : selectedAttributes){
            crossProduct += prepend + column + ",";
        }
        //Remove trailing comma
        return crossProduct.substring(0, crossProduct.length() - 1);
    }

    public String externalUnionWhereClause(String columnChoice, boolean negation, int density){
        String op = negation ? "!=" : "=";
        return "CUSTOMER_" + columnChoice + "_"+density + ".C_CUSTKEY = CUSTOMER.C_CUSTKEY AND CUSTOMER_" + columnChoice + "_"+ density +"." + columnChoice + "_CHOICE"+op+"0";
    }

    public String externalUnionSelect(String columnChoice, int density){
        return "(SELECT CUSTOMER.C_CUSTKEY, CUSTOMER.C_NAME, CUSTOMER.C_ADDRESS, CUSTOMER.C_NATIONKEY, CUSTOMER.C_PHONE, CUSTOMER.C_ACCTBAL, CUSTOMER.C_MKTSEGMENT, CUSTOMER.C_COMMENT \n" +
                "FROM CUSTOMER, CUSTOMER_" + columnChoice +"_"+ density + " \n" +
                "WHERE \n" +
                externalUnionWhereClause(columnChoice, false, density) +
                ") \n";
    }

    public String unmodifiedQuery(String[] selectedAttributes) {
        return "SELECT " + createSelects(selectedAttributes) + " FROM CUSTOMER";
    }

    public String hippocraticInternalPrivacyAwareQuery(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT \n";
        for (String selectedAttribute : selectedAttributes) {
            //If the selected attribute has a choice, add a case statement
            if (Arrays.asList(choiceColumns).contains(selectedAttribute)) {
                query += caseStatement(internalCondition(selectedAttribute, density, "=1"), selectedAttribute);
            } else {
                query += selectedAttribute;
            }
            //Add a comma (to create cross join) if we're not the last element
            if (!selectedAttributes[selectedAttributes.length - 1].equals(selectedAttribute)) {
                query += ",\n";
            } else {
                query += "\n";
            }
        }
        query += "FROM " + tableName + " \n";
        if (Arrays.asList(choiceColumns).contains(tableKey)) {
            query += "WHERE \n";
            query += internalCondition(tableKey, density, "=1");
        }
        return query;
    }

    private String internalNotExists(int density, String choiceColumn, String[] selectedAttributes){
        return "NOT EXISTS(SELECT " + createSelects(selectedAttributes) + " FROM " + tableName + " c2 WHERE \n" +
                choiceColumn + "_CHOICE_"+ density + "=0 AND c1." + tableKey + "=c2." + tableKey + ")";
    }

    /* NOT EXISTS version */
    private String dbconsentInternalPrivacyAwareQueryBruteForce(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT " + createSelects(selectedAttributes) + " FROM " + tableName + " c1 \n";
        query += "WHERE\n";
        for (String choiceColumn : choiceColumns ){
            query += internalNotExists(density, choiceColumn, selectedAttributes);

            //Add an AND if we're not the last element
            if (!choiceColumns[choiceColumns.length - 1].equals(choiceColumn)) {
                query += " AND \n";
            } else {
                query += "\n";
            }
        }
        return query;
    }

    /* LEFT OUTER JOIN version */
    private String dbconsentOptimisedInternalPrivacyAwareQuery(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT " + createSelects(selectedAttributes) + " FROM " + tableName + " \n";
        query += "WHERE \n";
        for (String choiceColumn : choiceColumns ){
            query += internalCondition(choiceColumn, density, "!=0");

            //Add an AND if we're not the last element
            if (!choiceColumns[choiceColumns.length - 1].equals(choiceColumn)) {
                query += " AND \n";
            } else {
                query += "\n";
            }
        }
        return query;
    }

    private String hippocraticExternalPrivacyAwareQuery(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT \n";
        for (String selectedAttribute : selectedAttributes) {
            //If the selected attribute has a choice, add a case statement
            if (Arrays.asList(choiceColumns).contains(selectedAttribute)) {
                query += caseStatement(externalExists(tableName,selectedAttribute, tableKey, density, "=1"), selectedAttribute);
            } else {
                query += selectedAttribute;
            }
            //Add a comma (to create cross join) if we're not the last element
            if (!selectedAttributes[selectedAttributes.length - 1].equals(selectedAttribute)) {
                query += ",\n";
            } else {
                query += "\n";
            }
        }
        query += "FROM " + tableName + " \n";
        if (Arrays.asList(choiceColumns).contains(tableKey)) {
            query += "WHERE \n";
            query += externalExists(tableName, tableKey, tableKey, density, "= 1");
        }
        return query;
    }

    private String externalNotExists(int density, String choiceColumn, String[] selectedAttributes){
        String choiceTable = tableName + "_" + choiceColumn + "_" + density;
        return "NOT EXISTS(\n" +
                "SELECT * FROM " + choiceTable + " WHERE \n" +
                choiceTable + "." + tableKey + " = " + tableName + "." + tableKey +
                " AND " +
                choiceTable + "." + choiceColumn + "_CHOICE=0\n)";
    }

    private String dbconsentExternalCompactQueryNotExists(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT " + createSelects(selectedAttributes) + " FROM " + tableName + " \n";
        query += "WHERE\n";
        for (String choiceColumn : choiceColumns ){
            query += externalNotExists(density, choiceColumn, selectedAttributes);

            //Add an AND if we're not the last element
            if (!choiceColumns[choiceColumns.length - 1].equals(choiceColumn)) {
                query += " AND \n";
            } else {
                query += "\n";
            }
        }
        return query;
    }

    private String leftOuterJoin(String columnName, int density){
        return "LEFT OUTER JOIN CUSTOMER_"+ columnName +"_"+ density +" ON CUSTOMER_"+ columnName +"_"+ density +".C_CUSTKEY = CUSTOMER.C_CUSTKEY";
    }

    private String dbconsentExternalCompactQueryLeftJoin(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT " + createSelects(selectedAttributes, tableName) + " \n";
        query += "FROM \n";
        //All tables in original query inner joined
        query += tableName + " \n";
        //All (additional tables from constraint inner joined) from each constraint that applies
        for (String choiceColumn : choiceColumns ){
            query += leftOuterJoin(choiceColumn, density) + " \n";
        }
        query += "WHERE \n";
        for (String choiceColumn : choiceColumns ){
            //The condition from the constraint
            query += "(" + choiceColumn + "_CHOICE!=0 \n";
            //OR the join was null
            query += "OR \n";
            query += tableName + "_" + choiceColumn + "_" + density + "." + tableKey + " IS NULL)";

            //Add an AND if we're not the last element
            if (!choiceColumns[choiceColumns.length - 1].equals(choiceColumn)) {
                query += " AND \n";
            } else {
                query += "\n";
            }
        }
        return query;
    }

    private String dbconsentExternalCompactQueryExcept(int density, String[] choiceColumns, String[] selectedAttributes) {
        String query = "";
        query += "SELECT " + createSelects(selectedAttributes, "c1") + " FROM " + tableName + " c1 \n";
        query += "EXCEPT \n";
        query += "( \n";
        for (String choiceColumn : choiceColumns) {
            String choiceTable = tableName + "_" + choiceColumn + "_" + density;
            query += "(SELECT " + createSelects(selectedAttributes, tableName) + " FROM " + tableName + ", " + choiceTable + " \n";
            query += "WHERE \n";
            query += choiceTable + "." + tableKey + " = " + tableName + "." + tableKey + " \n";
            query += "AND " + choiceTable + "." + choiceColumn + "_CHOICE=0) \n";

            //Add a UNION ALL if we're not the last element
            if (!choiceColumns[choiceColumns.length - 1].equals(choiceColumn)) {
                query += "UNION ALL \n";
            } else {
                query += "\n";
            }
        }
        query += ") \n";
        return query;
    }

}
