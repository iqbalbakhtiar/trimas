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
			<td align="right" width="16%">Person In Charge&nbsp;&nbsp;</td>
			<td width="4%">:&nbsp;</td>
			<td width="40%">
				<select id="person" name="pic" class="combobox-ext">
				</select>
				&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popuppersonview.htm?target=person&partyRole=33'/>');"  title="Person In Charge" />
			</td>
		</tr> 
		<tr>
			<td align="right" width="16%">Category&nbsp;&nbsp;</td>
			<td width="4%">:&nbsp;</td>
			<td width="40%">
				<select id="category" name="category" class="combobox-ext">
				</select>
				&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/fixedassetcategorypopup.htm?target=category'/>');"  title="Fixed Asset Category" />
			</td>
		</tr>  
		<tr>
			<td align="right">Name&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right">Barcode / Legend&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="legend" name="legend" value="${filterCriteria.legend}" size="35" class="inputbox"/></td>
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
