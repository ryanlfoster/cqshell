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
public class MethodFactoryImpl implements MethodFactory{

    private static final Logger LOG = LoggerFactory.getLogger(MethodFactoryImpl.class);

    @Reference(referenceInterface = MethodProcessor.class,
            policy = ReferencePolicy.DYNAMIC,
            bind = "bindService",
            unbind = "unbindService",
            cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE)
    private ConcurrentMap<String, MethodProcessor> processorServices = new ConcurrentSkipListMap<String, MethodProcessor>();

    protected void bindService(final MethodProcessor service) {
        processorServices.put(service.getKey(), service);
        LOG.debug("Registered MethodProcessor for container ID [{}]", service.getKey());
    }

    protected void unbindService(final MethodProcessor service) {
        processorServices.remove(service.getKey());
        LOG.debug("Removed MethodProcessor for container ID [{}]", service.getKey());
    }

    public MethodProcessor findProcessor(final String methodName){

        return null;
    }
}
