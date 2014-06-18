package net.thartm.cq.cqshell.impl.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/** Shell command sentence consisting of an action and a list of arguments.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class Sentence {

    private String action;

    private List<Argument> arguments = Lists.newArrayList();

    private Operator leadingOperator = Operator.NONE;

    private int position = 0;

    public Sentence(final String action, final List<Argument> arguments) {
        this.action = action;
        this.arguments = arguments;
    }

    public Sentence(final String action, final Argument... arguments) {
        this.action = action;
        this.arguments = Arrays.asList(arguments);
    }

    public Sentence(final String action) {
        this.action = action;
    }

    public Sentence() {
    }

    public boolean isPrimarySentence() {
        return position == 0;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public Operator getLeadingOperator() {
        return leadingOperator;
    }

    public void setLeadingOperator(Operator leadingOperator) {
        this.leadingOperator = leadingOperator;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (leadingOperator != Operator.NONE) {
            sb.append(leadingOperator.get());
        }
        sb.append(this.getAction()).append(" ");
        for (final Argument arg : arguments) {
            appendNotBlank(sb, arg.getKey());
            if (arg.getValue() != null) {
                if (arg.getClass().equals(String.class)) {
                    appendNotBlank(sb, (String) arg.getValue());
                } else {
                    appendNotBlank(sb, arg.getValue().toString());
                }
            }
        }
        return sb.toString().trim();
    }

    private void appendNotBlank(final StringBuilder sb, final String value) {
        if (StringUtils.isNotBlank(value)) {
            sb.append(value);
            sb.append(" ");
        }
    }

    public boolean containsAction() {
        return StringUtils.isNotBlank(action);
    }

    public void addArgument(final Argument argument) {
        arguments.add(argument);
    }
}
