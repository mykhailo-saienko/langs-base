package ms.parser.error;

import static ms.ipp.Iterables.appendList;

import java.util.ArrayList;
import java.util.List;

public class ParseError extends RuntimeException {
	private static final long serialVersionUID = -2673454898301071548L;
	private final List<Error> errors;

	public ParseError(String message, Throwable cause) {
		super(message, cause);
		errors = new ArrayList<>();
	}

	public ParseError(String message, List<Error> errors) {
		super(message + (errors == null ? "" : ":\n" + getErrorMessage(errors)));
		this.errors = new ArrayList<>(errors == null ? new ArrayList<>() : errors);
	}

	private static String getErrorMessage(List<Error> errors) {
		return appendList(errors, "", "", "\n", (e, s) -> s.append(e.toString()));
	}

	public String getErrorMessage() {
		return getErrorMessage(errors);
	}

	public List<Error> getErrors() {
		return errors;
	}
}