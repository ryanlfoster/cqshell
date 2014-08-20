package net.thartm.cq.cqshell.impl.action.basic;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import net.thartm.cq.cqshell.impl.action.AbstractShellAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */

@Service
@Component
public class ListAction extends AbstractShellAction {

    private static final String NAME = "ls";

    private static final String TEMPLATE = "[jcr:primaryType=%s]   %s\n";

    public static final Map<String, Expectation> expectations = new HashMap<String, Expectation>();

    static {
        // add relative and absolute paths
    }

    @Override
    protected Map<String, Expectation> getExpectations() {
        return expectations;
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

        final Node node = session.getNode(path);

        if (node != null) {
            final StringBuilder resultBuilder = new StringBuilder();
            NodeIterator it = node.getNodes();

            while (it.hasNext()) {
                final Node child = it.nextNode();
                final String entry = String.format(TEMPLATE, child.getPrimaryNodeType(), child.getName());
                resultBuilder.append(entry);
            }
            return ActionResponse.success(resultBuilder.toString(), node.getPath());
        }
        return ActionResponse.error("Unable to list contents for ", path);
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
