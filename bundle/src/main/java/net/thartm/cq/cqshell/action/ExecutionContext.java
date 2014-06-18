package net.thartm.cq.cqshell.action;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class ExecutionContext {

    private String path;
    private String userId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
