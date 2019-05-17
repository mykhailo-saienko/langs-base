package ms.lang.types;

public class BaseConstructor extends BaseMethod {

	public BaseConstructor(BaseMethod source) {
		super(source);
	}

	public BaseConstructor(Mod modifier, TypeName type, String name, String body) {
		super(modifier, false, type, name, body);
	}
}
