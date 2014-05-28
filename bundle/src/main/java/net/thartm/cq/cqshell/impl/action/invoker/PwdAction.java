package net.thartm.cq.cqshell.impl.action.invoker;

import net.thartm.cq.cqshell.api.Expectation;
import net.thartm.cq.cqshell.api.Parameter;
import net.thartm.cq.cqshell.api.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@Service
@Component
public class PwdAction extends AbstractShellAction {

    private static final String NAME = "pwd";

    private static final String PATH_ARG = "path";

    private static final Map<String, Expectation> expectations = new HashMap<String, Expectation>();
    static {
        expectations.put(PATH_ARG, Expectation.create(PATH_ARG, true));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected Map<String, Expectation> getExpectations() {
        return expectations;
    }

    @Override
    protected Result invokeMethod(final Map<String, Parameter> arguments) {
        final Parameter pathParam = arguments.get("path");
        final String path = (String) pathParam.getValue();
        if(StringUtils.isNotBlank(path)){

        }

        return null;
    }

    @Override
    public boolean isValid(final Parameter... parameters) {
        return false;
    }

    @Override
    public List<Parameter> getParameterInformation() {
        return Collections.EMPTY_LIST;
    }

}
