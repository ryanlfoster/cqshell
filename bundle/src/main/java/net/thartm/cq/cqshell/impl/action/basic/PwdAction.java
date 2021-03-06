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
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@Service
@Component
public class PwdAction extends AbstractShellAction {

    private static final String NAME = "pwd";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isInvokable() {
        return true;
    }

    @Override
    protected Map<String, Expectation> getExpectations() {
        return DEFAULT_EXPECTATIONS;
    }

    @Override
    protected ActionResponse invokeMethod(final ResourceResolver resolver, final ExecutionContext context, final Map<String, Parameter> parameters)
            throws RepositoryException {
        final String path = StringUtils.isNotBlank(context.getPath()) ? context.getPath() : "/";
        final Resource resource = resolver.getResource(path);

        if (resource != null) {
            return ActionResponse.success(resource.getPath(), resource.getPath());
        }
        return ActionResponse.error("Unknown path", path);
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
