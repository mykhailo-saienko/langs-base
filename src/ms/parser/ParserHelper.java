package ms.parser;

import static ms.ipp.Iterables.appendList;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Function;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.parser.error.Error;
import ms.parser.error.ErrorHandler;
import ms.parser.error.ParseError;

public class ParserHelper {
	private static final Logger logger = LogManager.getLogger();

	public static <T extends Parser> T getParser(String source, Function<CharStream, Lexer> lexer,
			Function<TokenStream, T> generator) {
		ANTLRInputStream charStream = new ANTLRInputStream(source);
		CommonTokenStream tokens = new CommonTokenStream(lexer.apply(charStream));
		T parser = generator.apply(tokens);
		// insert the error-listener which collects errors into a list
		parser.removeErrorListeners();
		ErrorHandler errorHandler = new ErrorHandler();
		parser.addErrorListener(errorHandler);
		return parser;
	}

	public static <T extends Throwable> T createError(Class<T> wrapper, String message, List<Error> errors) {
		if (wrapper == null) {
			throw new IllegalArgumentException("ErrorClass cannot be null");
		}
		T result = null;
		try {
			if (wrapper == ParseError.class) {
				// We cannot directly convert ParseError to T, that's why we are
				// using the constructor's newInstance.
				Constructor<T> constructor = wrapper.getConstructor(String.class, List.class);
				result = constructor.newInstance(message, errors);
			} else {
				Constructor<T> constructor = wrapper.getConstructor(String.class);
				result = constructor.newInstance(message + ":\n" + appendList(errors, "", "", "\n", Object::toString));
			}
		} catch (Exception e1) {
			logger.fatal("Unexpected error while instantiating a constructor for " + wrapper.getName()
					+ ". This is a design-issue", e1);
		}

		return result;
	}

	public static <T extends Throwable> void checkErrors(Parser parser, String message, Class<T> errorClass) throws T {
		ErrorHandler handler = (ErrorHandler) parser.getErrorListeners().get(0);
		if (!handler.getErrors().isEmpty()) {
			throw createError(errorClass, message, handler.getErrors());
		}
	}
}
