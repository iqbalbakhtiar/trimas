<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 40%; height: 230px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td width="35%" align="right"><spring:message code="transferorder.code"/>&nbsp;&nbsp;</td>
			<td width="5%">:&nbsp;</td>
			<td width="60%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="makloonandsteam.category"/></td>
			<td>:&nbsp;</td>
			<td>
				<select id="category" name="category">
					<option value=""></option>
					<option value="MAKLOON">MAKLOON</option>
					<option value="STEAM">STEAM</option>
				</select>
			</td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/>&nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
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
<script type="text/javascript">
	document.getElementById('category').value = '${filterCriteria.category}';
</script>