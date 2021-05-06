package ms.utils;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.map;
import static ms.ipp.base.KeyValue.KVP;
import static ms.utils.StringHelper.NON_QUOTED_COMMA;
import static ms.utils.StringHelper.splitTrim;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import ms.ipp.base.KeyValue;

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

    @TestFactory
    public List<DynamicTest> testSplitNonQuotedCommas() {
        return map(splitCases,
                   p -> dynamicTest("test '" + p.getKey() + "'=>" + p.getValue(),
                                    () -> assertEquals(p.getValue(),
                                                       splitTrim(p.getKey(), NON_QUOTED_COMMA))));
    }

    private static List<KeyValue<String, List<String>>> splitCases //
            = asList(KVP("", asList()), // empty
                     KVP(" ", asList()), // blank
                     KVP("a,b", asList("a", "b")), // standard
                     KVP("a,\"b,c\"", asList("a", "\"b,c\"")), // quotes
                     KVP("a,", asList("a", "")), // empty field
                     KVP("a,,", asList("a", "", "")), // two empty fields (end)
                     KVP(",,a", asList("", "", "a")), // two empty fields (end)
                     KVP("a,\"\"", asList("a", "\"\"")), // empty quoted field
                     KVP("a,\",\"", asList("a", "\",\"")) // single quoted comma
            );
}
