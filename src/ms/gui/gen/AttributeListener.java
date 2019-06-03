// Generated from Attribute.g4 by ANTLR 4.6

	package ms.gui.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AttributeParser}.
 */
public interface AttributeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AttributeParser#VALUE}.
	 * @param ctx the parse tree
	 */
	void enterValue(AttributeParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link AttributeParser#VALUE}.
	 * @param ctx the parse tree
	 */
	void exitValue(AttributeParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link AttributeParser#namePart}.
	 * @param ctx the parse tree
	 */
	void enterNamePart(AttributeParser.NamePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link AttributeParser#namePart}.
	 * @param ctx the parse tree
	 */
	void exitNamePart(AttributeParser.NamePartContext ctx);
}