package ms.parser.error;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ErrorHandler extends BaseErrorListener {

	private final List<Error> errors;

	public ErrorHandler() {
		errors = new ArrayList<>();
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		if (offendingSymbol != null && offendingSymbol instanceof CommonToken) {
			offendingSymbol = ((CommonToken) offendingSymbol).getText();
		}
		getErrors().add(new Error(offendingSymbol, line, charPositionInLine, msg));
	}

	public List<Error> getErrors() {
		return errors;
	}
}