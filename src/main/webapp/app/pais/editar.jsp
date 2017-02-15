<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
      
            <form:form method="post" action="save/${pais.id}" commandName="pais" class="form-horizontal">
                <form:hidden path="id" />
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="nome">Nome</form:label>
                	<div class="col-sm-4">
                		<form:input path="nome"  cssClass="form-control" />
                	</div>
	            </div>
                <input type="submit" value="Salvar" class="btn btn-primary"/>
            </form:form>
        </div>
        
        
     
