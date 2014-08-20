package net.thartm.cq.cqshell.server;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.discovery.TopologyEvent;
import org.apache.sling.discovery.TopologyEventListener;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;

import java.util.Map;

/** Provides access to the scr managed sling services. This is a workaround as directly referencing all require slingServices from the CommandHandler fails
 * because of a JDK 7 issue.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 08/2014 */
@Service(value = SlingServiceProxy.class)
@Component(immediate = true)
public class SlingServiceProxy implements TopologyEventListener {

    private static final String REPO_DESC_ID = "crx.repository.systemid";
    private static final String REPO_DESC_CLUSTER_ID = "crx.cluster.id";

    @Reference
    private SlingRepository repository;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private SlingSettingsService settings;

    private String slingId;

    private volatile String clusterId;

    private CharSequence repositoryId;

    @Activate
    private void activate(final Map<String, Object> configuration) {
        this.slingId = this.settings.getSlingId();
    }

    public SlingRepository getRepository() {
        return repository;
    }

    public ResourceResolverFactory getResolverFactory() {
        return resolverFactory;
    }

    public SlingSettingsService getSettings() {
        return settings;
    }

    public String getSlingId() {
        return slingId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public String getRepositoryId() {
        this.repositoryId = repository.getDescriptor(REPO_DESC_CLUSTER_ID);

        if (StringUtils.isBlank(this.repositoryId)) {
            this.repositoryId = repository.getDescriptor(REPO_DESC_ID);
        }

        if (StringUtils.isBlank(this.repositoryId)) {
            this.repositoryId = this.clusterId;
        }
        return this.repositoryId != null ? (String) repositoryId : slingId;
    }

    @Override
    public void handleTopologyEvent(final TopologyEvent event) {
        if ((event.getType() == TopologyEvent.Type.TOPOLOGY_CHANGED) || (event.getType() == TopologyEvent.Type.TOPOLOGY_INIT))
        {
            this.clusterId = event.getNewView().getLocalInstance().getClusterView().getId();
        }
    }
}
