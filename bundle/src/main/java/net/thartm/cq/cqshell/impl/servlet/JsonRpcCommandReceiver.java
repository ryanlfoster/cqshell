package net.thartm.cq.cqshell.impl.servlet;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import net.thartm.cq.cqshell.api.MethodCall;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public class JsonRpcCommandReceiver extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(JsonRpcCommandReceiver.class);

    @Override
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("application/json; encoding=UTF-8");

        final String messageBody = getRequestBody(request);
        final Optional<MethodCall> call = getMethodCall(messageBody);

        if(call.isPresent()){
            // evaluate method call:
            // - check if method exist
            // - validate parameters
            // - call method
            // evaluate result
            // create response
            // return response

        }else{
            //TODO send error ... method does not exist
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

    private Optional<MethodCall> getMethodCall(final String messageBody) {
        if (StringUtils.isNotBlank(messageBody)) {
            final Gson gson = new Gson();
            final MethodCall call = gson.fromJson(messageBody, MethodCall.class);
            return Optional.fromNullable(call);
        }
        return Optional.absent();
    }
}