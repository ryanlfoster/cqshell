package net.thartm.cq.cqshell.action;

import net.thartm.cq.cqshell.method.Parameter;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

/** ShellAction is a command invoked with a defined set of parameters. <br />
 * Each action know about it's parameter expectations and is able to validate the input.
 * 
 * Any ShellAction implementation must be stateless as it is a Service.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public interface ShellAction {

    /** The name is a shell action's unique identifier. Any action is called by it's name.
     * 
     * @return String representation of the name. */
    String getName();

    /** Can be invoked.
     * 
     * @return True if it can be invoked. */
    boolean isInvokable();

    /** Executes the ShellAction using the given session object and parameters.
     * 
     * @param resolver
     * @param context
     * @param parameters
     * @return Result of execution. */
    ActionResponse execute(final ResourceResolver resolver, final ExecutionContext context, final Parameter... parameters) throws RepositoryException;

    /** Executes the ShellAction using the given session object and parameters.
     * 
     * @param resolver
     * @param context
     * @param parameters
     * @return Result of execution. */
    ActionResponse execute(final ResourceResolver resolver, final ExecutionContext context, final List<Parameter> parameters) throws RepositoryException;

    /** Verifies if it is possible to run the action with the given set of parameters.
     * 
     * @param parameters
     * @return */
    boolean isValid(final Parameter... parameters);

    List<Parameter> getParameterInformation();
}
