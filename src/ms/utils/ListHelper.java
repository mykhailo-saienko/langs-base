package ms.utils;

import static ms.ipp.Iterables.appendList;
import static ms.utils.StringHelper.quote;

import java.util.Collection;

public class ListHelper {
	public static void appendQuotedList(StringBuffer buf, Collection<?> list) {
		appendList(buf, list, (t, b) -> b.append(quote(t.toString())));
	}

}
