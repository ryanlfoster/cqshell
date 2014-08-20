package net.thartm.cq.cqshell.impl.action.basic;

import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.ExecutionContext;
import net.thartm.cq.cqshell.method.Expectation;
import net.thartm.cq.cqshell.method.Parameter;
import net.thartm.cq.cqshell.impl.action.AbstractShellAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.*;
import java.util.*;

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
    protected ActionResponse invokeMethod(final ResourceResolver resourceResolver, final ExecutionContext context, final Map<String, Parameter> arguments)
            throws RepositoryException {
        final String path = StringUtils.isNotBlank(context.getPath()) ? context.getPath() : "/";

        final Resource resource = resourceResolver.getResource(path);

        if (resource != null) {
            final StringBuilder resultBuilder = new StringBuilder();
            final Iterator<Resource> it = resource.listChildren();

            while (it.hasNext()) {
                final Resource child = it.next();

                final Node node = child.adaptTo(Node.class);
                final String primaryType = node != null ? node.getPrimaryNodeType().toString() : "";
                final String entry = String.format(TEMPLATE, primaryType, child.getName());
                resultBuilder.append(entry);
            }
            return ActionResponse.success(resultBuilder.toString(), resource.getPath());
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
