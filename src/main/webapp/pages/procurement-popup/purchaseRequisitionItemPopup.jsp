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
								<form id="popupForm" name="popupForm" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="15%" align="right"><spring:message code="sirius.code"/></td>
									  	<td width="5%" align="center">:</td>
								  	  	<td width="65%" height="28" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right"><spring:message code="purchaserequisition.requisitioner"/></td>
										<td>:&nbsp;</td>
										<td><input type="text" id="requisitionerName" name="requisitionerName" value="${filterCriteria.requisitionerName}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>
											<%@ include file="/common/button.jsp"%>
											<input type="button" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.ok"/>' onclick="addRequisitions();"/></td>
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
				            <th width="12%" nowrap="nowrap"><spring:message code="product"/></th>
				            <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
				            <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
			                <th width="8%"><spring:message code="sirius.code"/></th>
			                <th width="8%"><spring:message code="sirius.date"/></th>
                       	  	<th width="20%"><spring:message code="sirius.note"/></th>
				  		</tr>
						<c:forEach items="${requisitions}" var="item">
						<tr>
					  		<td><input id="${item.id}" type="checkbox" name="checkRequisitions" onclick="tickRequisition('${item.id}');" class="requisitionItems"/></td>
			                <td><c:out value="${item.product.name}"/></td>
							<td><fmt:formatNumber value='${item.quantity}' pattern=',##0'/></td>
			                <td><c:out value="${item.product.unitOfMeasure.measureId}"/></td>
							<td><c:out value='${item.purchaseRequisition.code}'/></td>
                            <td><fmt:formatDate value='${item.purchaseRequisition.date}' pattern='dd-MM-yyyy'/></td>
			                <td><c:out value="${item.note}"/></td>
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
<script type="text/javascript" src="<c:url value='/js/requisition.js'/>"></script>
<script type="text/javascript">
	$(function(){
		//checkSelected();
		
		$('.checkall').click(function () {
	        $('.requisitionItems').prop("checked", this.checked);
	        
	        $('.requisitionItems').each(function() {
	        	tickRequisition($(this).attr('id'));
	        });
	    });
	});
	
	/* function checkSelected()
	{
		var selectedRequisitions = self.opener.selectedRequisitions;
		for (var i = selectedRequisitions.length - 1; i >= 0; i--) 
		{
			var checkRequisition = document.getElementById(selectedRequisitions[i]);
	
			if(checkRequisition)
				checkRequisition.checked = true;
		}
		
		$('#totalTicked').text(selectedRequisitions.length);
	} */
	
	function tickRequisition(id)
	{
		var checkRequisition = document.getElementById(id);
		var selectedRequisitions = self.opener.selectedRequisitions;
		
		if(checkRequisition.checked)
		{
			//Check already add to table
			if(checkSelectedStatus(id))
			{
				alert("Item has been selected !!!");
				checkRequisition.checked = false;
			}
			else {
				//Push only if data isn't in array
				if(!selectedRequisitions.includes(id))
					selectedRequisitions.push(id);
			}
		}
		else
		{
			var index = selectedRequisitions.indexOf(id);
			if(index > -1)
				selectedRequisitions.splice(index, 1);
		}
		
		$('#totalTicked').text(selectedRequisitions.length);
	}
	
	function checkSelectedStatus(id)
	{
		var requisitionItemIds = self.opener.document.getElementsByClassName('requisitions');
	
		for (var i = requisitionItemIds.length - 1; i >= 0; i--) {
			if(requisitionItemIds[i].value == id)
				return true;
		}
	
		return false;
	}
	
	function addRequisitions()
	{
		var selectedRequisitions = self.opener.selectedRequisitions;
		for (var i = selectedRequisitions.length - 1; i >= 0; i--) 
		{
			var index = self.opener.index;
			self.opener.addLine();
	
			setclient(index, selectedRequisitions[i]);
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