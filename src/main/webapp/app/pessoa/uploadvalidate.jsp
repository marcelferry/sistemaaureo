<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      <div class="row">
            <form:form method="post" action="saveImport" commandName="importada" class="form-horizontal">
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="nome">Nome Completo</form:label>
					<div class="col-sm-10">
	                	<form:input path="nome" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="pai">Nome do Pai</form:label>
					<div class="col-sm-10">
	                	<form:input path="pai"  cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="mae">Nome da Mãe</form:label>
					<div class="col-sm-10">
	                	<form:input path="mae" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="rg">RG</form:label>
					<div class="col-sm-4">
	                	<form:input path="rg" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="cpf">CPF</form:label>
					<div class="col-sm-4">
	                	<form:input path="cpf" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="dataNascimento">Data de Nascimento</form:label>
					<div class="col-sm-4">
	                	<form:input path="dataNascimento" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="naturalidade">Natural de</form:label>
					<div class="col-sm-6">
	                	<form:input path="naturalidade" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="estadoCivil">Estado Civil</form:label>
					<div class="col-sm-4">
	                	<select name="enumEstadoCivil" id="enumEstadoCivil" class="form-control" >
						    <option value=""></option>
						        <c:forEach items="${estadocivilList}" var="option">
						        	<c:choose>
									      <c:when test="${option eq importada.enumEstadoCivil}">
												<option selected="selected" value="${option}">
							                    <c:out value="${option}"></c:out>
							                </option>
									      </c:when>
									      <c:otherwise>
										      <option value="${option}">
							                    <c:out value="${option}"></c:out>
							                  </option>
									      </c:otherwise>
									</c:choose>
						        </c:forEach>
						</select>
			                	
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="conjuge">Nome Conjuge</form:label>
					<div class="col-sm-10">
	                	<form:input path="conjuge" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="endereco">Logradouro (Rua,Av,etc...)</form:label>
					<div class="col-sm-10">
	                	<form:input path="endereco" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="numero">Número</form:label>
					<div class="col-sm-2">
	                	<form:input path="numero" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="bairro">Bairro</form:label>
					<div class="col-sm-10">
	                	<form:input path="bairro" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="cep">CEP</form:label>
					<div class="col-sm-4">
	                	<form:input path="cep" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="estado">Estado</form:label>
					<div class="col-sm-4">
	                	<form:input path="estado" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="cidade">Cidade</form:label>
					<div class="col-sm-10">
	                	<form:input path="cidade" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="telResDDD">Tel. Res. DDD</form:label>
					<div class="col-sm-2">
	                	<form:input path="telResDDD" cssClass="form-control" />
	                </div>
		            <form:label class="col-sm-2 control-label" path="telResNum">Tel. Res. Núm.</form:label>
					<div class="col-sm-3">
	                	<form:input path="telResNum" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="telComDDD">Tel. Com. DDD</form:label>
					<div class="col-sm-2">
	                	<form:input path="telComDDD" cssClass="form-control" />
	                </div>
		            <form:label class="col-sm-2 control-label" path="telComNum">Tel. Com. Núm.</form:label>
					<div class="col-sm-3">
	                	<form:input path="telComNum" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="telCelDDD">Tel. Cel. DDD</form:label>
					<div class="col-sm-2">
	                	<form:input path="telCelDDD" cssClass="form-control" />
	                </div>
		            <form:label class="col-sm-2 control-label" path="telCelNum">Tel. Cel. Núm.</form:label>
					<div class="col-sm-3">
	                	<form:input path="telCelNum" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="telCel2DDD">Tel. Cel. DDD 2</form:label>
					<div class="col-sm-2">
	                	<form:input path="telCel2DDD" cssClass="form-control" />
	                </div>
		            <form:label class="col-sm-2 control-label" path="telCel2Num">Tel. Cel. Núm. 2</form:label>
					<div class="col-sm-3">
	                	<form:input path="telCel2Num" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="email">Email</form:label>
					<div class="col-sm-10">
	                	<form:input path="email" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="facebook">Facebook</form:label>
					<div class="col-sm-10">
	                	<form:input path="facebook" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="profissao">Profissão</form:label>
					<div class="col-sm-10">
	                	<form:input path="profissao" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="trabalhaPosto">Trabalha no Posto</form:label>
					<div class="col-sm-10">
	                	<form:input path="trabalhaPosto" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="posto">Qual Posto</form:label>
					<div class="col-sm-10">
	                	<form:input path="posto" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="associado">Associado</form:label>
					<div class="col-sm-10">
	                	<form:input path="associado" cssClass="form-control" />
	                </div>
	            </div>
				<div class="form-group">
		            <form:label class="col-sm-2 control-label" path="anotacoes">Anotações</form:label>
					<div class="col-sm-10">
	                	<form:input path="anotacoes" cssClass="form-control" />
	                </div>
	            </div>
                <input type="submit" value="Confirmar" class="btn"/>
            </form:form>
			
        </div>
