package net.thartm.cq.cqshell.impl.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import net.thartm.cq.cqshell.action.ActionCall;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.*;

/** JSON RPC endpoint for method action invocations. Expects the action to be send as a JSON payload. <br />
 * Unserializes the payload and delegates the call to an invoker.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
@SlingServlet(paths = { "/bin/cqshell/rpc/2/action" }, methods = { "GET", "POST" })
public class MethodActionServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MethodActionServlet.class);

    @Override
    protected final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {
        final Optional idOptional = getParamOptional(request, JsonRpcParameter.ID);
        final Optional methodOptional = getParamOptional(request, JsonRpcParameter.METHOD);
        final Optional argOptional = getParamOptional(request, JsonRpcParameter.ARGUMENTS);

        final ObjectMapper mapper = new ObjectMapper();
        final ActionCall call = new ActionCall("1", "ls", "-l", "-a");
        final PrintWriter writer = response.getWriter();

        writer.write(mapper.writeValueAsString(call));
        response.flushBuffer();
        // TODO validate param optionals
    }

    private Optional getParamOptional(final SlingHttpServletRequest request, final JsonRpcParameter parameter) {
        final String param = request.getParameter(parameter.getName());
        return Optional.fromNullable(param);
    }

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json; encoding=UTF-8");

        final String messageBody = getRequestBody(request);
        final Optional<ActionCall> call = getMethodCall(messageBody);

        if (call.isPresent()) {
            // evaluate method call:
            // - check if method exist
            // - validate parameters
            // - call method
            // evaluate result
            // create response
            // return response

        } else {
            // TODO send error ... method does not exist
        }
    }

    protected String getRequestBody(final SlingHttpServletRequest request) throws IOException {

        final StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[1024];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    private Optional<ActionCall> getMethodCall(final String messageBody) throws IOException {
        if (StringUtils.isNotBlank(messageBody)) {
            final ObjectMapper mapper = new ObjectMapper();
            final ActionCall call = mapper.readValue(messageBody, ActionCall.class);
            return Optional.fromNullable(call);
        }
        return Optional.absent();
    }
}
