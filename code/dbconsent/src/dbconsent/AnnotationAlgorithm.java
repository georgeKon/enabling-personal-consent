package dbconsent;

import dbconsent.DatalogParser.ParseDatalog;
import dbconsent.PostgreSQLParser.ParsePostgreSQL;
import dbconsent.experiments.Experiment;
import dbconsent.experiments.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AnnotationAlgorithm {

    static HashSet<Integer> tuplesToRemove = new HashSet<>(); // Set of ints corresponding to row number of tuples in resultSet to remove

    public static void main (String [] args){
        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        ParseDatalog parseDatalog = new ParseDatalog();
        ParsePostgreSQL parsePostgreSQL = new ParsePostgreSQL();
        Experiment experiment = new Experiment(connect);

        ArrayList<DatalogStatement> constraints = new ArrayList<>();

        try {
            //Parse Query
            DatalogStatement query = parsePostgreSQL.parseFile(projectDir + "queries+constraints/tpch/10-clean.sql");

            // Load in constraints
            BufferedReader reader = new BufferedReader(new FileReader(projectDir + "queries+constraints/tpch/25-tpch001-joined-customer-constraint.dl"));
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                DatalogStatement constraint = parseDatalog.safeParseString(line);
                constraint.indexAtoms();
                constraints.add(constraint);
                line = reader.readLine();
            }

            // Add just first constraint to list - for purposes of speeding up
//            ArrayList<DatalogStatement> single_con_list = new ArrayList<>();
//            single_con_list.add(constraints.get(0));
//            single_con_list.addAll(constraints);
            ArrayList<DatalogStatement> constraints25 = new ArrayList<>();
            for (int i = 0 ; i < 25; i++){
                constraints25.add(constraints.get(i));
            }


            // Call algorithm on the query and constraints
            ResultSet annotation_result = AnnotationAlgorithm.filterQuerySet(query, experiment, constraints25);

            String rwQuery = RewritingAlgorithm.rewriteQuery(query, new HashSet<>(constraints)).toPostgreSQLString();
            ResultSet rewriting_result = experiment.execQuery(rwQuery);

            // TESTING ALIGNMENT
            /*Map<String, ArrayList<String>> queryCols = new HashMap<>();
            queryCols.put("C", new ArrayList<>(Arrays.asList("C", "A")));
            queryCols.put("A", new ArrayList<>(Arrays.asList("A")));
            queryCols.put("B", new ArrayList<>(Arrays.asList("B", "E")));
            queryCols.put("E", new ArrayList<>(Arrays.asList("E")));
            queryCols.put("D", new ArrayList<>(Arrays.asList("D")));

            Map<String, ArrayList<String>> constCols = new HashMap<>();
            constCols.put("B", new ArrayList<>(Arrays.asList("B", "A"))); // with 3
            constCols.put("C", new ArrayList<>(Arrays.asList("C")));  // with 1
            constCols.put("A", new ArrayList<>(Arrays.asList("A", "E")));  // with 2
            constCols.put("D", new ArrayList<>(Arrays.asList("D"))); // with 5

            Map<String, String> currentAlignment = new HashMap<>();
            Set alignments = new HashSet();
            System.out.println("Final alignments" + alignmentRec(queryCols, constCols, currentAlignment, alignments));*/

            // TESTING WITH REWRITING
            /*int s_tally = 0;
            int f_tally = 0;

            // TODO - Doesn't clearly show when annotated has removed less than rewriting
            REWRITING_TUPLES:
            while (rewriting_result.next()){  // loop through rewriting tuples
                annotation_result.beforeFirst();
                ANNOTATION_TUPLES:
                while(annotation_result.next()) {  // loop through annotation tuples
                    if (!tuplesToRemove.contains(annotation_result.getRow())){ // If not removed (annotation), compare.
                        // Compare tuples -
                        for (int i = 1; i<=rewriting_result.getMetaData().getColumnCount() ; i++) {
                            // If there is no match we know they tuples differ and continue with next tuple (of annotation)
                            if (!rewriting_result.getArray(i).toString().equals(annotation_result.getArray(i).toString())){
                                continue ANNOTATION_TUPLES;
                            }
                        }
                        s_tally += 1;
                        //System.out.println("TUPLE MATCH " + s_tally);
                        // If there is a match, we don't need to look at any more and continue to next tuple (of rewriting)
                        continue REWRITING_TUPLES;
                    }
                }
                f_tally += 1;
                System.out.println("FAILED TO MATCH TALLY : " + f_tally);
                // If we fail all tuples of annotation, we know there wan't a match
            }

            System.out.println("Matched tuples: " + s_tally);
            System.out.println("Non matched tuples: " + f_tally);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AnnotatedQuery annotateQuery(DatalogStatement query, Experiment experiment){
        return annotateQuery(query.toPostgreSQLString(), experiment);
    }

    private static AnnotatedQuery annotateQuery(String query, Experiment experiment){

        // Split query into list based on new line
        String [] SQList = query.split("\n");

        // Determine, SELECT, FROM and WHERE parts of the query. WHERE has to deal with ANDs.
        String [] selectList = SQList[0].substring(7).split(", ");
        String fromList = SQList[1];
        String [] whereList = null;
        try {
            whereList = String.join("\n", Arrays.copyOfRange(SQList, 2, SQList.length)).substring(6).split("\nAND ");
        } catch (IndexOutOfBoundsException e){
            // DO NOTHING AS THERE IS NO WHERE CLAUSE WHICH IS OK
        }

        ArrayList<String> requiredIds = new ArrayList<>();  // List of id we need to project
        Map<String, ArrayList<String>> idMapping = new HashMap<>();  // Map of column to the relation
        Map<String, ArrayList<String>> equivCols = new HashMap<>();  // Map of columns which are equivalent (due to joining)

        if (selectList[0].equals("*")){
            try {
                selectList = getAttribList(experiment, fromList);
            } catch (SQLException e) {e.printStackTrace();}
        }

        for (String s: selectList){
            String relation = s.trim().split("\\.")[0].trim();

            // Add each head variable, with its annotation (relation id), to map
            ArrayList<String> a = new ArrayList<>();
            a.add("id_" + relation);
            idMapping.put(s.trim(), a);

            // Add these annotations to those we want to project
            addId(relation, requiredIds);

            // Add each head variable to its set of associated column names
            ArrayList var = new ArrayList();
            var.add(s.trim());
            equivCols.put(s.trim(), var);
        }

        // Loop through where clause and add to appropriate mappings
        if (!(whereList == null)){
            for (String w: whereList){
                String w_left = w.split(" = ")[0].trim();
                String w_right = w.split(" = ")[1].trim();
                if (w_right.contains(".")) {  // checking that we are equating attributes
                    if (idMapping.containsKey(w_left)){
                        idMapping.get(w_left).add("id_" + getAttRelation(w_right));
                        addId(getAttRelation(w_right), requiredIds);
                    }
                    if (idMapping.containsKey(w_right)){
                        idMapping.get(w_right).add("id_" + getAttRelation(w_left));
                        addId(getAttRelation(w_left), requiredIds);
                    }
                    if (equivCols.containsKey(w_left)){
                        equivCols.get(w_left).add(w_right);
                    }
                    if (equivCols.containsKey(w_right)){
                        equivCols.get(w_right).add(w_left);
                    }
                }
            }
        }


        // Build up new SQL statement with required ids added
        String aSQL = "SELECT " + String.join(", ", selectList).trim();
        for (String r : requiredIds){
            aSQL += ", " + r + ".id AS id_" + r;
        }
        if (!(whereList == null)){
            aSQL += "\n" + fromList + "\n" + "WHERE " + String.join("\nAND ", whereList);
        } else{aSQL += "\n" + fromList;}

        return new AnnotatedQuery(aSQL, idMapping, equivCols, requiredIds);
    }

    private static String getAttRelation (String att){
        return att.split("\\.")[0];
    }

    private static void addId(String relation, ArrayList<String> requiredIds){
        if (!requiredIds.contains(relation.trim())){
            requiredIds.add(relation.trim());
        }
    }

    private static String [] getAttribList (Experiment experiment, String from) throws SQLException {

        String [] fromList  = from.substring(5).split(", ");
        ArrayList<String> atts = new ArrayList<>();

        for (String f : fromList){
            String relation = f.split(" ")[0].trim();
            String relation_name = f.split(" ")[1].trim();
            String q = "SELECT * FROM " + relation + " LIMIT 0";
            ResultSet RS = experiment.execQuery(q);
            RS.getMetaData().getColumnCount();
            for (int i = 1; i <= RS.getMetaData().getColumnCount(); i++){
                if (!RS.getMetaData().getColumnName(i).equals("id")){
                    atts.add(relation_name + "." + RS.getMetaData().getColumnName(i));
                }
            }
        }
        return atts.toArray(new String[0]);
    }

    private static ResultSet filterOutConstraint(ResultSet qResult, AnnotatedQuery aq, ResultSet cResult, AnnotatedQuery ac) {

        try {
            qResult.beforeFirst();
            cResult.beforeFirst();

            for (Map<String, String> alignment : createAlignments(aq, ac)){ // loop alignments
                qResult.beforeFirst();
                cResult.beforeFirst();
                ITERATE_QUERY_TUPLES:
                while (qResult.next()){  // loop through query tuples
                    while (cResult.next()){  // loop through constraint tuple
                        for (String q_col : alignment.keySet()) {  // loop columns of tuple
                            if (qResult.getArray(reformat_column(q_col)).toString().equals(cResult.getArray(reformat_column(alignment.get(q_col))).toString())){ // If the values of the column are the same
                                // for each in idMapping, if they are the same, if the values are the same
                                for (String id_table_q : aq.idMapping.get(q_col)){  // loop through annotations/ ids/ tables of column for query
                                    for (String id_table_c : ac.idMapping.get(alignment.get(q_col))){  // loop through annotations/ ids/ tables of column for constraint
                                        if (id_table_q.equals(id_table_c)){  // if they come from the same table, i.e annotations are from the same id
                                            if (qResult.getArray(id_table_q).toString().equals(cResult.getArray(id_table_c).toString())){  // if they have same id value, i.e tuple in table
                                                //qResult.deleteRow();
                                                tuplesToRemove.add(qResult.getRow());
                                                cResult.beforeFirst();
                                                continue ITERATE_QUERY_TUPLES;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    cResult.beforeFirst();
                }
                qResult.beforeFirst();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return qResult;
    }

    public static ResultSet filterQuerySet (DatalogStatement query, Experiment experiment, List<DatalogStatement> constraints){
        // Annotate query
        AnnotatedQuery aq = AnnotationAlgorithm.annotateQuery(query, experiment);

        // Execute query
        ResultSet qResult = experiment.execQuery(aq.annotated_postgresSQL);
        try {
            for (DatalogStatement constraint : constraints){
                AnnotatedQuery ac = annotateQuery(constraint, experiment);
                ResultSet cResult = experiment.execQuery(ac.annotated_postgresSQL);
                qResult = AnnotationAlgorithm.filterOutConstraint(qResult, aq, cResult, ac);
            }

           /* MyResultSet QResultSet = new MyResultSet(qResult);
            for (String id: aq.getIdList()){
                QResultSet.removeColumn(id);
            }*/

            return qResult; // TODO - not removed annotations from resultSet
        } catch (Exception e) {
            e.printStackTrace();
            return null; // TODO Not sure what to do if I can't popualte with the cachedRowSet
        }
    }

    public static ResultSet filterQuerySet (String query, Experiment experiment, List<String> constraints){
        // Annotate query
        AnnotatedQuery aq = AnnotationAlgorithm.annotateQuery(query, experiment);
        // Execute query
        ResultSet qResult = experiment.execQuery(aq.annotated_postgresSQL);
        for (String constraint : constraints){
            AnnotatedQuery ac = annotateQuery(constraint, experiment);
            ResultSet cResult = experiment.execQuery(ac.annotated_postgresSQL);
            qResult = AnnotationAlgorithm.filterOutConstraint(qResult, aq, cResult, ac);
        }
        return qResult;
    }

    public static HashSet<Integer> getTuplesToRemove() {
        return tuplesToRemove;
    }

    private static Set<Map<String, String>> createAlignments(AnnotatedQuery aq, AnnotatedQuery ac){
        Map<String, String> currentAlignment = new HashMap<>();
        Set alignments = new HashSet();
        return alignmentRec(aq.equivCols, ac.equivCols, currentAlignment, alignments);
    }

    private static Set<Map<String, String>> alignmentRec (Map<String, ArrayList<String>> q_cols, Map<String, ArrayList<String>>  c_cols, Map<String, String> currentAlignment, Set<Map<String, String>> alignmentSet){

        // BASE CONDITION OF RECURSION - IF ALL CONSTRAINTS MATCHED
        if (c_cols.isEmpty()){
            alignmentSet.add(currentAlignment);
            return alignmentSet;
        }

        Set<String> constraint_columns = c_cols.keySet();
        Iterator<String> constraintIT = constraint_columns.iterator();
        Set<String> query_columns = q_cols.keySet();
        Iterator<String> queryIT = query_columns.iterator();

        while (constraintIT.hasNext()){
            String c = constraintIT.next();
            while (queryIT.hasNext()){
                String q = queryIT.next();
                if (checkColumnOverlap(c_cols.get(c), q_cols.get(q))){
                    // create match
                    HashMap<String, String> new_currentAlignment = new HashMap<>(currentAlignment);
                    new_currentAlignment.put(q, c);
                    HashMap<String, ArrayList<String>> new_q_cols = new HashMap<>(q_cols);
                    HashMap<String, ArrayList<String>> new_c_cols = new HashMap<>(c_cols);
                    new_q_cols.remove(q);
                    new_c_cols.remove(c);
                    alignmentSet.addAll(alignmentRec(new_q_cols, new_c_cols, new_currentAlignment, alignmentSet));
                }
            }
        }
        return alignmentSet;
    }

    private static boolean checkColumnOverlap(ArrayList<String> qs, ArrayList<String> cs){
        ArrayList<String> intersection = new ArrayList<>(qs); // use the copy constructor
        intersection.retainAll(cs);
        return !intersection.isEmpty();
    }

    private static String reformat_column(String col){

        return col.split("\\.")[1].toLowerCase(Locale.ROOT);
    }

    private static class AnnotatedQuery {
        private String annotated_postgresSQL;
        private Map<String, ArrayList<String>> idMapping;
        private Map<String, ArrayList<String>> equivCols;
        private ArrayList<String> idList;

        public AnnotatedQuery (String annotated_postgresSQL, Map<String, ArrayList<String>> idMapping, Map<String, ArrayList<String>> equivCols, ArrayList<String> idList){
            this.annotated_postgresSQL = annotated_postgresSQL;
            this.idMapping = idMapping;
            this.equivCols = equivCols;
            this.idList = idList;
        }

        public String getAnnotated_postgresSQL() {
            return annotated_postgresSQL;
        }

        public Map<String, ArrayList<String>> getIdMapping() {
            return idMapping;
        }

        public Map<String, ArrayList<String>> getEquivCols() {
            return equivCols;
        }

        public ArrayList<String> getIdList() {
            return idList;
        }
    }

}
