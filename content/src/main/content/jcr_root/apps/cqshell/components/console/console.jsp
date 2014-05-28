<%@page session="false" contentType="text/html" pageEncoding="utf-8"
        import="java.util.Collection,
                java.util.List,
                org.apache.sling.discovery.ClusterView,
                org.apache.sling.discovery.DiscoveryService,
                org.apache.sling.discovery.InstanceDescription,
                org.apache.sling.discovery.TopologyView,
                com.adobe.granite.ui.components.AttrBuilder,
                com.adobe.granite.ui.components.Config,
                com.adobe.granite.ui.components.Tag" %>
<%
%>
<%@include file="/libs/granite/ui/global.jsp" %>
<%
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%%>
<section></section>
<div id="console_box" class="terminal"></div>
<script>
    jQuery(document).ready(function ($) {
        var id = 1;
        var endpointUrl = "/bin/cqshell/rpc/2/action";
        // TODO use for more detailed debug messages
        var debugMode = false;

        function invoke_command(method, arguments, term) {
            term.pause();
            $.jrpc("/bin/cqshell/rpc/2/action",
                    method,
                    arguments,
                    function (data) {
                        term.resume();
                        if (data.error && data.error.message) {
                            term.error(data.error.message);
                        } else {
                            if (typeof data.result == 'boolean') {
                                term.echo(data.result ? 'success' : 'fail');
                            } else {
                                var len = data.result.length;
                                for (var i = 0; i < len; ++i) {
                                    term.echo(data.result[i].join(' | '));
                                }
                            }
                        }
                    },
                    function (xhr, status, error) {
                        term.error('[AJAX] ' + status +
                                ' - Server reponse is: \n' +
                                xhr.responseText);
                        term.resume();
                    });
        }

        $('#console_box').terminal(function (command, term) {
            var expressionArr = command.split(" ");
            if (expressionArr.length > 0) {
                // split both into method and argument list
                var method = expressionArr[0];

                var arguments = expressionArr.length > 1 ? expressionArr.slice(1) : [];

                if (method == 'help') {
                    term.echo("Available commands are grouped. \n" +
                            "Please enter help [groupname] for detailed information.");
                } else if (method == 'ping') {
                    term.echo("Ping to CQ instance ... TODO implement it");
                } else if (method == 'pwd') {
                    term.push(invoke_command(method, arguments, term),
                            {
                                greetings: "This is an example greeting message",
                                prompt: "cq> "
                            });
                } else if (method == 'sql2') {
                    term.push(invoke_command(method, arguments, term),
                            {
                                greetings: "Please enter the JCR_SQL2 query",
                                prompt: "sql2> "
                            });
                } else {
                    term.echo("unknown command " + method);
                }
            }
        }, {
            greetings: "Welcome to CQ Shell ::: because every software needs a shell!!!",
            onBlur: function () {
                // prevent loosing focus
                return false;
            }
        });
    });
</script>