// Generated from ObjectParser.g4 by ANTLR 4.6

	package ms.lang.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ObjectParser}.
 */
public interface ObjectParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ObjectParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(ObjectParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(ObjectParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(ObjectParser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(ObjectParser.PackageNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#templateArgs}.
	 * @param ctx the parse tree
	 */
	void enterTemplateArgs(ObjectParser.TemplateArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#templateArgs}.
	 * @param ctx the parse tree
	 */
	void exitTemplateArgs(ObjectParser.TemplateArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ObjectParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ObjectParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#packageClause}.
	 * @param ctx the parse tree
	 */
	void enterPackageClause(ObjectParser.PackageClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#packageClause}.
	 * @param ctx the parse tree
	 */
	void exitPackageClause(ObjectParser.PackageClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#importClass}.
	 * @param ctx the parse tree
	 */
	void enterImportClass(ObjectParser.ImportClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#importClass}.
	 * @param ctx the parse tree
	 */
	void exitImportClass(ObjectParser.ImportClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(ObjectParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(ObjectParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ObjectParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ObjectParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#superMod}.
	 * @param ctx the parse tree
	 */
	void enterSuperMod(ObjectParser.SuperModContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#superMod}.
	 * @param ctx the parse tree
	 */
	void exitSuperMod(ObjectParser.SuperModContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#typeInit}.
	 * @param ctx the parse tree
	 */
	void enterTypeInit(ObjectParser.TypeInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#typeInit}.
	 * @param ctx the parse tree
	 */
	void exitTypeInit(ObjectParser.TypeInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#headerInit}.
	 * @param ctx the parse tree
	 */
	void enterHeaderInit(ObjectParser.HeaderInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#headerInit}.
	 * @param ctx the parse tree
	 */
	void exitHeaderInit(ObjectParser.HeaderInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#classVar}.
	 * @param ctx the parse tree
	 */
	void enterClassVar(ObjectParser.ClassVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#classVar}.
	 * @param ctx the parse tree
	 */
	void exitClassVar(ObjectParser.ClassVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#localVar}.
	 * @param ctx the parse tree
	 */
	void enterLocalVar(ObjectParser.LocalVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#localVar}.
	 * @param ctx the parse tree
	 */
	void exitLocalVar(ObjectParser.LocalVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#varDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclarator(ObjectParser.VarDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#varDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclarator(ObjectParser.VarDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#varName}.
	 * @param ctx the parse tree
	 */
	void enterVarName(ObjectParser.VarNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#varName}.
	 * @param ctx the parse tree
	 */
	void exitVarName(ObjectParser.VarNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#mod}.
	 * @param ctx the parse tree
	 */
	void enterMod(ObjectParser.ModContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#mod}.
	 * @param ctx the parse tree
	 */
	void exitMod(ObjectParser.ModContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#varMod}.
	 * @param ctx the parse tree
	 */
	void enterVarMod(ObjectParser.VarModContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#varMod}.
	 * @param ctx the parse tree
	 */
	void exitVarMod(ObjectParser.VarModContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(ObjectParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(ObjectParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(ObjectParser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(ObjectParser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(ObjectParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(ObjectParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#paramDef}.
	 * @param ctx the parse tree
	 */
	void enterParamDef(ObjectParser.ParamDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#paramDef}.
	 * @param ctx the parse tree
	 */
	void exitParamDef(ObjectParser.ParamDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(ObjectParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(ObjectParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#bracketedBody}.
	 * @param ctx the parse tree
	 */
	void enterBracketedBody(ObjectParser.BracketedBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#bracketedBody}.
	 * @param ctx the parse tree
	 */
	void exitBracketedBody(ObjectParser.BracketedBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ObjectParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ObjectParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(ObjectParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(ObjectParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ObjectParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ObjectParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#statementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterStatementNoShortIf(ObjectParser.StatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#statementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitStatementNoShortIf(ObjectParser.StatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#baseStatement}.
	 * @param ctx the parse tree
	 */
	void enterBaseStatement(ObjectParser.BaseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#baseStatement}.
	 * @param ctx the parse tree
	 */
	void exitBaseStatement(ObjectParser.BaseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(ObjectParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(ObjectParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#statementExpr}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpr(ObjectParser.StatementExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#statementExpr}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpr(ObjectParser.StatementExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenStatement(ObjectParser.IfThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#ifThenStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenStatement(ObjectParser.IfThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStatement(ObjectParser.IfThenElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseStatement(ObjectParser.IfThenElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStatementNoShortIf(ObjectParser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseStatementNoShortIf(ObjectParser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ObjectParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ObjectParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatementNoShortIf(ObjectParser.WhileStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatementNoShortIf(ObjectParser.WhileStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(ObjectParser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(ObjectParser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(ObjectParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(ObjectParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterForStatementNoShortIf(ObjectParser.ForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitForStatementNoShortIf(ObjectParser.ForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#basicForStatement}.
	 * @param ctx the parse tree
	 */
	void enterBasicForStatement(ObjectParser.BasicForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#basicForStatement}.
	 * @param ctx the parse tree
	 */
	void exitBasicForStatement(ObjectParser.BasicForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterBasicForStatementNoShortIf(ObjectParser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitBasicForStatementNoShortIf(ObjectParser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(ObjectParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(ObjectParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(ObjectParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(ObjectParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#statementExprList}.
	 * @param ctx the parse tree
	 */
	void enterStatementExprList(ObjectParser.StatementExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#statementExprList}.
	 * @param ctx the parse tree
	 */
	void exitStatementExprList(ObjectParser.StatementExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#enhancedForStatement}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForStatement(ObjectParser.EnhancedForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#enhancedForStatement}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForStatement(ObjectParser.EnhancedForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForStatementNoShortIf(ObjectParser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForStatementNoShortIf(ObjectParser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(ObjectParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(ObjectParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(ObjectParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(ObjectParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ObjectParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ObjectParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ObjectParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ObjectParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ObjectParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ObjectParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(ObjectParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(ObjectParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ObjectParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ObjectParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvocation(ObjectParser.MethodInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvocation(ObjectParser.MethodInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void enterLeftHandSide(ObjectParser.LeftHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void exitLeftHandSide(ObjectParser.LeftHandSideContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#expressionName}.
	 * @param ctx the parse tree
	 */
	void enterExpressionName(ObjectParser.ExpressionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#expressionName}.
	 * @param ctx the parse tree
	 */
	void exitExpressionName(ObjectParser.ExpressionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#methodInvocation_base}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvocation_base(ObjectParser.MethodInvocation_baseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#methodInvocation_base}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvocation_base(ObjectParser.MethodInvocation_baseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#primary_complex}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_complex(ObjectParser.Primary_complexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#primary_complex}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_complex(ObjectParser.Primary_complexContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#primary_header}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_header(ObjectParser.Primary_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#primary_header}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_header(ObjectParser.Primary_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#primary_nested}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_nested(ObjectParser.Primary_nestedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#primary_nested}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_nested(ObjectParser.Primary_nestedContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#lhs_end}.
	 * @param ctx the parse tree
	 */
	void enterLhs_end(ObjectParser.Lhs_endContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#lhs_end}.
	 * @param ctx the parse tree
	 */
	void exitLhs_end(ObjectParser.Lhs_endContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#arrayExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpression(ObjectParser.ArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#arrayExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpression(ObjectParser.ArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#simpleArguments}.
	 * @param ctx the parse tree
	 */
	void enterSimpleArguments(ObjectParser.SimpleArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#simpleArguments}.
	 * @param ctx the parse tree
	 */
	void exitSimpleArguments(ObjectParser.SimpleArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(ObjectParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(ObjectParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#simpleArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterSimpleArgumentList(ObjectParser.SimpleArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#simpleArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitSimpleArgumentList(ObjectParser.SimpleArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#namedArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterNamedArgumentList(ObjectParser.NamedArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#namedArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitNamedArgumentList(ObjectParser.NamedArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#namedAssignment}.
	 * @param ctx the parse tree
	 */
	void enterNamedAssignment(ObjectParser.NamedAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#namedAssignment}.
	 * @param ctx the parse tree
	 */
	void exitNamedAssignment(ObjectParser.NamedAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#namedArgument}.
	 * @param ctx the parse tree
	 */
	void enterNamedArgument(ObjectParser.NamedArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#namedArgument}.
	 * @param ctx the parse tree
	 */
	void exitNamedArgument(ObjectParser.NamedArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#objectCreation}.
	 * @param ctx the parse tree
	 */
	void enterObjectCreation(ObjectParser.ObjectCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#objectCreation}.
	 * @param ctx the parse tree
	 */
	void exitObjectCreation(ObjectParser.ObjectCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#stdObjCreation}.
	 * @param ctx the parse tree
	 */
	void enterStdObjCreation(ObjectParser.StdObjCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#stdObjCreation}.
	 * @param ctx the parse tree
	 */
	void exitStdObjCreation(ObjectParser.StdObjCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#schedulerCreation}.
	 * @param ctx the parse tree
	 */
	void enterSchedulerCreation(ObjectParser.SchedulerCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#schedulerCreation}.
	 * @param ctx the parse tree
	 */
	void exitSchedulerCreation(ObjectParser.SchedulerCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#guiCreation}.
	 * @param ctx the parse tree
	 */
	void enterGuiCreation(ObjectParser.GuiCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#guiCreation}.
	 * @param ctx the parse tree
	 */
	void exitGuiCreation(ObjectParser.GuiCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#condSchedulerCreation}.
	 * @param ctx the parse tree
	 */
	void enterCondSchedulerCreation(ObjectParser.CondSchedulerCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#condSchedulerCreation}.
	 * @param ctx the parse tree
	 */
	void exitCondSchedulerCreation(ObjectParser.CondSchedulerCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#genericObjCreation}.
	 * @param ctx the parse tree
	 */
	void enterGenericObjCreation(ObjectParser.GenericObjCreationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#genericObjCreation}.
	 * @param ctx the parse tree
	 */
	void exitGenericObjCreation(ObjectParser.GenericObjCreationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#conditionalExpr}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpr(ObjectParser.ConditionalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#conditionalExpr}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpr(ObjectParser.ConditionalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(ObjectParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(ObjectParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(ObjectParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(ObjectParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(ObjectParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(ObjectParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(ObjectParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(ObjectParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(ObjectParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(ObjectParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(ObjectParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(ObjectParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(ObjectParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(ObjectParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#preIncrementExpr}.
	 * @param ctx the parse tree
	 */
	void enterPreIncrementExpr(ObjectParser.PreIncrementExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#preIncrementExpr}.
	 * @param ctx the parse tree
	 */
	void exitPreIncrementExpr(ObjectParser.PreIncrementExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#preDecrementExpr}.
	 * @param ctx the parse tree
	 */
	void enterPreDecrementExpr(ObjectParser.PreDecrementExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#preDecrementExpr}.
	 * @param ctx the parse tree
	 */
	void exitPreDecrementExpr(ObjectParser.PreDecrementExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(ObjectParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(ObjectParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#postIncrementExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostIncrementExpr(ObjectParser.PostIncrementExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#postIncrementExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostIncrementExpr(ObjectParser.PostIncrementExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#postDecrementExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostDecrementExpr(ObjectParser.PostDecrementExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#postDecrementExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostDecrementExpr(ObjectParser.PostDecrementExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#castExpr}.
	 * @param ctx the parse tree
	 */
	void enterCastExpr(ObjectParser.CastExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#castExpr}.
	 * @param ctx the parse tree
	 */
	void exitCastExpr(ObjectParser.CastExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ObjectParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ObjectParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#schedule}.
	 * @param ctx the parse tree
	 */
	void enterSchedule(ObjectParser.ScheduleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#schedule}.
	 * @param ctx the parse tree
	 */
	void exitSchedule(ObjectParser.ScheduleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#event}.
	 * @param ctx the parse tree
	 */
	void enterEvent(ObjectParser.EventContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#event}.
	 * @param ctx the parse tree
	 */
	void exitEvent(ObjectParser.EventContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#conditionalSchedule}.
	 * @param ctx the parse tree
	 */
	void enterConditionalSchedule(ObjectParser.ConditionalScheduleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#conditionalSchedule}.
	 * @param ctx the parse tree
	 */
	void exitConditionalSchedule(ObjectParser.ConditionalScheduleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#condEvent}.
	 * @param ctx the parse tree
	 */
	void enterCondEvent(ObjectParser.CondEventContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#condEvent}.
	 * @param ctx the parse tree
	 */
	void exitCondEvent(ObjectParser.CondEventContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(ObjectParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(ObjectParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(ObjectParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(ObjectParser.AttributeContext ctx);
}