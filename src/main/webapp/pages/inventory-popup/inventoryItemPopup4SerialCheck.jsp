<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/filterandpaging.jsp"%>
</head>
<body>
<div id="se-containers_pick">
	<div id="se-r00">
		<div id="se-r01a">&nbsp;</div>
		<div id="se-r03">&nbsp;</div>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td align="right"><spring:message code="barcode"/></td>
									  	<td align="center">:&nbsp;</td>
										<td align="left"><input type="text" id="serialNo" name="serialNo" value="${filterCriteria.serialNo}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right"><spring:message code="product.name"/></td>
									  	<td align="center">:&nbsp;</td>
										<td align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right"><spring:message code="product.lot"/></td>
									  	<td align="center">:&nbsp;</td>
										<td align="left"><input type="text" id="lotCode" name="lotCode" value="${filterCriteria.lotCode}" size="10"/></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td align="left">
											<%@ include file="/common/button.jsp"%>
											<input type="button" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.ok"/>' onclick="addBarcodes();"/></td>
										</td>
									</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"><strong>Total Ticked : <span id="totalTicked">0</span></strong></td>
							<td width="66%" align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>

						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="1%"><input type="checkbox" name="checkMaster" class="checkall"/></th>
					  		<th width="10%"><spring:message code="barcode"/></th>
                          	<th width="5%"><spring:message code="product.lot"/></th>
                          	<th width="15%"><spring:message code="product.name"/></th>
                          	<th style="text-align: right;" width="15%"><spring:message code="product.quantity"/></th>
                          	<th style="text-align: center;" width="15%"><spring:message code="product.uom"/></th>
                          	<th width="10%"><spring:message code="container"/></th>
				  		</tr>
						<c:forEach items="${onhands}" var="onhand">
						<tr>
					  		<td><input id="${onhand.id}" type="checkbox" name="checkBarcode" onclick="tickBarcode('${onhand.id}');" class="barcodes"/></td>
					  		<td nowrap="nowrap"><c:out value='${onhand.lot.serial}'/></td>
					  		<td nowrap="nowrap"><c:out value='${onhand.lot.code}'/></td>
                            <td nowrap="nowrap"><c:out value='${onhand.product.name}'/></td>
                            <td style="text-align: right;" nowrap="nowrap"><fmt:formatNumber value='${onhand.onHand}' pattern=',##0.00'/></td>
                            <td style="text-align: center;" nowrap="nowrap"><c:out value='${onhand.product.unitOfMeasure.measureId}'/></td>
                            <td nowrap="nowrap"><c:out value='${onhand.container.name}'/></td>
					  	</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
					  	</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/sirius-footer.jsp"%>
</div>
</body>
</html>
<script type="text/javascript" src="<c:url value='/js/product.js'/>"></script>
<script type="text/javascript">
	$(function(){
		checkSelected();
		$('.checkall').click(function () {
	        $('.barcodes').prop("checked", this.checked);
	        
	        $('.barcodes').each(function() {
	        	tickBarcode($(this).attr('id'));
	        });
	    });
	});
	
	function checkSelected()
	{
		var selectedRequisitions = self.opener.selectedRequisitions;
		for (var i = selectedRequisitions.length - 1; i >= 0; i--) 
		{
			var checkRequisition = document.getElementById(selectedRequisitions[i]);
	
			if(checkRequisition)
				checkRequisition.checked = true;
		}
		
		$('#totalTicked').text(selectedRequisitions.length);
	}
	
	function tickBarcode(id)
	{
		var checkBarcode = document.getElementById(id);
		var selectedBarcodes = self.opener.selectedBarcodes;
		
		if(checkBarcode.checked)
		{
			//Check already add to table
			if(checkSelectedStatus(id))
			{
				alert("Item has been selected !!!");
				checkBarcode.checked = false;
			}
			else {
				//Push only if data isn't in array
				if(!selectedBarcodes.includes(id))
					selectedBarcodes.push(id);
			}
		}
		else
		{
			var index = selectedBarcodes.indexOf(id);
			if(index > -1)
				selectedBarcodes.splice(index, 1);
		}
		
		$('#totalTicked').text(selectedBarcodes.length);
	}
	
	function checkSelectedStatus(id)
	{
		var requisitionItemIds = self.opener.document.getElementsByClassName('barcodes');
	
		for (var i = requisitionItemIds.length - 1; i >= 0; i--) {
			if(requisitionItemIds[i].value == id)
				return true;
		}
	
		return false;
	}
	
	function addBarcodes()
	{
		var selectedBarcodes = self.opener.selectedBarcodes;
		var referenceId = self.opener.refId;
		
		for (var i = selectedBarcodes.length - 1; i >= 0; i--) 
		{
			var index = self.opener.index;
			self.opener.addLine();
	
			setclient(index, selectedBarcodes[i]);
		}
		
		window.close();
	}

	function setclient(index, id)
	{
		RequisitionItem.load(id).then(requisition => {
			let _client = self.opener.document.getElementById('reference['+index+']');
			if(_client)
			{
				_client.remove(_client.selectedIndex);

				let _opt = document.createElement('option');
				_opt.value = requisition.id;
				_opt.text = requisition.product.name;

				_client.appendChild(_opt);

				_client.dispatchEvent(new Event('change'));
			}

			let _uom = self.opener.document.getElementById('uom['+index+']');
			if(_uom)
				_uom.value = requisition.product.unitOfMeasure.measureId;

			let _quantity = self.opener.document.getElementById('quantity['+index+']');
			if(_quantity)
				_quantity.value = requisition.quantity.numberFormat('#,#');

			window.close();
		});
	}
</script>