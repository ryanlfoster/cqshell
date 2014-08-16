<%@page session="false" contentType="text/html" pageEncoding="utf-8"%>
<%@include file="/libs/granite/ui/global.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%%>
<section></section>
<div id="console_box" class="terminal"></div>
<script>
    jQuery(document).ready(function ($) {
        var id = 1;
        var debugMode = false;

        var config ={
            "wsUri" : "ws://localhost:9999/console",
            "executionPath": "/"
        };
        function invoke_command(method, arguments, term) {
            term.pause();
        }

        var websocket = new WebSocket(config.wsUri);

        var terminal = $('#console_box').terminal(function (command, term) {
            var expressionArr = command.split(" ");
            if (expressionArr.length > 0) {
                // split both into method and argument list
                var method = expressionArr[0];
                var arguments = expressionArr.length > 1 ? expressionArr.slice(1) : [];

                if (method == 'help') {
                    term.echo("Available commands are grouped. \n" +
                            "Please enter help [groupname] for detailed information.");
                } else if(method == 'config') {
                    term.echo(JSON.stringify(config));
                } else {
                    console.log("Sending command to server: [" + command + "]");
                    var messageObject = {"command":command, "path": config.executionPath};

                    var message = JSON.stringify(messageObject);
                    console.log("Sending message [" + message + "]")
                    websocket.send(message);
                }
            }
        }, {
            greetings: "Welcome to CQ Shell ::: because every software needs a shell!!!",
            onBlur: function () {
                // prevent loosing focus
                return false;
            }
        });

        websocket.onmessage = function (evt) {
            var response = JSON.parse(evt.data)
            if(response.type !== "ERROR" && config.executionPath !== response.path){
                console.log("Changed executionPath to ["+response.path+"]");
                config.executionPath = response.path;
            }
            terminal.echo(response.message);
        };

        websocket.onerror = function (evt) {
            terminal.echo(evt.data)
        };

        websocket.onopen = function (evt) {
            terminal.echo(evt.data)
        };
    });
</script>