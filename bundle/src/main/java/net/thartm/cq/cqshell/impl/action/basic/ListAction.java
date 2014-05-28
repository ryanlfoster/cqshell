package net.thartm.cq.cqshell.impl.action.basic;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import net.thartm.cq.cqshell.impl.action.AbstractShellAction;

import java.util.List;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class ListAction extends AbstractShellAction {


    @Override
    protected Map<String, Expectation> getExpectations() {
        return null;
    }

    @Override
    protected ActionResponse invokeMethod(Map<String, Parameter> arguments) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isValid(Parameter... parameters) {
        return false;
    }

    @Override
    public List<Parameter> getParameterInformation() {
        return null;
    }
}
