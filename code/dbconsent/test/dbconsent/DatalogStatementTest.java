package dbconsent;

import static org.junit.Assert.*;

import org.junit.Test;

import dbconsent.DatalogParser.ParseDatalog;

public class DatalogStatementTest {

	@Test
	public void testToPostgreSQLStringAllTuplesRemoved() {
        String query = "Q(a) :- A(a,b)";
        String constraint = "N(a) :- A(a,b)";
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
                
        String expected = "SELECT A0.a1 \n" + 
        		"FROM A A0 \n" + 
        		"WHERE 1 = 0\n";
        
        assertEquals(expected, rewritingDS.toPostgreSQLString(TestSchema.getInstance()));
	}

	@Test
	public void testToPostgreSQLStringAllTuplesRemovedLargerQueryHead() {
        String query = "Q(a,b) :- A(a,b)";
        String constraint = "N(a) :- A(a,b)";
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
                
        String expected = "SELECT A0.a1, A0.a2 \n" + 
        		"FROM A A0 \n" + 
        		"WHERE 1 = 0\n";
        
        assertEquals(expected, rewritingDS.toPostgreSQLString(TestSchema.getInstance()));
	}

	@Test
	public void testToPostgreSQLStringAllTuplesRemovedWithJoin() {
        String query = "Q(a,b) :- A(a,b),B(b,c)";
        String constraint = "N(a) :- A(a,b)";
        
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement queryDS = parseDatalog.safeParseString(query);
        DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
        DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
                
        String expected = "SELECT A0.a1, A0.a2 \n" + 
        		"FROM A A0, B B0 \n" + 
        		"WHERE A0.a2 = B0.b1\n" + 
        		"AND 1 = 0\n";
        
        assertEquals(expected, rewritingDS.toPostgreSQLString(TestSchema.getInstance()));
	}
}
