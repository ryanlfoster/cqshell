package net.thartm.cq.cqshell.impl.action;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public abstract class AbstractShellAction implements ShellAction {

    public static final Map<String, Expectation> DEFAULT_EXPECTATIONS = new HashMap<String, Expectation>();
    static {
    }

    @Override
    public ActionResponse execute(final Session session, final ExecutionContext context, final Parameter... parameters) throws RepositoryException {
        final Map<String, Parameter> paramMap = createParameterMap(parameters);

/*        final Map<String, Parameter> invocationArguments = Maps.newHashMap();
        for (final Expectation expectation : getExpectations().values()) {
            // guard method invokation
            if (isMissingMandatory(expectation, paramMap)) {
                // TODO return MissingArgumentResult
            }

            if (isInvalid(expectation, paramMap)) {

            }

            final Parameter parameter = paramMap.get(expectation.getName());
            if (parameter != null) {
                invocationArguments.put(parameter.getName(), parameter);
            }
        }*/

        return invokeMethod(session, context, paramMap);
    }

    protected abstract Map<String, Expectation> getExpectations();

    protected abstract ActionResponse invokeMethod(final Session session, final ExecutionContext context, final Map<String, Parameter> arguments)
            throws RepositoryException;

    private ImmutableMap<String, Parameter> createParameterMap(Parameter[] parameters) {
        return Maps.uniqueIndex(Arrays.asList(parameters), new Function<Parameter, String>() {
            public String apply(final Parameter parameter) {
                return parameter.getName();
            }
        });
    }

    private boolean isMissingMandatory(final Expectation expectation, final Map<String, Parameter> paramMap) {
        return expectation.isMandatory() && !paramMap.containsKey(expectation.getName());
    }

    private boolean isInvalid(final Expectation expectation, final Map<String, Parameter> paramMap) {
        final Parameter parameter = paramMap.get(expectation.getName());

        if (expectation.requiresOptionVerification()) {
            return !expectation.optionsMatch(parameter);
        }

        // TODO apply the evaluation patterns
        return true;
    }

    private boolean optionsValid(Expectation expectation, Parameter param) {
        for (final String option : expectation.getOptions()) {

            if (param.getValue() instanceof String) {
                if (StringUtils.equals(option, (String) param.getValue())) {
                    return true;
                }
            } else {
                // TODO illegal parameter value
            }
        }
        return false;
    }
}
