package net.thartm.cq.cqshell.api;

import java.util.Collections;
import java.util.Set;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class Expectation {

    private String name;
    private Set<String> options;
    private Set<String> validationPattern;
    private boolean mandatory;

    public Expectation(final String name, final Set<String> options, final Set<String> validationPattern, final boolean mandatory) {
        this.name = name;
        this.options = options;
        this.validationPattern = validationPattern;
        this.mandatory = mandatory;
    }

    public Expectation(final String name, final Set<String> options, final Set<String> validationPattern) {
        this.name = name;
        this.options = options;
        this.validationPattern = validationPattern;
        this.mandatory = false;
    }

    public String getName() {
        return name;
    }

    public boolean verifiesOptions() {
        return this.options.size() > 0;
    }

    public Set<String> getOptions() {
        return options;
    }

    public Set<String> getValidationPattern() {
        return validationPattern;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public static Expectation create(final String name, final boolean mandatory) {
        return new Expectation(name, Collections.EMPTY_SET, Collections.EMPTY_SET, mandatory);

    }
}
