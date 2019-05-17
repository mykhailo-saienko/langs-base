// Generated from Expression.g4 by ANTLR 4.6

	package ms.lang.ix.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		BoolConstant=32, Identifier=33, IntConstant=34, StringLiteral=35, Whitespace=36;
	public static final int
		RULE_block = 0, RULE_expression = 1, RULE_memOp = 2, RULE_funcOp = 3, 
		RULE_funcCl = 4, RULE_assOp = 5, RULE_primary = 6, RULE_idOrMethod = 7;
	public static final String[] ruleNames = {
		"block", "expression", "memOp", "funcOp", "funcCl", "assOp", "primary", 
		"idOrMethod"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'++'", "'--'", "'+'", "'-'", "'!'", "'*'", "'/'", "'<='", 
		"'>='", "'>'", "'<'", "'=='", "'!='", "'&'", "'^'", "'|'", "'?'", "':'", 
		"'.'", "'('", "')'", "'='", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", 
		"'^='", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "BoolConstant", "Identifier", 
		"IntConstant", "StringLiteral", "Whitespace"
	};
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
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class BlockContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(16);
					expression(0);
					setState(17);
					match(T__0);
					}
					} 
				}
				setState(23);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(24);
			expression(0);
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(25);
				match(T__0);
				}
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

	public static class ExpressionContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssOpContext assOp() {
			return getRuleContext(AssOpContext.class,0);
		}
		public MemOpContext memOp() {
			return getRuleContext(MemOpContext.class,0);
		}
		public IdOrMethodContext idOrMethod() {
			return getRuleContext(IdOrMethodContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__20:
			case BoolConstant:
			case Identifier:
			case IntConstant:
			case StringLiteral:
				{
				setState(29);
				primary();
				}
				break;
			case T__1:
			case T__2:
			case T__3:
			case T__4:
				{
				setState(30);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(31);
				expression(11);
				}
				break;
			case T__5:
				{
				setState(32);
				match(T__5);
				setState(33);
				expression(10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(73);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(71);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(36);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(37);
						_la = _input.LA(1);
						if ( !(_la==T__6 || _la==T__7) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(38);
						expression(10);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(39);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(40);
						_la = _input.LA(1);
						if ( !(_la==T__3 || _la==T__4) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(41);
						expression(9);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(42);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(43);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(44);
						expression(8);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(45);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(46);
						_la = _input.LA(1);
						if ( !(_la==T__12 || _la==T__13) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(47);
						expression(7);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(48);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(49);
						match(T__14);
						setState(50);
						expression(6);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(51);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(52);
						match(T__15);
						setState(53);
						expression(5);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(54);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(55);
						match(T__16);
						setState(56);
						expression(4);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(57);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(58);
						match(T__17);
						setState(59);
						expression(0);
						setState(60);
						match(T__18);
						setState(61);
						expression(3);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(63);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(64);
						assOp();
						setState(65);
						expression(1);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(67);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(68);
						memOp();
						setState(69);
						idOrMethod();
						}
						break;
					}
					} 
				}
				setState(75);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class MemOpContext extends ParserRuleContext {
		public MemOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterMemOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitMemOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitMemOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemOpContext memOp() throws RecognitionException {
		MemOpContext _localctx = new MemOpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_memOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(T__19);
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

	public static class FuncOpContext extends ParserRuleContext {
		public FuncOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterFuncOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitFuncOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitFuncOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncOpContext funcOp() throws RecognitionException {
		FuncOpContext _localctx = new FuncOpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_funcOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__20);
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

	public static class FuncClContext extends ParserRuleContext {
		public FuncClContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterFuncCl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitFuncCl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitFuncCl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncClContext funcCl() throws RecognitionException {
		FuncClContext _localctx = new FuncClContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_funcCl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__21);
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

	public static class AssOpContext extends ParserRuleContext {
		public AssOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterAssOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitAssOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitAssOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssOpContext assOp() throws RecognitionException {
		AssOpContext _localctx = new AssOpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29))) != 0)) ) {
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

	public static class PrimaryContext extends ParserRuleContext {
		public IdOrMethodContext idOrMethod() {
			return getRuleContext(IdOrMethodContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode BoolConstant() { return getToken(ExpressionParser.BoolConstant, 0); }
		public TerminalNode IntConstant() { return getToken(ExpressionParser.IntConstant, 0); }
		public List<TerminalNode> StringLiteral() { return getTokens(ExpressionParser.StringLiteral); }
		public TerminalNode StringLiteral(int i) {
			return getToken(ExpressionParser.StringLiteral, i);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primary);
		try {
			int _alt;
			setState(96);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				idOrMethod();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(T__20);
				setState(86);
				expression(0);
				setState(87);
				match(T__21);
				}
				break;
			case BoolConstant:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				match(BoolConstant);
				}
				break;
			case IntConstant:
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				match(IntConstant);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(92); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(91);
						match(StringLiteral);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(94); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class IdOrMethodContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ExpressionParser.Identifier, 0); }
		public FuncOpContext funcOp() {
			return getRuleContext(FuncOpContext.class,0);
		}
		public FuncClContext funcCl() {
			return getRuleContext(FuncClContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IdOrMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOrMethod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterIdOrMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitIdOrMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitIdOrMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdOrMethodContext idOrMethod() throws RecognitionException {
		IdOrMethodContext _localctx = new IdOrMethodContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_idOrMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(Identifier);
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(99);
				funcOp();
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__20) | (1L << BoolConstant) | (1L << Identifier) | (1L << IntConstant) | (1L << StringLiteral))) != 0)) {
					{
					setState(100);
					expression(0);
					setState(105);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__30) {
						{
						{
						setState(101);
						match(T__30);
						setState(102);
						expression(0);
						}
						}
						setState(107);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(110);
				funcCl();
				}
				break;
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 8);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		case 8:
			return precpred(_ctx, 1);
		case 9:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3&u\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\7\2\26\n"+
		"\2\f\2\16\2\31\13\2\3\2\3\2\5\2\35\n\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3%\n"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\7\3J\n\3\f\3\16\3M\13\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\6\b_\n\b\r\b\16\b`\5\bc\n\b\3\t\3\t\3\t\3\t\3\t\7"+
		"\tj\n\t\f\t\16\tm\13\t\5\to\n\t\3\t\3\t\5\ts\n\t\3\t\2\3\4\n\2\4\6\b\n"+
		"\f\16\20\2\b\3\2\4\7\3\2\t\n\3\2\6\7\3\2\13\16\3\2\17\20\3\2\31 \u0082"+
		"\2\27\3\2\2\2\4$\3\2\2\2\6N\3\2\2\2\bP\3\2\2\2\nR\3\2\2\2\fT\3\2\2\2\16"+
		"b\3\2\2\2\20d\3\2\2\2\22\23\5\4\3\2\23\24\7\3\2\2\24\26\3\2\2\2\25\22"+
		"\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\32\3\2\2\2\31\27"+
		"\3\2\2\2\32\34\5\4\3\2\33\35\7\3\2\2\34\33\3\2\2\2\34\35\3\2\2\2\35\3"+
		"\3\2\2\2\36\37\b\3\1\2\37%\5\16\b\2 !\t\2\2\2!%\5\4\3\r\"#\7\b\2\2#%\5"+
		"\4\3\f$\36\3\2\2\2$ \3\2\2\2$\"\3\2\2\2%K\3\2\2\2&\'\f\13\2\2\'(\t\3\2"+
		"\2(J\5\4\3\f)*\f\n\2\2*+\t\4\2\2+J\5\4\3\13,-\f\t\2\2-.\t\5\2\2.J\5\4"+
		"\3\n/\60\f\b\2\2\60\61\t\6\2\2\61J\5\4\3\t\62\63\f\7\2\2\63\64\7\21\2"+
		"\2\64J\5\4\3\b\65\66\f\6\2\2\66\67\7\22\2\2\67J\5\4\3\789\f\5\2\29:\7"+
		"\23\2\2:J\5\4\3\6;<\f\4\2\2<=\7\24\2\2=>\5\4\3\2>?\7\25\2\2?@\5\4\3\5"+
		"@J\3\2\2\2AB\f\3\2\2BC\5\f\7\2CD\5\4\3\3DJ\3\2\2\2EF\f\16\2\2FG\5\6\4"+
		"\2GH\5\20\t\2HJ\3\2\2\2I&\3\2\2\2I)\3\2\2\2I,\3\2\2\2I/\3\2\2\2I\62\3"+
		"\2\2\2I\65\3\2\2\2I8\3\2\2\2I;\3\2\2\2IA\3\2\2\2IE\3\2\2\2JM\3\2\2\2K"+
		"I\3\2\2\2KL\3\2\2\2L\5\3\2\2\2MK\3\2\2\2NO\7\26\2\2O\7\3\2\2\2PQ\7\27"+
		"\2\2Q\t\3\2\2\2RS\7\30\2\2S\13\3\2\2\2TU\t\7\2\2U\r\3\2\2\2Vc\5\20\t\2"+
		"WX\7\27\2\2XY\5\4\3\2YZ\7\30\2\2Zc\3\2\2\2[c\7\"\2\2\\c\7$\2\2]_\7%\2"+
		"\2^]\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2bV\3\2\2\2bW\3\2\2"+
		"\2b[\3\2\2\2b\\\3\2\2\2b^\3\2\2\2c\17\3\2\2\2dr\7#\2\2en\5\b\5\2fk\5\4"+
		"\3\2gh\7!\2\2hj\5\4\3\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2lo\3\2"+
		"\2\2mk\3\2\2\2nf\3\2\2\2no\3\2\2\2op\3\2\2\2pq\5\n\6\2qs\3\2\2\2re\3\2"+
		"\2\2rs\3\2\2\2s\21\3\2\2\2\f\27\34$IK`bknr";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}