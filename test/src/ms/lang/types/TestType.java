package ms.lang.types;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import ms.ipp.iterable.tree.path.StdPathManipulator;
import ms.lang.Language;

public class TestType {

	@Test
	public void testTypeName() {
		String name = "Test", fullName = "de.ms.Test", base = "de.BaseTest";
		Integer language = Language.JAVA;
		Type type = new Type(new TypeName(name, fullName, false), language,
				new TypeName(StdPathManipulator.toSimpleName(base), base, false));
		Assert.assertEquals("de.ms.Test", type.getType().getFullName());
		Assert.assertEquals("Test", type.getType().getName());
		Assert.assertEquals("de.ms", type.getPackage());
		Assert.assertEquals("de.BaseTest", type.getBase().getFullName());
		Assert.assertEquals("BaseTest", type.getBase().getName());
	}
}
