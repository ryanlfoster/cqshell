package net.thartm.cq.cqshell.server;

import com.day.crx.security.token.TokenCookie;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.thartm.cq.cqshell.action.*;
import net.thartm.cq.cqshell.impl.evaluation.ExpressionParserService;
import net.thartm.cq.cqshell.impl.evaluation.MethodActionFactory;
import net.thartm.cq.cqshell.impl.model.Argument;
import net.thartm.cq.cqshell.impl.model.Sentence;
import net.thartm.cq.cqshell.method.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.authentication.token.TokenCredentials;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.auth.core.AuthenticationSupport;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.HttpRequest;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

/** @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */

public class CommandHandler implements WebSocketHandler, CommandExecutor {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MethodActionFactory actionFactory;
    private final ExpressionParserService expressionParser;
    private SlingServiceProxy serviceProxy;
    private ObjectMapper mapper = new ObjectMapper();

    public CommandHandler(SlingServiceProxy serviceProxy, ExpressionParserService expressionParser, MethodActionFactory actionFactory) {
        this.serviceProxy = serviceProxy;
        this.expressionParser = expressionParser;
        this.actionFactory = actionFactory;
    }

    public void onOpen(WebSocketConnection connection) throws IOException {
        final ActionResponse response = ActionResponse.success("This is " + getClass().getName() + ", please enter you command.", "/");
        send(connection, response);
    }

    public void onClose(WebSocketConnection connection) {
    }

    public void onMessage(final WebSocketConnection connection, final String message) throws IOException {

        ResourceResolver resolver = null;
        try {
            resolver = getResourceResolver(connection);

            if (StringUtils.isNotBlank(message)) {
                final SocketCall call = mapper.readValue(message, SocketCall.class);
                execute(mapper, connection, call, resolver);
            }
        } catch (LoginException e) {
            log.error("Unable to get authenticated resourceResolver " + e, e);
            sendError(connection, "Unable to execute action as authentication failed.");
        } catch (RepositoryException e) {
            log.error("Unable to execute action" + e, e);
            sendError(connection, "Unable to execute action.");
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }

    private ResourceResolver getResourceResolver(final WebSocketConnection connection) throws LoginException {
        final TokenCookie.Info info = getTokenInfo(connection);
        final TokenCredentials credentials = new TokenCredentials(info.token);

        AuthenticationInfo authInfo = new AuthenticationInfo("TOKEN");
        authInfo.put("user.jcr.credentials", credentials);

        // Session s = this.serviceProxy.getRepository().login(credentials, info.workspace);
        return serviceProxy.getResolverFactory().getResourceResolver(authInfo);
    }

    private TokenCookie.Info getTokenInfo(final WebSocketConnection connection) {
        final HttpRequest request = connection.httpRequest();
        final HttpCookie cookie = request.cookie("login-token");

        final TokenCookie tokenCookie = TokenCookie.fromString(cookie.getValue());
        return tokenCookie.getInfos().get(serviceProxy.getRepositoryId());
    }

    private void execute(final ObjectMapper mapper, final WebSocketConnection connection, final SocketCall call, final ResourceResolver resolver)
            throws RepositoryException, IOException {

        final List<Sentence> sentences = expressionParser.parseToSentences(call.getCommand());
        for (final Sentence sentence : sentences) {

            log.debug("Executing sentence [{}]", sentence.toString());

            final String actionName = sentence.getAction();
            final Optional<ShellAction> actionOptional = actionFactory.findAction(actionName);

            if (actionOptional.isPresent()) {
                final ShellAction action = actionOptional.get();
                if (action.isInvokable()) {

                    final ExecutionContext context = new ExecutionContext(call.getPath(), "");
                    final ActionResponse response = invokeAction(sentence, context, action, resolver);
                    send(connection, response);
                }
            } else {
                sendError(connection, String.format("Unknown action [%s]", actionName));
            }
        }
    }

    private ActionResponse invokeAction(final Sentence sentence, final ExecutionContext context, final ShellAction action, final ResourceResolver resolver)
            throws RepositoryException {

        final List<Argument> arguments = sentence.getArguments();
        final List<Parameter> params = createParameterList(arguments);
        Session session = resolver.adaptTo(Session.class);
        return action.execute(session, context, params);
    }

    private List<Parameter> createParameterList(final List<Argument> arguments) {
        final List results = Lists.newArrayList();
        for (final Argument arg : arguments) {
            results.add(arg.toParameter());
        }
        return results;
    }

    protected void send(final WebSocketConnection connection, final ActionResponse response) throws IOException {
        connection.send(mapper.writeValueAsString(response));
    }

    protected void sendError(final WebSocketConnection connection, final String message) throws IOException {
        final String response = mapper.writeValueAsString(ActionResponse.error(message));
        mapper.writeValueAsString(response);
    }

    @Override
    public void onMessage(WebSocketConnection connection, byte[] data) throws Throwable {
        onMessage(connection, new String(data));
    }

    @Override
    public void onPing(WebSocketConnection connection, byte[] data) throws Throwable {
    }

    @Override
    public void onPong(WebSocketConnection connection, byte[] data) throws Throwable {
    }
}
