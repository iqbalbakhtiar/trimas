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
	<script type="text/javascript">
		function setclient(id)
		{
			Product.inventory(id);
			var inventory = Product.data;

			if(!jQuery.isEmptyObject(inventory))
			{
				var _serial = self.opener.document.getElementById('${param.target}');
				if(_serial)
				{
					_serial.remove(_serial.selectedIndex);
					var _opt = document.createElement('option');
					
					if(inventory.serial != null)
					{
						_opt.value = inventory.serial;
						_opt.text = inventory.serial;
					}
	
					_serial.appendChild(_opt);

					const duplicate = String.duplicate('barcodes');

			        if(duplicate) {
			            alert('<spring:message code="barcode"/> ('+duplicate+') <spring:message code="notif.duplicate"/> !!!');
			            _serial.remove(_serial.selectedIndex);
			            
			            return;
			        }
				}

				var _client = self.opener.document.getElementById('product[${param.index}]');
				if (_client) {
					console.log('>>> '+_client.getAttribute('readonly'));
					if (_client.getAttribute('readonly') === 'null' && _client.tagName === 'SELECT') {
						_client.options.length = 0;
						var _opt = document.createElement('option');
						_opt.value = inventory.product.id;
						_opt.text = inventory.product.name;
						_client.appendChild(_opt);
					}
				}
				
				var _onHand = self.opener.document.getElementById('onHand[${param.index}]');
				if(_onHand)
					_onHand.value = inventory.onHand.numberFormat('#,#.00');
				
				var _quantity = self.opener.document.getElementById('quantity[${param.index}]');
				if(_quantity)
					_quantity.value = inventory.onHand.numberFormat('#,#.00');
				
				var _uom = self.opener.document.getElementById('uom[${param.index}]');
				if(_uom)
					_uom.value = inventory.product.unitOfMeasure.measureId;

				var _container = self.opener.document.getElementById('container[${param.index}]');
				if (_container) {
					if (_container.tagName === 'SELECT') {
						_container.options.length = 0;
						var _opt = document.createElement('option');
						_opt.value = inventory.container.containerId;
						_opt.text = inventory.container.containerName;
						_container.appendChild(_opt);
					}
				}

				var _lot = self.opener.document.getElementById('lotCode[${param.index}]');
				if(_lot)
					_lot.value = inventory.lotCode;
				
				var _productId = self.opener.document.getElementById('productId[${param.index}]');
				if(_productId)
					_productId.value = inventory.product.id;
				
				var _productCode = self.opener.document.getElementById('productCode[${param.index}]');
				if(_productCode)
					_productCode.value = inventory.product.code;
				
				let requestData = {
		            productId: inventory.product.id,
		            containerId: inventory.container.containerId,
		            serial:inventory.serial
		        };
				
				var _price = self.opener.document.getElementById('price[${param.index}]');
				if(_price) {
					$.ajax({
						url:"<c:url value='/page/stockadjustmentbyproductjson.htm'/>",
						data:requestData,
						method : 'GET',
						dataType : 'json',
						success : function(json) {
							if(json)
							{
								if(json.status == 'OK'){
									if(json.product != null)
										_price.value = parseFloat(json.product.price).numberFormat('#,##0.00');
									else
										_price.value = parseFloat(0).numberFormat('#,##0.00');
								}
							}
						}
					});
				}
				
				
				if(self.opener.calculateTotals)
					self.opener.calculateTotals();
				

				if(_serial) {
					const event = new Event('change');
					_serial.dispatchEvent(event);
				}
			}
			
			window.close();
		}
	</script>
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
						<form id="filterPopup" name="filterPopup" method="post" action="/page/.htm?target=${target}">
							<table width="100%" cellspacing="0" cellpadding="1" align="center">
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
								<td align="left"><%@ include file="/common/button.jsp"%></td>
							</tr>
							</table>
						</form>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="1%">&nbsp;</th>
					  		<th width="10%"><spring:message code="barcode"/></th>
                          	<th width="5%"><spring:message code="product.lot"/></th>
                          	<th width="15%"><spring:message code="product.name"/></th>
                          	<th style="text-align: right;" width="15%"><spring:message code="product.quantity"/></th>
                          	<th style="text-align: center;" width="15%"><spring:message code="product.uom"/></th>
                          	<th width="10%"><spring:message code="container"/></th>
				  		</tr>
						<c:forEach items="${onhands}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
					  		<td nowrap="nowrap"><c:out value='${com.lot.serial}'/></td>
					  		<td nowrap="nowrap"><c:out value='${com.lot.code}'/></td>
                            <td nowrap="nowrap"><c:out value='${com.product.name}'/></td>
                            <td style="text-align: right;" nowrap="nowrap"><fmt:formatNumber value='${com.onHand}' pattern=',##0.00'/></td>
                            <td style="text-align: center;" nowrap="nowrap"><c:out value='${com.product.unitOfMeasure.measureId}'/></td>
                            <td nowrap="nowrap"><c:out value='${com.container.name}'/></td>
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