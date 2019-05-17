package ms.lang.types;

import static ms.ipp.Iterables.appendList;

import java.util.ArrayList;
import java.util.List;

public class TypeName {
	private final String name;
	private final List<TypeName> templateArgs;
	private boolean isTemplate;

	private String fullName;

	public TypeName(TypeName source) {
		this(source.name, source.isTemplate);
		this.setFullName(source.fullName);
		if (source.isTemplate()) {
			for (TypeName t : source.templateArgs) {
				addTemplateArg(new TypeName(t));
			}
		}
	}

	public TypeName(String name, boolean isTemplate) {
		this(name, name, isTemplate);
	}

	public TypeName(String name, String fullName, boolean isTemplate) {
		templateArgs = new ArrayList<>();
		this.isTemplate = isTemplate;
		this.name = name;
		this.setFullName(fullName);
	}

	public TypeName getTemplateArg(int index) {
		return templateArgs.get(index);
	}

	public void addTemplateArg(TypeName arg) {
		templateArgs.add(arg);
		this.isTemplate = true;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public boolean isTemplate() {
		return isTemplate;
	}

	public boolean isSimple() {
		return !name.contains(".");
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TypeName)) {
			return false;
		}
		TypeName src = (TypeName) obj;
		return name.equals(src.name) && isTemplate == src.isTemplate && templateArgs.equals(src.templateArgs);
	}

	public String serialize() {
		StringBuffer sb = new StringBuffer(100);
		serialize(sb);
		return sb.toString();
	}

	private void serialize(StringBuffer sb) {
		sb.append(name);
		if (isTemplate()) {
			appendList(sb, templateArgs, "<", ">", ", ", (t, s) -> t.serialize(s));
		}
	}

	@Override
	public String toString() {
		return serialize();
	}

	public String getImport() {
		return fullName.equals(name) ? null : fullName;
	}

	public void addImports(List<String> imports) {
		String imp = getImport();
		if (imp != null && !imports.contains(imp)) {
			imports.add(imp);
		}
		if (isTemplate()) {
			for (TypeName a : templateArgs) {
				a.addImports(imports);
			}
		}
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
