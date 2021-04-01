// Generated from /home/ross/git/consent/code/dbconsent/src/dbconsent/DatalogParser/Datalog.g4 by ANTLR 4.7.2
package dbconsent.DatalogParser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DatalogLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, BLOCK_COMMENT=2, LINE_COMMENT=3, LPAREN=4, RPAREN=5, COMMA=6, 
		PERIOD=7, COLONDASH=8, PREDICATE_NAME=9, VARIABLE=10, STRING_CONST=11, 
		BOOLEAN_CONST=12, INTEGER_CONST=13, FLOAT_CONST=14, NULL_CONST=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "BLOCK_COMMENT", "LINE_COMMENT", "LPAREN", "RPAREN", "COMMA", 
			"PERIOD", "COLONDASH", "UNDERSCORE", "PREDICATE_NAME", "VARIABLE", "STRING_CONST", 
			"BOOLEAN_CONST", "INTEGER_CONST", "FLOAT_CONST", "TRUE_CONST", "FALSE_CONST", 
			"NULL_CONST", "LOWERCASE", "UPPERCASE", "DIGIT", "SQUOTE", "DQUOTE", 
			"SYMBOL"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'('", "')'", "','", "'.'", "':-'", null, null, 
			null, null, null, null, "'Null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "BLOCK_COMMENT", "LINE_COMMENT", "LPAREN", "RPAREN", 
			"COMMA", "PERIOD", "COLONDASH", "PREDICATE_NAME", "VARIABLE", "STRING_CONST", 
			"BOOLEAN_CONST", "INTEGER_CONST", "FLOAT_CONST", "NULL_CONST"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DatalogLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Datalog.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u00c2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\6\2\65\n\2\r\2\16\2\66\3\2\3\2\3\3\3\3\3\3\3\3\7\3?\n\3\f\3"+
		"\16\3B\13\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4M\n\4\f\4\16\4P\13"+
		"\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\7\13g\n\13\f\13\16\13j\13\13\3\f\3\f\5\fn\n\f\3\f"+
		"\3\f\3\f\3\f\7\ft\n\f\f\f\16\fw\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\177\n"+
		"\r\f\r\16\r\u0082\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u008b\n\r\f\r\16"+
		"\r\u008e\13\r\3\r\5\r\u0091\n\r\3\16\3\16\5\16\u0095\n\16\3\17\6\17\u0098"+
		"\n\17\r\17\16\17\u0099\3\20\6\20\u009d\n\20\r\20\16\20\u009e\3\20\3\20"+
		"\6\20\u00a3\n\20\r\20\16\20\u00a4\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\27\3\27\3\30\3\30\3\31\3\31\4@N\2\32\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\2\25\13\27\f\31\r\33\16\35\17\37\20!\2#\2%\21\'\2)\2+\2-\2"+
		"/\2\61\2\3\2\t\5\2\13\f\17\17\"\"\4\2$$^^\4\2))^^\3\2c|\3\2C\\\3\2\62"+
		";\7\2#\61<B]b}\u0080\u00a5\u00a5\2\u00ce\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2%\3\2\2\2\3\64\3\2\2\2\5:\3\2\2\2\7H\3\2\2\2\tU\3\2\2\2\13W"+
		"\3\2\2\2\rY\3\2\2\2\17[\3\2\2\2\21]\3\2\2\2\23`\3\2\2\2\25b\3\2\2\2\27"+
		"m\3\2\2\2\31\u0090\3\2\2\2\33\u0094\3\2\2\2\35\u0097\3\2\2\2\37\u009c"+
		"\3\2\2\2!\u00a6\3\2\2\2#\u00ab\3\2\2\2%\u00b1\3\2\2\2\'\u00b6\3\2\2\2"+
		")\u00b8\3\2\2\2+\u00ba\3\2\2\2-\u00bc\3\2\2\2/\u00be\3\2\2\2\61\u00c0"+
		"\3\2\2\2\63\65\t\2\2\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67"+
		"\3\2\2\2\678\3\2\2\289\b\2\2\29\4\3\2\2\2:;\7\61\2\2;<\7,\2\2<@\3\2\2"+
		"\2=?\13\2\2\2>=\3\2\2\2?B\3\2\2\2@A\3\2\2\2@>\3\2\2\2AC\3\2\2\2B@\3\2"+
		"\2\2CD\7,\2\2DE\7\61\2\2EF\3\2\2\2FG\b\3\2\2G\6\3\2\2\2HI\7/\2\2IJ\7/"+
		"\2\2JN\3\2\2\2KM\13\2\2\2LK\3\2\2\2MP\3\2\2\2NO\3\2\2\2NL\3\2\2\2OQ\3"+
		"\2\2\2PN\3\2\2\2QR\7\f\2\2RS\3\2\2\2ST\b\4\2\2T\b\3\2\2\2UV\7*\2\2V\n"+
		"\3\2\2\2WX\7+\2\2X\f\3\2\2\2YZ\7.\2\2Z\16\3\2\2\2[\\\7\60\2\2\\\20\3\2"+
		"\2\2]^\7<\2\2^_\7/\2\2_\22\3\2\2\2`a\7a\2\2a\24\3\2\2\2bh\5)\25\2cg\5"+
		")\25\2dg\5+\26\2eg\7)\2\2fc\3\2\2\2fd\3\2\2\2fe\3\2\2\2gj\3\2\2\2hf\3"+
		"\2\2\2hi\3\2\2\2i\26\3\2\2\2jh\3\2\2\2kn\5)\25\2ln\5\'\24\2mk\3\2\2\2"+
		"ml\3\2\2\2nu\3\2\2\2ot\5)\25\2pt\5\'\24\2qt\5+\26\2rt\5\23\n\2so\3\2\2"+
		"\2sp\3\2\2\2sq\3\2\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2v\30\3\2"+
		"\2\2wu\3\2\2\2x\u0080\7$\2\2yz\7^\2\2z\177\13\2\2\2{|\7$\2\2|\177\7$\2"+
		"\2}\177\n\3\2\2~y\3\2\2\2~{\3\2\2\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080"+
		"~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2"+
		"\u0083\u0091\7$\2\2\u0084\u008c\7)\2\2\u0085\u0086\7^\2\2\u0086\u008b"+
		"\13\2\2\2\u0087\u0088\7)\2\2\u0088\u008b\7)\2\2\u0089\u008b\n\4\2\2\u008a"+
		"\u0085\3\2\2\2\u008a\u0087\3\2\2\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2"+
		"\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008f\u0091\7)\2\2\u0090x\3\2\2\2\u0090\u0084\3\2\2\2\u0091"+
		"\32\3\2\2\2\u0092\u0095\5!\21\2\u0093\u0095\5#\22\2\u0094\u0092\3\2\2"+
		"\2\u0094\u0093\3\2\2\2\u0095\34\3\2\2\2\u0096\u0098\5+\26\2\u0097\u0096"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\36\3\2\2\2\u009b\u009d\5+\26\2\u009c\u009b\3\2\2\2\u009d\u009e\3\2\2"+
		"\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2"+
		"\5\17\b\2\u00a1\u00a3\5+\26\2\u00a2\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2"+
		"\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5 \3\2\2\2\u00a6\u00a7\7"+
		"V\2\2\u00a7\u00a8\7t\2\2\u00a8\u00a9\7w\2\2\u00a9\u00aa\7g\2\2\u00aa\""+
		"\3\2\2\2\u00ab\u00ac\7H\2\2\u00ac\u00ad\7c\2\2\u00ad\u00ae\7n\2\2\u00ae"+
		"\u00af\7u\2\2\u00af\u00b0\7g\2\2\u00b0$\3\2\2\2\u00b1\u00b2\7P\2\2\u00b2"+
		"\u00b3\7w\2\2\u00b3\u00b4\7n\2\2\u00b4\u00b5\7n\2\2\u00b5&\3\2\2\2\u00b6"+
		"\u00b7\t\5\2\2\u00b7(\3\2\2\2\u00b8\u00b9\t\6\2\2\u00b9*\3\2\2\2\u00ba"+
		"\u00bb\t\7\2\2\u00bb,\3\2\2\2\u00bc\u00bd\7)\2\2\u00bd.\3\2\2\2\u00be"+
		"\u00bf\7$\2\2\u00bf\60\3\2\2\2\u00c0\u00c1\t\b\2\2\u00c1\62\3\2\2\2\24"+
		"\2\66@Nfhmsu~\u0080\u008a\u008c\u0090\u0094\u0099\u009e\u00a4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}