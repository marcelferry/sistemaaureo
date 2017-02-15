<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title"><tiles:insertAttribute name="header" /></h4>
</div>
<div class="modal-body">
	<tiles:insertAttribute name="bodyPage" />
</div>
<div class="modal-footer">
	<button type="button" data-dismiss="modal" class="btn btn-default">Fechar</button>
	<!-- button type="button" class="btn btn-primary">Salvar</button-->
</div>