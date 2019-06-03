package ms.gui;

import ms.gui.gen.AttributeBaseVisitor;
import ms.gui.gen.AttributeParser.NamePartContext;
import ms.gui.gen.AttributeParser.ValueContext;

public class MyAttributeVisitor extends AttributeBaseVisitor<Attribute> {

	@Override
	public Attribute visitValue(ValueContext ctx) {
		String value = "";
		if (!ctx.namePart().isEmpty()) {
			StringBuilder sb = new StringBuilder(500);
			ctx.namePart().forEach(s -> sb.append(parseNamePart(s)));
			value = sb.toString();
		}

		Attribute a = new Attribute(value);
		ctx.value().forEach(v -> a.add(visitValue(v)));
		return a;
	}

	private String parseNamePart(NamePartContext ctx) {
		if (ctx.Name() != null) {
			return ctx.Name().getText();
		}

		String payload = null;
		if (ctx.QString() != null) {
			payload = ctx.QString().getText();
		} else if (ctx.String() != null) {
			payload = ctx.String().getText();
		} else {
			throw new IllegalArgumentException("You've seriously messed up, dude!");
		}
		return payload.substring(1, payload.length() - 1);
	}
}
