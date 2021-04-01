package dbconsent;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import dbconsent.DatalogParser.ParseDatalog;

public class RewritingAlgorithmTest {

    @Before
    public void init() {
        PoolOfNames.resetPool();
    }

	@Test
	public void testRewriteDatalogStatementUnusedConstraintAtom() {
        String query = "Q(a) :- P(a,b,c),P(b,d,e)";
        String constraint = "N(i) :- P(i,j,0),P(j,k,1)";
        //Expected rewriting: "Q'(a) :- P(a,b,c),P(b,d,e) / P(b,k,1), 0 = c".
        
        Set<Equality> expectedEqualities = new HashSet<>();
        expectedEqualities.add(new Equality(new IntegerConstant(0), new Variable("c")));
        Atom expectedAtom = new Atom("P");
        expectedAtom.addTerm(new Variable("b"));
        expectedAtom.addTerm(new Variable("k"));
        expectedAtom.addTerm(new IntegerConstant(1));
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
			assertEquals(expectedEqualities, rwConstraint.getEqualities());
			assertEquals(new ArrayList<>(Collections.singletonList(expectedAtom)), rwConstraint.getAtoms());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementSameQueryAndConstraint() {
        String query = "Q(a) :- A(a,b,c)";
        String constraint = "N(x) :- A(x,y,z)";
        //Expected rewriting: "Q'(a) :- A(a,b,c), ( 1 ≠ 1 )".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(0, rwConstraint.numberOfAtoms());
            assertEquals(0, rwConstraint.getEqualities().size());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementQueryHeadSupersetOfConstraintHead() {
        String query = "Q(a,b,c) :- A(a,b,c)";
        String constraint = "N(a) :- A(a,b,c)";
        //Expected rewriting: "Q'(a,b,c) :- A(a,b,c), ( 1 ≠ 1 )".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(0, rwConstraint.numberOfAtoms());
            assertEquals(0, rwConstraint.getEqualities().size());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementConstantInQueryCoversConstraint() {
        String query = "Q(x,y) :- A(x,y,5)";
        String constraint = "N(a,c) :- A(a,b,c)";
        //Expected rewriting: "Q'(x,y) :- A(x,y,5), ( 1 ≠ 1 )".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(0, rwConstraint.numberOfAtoms());
            assertEquals(0, rwConstraint.getEqualities().size());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementQueryAndConstraintHeadDisjoint() {
        String query = "Q(a) :- A(a,b,c)";
        String constraint = "N(b) :- A(a,b,c)";
        //Expected rewriting: "Q'(a) :- A(a,b,c)".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(0, rewritingDS.getConstraints().size());
	}
	
	@Test
	public void testRewriteDatalogStatementQueryHeadSubsetOfConstraintHead() {
        String query = "Q(a) :- A(a,b,c)";
        String constraint = "N(a,b) :- A(a,b,c)";
        //Expected rewriting: "Q'(a) :- A(a,b,c)".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(0, rewritingDS.getConstraints().size());
	}
	
	@Test
	public void testRewriteDatalogStatementConstantAndJoinInConstraint() {
        String query = "Q'(a,b,c,d,e) :- P(a,b,c),P(b,d,e)";
        String constraint = "N(i,j,k) :- P(i,j,0),P(j,k,1)";
        //Expected rewriting: "Q'(a,b,c,d,e) :- P(a,b,c),P(b,d,e), ( 1 ≠ c | a ≠ d | 0 ≠ e ), ( 0 ≠ c | 1 ≠ e )".

        Set<Equality> expectedEqualities1 = new HashSet<>();
        expectedEqualities1.add(new Equality(new IntegerConstant(0), new Variable("e")));
        expectedEqualities1.add(new Equality(new IntegerConstant(1), new Variable("c")));
        expectedEqualities1.add(new Equality(new Variable("a"), new Variable("d")));
        Set<Equality> expectedEqualities2 = new HashSet<>();
        expectedEqualities2.add(new Equality(new IntegerConstant(0), new Variable("c")));
        expectedEqualities2.add(new Equality(new IntegerConstant(1), new Variable("e")));
        Set<Set<Equality>> expected = new HashSet<>();
        expected.add(expectedEqualities1);
        expected.add(expectedEqualities2);
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(expected.size(), rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
			assertTrue(expected.contains(rwConstraint.getEqualities()));
		}
	}
	
	@Test
	public void testRewriteDatalogStatementSingleEquality() {
        String query = "Q(a) :- A(a,b)";
        String constraint = "N(d) :- A(d,d)";
        //Expected rewriting: "Q'(a) :- A(a,b), ( a ≠ b )".
        
        Set<Equality> expected= new HashSet<>();
        expected.add(new Equality(new Variable("a"), new Variable("b")));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(expected, rwConstraint.getEqualities());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementConstantInQuery() {
        String query = "Q(a,b) :- A(a,b,c)";
        String constraint = "N(f) :- A(f,g,5)";
        //Expected rewriting: "Q'(a,b) :- A(a,b,c), ( 5 ≠ c )".
        
        Set<Equality> expected= new HashSet<>();
        expected.add(new Equality(new IntegerConstant(5), new Variable("c")));

        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(expected, rwConstraint.getEqualities());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementTransitivePairs() {
        String query = "Q(a,c) :- A(a,b),A(b,c)";
        String constraint = "N(f,g) :- A(f,g)";
        //Expected rewriting: "Q'(a,c) :- A(a,b),A(b,c), ( a ≠ b | b ≠ c | a ≠ c )".

        Set<Equality> expected = new HashSet<>();
        expected.add(new Equality(new Variable("a"), new Variable("b")));
        expected.add(new Equality(new Variable("b"), new Variable("c")));
        expected.add(new Equality(new Variable("a"), new Variable("c")));
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
			assertEquals(expected, rwConstraint.getEqualities());
		}	
    }
	
	@Test
	public void testRewriteDatalogStatementConstantInQueryConstructedPairs() {
        String query = "Q(x,z) :- A(x,5), A(y,z)";
        String constraint = "N(a,b) :- A(a,b)";
        //Expected rewriting: "Q'(x,z) :- A(x,5),A(y,z), ( 1 ≠ 1 )".
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(1, rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
            assertEquals(0, rwConstraint.numberOfAtoms());
            assertEquals(0, rwConstraint.getEqualities().size());
		}
	}
	
	@Test
	public void testRewriteDatalogStatementConstantsInConstraint() {
        String query = "Q(a,b,c,d,e) :- P(a,b,c),P(b,d,e)";
        String constraint = "N(j,k,l,m) :- P(j,k,0),P(l,m,1)";
        //Expected rewriting: "Q'(a,b,c,d,e) :- P(a,b,c),P(b,d,e), ( 1 ≠ c | 0 ≠ e ), ( 0 ≠ c | 1 ≠ e )".
        
        Set<Equality> expectedEqualities1 = new HashSet<>();
        expectedEqualities1.add(new Equality(new IntegerConstant(0), new Variable("e")));
        expectedEqualities1.add(new Equality(new IntegerConstant(1), new Variable("c")));
        Set<Equality> expectedEqualities2 = new HashSet<>();
        expectedEqualities2.add(new Equality(new IntegerConstant(0), new Variable("c")));
        expectedEqualities2.add(new Equality(new IntegerConstant(1), new Variable("e")));
        Set<Set<Equality>> expected = new HashSet<>();
        expected.add(expectedEqualities1);
        expected.add(expectedEqualities2);
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(expected.size(), rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
			assertTrue(expected.contains(rwConstraint.getEqualities()));
		}
	}
	
	@Test
	public void testRewriteDatalogStatementTransitivePairsConstraint() {
        String query = "Q(a,b,c,e) :- A(a,b),A(b,c),B(c,d),B(d,e)";
        String constraint = "N(i,k) :- A(i,j),B(j,k)";
        //Expected rewriting: "Q'(a,b,c,e) :- A(a,b),A(b,c),B(c,d),B(d,e), ( c ≠ d ), ( b ≠ d )".
        
        Set<Equality> expectedEqualities1 = new HashSet<>();
        expectedEqualities1.add(new Equality(new Variable("d"), new Variable("c")));
        Set<Equality> expectedEqualities2 = new HashSet<>();
        expectedEqualities2.add(new Equality(new Variable("d"), new Variable("b")));
        Set<Set<Equality>> expected = new HashSet<>();
        expected.add(expectedEqualities1);
        expected.add(expectedEqualities2);
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
        
        assertEquals(expected.size(), rewritingDS.getConstraints().size());
        for (RewrittenConstraint rwConstraint : rewritingDS.getConstraints()) {
			assertTrue(expected.contains(rwConstraint.getEqualities()));
		}
	}
	
	@Test
	public void testUnify() throws ConstantClashException{
        String query = "Q(x,y,z,w) :- A(x,y), A(y,z), B(z,v), B(v,w)";
		ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        
        Atom queryAtom = new Atom("A");
        queryAtom.addTerm(new Variable("x"));
        queryAtom.addTerm(new Variable("y"));
        Atom constraintAtom = new Atom("A");
        constraintAtom.addTerm(new Variable("a"));
        constraintAtom.addTerm(new Variable("b"));
        ECset unifiedECset = RewritingAlgorithm.unify(queryDS, queryAtom, constraintAtom);
        
        ECset expected = new ECset(queryDS, new Atom("A"));
        expected.addSubstitution(new Variable("x"), new Variable("a"));
		expected.addSubstitution(new Variable("y"), new Variable("b"));

        assert unifiedECset != null;
        assertEquals(expected.getEquivalenceClasses(), unifiedECset.getEquivalenceClasses());
	}
	
	@Test
	public void testUnifyDifferentAtom(){
        String query = "Q(x,y,z,w) :- A(x,y), A(y,z), B(z,v), B(v,w)";
		ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);

        Atom queryAtom = new Atom("A");
        queryAtom.addTerm(new Variable("x"));
        queryAtom.addTerm(new Variable("y"));
        Atom constraintAtom = new Atom("B");
        constraintAtom.addTerm(new Variable("a"));
        constraintAtom.addTerm(new Variable("b"));
        
        ECset unifiedECset = RewritingAlgorithm.unify(queryDS, queryAtom, constraintAtom);

        assertNull(unifiedECset);
	}
	
	@Test
	public void testUnifyDifferentNumberOfTerms(){
        String query = "Q(x,y,z,w) :- A(x,y), A(y,z), B(z,v), B(v,w)";
		ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);

        Atom queryAtom = new Atom("A");
        queryAtom.addTerm(new Variable("x"));
        queryAtom.addTerm(new Variable("y"));
        queryAtom.addTerm(new Variable("z"));
        Atom constraintAtom = new Atom("A");
        constraintAtom.addTerm(new Variable("a"));
        constraintAtom.addTerm(new Variable("b"));
        
        ECset unifiedECset = RewritingAlgorithm.unify(queryDS, queryAtom, constraintAtom);

        assertNull(unifiedECset);
	}
	
	@Test
	public void testUnifyConstantClash(){
        String query = "Q(x,y,z,w) :- A(x,4), A(y,z), B(z,v), B(v,w)";
		ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);

        Atom queryAtom = new Atom("A");
        queryAtom.addTerm(new Variable("x"));
        queryAtom.addTerm(new IntegerConstant(4));
        Atom constraintAtom = new Atom("A");
        constraintAtom.addTerm(new Variable("a"));
        constraintAtom.addTerm(new IntegerConstant(5));
        
        ECset unifiedECset = RewritingAlgorithm.unify(queryDS, queryAtom, constraintAtom);

        assertNull(unifiedECset);
	}
}
