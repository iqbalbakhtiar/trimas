<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/item-management/brandFilter.jsp"%>
<div class="item-navigator">
	  <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		<tr>
			<td width="30%" height="30" align="left" valign="middle">															
				<div class="toolbar-clean">
					<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/brandpreadd.htm' />"><span><spring:message code="brand.new"/></span></a>
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
  	  	<th width="10%" nowrap="nowrap"><spring:message code="brand.code"/></th>
	  	<th width="25%" nowrap="nowrap"><spring:message code="brand.name"/></th>
  	  	<th width="45%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
	</tr>
	<c:forEach items="${brands}" var="brand">
	<tr>
	 	<td class="tools">
    	<c:if test='${access.edit}'>
        	<a class="item-button-edit" href="<c:url value='/page/brandpreedit.htm?id=${brand.id}'/>" title="<spring:message code="sirius.edit"/>"><span><spring:message code="sirius.edit"/></span></a>
        </c:if>
		<c:if test='${access.delete and brand.delete}'>
        	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/branddelete.htm?id=${brand.id}'/>', '<spring:message code="notif.delete"/>');" title="<spring:message code="sirius.delete"/>"><span><spring:message code="sirius.delete"/></span></a>
        </c:if>
	 	</td>
	 	<td nowrap="nowrap">${brand.code}</td>
		<td nowrap="nowrap">${brand.name}</td>
		<td nowrap="nowrap">${brand.note}</td>
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