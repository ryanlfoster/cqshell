package net.thartm.cq.cqshell.impl.model;

import net.thartm.cq.cqshell.method.Parameter;
import org.apache.commons.lang3.StringUtils;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class Argument<T> {

    private String key;

    private T value;

    private ArgumentType argumentType;

    public Argument(final String key, final T value, final ArgumentType argumentType) {
        this.key = key;
        this.value = value;
        this.argumentType = argumentType;
    }

    public static Argument<String> createStringKeyValueArgument(final String key, final String value) {
        return new Argument<String>(key, value, ArgumentType.KEYVALUE);
    }

    public static Argument<String> createStringValueArgument(final String value) {
        return new Argument<String>("", value, ArgumentType.VALUE_ONLY);
    }

    public static Argument<String> createStringKeyArgument(final String key) {
        return new Argument<String>(key, "", ArgumentType.KEY_ONLY);
    }

    public Class getValueTypeHint() {
        return value.getClass();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public void setArgumentType(ArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public Parameter toParameter(){
       final String key = StringUtils.isEmpty(this.key) ? "" : this.key;
       return new Parameter(key , this.value);
    }
}
