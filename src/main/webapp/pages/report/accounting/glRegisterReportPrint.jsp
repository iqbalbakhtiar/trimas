<%@ include file="/common/tld-common.jsp"%>
<table id="size" value="${adapter.size}" width="100%" cellpadding="3" cellspacing="0">
<thead>
	<tr>
		<td colspan="7" class="pageTitle"><strong>${reportTitle}</strong></td>
	</tr>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<tr>
		<td nowrap="nowrap"><spring:message code="accountingperiod"/></td>
		<td>:</td>
		<td colspan="5">
		<c:forEach items='${criteria.accountingPeriods}' var='period'>
			<input name="periods" value="${period.id}" class="periods" style="display: none;"/>
			   <c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${period.name}"/>
		</c:forEach>
		</td>
	</tr>
	<tr>			  
		<td><spring:message code="accreport.organization"/></td>
		<td>:</td>
		<td colspan="5">
			<c:forEach items='${criteria.organizations}' var='org'>
				<input name="organizations" value="${org.id}" class="organizations" style="display: none;"/>
				<c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${org.fullName}"/>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td colspan="7">&nbsp;</td>
	</tr>
	<tr>
		<th width="5%" align="center" style="border-bottom: 1px black solid;"><spring:message code="accreport.account"/></th>
		<th width="71%" colspan="3" align="center" style="border-bottom: 1px black solid;"><spring:message code="accreport.referenceno"/></th>
		<th width="8%" align="right" style="border-bottom: 1px black solid;"><spring:message code="accreport.debit"/></th>	
		<th width="8%" align="right" style="border-bottom: 1px black solid;"><spring:message code="accreport.credit"/></th>
		<th width="8%" align="right" style="border-bottom: 1px black solid;"><spring:message code="accreport.balance"/></th>
	</tr>
</thead>
<c:forEach items="${adapter.accounts}" var="account" >
	<c:if test='${not empty adapter.openingPeriods}'>
		<tbody class="glreg" id="${account}">
		</tbody>
	</c:if>
</c:forEach >
</table>