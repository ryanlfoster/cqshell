package net.thartm.cq.cqshell.method;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ExpectationTest {

    private Expectation simpleExpectationWithOptions;
    private Expectation simpleExpectationWithoutOptions;

    @Before
    public void setUp() throws Exception {
        simpleExpectationWithOptions = new Expectation("withOptions");
        simpleExpectationWithOptions.setValidationType(ValidationType.NONE);
        final Set<String> options = new HashSet<>();
        options.add("option1");
        options.add("test");
        simpleExpectationWithOptions.setOptions(options);

        simpleExpectationWithoutOptions = new Expectation("withoutOptions");
        simpleExpectationWithoutOptions.setValidationType(ValidationType.NONE);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRequiresOptionVerification() throws Exception {
        Assert.assertTrue(simpleExpectationWithOptions.requiresOptionVerification());
        Assert.assertFalse(simpleExpectationWithoutOptions.requiresOptionVerification());
    }

    @Test
    public void testOptionsMatch() throws Exception {
        final Parameter matchingParameter = new Parameter("key", "test");
        Assert.assertTrue(simpleExpectationWithOptions.optionsMatch(matchingParameter));

        final Parameter nonMatchingParameter = new Parameter("key", "xxxx");
        Assert.assertFalse(simpleExpectationWithOptions.optionsMatch(nonMatchingParameter));
    }

    @Test
    public void testCreate() throws Exception {
        final Expectation expectation = Expectation.create("testParameter", true);

        Assert.assertEquals("testParameter", expectation.getName());
        Assert.assertTrue(expectation.isMandatory());
        Assert.assertEquals(0, expectation.getOptions().size());
        Assert.assertEquals(ValidationType.NONE, expectation.getValidationType());
    }

    @Test
    public void testCreateWithJsonValidation() throws Exception {
        final Expectation expectation = Expectation.createWithJsonValidation("testParameter", true);

        Assert.assertEquals("testParameter", expectation.getName());
        Assert.assertTrue(expectation.isMandatory());
        Assert.assertEquals(0, expectation.getOptions().size());
        Assert.assertEquals(ValidationType.JSON, expectation.getValidationType());
    }

    @Test
    public void testCreateWithRegexValidation() throws Exception {
        final String[] patterns = new String[] { "\\S", "\\s" };
        final Expectation expectation = Expectation.createWithRegexValidation("testParameter", true, patterns);

        Assert.assertEquals("testParameter", expectation.getName());
        Assert.assertTrue(expectation.isMandatory());
        Assert.assertEquals(0, expectation.getOptions().size());
        Assert.assertEquals(ValidationType.REGEX, expectation.getValidationType());

        for (final String pattern : patterns) {
            Assert.assertTrue(expectation.getValidationPatterns().contains(pattern));
        }
    }
}
