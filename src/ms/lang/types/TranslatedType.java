package ms.lang.types;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class TranslatedType implements IType {

    private IType original;
    private String translatedSource;

    public TranslatedType(IType original, String translatedSource) {
        this.original = original;
        this.translatedSource = translatedSource;
    }

    public IType getOriginal() {
        return original;
    }

    public void setOriginal(IType original) {
        this.original = original;
    }

    public String getTranslatedSource() {
        return translatedSource;
    }

    public void setTranslatedSource(String translatedSource) {
        this.translatedSource = translatedSource;
    }

    @Override
    public void addVariable(ClassVariable var, BiConsumer<ClassVariable, ClassVariable> updater) {
        original.addVariable(var, updater);
    }

    @Override
    public void addMethod(BaseMethod method, BiConsumer<BaseMethod, BaseMethod> updater) {
        original.addMethod(method, updater);
    }

    @Override
    public void addConstructor(BaseConstructor constructor,
                               BiConsumer<BaseConstructor, BaseConstructor> updater) {
        original.addConstructor(constructor, updater);
    }

    @Override
    public BaseMethod getStaticMethod(String name, String... params) {
        return original.getStaticMethod(name, params);
    }

    @Override
    public BaseMethod getMemberMethod(String name, Object instance, String... params) {
        return original.getMemberMethod(name, instance, params);
    }

    @Override
    public Iterable<BaseMethod> methods(Predicate<? super BaseMethod> pred) {
        return original.methods(pred);
    }

    @Override
    public BaseMethod getMethod(String name, Predicate<? super BaseMethod> pred) {
        return original.getMethod(name, pred);
    }

    @Override
    public Iterable<Definition> definitions(Predicate<? super Definition> pred) {
        return original.definitions(pred);
    }

    @Override
    public Iterable<ClassVariable> variables(Predicate<? super ClassVariable> pred) {
        return original.variables(pred);
    }

    @Override
    public Iterable<BaseConstructor> constructors(Predicate<? super BaseConstructor> pred) {
        return original.constructors(pred);
    }

    @Override
    public BaseConstructor getConstructor(String... params) {
        return original.getConstructor(params);
    }

    @Override
    public BaseConstructor getConstructor(Predicate<? super BaseConstructor> pred) {
        return original.getConstructor(pred);
    }

    @Override
    public BaseConstructor getStdConstructor() {
        return original.getStdConstructor();
    }

    @Override
    public TypeName getType() {
        return original.getType();
    }

    @Override
    public String getPackage() {
        return original.getPackage();
    }

    @Override
    public String getFullName() {
        return original.getFullName();
    }

    @Override
    public String getSimpleName() {
        return original.getSimpleName();
    }

    @Override
    public String getBaseName() {
        return original.getBaseName();
    }

    @Override
    public TypeName getBase() {
        return original.getBase();
    }

    @Override
    public ClassVariable getVariable(String name, Predicate<ClassVariable> pred) {
        return original.getVariable(name, pred);
    }

    @Override
    public void setAbstract(boolean abstr) {
        original.setAbstract(abstr);
    }

    @Override
    public boolean isAbstract() {
        return original.isAbstract();
    }

    @Override
    public boolean isFleeting() {
        return original.isFleeting();
    }

    @Override
    public Integer getLanguage() {
        return original.getLanguage();
    }

    @Override
    public String getSource() {
        return original.getSource();
    }

    @Override
    public void setSource(String source) {
        original.setSource(source);
    }

    @Override
    public List<String> collectImports(boolean includePackage) {
        return original.collectImports(includePackage);
    }

    @Override
    public boolean isAssignableFrom(IType type) {
        return original.isAssignableFrom(type);
    }

    @Override
    public Instance<Object> getTypeRef() {
        return original.getTypeRef();
    }

}
