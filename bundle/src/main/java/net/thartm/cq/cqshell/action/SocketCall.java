package net.thartm.cq.cqshell.action;

/** @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */
public class SocketCall {

    private String command;
    private String path;

    public SocketCall() {
    }

    public SocketCall(String command, String path) {
        this.command = command;
        this.path = path;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
