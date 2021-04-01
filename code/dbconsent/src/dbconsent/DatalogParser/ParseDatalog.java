package dbconsent.DatalogParser;

import dbconsent.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class ParseDatalog extends DatalogBaseListener{

    public static void main(String[] args) {
        ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement query = parseDatalog.safeParseString("Q(x,y,z,w) :- CUSTOMER(x,y,z,'HELLO WORLD',w,b,c,x)");
        System.out.println(query.toString());
    }

    private DatalogStatement query;
    private Atom currentAtom;

    public DatalogStatement safeParseString(String query) {
        DatalogStatement result = parseString(query);
        try {
            PoolOfNames.safeNamespace(result);
        } catch (HeadVariableNotInQueryException e) {
            // This should never be thrown, as this error will be detected by the parser.
            e.printStackTrace();
        }
        return result;
    }

    public DatalogStatement parseString(String query) {
        CharStream cs = CharStreams.fromString(query);
        return parse(cs);
    }

    private DatalogStatement parse(CharStream cs) {
        DatalogLexer lexer = new DatalogLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        DatalogParser parser = new DatalogParser(tokens);

        ParseTree tree = parser.query(); // parse the content and get the tree
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this,tree);
        return query;
    }

    @Override public void enterHead(DatalogParser.HeadContext ctx) {
        query = new DatalogStatement(ctx.PREDICATE_NAME().getText());

        for(TerminalNode distinguishedVariable : ctx.VARIABLE()){
            Variable variable = new Variable(distinguishedVariable.getText());
            query.addHeadVariable(variable);
        }
    }

    @Override public void enterAtom(DatalogParser.AtomContext ctx) {
        currentAtom = new Atom(ctx.PREDICATE_NAME().getText());
        query.addAtom(currentAtom);
    }

    @Override public void enterPredicateArgument(DatalogParser.PredicateArgumentContext ctx) {
        Term predicateArgument = null;
        if(ctx.VARIABLE() != null) {
            predicateArgument = new Variable(ctx.VARIABLE().getText());
        } else if (ctx.INTEGER_CONST() != null) {
            Integer value = Integer.parseInt(ctx.INTEGER_CONST().getText());
            predicateArgument = new IntegerConstant(value);
        } else if (ctx.FLOAT_CONST() != null) {
            Double value = Double.parseDouble(ctx.FLOAT_CONST().getText());
            predicateArgument = new FloatConstant(value);
        } else if (ctx.BOOLEAN_CONST() != null) {
            Boolean value = Boolean.parseBoolean(ctx.BOOLEAN_CONST().getText());
            predicateArgument = new BooleanConstant(value);
        } else if (ctx.STRING_CONST() != null) {
            predicateArgument = new StringConstant(ctx.STRING_CONST().getText());
        } else if (ctx.NULL_CONST() != null) {
            predicateArgument = new NullConstant();
        }
        currentAtom.addTerm(predicateArgument);
    }

}
