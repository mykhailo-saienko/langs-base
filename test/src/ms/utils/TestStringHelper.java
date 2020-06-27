package ms.utils;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestStringHelper {

	@Test
	public void testCutoff() {
		String source = "bla";
		for (int i = 0; i < 5; ++i) {
			Assert.assertEquals(source, StringHelper.cutoff(source, i));
		}

		source = " bla";
		Assert.assertEquals(source, StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}

		source = "bla ";
		Assert.assertEquals(source, StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}

		source = " bla ";
		Assert.assertEquals(source, StringHelper.cutoff(source, 5));
		Assert.assertEquals("bla ", StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}

		source = "  bla ";
		Assert.assertEquals(source, StringHelper.cutoff(source, 6));
		Assert.assertEquals(" bla ", StringHelper.cutoff(source, 5));
		Assert.assertEquals("bla ", StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}

		source = "  bla";
		Assert.assertEquals(source, StringHelper.cutoff(source, 5));
		Assert.assertEquals(" bla", StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}

		source = "bla  ";
		Assert.assertEquals(source, StringHelper.cutoff(source, 5));
		Assert.assertEquals("bla ", StringHelper.cutoff(source, 4));
		for (int i = 0; i < 4; ++i) {
			Assert.assertEquals("bla", StringHelper.cutoff(source, i));
		}
	}
}
