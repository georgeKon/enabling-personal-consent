package dbconsent.PostgreSQLParser;// Generated from PostgreSQLParser.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PostgreSQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PostgreSQLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#select_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_stmt(PostgreSQLParser.Select_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#selector_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector_clause(PostgreSQLParser.Selector_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#column_selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_selection(PostgreSQLParser.Column_selectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#from_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_clause(PostgreSQLParser.From_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#where_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhere_clause(PostgreSQLParser.Where_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#combine_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombine_clause(PostgreSQLParser.Combine_clauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateNOT}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateNOT(PostgreSQLParser.PredicateNOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateConditional}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateConditional(PostgreSQLParser.PredicateConditionalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateAND}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateAND(PostgreSQLParser.PredicateANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateParen}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateParen(PostgreSQLParser.PredicateParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionEqual(PostgreSQLParser.ConditionEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionNotEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionNotEqual(PostgreSQLParser.ConditionNotEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionComparison}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionComparison(PostgreSQLParser.ConditionComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionIsBooleanOrNull}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionIsBooleanOrNull(PostgreSQLParser.ConditionIsBooleanOrNullContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#predicateArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateArgument(PostgreSQLParser.PredicateArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(PostgreSQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#value_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue_list(PostgreSQLParser.Value_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#bool_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_expr(PostgreSQLParser.Bool_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#type_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_literal(PostgreSQLParser.Type_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#output_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput_name(PostgreSQLParser.Output_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#table_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_name(PostgreSQLParser.Table_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#from_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_item(PostgreSQLParser.From_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#join_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_type(PostgreSQLParser.Join_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#join_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_clause(PostgreSQLParser.Join_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(PostgreSQLParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostgreSQLParser#column_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_name(PostgreSQLParser.Column_nameContext ctx);
}