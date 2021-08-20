package dbconsent;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AggregationReapplication {

    static List<DatalogStatement> customerConstraints = new ArrayList<>();
    static List<DatalogStatement> joinedCustomerConstraints = new ArrayList<>();

    public static void main(String[] args) {

        Properties properties = new Properties();
        String projectDir = properties.getProjectDir();
        Experiment experiment = new Experiment(properties);

        ParseDatalog parseDatalog = new ParseDatalog();

        // Load in constraints
        int tpchscale = 7;
        int count = 500;
        try {
//            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/"+count+"-tpch"+tpchscale+"-joined-order-constraint.dl"));
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/25-tpch001-joined-customer-constraint.dl"));
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
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/"+count+"-tpch"+tpchscale+"-customer-constraint.dl"));
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


        // Load queries
        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL(); // 10
//        int[] queries = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21};
        int[] queries = {10};
        
        int noConstraints = 25;  // no.constraints

        /*
        // Gets random constraints of size  
        Set<DatalogStatement> constraints = new HashSet<>();
        Random randomGenerator = new Random();
        for (int i = 0; i<noConstraints; i++){
            int index = randomGenerator.nextInt(customerConstraints.size());
            constraints.add(customerConstraints.get(index));
        }*/
        // Gets first size () constraints
        Set<DatalogStatement> constraints = new HashSet<>(joinedCustomerConstraints.subList(0, noConstraints));

        for (Integer queryID : queries) {

            System.out.println(queryID);

            DatalogStatement query_clean = null;
            String query = "";
            try {
                query_clean = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/" + queryID + "-clean.sql");
                query = readFile(projectDir + "queries+constraints/tpch/" + queryID + "-initial.sql");
                //System.out.println("Query: \n" + query);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String rwQuery = RewritingAlgorithm.rewriteQuery(query_clean, constraints).toPostgreSQLString();
//            System.out.println("rwQuery:\n" + rwQuery);

            // Call METHOD
            ArrayList<String> resultSQL = convertQuery(query, rwQuery);
            String new_query = resultSQL.get(1);
            System.out.println(new_query);
            if (resultSQL.get(0) != null){
                experiment.execQuery(resultSQL.get(0));
                experiment.execQuery(new_query);
                experiment.execQuery(resultSQL.get(2));
            } else{
                experiment.execQuery(new_query);
            }
        }
    }

    // Will return ArrayList with first being the query, second being the before part of the query and after part of the query
    public static ArrayList<String> convertQuery(String original_query, String rwQuery){

        String query;
        String before_query = null;
        String drop_queries = null;

        // Check if the query uses views and if so determine before_query and the drop query lines
        if (original_query.split("\ncreate view").length == 1) {
            query = "select" + original_query.split("\nselect")[1].split("\n:")[0].replaceAll("\r\n", "\n");
        }else{
            query = "create view" + original_query.replaceAll(":[os]", "").split("\ncreate view")[1].split("\n:")[0].replaceAll("\r\n", "\n");

            String [] query_parts = query.split(";\n");

            before_query = query_parts[0] + ";";

            // take the query part and remove the double newline
            query = query_parts[1].replaceAll("\n\n", "") + ";";
            drop_queries = query_parts[2].trim();
        }
        query = "\n" + query.replaceAll("\r\n", "\n").replaceAll(":[os]", "").replaceAll("\r", "");


        // Now we split the main part of the query into 3 parts, the where clause and what comes before (select ... from ...) and after (group by ... ect)
        // However, there may not be a where which we need to also consider
        boolean isWhere;
        String before_where;
        String after_where = "";
        String [] query_where;
        String where = "";


        // Splits on newline where and selects the part after. Substring is to remove space above. (2) - without tab. Then split on newline tab
        try{
            query_where = (query.split("\nwhere")[1]).substring(2).split("\n\tand ");
            isWhere = true;
        } catch (ArrayIndexOutOfBoundsException e){
            //System.out.println("No where clause");
            query_where = new String [0];
            isWhere = false;
        }

        // We have to determine where the where clause ends, if there is one. If not, then where the from clause ends.
        // To do this we take want to split when we come to a newline which doesn't start with a tab
        String [] toSplitOnNoTab;

        // The following part determines the before and after where clause parts
        if (isWhere){
            before_where = "select" + (query.split("\nwhere")[0]).split("\nselect")[1]; // todo might not need .replace()

            // defines a list of lines, after the last where and
                // split removes the newlines, so have to add
            String [] last_where = query_where[query_where.length -1].split("\n");

            // removes the first line from last_where
            last_where = Arrays.copyOfRange(last_where, 1,last_where.length);

            // Replaces last element of query_where with just the first line of it, as we have removed from last_where
            query_where[query_where.length - 1] = query_where[query_where.length- 1].split("\n")[0];

            toSplitOnNoTab = last_where;

            after_where = splitOnFirstNoTab(last_where).get(1).toString();

        } else {
            String [] splitOnFrom = query.split("\nfrom\n");
            String before_from = "select" + (splitOnFrom[0]).split("\nselect")[1];

            // defining list of lines after from
                // split removes the newlines, so hove to add
            toSplitOnNoTab = splitOnFrom[1].split("\n");

            // define end of the from
            String from = "\nfrom" + splitOnFirstNoTab(toSplitOnNoTab).get(0).toString();
            after_where = splitOnFirstNoTab(toSplitOnNoTab).get(1).toString();

            before_where = before_from + from;
        }

        // generates where from query_where
        where = "where";
        boolean first = true;
        for (String s: query_where){
            if (first){
                where += "\n\t" + s;
                first = false;
            } else{
                where += "\n\tand " + s;
            }
        }

        if (isWhere) {
            // if there is a where, we want to add the end of the where clause
            where = where + splitOnFirstNoTab(toSplitOnNoTab).get(0).toString();
        }

        String [] rwq_where = null;
        
        rwQuery = rwQuery.replaceAll("\\)AND \\(", "\\)\nAND \\(");
        try {

            // Split rewritten query on newline
            String [] SQList = rwQuery.split("\n");
            // Take from third line (WHERE) and split on "\n) \nAND"
            String fromWhere = String.join("\n", Arrays.copyOfRange(SQList, 2, SQList.length));
            rwq_where = fromWhere.substring(0, fromWhere.length()-2).split("\n\\)\nAND ");

            // Determine which statements in the rewritten query we want
            // Only take those in form "NOT EXISTS", or "\nAND (\n" or "WHERE (\n"
            try {
                //remove all before NOT EXISTS
                rwq_where[0] = rwq_where[0].substring(rwq_where[0].indexOf("NOT EXISTS"));
            } catch (IndexOutOfBoundsException e){
                try {
                    Pattern andPattern = Pattern.compile("\nAND [(]\n");
                    Matcher andMatcher = andPattern.matcher(rwq_where[0]);

                    Pattern wherePattern = Pattern.compile("WHERE [(]\n");
                    Matcher whereMatcher = wherePattern.matcher(rwq_where[0]);

                    if (whereMatcher.find()) {
                        rwq_where[0] = rwq_where[0].substring(whereMatcher.start()+6);
                    }
                    else if (andMatcher.find()){
                        rwq_where[0] = rwq_where[0].substring(andMatcher.start()+5);
                    } else {
                        // if pattern not matches, then we have 0 constraints to add to the query
                        rwq_where = new String [0];
                    }
                } catch (IndexOutOfBoundsException f){
                    rwq_where = new String [0];
                }
            }
            
            // Turn [] to arrayList so can use collection's methods (map)
            List<String> rwq_where_A = new ArrayList<>(Arrays.asList(rwq_where));

            // Create list of relation names (aliases)
            String [] reQ_Froms = Arrays.stream(rwQuery.split("\n")[1].substring(5).split(",")).map(s -> s.replaceAll("[A-Z]+ | [A-Z]+ ", "")).toArray(String[]::new);

            // Turning the list of names into a regex to remove from where clause statements
            String toReplaceRegex = String.join(".|", reQ_Froms).trim() + ".";

            // Map across the constraints (where clause statements) removing relation name and adding appropriate formatting
            rwq_where = rwq_where_A.stream().map(s ->
                    {
                        if (s.contains("NOT EXIST")) {
                            return s.replaceAll(toReplaceRegex, "").replaceAll("\n", "\n\t") + ")";
                        } else {
                            return s.replaceAll(toReplaceRegex, "").replaceAll("\\(\n", "(").replaceAll("\n", "\n\t\t") + ")";
                        }
                    }).toArray(String[]::new);

        } catch (IndexOutOfBoundsException e){
            // DO NOTHING AS THERE IS NO WHERE CLAUSE WHICH IS OK
        }

        if (rwq_where != null){
            if (isWhere){ // Then we put the constraints in the where clause
                for (String s : rwq_where){
                    if (s.startsWith("NOT EXISTS")){
                        if (!where.contains("\n")){
                            where += "\n\t " + s;
                        } else {  // ensuring first doesn't start with and
                            where += "\n\n\tand " + s;
                        }

                    } else {
                        if (where.contains("\n")){
                            where += "\n\n\tand " + s;
                        } else {  // ensuring first doesn't start with and
                            where += "\n\t" + s;
                        }
                    }

                }
            }
            else {  // We put the constraints after the last 'and' in before_where
                // TODO IS HARDCODEY - THIS JUST ADDS TO THE LAST AND, BUT COULD BE MUTLIPLE INNER QUERIES

                String [] splitOnAnds = before_where.split("\n\t+and ");
                String lastSplitAnd = splitOnAnds[splitOnAnds.length - 1];
                String [] splitOnLine = lastSplitAnd.split("\n");
                String lastSplitLine = splitOnLine[0];

                for (String s : rwq_where){
                    lastSplitLine += "\n\n\t\tand " + s;
                }

                splitOnLine[0] = lastSplitLine;
                splitOnAnds[splitOnAnds.length - 1] = String.join("\n", splitOnLine);
                before_where = String.join("\n\t\t and ", splitOnAnds);
            }
        }

        // i.e there is no where at all
        if (!where.contains("\n")){
            where = "";
        }
        // if the is nothing after the where and we append to the where we need to move the ";" to the end again
        if (where.contains(";")){
            where = where.replaceAll(";", "");
            where += ";";
        }

        String returnQuery = before_where + "\n" + where + after_where.replaceAll("\n:n -*[0123456789]+", "");
//        System.out.println(returnQuery);

        // Prepare return
        ArrayList<String> returnArrayList = new ArrayList<>();
        returnArrayList.add(before_query);
        returnArrayList.add(returnQuery);
        returnArrayList.add(drop_queries);

        return returnArrayList;
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

    private static ArrayList<StringBuilder> splitOnFirstNoTab (String [] lines){
        StringBuilder before_SB = new StringBuilder();
        StringBuilder after_SB = new StringBuilder();
        boolean done = false;
        for (String line : lines){
            if (!done){
                // If doesn't start with a tab, we are now done
                if (!line.startsWith("\t")){
                    after_SB.append("\n");
                    after_SB.append(line);
                    done = true;
                } else{
                    // If starts with tab, we are still adding to where
                    before_SB.append("\n");
                    before_SB.append(line);
                }
            }else{
                // If we are done, we just add to after_where
                after_SB.append("\n");
                after_SB.append(line);
            }
        }
        return new ArrayList<>(Arrays.asList(before_SB, after_SB));
    }
}
