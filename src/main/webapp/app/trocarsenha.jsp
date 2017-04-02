<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Sistema Áureo</title>

    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">

</head>

<body>

    <div class="container">
        <div class="row">
        	<div class="col-md-12" style="text-align: center;">
        		<span style="font-size: 20px;">
        			<spring:message code="app.name" />
        		</span>
        	</div>
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Redefinição de senha</h3>
                    </div>
                    <div class="panel-body">
	                    <c:if test="${not empty firstAccess}">
	                    	<div class="alert alert-warning alert-dismissable">
	  								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
									Você deve trocar sua senha no primeiro acesso.</br/>
									User pelo menos 6 caracteres.
							</div>
						</c:if>
                    	
                        <form:form method="post" action="/gestao/userprofile/trocarsenha" commandName="usuarioform" class="validado">
                            <fieldset>
                                <div class="form-group">
                                    <form:input path="username" cssClass="form-control login" placeholder="E-mail" readonly="true" />
                                </div>
                                <div class="form-group">
                                     <form:password path="password" cssClass="form-control login" placeholder="Sua Nova Senha" 
                                     data-msg-required="A nova senha é obrigatória."
                                     data-msg-minlength="A senha deve ter pelo menos 6 caracteres."
                                     data-rule-required="true" 
                                     data-rule-minlength="6" />
                                </div>
                                <div class="form-group">
                                    <form:password path="confirmPassword" cssClass="form-control login" placeholder="Repita Sua Nova Senha"
                                     data-msg-equalto="A senhas devem ser iguais."
                                     data-rule-equalto="#password"/>
                                </div>
                                <form:errors cssClass="error-message-list" element="div"/>
                                <br/>
                                <button type="submit" class="btn btn-lg btn-success btn-block">Redefinir</button>
                            </fieldset>
                            
                            <c:if test="${not empty error}">
                             <br/>
                            <div class="alert alert-danger alert-dismissable">
  								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								${error}
							</div>
							</c:if>
							<c:if test="${not empty msg}">
							 <br/>
							<div class="alert alert-warning alert-dismissable">
  								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								${msg}
							</div>
							</c:if>
                        </form:form>
                        <br/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
   

    <!-- Core Scripts - Include with every page -->
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="/js/plugins/validation/jquery.validate.min.js"></script>
	<script src="/js/plugins/validation/additional-methods.min.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="/js/sb-admin.js"></script>

</body>

</html>
