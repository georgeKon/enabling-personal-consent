package dbconsent.generation;

import dbconsent.Atom;
import dbconsent.DatalogStatement;
import dbconsent.Term;
import dbconsent.Variable;
import dbconsent.experiments.Properties;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PropagatedQueryGenerator {

    // Map of heads of standard TPCH queries.
    private static Map<Integer, Atom> queryHeads = new HashMap<>();

    static {
        queryHeads.put(10, new Atom("Q10", Arrays.asList(new Variable("c_custkey"), new Variable("c_name"),
                new Variable("c_acctbal"), new Variable("n_name"), new Variable("c_address"),
                new Variable("c_phone"), new Variable("c_comment"))));
        queryHeads.put(13, new Atom("Q13", Arrays.asList(new Variable("c_custkey"), new Variable("o_orderkey"))));
        queryHeads.put(18, new Atom("Q18", Arrays.asList(new Variable("c_name"), new Variable("c_custkey"),
                new Variable("o_orderkey"), new Variable("o_orderdate"), new Variable("o_totalprice"),
                new Variable("l_quantity"))));
        queryHeads.put(4, new Atom("Q4", Arrays.asList(new Variable("o_orderpriority"))));
        queryHeads.put(21, new Atom("Q21", Arrays.asList(new Variable("s_name"))));
        queryHeads.put(7, new Atom("Q7", Arrays.asList(new Variable("l_shipdate"), new Variable("l_extendedprice"))));
        queryHeads.put(5, new Atom("Q5", Arrays.asList(new Variable("n_name"))));
        queryHeads.put(3, new Atom("Q3", Arrays.asList(new Variable("l_orderkey"), new Variable("l_extendedprice"), new Variable("o_orderdate"), new Variable("o_shippriority"))));
    }

    public static void main(String[] args) {
        generatePropagatedQueries(10, new int[]{10, 13, 18, 4, 21, 7, 5, 3});
    }

    public static void generatePropagatedQueries(int numberToGenerate, int[] previousQueriesToUse) {
        Random rng = new Random();
        DatalogStatement[] generatedQueries = new DatalogStatement[numberToGenerate];
        for (int i = 0; i < numberToGenerate; i++) {
            DatalogStatement query = new DatalogStatement("P");
            for (Integer q : previousQueriesToUse) {
                if (rng.nextFloat() < 0.3) {
                    query.addAtom(new Atom(queryHeads.get(q)));
                }
            }

            // Ensure that at least one atom is added.
            if (query.getAtoms().size() == 0) {
                Integer queryToAdd = previousQueriesToUse[rng.nextInt(previousQueriesToUse.length)];
                query.addAtom(new Atom(queryHeads.get(queryToAdd)));
            }

            // Ensure that at least one head variable is added.
            while (query.getHeadVariables().isEmpty()) {
                for (Atom a : query.getAtoms()) {
                    for (Term t : a.getTerms()) {
                        if (t instanceof Variable && rng.nextFloat() < 0.4) {
                            query.addHeadVariable((Variable) t);
                        }
                    }
                }
            }

            generatedQueries[i] = query;
        }

        Properties connect = new Properties();
        String projectDir = connect.getProjectDir();

        try (PrintWriter writer = new PrintWriter(projectDir + "queries+constraints/tpch/propagated.dl", "UTF-8")) {
            for (DatalogStatement generatedQuery : generatedQueries) {
                writer.println(generatedQuery.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}