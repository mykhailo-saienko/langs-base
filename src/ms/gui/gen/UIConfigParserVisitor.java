// Generated from UIConfigParser.g4 by ANTLR 4.6

	package ms.gui.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link UIConfigParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface UIConfigParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#document}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDocument(UIConfigParser.DocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#prolog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProlog(UIConfigParser.PrologContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(UIConfigParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(UIConfigParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(UIConfigParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#chardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChardata(UIConfigParser.ChardataContext ctx);
	/**
	 * Visit a parse tree produced by {@link UIConfigParser#misc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMisc(UIConfigParser.MiscContext ctx);
}