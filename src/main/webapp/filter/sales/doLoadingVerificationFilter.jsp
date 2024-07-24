<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 45%; height: 330px; left: 525px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="1" cellpadding="1" align="right">
		<tr>
			<td align="right">ID&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right" width="16%">Company&nbsp;&nbsp;</td>
			<td width="4%">:&nbsp;</td>
			<td width="40%">
				<select id="org" name="organization" class="combobox-ext">
				</select>
				&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right">Sign By&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="signBy" name="signBy" value="${filterCriteria.signBy}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right">Driver&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="driver" name="driver" value="${filterCriteria.driver}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right">Vehicle&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="vehicle" name="vehicle" value="${filterCriteria.vehicle}" size="35" class="inputbox"/></td>
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
