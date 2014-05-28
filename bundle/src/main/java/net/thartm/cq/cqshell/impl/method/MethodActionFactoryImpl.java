package net.thartm.cq.cqshell.impl.method;

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

    @Reference(referenceInterface = MethodAction.class,
            policy = ReferencePolicy.DYNAMIC,
            bind = "bindService",
            unbind = "unbindService",
            cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE)
    private ConcurrentMap<String, MethodAction> processorServices = new ConcurrentSkipListMap<String, MethodAction>();

    protected void bindService(final MethodAction service) {
        processorServices.put(service.getKey(), service);
        LOG.debug("Registered MethodProcessor for container ID [{}]", service.getKey());
    }

    protected void unbindService(final MethodAction service) {
        processorServices.remove(service.getKey());
        LOG.debug("Removed MethodProcessor for container ID [{}]", service.getKey());
    }

    public MethodAction findProcessor(final String methodName){

        return null;
    }
}
