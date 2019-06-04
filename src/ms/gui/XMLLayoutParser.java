package ms.gui;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ms.gui.gen.UIConfigParser.AttributeContext;
import ms.gui.gen.UIConfigParser.ContentContext;
import ms.gui.gen.UIConfigParser.DocumentContext;
import ms.gui.gen.UIConfigParser.ElementContext;
import ms.utils.Options;

public class XMLLayoutParser<T> {
	private final GUIFactory<T> factory;

	public XMLLayoutParser(GUIFactory<T> factory) {
		this.factory = factory;
	}

	public void visitDocument(DocumentContext ctx) {
		factory.init();

		if (ctx.element() != null) {
			for (ElementContext elem : ctx.element()) {
				addElement(elem);
			}
		}

		factory.terminate();
	}

	private void addElement(ElementContext ctx) {
		factory.beginProcess(getTag(ctx), getAttributes(ctx));

		ContentContext cc = ctx.content();
		List<ElementContext> elems = cc != null ? cc.element() : null;
		// the terminal condition is "no elems left".
		if (elems != null) {
			for (ElementContext elem : elems) {
				addElement(elem);
			}
		}

		factory.endProcess();
	}

	private Options<String> getAttributes(ElementContext ctx) {
		Options<String> attrs = new Options<>();
		List<AttributeContext> attrCtxs = ctx.attribute();
		for (AttributeContext ac : attrCtxs) {
			String name = ac.Name().getText();
			String value = ac.STRING().getText();
			// trim the quotes and store the value
			attrs.put(name, value.substring(1, value.length() - 1));
		}
		return attrs;
	}

	private String getTag(ElementContext ctx) {
		List<TerminalNode> names = ctx.Name();

		if (names == null || names.isEmpty() || names.size() > 2) {
			throw new IllegalArgumentException("Malformed tag: " + ctx.getText());
		}
		// short tag
		if (names.size() == 1 && names.get(0) != null) {
			return names.get(0).getText();
		}

		// long tag
		TerminalNode open = names.get(0), close = names.get(1);
		if (open == null || close == null) {
			throw new IllegalArgumentException("Malformed tag: " + ctx.getText());
		}
		String openTag = open.getText(), closeTag = close.getText();
		if (openTag == null || closeTag == null || !openTag.equals(closeTag)) {
			throw new IllegalArgumentException("Malformed tag: " + ctx.getText());
		}
		return openTag;
	}
}
