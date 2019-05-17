// Generated from Expression.g4 by ANTLR 4.6

	package ms.lang.ix.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ExpressionParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ExpressionParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#memOp}.
	 * @param ctx the parse tree
	 */
	void enterMemOp(ExpressionParser.MemOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#memOp}.
	 * @param ctx the parse tree
	 */
	void exitMemOp(ExpressionParser.MemOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#funcOp}.
	 * @param ctx the parse tree
	 */
	void enterFuncOp(ExpressionParser.FuncOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#funcOp}.
	 * @param ctx the parse tree
	 */
	void exitFuncOp(ExpressionParser.FuncOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#funcCl}.
	 * @param ctx the parse tree
	 */
	void enterFuncCl(ExpressionParser.FuncClContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#funcCl}.
	 * @param ctx the parse tree
	 */
	void exitFuncCl(ExpressionParser.FuncClContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#assOp}.
	 * @param ctx the parse tree
	 */
	void enterAssOp(ExpressionParser.AssOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#assOp}.
	 * @param ctx the parse tree
	 */
	void exitAssOp(ExpressionParser.AssOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ExpressionParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ExpressionParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#idOrMethod}.
	 * @param ctx the parse tree
	 */
	void enterIdOrMethod(ExpressionParser.IdOrMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#idOrMethod}.
	 * @param ctx the parse tree
	 */
	void exitIdOrMethod(ExpressionParser.IdOrMethodContext ctx);
}