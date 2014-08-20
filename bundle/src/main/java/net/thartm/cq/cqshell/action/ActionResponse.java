package net.thartm.cq.cqshell.action;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class ActionResponse {

    private String path;
    private Type type;
    private String message;

    public ActionResponse(final String message, final String path, final Type type) {
        this.type = type;
        this.message = message;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public static ActionResponse error(final String message, final String path) {
        return new ActionResponse(message, path, Type.ERROR);
    }

    public static ActionResponse error(final String message) {
        return new ActionResponse(message, "", Type.ERROR);
    }

    public static ActionResponse success(final String message, final String path) {
        return new ActionResponse(message, path, Type.SUCCESS);
    }

    public enum Type {
        SUCCESS, ERROR
    }
}
