<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<style>
<!--

@page {  
	size: a4; 
	margin: 0.2in;
}

body{
	font-family: helvetica, Arial, sans-serif;
	font-size: 12px;
}

table {
	-fs-table-paginate: paginate;
	-fs-border-spacing-horizontal: 0;
    -fs-border-spacing-vertical: 0;
    border-spacing: 0;
    border-style: solid;
    border-width: 1px;
    border-color: black;
	font-size: 10px;
}

td, th {
	border-bottom: solid 1px black;
	border-right: solid 1px black;
}

td:first-child, th:first-child {
	border-bottom: solid 1px black;
	border-right: solid 1px black;
}

td:last-child, th:last-child {
	border-bottom: solid 1px black;
	border-right: none;
}

tr:last-child > td {
	border-bottom: none;
	border-right: solid 1px black;
}

tr:last-child > td:first-child {
	border-bottom: none;
	border-right: solid 1px black;
}

tr:last-child > td:last-child {
	border: none;
}

td.fixMergedLast {
	border-right: solid 1px black;
}

td.fixMergedLastRow {
	border-bottom: none;
	border-right: solid 1px black;
}


.separador{
	page-break-after: always;
}

-->
</style>
<c:forEach items="${planoList}" var="planoMetasForm">
<div class="row">
		<div style="text-align: center; width: 100%;">
			<h3><spring:message code="titulo.fichacentroespirita" /></h3>
		</div>
		<table  border="0" cellpadding="5" cellspacing="0" width="100%">
			<tr>
				<td width="14%"><b>Instituto:</b></td>
				<td width="32%" colspan="4"><b>${planoMetasForm.instituto.descricao}</b></td>
				<td width="6%"><b>Dir.Nac:</b></td>
				<td width="33%" colspan="4">${planoMetasForm.instituto.dirigenteNacional.nome}</td>
				<td width="5%"><b>Ano:</b></td>
				<td width="10%"><b>${planoMetasForm.rodizio.ciclo}</b></td>
			</tr>
		<c:if test="${ ! empty planoMetasForm.entidade.razaoSocial}">
			<tr>
				<td><b><spring:message code="label.instituicao" />:</b></td>
				<td colspan="5">${planoMetasForm.entidade.razaoSocial}&nbsp;</td>
				<c:if test="${planoMetasForm.evento == 'RODIZIO'}">
					<td><b>Facilitador:</b></td>
					<td colspan="5">${planoMetasForm.facilitador.nome} &nbsp;</td>
				</c:if>
				<c:if test="${planoMetasForm.evento == 'PRERODIZIO'}">
					<td><b>Facilitador:</b></td>
					<td colspan="5">&nbsp; </td>
				</c:if>
			</tr>
		</c:if>
		<c:if test="${ empty planoMetasForm.entidade.razaoSocial}">
			<tr>
				<td rowspan="2"><b><spring:message code="label.instituicao" />:</b></td>
				<td colspan="5">&nbsp;</td>
				<c:if test="${planoMetasForm.evento == 'RODIZIO'}">
					<td rowspan="2"><b>Facilitador:</b></td>
					<td colspan="5">${planoMetasForm.facilitador.nome} &nbsp;</td>
				</c:if>
				<c:if test="${planoMetasForm.evento == 'PRERODIZIO'}">
					<td rowspan="2"><b>Facilitador:</b></td>
					<td colspan="5">&nbsp; </td>
				</c:if>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ !empty planoMetasForm.entidade.razaoSocial}">
			<tr>
				<td><b><spring:message code="label.endereco" />:</b></td>
				<td  colspan="5">${planoMetasForm.entidade.endereco} &nbsp;</td>
				<td  width="8.5%"><b>Cidade:</b></td>
				<td  width="17%" colspan="3">${planoMetasForm.entidade.cidade} &nbsp;</td>
				<td  width="5%"><b>UF:</b></td>
				<td  width="10%">${planoMetasForm.entidade.uf} &nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ empty planoMetasForm.entidade.razaoSocial}">
			<tr>
				<td rowspan="2"><b><spring:message code="label.endereco" />:</b></td>
				<td colspan="5">${planoMetasForm.entidade.endereco} &nbsp;</td>
				<td rowspan="2" width="8.5%"><b>Cidade:</b></td>
				<td rowspan="2" width="17%" colspan="3">${planoMetasForm.entidade.cidade} &nbsp;</td>
				<td rowspan="2" width="5%"><b>UF:</b></td>
				<td rowspan="2" width="10%">${planoMetasForm.entidade.uf} &nbsp;</td>
			</tr>
			<tr>
				<td colspan="5" class="fixMergedLast" >&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ ! empty planoMetasForm.presidente.nome}">
			<tr>
				<td><c:if test="${ planoMetasForm.tipoContratante == 'PRESIDENTE' }">( X )</c:if>&nbsp;<b>Presidente:</b></td>
				<td  colspan="4">${planoMetasForm.presidente.nome}</td>
				<td><b>Email:</b></td>
				<td  colspan="4">${planoMetasForm.presidente.email}</td>
				<td><b>Fone:</b></td>
				<td>${planoMetasForm.presidente.telefone}</td>
			</tr>
		</c:if>
		<c:if test="${ empty planoMetasForm.presidente.nome}">
			<tr>
				<td rowspan="2"><c:if test="${ planoMetasForm.tipoContratante != 'PRESIDENTE' }">( &nbsp;&nbsp; )</c:if>&nbsp;<b>Presidente:</b></td>
				<td  colspan="4">&nbsp;</td>
				<td rowspan="2"><b>Email:</b></td>
				<td  colspan="4">&nbsp;</td>
				<td rowspan="2"><b>Fone:</b></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ ! empty planoMetasForm.coordenador.nome}">
			<tr>
				<td><c:if test="${ planoMetasForm.tipoContratante == 'COORDENADOR' }">( X )</c:if>&nbsp;<b>Coord.:</b></td>
				<td colspan="4">${planoMetasForm.coordenador.nome}</td>
				<td><b>Email:</b></td>
				<td colspan="4">&nbsp;</td>
				<td><b>Fone:</b></td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ empty planoMetasForm.coordenador.nome}">
			<tr>
				<td rowspan="2"><c:if test="${ planoMetasForm.tipoContratante != 'COORDENADOR' }">( &nbsp;&nbsp; )</c:if>&nbsp;<b>Coord.:</b></td>
				<td colspan="4">&nbsp;</td>
				<td rowspan="2"><b>Email:</b></td>
				<td colspan="4">&nbsp;</td>
				<td rowspan="2"><b>Fone:</b></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ ! empty planoMetasForm.outro.nome}">
			<tr>
				<td class="fixMergedLastRow"><c:if test="${ planoMetasForm.tipoContratante == 'OUTRO' }">( X )</c:if>&nbsp;<b>Trabalhador:</b></td>
				<td colspan="4">${planoMetasForm.outro.nome}</td>
				<td class="fixMergedLastRow"><b>Email:</b></td>
				<td colspan="4">&nbsp;</td>
				<td class="fixMergedLastRow"><b>Fone:</b></td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${ empty planoMetasForm.outro.nome}">
			<tr>
				<td rowspan="2" class="fixMergedLastRow"><c:if test="${ planoMetasForm.tipoContratante != 'OUTRO' }">( &nbsp;&nbsp; )</c:if>&nbsp;<b>Trabalhador:</b></td>
				<td colspan="4">&nbsp;</td>
				<td rowspan="2" class="fixMergedLastRow"><b>Email:</b></td>
				<td colspan="4">&nbsp;</td>
				<td rowspan="2" class="fixMergedLastRow"><b>Fone:</b></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		</table>
	<br/>
	<c:if test="${ true }">
	<div style="font-size: x-small;">
	<b>Legenda:</b><br/>
	<b>( &nbsp;I&nbsp; ) - Implantado: </b> <spring:message code="label.legenda.implantada.texto" /><br/>
	<b>( IP ) - Implantado Parcialmente: </b> <spring:message code="label.legenda.implantadaparcialmente.texto" /><br/>
	<b>( NI ) - Nao Implantado: </b> <spring:message code="label.legenda.naoimplantada.texto" /><br/>
	<b>( R ) - Realizou: </b> <spring:message code="label.legenda.implantadaparcialmente.texto" /><br/>
	<b>( NR ) - Nao realizou: </b> <spring:message code="label.legenda.naoimplantada.texto" /><br/>
	
	</div>
	</c:if>
	<br/>
	<table border="0" cellpadding="5" cellspacing="0" class="metas"  width="100%">
		<thead>
			<tr>
				<th style="width: 35%" class="bg-primary">Atividade</th>
				<th style="width: 20%" colspan="2" class="bg-primary"><spring:message code="label.situacao.atual" /></th>
				<th style="width: 20%" colspan="2" class="bg-primary">Planejamento</th>
				<th style="width: 25%" class="bg-primary"><spring:message code="label.observacoes" /></th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${ empty branco }">
			<c:set var="branco" value="false"/>
		</c:if>
		<c:if test="${ empty planoMetasForm.entidade.razaoSocial}">
			<c:set var="controlequebra" value="15" scope="request" />
		</c:if>
		<c:if test="${ ! empty planoMetasForm.entidade.razaoSocial}">
			<c:set var="controlequebra" value="11" scope="request" />
		</c:if>
		<c:forEach items="${planoMetasForm.dependencias}" var="meta"
			varStatus="status">
			<template:itemMetaImpressao meta="${meta}" index="${status.index}" count="${status.count}" nivel="0" branco="${ branco }"/>
		</c:forEach>
		</tbody>
	</table>
