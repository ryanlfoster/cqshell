package net.thartm.cq.cqshell.impl.model;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public enum Operator {

    NONE(""),
    PIPE("|"),
    AND("&&"),
    WRITE(">");

    private String value;

    Operator(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }

}
