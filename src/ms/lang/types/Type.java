package ms.lang.types;

import static ms.ipp.Algorithms.ignore1;
import static ms.ipp.Iterables.filtered;
import static ms.ipp.Iterables.list;
import static ms.ipp.Iterables.mapped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.iterable.tree.DelegatingTree;
import ms.ipp.iterable.tree.StdMultiTree;
import ms.ipp.iterable.tree.StdTree;
import ms.ipp.iterable.tree.path.StdPathManipulator;

public class Type extends DelegatingTree<Definition> implements IType {
    protected static final Logger logger = LogManager.getLogger();

    /*
     * Fleeting classes cannot be used in other classes, only instantiated. On the other hand, they
     * can be re-compiled and re-deployed to the Virtual Machine (in which case, all instances of
     * the class are destroyed).<br> Pure is the synonym for abstract class (abstract is just a
     * reserved word)
     */
    private boolean fleeting, pure;
    private final TypeName name;
    private TypeName base;
    private final Integer lang;

    private String source;

    public static Type prototype(String packageName,
                                 String simpleName,
                                 Integer lang,
                                 String sourceCode) {
        String fullName = new StdPathManipulator('.').combine(packageName, simpleName);
        Type type = new Type(new TypeName(simpleName, fullName, false), lang);
        type.setSource(sourceCode);
        return type;
    }

    public Type(TypeName name, Integer lang) {
        this(name, lang, null);
    }

    public Type(TypeName name, Integer lang, TypeName base) {
        super(Definition.class);
        this.name = name;
        if (!name.isSimple()) {
            throw new IllegalArgumentException("Name '" + name + "' is not simple");
        }
        this.base = base;
        this.lang = lang;

        source = null;
        fleeting = pure = false;

        add(ClassVariable.class, new StdTree<>(ClassVariable.class));
        add(BaseMethod.class, new StdMultiTree<>(BaseMethod.class, BaseMethod::sameSignatureAs));
        add(BaseConstructor.class,
            new StdMultiTree<>(BaseConstructor.class, BaseConstructor::sameSignatureAs));
    }

    @Override
    public void addVariable(ClassVariable var, BiConsumer<ClassVariable, ClassVariable> updater) {
        setUpdater(ClassVariable.class, updater);
        get(ClassVariable.class).set(var.getName(), var);
    }

    @Override
    public void addMethod(BaseMethod method, BiConsumer<BaseMethod, BaseMethod> updater) {
        setUpdater(BaseMethod.class, updater);
        get(BaseMethod.class).set(method.getName(), method);
    }

    @Override
    public void addConstructor(BaseConstructor constructor,
                               BiConsumer<BaseConstructor, BaseConstructor> updater) {
        setUpdater(BaseConstructor.class, updater);
        get(BaseConstructor.class).set(constructor.getName(), constructor);
    }

    @Override
    public Iterable<BaseMethod> methods(Predicate<? super BaseMethod> pred) {
        return mapped(filtered(get(BaseMethod.class), ignore1(pred)), e -> e.getValue());
    }

    @Override
    public Iterable<BaseConstructor> constructors(Predicate<? super BaseConstructor> pred) {
        return mapped(filtered(get(BaseConstructor.class), ignore1(pred)), e -> e.getValue());
    }

    @Override
    public Iterable<ClassVariable> variables(Predicate<? super ClassVariable> pred) {
        return mapped(filtered(get(ClassVariable.class), ignore1(pred)), e -> e.getValue());
    }

    @Override
    public Iterable<Definition> definitions(Predicate<? super Definition> pred) {
        return mapped(filtered(this, ignore1(pred)), e -> e.getValue());
    }

    @Override
    public TypeName getType() {
        return name;
    }

    public void setBase(TypeName base) {
        this.base = base;
    }

    @Override
    public TypeName getBase() {
        return base;
    }

    @Override
    public void setAbstract(boolean abstr) {
        pure = abstr;
    }

    @Override
    public boolean isAbstract() {
        return pure;
    }

    @Override
    public boolean isFleeting() {
        return fleeting;
    }

    public void setFleeting(boolean fleeting) {
        this.fleeting = fleeting;
    }

    @Override
    public Integer getLanguage() {
        return lang;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public List<String> collectImports(boolean includePackage) {
        List<String> imports = new ArrayList<>();
        if (includePackage) {
            imports.add(getPackage() + ".*");
        }
        if (base != null) {
            base.addImports(imports);
        }
        for (Entry<String, Definition> e : this) {
            Definition d = e.getValue();
            // Constructors are still of type BaseMethod
            if (d instanceof BaseMethod) {
                BaseMethod m = (BaseMethod) d;
                logger.trace("Collecting imports in the {} {}",
                             m.getMethodType() == MethodType.METHOD ? "method" : "constructor",
                             m.getName());
                m.addImports(imports);
            } else {
                Variable v = (Variable) d;
                v.addImports(imports);
            }
        }
        String pack = getPackage();
        if (!includePackage && !pack.isEmpty()) {
            imports = list(imports, s -> !s.startsWith(pack));
        }
        return imports;
    }

    @Override
    public boolean isAssignableFrom(IType type) {
        return false;
    }

    @Override
    public Instance<Object> getTypeRef() {
        return null;
    }
}
