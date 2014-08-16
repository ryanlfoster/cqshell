package net.thartm.cq.cqshell.impl.action.basic;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.impl.action.AbstractShellAction;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */
@Service
@Component
public class CDAction extends AbstractShellAction {

    private static final String NAME = "cd";

    public static final Map<String, Expectation> expectations = new HashMap<String, Expectation>();

    static {
        expectations.put("..", Expectation.create("..", false));
        expectations.put("..", Expectation.create(".", false));
        // add relative and absolute paths
    }

    public static final String ARG_BACK = "..";
    public static final String ARG_HERE = ".";
    public static final String ARG_ABSOLUTE = "/";

    @Override
    protected Map<String, Expectation> getExpectations() {
        return DEFAULT_EXPECTATIONS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isInvokable() {
        return true;
    }

    @Override
    public ActionResponse execute(final Session session, final ExecutionContext context, final List<Parameter> parameters)
            throws RepositoryException {
        return super.execute(session, context, parameters.toArray(new Parameter[parameters.size()]));
    }

    @Override
    protected ActionResponse invokeMethod(final Session session, final ExecutionContext context, final Map<String, Parameter> arguments)
            throws RepositoryException {
        final String path = StringUtils.isNotBlank(context.getPath()) ? context.getPath() : "/";

        if (arguments.size() == 1) {
            final Node currentNode = session.getNode(path);
            if (currentNode != null) {
                final Parameter<String> param = arguments.entrySet().iterator().next().getValue();

                // check if argument points to current location
                if (ARG_HERE.equalsIgnoreCase(param.getValue())) {
                    ActionResponse.success(currentNode.getPath(), currentNode.getPath());
                }

                // check if argument points to parent location
                if (ARG_BACK.equalsIgnoreCase(param.getValue())) {
                    return changeToParent(currentNode, param.getValue());
                }

                // check if argument points to absolute location
                if (param.getValue().startsWith(ARG_ABSOLUTE)) {
                    return changeToAbsolute(session, context, param.getValue());
                }

                // check if argument points to relative location
                return changeToRelative(currentNode, context, param.getValue());
            }
        }
        return ActionResponse.error("Illegal arguments", context.getPath());
    }

    private ActionResponse changeToAbsolute(final Session session, final ExecutionContext context, final String path) throws RepositoryException {
        final Node node = session.getNode(path);
        return node != null ? ActionResponse.success(node.getPath(), node.getPath())
                : ActionResponse.error("Path " + path + " does not exist", context.getPath());
    }

    private ActionResponse changeToRelative(final Node node, final ExecutionContext context, final String path) throws RepositoryException {
        if (StringUtils.isNotBlank(path)) {
            final Node subNode = node.getNode(path);
            return node != null ? ActionResponse.success(subNode.getPath(), subNode.getPath())
                    : ActionResponse.error("Path " + path + " does not exist", context.getPath());
        }
        return ActionResponse.error("Path argument is empty.", context.getPath());
    }

    private ActionResponse changeToParent(final Node currentNode, final String argument) throws RepositoryException {
        final Node parentNode = currentNode.getParent();
        return parentNode != null ? ActionResponse.success(parentNode.getPath(), parentNode.getPath()) : ActionResponse.success(currentNode.getPath(),
                currentNode.getPath());
    }

    @Override
    public boolean isValid(final Parameter... parameters) {
        return false;
    }

    @Override
    public List<Parameter> getParameterInformation() {
        return null;
    }
}
