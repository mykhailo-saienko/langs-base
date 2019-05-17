package ms.lang;

import static ms.ipp.Algorithms.ignore2;
import static ms.ipp.Iterables.deleteHook;

import java.util.Map.Entry;
import java.util.function.Function;

import ms.ipp.iterable.BiIterable;
import ms.ipp.iterable.tree.AbstractTree.Recursion;
import ms.ipp.iterable.tree.DelegatingTree;
import ms.ipp.iterable.tree.StdTree;
import ms.ipp.iterable.tree.SyntheticTree;
import ms.ipp.iterator.EmptyIterator;
import ms.lang.types.Ref;

/**
 * 
 * @author mykhailo.saienko
 *
 * @param <IV> Internal variable representation (for types and variables)
 * @param <EV> External variable representation
 * @param <TV> Type representation
 */
public abstract class AbstractContainer<IV extends Ref<EV>, EV, TV> {

	private DelegatingTree<IV> vars, highest, lowest, roots;
	private SyntheticTree<IV> type;
	private final Class<IV> internal;

	public AbstractContainer(Class<IV> internal) {
		this.internal = internal;
		highest = lowest = null;
		initContainer();
	}

	public EV get(String fullName) {
		IV ref = getInternal(fullName);
		return ref == null ? null : ref.getValue();
	}

	public final EV set(String name, EV var) {
		// in essence, adds var to the last stack created
		// NOTE: all other entities (highest, type, lowest) are read-only, so it
		// is safe to set directly in the roots (and a bit more efficient)
		EV result = roots.set(name, createInstRef(var)).getValue();
		onSet(name, result);
		return result;
	}

	public void setGlobal(String name, EV var) {
		// the last one in the roots is the global
		roots.get(roots.size() - 1).set(name, var);
	}

	public final void delete(String name) {
		onDelete(name);
		roots.delete(name);
	}

	public final void pushStack() {
		roots.add(new StdTree<>(internal), 0);
	}

	public final BiIterable<String, IV> internals() {
		// we add the delete-hook called in delete();
		return deleteHook(vars, ignore2(this::onDelete));
	}

	public final void popStack() {
		if (roots.size() <= 1) {
			throw new IllegalArgumentException("Cannot pop the global stack");
		}
		// remove the most local (high-priority) stack
		roots.remove(0);
	}

	protected IV getInternal(String fullName) {
		return vars.peek(fullName);
	}

	protected final void addHighest(Function<String, IV> retriever) {
		if (highest == null) {
			// insert before any other entity;
			highest = new DelegatingTree<>(internal);
			vars.add(highest, 0);
		}
		highest.add(new SyntheticTree<>(retriever, internal));
	}

	protected final void addLowest(Function<String, IV> retriever) {
		if (lowest == null) {
			// insert AFTER any other entity;
			lowest = new DelegatingTree<>(internal);
			vars.add(lowest, vars.size());
		}
		lowest.add(new SyntheticTree<>(retriever, internal));
	}

	protected abstract IV createTypeRef(TV type);

	protected abstract IV createInstRef(EV inst);

	protected abstract TV getType(String path);

	protected void onSet(String name, EV result) {
	}

	protected void onDelete(String name) {
	};

	private void initContainer() {
		vars = new DelegatingTree<>(internal);

		// add global container for instances
		roots = new DelegatingTree<>(internal);
		pushStack();
		vars.add(roots);

		// add type-retriever (normally, it's greedy)
		type = new SyntheticTree<>(s -> {
			TV type = getType(s);
			return type == null ? null : createTypeRef(type);
		}, EmptyIterator<Entry<String, IV>>::new, internal);
		type.setRecursion(Recursion.EXHAUSTIVE);
		vars.add(type);
	}
}
