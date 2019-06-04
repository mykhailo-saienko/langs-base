// Generated from UIConfigParser.g4 by ANTLR 4.6

	package ms.gui.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UIConfigParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, CDATA=2, DTD=3, SEA_WS=4, OPEN=5, XMLDeclOpen=6, TEXT=7, CLOSE=8, 
		SPECIAL_CLOSE=9, SLASH_CLOSE=10, SLASH=11, EQUALS=12, STRING=13, Name=14, 
		S=15, PI=16;
	public static final int
		RULE_document = 0, RULE_prolog = 1, RULE_content = 2, RULE_element = 3, 
		RULE_attribute = 4, RULE_chardata = 5, RULE_misc = 6;
	public static final String[] ruleNames = {
		"document", "prolog", "content", "element", "attribute", "chardata", "misc"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, "'<'", null, null, "'>'", null, "'/>'", 
		"'/'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "COMMENT", "CDATA", "DTD", "SEA_WS", "OPEN", "XMLDeclOpen", "TEXT", 
		"CLOSE", "SPECIAL_CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", "STRING", 
		"Name", "S", "PI"
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
	public String getGrammarFileName() { return "UIConfigParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public UIConfigParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DocumentContext extends ParserRuleContext {
		public PrologContext prolog() {
			return getRuleContext(PrologContext.class,0);
		}
		public List<MiscContext> misc() {
			return getRuleContexts(MiscContext.class);
		}
		public MiscContext misc(int i) {
			return getRuleContext(MiscContext.class,i);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitDocument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_document);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==XMLDeclOpen) {
				{
				setState(14);
				prolog();
				}
			}

			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << SEA_WS) | (1L << OPEN) | (1L << PI))) != 0)) {
				{
				setState(19);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case COMMENT:
				case SEA_WS:
				case PI:
					{
					setState(17);
					misc();
					}
					break;
				case OPEN:
					{
					setState(18);
					element();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(23);
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

	public static class PrologContext extends ParserRuleContext {
		public TerminalNode XMLDeclOpen() { return getToken(UIConfigParser.XMLDeclOpen, 0); }
		public TerminalNode SPECIAL_CLOSE() { return getToken(UIConfigParser.SPECIAL_CLOSE, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public PrologContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prolog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterProlog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitProlog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitProlog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrologContext prolog() throws RecognitionException {
		PrologContext _localctx = new PrologContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prolog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(XMLDeclOpen);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(25);
				attribute();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(SPECIAL_CLOSE);
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

	public static class ContentContext extends ParserRuleContext {
		public List<ChardataContext> chardata() {
			return getRuleContexts(ChardataContext.class);
		}
		public ChardataContext chardata(int i) {
			return getRuleContext(ChardataContext.class,i);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public List<TerminalNode> CDATA() { return getTokens(UIConfigParser.CDATA); }
		public TerminalNode CDATA(int i) {
			return getToken(UIConfigParser.CDATA, i);
		}
		public List<TerminalNode> PI() { return getTokens(UIConfigParser.PI); }
		public TerminalNode PI(int i) {
			return getToken(UIConfigParser.PI, i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(UIConfigParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(UIConfigParser.COMMENT, i);
		}
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_content);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEA_WS || _la==TEXT) {
				{
				setState(33);
				chardata();
				}
			}

			setState(47);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(40);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case OPEN:
						{
						setState(36);
						element();
						}
						break;
					case CDATA:
						{
						setState(37);
						match(CDATA);
						}
						break;
					case PI:
						{
						setState(38);
						match(PI);
						}
						break;
					case COMMENT:
						{
						setState(39);
						match(COMMENT);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(43);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEA_WS || _la==TEXT) {
						{
						setState(42);
						chardata();
						}
					}

					}
					} 
				}
				setState(49);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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

	public static class ElementContext extends ParserRuleContext {
		public List<TerminalNode> Name() { return getTokens(UIConfigParser.Name); }
		public TerminalNode Name(int i) {
			return getToken(UIConfigParser.Name, i);
		}
		public ContentContext content() {
			return getRuleContext(ContentContext.class,0);
		}
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_element);
		int _la;
		try {
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				match(OPEN);
				setState(51);
				match(Name);
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(52);
					attribute();
					}
					}
					setState(57);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(58);
				match(CLOSE);
				setState(59);
				content();
				setState(60);
				match(OPEN);
				setState(61);
				match(SLASH);
				setState(62);
				match(Name);
				setState(63);
				match(CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				match(OPEN);
				setState(66);
				match(Name);
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(67);
					attribute();
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73);
				match(SLASH_CLOSE);
				}
				break;
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(UIConfigParser.Name, 0); }
		public TerminalNode EQUALS() { return getToken(UIConfigParser.EQUALS, 0); }
		public TerminalNode STRING() { return getToken(UIConfigParser.STRING, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(Name);
			setState(77);
			match(EQUALS);
			setState(78);
			match(STRING);
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

	public static class ChardataContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(UIConfigParser.TEXT, 0); }
		public TerminalNode SEA_WS() { return getToken(UIConfigParser.SEA_WS, 0); }
		public ChardataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chardata; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterChardata(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitChardata(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitChardata(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChardataContext chardata() throws RecognitionException {
		ChardataContext _localctx = new ChardataContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_la = _input.LA(1);
			if ( !(_la==SEA_WS || _la==TEXT) ) {
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

	public static class MiscContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(UIConfigParser.COMMENT, 0); }
		public TerminalNode PI() { return getToken(UIConfigParser.PI, 0); }
		public TerminalNode SEA_WS() { return getToken(UIConfigParser.SEA_WS, 0); }
		public MiscContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_misc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).enterMisc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UIConfigParserListener ) ((UIConfigParserListener)listener).exitMisc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof UIConfigParserVisitor ) return ((UIConfigParserVisitor<? extends T>)visitor).visitMisc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MiscContext misc() throws RecognitionException {
		MiscContext _localctx = new MiscContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_misc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << SEA_WS) | (1L << PI))) != 0)) ) {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\22W\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\5\2\22\n\2\3\2\3\2\7\2"+
		"\26\n\2\f\2\16\2\31\13\2\3\3\3\3\7\3\35\n\3\f\3\16\3 \13\3\3\3\3\3\3\4"+
		"\5\4%\n\4\3\4\3\4\3\4\3\4\5\4+\n\4\3\4\5\4.\n\4\7\4\60\n\4\f\4\16\4\63"+
		"\13\4\3\5\3\5\3\5\7\58\n\5\f\5\16\5;\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\7\5G\n\5\f\5\16\5J\13\5\3\5\5\5M\n\5\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\4\4\2\6\6\t\t\5\2\3\3\6\6\22\22"+
		"\\\2\21\3\2\2\2\4\32\3\2\2\2\6$\3\2\2\2\bL\3\2\2\2\nN\3\2\2\2\fR\3\2\2"+
		"\2\16T\3\2\2\2\20\22\5\4\3\2\21\20\3\2\2\2\21\22\3\2\2\2\22\27\3\2\2\2"+
		"\23\26\5\16\b\2\24\26\5\b\5\2\25\23\3\2\2\2\25\24\3\2\2\2\26\31\3\2\2"+
		"\2\27\25\3\2\2\2\27\30\3\2\2\2\30\3\3\2\2\2\31\27\3\2\2\2\32\36\7\b\2"+
		"\2\33\35\5\n\6\2\34\33\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2"+
		"\37!\3\2\2\2 \36\3\2\2\2!\"\7\13\2\2\"\5\3\2\2\2#%\5\f\7\2$#\3\2\2\2$"+
		"%\3\2\2\2%\61\3\2\2\2&+\5\b\5\2\'+\7\4\2\2(+\7\22\2\2)+\7\3\2\2*&\3\2"+
		"\2\2*\'\3\2\2\2*(\3\2\2\2*)\3\2\2\2+-\3\2\2\2,.\5\f\7\2-,\3\2\2\2-.\3"+
		"\2\2\2.\60\3\2\2\2/*\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62"+
		"\7\3\2\2\2\63\61\3\2\2\2\64\65\7\7\2\2\659\7\20\2\2\668\5\n\6\2\67\66"+
		"\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;9\3\2\2\2<=\7\n\2\2"+
		"=>\5\6\4\2>?\7\7\2\2?@\7\r\2\2@A\7\20\2\2AB\7\n\2\2BM\3\2\2\2CD\7\7\2"+
		"\2DH\7\20\2\2EG\5\n\6\2FE\3\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2"+
		"\2\2JH\3\2\2\2KM\7\f\2\2L\64\3\2\2\2LC\3\2\2\2M\t\3\2\2\2NO\7\20\2\2O"+
		"P\7\16\2\2PQ\7\17\2\2Q\13\3\2\2\2RS\t\2\2\2S\r\3\2\2\2TU\t\3\2\2U\17\3"+
		"\2\2\2\r\21\25\27\36$*-\619HL";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}