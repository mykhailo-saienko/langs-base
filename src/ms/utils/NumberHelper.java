package ms.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberHelper {

	private static final Logger logger = LogManager.getLogger();

	private static NumberFormat generalFormat;
	private static NumberFormat formatWithPad;

	private static NumberFormat priceFormat;
	private static DecimalFormat qtyFormat;

	public static BigDecimal ZERO_PRICE = BigDecimal.ZERO.setScale(2);

	public static void temp(Integer i) {
		System.out.println("Integer: " + i);
	}

	public static void temp(boolean i) {
		System.out.println("boolean: " + i);
	}

	public static int log2floor(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		return 31 - Integer.numberOfLeadingZeros(n);
	}

	public static int log2floor(long n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		return 63 - Long.numberOfLeadingZeros(n);
	}

	public static synchronized BigDecimal parse(String priceText) throws ParseException {
		return (BigDecimal) getGeneralFormat().parse(priceText);
	}

	public static synchronized BigDecimal parsePrice(String priceText) throws ParseException {
		return (BigDecimal) getPriceFormat().parse(priceText);
	}

	public static synchronized int parseQuantity(String qtyText) throws ParseException {
		return getQtyFormat().parse(qtyText).intValue();
	}

	public static synchronized BigDecimal safeParse(String numberText) {
		try {
			return parse(numberText);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static synchronized BigDecimal safeParsePrice(String priceText) {
		try {
			return parsePrice(priceText);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static synchronized int safeParseQuantity(String qtyText) {
		try {
			return parseQuantity(qtyText);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static synchronized String formatWithLeftPad(Number number, int nrIntegerDigits) {
		getFormatWithPad().setMinimumIntegerDigits(nrIntegerDigits);
		if (number instanceof Integer || number instanceof Double || number instanceof Float
				|| number instanceof Long) {
			return getFormatWithPad().format(number);
		} else {
			return getFormatWithPad().format(number.doubleValue());
		}
	}

	public static synchronized String format(Number number) {
		if (number == null)
			return "null";

		if (number instanceof Integer || number instanceof Double || number instanceof Float || number instanceof Long
				|| number instanceof BigDecimal) {
			return getGeneralFormat().format(number);
		} else {
			return getGeneralFormat().format(number.doubleValue());
		}
	}

	public static synchronized String formatPrice(BigDecimal price) {
		return getPriceFormat().format(price);
	}

	public static synchronized String formatPrice(Float price) {
		return getPriceFormat().format(price);
	}

	public static synchronized String formatQuantity(int qty) {
		return getQtyFormat().format(qty);
	}

	public static synchronized NumberFormat getPriceFormat() {
		if (priceFormat == null) {
			DecimalFormat format = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(DateHelper.getLocale()));
			format.setParseBigDecimal(true);
			priceFormat = format;
		}
		return priceFormat;
	}

	public static synchronized NumberFormat getFormatWithPad() {
		if (formatWithPad == null) {
			DecimalFormat format = createGenericFormat(340, 340);
			format.setGroupingUsed(false);
			formatWithPad = format;
		}
		return formatWithPad;
	}

	public static synchronized NumberFormat getGeneralFormat() {
		if (generalFormat == null) {
			generalFormat = createGenericFormat(340, 340);
		}
		return generalFormat;
	}

	public static synchronized DecimalFormat getQtyFormat() {
		if (qtyFormat == null) {
			qtyFormat = new DecimalFormat("#,##0", new DecimalFormatSymbols(DateHelper.getLocale()));
		}
		return qtyFormat;
	}

	public static DecimalFormat createGenericFormat(int intDigits, int fracDigits) {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(DateHelper.getLocale()));
		format.setParseBigDecimal(true);
		format.setMinimumIntegerDigits(1);
		format.setMaximumIntegerDigits(intDigits);
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(fracDigits);
		return format;
	}

	public static double round(double num, int decimals) {
		double mult = Math.pow(10, decimals);
		int temp = (int) (num * mult);
		return temp / mult;
	}

	public static BigDecimal price(Number val) {
		return bd(val, 2);
	}

	public static BigDecimal bd(Number val) {
		return bd(val, -1);
	}

	public static BigDecimal bd(Number val, int scale) {
		if (val == null)
			return null;
		if (val instanceof Double || val instanceof Float) {
			return bd(val.doubleValue(), scale);
		} else if (val instanceof BigDecimal) {
			BigDecimal dec = (BigDecimal) val;
			return scale >= 0 && val != null ? dec.setScale(scale) : dec;
		} else {
			BigDecimal dec = new BigDecimal(val.longValue());
			if (scale >= 0) {
				dec = dec.setScale(scale);
			}
			return dec;
		}
	}

	public static BigDecimal bd(double val) {
		return bd(val, -1);
	}

	public static BigDecimal bd(double val, int scale) {
		try {
			BigDecimal num = parse(format(val));
			return scale < 0 ? num : num.setScale(scale, RoundingMode.HALF_UP);
		} catch (ParseException e) {
			logger.fatal("An error occured while converting double into BigDecimal. This is a design issue", e);
			return null;
		}
	}

	public static BigDecimal min(BigDecimal one, BigDecimal two) {
		return one == null ? (two == null ? null : two) : one.min(two);
	}

	public static BigDecimal max(BigDecimal one, BigDecimal two) {
		return one == null ? (two == null ? null : two) : one.max(two);
	}

	public static BigDecimal divide(BigDecimal dividend, int divisor) {
		return divide(dividend, new BigDecimal(divisor));
	}

	public static BigDecimal divide(int dividend, BigDecimal divisor) {
		return divide(new BigDecimal(dividend), divisor);
	}

	public static BigDecimal divide(int dividend, int divisor) {
		return divide(new BigDecimal(dividend), new BigDecimal(divisor));
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
		// scale is increased by the number of divisor's significant digits
		int scale = dividend.scale() + divisor.precision() - divisor.scale();
		return divide(dividend, divisor, scale);
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
		return dividend.divide(divisor, scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal divide(BigDecimal dividend, int divisor, int scale) {
		return divide(dividend, new BigDecimal(divisor), scale);
	}

	public static BigDecimal divide(int dividend, BigDecimal divisor, int scale) {
		return divide(new BigDecimal(dividend), divisor, scale);
	}

	public static BigDecimal divide(int dividend, int divisor, int scale) {
		return divide(new BigDecimal(dividend), new BigDecimal(divisor), scale);
	}

	public static BigDecimal multiply(BigDecimal factor1, BigDecimal factor2) {
		return multiply(factor1, factor2, 2);
	}

	public static BigDecimal multiply(BigDecimal factor1, int factor2) {
		return multiply(factor1, new BigDecimal(factor2), 2);
	}

	public static BigDecimal multiply(BigDecimal factor1, float factor2, int scale) {
		return multiply(factor1, new BigDecimal(factor2), scale);
	}

	public static BigDecimal multiply(BigDecimal factor1, float factor2) {
		return multiply(factor1, factor2, 2);
	}

	public static BigDecimal multiply(int factor1, int factor2) {
		return multiply(new BigDecimal(factor1), new BigDecimal(factor2), 2);
	}

	public static BigDecimal multiply(BigDecimal factor1, BigDecimal factor2, int scale) {
		return factor1.multiply(factor2).setScale(scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal multiply(BigDecimal factor1, int factor2, int scale) {
		return multiply(factor1, new BigDecimal(factor2), scale);
	}

	public static BigDecimal multiply(int factor1, int factor2, int scale) {
		return multiply(new BigDecimal(factor1), new BigDecimal(factor2), scale);
	}

	public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
		return subtract(minuend, subtrahend, 2);
	}

	public static BigDecimal subtract(BigDecimal minuend, int subtrahend) {
		return subtract(minuend, new BigDecimal(subtrahend), 2);
	}

	public static BigDecimal subtract(int minuend, BigDecimal subtrahend) {
		return subtract(new BigDecimal(minuend), subtrahend, 2);
	}

	public static BigDecimal subtract(int minuend, int subtrahend) {
		return subtract(new BigDecimal(minuend), new BigDecimal(subtrahend), 2);
	}

	public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend, int scale) {
		return minuend.subtract(subtrahend).setScale(scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal subtract(BigDecimal minuend, int subtrahend, int scale) {
		return subtract(minuend, new BigDecimal(subtrahend), scale);
	}

	public static BigDecimal subtract(int minuend, BigDecimal subtrahend, int scale) {
		return subtract(new BigDecimal(minuend), subtrahend, scale);
	}

	public static BigDecimal subtract(int minuend, int subtrahend, int scale) {
		return subtract(new BigDecimal(minuend), new BigDecimal(subtrahend), scale);
	}

	public static BigDecimal add(BigDecimal summand1, BigDecimal summand2) {
		return add(summand1, summand2, 2);
	}

	public static BigDecimal add(BigDecimal summand1, int summand2) {
		return add(summand1, new BigDecimal(summand2), 2);
	}

	public static BigDecimal add(int summand1, int summand2) {
		return add(new BigDecimal(summand1), new BigDecimal(summand2), 2);
	}

	public static BigDecimal add(BigDecimal summand1, BigDecimal summand2, int scale) {
		return summand1.add(summand2).setScale(scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal add(BigDecimal summand1, int summand2, int scale) {
		return add(summand1, new BigDecimal(summand2), scale);
	}

	public static BigDecimal add(int summand1, int summand2, int scale) {
		return add(new BigDecimal(summand1), new BigDecimal(summand2), scale);
	}

	/**
	 * A sufficiently precise computation routine for square root which uses the
	 * Babylonian method.
	 * https://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java
	 * 
	 * @param value
	 * @param SCALE
	 * @return
	 */
	public static BigDecimal sqrt(BigDecimal value, final int SCALE) {
		BigDecimal x0 = new BigDecimal("0");
		BigDecimal x1 = new BigDecimal(Math.sqrt(value.doubleValue()));
		BigDecimal TWO = BigDecimal.valueOf(2);
		while (!x0.equals(x1)) {
			x0 = x1;
			x1 = value.divide(x0, SCALE, RoundingMode.HALF_UP);
			x1 = x1.add(x0);
			x1 = x1.divide(TWO, SCALE, RoundingMode.HALF_UP);
		}
		return x1;
	}

	/**
	 * Compares two {@link BigDecimal} numbers. NOTE: two numbers with the same
	 * values but different scales, e.g. 2.0 and 2.00, are considered equal by this
	 * method.
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static int compare(BigDecimal arg0, BigDecimal arg1) {
		if (arg0 == null && arg1 == null) {
			return 0;
		} else if (arg0 == null) {
			return -1;
		} else if (arg1 == null) {
			return 1;
		} else {
			return arg0.compareTo(arg1);
		}
	}

	public static int compare(BigDecimal arg0, int arg1) {
		return compare(arg0, new BigDecimal(arg1));
	}

	public static int compare(int arg0, BigDecimal arg1) {
		return compare(new BigDecimal(arg0), arg1);
	}

	public static boolean lesser(BigDecimal arg0, BigDecimal arg1) {
		return compare(arg0, arg1) < 0;
	}

	public static boolean lesser(int arg0, BigDecimal arg1) {
		return compare(arg0, arg1) < 0;
	}

	public static boolean lesser(BigDecimal arg0, int arg1) {
		return compare(arg0, arg1) < 0;
	}

	public static boolean lesserEqual(BigDecimal arg0, BigDecimal arg1) {
		return compare(arg0, arg1) <= 0;
	}

	public static boolean lesserEqual(int arg0, BigDecimal arg1) {
		return compare(arg0, arg1) <= 0;
	}

	public static boolean lesserEqual(BigDecimal arg0, int arg1) {
		return compare(arg0, arg1) <= 0;
	}

	/**
	 * Returns true if the two {@link BigDecimal} numbers are equal (ignoring their
	 * scale).
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean equal(BigDecimal arg0, BigDecimal arg1) {
		return compare(arg0, arg1) == 0;
	}

	public static boolean equal(int arg0, BigDecimal arg1) {
		return compare(arg0, arg1) == 0;
	}

	public static boolean equal(BigDecimal arg0, int arg1) {
		return compare(arg0, arg1) == 0;
	}

	public static boolean largerEqual(BigDecimal arg0, BigDecimal arg1) {
		return !lesser(arg0, arg1);
	}

	public static boolean largerEqual(int arg0, BigDecimal arg1) {
		return !lesser(arg0, arg1);
	}

	public static boolean largerEqual(BigDecimal arg0, int arg1) {
		return !lesser(arg0, arg1);
	}

	public static boolean larger(BigDecimal arg0, BigDecimal arg1) {
		return !lesserEqual(arg0, arg1);
	}

	public static boolean larger(int arg0, BigDecimal arg1) {
		return !lesserEqual(arg0, arg1);
	}

	public static boolean larger(BigDecimal arg0, int arg1) {
		return !lesserEqual(arg0, arg1);
	}

	public static BigDecimal price(BigDecimal number) {
		return round(number, 2);
	}

	public static BigDecimal round(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		} else {
			return number.setScale(digits, BigDecimal.ROUND_HALF_UP);
		}
	}
}
