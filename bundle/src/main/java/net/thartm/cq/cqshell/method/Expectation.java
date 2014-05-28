package net.thartm.cq.cqshell.method;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Expectation for a parameter. Each Action can define a set of expectations which is then used to verify all parameters.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class Expectation {

    private String name;
    private Set<String> options;
    private ValidationType validationType;
    private Set<String> validationPatterns;
    private boolean mandatory;

    protected Expectation(final String name) {
        this.name = name;
    }

    protected Expectation(final String name, final Set<String> options, final boolean mandatory) {
        this.name = name;
        this.options = options;
        this.validationType = ValidationType.NONE;
        this.validationPatterns = Collections.EMPTY_SET;
        this.mandatory = mandatory;
    }

    protected Expectation(final String name, final Set<String> options) {
        this.name = name;
        this.options = options;
        this.validationType = ValidationType.NONE;
        this.validationPatterns = Collections.EMPTY_SET;
        this.mandatory = false;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }

    public void setValidationPatterns(final Set<String> validationPatterns) {
        this.validationPatterns = validationPatterns;
    }

    public void addValidationPatterns(final String... validationPatterns) {
        this.validationPatterns = new HashSet<String>(Arrays.asList(validationPatterns));
    }

    public String getName() {
        return name;
    }

    public boolean requiresOptionVerification() {
        return this.options != null && this.options.size() > 0;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<String> getValidationPatterns() {
        return validationPatterns;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean optionsMatch(final Parameter parameter) {
        if (options != null) {
            for (final String option : options) {
                if (parameter.getValue() instanceof String) {
                    if (StringUtils.equals(option, (String) parameter.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Expectation create(final String name, final boolean mandatory) {
        return new Expectation(name, Collections.EMPTY_SET, mandatory);
    }

    public static Expectation createWithJsonValidation(final String name, final boolean mandatory) {
        final Expectation expectation = new Expectation(name, Collections.EMPTY_SET, mandatory);
        expectation.setValidationType(ValidationType.JSON);
        return expectation;
    }

    public static Expectation createWithRegexValidation(final String name, final boolean mandatory, final String... validationPatterns) {
        final Expectation expectation = new Expectation(name, Collections.EMPTY_SET, mandatory);
        expectation.setValidationType(ValidationType.REGEX);
        expectation.addValidationPatterns(validationPatterns);
        return expectation;
    }
}
