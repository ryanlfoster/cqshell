package net.thartm.cq.cqshell.impl.factory;

import com.google.common.base.Optional;
import net.thartm.cq.cqshell.action.ShellAction;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class MethodActionFactoryImpl implements MethodActionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MethodActionFactoryImpl.class);

    @Reference(referenceInterface = ShellAction.class,
            policy = ReferencePolicy.DYNAMIC,
            bind = "bindService",
            unbind = "unbindService",
            cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE)
    private ConcurrentMap<String, ShellAction> actions = new ConcurrentSkipListMap<String, ShellAction>();

    protected void bindService(final ShellAction service) {
        actions.put(service.getName(), service);
        LOG.debug("Registered MethodProcessor for container ID [{}]", service.getName());
    }

    protected void unbindService(final ShellAction service) {
        actions.remove(service.getName());
        LOG.debug("Removed MethodProcessor for container ID [{}]", service.getName());
    }

    public Optional<ShellAction> findAction(final String methodName) {
        if (actions.containsKey(methodName)) {
            return Optional.of(actions.get(methodName));
        }
        return Optional.absent();
    }
}
