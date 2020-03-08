/**
 * 
 */
package ms.utils;

import static java.lang.System.currentTimeMillis;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Mykhailo Saienko
 * 
 */
public class DateHelper {
	private static final Logger logger = LogManager.getLogger();

	public static final Locale LOCALE = Locale.ENGLISH;

	// Default is the system time zone
	static final ThreadLocal<Calendar> calendar = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return getCalendar();
		};
	};

	public static TimeZone getUTC() {
		return TimeZone.getTimeZone("UTC");
	}

	public static void setSystemTimeZone(TimeZone zone) {
		TimeZone.setDefault(zone);
		calendar.set(getCalendar());
	}

	public static TimeZone getSystemTimeZone() {
		return TimeZone.getDefault();
	}

	static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static final ThreadLocal<SimpleDateFormat> DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT, getLocale());
		};
	};
	static final ThreadLocal<SimpleDateFormat> DATETIME_FORMAT_ALL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT + ".SSS", getLocale());
		};
	};
	static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd", getLocale());
		};
	};
	static final ThreadLocal<SimpleDateFormat> TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss", getLocale());
		};
	};

	public static final Locale getLocale() {
		return LOCALE;
	}

	public static final Date getNow() {
		calendar.get().setTimeInMillis(currentTimeMillis());
		return calendar.get().getTime();
	}

	public static boolean before(Date date, Date ref, boolean strict) {
		return date.before(ref) || (!strict && date.equals(ref));
	}

	public static boolean after(Date date, Date ref, boolean strict) {
		return date.after(ref) || (!strict && date.equals(ref));
	}

	public static Date closest(Date ref, Predicate<Date> pred, Function<Date, Date> generator,
			int periodType, int periodLength) {
		// TODO: May have more efficient implementation for concrete Schedulers
		Date closest = generator.apply(ref);
		logger.trace("closest enter: ref={}, closest={}", format(ref), format(closest));
		Date initStart = ref;
		Date preliminaryClosest = closest;

		// 1. ensure there is no date closer to ref than 'closest' (but still
		// satisfying the predicate)
		while (pred.test(preliminaryClosest)) {
			closest = preliminaryClosest;
			initStart = DateHelper.add(initStart, periodType, periodLength);
			preliminaryClosest = generator.apply(initStart);
			logger.trace("closest Phase 1: ref={}, closest={}, preliminary={}, initStart={}",
					format(ref), format(closest), format(preliminaryClosest), format(initStart));
		}

		// 2. ensure we haven't landed in the past (or optionally the present)
		while (!pred.test(closest)) {
			closest = generator.apply(initStart);
			initStart = DateHelper.add(initStart, periodType, -periodLength);
			logger.trace("closest Phase 2: closest={}, initStart={}", format(closest),
					format(closest));
		}
		logger.trace("closest result={}", format(closest));
		return closest;
	}

	public static Date shift(Date source, TimeZone from, TimeZone to) {
		if (source == null) {
			return null;
		} else if (from.hasSameRules(to)) {
			return source;
		} else {
			long time = source.getTime();
			long newTime = time - from.getOffset(time) + to.getOffset(time);
			return new Date(newTime);
		}
	}

	public static Date getToday(int hours, int minutes, int seconds, int millisecs) {
		return get(getNow(), hours, minutes, seconds, millisecs);
	}

	public static Date get(Date source, int hours, int minutes, int seconds, int millisecs) {
		Calendar cal = calendar.get();
		cal.setTime(source);
		cal.set(HOUR_OF_DAY, hours);
		cal.set(MINUTE, minutes);
		cal.set(SECOND, seconds);
		cal.set(MILLISECOND, millisecs);
		return cal.getTime();

	}

	/**
	 * 
	 * @param year
	 * @param month     month is 0-based, i.e., 0 stands for January
	 * @param day
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param millisecs
	 * @return
	 */
	public static Date get(int year, int month, int day, int hours, int minutes, int seconds,
			int millisecs) {
		Calendar cal = calendar.get();
		cal.set(year, month, day, hours, minutes, seconds);
		cal.set(MILLISECOND, millisecs);
		return cal.getTime();
	}

	public static int get(Date source, int field) {
		Calendar cal = calendar.get();
		cal.setTime(source);
		return cal.get(field);
	}

	public static Date set(Date source, int field, int value) {
		Calendar cal = calendar.get();
		cal.setTime(source);
		cal.set(field, value);
		return cal.getTime();
	}

	public static Date add(Date source, int field, int amount) {
		Calendar cal = calendar.get();
		cal.setTime(source);
		cal.add(field, amount);
		return cal.getTime();
	}

	/**
	 * Returns the date diff date1 - date2 in terms of seconds.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiff(Date date1, Date date2) {
		return secsSinceEpoch(date1) - secsSinceEpoch(date2);

	}

	public static long secsSinceEpoch(Date date) {
		return date == null ? 0 : date.getTime() / 1000;
	}

	public static Date getDate(long secsSinceEpoch) {
		return new Date(secsSinceEpoch * 1000);
	}

	public static Date addSecsToDate(Date date, int secs) {
		return new Date(date.getTime() + secs * 1000);
	}

	public static void addTime(Date date, int hrs, int mins, int secs, long millisecs) {
		date.setTime(date.getTime() + hrs * 3600000 + mins * 60000 + secs * 1000 + millisecs);
	}

	public static Date combine(Date inDate, Date inTime) {
		return combine(inDate, inTime, false);
	}

	public static Date combine(Date inDate, Date inTime, boolean milliSecs) {
		if (inDate == null || inTime == null) {
			return null;
		}
		Calendar date = getCalendar();
		date.setTime(inDate);

		Calendar time = getCalendar();
		time.setTime(inTime);
		date.set(HOUR_OF_DAY, time.get(HOUR_OF_DAY));
		date.set(MINUTE, time.get(MINUTE));
		date.set(SECOND, time.get(SECOND));
		date.set(MILLISECOND, milliSecs ? time.get(MILLISECOND) : 0);
		return date.getTime();
	}

	public static final String getNowString() {
		return format(getNow());
	}

	public static final String format(Date date) {
		return date == null ? null : DATETIME_FORMAT.get().format(date);
	}

	public static final String formatAll(Date date) {
		return date == null ? null : DATETIME_FORMAT_ALL.get().format(date);
	}

	public static final String formatTimeOnly(Date date) {
		return date == null ? null : TIME_FORMAT.get().format(date);
	}

	public static final String formatDateOnly(Date date) {
		return date == null ? null : DATE_FORMAT.get().format(date);
	}

	public static final Date safeParse(String date) {
		try {
			return parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static final Date safeParseDate(String date) {
		try {
			return parseDate(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static final Date safeParseTime(String date) {
		try {
			return parseTime(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Flexibly decides which format to use: the one with the milliseconds and the
	 * one without
	 * 
	 * @param date
	 * @return
	 */
	public static final Date safeParseAll(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() == DEFAULT_DATE_FORMAT.length()) {
			return safeParse(date);
		}
		try {
			return DATETIME_FORMAT_ALL.get().parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static final Date parseDate(String date) throws ParseException {
		return date == null ? null : DATE_FORMAT.get().parse(date);
	}

	public static final Date parse(String date) throws ParseException {
		return date == null ? null : DATETIME_FORMAT.get().parse(date);
	}

	/**
	 * Parses text from a string to produce a Date. <br>
	 * The method attempts to parse text starting at the index given by pos. If
	 * parsing succeeds, then the index of pos is updated to the index after the
	 * last character used (parsing does not necessarily use all characters up to
	 * the end of the string), and the parsed date is returned. The updated pos can
	 * be used to indicate the starting point for the next call to this method. If
	 * an error occurs, then the index of pos is not changed, the error index of pos
	 * is set to the index of the character where the error occurred, and null is
	 * returned.
	 * 
	 * @param source
	 * @return
	 */
	public static final Date parse(String source, ParsePosition pos) {
		return DATETIME_FORMAT.get().parse(source, pos);
	}

	public static final Date parseTime(String source) throws ParseException {
		return TIME_FORMAT.get().parse(source);
	}

	public static void sleep(int millisecs) {
		try {
			Thread.sleep(millisecs);
		} catch (InterruptedException e) {
			e.printStackTrace(); // this never happens anyway :)
		}
	}

	public static void fork(Runnable r, String name) {
		new Thread(r, name).start();
	}

	public static boolean isWeekend(Date date) {
		int day = get(date, Calendar.DAY_OF_WEEK);
		return day == Calendar.SUNDAY || day == Calendar.SATURDAY;
	}

	/**
	 * 
	 * @param target
	 * @param threshold
	 * @param resolution minimum time resolution in milliseconds
	 */
	public static void advance(Date target, Date threshold, Integer resolution) {
		if (resolution == null || threshold.before(target)) {
			return;
		}

		long msecsDelta = threshold.getTime() - target.getTime();
		// difference in terms of resolution steps
		long stepsDelta = msecsDelta / resolution;
		stepsDelta++;// next resolution step must be one step ahead of cur time
		// add that many millis to obtain new resolution time
		long millisToAdvance = stepsDelta * resolution;
		target.setTime(target.getTime() + millisToAdvance);

	}

	/**
	 * Simple but slow advance method. Recommended for usage if the dates are not
	 * very far from each other
	 * 
	 * @param target
	 * @param threshold
	 * @param periodType
	 */
	public static Date advance(Date target, Date threshold, int periodType, int periodLength) {
		Date newDate = target;
		while (!newDate.after(threshold)) {
			newDate = add(newDate, periodType, periodLength);
		}
		return newDate;
	}

	/**
	 * Aligns a date according to the granularity given by the periodType and
	 * periodLength. The returned date is never after the original date.
	 * 
	 * @param target
	 * @param periodType
	 * @param periodLength
	 * @return
	 */
	public static Date align(Date target, int periodType, int periodLength) {
		if (periodType == Calendar.ERA || periodType == Calendar.AM_PM) {
			throw new IllegalArgumentException(
					"Calendar.ERA and Calendar.AM_PM are not acceptable periodTypes");
		}
		// --- first, truncate all less significant fields
		if (periodType < MILLISECOND) {
			target = set(target, MILLISECOND, 0);
		}
		if (periodType < SECOND) {
			target = set(target, SECOND, 0);
		}
		if (periodType < MINUTE) {
			target = set(target, MINUTE, 0);
		}
		if (periodType < HOUR) {
			target = set(target, HOUR_OF_DAY, 0);
		}
		if (periodType == Calendar.WEEK_OF_MONTH || periodType == Calendar.WEEK_OF_YEAR) {
			target = set(target, Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		} else if (periodType == Calendar.MONTH) {
			target = set(target, Calendar.DAY_OF_MONTH, 1);
		} else if (periodType == Calendar.YEAR) {
			target = set(target, Calendar.DAY_OF_YEAR, 1);
		}

		// --- second, make the significant field a multiple of periodLength
		int amount = get(target, periodType);
		amount = (amount / periodLength) * periodLength;
		return set(target, periodType, amount);
	}

	/**
	 * The task should take into account that the first parameter is the day the
	 * task has to process and the second parameter is the same day with time
	 * 23:59:59,999
	 * 
	 * @param begin
	 * @param end
	 * @param task
	 */
	public static void forEachDay(Date begin, Date end, boolean includeStart,
			BiConsumer<Date, Date> task) {
		forEachPeriod(begin, end, includeStart, Calendar.DATE, 1, task);
	}

	public static void forEachPeriod(Date begin, Date end, boolean includeStart, int type,
			int number, BiConsumer<Date, Date> task) {
		if (begin == null) {
			throw new IllegalArgumentException("Begin date cannot be null");
		}

		if (end == null) {
			end = getNow();
		}
		// interpret both dates as END dates of some continuous events. Hence, if the
		// caller signals that 'begin' must be included (i.e., treated as the end of an
		// event) and if begin is aligned, it means the first event ENDS at an aligned
		// date but its start date is BEFORE this date. Hence, we need to start with
		// (begin-1 period, begin] in this case.
		Date date = align(begin, type, number);
		if (date.equals(begin) && includeStart) {
			date = add(begin, type, -number);
		}

		while (!date.after(end)) {

			Date from = date;
			date = add(from, type, number);
			task.accept(from, date);
		}
	}

	public static boolean isTime(Date data, Date expTime) {
		if (expTime == null) {
			return true;
		}
		return isTime(data, get(expTime, Calendar.HOUR_OF_DAY), get(expTime, Calendar.MINUTE),
				get(expTime, Calendar.SECOND));
	}

	public static boolean isTime(Date date, Integer hour, Integer minute, Integer second) {
		return (hour == null || get(date, Calendar.HOUR_OF_DAY) == hour)//
				&& (minute == null || get(date, Calendar.MINUTE) == minute) //
				&& (second == null || get(date, Calendar.SECOND) == second);
	}

	public static boolean isTime(Date date, Predicate<Integer> hour, Predicate<Integer> minute,
			Predicate<Integer> second) {
		return (hour == null || hour.test(get(date, Calendar.HOUR_OF_DAY)))//
				&& (minute == null || minute.test(get(date, Calendar.MINUTE))) //
				&& (second == null || second.test(get(date, Calendar.SECOND)));
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

}
