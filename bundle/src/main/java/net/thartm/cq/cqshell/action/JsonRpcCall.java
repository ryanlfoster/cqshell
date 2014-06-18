package net.thartm.cq.cqshell.action;

/** An incoming JSON RPC call to a specific method.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class JsonRpcCall {

    private String id;
    private String method;
    private String expression;
    private ExecutionContext executionContext;

    public JsonRpcCall() {
    }

    public JsonRpcCall(final String id, final String method, final String expression, final ExecutionContext executionContext) {
        this.id = id;
        this.method = method;
        this.expression = expression;
        this.executionContext = executionContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
}
