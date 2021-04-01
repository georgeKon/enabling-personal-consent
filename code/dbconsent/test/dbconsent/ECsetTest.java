package dbconsent;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dbconsent.DatalogParser.ParseDatalog;

public class ECsetTest {

	@Before
	public void init() {
		PoolOfNames.resetPool();
	}

	@Test
	public void testAddSubstitution() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, new Atom("P"));
		
		EquivalenceClass expected = new EquivalenceClass(query);

		ecSet1.addSubstitution(new Variable("x"), new Variable("b"));
		ecSet1.addSubstitution(new Variable("y"), new Variable("b"));
		expected.equatePredicateArgument(new Variable("x"));
		expected.equatePredicateArgument(new Variable("y"));
		expected.equatePredicateArgument(new Variable("b"));
		
		assertEquals(expected, ecSet1.getEquivalenceClasses().get(new Variable("x")));
		assertEquals(expected, ecSet1.getEquivalenceClasses().get(new Variable("y")));
		assertEquals(expected, ecSet1.getEquivalenceClasses().get(new Variable("b")));
	}
	
	@Test
	public void testAddSubstitutionConstants() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		EquivalenceClass expected = new EquivalenceClass(query);

		ecSet1.addSubstitution(new Variable("a"), new Variable("x"));
		ecSet1.addSubstitution(new Variable("b"), new Variable("y"));
		ecSet1.addSubstitution(new Variable("c"), new IntegerConstant(0));
		expected.equatePredicateArgument(new Variable("c"));
		expected.equatePredicateArgument(new IntegerConstant(0));
		
		assertEquals(expected, ecSet1.getEquivalenceClasses().get(new Variable("c")));
		assertEquals(expected, ecSet1.getEquivalenceClasses().get(new IntegerConstant(0)));
	}
	
	@Test(expected = ConstantClashException.class)
	public void testAddSubstitutionConstantClash() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		
		ecSet1.addSubstitution(new IntegerConstant(1), new Variable("c"));
		ecSet1.addSubstitution(new Variable("c"), new IntegerConstant(0));
	}
	
	@Test(expected = ConstantClashException.class)
	public void testAddSubstitutionConstantClash2() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		
		ecSet1.addSubstitution(new IntegerConstant(1), new IntegerConstant(2));
	}

	@Test
	public void testUnionECsets() throws ConstantClashException {
		ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		ECset ecSet2 = new ECset(query, query.getAtoms().get(0));
		
		ECset expected = new ECset(query, query.getAtoms().get(0));

		ecSet1.addSubstitution(new Variable("x"), new Variable("b"));
		ecSet2.addSubstitution(new Variable("y"), new Variable("b"));
		expected.addSubstitution(new Variable("x"), new Variable("b"));
		expected.addSubstitution(new Variable("y"), new Variable("b"));
		
		assertEquals(expected.getEquivalenceClasses(), ecSet1.unionECsets(ecSet2).getEquivalenceClasses());

	}
	
	@Test
	public void testUnionECsetsEquality() throws ConstantClashException {
		ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		ECset ecSet2 = new ECset(query, query.getAtoms().get(0));
		
		Set<Equality> expected = new HashSet<Equality>();
		expected.add(new Equality(new Variable("b"), new Variable("a")));

		ecSet1.addSubstitution(new Variable("x"), new Variable("b"));
		ecSet2.addSubstitution(new Variable("x"), new Variable("a"));
		
		assertEquals(expected, ecSet1.unionECsets(ecSet2).getEquivalenceClasses().get(new Variable("x")).getEqualities());
	}
	
	@Test
	public void testUnionECsetsConstants() throws ConstantClashException {
		ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		ECset ecSet2 = new ECset(query, query.getAtoms().get(0));
		
		ECset expected = new ECset(query, query.getAtoms().get(0));

		ecSet1.addSubstitution(new Variable("a"), new Variable("x"));
		ecSet2.addSubstitution(new Variable("b"), new Variable("y"));
		ecSet2.addSubstitution(new Variable("b"), new IntegerConstant(4));
		expected.addSubstitution(new Variable("a"), new Variable("x"));
		expected.addSubstitution(new Variable("b"), new Variable("y"));
		expected.addSubstitution(new Variable("b"), new IntegerConstant(4));
		
		assertEquals(expected.getEquivalenceClasses(), ecSet1.unionECsets(ecSet2).getEquivalenceClasses());
	}

	@Test(expected = ConstantClashException.class)
	public void testUnionECsetsConstantClash() throws ConstantClashException {
		ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		ECset ecSet1 = new ECset(query, query.getAtoms().get(0));
		ECset ecSet2 = new ECset(query, query.getAtoms().get(0));

		ecSet1.addSubstitution(new Variable("b"), new IntegerConstant(3));
		ecSet2.addSubstitution(new Variable("b"), new IntegerConstant(4));
		
		ecSet1.unionECsets(ecSet2);
	}
}
