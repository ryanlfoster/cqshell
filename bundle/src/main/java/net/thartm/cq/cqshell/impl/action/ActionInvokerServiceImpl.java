package net.thartm.cq.cqshell.impl.action;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.thartm.cq.cqshell.action.ActionResponse;
import net.thartm.cq.cqshell.action.RpcCall;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.impl.factory.MethodActionFactory;
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

    public Optional<ActionResponse> invoke(final Session session, final RpcCall rpcCall) {

        final String method = rpcCall.getMethod();
        if (StringUtils.isNotBlank(method)) {
            final Optional<ShellAction> actionOptional = actionFactory.findAction(method);
            if (actionOptional.isPresent()) {
                final ShellAction action = actionOptional.get();
                return invokeAction(session, rpcCall, action);
            } else {
                // TODO called a non existing method
            }
        }

        return Optional.absent();
    }

    private Optional<ActionResponse> invokeAction(final Session session, final RpcCall rpcCall, final ShellAction action) {

        final List<Parameter> params = createParameters(rpcCall);
        final ActionResponse response = action.execute(session, params);
        return Optional.fromNullable(response);
    }

    private List<Parameter> createParameters(final RpcCall call) {
        final List<Parameter> parameters = Lists.newArrayList();
        for (Object p : call.getParams()) {
            Parameter.
        }
        return parameters;
    }

}
