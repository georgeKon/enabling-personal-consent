package dbconsent;

import dbconsent.DatalogParser.ParseDatalog;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropagationTest {

    @Before
    public void init() {
        PoolOfNames.resetPool();
    }

    @Test
    public void unfoldQueryBasic() throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        String previousQuery = "P(x) :- A(x,y)";
        String query = "Q(a) :- P(a)";
        String expected = "Q(a) :- A(a,c)";

        DatalogStatement unfolded = unfoldQueryFromStrings(query, Collections.singletonList(previousQuery));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement e = parseDatalog.parseString(expected);

        assertEquals(e.getPredicate(), unfolded.getPredicate());
        assertEquals(e.getAtoms(), unfolded.getAtoms());
        assertEquals(e.getHeadVariables(), unfolded.getHeadVariables());
    }

    @Test
    public void unfoldQueryWithAnotherAtom() throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        String previousQuery = "P(x) :- A(x,y)";
        String query = "Q(a) :- P(a), B(x,y)";
        String expected = "Q(a) :- A(a,e),B(b,c)";

        DatalogStatement unfolded = unfoldQueryFromStrings(query, Collections.singletonList(previousQuery));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement e = parseDatalog.parseString(expected);

        assertEquals(e.getPredicate(), unfolded.getPredicate());
        assertEquals(e.getAtoms(), unfolded.getAtoms());
        assertEquals(e.getHeadVariables(), unfolded.getHeadVariables());
    }

    @Test
    public void unfoldQueryMultiplePreviousQueries() throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        String[] previousQueries = { "P(x) :- A(x,y)", "R(x) :- B(x,y)" };
        String query = "Q(a) :- P(a), R(a)";
        String expected = "Q(a) :- A(a,c),B(a,e)";

        DatalogStatement unfolded = unfoldQueryFromStrings(query, Arrays.asList(previousQueries));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement e = parseDatalog.parseString(expected);

        assertEquals(e.getPredicate(), unfolded.getPredicate());
        assertEquals(e.getAtoms(), unfolded.getAtoms());
        assertEquals(e.getHeadVariables(), unfolded.getHeadVariables());
    }

    @Test
    public void unfoldQueryJoinAcrossPreviousQueries() throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        String[] previousQueries = { "R(x,y) :- B(x,y)", "P(x,y) :- A(x,y)" };
        String query = "Q(a,c) :- P(a,b), R(b,c)";
        String expected = "Q(a,c) :- A(a,b),B(b,c)";

        DatalogStatement unfolded = unfoldQueryFromStrings(query, Arrays.asList(previousQueries));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement e = parseDatalog.parseString(expected);

        assertEquals(e.getPredicate(), unfolded.getPredicate());
        assertEquals(e.getAtoms(), unfolded.getAtoms());
        assertEquals(e.getHeadVariables(), unfolded.getHeadVariables());
    }

    @Test(expected = InvalidQueryUnfoldingException.class)
    public void unfoldQueryTooGeneralUsage() throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        String previousQuery = "P(x,x) :- A(x,x)";
        String query = "Q(a) :- P(a,b)";

        unfoldQueryFromStrings(query, Collections.singletonList(previousQuery));
    }

    @Test
    public void unfoldQuerySingleEquality() throws InvalidQueryUnfoldingException {
        String previousQuery = "P(a) :- A(a,b)";
        String constraint = "N(d) :- A(d,d)";
        String query = "Q(e) :- P(e),B(e,f)";

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement p = parseDatalog.safeParseString(previousQuery);
        DatalogStatement n = parseDatalog.safeParseString(constraint);
        // Should produce: P(x) :- A(x,y) / x ≠ y
        DatalogStatement rewrittenPreviousQuery = RewritingAlgorithm.rewriteQuery(p, n);

        DatalogStatement q = parseDatalog.safeParseString(query);
        DatalogStatement unfolded = Propagation.unfoldQuery(q, Collections.singleton(rewrittenPreviousQuery));

        String expected = "Q(e) :- A(e,b),B(e,f)";
        DatalogStatement e = parseDatalog.parseString(expected);
        RewrittenConstraint expectedConstraint = new RewrittenConstraint("N");
        expectedConstraint.addEquates(Collections.singleton(new Equality(new Variable("b"), new Variable("e"))));
        e.applyConstraints(Collections.singleton(expectedConstraint));

        assertEquals(e.getPredicate(), unfolded.getPredicate());
        assertEquals(e.getAtoms(), unfolded.getAtoms());
        assertEquals(e.getHeadVariables(), unfolded.getHeadVariables());
        for (RewrittenConstraint rewrittenConstraint : unfolded.getConstraints()) {
            for (RewrittenConstraint expectedRewrittenConstraint : e.getConstraints()) {
                assertEquals(rewrittenConstraint, expectedRewrittenConstraint);
            }
            assertTrue(e.getConstraints().contains(rewrittenConstraint));
        }
    }

    private static DatalogStatement unfoldQueryFromStrings(String query, List<String> previousQueries) throws InvalidQueryUnfoldingException, HeadVariableNotInQueryException {
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement q = parseDatalog.safeParseString(query);
        Set<DatalogStatement> ps = previousQueries.stream().map(parseDatalog::safeParseString).collect(Collectors.toSet());

        return Propagation.unfoldQuery(q, ps);
    }
}
