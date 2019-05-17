// Generated from Expression.g4 by ANTLR 4.6

	package ms.lang.ix.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ExpressionParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#memOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemOp(ExpressionParser.MemOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#funcOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncOp(ExpressionParser.FuncOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#funcCl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCl(ExpressionParser.FuncClContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#assOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssOp(ExpressionParser.AssOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ExpressionParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#idOrMethod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdOrMethod(ExpressionParser.IdOrMethodContext ctx);
}