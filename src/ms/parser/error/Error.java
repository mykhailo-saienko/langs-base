package ms.parser.error;

public class Error {
	public Error(Object offendingSymbol, int line, int charInLine, String msg) {
		this.offendingSymbol = offendingSymbol;
		this.line = line;
		this.charInLine = charInLine;
		this.msg = msg;
	}

	public Object getOffendingSymbol() {
		return offendingSymbol;
	}

	public int getLine() {
		return line;
	}

	public int getCharInLine() {
		return charInLine;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(1000);
		sb.append(msg).append(" at char ").append(charInLine).append(" on line ").append(line);
		if (offendingSymbol != null) {
			sb.append(" symbol '").append(offendingSymbol).append("'");
		}

		return sb.toString();
	}

	private final Object offendingSymbol;
	private final int line;
	private final int charInLine;
	private final String msg;
}