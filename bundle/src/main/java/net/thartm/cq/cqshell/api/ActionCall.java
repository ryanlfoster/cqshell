package net.thartm.cq.cqshell.api;

import java.util.Arrays;
import java.util.List;

/** A request call to a specific method.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class ActionCall {

    private String id;
    private String method;
    private List<Object> params;

    public ActionCall(){}

    public ActionCall(final String id, final String method, final List<Object> params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public ActionCall(final String id, final String method, final Object... params) {
        this.id = id;
        this.method = method;
        this.params = Arrays.asList(params);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(final List<Object> params) {
        this.params = params;
    }
}
