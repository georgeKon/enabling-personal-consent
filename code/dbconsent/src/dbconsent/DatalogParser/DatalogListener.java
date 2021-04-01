// Generated from /home/ross/git/consent/code/dbconsent/src/dbconsent/DatalogParser/Datalog.g4 by ANTLR 4.7.2
package dbconsent.DatalogParser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DatalogParser}.
 */
public interface DatalogListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DatalogParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(DatalogParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(DatalogParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#head}.
	 * @param ctx the parse tree
	 */
	void enterHead(DatalogParser.HeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#head}.
	 * @param ctx the parse tree
	 */
	void exitHead(DatalogParser.HeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(DatalogParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(DatalogParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#predicateArgument}.
	 * @param ctx the parse tree
	 */
	void enterPredicateArgument(DatalogParser.PredicateArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#predicateArgument}.
	 * @param ctx the parse tree
	 */
	void exitPredicateArgument(DatalogParser.PredicateArgumentContext ctx);
}