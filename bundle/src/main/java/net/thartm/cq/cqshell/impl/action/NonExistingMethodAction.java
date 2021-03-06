package net.thartm.cq.cqshell.impl.action;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Session;
import java.util.List;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class NonExistingMethodAction implements ShellAction {

    private static final String KEY = "NON_EXISTING_METHOD";

    @Override
    public String getName() {
        return KEY;
    }

    @Override
    public boolean isInvokable() {
        return false;
    }

    @Override
    public ActionResponse execute(ResourceResolver resourceResolver, final ExecutionContext context, final Parameter... parameters) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override public ActionResponse execute(ResourceResolver resourceResolver, final ExecutionContext context, final List<Parameter> parameters) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public boolean isValid(Parameter... parameters) {
        throw new NoSuchMethodError("Not implemented");
    }

    @Override
    public List<Parameter> getParameterInformation() {
        throw new NoSuchMethodError("Not implemented");
    }
}
