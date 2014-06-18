package net.thartm.cq.cqshell.impl.evaluation;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import junit.framework.Assert;
import net.thartm.cq.cqshell.action.ShellAction;
import net.thartm.cq.cqshell.impl.model.Operator;
import net.thartm.cq.cqshell.impl.model.Sentence;
import net.thartm.cq.cqshell.util.ReflectionUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionParserServiceImplTest {

    private ExpressionParserServiceImpl parser;

    private MethodActionFactory methodActionFactory = new MethodActionFactoryStub();

    @Before
    public void setUp() throws Exception {
        parser = new ExpressionParserServiceImpl();
        ReflectionUtil.setFieldValue(parser, "methodActionFactory", methodActionFactory);
    }

    @Test
    public void testParseSimpleExpression() throws Exception {
        final String expression = "rm -R /tmp/test";
        final List<Sentence> sentences = parser.findSentences(expression);
        Assert.assertEquals(1, sentences.size());

        final Sentence sentence = sentences.get(0);
        Assert.assertEquals(sentence.getAction(), "rm");
        Assert.assertEquals(sentence.getArguments().get(0).getKey(), "-R");
        Assert.assertEquals(sentence.getArguments().get(0).getValue(), "/tmp/test");
    }

    @Test
    public void testParseExpressionsWithOperator() throws Exception {

        String expression = "ls -l /tmp/test > /tmp/results";
        final List<Sentence> sentences = parser.findSentences(expression);
        Assert.assertEquals(2, sentences.size());

        final Sentence sentence = sentences.get(0);
        Assert.assertEquals(sentence.getAction(), "ls");
        Assert.assertEquals(sentence.getArguments().get(0).getKey(), "-l");
        Assert.assertEquals(sentence.getArguments().get(0).getValue(), "/tmp/test");

        final Sentence sentence2 = sentences.get(1);
        Assert.assertEquals(sentence2.getAction(), ">");
        Assert.assertEquals(sentence2.getLeadingOperator(), Operator.WRITE);
        Assert.assertEquals(sentence2.getArguments().get(0).getKey(), "");
        Assert.assertEquals(sentence2.getArguments().get(0).getValue(), "/tmp/results");
    }

    private class MethodActionFactoryStub implements MethodActionFactory {

        private final Set<String> actions = Sets.newHashSet();
        {
            actions.add("pwd");
            actions.add("rm");
            actions.add("ls");
            actions.add("cd");
            actions.add("bundle");
        }

        @Override
        public Optional<ShellAction> findAction(String methodName) {
            if (actions.contains(methodName)) {
                final ShellAction mockShellAction = mock(ShellAction.class);
                // prepare shell action mock
                when(mockShellAction.getName()).thenReturn(methodName);
                return Optional.fromNullable(mockShellAction);
            }
            return Optional.absent();
        }
    }
}
