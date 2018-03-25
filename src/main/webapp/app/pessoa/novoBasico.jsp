<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
<!--
.ui-datepicker { font-size: 9pt !important; }
-->
</style>

      <div id="principal" class="row">
            <form:form method="post" action="add" commandName="pessoa" class="form-horizontal validado">
            
	                <div class="form-group">
	                	<form:hidden path="id"/>
			            <form:label class="col-sm-2 control-label" path="nome">Nome Completo</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="nome"  cssClass="form-control upper"
	                		data-rule-required="true" 
	                		data-msg-required="Nome é obrigatório"
	                		autocomplete="off"
	                		readonly="${readonly}"/>
	                	</div>
		            </div>
	                
	            	 <div class="form-group">
			            <form:label class="col-sm-2 control-label" path="cpf">CPF</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="cpf"  cssClass="form-control cpf"
	                		data-rule-cpf="true"
	                		readonly="${readonly}" />
	                	</div>
		            </div>
		            
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="endereco.cidade.nome">Cidade</form:label>
		                <div class="col-sm-5">
			                <form:hidden path="endereco.cidade.id" />
							<form:input path="endereco.cidade.nome" cssClass="form-control upper" 
							data-rule-required="true" 
	                		data-msg-required="Cidade é obrigatório"
	                		autocomplete="off"
	                		readonly="${readonly}"/>
						</div>
					</div>
		            
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cidade.estado.sigla">UF</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.cidade.estado.sigla" cssClass="form-control upper"
	                		data-rule-required="true" 
	                		data-msg-required="Estado é obrigatório"
	                		readonly="${readonly}"/>
	                	</div>
		            </div>
		            
	     			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="telefones">Telefones</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="telefones">
	   						<tr>
	   							<td>Tipo</td>
	   							<td>DDD</td>
	   							<td>Número</td>
	   							<td>Operadora</td>
	   							<td>Ação</td>
	   						</tr>
		                    <c:forEach items="${pessoa.telefones}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${pessoa.telefones[loop.index].remove eq 1}">
		                            	<c:set var="cssTelefoneHidden" value="hidden"/>
		                            </c:when>
		                            <c:otherwise>
		                            	<c:set var="cssTelefoneHidden" value=""/>
		                            </c:otherwise>
		                        </c:choose>
		                        <tr id="telefones${loop.index}.wrapper" class=" ${cssTelefoneHidden}">
		               			 <td>
		               			 	<form:hidden path="telefones[${loop.index}].id"/>
		                        	<select name="telefones[${loop.index}].tipo" id="telefones${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipotelefoneList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq pessoa.telefones[loop.index].tipo}">
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
									</td>
		                            <td>
		                            <!-- Generate the fields -->
		                           		 <form:input path="telefones[${loop.index}].ddd" placeholder="DDD"  class="form-control input-sm" readonly="${readonly}"/>
		                            </td>
		                            <td>
		                            	<form:input path="telefones[${loop.index}].numero" placeholder="Número Telefone" class="form-control input-sm" readonly="${readonly}" />
		                            </td>
		                            <td>
		                            	<form:select path="telefones[${loop.index}].operadora" class="form-control input-sm" readonly="${readonly}">
		                            		<form:option value="">Selecione</form:option>
		                            		<form:option value="CLARO">Claro</form:option>
		                            		<form:option value="OI">Oi</form:option>
		                            		<form:option value="TIM">Tim</form:option>
		                            		<form:option value="VIVO">Vivo</form:option>
		                            		<form:option value="ALGAR">ALGAR</form:option>
		                            		<form:option value="NEXTEL">NEXTEL</form:option>
		                            		<form:option value="MVNO">MVNO's</form:option>
		                            		<form:option value="Sercomtel">Sercomtel</form:option>
		                            	</form:select>
		                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${pessoa.telefones[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
		                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
		                            </c:choose>
		                            <form:hidden path="telefones[${loop.index}].remove" value="${hiddenValue}" />
		                            
		                            <button class="btn  btn-danger btn-xs  telefones.remove" data-index="${loop.index}">
		                            	<span class="glyphicon glyphicon-remove-sign">
			  							</span>
		                            </button>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </table>
		                    <c:if test="${ ! readonly }">
		                    <button id="addTelefones" type="button">Novo Telefone</button>
		                    </c:if>
		                </div>
		              </div>
	     			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="emails">Internet</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="emails">
	   						<tr>
	   							<td class="col-sm-2">Tipo</td>
	   							<td class="col-sm-8">Contato</td>
	   							<td class="col-sm-1">Principal</td>
	   							<td class="col-sm-1">Ação</td>
	   						</tr>
		                    <c:forEach items="${pessoa.emails}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${pessoa.emails[loop.index].remove eq 1}">
		                                <c:set var="cssInternetHidden" value="hidden"/>
		                            </c:when>
		                            <c:otherwise>
		                               <c:set var="cssInternetHidden" value=""/>
		                            </c:otherwise>
		                        </c:choose>
		                         <tr id="emails${loop.index}.wrapper" class=" ${cssInternetHidden}">
		               			 <td>
		               			    <form:hidden path="emails[${loop.index}].id"/>
		                        	<select name="emails[${loop.index}].tipo" id="emails${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipocontatoList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq pessoa.emails[loop.index].tipo}">
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
									</td>
		                            <td>
		                            <!-- Generate the fields -->
		                           		 <form:input path="emails[${loop.index}].contato" placeholder="Contato"  class="form-control input-sm" readonly="${readonly}"/>
		                            </td>
		                             <td>
			                            <!-- Generate the fields -->
			                           		 <form:checkbox path="emails[${loop.index}].principal" class="form-control input-sm" readonly="${readonly}"/>
			                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${pessoa.emails[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
		                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
		                            </c:choose>
		                            <form:hidden path="emails[${loop.index}].remove" value="${hiddenValue}" />
		                            
		                            <button class="btn  btn-danger btn-xs  emails.remove" data-index="${loop.index}">
		                            	<span class="glyphicon glyphicon-remove-sign">
			  							</span>
		                            </button>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </table>
		                    <c:if test="${ ! readonly }">
		                    <button id="addEmails" type="button">Novo Contato</button>
		                    </c:if>
		                </div>
		              </div>
	              <button type="button" onclick="enviar();" class="btn btn-primary">Salvar</button>
            </form:form>
        </div>
        
        <!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body">
		      </div>
		      </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
        
  <script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
  <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
  <script>

	function enviar(){
		var id = $("#id").val();
		if(id != null && id > 0){
			$("#pessoa").attr('action', BASEURL + "/gestao/pessoa/editBase/save/" + id );
			//alert($("#pessoa").attr('action'));
		} else {
			$("#pessoa").attr('action', BASEURL + "/gestao/pessoa/addBase/" );
			//alert($("#pessoa").attr('action'));
		}
		$("#pessoa").submit();
	}
  
  $(function() {
	  
	  completeCidade($('#endereco\\.cidade\\.nome'), $('#endereco\\.cidade\\.id'), BASEURL, function(item){
		    $('#endereco\\.cidade\\.estado\\.sigla').val(item.uf);
	        $('#endereco\\.cidade\\.estado\\.sigla').attr('readonly', true);
	        return item.name;
		}, false);
		
	  completePessoa($('#nome'), $('#id'), BASEURL, function(item){
			  var myUrl = BASEURL + "/gestao/pessoa/editBase/" + item.id;// + " #principal";
			  //window.location.replace(myUrl);

			  var form = document.createElement("form");
			    form.method = "POST";
			    form.action = myUrl;   
			    form.submit();
			  
	        return item.name;
		});
	  
	  $('.cpf').mask('000.000.000-00', {reverse: true});
	  
   	// Start indexing at the size of the current list
      var idxPhone = ${fn:length(pessoa.telefones)};
      var idxContato = ${fn:length(pessoa.emails)};

      // Add a new telefone
      $("#addTelefones").off("click").on("click", function() {
          $("#telefones tr:last").after(function() {
              var html = '<tr id="telefones' + idxPhone + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="telefones[' + idxPhone + '].tipo" id="telefones' + idxPhone + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipotelefoneList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" size="4" id="telefones' + idxPhone + '.ddd" name="telefones[' + idxPhone + '].ddd" placeholder="DDD"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="text" size="10" id="telefones' + idxPhone + '.numero" name="telefones[' + idxPhone + '].numero" placeholder="Número Telefone"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<select id="telefones' + idxPhone + '.operadora" name="telefones[' + idxPhone + '].operadora" class="form-control input-sm" >';
              html += '<option value="">Selecione</option>';
              html += '<option value="CLARO">Claro</option>';
              html += '<option value="OI">Oi</option>';
              html += '<option value="TIM">TIM</option>';
              html += '<option value="VIVO">Vivo</option>';
              html += '<option value="ALGAR">ALGAR</option>';
              html += '<option value="NEXTEL">NEXTEL</option>';
              html += '<option value="MVNO">MVNO\'s</option>';
              html += '<option value="Sercomtel">Sercomtel</option>';
              html += '<select>';
              html += '</td><td>';
              html += '<input type="hidden" id="telefones' + idxPhone + '.remove" name="telefones[' + idxPhone + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs telefones.remove" data-index="' + idxPhone + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#telefones" + idxPhone + "\\.wrapper").removeClass('hidden');
          $(".telefones\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#telefones" + index2remove + "\\.wrapper").addClass('hidden');
              $("#telefones" + index2remove + "\\.remove").val("1");
              return false;
          });
          idxPhone++;
          return false;
      });

      // Remove a telefone
      $(".telefones\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#telefones" + index2remove + "\\.wrapper").addClass('hidden');
          $("#telefones" + index2remove + "\\.remove").val("1");
          return false;
      });
      
      // Add a new email
      $("#addEmails").off("click").on("click", function() {
          $("#emails tr:last").after(function() {
              var html = '<tr id="emails' + idxContato + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="emails[' + idxContato + '].tipo" id="emails' + idxContato + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipocontatoList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" id="emails' + idxContato + '.contato" name="emails[' + idxContato + '].contato" placeholder="Contato"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="checkbox" id="emails' + idxContato + '.principal" name="emails[' + idxContato + '].principal" class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="emails' + idxContato + '.remove" name="emails[' + idxContato + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs emails.remove" data-index="' + idxContato + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#emails" + idxContato + "\\.wrapper").removeClass('hidden');
          $(".emails\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
              $("#emails" + index2remove + "\\.remove").val("1");
              return false;
          });
          idxContato++;
          return false;
      });

      // Remove an email
      $(".emails\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
          $("#emails" + index2remove + "\\.remove").val("1");
          return false;
      });

      $('#myModal').on('show.bs.modal', function () {
    		 
	        $(this).find('.modal-dialog').css({
	                  width:'40%x', //choose your width
	                  height:'100%', 
	                  'padding':'0'
	           });
	         $(this).find('.modal-content').css({
	                  height:'100%', 
	                  'border-radius':'0',
	                  'padding':'0'
	           });
	         $(this).find('.modal-body').css({
	                  width:'auto',
	                  height:'100%', 
	                  'padding':'0'
	           });
	    });
  });
  </script>
