package dbconsent.PostgreSQLParser;

import dbconsent.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.*;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class ParsePostgreSQL extends PostgreSQLParserBaseVisitor<String> {

	private DatalogStatement query = new DatalogStatement("Q");
    //Set of Variables
    private Set<Variable> headVariables = new HashSet<>();

    private Map<String, String> aliases = new HashMap<>();
    //String tableName/Alias to List<Term> -> will become predicates
    private Map<String, Term[]> protoPredicates = new HashMap<>();
    private boolean isSelectStar = false;

	public static void main(String[] args) throws IOException {
	    ParsePostgreSQL visitor = new ParsePostgreSQL();
	    DatalogStatement datalogStatement = visitor.parseString("SELECT * FROM CUSTOMER WHERE C_NAME = 'HELLO THERE'");
        System.out.println("Parsing PostgreSQL query");
        System.out.println("1.) Serialising Parsed Datalog Query");
        System.out.println(datalogStatement.toString());
        System.out.println("2.) Serialising Parsed Datalog Query as PostgreSQL");
        System.out.println(datalogStatement.toPostgreSQLString());
	}

	public DatalogStatement parseFile(String pathToFile) throws IOException {
        CharStream cs = fromFileName(pathToFile);  //load the file
        return parse(cs);
    }

    public DatalogStatement parseString(String postgresQuery) {
        CharStream cs = CharStreams.fromString(postgresQuery);
        return parse(cs);
    }

    public DatalogStatement parse(CharStream charStream){
	    reset();
        PostgreSQLLexer lexer = new PostgreSQLLexer(charStream);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        PostgreSQLParser parser = new PostgreSQLParser(tokens);

        ParseTree tree = parser.select_stmt(); // parse the content and get the tree

        visit(tree);
        return query;
    }

    private void reset() {
        query = new DatalogStatement("Q");
        headVariables = new HashSet<>();
        aliases = new HashMap<>();
        protoPredicates = new HashMap<>();
        isSelectStar = false;
    }


    @Override
    public String visitSelect_stmt(PostgreSQLParser.Select_stmtContext ctx) {

        //We want to build the protopredicates, populate them with variables, then find the variables we are selecting
        visit(ctx.from_clause());
        if(ctx.where_clause() != null){
            visit(ctx.where_clause());
        }
        for (String predicateAlias : protoPredicates.keySet()){
            Term[] predicateArguments = protoPredicates.get(predicateAlias);
            String tableName = aliases.getOrDefault(predicateAlias, predicateAlias);
            Atom atom = new Atom(tableName);
            for (int i = 0; i < predicateArguments.length; i++) {
                Term predicateArgument = predicateArguments[i];
                if (predicateArgument == null) {
                    predicateArguments[i] = new Variable(PoolOfNames.getName());
                }
            }

            atom.addAllTerms(Arrays.asList(predicateArguments));
            query.addAtom(atom);
        }
        visit(ctx.selector_clause());
        return "";
    }

    @Override
    public String visitFrom_clause(PostgreSQLParser.From_clauseContext ctx) {
        for (PostgreSQLParser.From_itemContext fromItem: ctx.from_item()){
            //Create the proto predicate
            String tableName = fromItem.table_name().IDENTIFIER().toString().toUpperCase();
            String alias = null;
            if(fromItem.output_name() != null){
                alias = fromItem.output_name().IDENTIFIER().toString().toUpperCase();
                aliases.put(alias, tableName);
            }
            int predicateSize = TPCHSchema.getInstance().getColumnsForTable(tableName).size();

            //If we're selecting star, we need to add every column to the head
            if(isSelectStar) {
                for(String columnName : TPCHSchema.getInstance().getColumnsForTable(tableName)) {
                    headVariables.add(new Variable(columnName));
                }
            }

            Term[] protoPredicate = new Term[predicateSize];
            String protoIndex = (alias != null) ? alias : tableName;
            protoPredicates.put(protoIndex, protoPredicate);
        }
        return "";
    }

    /* Method to get the Atom (table) that the identifier came from
     */
    private String getTableOfIdentifier(String identifier) {
        //Identifier can be either tablename.columnname or just columname
        String[] parts = identifier.split("\\.");
        if (parts.length == 2) {
            //Identifier was tablename.columname
            return parts[0].toUpperCase();
        } else {
            //Identifier was columname
            String columnName = identifier.toUpperCase();
            for(String table: TPCHSchema.getInstance().getTables()) {
                if (TPCHSchema.getInstance().getColumnsForTable(table).contains(columnName)) {
                    return table;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    /* Method to get column from the identifier
     */
    private String getColumnOfIdentifier(String identifier) {
        //Identifier can be either tablename.columnname or just columname
        String[] parts = identifier.split("\\.");
        if (parts.length == 2) {
            //Identifier was tablename.columname
            return parts[1].toUpperCase();
        } else {
            //Identifier was columname
            return identifier.toUpperCase();
        }
    }

    /* Method to get the index in the table of the column represented by the identifier.
    *  An identifier has the format tablename.columnname or columnname
    * */
    private int getIndexOfIdentifier(String identifier) {
        String table = getTableOfIdentifier(identifier);
        String column = getColumnOfIdentifier(identifier);
        return getIndexOfColumn(table, column);
    }

    private int getIndexOfColumn(String tableName, String columnName) {
        return TPCHSchema.getInstance().getColumnsForTable(aliases.getOrDefault(tableName,tableName).toUpperCase()).indexOf(columnName);
    }

    private Term getPredicateArgument(String identifier) {
        String table = getTableOfIdentifier(identifier);
        Term[] protoPredicate = protoPredicates.get(table.toUpperCase());
        String column = getColumnOfIdentifier(identifier);
        int index = getIndexOfColumn(table, column);
        return protoPredicate[index];
    }

    private void replaceAllOf(Term replace, Term with){
        //For each protopredicate, replace PredicateArguments equalling `replace` with `with`
        for(Term[] predicateArguments : protoPredicates.values()){
            for (int i = 0; i < predicateArguments.length; i++) {
                Term predicateArgument = predicateArguments[i];
                if (predicateArgument.equals(replace)) {
                    predicateArguments[i] = with;
                }
            }
        }
    }

    private void insertPredicateArgument(String identifier, Term predicateArgument) {
        String table = getTableOfIdentifier(identifier);
        Term[] protoPredicate = protoPredicates.get(table.toUpperCase());
        int index = getIndexOfIdentifier(identifier);
        protoPredicate[index] = predicateArgument;
    }

    private void handleEqualityVariables(PostgreSQLParser.PredicateArgumentContext leftCtx, PostgreSQLParser.PredicateArgumentContext rightCtx){
        String left = parseIdentifierContext(leftCtx.identifier());
        Term leftExistingPredicateArgument = getPredicateArgument(left);

        String right = parseIdentifierContext(rightCtx.identifier());
        Term rightExistingPredicateArgument = getPredicateArgument(right);

        //Different cases based on existing relationships in protopredicate
        if(leftExistingPredicateArgument instanceof Variable && rightExistingPredicateArgument instanceof Variable) {
            //We need to ensure that all of our variables point to the same thing (same predicate)
            //in *all* protoPredicates.
            //We'll arbitrarily replace everything on right with the left.
            replaceAllOf(rightExistingPredicateArgument, leftExistingPredicateArgument);
        } else if (leftExistingPredicateArgument instanceof Constant && rightExistingPredicateArgument instanceof Variable) {
            //Replace all right variables with the constant
            replaceAllOf(rightExistingPredicateArgument, leftExistingPredicateArgument);
        } else if (leftExistingPredicateArgument instanceof Variable && rightExistingPredicateArgument instanceof Constant) {
            //Replace all left variables with the constant
            replaceAllOf(leftExistingPredicateArgument, rightExistingPredicateArgument);
        } else if (leftExistingPredicateArgument instanceof Constant && rightExistingPredicateArgument instanceof Constant) {
            //Essentially saying two constants equal each other (not that this makes much sense)
            //You can't represent this in Datalog, unless the two constants are the same, in which case there is nothing to do
            throw new IllegalArgumentException();
        } else if (leftExistingPredicateArgument != null && rightExistingPredicateArgument == null) {
            //We don't know anything about right - add the left thing there
            insertPredicateArgument(right, leftExistingPredicateArgument);
        } else if (leftExistingPredicateArgument == null && rightExistingPredicateArgument != null) {
            //We don't know anything about left - add the right thing there
            insertPredicateArgument(left, rightExistingPredicateArgument);
        } else if (leftExistingPredicateArgument == null && rightExistingPredicateArgument == null) {
            //We don't know anything about either - create a new variable
            Variable variable = new Variable(PoolOfNames.getName());
            insertPredicateArgument(left, variable);
            insertPredicateArgument(right, variable);
        }
    }

    private Constant generateConstant(PostgreSQLParser.PredicateArgumentContext constantCtx) {
        PostgreSQLParser.ValueContext valueCtx = constantCtx.value();
        if (valueCtx.bool_expr() != null){
            if(valueCtx.bool_expr().TRUE() != null) {
                return new BooleanConstant(true);
            } else {
                return new BooleanConstant(false);
            }
        } else if (valueCtx.DOUBLEQ_STRING_LITERAL() != null) {
            return new StringConstant(valueCtx.DOUBLEQ_STRING_LITERAL().toString());
        } else if (valueCtx.SINGLEQ_STRING_LITERAL() != null) {
            return new StringConstant(valueCtx.SINGLEQ_STRING_LITERAL().toString());
        } else if (valueCtx.INTEGER_LITERAL() != null) {
            return new IntegerConstant(Integer.valueOf(valueCtx.INTEGER_LITERAL().toString()));
        } else if (valueCtx.NULL() != null) {
            return new NullConstant();
        }
        return null;
    }

    private void handleEqualityConstant(PostgreSQLParser.PredicateArgumentContext variableCtx, PostgreSQLParser.PredicateArgumentContext constantCtx) {

        String left = parseIdentifierContext(variableCtx.identifier());
        Term leftExistingPredicateArgument = getPredicateArgument(left);

        Constant constant = generateConstant(constantCtx);

        if (leftExistingPredicateArgument instanceof Variable) {
            //The left already has some variable. We now replace that with our constant.
            replaceAllOf(leftExistingPredicateArgument, constant);
        } else if (leftExistingPredicateArgument instanceof Constant) {
            //Essentially saying two constants equal each other (not that this makes much sense)
            //You can't represent this in Datalog, unless the two constants are the same, in which case there is nothing to do
            throw new IllegalArgumentException();
        } else if (leftExistingPredicateArgument == null) {
            insertPredicateArgument(left, constant);
        }
    }

    private String parseIdentifierContext(PostgreSQLParser.IdentifierContext ctx) {
        if(ctx.IDENTIFIER().size() == 2){
            return ctx.IDENTIFIER(0).toString() + "." + ctx.IDENTIFIER(1).toString();
        } else {
            return ctx.IDENTIFIER(0).toString();
        }
    }

    @Override
    public String visitConditionEqual(PostgreSQLParser.ConditionEqualContext ctx) {
        PostgreSQLParser.PredicateArgumentContext leftCtx = ctx.predicateArgument(0);
        PostgreSQLParser.PredicateArgumentContext rightCtx = ctx.predicateArgument(1);

        if (leftCtx.identifier() != null && rightCtx.identifier() != null) {
            //Case where sql reads similar to `WHERE identifier = identifier`
            handleEqualityVariables(leftCtx, rightCtx);
        } else if (leftCtx.identifier() != null && rightCtx.value() != null) {
            //Case where sql reads similar to `WHERE identifier = SOMECONSTANT`
            handleEqualityConstant(leftCtx, rightCtx);
        } else if (leftCtx.value() != null && rightCtx.identifier() != null) {
            //Case where sql reads similar to `WHERE SOMECONSTANT = identifier`
            handleEqualityConstant(rightCtx, leftCtx);
        } else {
            //Case where sql reads similar to `WHERE SOMECONSTANT = SOMECONSTANT`
            //You can't represent this in Datalog, unless the two constants are the same, in which case there is nothing to do
            throw new IllegalArgumentException();
        }

        return "";
    }


    @Override
	public String visitSelector_clause(PostgreSQLParser.Selector_clauseContext ctx) {
		//Get each of the projections from the SELECT ... FROM part
	    List<PostgreSQLParser.Column_selectionContext> column_selectionList = ctx.column_selection();
		for(int i=0; i < column_selectionList.size(); i++) {
			PostgreSQLParser.Column_selectionContext column_selectionContext = column_selectionList.get(i);
			visit(column_selectionContext);
		}
		return "";
	}

	@Override
	public String visitColumn_selection(PostgreSQLParser.Column_selectionContext ctx) {
        if (ctx.identifier() != null) {
            String variableStr = parseIdentifierContext(ctx.identifier());
            Term predicateArgument = getPredicateArgument(variableStr);

            if(!headVariables.contains(predicateArgument) && predicateArgument instanceof Variable){
                Variable projectedVariable = (Variable)predicateArgument;
                headVariables.add(projectedVariable);
                query.addHeadVariable(projectedVariable);
            }
        } else {
            isSelectStar = true;
        }
		return "";
	}

}