package net.thartm.cq.cqshell.impl.authentication;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 08/2014
 */
public class TokenAuthenticationServiceImpl {

    private static final String QUERY_BY_TOKEN = "SELECT * FROM [nt:base] WHERE jcr:primaryType = 'rep:Token' AND ";
}
