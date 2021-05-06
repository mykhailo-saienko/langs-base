package ms.utils;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestExecutor {

    @Test
    public void testExecutorRun() {
        List<String> attempts = new ArrayList<>();
        Runnable r = () -> {
            attempts.add("");
            if (attempts.size() < 3) {
                throw new IllegalArgumentException();
            }
        };

        var nonFatals = asList(IllegalArgumentException.class);
        Assert.assertFalse(//
                           new Executor().setNonFatals(nonFatals).run(r, "Test", 2));
        Assert.assertEquals(2, attempts.size());
        attempts.clear();

        Assert.assertTrue(new Executor().setNonFatals(nonFatals).run(r, "Test", 3));
        Assert.assertEquals(3, attempts.size());
    }
}
