package ms.lang.ix;

import ms.parser.Container;

public interface IXContainer extends Container<Var<?>, LXClass<?>> {
	/**
	 * Returns an anonymous block. If the block consists of only one statement,
	 * returns only the statement as an expression.
	 * 
	 * @param code
	 * @param retType
	 * @return
	 */
	<U> Var<U> parseBlock(String code, LXClass<U> retType);

	/**
	 * Return a reference to a valid existing primitive or throws
	 * {@link IllegalArgumentException} if the primitive does not exist or is not
	 * convertible to a given type.
	 * 
	 * @param desc
	 * @param type if not null, the result will be ensured to be convertible to a
	 *             specified type
	 * @return
	 */
	<U> Var<U> convertVar(String name, LXClass<U> type);

	// Enumeration is just a special type so there must be some connection between
	// them here.
	Enumeration getEnum(String name, boolean errorOnNull);
}
