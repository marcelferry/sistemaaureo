<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
      <div class="row">
       <div class="panel panel-primary">
                <div class="panel-heading">
                    Dados Gerais
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                	<label>Instituto/Comissão:</label>
                	<input type="text" value="${meta.instituto.descricao}" class="form-control" readonly="readonly"/>
                	<label>Meta:</label>
                	<input type="text" value="${meta.descricao}" class="form-control" readonly="readonly"/>
                </div>
            </div>
          <c:if  test="${!empty listaMetas}">
         
          <div class="panel panel-primary">
              <div class="panel-heading">
                  Entidades
              </div>
              <!-- /.panel-heading -->
              <div class="panel-body">
               <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                	<thead>
	                    <tr>
	                        <th>Cod.</th>
	                        <th>Entidade</th>
	                        <th>Cidade/UF</th>
	                        <th>Situacao</th>
	                        <c:if test="${status != 'NAOPLANEJADA'}">
	                        <th>
	                        <c:if test="${status == 'IMPLANTADA'}">
	                        Desde
	                        </c:if>
	                        <c:if test="${status == 'PLANEJADA'}">
	                        Previsão
	                        </c:if>
	                        </th>
	                        </c:if>
	                        <th>&nbsp;</th>
	                    </tr>
                    </thead>
                   <tbody>
                   <c:forEach items="${listaMetas}" var="meta">
                       <tr>
                           <td>${meta.idMeta}</td>
                           <td>${meta.entidade}</td>
                           <td>${meta.cidade}/${meta.uf}</td>
                           <td>${meta.situacao}</td>
                           <c:if test="${status != 'NAOPLANEJADA'}">
                           <td>
	                           <c:if test="${status == 'IMPLANTADA'}">
	                           		<c:if test="${meta.tipoMeta == 'QUANTITATIVA'}">
	                           <fmt:formatNumber value="${meta.realizado}"/> 
	                           		</c:if>
	                           		<c:if test="${meta.tipoMeta != 'QUANTITATIVA'}">
	                           <fmt:formatDate value="${meta.conclusao}" pattern="MMM/yyyy"/> 
	                           		</c:if>
	                           	</c:if>
	                           <c:if test="${status == 'PLANEJADA'}">
	                           		<c:if test="${meta.tipoMeta == 'QUANTITATIVA'}">
	                           <fmt:formatNumber value="${meta.previsto}"/> 
	                           		</c:if>
	                           		<c:if test="${meta.tipoMeta != 'QUANTITATIVA'}">
	                           <fmt:formatDate value="${meta.previsao}" pattern="MMM/yyyy"/> 
	                           		</c:if>
	                           </c:if>
                           </td>
                           </c:if>
                           <td>
                           </td>
                       </tr>
                   </c:forEach>
                   </tbody>
                   <tfoot>
	                    <tr>
	                        <th>Cod.</th>
	                        <th>Entidade</th>
	                        <th>Cidade/UF</th>
	                        <th>Situacao</th>
	                        <c:if test="${status != 'NAOPLANEJADA'}">
	                        <th>
	                        <c:if test="${status == 'IMPLANTADA'}">
	                        Desde
	                        </c:if>
	                        <c:if test="${status == 'PLANEJADA'}">
	                        Previsão
	                        </c:if>
	                        </th>
	                        </c:if>
	                        <th>&nbsp;</th>
	                    </tr>
                    </tfoot>
               </table>
           </div>
       </div>
       </div>
          </c:if>
          <c:if  test="${empty listaMetas}">
         	<div class="panel panel-primary">
              <div class="panel-heading">
                  Mensagem
              </div>
              <!-- /.panel-heading -->
              <div class="panel-body">
               Não há entidades nessa situação.
           </div>
       </div>
         </c:if>
  </div>
	    
	    
<!-- Page-Level Plugin Scripts - Tables -->
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/pdfmake.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/vfs_fonts.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.13/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.13/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/dataTables.buttons.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.colVis.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.html5.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.print.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Responsive-2.1.1/js/dataTables.responsive.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Select-1.2.0/js/dataTables.select.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').dataTable({
            "language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            }
        });
    });
    </script>
	   