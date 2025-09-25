<%@ include file="/common/sirius-popup-top.jsp"%>
<form id="filterPopup" name="filterPopup" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="130" align="right" style="WIDTH:130px;"><spring:message code="sirius.name"/></td>
			<td width="7">:&nbsp;</td>
			<td width="295" height="28" align="left">
				<input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/>
			</td>
		</tr>
		<tr>
			<td width="130" align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/></td>
			<td width="7">:&nbsp;</td>
			<td width="295" height="28" align="left">
				<input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/>
			</td>
		</tr>
		<tr>
			<td width="130" align="right" style="WIDTH:130px;"></td>
			<td width="7"></td>
			<td width="295" height="28" align="left">
				<%@ include file="/common/button.jsp"%>
			</td>
		</tr>
	</table>
</form>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
	</tr>
</table>

<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
	<tr>
		<th width="4%">&nbsp;</th>
		<th width="12%"><spring:message code="product.code"/></th>
		<th width="20%"><spring:message code="product.name"/></th>
		<th width="10%"><spring:message code="product.category"/></th>
		<th width="7%"><spring:message code="container"/></th>
		<th width="7%"><spring:message code="product.onhand"/></th>
		<th width="7%"><spring:message code="product.serial"/></th>
	</tr>
	<c:forEach items="${products}" var="inv">
		<tr>
			<td class="tools">
				<a class="item-button-add-row" href="javascript:setclient('${inv.product.id}','${inv.product.code}','${inv.product.name}','${inv.product.qtyToBase}','${inv.onHand}','${inv.product.unitOfMeasure.measureId}','${inv.product.productCategory.name}','${inv.product.serial}','${inv.grid.name}','${inv.container.id}','${inv.container.name}')"  title="Edit"><span>Edit</span></a>
			</td>
			<td nowrap="nowrap"><c:out value='${inv.product.code}'/></td>
			<td nowrap="nowrap"><c:out value='${inv.product.name}'/></td>
			<td nowrap="nowrap"><c:out value='${inv.product.productCategory.name}'/></td>
			<td nowrap="nowrap"><c:out value='${inv.container.name}'/></td>
			<td nowrap="nowrap"><fmt:formatNumber value='${inv.onHand}' pattern=',##0.00'/></td>
			<td nowrap="nowrap">
				<c:if test="${inv.product.serial}"><spring:message code="sirius.yes"/></c:if>
				<c:if test="${!inv.product.serial}"><spring:message code="sirius.no"/></c:if>
			</td>
		</tr>
	</c:forEach>
	<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
	</tr>
</table>
<%@ include file="/common/sirius-popup-bottom.jsp"%>

<script type="text/javascript">
	function setclient(id,code,name,qtyBase,onhand,uom,category,serial,grid,containerId,containerName)
	{
		if(id && name)
		{
			var _client = self.opener.document.getElementById('${target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = id;
				_opt.text = name.replaceAll("*", "\"");;
				
				_client.appendChild(_opt);
			}
			
			var sourceId = $('#source').val();  
			var sourceName = $('#source option:selected').text();  
			
			var _onhand = self.opener.document.getElementById('onhand[${index}]');
			var _serial = self.opener.document.getElementById('serialCheck[${index}]');
			var _uom = self.opener.document.getElementById('uom[${index}]');
			var _qtyBase = self.opener.document.getElementById('qtyToBase[${index}]');
			var _code = self.opener.document.getElementById('codeprod[${index}]');
			var _category = self.opener.document.getElementById('category[${index}]');
			var _gridFrom = self.opener.document.getElementById('gridFrom[${index}]');
			//var _container = self.opener.document.getElementById('container[${index}]');
			var _source = self.opener.document.getElementById('source[${index}]');

			if(_onhand)
				_onhand.value = parseFloat(onhand).numberFormat('#,##0.00');
			
			if(_uom)
				_uom.value = uom;
				
			if(_qtyBase && qtyBase)
				_qtyBase.value = parseFloat(qtyBase).numberFormat('#,##0.00');

			if(_code)
				_code.value = code;
				
			if(_serial)
				_serial.value = serial;
			
			if(_category)
				_category.value = category;
			
			if(_gridFrom)
				_gridFrom.value = grid;
			
			/*if(_container)
			{
				_container.remove(_source.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = containerId;
				_opt.text = containerName.replaceAll("*", "\"");;
				
				_container.appendChild(_opt);
			}*/
			
			if(_source)
			{
				_source.remove(_source.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = sourceId;
				_opt.text = sourceName.replaceAll("*", "\"");;
				
				_source.appendChild(_opt);
			}

			var event = new Event('change');
			_client.dispatchEvent(event);
		}
		
		window.close();
	}
</script>