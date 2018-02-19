<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <c:if test="${ not empty profiles && profiles[0] != 'prod' }">
                	<c:set var="envAmb" value=" - AMBIENTE DE TESTES"/>
                </c:if>
                <c:if test="${ empty profiles || profiles[0] == 'prod'  }">
                	<c:set var="envAmb" value=" "/>
                </c:if>
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR'}"> 
                	<a class="navbar-brand" href="#"><spring:message code="app.nameShort" /><font color="red"><b>${ envAmb }</b></font></a>
                </c:if>
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}"> 
                	<a class="navbar-brand" href="/gestao/home/dashboard/${CICLO_CONTROLE.id}"><spring:message code="app.nameShort" /><font color="red"><b>${ envAmb }</b></font></a>
                </c:if>
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}"> 
                	<a class="navbar-brand" href="/gestao/home/presidente/${CICLO_CONTROLE.id}"><spring:message code="app.nameShort" /><font color="red"><b>${ envAmb }</b></font></a>
                </c:if>
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}"> 
                	<a class="navbar-brand" href="/gestao/home/dirigente"><spring:message code="app.nameShort" /><font color="red"><b>${ envAmb }</b></font></a>
                </c:if>
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_CONSELHO'}"> 
                	<a class="navbar-brand" href="/gestao/home/dashboard/${CICLO_CONTROLE.id}"><spring:message code="app.nameShort" /><font color="red"><b>${ envAmb }</b></font></a>
                </c:if>
            </div>
            <!-- /.navbar-header -->
			
            <ul class="nav navbar-top-links navbar-right">
            	<sec:authorize access="hasRole('ROLE_METAS_PRESIDENTE')">
            		<c:if test="${not empty INSTITUICAO_CONTROLE}">
                	${INSTITUICAO_CONTROLE.razaoSocial} - 
                	</c:if>
                </sec:authorize>
            	<sec:authorize access="hasRole('ROLE_METAS_DIRIGENTE') || hasRole('ROLE_METAS_FACILITADOR') ">
            		<c:if test="${not empty INSTITUTO_CONTROLE}">
                	${INSTITUTO_CONTROLE.descricao} - 
                	</c:if>
                </sec:authorize>
                <sec:authorize access="isAuthenticated() && !hasRole('ROLE_ADMIN')">
                <sec:authentication property="principal.pessoa.nome" var="nomeUsuarioLogado"/>
                <c:set var="nomeUsuario" value="${fn:split(nomeUsuarioLogado, ' ')}" />
                            ${nomeUsuario[0]} 
                </sec:authorize>
           		<c:if test="${not empty ROLE_CONTROLE}">
           			<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' }">
               			<sup>(Facilitador)</sup>
               		</c:if>
               		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
               			<sup>(Presidente)</sup>
               		</c:if>
               		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE' }">
               			<sup>(Dirigente)</sup>
               		</c:if>
               	</c:if>
                <!-- /.dropdown -->
                <sec:authorize access="isAuthenticated()">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                    		<sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> Perfil Usuário</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Configurações</a>
                        </li>
                        <li class="divider"></li>
                        </sec:authorize>
                        <li><a href="/gestao/home/roles/false"><i class="fa fa-sign-out fa-fw"></i> Trocar Perfil</a>
                        <li><a href="/gestao/userprofile/trocarsenha"><i class="fa fa-lock fa-fw"></i> Trocar Senha</a>
                        <li><a href="#" onclick="formLogoutSubmit();"><i class="fa fa-sign-out fa-fw"></i> Sair</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                </sec:authorize>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->
