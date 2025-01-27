<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 220px; left: 300px;display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right" ><spring:message code="sirius.id"/> &nbsp;</td>
			<td width="5%" align="center">:</td>
			<td ><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
		  <td align="right" >Category &nbsp;</td>
		  <td align="center">:</td>
		  <td ><input type="text" id="name" name="name" value="${filterCriteria.name}" size="30" class="inputbox"/></td>
		 </tr>
		<tr>
			<td align="right" ><spring:message code="sirius.type"/>&nbsp;</td>
			<td width="5%" align="center">:</td>
			<td >
				<select id="type" name="type" class="combobox">
					<option value=""><spring:message code="sirius.all"/></option>
                    <option value="STOCK" ${not empty filterCriteria.type and filterCriteria.type == "STOCK"? "selected" : ""}>STOCK</option>
					<option value="NONSTOCK" ${not empty filterCriteria.type and filterCriteria.type != "STOCK"? "selected" : ""}>NON STOCK</option>
				</select>
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
