<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
      
            <form:form method="post" action="add" commandName="pais" class="form-horizontal">
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="nome">Nome</form:label>
                	<div class="col-sm-4">
                		<form:input path="nome"  cssClass="form-control" />
                	</div>
	            </div>
                <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
               				<input type="submit" value="Salvar" class="btn btn-primary btn-mini"/>
               		</div>
				</div>
            </form:form>
        </div>
        
        
     
