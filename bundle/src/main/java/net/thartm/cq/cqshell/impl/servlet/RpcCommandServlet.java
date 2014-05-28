package net.thartm.cq.cqshell.impl.servlet;

import com.google.gson.Gson;
import net.thartm.cq.cqshell.api.ActionCall;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@SlingServlet(paths = { "/bin/cqshell/rpc/command" }, methods = "GET")
public class RpcCommandServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(RpcCommandServlet.class);

    @Override
    protected final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {

        final Gson gson = new Gson();
        final ActionCall call = new ActionCall("1", "ls", "-l", "-a");
        final PrintWriter writer = response.getWriter();

        writer.write(gson.toJson(call));
        response.flushBuffer();
    }
}
