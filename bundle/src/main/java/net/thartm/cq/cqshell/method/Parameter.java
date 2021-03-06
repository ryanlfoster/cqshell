package net.thartm.cq.cqshell.method;

/** Parameter object for an ActionCall.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class Parameter<T> {

    private String name;
    private T value;

    public Parameter() {
    }

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Class type() {
        return value.getClass();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
