<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.ui-helper-hidden-accessible { display:none; }
</style>

      <div class="row">
      
            <form:form method="post" action="add" commandName="cidade" class="form-horizontal">
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="nome">Nome</form:label>
                	<div class="col-sm-4">
                		<form:input path="nome"  cssClass="form-control" />
                	</div>
	            </div>
				<div class="form-group">
	                <form:label   class="col-sm-2 control-label"  path="estado.nome">Estado</form:label>
	                <div class="col-sm-5">
		                <form:hidden path="estado.id" />
						<form:input path="estado.nome" cssClass="form-control" />
					</div>
				</div>
                <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
               				<input type="submit" value="Salvar" class="btn btn-primary btn-mini"/>
               		</div>
				</div>
            </form:form>
        </div>
        
        
         
        <script type="text/javascript">
		$(function(){
			
			$("#estado\\.nome").autocomplete({
				source: function (request, response) {
					$.ajax({
			            dataType: "json",
			            type : 'Get',
			            url: BASEURL + '/estado/list',
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
				minLength: 2,
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
			    close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				},
				select: function(event, ui) {
			        event.preventDefault();
			        $("#estado\\.nome").val(ui.item.label);
			        $("#estado\\.id").val(ui.item.value);
			    },
			    focus: function(event, ui) {
			        event.preventDefault();
			        $("#estado\\.nome").val(ui.item.label);
			    }
			}); 
		});
	</script>
        
     
