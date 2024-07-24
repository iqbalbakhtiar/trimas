<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 220px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td align="right" style="WIDTH:130px;" nowrap="nowrap"><spring:message code="bankaccount.code"/> : </td>
		<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="50" class="inputbox"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;" nowrap="nowrap"><spring:message code="bankaccount.accountname"/> : </td>
		<td><input type="text" id="name" name="name" value="${filterCriteria.name}" size="50" class="inputbox"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;" nowrap="nowrap"><spring:message code="bankaccount.bankname"/> : </td>
		<td width="310"><input type="text" id="bank" name="bank" value="${filterCriteria.bank}" class="inputbox"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;" nowrap="nowrap"><spring:message code="bankaccount.holder"/> : </td>
		<td width="310"><input type="text" id="holder" name="holder" value="${filterCriteria.holder}" class="inputbox"/></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td>&nbsp;</td>
		<td align="left">
			<%@ include file="/common/filter.jsp"%>
		</td>
	</tr>
	</table>
	</form>
</div>
