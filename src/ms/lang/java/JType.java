package ms.lang.java;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;
import static ms.ipp.Iterables.list;
import static ms.ipp.Iterables.map;
import static ms.lang.Language.JAVA;
import static ms.lang.java.JavaRef.fromClass;
import static ms.lang.types.VarModifier.FINAL;
import static ms.lang.types.VarModifier.STANDARD;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.lang.types.BaseMethod;
import ms.lang.types.ClassVariable;
import ms.lang.types.IType;
import ms.lang.types.Instance;
import ms.lang.types.MethodType;
import ms.lang.types.Mod;
import ms.lang.types.TranslatedType;
import ms.lang.types.Type;
import ms.lang.types.TypeName;
import ms.lang.types.VarModifier;

public class JType extends TranslatedType {
    private static final Logger logger = LogManager.getLogger();

    private Class<?> jClass;

    public JType(IType declaration) {
        super(declaration, declaration.getSource());
        this.jClass = null;
    }

    public JType(Class<?> jClass) {
        this(new Type(fromClass(jClass), JAVA, fromClass(getBestBase(jClass))));
        setCompiled(jClass);
    }

    public Class<?> getCompiled() {
        return jClass;
    }

    public void setCompiled(Class<?> jClass) {
        if (this.jClass != null) {
            throw new IllegalArgumentException("Java compiled class is already set for '"
                                               + getFullName() + "'");
        }
        if (!jClass.getCanonicalName().equals(getFullName())) {
            throw new IllegalArgumentException("Java Class' canonical name '"
                                               + jClass.getCanonicalName()
                                               + "' is not equal to the type's full name '"
                                               + getFullName() + "'");
        }
        if (!jClass.isPrimitive()) {
            updateType(jClass);
        }
        this.jClass = jClass;
    }

    @Override
    public boolean isAssignableFrom(IType type) {
        if (jClass == null) {
            return false;
        }
        Class<?> targetClass = null;
        if (type instanceof JType) {
            targetClass = ((JType) type).getCompiled();
        }
        return targetClass == null ? false : jClass.isAssignableFrom(targetClass);
    }

    @Override
    public Instance<Object> getTypeRef() {
        Class<?> clazz = getCompiled();
        return clazz == null ? null : new JavaRef(clazz);
    }

    private void updateType(Class<?> source) {
        try {
            setAbstract(Modifier.isAbstract(source.getModifiers()) || source.isInterface());

            for (Field f : source.getDeclaredFields()) {
                if (f.isSynthetic()) {
                    continue;
                }
                Mod m = JavaRefHelper.modifierFromJava(f.getModifiers());
                VarModifier vm = isFinal(f.getModifiers()) ? FINAL : STANDARD;
                boolean isStatic = isStatic(f.getModifiers());
                TypeName tn = new TypeName(source.getCanonicalName(), false);
                ClassVariable cv = new ClassVariable(m, vm, isStatic, tn, f.getName(), null);
                addVariable(cv, (n, o) -> n.setExpression(o.getExpression()));
            }

            for (Method m : source.getDeclaredMethods()) {
                if (m.isSynthetic()) {
                    continue;
                }
                BaseMethod m2 = JavaRefHelper.methodFromJava(m.getName(),
                                                             m.getModifiers(),
                                                             m.getParameters(),
                                                             m.getGenericParameterTypes(),
                                                             m.getReturnType());
                JavaMethod m3 = new JavaMethod(m2, m);
                addMethod(m3, BaseMethod::updateMeta);
            }

            // there is at least one method that hasn't been augmented
            List<BaseMethod> notUpdated = list(methods(me -> !(me instanceof JavaMethod)));
            if (!notUpdated.isEmpty()) {
                String message = "Following methods have not been found in the Java-Class '"
                                 + source.getCanonicalName() + "': "
                                 + map(notUpdated, BaseMethod::getSignature);
                logger.warn(message);
                throw new IllegalArgumentException(message);
            }

            JavaConstructor stdConstr = null;
            for (Constructor<?> javaC : source.getDeclaredConstructors()) {
                if (javaC.isSynthetic()) {
                    continue;
                }
                BaseMethod baseM = JavaRefHelper.methodFromJava(source.getSimpleName(),
                                                                javaC.getModifiers(),
                                                                javaC.getParameters(),
                                                                javaC.getGenericParameterTypes(),
                                                                source);
                baseM.setMethodType(MethodType.CONSTRUCTOR);
                JavaConstructor constr = new JavaConstructor(baseM, javaC);
                if (stdConstr == null
                    || baseM.getArguments().size() > stdConstr.getArguments().size()) {
                    stdConstr = constr;
                }
                // NOTE: c3's type may be upgraded to STD_CONSTRUCTOR here, if
                // there is a predefined standard constructor in the given type
                addConstructor(constr, BaseMethod::updateMeta);
            }
            // if there is no predefined standard constructor, we define the
            // standard constructor as the one with the highest nr of arguments
            // NOTE: This is only true for non-abstract types
            if (getStdConstructor() == null) {
                if (stdConstr == null && !isAbstract()) {
                    throw new IllegalArgumentException("Class '" + source.getSimpleName()
                                                       + "' has no constructors");
                } else if (stdConstr != null) {
                    stdConstr.setMethodType(MethodType.STD_CONSTRUCTOR);
                }
            }
            // there is at least one method that hasn't been augmented
            notUpdated = list(constructors(me -> !(me instanceof JavaConstructor)));
            if (!notUpdated.isEmpty()) {
                throw new IllegalArgumentException("Following constructors have not been found in the Java-Class '"
                                                   + source.getCanonicalName() + "': "
                                                   + notUpdated);
            }
        } catch (NoClassDefFoundError e) {
            // fields or methods use classes in their definitions which are
            // missing on the classpath. Such classes are not interesting for
            // us, we obviously don't use them anyway.
            return;
        }
    }

    private static Class<?> getBestBase(Class<?> jClass) {
        if (jClass.getSuperclass() != null) {
            return jClass.getSuperclass();
        }
        if (jClass.getInterfaces() != null && jClass.getInterfaces().length != 0) {
            return jClass.getInterfaces()[0];
        }
        return null;
    }

}
