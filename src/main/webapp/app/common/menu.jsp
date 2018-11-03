<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<div class="navbar-default navbar-static-side" role="navigation">
                <div class="sidebar-collapse">
                	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR'}">  
                	<ul class="nav" id="side-menu">
                		<li>
                            <a href="/gestao/planodemetas/"><i class="fa fa-calendar fa-fw"></i> <spring:message code="menu.contratacao" /></a>
                        </li>
                    </ul>
                	</c:if>
                	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}"> 
                	<ul class="nav" id="side-menu">
                		
                		 <li class="sidebar-search">
                        	<select id="ciclo_atual_presidente" class="form-control">
                        		<c:forEach items="${rodiziolist}" var="rodizio" varStatus="status">
                        			<c:if test="${CICLO_CONTROLE.id == rodizio.id}">
                        			<option value="${rodizio.id}" selected="selected">${rodizio.ciclo}</option>
                        			</c:if>
                        			<c:if test="${CICLO_CONTROLE.id != rodizio.id}">
                        			<option value="${rodizio.id}">${rodizio.ciclo}</option>
                        			</c:if>
                        		</c:forEach>
                        	</select>
                        </li>
                        <jsp:useBean id="now" class="java.util.Date"/>
                        <c:if test="${CICLO_CONTROLE.inicioAjustes <= now && CICLO_CONTROLE.terminoAjustes >= now }">
                        <li>
                            <a href="/gestao/pessoa/entidade/"><i class="fa fa-pencil fa-fw"></i> Dados de Trabalhadores</a>
                        </li>
                		<li>
                            <a href="javascript:void(0);" onclick="$(this).next('form').submit();"><i class="fa fa-pencil fa-fw"></i> <spring:message code="menu.dados.instituicao" /></a>
                            <form action="/gestao/entidade/edit/${ INSTITUICAO_CONTROLE.id }" method="post">
                            </form>
                        </li>
                		<li>
                            <a href="/gestao/planodemetas/ciclo/${CICLO_CONTROLE.id}"><i class="fa fa-calendar fa-fw"></i> <spring:message code="menu.contratacao" /> ${CICLO_CONTROLE.ciclo}</a>
                        </li>
                        </c:if>
                        <li>
                            <a href="/gestao/home/presidente/${CICLO_CONTROLE.id}"><i class="fa fa-bar-chart-o fa-fw"></i> Dashboard</a>
                        </li>
                        <c:if test="${CICLO_CONTROLE.dataAprovacao < now }">
                        <li>
                            <a href="/gestao/home/dashboard/listagem/${CICLO_CONTROLE.id}"><i class="fa fa-list fa-fw"></i> <spring:message code="menu.presidente.metas" /></a>
                        </li>
                        </c:if>
                        <li>
                            <a href="/gestao/relatorio/entidade/fichasimpressao/${CICLO_CONTROLE.id}"><i class="fa fa-file-text-o fa-fw"></i> <spring:message code="menu.presidente.metasimpressao" /> </a>
                        </li>
                        <li>
                        	<a href="/gestao/relatorio/fichasembranco"><i class="fa fa-file-o fa-fw"></i> <spring:message code="menu.presidente.fichasembranco" /></a>
                        </li>
                    </ul>
                    </c:if> 
                    <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
                    <ul class="nav" id="side-menu">
                    	<li class="sidebar-search">
                        	<select id="ciclo_atual_dirigente" class="form-control">
                        		<c:forEach items="${rodiziolist}" var="rodizio" varStatus="status">
                        			<c:if test="${CICLO_CONTROLE.id == rodizio.id}">
                        			<option value="${rodizio.id}" selected="selected">${rodizio.ciclo}</option>
                        			</c:if>
                        			<c:if test="${CICLO_CONTROLE.id != rodizio.id}">
                        			<option value="${rodizio.id}">${rodizio.ciclo}</option>
                        			</c:if>
                        		</c:forEach>
                        	</select>
                        </li>
                    
                    
                    	<li>
                            <a href="/gestao/home/dirigente/${CICLO_CONTROLE.id}"><i class="fa fa-bar-chart-o fa-fw"></i> Dashboard</a>
                        </li>
                         <li>
                            <a href="/gestao/home/dashboard/listagem/${CICLO_CONTROLE.id}"><i class="fa fa-list fa-fw"></i> Metas Contratadas</a>
                        </li>
                        <li>
                        	<a href="/gestao/planoModelo/listar"><i class="fa fa-file-text-o fa-fw"></i> Situação de Todas as Metas</a>
                        </li>
                        <li>
                        	<a href="/gestao/facilitador/"><i class="fa fa-file-text-o fa-fw"></i> Facilitadores</a>
                        </li>
                    </ul>	    
                    </c:if>
                	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_ADMIN' }">
                    <ul class="nav" id="side-menu">
                        <!-- li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Localizar...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                        </li -->
                        <li class="sidebar-search">
                        	<select id="ciclo_atual_secretaria" class="form-control">
                        		<c:forEach items="${rodiziolist}" var="rodizio" varStatus="status">
                        			<c:if test="${CICLO_CONTROLE.id == rodizio.id}">
                        			<option value="${rodizio.id}" selected="selected">${rodizio.ciclo}</option>
                        			</c:if>
                        			<c:if test="${CICLO_CONTROLE.id != rodizio.id}">
                        			<option value="${rodizio.id}">${rodizio.ciclo}</option>
                        			</c:if>
                        		</c:forEach>
                        	</select>
                        </li>
                        <li>
                            <a href="/gestao/home/dashboard/${CICLO_CONTROLE.id}"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                        </li>
                        <li ${relatorio?"class=\"active\"":""}>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> <spring:message code="menu.relatorios" /><span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            	<c:if test="${ PERIODO_METAS }">
                                <li><a href="/gestao/relatorio/statusEntidadePreenchimentoCiclo"><spring:message code="menu.relatorios.preenchimentoprevio" /></a></li>
                                </c:if>
                                <li><a href="/gestao/relatorio/metascontratadasporentidade/ciclos"><spring:message code="menu.relatorios.metascontratadasentidade" /></a></li>
                                <li><a href="/gestao/relatorio/statusInstitutoContratacaoCiclo"><spring:message code="menu.relatorios.metascontratadasinstituto" /></a></li>
                                <li><a href="/gestao/relatorio/fichasembranco"><spring:message code="menu.relatorios.fichasembranco" /></a></li>
                                <li><a href="/gestao/userprofile/listaHistorico"><spring:message code="menu.relatorios.historicoacesso" /></a></li>
                        
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="/gestao/planodemetas/secretaria/contratacaoListaCiclo"><i class="fa fa-calendar fa-fw"></i> <spring:message code="menu.contratacao" /></a>
                        </li>
                        <li ${rodizio?"class=\"active\"":""}>
                            <a href="#"><i class="fa fa-check-circle-o fa-fw"></i> <spring:message code="menu.rodizio" /><span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li><a href="/gestao/rodizio/"><spring:message code="menu.rodizio" /></a></li>
                                <li><a href="/gestao/planoModelo/">Plano Modelo (Ficha)</a></li>
                                <li><a href="/gestao/facilitador/">Facilitadores</a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        
                        <li ${basicos?"class=\"active\"":""}>
                            <a href="#"><i class="fa fa-sitemap fa-fw"></i> <spring:message code="menu.cadastros.basicos" /><span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
					            <li><a href="/gestao/entidade/">Entidade</a></li>
					            <li><a href="/gestao/pessoa/">Caravaneiros</a></li>
					            <li><a href="/gestao/instituto/">Institutos</a></li>
				            	<li><a href="/gestao/comissao/"><spring:message code="menu.comissao" /></a></li>
				            	<li><a href="/gestao/atividade/">Atividades</a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li ${auxiliares?"class=\"active\"":""}>
                       		<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Cadastros Auxiliares<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
					            <li><a href="/gestao/cidade/">Cidades</a></li>
					            <li><a href="/gestao/estado/">Estados</a></li>
					            <li><a href="/gestao/pais/">Paises</a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                         </li>
                         <li ${utilidades?"class=\"active\"":""}>
                       		<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Utilidades<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
					            <li><a href="/gestao/email/entidade/envio">Envio de email</a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                         </li>
                         <sec:authorize access="hasRole('ROLE_ADMIN')">
                         <li ${admin?"class=\"active\"":""}>
                       		<a href="#"><i class="fa fa-coffee fa-fw"></i> Administrador<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
					             <li><a href="/gestao/userprofile/"><spring:message code="menu.usuarios"/> </a></li>
                            </ul>
                            <!-- /.nav-second-level -->
                         </li>
                         </sec:authorize>
                    </ul>
                    <!-- /#side-menu -->
                    </c:if> 
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
