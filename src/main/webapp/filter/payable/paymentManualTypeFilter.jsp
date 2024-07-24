<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 32%; height: 260px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" style='border:none;'>
		<tr>
			<td align="right"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.name"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="name" name="name" value="${filterCriteria.name}" size="30" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code='glaccount.account.name'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="account" name="account" value="${filterCriteria.account}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="paymentreferencetype"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="referenceType" name='referenceType'>
					<option value=''><spring:message code="sirius.all"/></option>
					<c:forEach items="${referenceTypes}" var="refType">
						<option value='${refType}' ${not empty filterCriteria.referenceType and filterCriteria.referenceType eq refType ? "selected" : ""}><spring:message code="paymentreferencetype.${refType.messageName}"/></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.active"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="status" name="status">
					<option value=""><spring:message code="sirius.all"/></option>
					<option value="true" ${not empty filterCriteria.status and filterCriteria.status ? "selected" : ""}><spring:message code="sirius.yes"/></option>
					<option value="false" ${not empty filterCriteria.status and !filterCriteria.status ? "selected" : ""}><spring:message code="sirius.no"/></option>
				</select>
	        </td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="3" align="center">
				<%@ include file="/common/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/paymentmanualtypeview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/paymentmanualtypeview.htm'/>";
	}
			
	function step(action)
	{
		var page="1";
				
		if(action == "next")
			page="${filterCriteria.nextPage}";
		else if(action == "prev")
			page="${filterCriteria.prevPage}";
		else if(action == "last")
			page="${filterCriteria.totalPage}";					
			
		document.filterForm.action = "<c:url value='/page/paymentmanualtypeview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	