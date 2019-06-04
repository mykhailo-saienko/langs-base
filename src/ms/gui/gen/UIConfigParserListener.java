// Generated from UIConfigParser.g4 by ANTLR 4.6

	package ms.gui.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UIConfigParser}.
 */
public interface UIConfigParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(UIConfigParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(UIConfigParser.DocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#prolog}.
	 * @param ctx the parse tree
	 */
	void enterProlog(UIConfigParser.PrologContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#prolog}.
	 * @param ctx the parse tree
	 */
	void exitProlog(UIConfigParser.PrologContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(UIConfigParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(UIConfigParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(UIConfigParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(UIConfigParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(UIConfigParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(UIConfigParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#chardata}.
	 * @param ctx the parse tree
	 */
	void enterChardata(UIConfigParser.ChardataContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#chardata}.
	 * @param ctx the parse tree
	 */
	void exitChardata(UIConfigParser.ChardataContext ctx);
	/**
	 * Enter a parse tree produced by {@link UIConfigParser#misc}.
	 * @param ctx the parse tree
	 */
	void enterMisc(UIConfigParser.MiscContext ctx);
	/**
	 * Exit a parse tree produced by {@link UIConfigParser#misc}.
	 * @param ctx the parse tree
	 */
	void exitMisc(UIConfigParser.MiscContext ctx);
}