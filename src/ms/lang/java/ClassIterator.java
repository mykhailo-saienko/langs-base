package ms.lang.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ClassIterator implements Iterator<Class<?>> {
	private Class<?> cur;

	public ClassIterator(Class<?> source) {
		this.cur = source;
	}

	@Override
	public boolean hasNext() {
		return cur != null && cur.getSuperclass() != null;
	}

	@Override
	public Class<?> next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Class<?> ret = cur;
		cur = cur.getSuperclass();
		return ret;
	}
}