<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code="app.name" /></title>

    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">
    <link href="/css/exceptionhandler.css" rel="stylesheet">
    

</head>
<body>
<div class="container">
	<br/>
	<br/>
	<br/>
	<div id="messagePanel" style="display:none;" class="alert alert-error offset4 span4"></div>
    <div class="offset2 span8 center">
    	<h2>ExceptionHandlerExceptionResolver</h2>
    	<br/>
    	<table class="table table-striped table-bordered">
    		<thead>
    			<tr>
    				<th class="center">Action</th>
    				<th class="center">Scenario</th>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td>
    					<button type="button" class="btn btn-primary" onclick="exception1()">Exception 1</button>
    				</td>
    				<td>Plain HTML text message</td>
    			</tr>
    			<tr>
    				<td>
    					<form action="/exception/http/exception2">
    						<button type="submit" class="btn btn-primary">Exception 2</button>
    					</form>
    				</td>
    				<td>Custom error page</td>
    			</tr>
    			<tr>
    				<td>
    					<form action="/exception/http/exception3">
    						<button type="submit" class="btn btn-primary">Exception 3</button>
    					</form>
    				</td>
    				<td>Generic error page with error code and error message</td>
    			</tr>
				<tr>
    				<td>
    					<form action="/exception/http/exception4">
    						<button type="submit" class="btn btn-primary">Exception 4</button>
    					</form>
    				</td>
    				<td>Redirect to custom page with functional exception</td>
    			</tr> 
				<tr>
    				<td>
    					<button type="button" class="btn btn-primary" onclick="exception5()">Exception 5</button>
    				</td>
    				<td>Ajax request with Javascript error code handling</td>
    			</tr>       			   	    			    			    			
    		</tbody>
    	</table>
    	<br/>
    	<h2>DefaultHandlerExceptionResolver</h2>
    	<br/>
    	<table class="table table-striped table-bordered">
    		<thead>
    			<tr>
    				<th class="center">Action</th>
    				<th class="center">Scenario</th>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td>
    					<form action="/exception/http/exception6">
    						<button type="submit" class="btn btn-primary">Exception 6</button>
    					</form>
    				</td>
    				<td>Test for DefaultHandlerExceptionResolver</td>
    			</tr>
    		</tbody>
    	</table>
		<br/>
    	<h2>SimpleMappingExceptionResolver</h2>
    	<br/>
    	<table class="table table-striped table-bordered">
    		<thead>
    			<tr>
    				<th class="center">Action</th>
    				<th class="center">Scenario</th>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td>
    					<form action="/exception/http/exception7">
    						<button type="submit" class="btn btn-primary">Exception 7</button>
    					</form>
    				</td>
    				<td>ClassNotFoundException</td>
    			</tr>
    			<tr>
    				<td>
    					<form action="/exception/http/exception8">
    						<button type="submit" class="btn btn-primary">Exception 8</button>
    					</form>
    				</td>
    				<td>CloneNotSupportedException</td>
    			</tr>    			
    		</tbody>
    	</table>    			
	</div>
    
    
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog  modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></span></h3>
                </div>
                <div class="modal-body">
                    <p>
			    	An error has occured in class <strong></strong>.<em></em> with the message :
			    	<br/>
			    	<br/> 
			    	<span class="red center"></span>
			    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Fechar</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    	
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/exceptionhandler.js"></script>
</div>
</body>
</html>