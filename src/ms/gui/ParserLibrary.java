package ms.gui;

import ms.gui.gen.AttributeLexer;
import ms.gui.gen.AttributeParser;
import ms.gui.gen.AttributeParser.ValueContext;
import ms.parser.ParserHelper;
import ms.parser.error.ParseError;

public class ParserLibrary {

	public static Attribute parseAttribute(String code) {
		AttributeParser parser = ParserHelper.getParser(code, AttributeLexer::new, AttributeParser::new);
		ValueContext value = parser.value();
		ParserHelper.checkErrors(parser, "Cannot parse attribute '" + code + "'", ParseError.class);

		return new MyAttributeVisitor().visit(value);
	}

}
