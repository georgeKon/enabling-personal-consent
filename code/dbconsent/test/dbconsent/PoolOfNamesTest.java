package dbconsent;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dbconsent.DatalogParser.ParseDatalog;

public class PoolOfNamesTest {

	@Before
	public void init() {
		PoolOfNames.resetPool();
	}
	
	@Test
	public void testSafeNamespaceSimple() throws HeadVariableNotInQueryException {
		String query = "Q(x) :- P(x,y,z),P(y,w,v)";
		String expected = "Q(a) :- P(a,b,c),P(b,d,e)";
		
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.parseString(query);
        DatalogStatement expectedDS = parseDatalog.parseString(expected);
        
        PoolOfNames.safeNamespace(queryDS);
        assertEquals(expectedDS.getAtoms(), queryDS.getAtoms());
        assertEquals(expectedDS.getHeadVariables(), queryDS.getHeadVariables());
	}
	
	@Test
	public void testSafeNamespaceConstants() throws HeadVariableNotInQueryException {
        String query = "Q(x,y,z,v) :- A(x,4), A(y,z), B(z,v), B(v,3)";
		String expected = "Q(a,b,c,d) :- A(a,4), A(b,c), B(c,d), B(d,3)";
		
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.parseString(query);
        DatalogStatement expectedDS = parseDatalog.parseString(expected);
        
        PoolOfNames.safeNamespace(queryDS);
        assertEquals(expectedDS.getAtoms(), queryDS.getAtoms());
        assertEquals(expectedDS.getHeadVariables(), queryDS.getHeadVariables());
	}
	
	@Test(expected = HeadVariableNotInQueryException.class)
	public void testSafeNamespaceUnsafeQuery() throws HeadVariableNotInQueryException {
        String query = "Q(x,y) :- A(y,z)";
		
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.parseString(query);
        
        PoolOfNames.safeNamespace(queryDS);
	}

	@Test
	public void testSafeNamespaceRewrittenConstraintAtom() throws HeadVariableNotInQueryException {
        String query = "Q(x) :- P(x,y,z)";
        // Constraint: P(y,w,1)
        // Expected: Q(a) :- P(a,b,c) / P(b,d,1), c = 1
        String expected = "Q(a) :- P(a,b,c)";
		
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.parseString(query);
        RewrittenConstraint constraint = new RewrittenConstraint("N");
        Atom constraintAtom = new Atom("P");
        constraintAtom.addTerm(new Variable("y"));
        constraintAtom.addTerm(new Variable("w"));
        constraintAtom.addTerm(new IntegerConstant(1));
        constraint.addAtom(constraintAtom);
        Equality equality = new Equality(new Variable("z"), new IntegerConstant(1));
        constraint.addEquates(new HashSet<>(Collections.singletonList((equality))));
        queryDS.applyConstraints(new HashSet<>(Collections.singletonList((constraint))));
        
        DatalogStatement expectedDS = parseDatalog.parseString(expected);
        RewrittenConstraint expectedConstraint = new RewrittenConstraint("N");
        Atom expectedConstraintAtom = new Atom("P");
        expectedConstraintAtom.addTerm(new Variable("b"));
        expectedConstraintAtom.addTerm(new Variable("d"));
        expectedConstraintAtom.addTerm(new IntegerConstant(1));
        expectedConstraint.addAtom(expectedConstraintAtom);
        Equality expectedEquality = new Equality(new Variable("c"), new IntegerConstant(1));
        expectedConstraint.addEquates(new HashSet<>(Collections.singletonList((expectedEquality))));
        
        PoolOfNames.safeNamespace(queryDS);
        
        assertEquals(expectedDS.getAtoms(), queryDS.getAtoms());
        assertEquals(expectedDS.getHeadVariables(), queryDS.getHeadVariables());
        assertEquals(1, queryDS.getConstraints().size());
        for (RewrittenConstraint rewrittenConstraint : queryDS.getConstraints()) {
        	assertEquals(expectedConstraint.getAtoms(), rewrittenConstraint.getAtoms());
        	assertEquals(expectedConstraint.getEqualities(), rewrittenConstraint.getEqualities());
        }
	}
	
	@Test
	public void testSafeNamespaceRewrittenConstraintEquality() throws HeadVariableNotInQueryException {
        String query = "Q(x) :- P(x,y,z)";
        // Expected: Q(a) :- P(a,b,c) / c = 1
        String expected = "Q(a) :- P(a,b,c)";
		
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.parseString(query);
        RewrittenConstraint constraint = new RewrittenConstraint("N");
        Equality equality = new Equality(new Variable("z"), new IntegerConstant(1));
        constraint.addEquates(new HashSet<>(Collections.singletonList((equality))));
        queryDS.applyConstraints(new HashSet<>(Collections.singletonList((constraint))));
        
        DatalogStatement expectedDS = parseDatalog.parseString(expected);
        RewrittenConstraint expectedConstraint = new RewrittenConstraint("N");
        Equality expectedEquality = new Equality(new Variable("c"), new IntegerConstant(1));
        expectedConstraint.addEquates(new HashSet<>(Collections.singletonList((expectedEquality))));
        
        PoolOfNames.safeNamespace(queryDS);
        
        assertEquals(expectedDS.getAtoms(), queryDS.getAtoms());
        assertEquals(expectedDS.getHeadVariables(), queryDS.getHeadVariables());
        assertEquals(1, queryDS.getConstraints().size());
        for (RewrittenConstraint rewrittenConstraint : queryDS.getConstraints()) {
        	assertEquals(expectedConstraint.getAtoms(), rewrittenConstraint.getAtoms());
        	assertEquals(expectedConstraint.getEqualities(), rewrittenConstraint.getEqualities());
        }
	}
}
