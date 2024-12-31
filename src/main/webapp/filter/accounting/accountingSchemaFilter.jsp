<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 35%; height: 210px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="accountingschema.code"/> </td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input name="code" id="code" class="inputbox" value="${filterCriteria.code}"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="accountingschema.name"/> </td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="name" name="name" class="inputbox" value="${filterCriteria.name}"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="accountingschema.organization"/> </td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28">
			<select id="org" name="organization" class="combobox-ext">
			</select>
			<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructureview.htm?target=org'/>');"  title="Company Structure" />
		</td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td colspan="2">&nbsp;</td>
		<td align="left">
			<%@ include file="/common/filter.jsp"%>
		</td>
	</tr>
	</table>
	</form>
</div>
