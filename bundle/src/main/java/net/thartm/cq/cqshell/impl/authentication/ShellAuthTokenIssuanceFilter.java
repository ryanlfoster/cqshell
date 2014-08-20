package net.thartm.cq.cqshell.impl.authentication;

import com.google.common.base.Optional;
import net.thartm.cq.cqshell.ShellConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.security.Principal;

/** Issues an authentication token that can then be fetched and used for websocket related communication.
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 07/2014 */
@SlingFilter(scope = SlingFilterScope.REQUEST, order = -10000)
public class ShellAuthTokenIssuanceFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ShellAuthTokenIssuanceFilter.class);
    private static final String SHELL_AUTH_COOKIE = "shell-token";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // not required
    }

    @Override
    public void destroy() {
        // not required
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        final String pagePath = slingRequest.getPathInfo().split("\\?")[0];

        if (StringUtils.equals(ShellConstants.PATH.toString(), pagePath.toLowerCase())) {

        }

        chain.doFilter(request, response);
    }

    private void authenticate(final SlingHttpServletRequest slingRequest, final ServletResponse response) {
        final Optional<Cookie> cookie = Optional.fromNullable(slingRequest.getCookie(SHELL_AUTH_COOKIE));
        final Principal principal = slingRequest.getUserPrincipal();

        if (!cookie.isPresent()) {
            final ResourceResolver resolver = slingRequest.getResourceResolver();
            //principal.getName()
        } else {

            // validate if cookie is still correct for the current user
            if (LOG.isDebugEnabled()) {
                LOG.debug("Authentication cookie present for user [{}]", principal.getName());
            }
        }
    }

    private void applyAuthenticationCookie(final String token, final SlingHttpServletResponse response){
        final Cookie cookie = new Cookie(SHELL_AUTH_COOKIE, token);
        response.addCookie(cookie);
    }

}
