package dbconsent.PostgreSQLParser;// Generated from PostgreSQLParser.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PostgreSQLParser}.
 */
public interface PostgreSQLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSelect_stmt(PostgreSQLParser.Select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSelect_stmt(PostgreSQLParser.Select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#selector_clause}.
	 * @param ctx the parse tree
	 */
	void enterSelector_clause(PostgreSQLParser.Selector_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#selector_clause}.
	 * @param ctx the parse tree
	 */
	void exitSelector_clause(PostgreSQLParser.Selector_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#column_selection}.
	 * @param ctx the parse tree
	 */
	void enterColumn_selection(PostgreSQLParser.Column_selectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#column_selection}.
	 * @param ctx the parse tree
	 */
	void exitColumn_selection(PostgreSQLParser.Column_selectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void enterFrom_clause(PostgreSQLParser.From_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void exitFrom_clause(PostgreSQLParser.From_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void enterWhere_clause(PostgreSQLParser.Where_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void exitWhere_clause(PostgreSQLParser.Where_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#combine_clause}.
	 * @param ctx the parse tree
	 */
	void enterCombine_clause(PostgreSQLParser.Combine_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#combine_clause}.
	 * @param ctx the parse tree
	 */
	void exitCombine_clause(PostgreSQLParser.Combine_clauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateNOT}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicateNOT(PostgreSQLParser.PredicateNOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateNOT}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicateNOT(PostgreSQLParser.PredicateNOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateConditional}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicateConditional(PostgreSQLParser.PredicateConditionalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateConditional}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicateConditional(PostgreSQLParser.PredicateConditionalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateAND}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicateAND(PostgreSQLParser.PredicateANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateAND}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicateAND(PostgreSQLParser.PredicateANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateParen}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicateParen(PostgreSQLParser.PredicateParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateParen}
	 * labeled alternative in {@link PostgreSQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicateParen(PostgreSQLParser.PredicateParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterConditionEqual(PostgreSQLParser.ConditionEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitConditionEqual(PostgreSQLParser.ConditionEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionNotEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterConditionNotEqual(PostgreSQLParser.ConditionNotEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionNotEqual}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitConditionNotEqual(PostgreSQLParser.ConditionNotEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionComparison}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterConditionComparison(PostgreSQLParser.ConditionComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionComparison}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitConditionComparison(PostgreSQLParser.ConditionComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionIsBooleanOrNull}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterConditionIsBooleanOrNull(PostgreSQLParser.ConditionIsBooleanOrNullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionIsBooleanOrNull}
	 * labeled alternative in {@link PostgreSQLParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitConditionIsBooleanOrNull(PostgreSQLParser.ConditionIsBooleanOrNullContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#predicateArgument}.
	 * @param ctx the parse tree
	 */
	void enterPredicateArgument(PostgreSQLParser.PredicateArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#predicateArgument}.
	 * @param ctx the parse tree
	 */
	void exitPredicateArgument(PostgreSQLParser.PredicateArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(PostgreSQLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(PostgreSQLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#value_list}.
	 * @param ctx the parse tree
	 */
	void enterValue_list(PostgreSQLParser.Value_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#value_list}.
	 * @param ctx the parse tree
	 */
	void exitValue_list(PostgreSQLParser.Value_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#bool_expr}.
	 * @param ctx the parse tree
	 */
	void enterBool_expr(PostgreSQLParser.Bool_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#bool_expr}.
	 * @param ctx the parse tree
	 */
	void exitBool_expr(PostgreSQLParser.Bool_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#type_literal}.
	 * @param ctx the parse tree
	 */
	void enterType_literal(PostgreSQLParser.Type_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#type_literal}.
	 * @param ctx the parse tree
	 */
	void exitType_literal(PostgreSQLParser.Type_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#output_name}.
	 * @param ctx the parse tree
	 */
	void enterOutput_name(PostgreSQLParser.Output_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#output_name}.
	 * @param ctx the parse tree
	 */
	void exitOutput_name(PostgreSQLParser.Output_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(PostgreSQLParser.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(PostgreSQLParser.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#from_item}.
	 * @param ctx the parse tree
	 */
	void enterFrom_item(PostgreSQLParser.From_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#from_item}.
	 * @param ctx the parse tree
	 */
	void exitFrom_item(PostgreSQLParser.From_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#join_type}.
	 * @param ctx the parse tree
	 */
	void enterJoin_type(PostgreSQLParser.Join_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#join_type}.
	 * @param ctx the parse tree
	 */
	void exitJoin_type(PostgreSQLParser.Join_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void enterJoin_clause(PostgreSQLParser.Join_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void exitJoin_clause(PostgreSQLParser.Join_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(PostgreSQLParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(PostgreSQLParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostgreSQLParser#column_name}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name(PostgreSQLParser.Column_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostgreSQLParser#column_name}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name(PostgreSQLParser.Column_nameContext ctx);
}