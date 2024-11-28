<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/item-management/productFilter.jsp"%>
<div class="item-navigator">
	  <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		<tr>
			<td width="30%" height="30" align="left" valign="middle">															
				<div class="toolbar-clean">
					<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/productpreadd.htm' />"><span><spring:message code="product.new"/></span></a>
					</c:if>
					<c:if test='${access.add and pictures.edit}'>
						<a class="item-button-add-gl-child" ><span>Picture Correction</span></a>
					</c:if>
		  			<div dojoType="Toggler" targetId="filter">
						<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
					</div>
				</div>
			</td>
			<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>		
		</tr>
	</table>
</div>
<div style="overflow-x: auto;">
	<table style="width:100%;" cellpadding="0" cellspacing="0" class="table-list">
		<tr valign="top">
			<th width="2%">&nbsp;</th>
	  	  	<th width="3%"><spring:message code="product.code"/></th>
		  	<th width="5%"><spring:message code="product.name"/></th>
		  	<th width="2%"><spring:message code="sirius.uom"/></th>
		  	<th width="2%"><spring:message code="sirius.type"/></th>
		  	<th width="4%"><spring:message code="productcategory"/></th>
		  	<th width="3%"><spring:message code="sirius.status"/></th>
<%-- 		  	<th width="3%"><spring:message code="product.base"/></th> --%>
		  	<th width="3%"><spring:message code="product.origin"/></th>
		  	<th width="3%"><spring:message code="product.brand"/></th>
		  	<th width="3%"><spring:message code="product.grade"/></th>
		  	<th width="3%"><spring:message code="product.part"/></th>
	  	  	<th width="5%"><spring:message code="sirius.note"/></th>
		</tr>
		<c:forEach items="${products}" var="product">
		<tr valign="top">
		 	<td class="tools">
	        	<c:if test='${access.edit}'>
	            	<a class="item-button-edit" href="<c:url value='/page/productpreedit.htm?id=${product.id}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>                               
	            </c:if>
				<c:if test='${access.delete && product.status}'>
	            	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/productdelete.htm?id=${product.id}'/>');" title="Delete"><span><spring:message code="sirius.delete"/></span></a>                             
	            </c:if>			
	        </td>
		 	<td nowrap="nowrap">${product.code}</td>
			<td nowrap="nowrap">${product.name}</td>
		  	<td nowrap="nowrap">${product.unitOfMeasure.measureId}</td>
		  	<td nowrap="nowrap">${product.type}</td>
		  	<td nowrap="nowrap">${product.productCategory.name}</td>
			<td nowrap="nowrap"><spring:message code="sirius.${product.status ? 'active' : 'inactive'}"/></td>
<%-- 			<td nowrap="nowrap"><spring:message code="sirius.${product.base ? 'active' : 'inactive'}"/></td> --%>
			<td nowrap="nowrap">${product.origin}</td>
			<td nowrap="nowrap">${product.brand}</td>
			<td nowrap="nowrap">${product.grade}</td>
			<td nowrap="nowrap">${product.part}</td>
			<td nowrap="nowrap">${product.note}</td>
		</tr>
		</c:forEach>
		<tr class="end-table"><td colspan="18">&nbsp;</td></tr>
  	</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>