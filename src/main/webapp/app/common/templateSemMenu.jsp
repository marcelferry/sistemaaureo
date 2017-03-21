<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    
    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/cupertino/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="/css/plugins/tooltipster/tooltipster.css" rel="stylesheet" type="text/css" />
	<link href="/css/plugins/treegrid/jquery.treegrid.css"  rel="stylesheet" >
	<link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">
    
    
    <!-- Core Scripts - Include with every page -->
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/jquery-ui-1.10.4.custom.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="/js/bootstrap3-typeahead.min.js"></script>
	<script src="/js/plugins/treegrid/jquery.treegrid.js" type="text/javascript" ></script>
    <script src="/js/plugins/treegrid/jquery.treegrid.bootstrap3.js" type="text/javascript"></script>
 	<script src="/js/loading-overlay.min.js"></script>
    
  </head>

  <body>
  
  
   <div id="wrapper">
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
	        <tiles:insertAttribute name="headerPage"/>
        </nav>
        
        <div id="page-wrapper" style="margin: 0px;">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header"><tiles:insertAttribute name="header" /></h1>
                </div>
                <!-- /.col-sm-12 -->
            </div>
            <!-- /.row -->
            <tiles:insertAttribute name="bodyPage" />
         </div>  
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <tiles:insertAttribute name="footerPage" />

</body>
</html>
