package ms.lang.ix;

import static ms.ipp.Iterables.forEach;
import static ms.ipp.Iterables.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.lang.ix.gen.ExpressionBaseVisitor;
import ms.lang.ix.gen.ExpressionParser.BlockContext;
import ms.lang.ix.gen.ExpressionParser.ExpressionContext;
import ms.lang.ix.gen.ExpressionParser.IdOrMethodContext;
import ms.lang.ix.gen.ExpressionParser.PrimaryContext;
import ms.parser.Container;
import ms.utils.NumberHelper;

public class VarExpressionParser extends ExpressionBaseVisitor<Var> {
	private static final Logger logger = LogManager.getLogger();

	private final Container<Var, LXClass<?>> parser;
	private final Set<String> ids;

	public VarExpressionParser(Container<Var, LXClass<?>> parser) {
		this.parser = parser;
		ids = new HashSet<>();
	}

	public ArrayList<Var> processBlock(BlockContext ctx) {
		logger.trace("Parsing block {}", () -> ctx.getText());
		ArrayList<Var> statements = new ArrayList<>();
		forEach(ctx.expression(), e -> statements.add(visitExpression(e)));
		return statements;
	}

	@Override
	public Var visitExpression(ExpressionContext ctx) {
		if (ctx.primary() != null) {
			return visitPrimary(ctx.primary());
		} else if (ctx.memOp() != null) {
			return createMember(visitExpression(ctx.expression(0)), ctx.idOrMethod());
		} else if (ctx.expression().size() == 1) {
			return createUnary(ctx.getChild(0).getText(), visitExpression(ctx.expression(0)));
		} else if (ctx.assOp() != null) {
			return createAssign(ctx.assOp().getText(), toVar(ctx.expression()));
		} else if (ctx.expression().size() >= 2) {
			return createFunc(ctx.getChild(1).getText(), toVar(ctx.expression()));
		}
		return null;
	}

	@Override
	public Var visitPrimary(PrimaryContext ctx) {
		if (ctx.expression() != null) {
			return visitExpression(ctx.expression());
		} else if (ctx.idOrMethod() != null) {
			return createRoot(ctx.idOrMethod());
		} else if (ctx.BoolConstant() != null) {
			return parser.parseConst(Boolean.valueOf(ctx.BoolConstant().getText()));
		} else if (ctx.IntConstant() != null) {
			return parser.parseConst(NumberHelper.safeParse(ctx.IntConstant().getText()));
		} else if (ctx.StringLiteral() != null) {
			String string = ctx.StringLiteral().stream().map(t -> t.getText()).map(s -> dequote(s)).reduce("",
					(s1, s2) -> s1 + s2);
			return parser.parseConst(string);
		}
		return null;
	}

	private Var createRoot(IdOrMethodContext ctx) {
		String id = ctx.Identifier().getText();
		if (ctx.funcOp() != null) {
			List<ExpressionContext> expr = ctx.expression();
			logger.trace("Root call '{}' with args '{}'", () -> id, () -> map(expr, e -> e.getText()));
			return parser.parseCall(id, toVar(expr));
		} else {
			logger.trace("Root var '{}'", id);
			ids.add(id);
			return parser.getVar(id);
		}
	}

	private Var createMember(Var parent, IdOrMethodContext ctx) {
		String id = ctx.Identifier().getText();
		if (ctx.funcOp() != null) {
			List<ExpressionContext> expr = ctx.expression();
			logger.trace("Member call '{}' with parent {} and args '{}'", () -> id, () -> parent.getName(),
					() -> map(expr, e -> e.getText()));
			return parser.parseCall(parent, id, toVar(expr));
		} else {
			logger.trace("Member '{}' with parent {}", id, parent.getName());
			return parser.getVar(parent, id);
		}
	}

	private Var createFunc(String func, List<Var> params) {
		logger.trace("Function/Operator '{}' with params {}", () -> func, () -> toText(params));
		return parser.parseCall(func, params);
	}

	private Var createUnary(String unaryOp, Var param) {
		logger.trace("Unary '{}' with param {}", unaryOp, param.getName());

		if ("+".equals(unaryOp)) {
			return param;
		}

		// ++ or --
		if (unaryOp.length() == 2) {
			// transform into "return var +=/-= 1"
			String ass = unaryOp.substring(0, 1) + "=";
			Var one = parser.parseConst(1);
			return createAssign(ass, Arrays.asList(param, one));
		}

		// - or !
		return parser.parseCall("u" + unaryOp, Arrays.asList(param));
	}

	private Var createAssign(String assOp, List<Var> params) {
		if (assOp.length() == 2) {
			logger.trace("AssignMod '{}' with params {}", assOp.substring(0, 1), toText(params));
			Var result = parser.parseCall(assOp.substring(0, 1), params);
			List<Var> old = params;
			params = new ArrayList<>();
			params.add(old.get(0));
			params.add(result);
			assOp = assOp.substring(1);
		}

		logger.trace("AssignOp '{}' with params {}", assOp, toText(params));
		return parser.parseCall(assOp, params);
	}

	private String dequote(String s) {
		// it is at least of length two (because of '').
		return (s == null) ? null : s.substring(1, s.length() - 1);
	}

	private List<Var> toVar(List<ExpressionContext> exprs) {
		return exprs.stream().map(e -> visitExpression(e)).collect(Collectors.toList());
	}

	private List<String> toText(List<Var> exprs) {
		return exprs.stream().map(e -> e.getName()).collect(Collectors.toList());
	}
}
