package net.thartm.cq.cqshell.impl.model;

import org.junit.Assert;
import org.junit.Test;

public class SentenceTest {

    @Test
    public void testMultiArgumentSentence() throws Exception {
        Argument<String> argument = Argument.createStringKeyValueArgument("-R", "/tmp/test");
        final Sentence sentence = new Sentence("rm", argument);

        Assert.assertEquals("rm -R /tmp/test", sentence.toString());
    }

    @Test
    public void testSingleValueArgumentSentence() throws Exception {
        Argument<String> argument = Argument.createStringValueArgument("/tmp/test");
        final Sentence sentence = new Sentence("cd", argument);

        Assert.assertEquals("cd /tmp/test", sentence.toString());
    }

    @Test
    public void testSingleKeyArgumentSentence() throws Exception {
        Argument<String> argument = Argument.createStringValueArgument("-l");
        final Sentence sentence = new Sentence("ls", argument);

        Assert.assertEquals("ls -l", sentence.toString());
    }
}
