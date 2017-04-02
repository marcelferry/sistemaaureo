<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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

	<style>
		input {text-transform: none;}
	</style>
</head>

<body>

    <div class="container">
        <div class="row">
        	<div class="col-md-12 col-sm-12" style="text-align: center;">
        		<span style="font-size: 20px;">
        			<spring:message code="app.name" />
        		</span>
        	</div>
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 ">
                <div class="login-panel panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Efetuar Login</h3>
                    </div>
                    <div class="panel-body">
                        <form  action="/authentication" method="POST" role="form" class="validado">
                            <fieldset>
                                <div class="form-group">
                                    <input id="username" name="username" class="form-control" placeholder="E-mail" autofocus
                                    data-msg-required="Usuário obrigatório."
                                    data-rule-required="true" >
                                </div>
                                <div class="form-group">
                                    <input id="password" name="password" class="form-control" placeholder="Senha" type="password" value=""
                                    data-msg-required="Senha obrigatória."
                                    data-rule-required="true" >
                                </div>
                                <!-- div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Permanecer Conectado
                                    </label>
                                </div-->
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-lg btn-primary btn-block">Entrar</button>
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
                        </form>
                        <br/>
                        Primeiro acesso? Esqueceu sua senha? <b><a href="/gestao/userprofile/recuperarsenha">Entre Aqui</a></b>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 hidden">
                <div class="login-panel panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Fechado para o Rodízio</h3>
                    </div>
                    <div class="panel-body">
                        O sistema gestão de metas, está disponível para preenchimento das fichas na rede interna do CEMAB durante esta quinta e sexta feira.
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 hidden">
                <div class="login-panel panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Sistema em manutenção</h3>
                    </div>
                    <div class="panel-body">
                        O sistema gestão de metas, esta em manutenção. Em breve retornaremos.
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
	<script src="/js/plugins/validation/additional-methods-br.js"></script>
	
    <!-- SB Admin Scripts - Include with every page -->
    <script src="/js/sb-admin.js"></script>

</body>

</html>
