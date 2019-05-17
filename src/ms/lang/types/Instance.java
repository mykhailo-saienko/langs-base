package ms.lang.types;

public interface Instance extends Ref<Object> {
	// TODO: Maybe IType getType(). By the time, a Ref is created, its type
	// must exist, too. But, it is time-consuming to create Types, which is
	// important, since Refs are created recursively in JavaRef/PythonRef
	TypeName getTypeName();
}
