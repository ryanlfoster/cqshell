package net.thartm.cq.cqshell.impl.action;

import com.google.common.base.Optional;
import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.JsonRpcCall;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.impl.evaluation.MethodActionFactory;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.Session;
import java.util.List;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@Service
@Component
public class ActionInvokerServiceImpl {

    @Reference
    private MethodActionFactory actionFactory;

    public Optional<ActionResponse> invoke(final Session session, final JsonRpcCall jsonRpcCall) {

        final String method = jsonRpcCall.getMethod();
        if (StringUtils.isNotBlank(method)) {
            final Optional<ShellAction> actionOptional = actionFactory.findAction(method);
            if (actionOptional.isPresent()) {
                final ShellAction action = actionOptional.get();
                return invokeAction(session, jsonRpcCall, action);
            } else {
                // TODO called a non existing method
            }
        }

        return Optional.absent();
    }

    private Optional<ActionResponse> invokeAction(final Session session, final JsonRpcCall jsonRpcCall, final ShellAction action) {

        final List<Parameter> params = createParameters(jsonRpcCall);
        final ActionResponse response = action.execute(session, params);
        return Optional.fromNullable(response);
    }

    private List<Parameter> createParameters(final JsonRpcCall call) {
        return null;
    }

}
