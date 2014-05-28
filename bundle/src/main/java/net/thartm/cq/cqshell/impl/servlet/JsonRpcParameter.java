package net.thartm.cq.cqshell.impl.servlet;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public enum JsonRpcParameter {

    ID("id"),
    METHOD("method"),
    ARGUMENTS("arguments");

    private String name;

    JsonRpcParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
