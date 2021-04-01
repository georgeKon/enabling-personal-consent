// Generated from /home/ross/git/consent/code/dbconsent/src/dbconsent/DatalogParser/Datalog.g4 by ANTLR 4.7.2
package dbconsent.DatalogParser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DatalogParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, BLOCK_COMMENT=2, LINE_COMMENT=3, LPAREN=4, RPAREN=5, COMMA=6, 
		PERIOD=7, COLONDASH=8, PREDICATE_NAME=9, VARIABLE=10, STRING_CONST=11, 
		BOOLEAN_CONST=12, INTEGER_CONST=13, FLOAT_CONST=14, NULL_CONST=15;
	public static final int
		RULE_query = 0, RULE_head = 1, RULE_atom = 2, RULE_predicateArgument = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "head", "atom", "predicateArgument"
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

	@Override
	public String getGrammarFileName() { return "Datalog.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DatalogParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class QueryContext extends ParserRuleContext {
		public HeadContext head() {
			return getRuleContext(HeadContext.class,0);
		}
		public TerminalNode COLONDASH() { return getToken(DatalogParser.COLONDASH, 0); }
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DatalogParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DatalogParser.COMMA, i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			head();
			setState(9);
			match(COLONDASH);
			setState(10);
			atom();
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(11);
				match(COMMA);
				setState(12);
				atom();
				}
				}
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeadContext extends ParserRuleContext {
		public TerminalNode PREDICATE_NAME() { return getToken(DatalogParser.PREDICATE_NAME, 0); }
		public TerminalNode LPAREN() { return getToken(DatalogParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DatalogParser.RPAREN, 0); }
		public List<TerminalNode> VARIABLE() { return getTokens(DatalogParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(DatalogParser.VARIABLE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DatalogParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DatalogParser.COMMA, i);
		}
		public HeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_head; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).enterHead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).exitHead(this);
		}
	}

	public final HeadContext head() throws RecognitionException {
		HeadContext _localctx = new HeadContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_head);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(PREDICATE_NAME);
			setState(19);
			match(LPAREN);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VARIABLE) {
				{
				setState(20);
				match(VARIABLE);
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(21);
					match(COMMA);
					setState(22);
					match(VARIABLE);
					}
					}
					setState(27);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(30);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode PREDICATE_NAME() { return getToken(DatalogParser.PREDICATE_NAME, 0); }
		public TerminalNode LPAREN() { return getToken(DatalogParser.LPAREN, 0); }
		public List<PredicateArgumentContext> predicateArgument() {
			return getRuleContexts(PredicateArgumentContext.class);
		}
		public PredicateArgumentContext predicateArgument(int i) {
			return getRuleContext(PredicateArgumentContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DatalogParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DatalogParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DatalogParser.COMMA, i);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_atom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(PREDICATE_NAME);
			setState(33);
			match(LPAREN);
			setState(34);
			predicateArgument();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(35);
				match(COMMA);
				setState(36);
				predicateArgument();
				}
				}
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(42);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateArgumentContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(DatalogParser.VARIABLE, 0); }
		public TerminalNode INTEGER_CONST() { return getToken(DatalogParser.INTEGER_CONST, 0); }
		public TerminalNode NULL_CONST() { return getToken(DatalogParser.NULL_CONST, 0); }
		public TerminalNode BOOLEAN_CONST() { return getToken(DatalogParser.BOOLEAN_CONST, 0); }
		public TerminalNode FLOAT_CONST() { return getToken(DatalogParser.FLOAT_CONST, 0); }
		public TerminalNode STRING_CONST() { return getToken(DatalogParser.STRING_CONST, 0); }
		public PredicateArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).enterPredicateArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogListener ) ((DatalogListener)listener).exitPredicateArgument(this);
		}
	}

	public final PredicateArgumentContext predicateArgument() throws RecognitionException {
		PredicateArgumentContext _localctx = new PredicateArgumentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_predicateArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VARIABLE) | (1L << STRING_CONST) | (1L << BOOLEAN_CONST) | (1L << INTEGER_CONST) | (1L << FLOAT_CONST) | (1L << NULL_CONST))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21\61\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3"+
		"\3\3\3\3\3\3\3\3\3\7\3\32\n\3\f\3\16\3\35\13\3\5\3\37\n\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\3\4\7\4(\n\4\f\4\16\4+\13\4\3\4\3\4\3\5\3\5\3\5\2\2\6\2\4"+
		"\6\b\2\3\3\2\f\21\2\60\2\n\3\2\2\2\4\24\3\2\2\2\6\"\3\2\2\2\b.\3\2\2\2"+
		"\n\13\5\4\3\2\13\f\7\n\2\2\f\21\5\6\4\2\r\16\7\b\2\2\16\20\5\6\4\2\17"+
		"\r\3\2\2\2\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\3\3\2\2\2\23\21"+
		"\3\2\2\2\24\25\7\13\2\2\25\36\7\6\2\2\26\33\7\f\2\2\27\30\7\b\2\2\30\32"+
		"\7\f\2\2\31\27\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\37"+
		"\3\2\2\2\35\33\3\2\2\2\36\26\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 !\7\7\2"+
		"\2!\5\3\2\2\2\"#\7\13\2\2#$\7\6\2\2$)\5\b\5\2%&\7\b\2\2&(\5\b\5\2\'%\3"+
		"\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*,\3\2\2\2+)\3\2\2\2,-\7\7\2\2-\7"+
		"\3\2\2\2./\t\2\2\2/\t\3\2\2\2\6\21\33\36)";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}