<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta charset="UTF-8">
        <title>Twitterwall</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.5/css/bootstrap.min.css'/>" media="screen">
        <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css">
		<script src="<c:url value='/webjars/bootstrap/3.3.5/js/bootstrap.min.js'/>"></script>
		<script src="<c:url value='/webjars/jquery/1.11.1/jquery.min.js'/>"></script>
        <script src="<c:url value='/js/jquery.periodicalupdater.js'/>"></script>
        <script src="<c:url value='/js/jquery.awesomeCloud-0.2.min.js'/>"></script>
    </head>
    <body>
    	<div class="container">
        <div class="row">
            <div id="header" class="col-md-12">
                <h1>Twitterwall:</h1><h4><c:out value="${twitterSearchterm}"/></h4>
            </div>
        </div>
         <div class="row">
            <div id="content" class="col-md-12">
                <%@ include file="/WEB-INF/views/twitterMessages.jsp"%>
            </div>
        </div>
		</div>
        <script type="text/javascript">
            $(window).load(function(){
                $.PeriodicalUpdater('<c:url value="/ajax"/>', {
                    method: 'get', // method; get or post
                    data: '', // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
                    minTimeout: 5000, // starting value for the timeout in milliseconds
                    maxTimeout: 20000, // maximum length of time between requests
                    multiplier: 2, // the amount to expand the timeout by if the response hasn't changed (up to maxTimeout)
                    type: 'text', // response type - text, xml, json, etc. See $.ajax config options
                    maxCalls: 0, // maximum number of calls. 0 = no limit.
                    autoStop: 0 // automatically stop requests after this many returns of the same data. 0 = disabled.
                }, function(remoteData, success, xhr, handle) {
                    $('#content').html(remoteData);
                    $("#wordcloud1").awesomeCloud({
                        "size" : {
                            "grid" : 3,
                            "factor" : <c:out value="${tagCloudFactor}"/>
                        },
                        "options" : {
                            "color" : "random-dark",
                            "rotationRatio" : 0.35
                        },
                        "font" : "'Times New Roman', Times, serif",
                        "shape" : "circle"
                    });
                });
            });
        </script>

    </body>
</html>
