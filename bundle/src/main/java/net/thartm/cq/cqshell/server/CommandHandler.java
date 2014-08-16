package net.thartm.cq.cqshell.server;

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
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.List;

/** @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */

public class CommandHandler implements WebSocketHandler, CommandExecutor {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SlingRepository repository;
    private final MethodActionFactory actionFactory;
    private final ExpressionParserService expressionParser;

    int connectionCount;

    public CommandHandler(SlingRepository repository, ExpressionParserService expressionParser, MethodActionFactory actionFactory) {
        this.repository = repository;
        this.expressionParser = expressionParser;
        this.actionFactory = actionFactory;
    }

    public void onOpen(WebSocketConnection connection) {
        connection.send("This is " + getClass().getName() + ", there are " + connectionCount + " other connections active");
        connectionCount++;
    }

    public void onClose(WebSocketConnection connection) {
        connectionCount--;
    }

    public void onMessage(final WebSocketConnection connection, final String message) {

        if (StringUtils.isNotBlank(message)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                final SocketCall call = mapper.readValue(message, SocketCall.class);
                execute(mapper, connection, call);

            } catch (IOException | RepositoryException e) {
                log.error("Unable to execute action", e);
                connection.send("Unable to execute action.");
            }
        }
    }

    private void execute(final ObjectMapper mapper, final WebSocketConnection connection, final SocketCall call)
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
                    final ActionResponse response = invokeAction(sentence, context, action);
                    connection.send(mapper.writeValueAsString(response));
                }
            } else {
                final String errorMessage = String.format("Unknown action [%s]", actionName);
                connection.send(mapper.writeValueAsString(ActionResponse.error(errorMessage, "")));
            }
        }
    }

    private ActionResponse invokeAction(final Sentence sentence, final ExecutionContext context, final ShellAction action) throws RepositoryException {
        Session session = null;
        try {
            session = repository.loginAdministrative(null);
            final List<Argument> arguments = sentence.getArguments();
            final List<Parameter> params = createParameterList(arguments);
            return action.execute(session, context, params);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

    private List<Parameter> createParameterList(final List<Argument> arguments) {
        final List results = Lists.newArrayList();
        for (final Argument arg : arguments) {
            results.add(arg.toParameter());
        }
        return results;
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
