package dbconsent;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dbconsent.DatalogParser.ParseDatalog;

public class EquivalenceClassTest {

	@Before
	public void init() {
		PoolOfNames.resetPool();
	}

	@Test
	public void testEquatePredicateArgumentEquality() {
		ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		Set<Equality> expected = new HashSet<Equality>();
		expected.add(new Equality(new Variable("a"), new Variable("b")));
		
		try {
			ec1.equatePredicateArgument(new Variable("a"));
			ec1.equatePredicateArgument(new Variable("x"));
			ec1.equatePredicateArgument(new Variable("b"));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		assertEquals(expected, ec1.getEqualities());
	}
	
	@Test
	public void testEquatePredicateArgumentConstantEquality(){
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		Set<Equality> expected = new HashSet<>();
		expected.add(new Equality(new IntegerConstant(4), new Variable("a")));
		
		try {
			ec1.equatePredicateArgument(new IntegerConstant(4));
			ec1.equatePredicateArgument(new Variable("a"));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
		assertEquals(expected, ec1.getEqualities());
	}
	
	@Test
	public void testEquatePredicateArgumentSameConstant(){
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass expected = new EquivalenceClass(query);

		try {
			ec1.equatePredicateArgument(new IntegerConstant(4));
			ec1.equatePredicateArgument(new IntegerConstant(4));
			expected.equatePredicateArgument(new IntegerConstant(4));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
		assertEquals(expected, ec1);
	}
	
	@Test
	public void testEquatePredicateArgumentSameVariable(){
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass expected = new EquivalenceClass(query);

		try {
			ec1.equatePredicateArgument(new Variable("a"));
			ec1.equatePredicateArgument(new Variable("a"));
			expected.equatePredicateArgument(new Variable("a"));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
		assertEquals(expected, ec1);
	}
	
	@Test
	public void testEquatePredicateArgumentConstantAlwaysRepresentative(){
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);

		try {
			ec1.equatePredicateArgument(new IntegerConstant(0));
			ec1.equatePredicateArgument(new Variable("a"));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
		assertEquals(new IntegerConstant(0), ec1.getRepresentative());
	}
	
	@Test(expected = ConstantClashException.class)
	public void testEquatePredicateArgumentConstantClash() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		ec1.equatePredicateArgument(new IntegerConstant(4));
		ec1.equatePredicateArgument(new IntegerConstant(3));
	}

	@Test
	public void testInPlaceUnion() {
        ParseDatalog parseDatalog = new ParseDatalog();
        
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass ec2 = new EquivalenceClass(query);
		EquivalenceClass expected = new EquivalenceClass(query);
		try {
			ec1.equatePredicateArgument(new Variable("x"));
			ec1.equatePredicateArgument(new Variable("b"));
			ec2.equatePredicateArgument(new Variable("y"));
			ec2.equatePredicateArgument(new Variable("b"));
			expected.equatePredicateArgument(new Variable("x"));
			expected.equatePredicateArgument(new Variable("y"));
			expected.equatePredicateArgument(new Variable("b"));
			
			assertEquals(expected, EquivalenceClass.inPlaceUnion(ec1, ec2));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInPlaceUnionEquality() {
        ParseDatalog parseDatalog = new ParseDatalog();
        
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass ec2 = new EquivalenceClass(query);
		
		Set<Equality> expected = new HashSet<>();
		expected.add(new Equality(new Variable("b"), new Variable("a")));
		
		try {
			ec1.equatePredicateArgument(new Variable("x"));
			ec1.equatePredicateArgument(new Variable("b"));
			ec2.equatePredicateArgument(new Variable("x"));
			ec2.equatePredicateArgument(new Variable("a"));
			
			assertEquals(expected, EquivalenceClass.inPlaceUnion(ec1, ec2).getEqualities());
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testInPlaceUnionConstant() {
        ParseDatalog parseDatalog = new ParseDatalog();
        
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass ec2 = new EquivalenceClass(query);
		EquivalenceClass expected = new EquivalenceClass(query);
		try {
			ec1.equatePredicateArgument(new Variable("x"));
			ec1.equatePredicateArgument(new Variable("b"));
			ec2.equatePredicateArgument(new IntegerConstant(0));
			ec2.equatePredicateArgument(new Variable("b"));
			expected.equatePredicateArgument(new Variable("x"));
			expected.equatePredicateArgument(new IntegerConstant(0));
			expected.equatePredicateArgument(new Variable("b"));
			
			assertEquals(expected, EquivalenceClass.inPlaceUnion(ec1, ec2));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = ConstantClashException.class)
	public void testInPlaceUnionConstantClash() throws ConstantClashException {
        ParseDatalog parseDatalog = new ParseDatalog();
        
		DatalogStatement query = parseDatalog.safeParseString("Q(a,b,c,d,e) :- P(a,b,c), P(b,d,e)");
		EquivalenceClass ec1 = new EquivalenceClass(query);
		EquivalenceClass ec2 = new EquivalenceClass(query);
		
		try {
			ec1.equatePredicateArgument(new Variable("b"));
			ec1.equatePredicateArgument(new IntegerConstant(1));
			ec2.equatePredicateArgument(new IntegerConstant(0));
			ec2.equatePredicateArgument(new Variable("b"));
		} catch (ConstantClashException e) {
			e.printStackTrace();
		}
		
		EquivalenceClass.inPlaceUnion(ec1, ec2);
	}
}
