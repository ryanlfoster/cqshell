package net.thartm.cq.cqshell.impl.evaluation;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.thartm.cq.cqshell.action.JsonRpcCall;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.impl.model.Argument;
import net.thartm.cq.cqshell.impl.model.ArgumentType;
import net.thartm.cq.cqshell.impl.model.Operator;
import net.thartm.cq.cqshell.impl.model.Sentence;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@Service
@Component
public class ExpressionParserServiceImpl implements ExpressionParserService {

    private static final Logger LOG = LoggerFactory.getLogger(ExpressionParserServiceImpl.class);

    @Reference
    private MethodActionFactory methodActionFactory;

    public List<Sentence> parseToSentences(final JsonRpcCall jsonRpcCall) {
        final String expression = jsonRpcCall.getExpression();
        return parseToSentences(expression);
    }

    public List<Sentence> parseToSentences(final String expression) {
        return findSentences(expression);
    }

    protected List<Sentence> findSentences(final String expression) {
        final List<Sentence> sentences = Lists.newLinkedList();
        final Scanner scanner = new Scanner(expression);

        Sentence sentence = new Sentence();
        final Stack<String> argumentStack = new Stack<>();
        while (scanner.hasNext()) {
            final String token = scanner.next();
            if (StringUtils.isNotBlank(token)) {
                // verify if we are dealing with an operator
                final Operator tokenOp = matchOperator(token);
                if (matchOperator(token) != Operator.NONE) {
                    LOG.info("Found operator [{}]", token);
                    if (Operator.WRITE.equals(tokenOp)) {
                        LOG.info("Found write operator [{}]. Using it as action.", token);
                        sentence = new Sentence(tokenOp.get());
                    } else {
                        sentence = new Sentence();
                    }
                    sentence.setLeadingOperator(tokenOp);
                    sentences.add(sentence);
                    continue;
                }

                // not an operator so it must be a command or an argument
                final Optional<ShellAction> action = methodActionFactory.findAction(token);
                if (action.isPresent()) {
                    final String shellActionName = action.get().getName();
                    LOG.info("Found action [{}]", shellActionName);
                    sentence.setAction(action.get().getName());
                    sentences.add(sentence);
                    continue;
                }

                // not an operator or action so it must be a an argument
                final Optional<Argument> argument = createArgument(scanner, argumentStack, token);
                if (argument.isPresent()) {
                    sentence.addArgument(argument.get());
                }
            }
        }

        return sentences;
    }

    private Optional<Argument> createArgument(Scanner scanner, Stack<String> argumentStack, String token) {
        Argument argument = null;
        if (token.startsWith("-") || token.startsWith("--")) {
            LOG.info("Found key argument [{}]", token);
            argumentStack.add(token);
            if (!scanner.hasNext()) {
                argument = new Argument<>(token, "", ArgumentType.KEY_ONLY);
            }
        } else {
            LOG.info("Found value argument [{}]", token);
            if ((argumentStack.size() > 0)) {
                argument = new Argument<>(argumentStack.pop(), token, ArgumentType.KEYVALUE);
            } else {
                argument = new Argument<>("", token, ArgumentType.VALUE_ONLY);
            }
        }

        return Optional.fromNullable(argument);
    }

    private Operator matchOperator(final String token) {
        if (token.length() == 1) {
            for (final Operator op : Operator.values()) {
                if (StringUtils.equals(op.get(), token)) {
                    return op;
                }
            }
        }
        return Operator.NONE;
    }
}
