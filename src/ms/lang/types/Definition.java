package ms.lang.types;

import java.util.Random;

public interface Definition {
	// TODO: These methods are supposedly used in some JUnit-Tests. Obviously,
	// we need a specialized test class for this stuff
	static int TEST = new Random().nextInt(100);

	static void dummy() {

	}

	String getName();
}
