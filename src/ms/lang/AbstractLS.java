package ms.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.Iterables;
import ms.lang.types.IType;

public abstract class AbstractLS implements LanguageSystem {
	private static final Logger logger = LogManager.getLogger(AbstractLS.class);

	public static final String EXTRACTED_OPTION = "Extracted";

	private Consumer<List<IType>> preCompiler;

	private final List<TypeLoader> typeLoaders;

	public AbstractLS() {
		this.typeLoaders = new ArrayList<>();
	}

	public void setPreCompiler(Consumer<List<IType>> preCompiler) {
		this.preCompiler = preCompiler;
	}

	@Override
	public List<IType> compile(String source, Map<String, Object> options) {
		List<IType> types = Iterables.get(EXTRACTED_OPTION, options, () -> extract(source, options));
		if (preCompiler != null) {
			preCompiler.accept(types);
		}
		compile(source, types, options);
		return types;
	}

	@Override
	public IType getType(List<String> packages, String name, Predicate<IType> pred) {
		if (name == null) {
			return null;
		}
		if (typeLoaders.isEmpty()) {
			typeLoaders.addAll(initTypeLoaders());
		}
		for (TypeLoader tl : typeLoaders) {
			Collection<String> modPackages = tl.packageModifier.apply(packages);
			IType type = doGetType(modPackages, name, tl.loader);
			if (type != null) {
				boolean result = pred == null || pred.test(type);
				logger.trace("Type '{}'" + " found {} the pred test", type.getFullName(),
						result ? " and passed" : " but did not pass");
				return result ? type : null;
			}
		}
		return null;
	}

	@Override
	public boolean isAssignableFrom(IType base, IType target) {
		if (base == null || target == null) {
			return false;
		} else if (base.isCompatible(target)) {
			return base.isAssignableFrom(target);
		}
		return isCrossLSAssignableFrom(base, target);
	}

	protected Predicate<String> getNameBasedPred(Predicate<IType> pred) {
		if (pred == null) {
			return null;
		}
		return name -> {
			IType type = getType(new ArrayList<>(), name);
			return (type != null && pred.test(type));
		};
	}

	protected boolean isCrossLSAssignableFrom(IType base, IType target) {
		return false;
	}

	protected abstract List<IType> extract(String source, Map<String, Object> options);

	protected abstract void compile(String source, List<IType> types, Map<String, Object> options);

	protected abstract List<TypeLoader> initTypeLoaders();

	protected static class TypeLoader {
		private final Function<String, IType> loader;
		private final Function<Collection<String>, Collection<String>> packageModifier;

		public static Collection<String> noPackage(Collection<String> source) {
			return new ArrayList<>();
		}

		public static Collection<String> add(Collection<String> source, Collection<String> surplus) {
			return Iterables.union(source, surplus);
		}

		public TypeLoader(Function<String, IType> loader) {
			this(loader, s -> s);
		}

		public TypeLoader(Function<String, IType> loader,
				Function<Collection<String>, Collection<String>> packageModifier) {
			this.loader = loader;
			this.packageModifier = packageModifier;
		}
	}

	private static IType doGetType(Collection<String> packages, String className, Function<String, IType> loader) {
		IType type = loader.apply(className);
		if (type != null) {
			return type;
		}
		for (String s : packages) {
			if (s.endsWith(className)) {
				return loader.apply(s);
			} else if (s.endsWith(".*")) {
				s = s.substring(0, s.length() - 1) + className;
				type = loader.apply(s);
				if (type != null) {
					return type;
				}
			}
		}
		return null;
	}
}
