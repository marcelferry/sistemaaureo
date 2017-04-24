<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.typeahead_wrapper { display: block; height: 30px; }
.typeahead_photo { float: left; max-width: 30px; max-height: 30px; margin-right: 5px; }
.typeahead_labels { float: left; height: 30px; }
.typeahead_primary { font-weight: bold; }
.typeahead_secondary { font-size: .8em; margin-top: -5px; }
</style>

    	<div class="row">
            <form:form method="post" action="save/${instituto.id}" commandName="instituto" class="form-horizontal">
				<form:hidden path="id" />
				<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="descricao">Nome</form:label>
		                <div class="col-sm-10">
		                	<form:input path="descricao" cssClass="form-control"/>
		                </div>
		            </div>
		        <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="nome">Abreviação</form:label>
		                <div class="col-sm-10">
		                	<form:input path="nome" cssClass="form-control"/>
		                </div>
		            </div>
                <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="objetivo">Objetivo</form:label>
		                <div class="col-sm-10">
		                	<form:textarea path="objetivo" cssClass="form-control"/>
		                </div>
		            </div>
		        <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="dirigenteNacional.id">Dirigente Nacional:</form:label>
		                <div class="col-sm-6">
							<form:hidden path="dirigenteNacional.id"/>
			                <form:input path="dirigenteNacional.nome" placeholder="Selecione o trabalhador"  class="form-control"/>	
						</div>
					</div>
                <div class="form-group">
	            	<div class="col-sm-offset-2 col-sm-10">
	            		<div class="checkbox">
                			<form:checkbox path="rodizio" /> Participa do Rodizio
                		</div>
                	</div>
                </div>
                 <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <input type="submit" value="Salvar Instituto" class="btn btn-primary btn-mini"/>
					    </div>
					  </div>
            </form:form>
        </div>
        
        <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
        <script type="text/javascript">
		$(function(){
			completePessoa($('#dirigenteNacional\\.nome'), $("#dirigenteNacional\\.id"), BASEURL );
		});

        </script>
        