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
	<tr>
		<th width="1%"><div style="width: 45px">&nbsp;</div></th>
  	  	<th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
	  	<th width="25%" nowrap="nowrap"><spring:message code="product.name"/></th>
  	  	<th width="5%" nowrap="nowrap"><spring:message code="product.uom"/></th>
  	  	<th width="10%" nowrap="nowrap"><spring:message code="productcategory"/></th>
  	  	<th width="45%" nowrap="nowrap"><spring:message code="product.uom"/></th>
	</tr>
	<c:forEach items="${products}" var="product">
	<tr>
	 	<td class="tools">
    	<c:if test='${access.edit}'>
        	<a class="item-button-edit" href="<c:url value='/page/productpreedit.htm?id=${product.id}'/>" title="<spring:message code="sirius.edit"/>"><span><spring:message code="sirius.edit"/></span></a>
        </c:if>
		<c:if test='${access.delete and product.deleteable}'>
        	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/productdelete.htm?id=${product.id}'/>', '<spring:message code="notif.delete"/>');" title="<spring:message code="sirius.delete"/>"><span><spring:message code="sirius.delete"/></span></a>
        </c:if>
	 	</td>
	 	<td nowrap="nowrap">${product.code}</td>
		<td nowrap="nowrap">${product.name}</td>
		<td nowrap="nowrap">${product.unitOfMeasure.name}</td>
		<td nowrap="nowrap">${product.productCategory.name}</td>
		<td nowrap="nowrap">
			<c:if test="${product.enabled}"><div style="color: green;"><spring:message code="sirius.active"/></div></c:if>
			<c:if test="${!product.enabled}"><div style="color: red;"><spring:message code="sirius.inactive"/></div></c:if>
		</td>
	</tr>
	</c:forEach>
	<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
  	</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>