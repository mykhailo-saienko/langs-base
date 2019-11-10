package ms.gui;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.appendList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ms.gui.gen.AttributeLexer;
import ms.gui.gen.AttributeParser;
import ms.gui.gen.AttributeParser.ValueContext;
import ms.lang.ix.Enumeration;
import ms.parser.ParserHelper;
import ms.parser.error.ParseError;

public class ParserLibrary {

	public static Attribute parseAttribute(String code) {
		AttributeParser parser = ParserHelper.getParser(code, AttributeLexer::new, AttributeParser::new);
		ValueContext value = parser.value();
		ParserHelper.checkErrors(parser, "Cannot parse attribute '" + code + "'", ParseError.class);

		return new MyAttributeVisitor().visit(value);
	}

	public static String getFormat(Enumeration enums) {
		StringBuffer sb = new StringBuffer(500);
		appendList(sb, enums.toMap().keySet(), "[", "]", " | ", (s, b) -> b.append(s));
		return sb.toString();
	}

	public static String getFormat(Object type, int size) {
		List<String> types = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			types.add(type.toString());
		}
		StringBuffer sb = new StringBuffer(500);
		appendList(sb, types);
		return sb.toString();
	}

	public static void throwAttr(String name, String value, String format) {
		throw new IllegalArgumentException(
				"Malformed '" + name + "'-attribute " + value + ", expected '" + format + "'");
	}

	public static void validate(String name, Attribute attr, Collection<String> expValues, int expNrParams,
			String expFormat) {
		if (attr == null) {
			throw new IllegalArgumentException("Attribute '" + name + "' is null.");
		}
		if ((expValues != null && !expValues.contains(attr.getValue())) //
				|| attr.getParams().size() != expNrParams) {
			throwAttr(name, attr.toString(), expFormat);
		}
	}

	public static void validate(String name, Attribute attr, String expValue, int expNrParams, String expFormat) {
		List<String> allowedOption = expValue == null ? null : asList(expValue);
		validate(name, attr, allowedOption, expNrParams, expFormat);
	}

}
