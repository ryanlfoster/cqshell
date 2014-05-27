package net.thartm.cq.cqshell.api;

/** Protocol definition according to http://www.jsonrpc.org/specification
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 05/2014 */
public enum Protocol {

    JSON_RPC_1("jsonrpc", "1.0"),
    JSON_RPC_2("jsonrpc", "2.0");

    private String key;
    private String value;

    Protocol(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
