package dbconsent;

import dbconsent.DatalogParser.ParseDatalog;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PropagationRewritingTest {

    @Before
    public void init() {
        PoolOfNames.resetPool();
    }

    @Test
    public void unfoldAndRewriteNoUnfoldNeeded() throws InvalidQueryUnfoldingException {
        // N(x) :- A(x,x)
        // P(x) :- A(x,y)
        // P'(x) :- A(x,y) / x = y
        // Q(a) :- P'(a), B(a,b)
        // Q'(a) :- A(a,c),B(a,b)

        String previousQuery = "P(a) :- A(a,b)";
        String constraint = "N(d) :- A(d,d)";
        String query = "Q(a) :- P'(a), B(a,b)";

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement p = parseDatalog.safeParseString(previousQuery);
        DatalogStatement n = parseDatalog.safeParseString(constraint);
        // Should produce: P'(x) :- A(x,y) / x ≠ y
        DatalogStatement rewrittenPreviousQuery = RewritingAlgorithm.rewriteQuery(p, n);

        DatalogStatement q = parseDatalog.safeParseString(query);
        DatalogStatement unfoldedRewritten = Propagation.minimalUnfoldAndRewriteQuery(q, Collections.singleton(rewrittenPreviousQuery), Collections.singleton(n));

        String expected = "Q(e) :- P'(e),B(e,f)";
        DatalogStatement e = parseDatalog.parseString(expected);

        assertEquals(e.getPredicate(), unfoldedRewritten.getPredicate());
        assertEquals(e.getAtoms(), unfoldedRewritten.getAtoms());
        assertEquals(e.getHeadVariables(), unfoldedRewritten.getHeadVariables());
        assertEquals(0, unfoldedRewritten.getConstraints().size());
//        for (RewrittenConstraint rewrittenConstraint : unfoldedRewritten.getConstraints()) {
//            for (RewrittenConstraint expectedRewrittenConstraint : e.getConstraints()) {
//                assertEquals(rewrittenConstraint, expectedRewrittenConstraint);
//            }
//            assertTrue(e.getConstraints().contains(rewrittenConstraint));
//        }
    }

    @Test
    public void unfoldAndRewriteNewConstraintButNoUnfoldNeeded() throws InvalidQueryUnfoldingException {
        // N(x,z) :- A(x,y), B(x,x,z)
        // P(x) :- A(x,y)
        // P'(x) :- A(x,y)
        // Q(x,z) :- P'(x), B(x,y,z)
        // Q'(x) :- P'(x), B(x,y,z) /

        String previousQuery = "P(v) :- A(v,w)";
        String constraint = "N(a,c) :- A(a,b), B(a,a,c)";
        String query = "Q(x,z) :- P'(x), B(x,y,z)";

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement p = parseDatalog.safeParseString(previousQuery);
        DatalogStatement n = parseDatalog.safeParseString(constraint);
        // Should produce: P'(x) :- A(x,y)
        DatalogStatement rewrittenPreviousQuery = RewritingAlgorithm.rewriteQuery(p, n);

        DatalogStatement q = parseDatalog.safeParseString(query);
        DatalogStatement unfoldedRewritten = Propagation.minimalUnfoldAndRewriteQuery(q, Collections.singleton(rewrittenPreviousQuery), Collections.singleton(n));

        System.out.println(unfoldedRewritten.toString());
    }
}
