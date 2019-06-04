package ms.gui;

import java.util.ArrayList;
import java.util.List;

import ms.utils.Options;

public class Context<T> {
	private final List<Level<T>> levels;

	public Context() {
		levels = new ArrayList<>();
	}

	public void init(T root) {
		levels.clear();
		push(null, new Options<>()).setConstructed(root);
	}

	public T root() {
		return levels.get(0).getConstructed();
	}

	public Level<T> last() {
		return levels.get(levels.size() - 1);
	}

	public Level<T> push(String tag, Options<Object> attrs) {
		levels.add(new Level<>(tag, attrs));
		return last();
	}

	public void pop() {
		levels.remove(levels.size() - 1);
	}

	public T getNonNullParent(int offset) {
		for (int i = levels.size() - 1; i >= 0; --i) {
			if (levels.get(i).getConstructed() != null) {
				return levels.get(i).getConstructed();
			}
		}
		throw new IllegalStateException("No non-null elements in the context");
	}

	public static class Level<T> {
		private final String tag;
		private final Options<Object> attrs;
		private T constructed;

		public Level(String tag, Options<Object> attrs) {
			this.tag = tag;
			this.attrs = attrs;
		}

		public T getConstructed() {
			return constructed;
		}

		public void setConstructed(T constructed) {
			this.constructed = constructed;
		}

		public String getTag() {
			return tag;
		}

		public Options<Object> getAttrs() {
			return attrs;
		}
	}
}
