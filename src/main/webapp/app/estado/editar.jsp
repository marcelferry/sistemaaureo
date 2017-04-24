<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.ui-helper-hidden-accessible { display:none; }
</style>

      <div class="row">
      
            <form:form method="post" action="save/${estado.id}" commandName="estado" class="form-horizontal">
                <form:hidden path="id" />
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="nome">Nome</form:label>
                	<div class="col-sm-4">
                		<form:input path="nome"  cssClass="form-control" />
                	</div>
	            </div>
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="sigla">Sigla</form:label>
                	<div class="col-sm-4">
                		<form:input path="sigla"  cssClass="form-control" />
                	</div>
	            </div>
	            <div class="form-group">
	                <form:label   class="col-sm-2 control-label"  path="pais.nome">País</form:label>
	                <div class="col-sm-10">
		                <form:hidden path="pais.id" />
						<form:input path="pais.nome" cssClass="form-control" />
					</div>
				</div>
                <input type="submit" value="Salvar" class="btn btn-primary"/>
            </form:form>
        </div>
        
        
         <script type="text/javascript">
		$(function(){
			
			$("#pais\\.nome").autocomplete({
				source: function (request, response) {
					$.ajax({
			            dataType: "json",
			            type : 'Get',
			            url: BASEURL + '/pais/list',
			           	data: {	            
							maxRows: 6,
							query: request.term
						},
						success: function( data ) {
							response( $.map( data, function( item ) {
								return {
									label: item.nome,
									value: item.id
								}
							}));
						}
					});
				},
				minLength: 1,
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
			    close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				},
				select: function(event, ui) {
			        event.preventDefault();
			        $("#pais\\.nome").val(ui.item.label);
			        $("#pais\\.id").val(ui.item.value);
			    },
			    focus: function(event, ui) {
			        event.preventDefault();
			        $("#pais\\.nome").val(ui.item.label);
			    }
			}); 
		});
	</script>
        
     
