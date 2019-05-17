// Generated from ObjectParser.g4 by ANTLR 4.6

	package ms.lang.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ObjectParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ObjectParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ObjectParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(ObjectParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(ObjectParser.PackageNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#templateArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateArgs(ObjectParser.TemplateArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ObjectParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#packageClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageClause(ObjectParser.PackageClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#importClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportClass(ObjectParser.ImportClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(ObjectParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ObjectParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#superMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperMod(ObjectParser.SuperModContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#typeInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeInit(ObjectParser.TypeInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#headerInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderInit(ObjectParser.HeaderInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#classVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassVar(ObjectParser.ClassVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#localVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVar(ObjectParser.LocalVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#varDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclarator(ObjectParser.VarDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#varName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarName(ObjectParser.VarNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#mod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMod(ObjectParser.ModContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#varMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarMod(ObjectParser.VarModContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(ObjectParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(ObjectParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(ObjectParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#paramDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDef(ObjectParser.ParamDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ObjectParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#bracketedBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedBody(ObjectParser.BracketedBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ObjectParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(ObjectParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ObjectParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#statementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementNoShortIf(ObjectParser.StatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#baseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseStatement(ObjectParser.BaseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(ObjectParser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#statementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpr(ObjectParser.StatementExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#ifThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenStatement(ObjectParser.IfThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatement(ObjectParser.IfThenElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatementNoShortIf(ObjectParser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ObjectParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatementNoShortIf(ObjectParser.WhileStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(ObjectParser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(ObjectParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatementNoShortIf(ObjectParser.ForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#basicForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatement(ObjectParser.BasicForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatementNoShortIf(ObjectParser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(ObjectParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(ObjectParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#statementExprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExprList(ObjectParser.StatementExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#enhancedForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatement(ObjectParser.EnhancedForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatementNoShortIf(ObjectParser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(ObjectParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(ObjectParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ObjectParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ObjectParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ObjectParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(ObjectParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ObjectParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation(ObjectParser.MethodInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftHandSide(ObjectParser.LeftHandSideContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#expressionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionName(ObjectParser.ExpressionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#methodInvocation_base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation_base(ObjectParser.MethodInvocation_baseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#primary_complex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_complex(ObjectParser.Primary_complexContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#primary_header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_header(ObjectParser.Primary_headerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#primary_nested}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_nested(ObjectParser.Primary_nestedContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#lhs_end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs_end(ObjectParser.Lhs_endContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#arrayExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpression(ObjectParser.ArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#simpleArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleArguments(ObjectParser.SimpleArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(ObjectParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#simpleArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleArgumentList(ObjectParser.SimpleArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#namedArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedArgumentList(ObjectParser.NamedArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#namedAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedAssignment(ObjectParser.NamedAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#namedArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedArgument(ObjectParser.NamedArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#objectCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectCreation(ObjectParser.ObjectCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#stdObjCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStdObjCreation(ObjectParser.StdObjCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#schedulerCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchedulerCreation(ObjectParser.SchedulerCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#guiCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuiCreation(ObjectParser.GuiCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#condSchedulerCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondSchedulerCreation(ObjectParser.CondSchedulerCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#genericObjCreation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericObjCreation(ObjectParser.GenericObjCreationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#conditionalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpr(ObjectParser.ConditionalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(ObjectParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(ObjectParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(ObjectParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(ObjectParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(ObjectParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(ObjectParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(ObjectParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#preIncrementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreIncrementExpr(ObjectParser.PreIncrementExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#preDecrementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreDecrementExpr(ObjectParser.PreDecrementExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(ObjectParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#postIncrementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpr(ObjectParser.PostIncrementExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#postDecrementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpr(ObjectParser.PostDecrementExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#castExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(ObjectParser.CastExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ObjectParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#schedule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchedule(ObjectParser.ScheduleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#event}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvent(ObjectParser.EventContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#conditionalSchedule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalSchedule(ObjectParser.ConditionalScheduleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#condEvent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondEvent(ObjectParser.CondEventContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(ObjectParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ObjectParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(ObjectParser.AttributeContext ctx);
}