package net.thartm.cq.cqshell.impl.action.replication;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.impl.action.AbstractShellAction;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */
@Service
@Component
public class ReplicationManagementAction extends AbstractShellAction {

    private static final String NAME = "replication";

    @Override
    protected Map<String, Expectation> getExpectations() {
        return null;
    }

    @Override
    protected ActionResponse invokeMethod(Session session, ExecutionContext context, Map<String, Parameter> arguments)
            throws RepositoryException {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isInvokable() {
        return false;
    }

    @Override
    public ActionResponse execute(Session session, ExecutionContext context, List<Parameter> parameters)
            throws RepositoryException {
        return ActionResponse.error("Not implemented yet.");
    }

    @Override
    public boolean isValid(Parameter... parameters) {
        return false;
    }

    @Override
    public List<Parameter> getParameterInformation() {
        return Collections.EMPTY_LIST;
    }
}
