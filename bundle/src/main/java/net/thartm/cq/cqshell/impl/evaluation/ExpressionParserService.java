package net.thartm.cq.cqshell.impl.evaluation;

import net.thartm.cq.cqshell.action.JsonRpcCall;
import net.thartm.cq.cqshell.impl.model.Sentence;

import java.util.List;

/** @author thomas.hartmann@netcentric.biz
 * @since 06/2014 */
public interface ExpressionParserService {

    List<Sentence> parseToSentences(final JsonRpcCall jsonRpcCall);

    List<Sentence> parseToSentences(final String expression);

}
