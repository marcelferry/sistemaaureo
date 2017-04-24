<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
      
            <form:form method="post" action="add" commandName="userprofile" class="form-horizontal">
            	<form:hidden path="lastLogin" />
	            <div class="form-group">
	            	<form:hidden path="pessoa.id"/>
	                <form:label  class="col-sm-2 control-label" path="pessoa.nome">Caravaneiro</form:label>
                	<div class="col-sm-8">
                		<form:input path="pessoa.nome" cssClass="form-control" />
                	</div>
                </div>
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="username">Nome Usuário</form:label>
                	<div class="col-sm-4">
                		<form:input path="username"  cssClass="form-control" />
                	</div>
	            </div>
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="password">Senha</form:label>
                	<div class="col-sm-6">
                		<form:password path="password"  cssClass="form-control" />
                	</div>
	            </div>
	            <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="passwordExpired">Trocar Senha</form:label>
		                <div class="col-sm-3">
							<form:select path="passwordExpired" cssClass="form-control">
								<form:option value="false">Não</form:option>
								<form:option value="true">Sim</form:option>
							</form:select>
						</div>
					</div>
	            <div class="form-group">
					<!-- The field label is defined in the messages file (for i18n) -->
					<label for="rolesIds" class="col-sm-2 control-label">Perfis</label>
					<div class="col-sm-10">
						<span class="checkbox col-md-12 col-sm-12">
							<input type="checkbox" name="select-all" id="select-all" />
							<label for="select-all">Selecionar/Deselecionar Tudo</label>
						</span>
						<form:checkboxes id="rolesIds" element="span class='checkbox col-md-4 col-sm-6'" itemLabel="descricao" itemValue="id" items="${alcadas}" path="rolesIds" />
						<form:errors id="rolesIds_errors" path="rolesIds" cssClass="label label-danger" />
						<script>
						$('[id^="rolesIds"]').each(function( index, value ) {
  							if($(value).val() == '3' || 
  									$(value).val() == '5' ||
  									$(value).val() == '7' ||
  									$(value).val() == '8'){
  								$(value).attr('disabled', true);
  							}
						});
						</script>
					</div>
				</div>
    
                 <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
               				<input type="submit" value="Salvar" class="btn btn-primary btn-mini"/>
               		</div>
				</div>
            </form:form>
        </div>   
        <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
  <script>
  $(function() {
	  
    completePessoa($('#pessoa\\.nome'), $("#pessoa\\.id"), BASEURL );  
    
   	// Listen for click on toggle checkbox
   	$('#select-all').click(function(event) {   
   	    if(this.checked) {
   	        // Iterate each checkbox
   	        $(':checkbox').each(function() {
   	        	if(!this.disabled)
   	            this.checked = true;                        
   	        });
   	    } else {
   	        // Iterate each checkbox
   	        $(':checkbox').each(function() {
   	        	if(!this.disabled)
   	        	this.checked = false;                        
   	        });
   	    }
   	});   
  });
 </script>
