package ms.gui;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import ms.gui.comp.GUIHelper;

public class TestGUIHelper {

	@Test
	public void testExecute() {
		List<String> attempts = new ArrayList<>();
		Runnable r = () -> {
			attempts.add("");
			if (attempts.size() < 3) {
				throw new IllegalArgumentException();
			}
		};

		Assert.assertFalse(GUIHelper.execute(r, "Test", 2, i -> i));
		Assert.assertEquals(2, attempts.size());
		attempts.clear();

		Assert.assertTrue(GUIHelper.execute(r, "Test", 3, i -> i));
		Assert.assertEquals(3, attempts.size());
	}
}