</div>
<div class="separador" style="width: 100%; text-align: center; margin-top: 5px;">${planoMetasForm.instituto.descricao}</div>
</c:forEach>
<script src="/js/jquery-1.10.2.js"></script>
<script>
$(document).ready(function() {  
	$(document).find(".separador:last").removeClass("separador");
	//alert("Para imprimir corretamente, reduzir as margens, remover o cabecalho e rodape da impressão e utilizar folha A4.");
	//self.print();
	$('#impPdf').submit();
});
</script>

<form id="impPdf" onsubmit="imprimirPdf()" action="/gestao/relatorio/common/reportgenerator/generatePDF" method="post">
 <script>
 	function imprimirPdf(){

 	 	var htmlContentString = $('html').html();

 	 	var htmlContent = $(htmlContentString); 
 	 	
 	 	htmlContent.find('script').remove();
 	 	//htmlContent.find('form').remove();

 		var conteudo = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">\n<html>' + htmlContent.html() + '</html>';

 		$('#impPdf').append('<input type="hidden" id="content" name="content" value="" />');
 		$('#impPdf').append('<input type="hidden" id="filename" name="filename" value="" />');
 		$('#impPdf').append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />');
 		     
 		$('#content').val(conteudo);
 		$('#filename').val("${planoMetasForm.instituto.descricao}");
 		
 		return true;
  }
 </script>
</form>