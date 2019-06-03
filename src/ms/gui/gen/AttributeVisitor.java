// Generated from Attribute.g4 by ANTLR 4.6

	package ms.gui.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AttributeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AttributeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AttributeParser#VALUE}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(AttributeParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AttributeParser#namePart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamePart(AttributeParser.NamePartContext ctx);
}