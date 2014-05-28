package net.thartm.cq.cqshell.action;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class ActionResponse {

    private Type type;

    private String message;

    public ActionResponse(final String message, final Type type) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ActionResponse error(final String message) {
        return new ActionResponse(message, Type.ERROR);
    }

    public static ActionResponse success(final String message) {
        return new ActionResponse(message, Type.SUCCESS);
    }

    public enum Type {
        SUCCESS, ERROR
    }
}
