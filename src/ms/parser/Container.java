package ms.parser;

import java.util.List;
import java.util.Map;

public interface Container<V, T> {

	/**
	 * Returns a parsed expression representing a call to a function with a
	 * given list of expressions as arguments. Binary operator calls should be
	 * parsed by this method, too, by their direct names: +=, +, etc.
	 * 
	 * 
	 * @param name
	 * @param vars
	 * @return
	 */
	V parseCall(String name, List<V> params);

	V parseCall(V parent, String name, List<V> params);

	/**
	 * Returns an expression representing a constant with a given value
	 * 
	 * @param constant
	 * @return
	 */
	<U> V parseConst(U constant);

	/**
	 * Returns an expression representing a variable with a given name
	 * 
	 * @param name
	 * @return
	 */
	V getVar(String name);

	V getVar(V parent, String name);

	void declareVar(String name, String value, String type, Map<String, String> attrs);

	T getType(String name);
}
