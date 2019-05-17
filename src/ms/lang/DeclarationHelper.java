package ms.lang;

import static ms.ipp.Iterables.any;
import static ms.ipp.Iterables.appendList;
import static ms.ipp.Iterables.list;
import static ms.ipp.Iterables.map;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.Iterables;
import ms.lang.types.BaseMethod;
import ms.lang.types.IType;
import ms.lang.types.Variable;

public class DeclarationHelper {
	private static final Logger logger = LogManager.getLogger();

	public static String call(String name, List<String> arguments) {
		return appendList(arguments, name + "(", ")", ", ", (v, s) -> s.append(v));
	}

	public static String varCall(String name, List<Variable> arguments) {
		return call(name, map(arguments, v -> v.getName()));
	}

	public static boolean isAbstract(IType type, BiFunction<List<String>, String, IType> typeProvider) {
		// if type has implemented only a part of abstract methods from
		// the base, it is still abstract

		// create a hierarchy of types where the most base class comes first
		// and this class comes last
		List<IType> bases = new ArrayList<>();
		IType base = type;
		String baseName = type.getFullName();
		while (true) {
			bases.add(0, base);

			baseName = base.getBaseName();
			List<String> imports = base.collectImports(true);
			if (baseName == null) {
				break;
			}
			base = typeProvider.apply(imports, baseName);
			if (base == null) {
				throw new IllegalArgumentException("Base class '" + baseName + "' doesn't exist");
			}
			// if the base is not abstract
			if (!base.isAbstract()) {
				break;
			}
		}

		// now start accumulating abstract methods and deleting them from the
		// list if they are implemented in any of the subclasses
		List<BaseMethod> abstractMethods = new ArrayList<>();
		for (IType t : bases) {
			logger.trace("Scanning type {}. Remaining abstracts {}", () -> t.getFullName(),
					() -> map(abstractMethods, m -> m.getSignature()));

			// if the class implements an abstract method, remove it from the
			// list of unimplemented abstract methods
			// We pre-filter the methods for the sake of efficiency, as we
			// assume that the number of abstract methods is small compared to
			// non-abstract methods
			List<BaseMethod> concreteMethods = list(t.methods(m -> !m.isAbstract()));
			for (int i = 0; i < abstractMethods.size();) {
				BaseMethod am = abstractMethods.get(i);
				// at least one abstract method is not implemented
				boolean implemented = any(concreteMethods, m -> m.sameSignatureAs(am));
				if (implemented) {
					logger.trace("Removing abstract method {}", am.getSignature());
					abstractMethods.remove(i);
				} else {
					++i;
				}
			}

			// add all abstract methods from this class
			Iterable<BaseMethod> abstracts = t.methods(m -> m.isAbstract());
			logger.trace("Adding abstracts: {}", () -> Iterables.mapped(abstracts, m -> m.getSignature()));
			Iterables.forEach(abstracts, abstractMethods::add);
		}

		logger.trace("Checking if type '{}' is abstract: unimplemented abstract methods: {}", () -> type.getFullName(),
				() -> map(abstractMethods, m -> m.getSignature()));

		// class is abstract if at least 1 abstract method remains unimplemented
		return !abstractMethods.isEmpty();
	}

}
