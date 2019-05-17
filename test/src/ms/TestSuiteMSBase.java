package ms;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import ms.lang.types.TestType;

@RunWith(JUnitPlatform.class)
@SelectClasses({ TestType.class, })
public class TestSuiteMSBase {

}
