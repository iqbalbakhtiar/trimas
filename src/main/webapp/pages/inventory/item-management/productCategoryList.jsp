<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/item-management/productCategoryFilter.jsp"%>

<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		<tr>
			<td width="30%" height="30" align="left" valign="middle">															
				<div class="toolbar-clean">
		         	<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/productcategorypreadd.htm'/>"><span><spring:message code="productcategory.new"/></span></a>
		            </c:if>
		  			<div dojoType="Toggler" targetId="filter">
						<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
					</div>
<%-- 					<a class="item-button-export-xls" href="<c:url value='/page/productcategoryprint.xls'/>"><span><spring:message code="sirius.export"/></span></a> --%>
		         </div>
			</td>
			<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>							
		</tr>
	</table>
</div>
 
<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
	<tr>
		<th width="4%">&nbsp;</th>
  	  	<th width="6%"><spring:message code="sirius.id"/></th>
 	  	<th width="14%"><spring:message code="productcategory"/></th>
  	  	<th width="10%"><spring:message code="sirius.type"/></th>
  	  	<th width="34%"><spring:message code="sirius.note"/></th>
	</tr>
		<c:forEach items="${categorys}" var="cat">
			<tr>
				<td class="tools">
					<c:if test='${access.edit}'>
						<a class="item-button-edit"  href="<c:url value='/page/productcategorypreedit.htm?id=${cat.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
					</c:if>
					<c:if test='${access.delete && cat.delete}'>
						<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/productcategorydelete.htm?id=${cat.id}'/>');"><span><spring:message code="sirius.delete"/></span></a>
					</c:if>
				</td>
				<td nowrap="nowrap"><c:out value='${cat.code}'/></td>        
				<td nowrap="nowrap"><c:out value='${cat.name}'/></td>
				<td nowrap="nowrap"><c:out value='${cat.type}'/></td>
				<td><c:out value='${cat.note}'/></td>					
			</tr>
		</c:forEach>
		<tr class="end-table"><td colspan="9">&nbsp;</td></tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
	<tr>
		<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>