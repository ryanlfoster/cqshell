package net.thartm.cq.cqshell.server;

import net.thartm.cq.cqshell.impl.evaluation.ExpressionParserService;
import net.thartm.cq.cqshell.impl.evaluation.MethodActionFactory;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;

import java.util.concurrent.Future;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2014
 */
@Component(immediate=true)
public class WebsocketServer {

    @Reference
    private MethodActionFactory actionFactory;

    @Reference
    private ExpressionParserService expressionParser;

    @Reference
    private SlingRepository repository;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private int port = 9999;
    private Future<? extends WebServer> serverFuture;

    @Activate
    protected void activate(ComponentContext ctx) {
        serverFuture = WebServers.createWebServer(port)
                .add("/console", new CommandHandler(repository, expressionParser, actionFactory))
                .start();
        log.info("WebServer started on port {}", port);
    }

    @Deactivate
    protected void deactivate(ComponentContext ctx) throws Exception {
        log.warn("Stopping WebServer");
        serverFuture.get().stop();
        log.warn("WebServer stopped");
    }
}
