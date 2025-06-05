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
						<form id="filterPopup" name="filterPopup" method="post" action="/page/.htm?target=${target}">
							<table width="100%" cellspacing="0" cellpadding="1" align="center">
							<tr>
								<td align="right"><spring:message code="product.code"/></td>
								<td align="center">:</td>
								<td align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="product.name"/></td>
								<td align="center">:&nbsp;</td>
								<td align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td align="left">
									<%@ include file="/common/button.jsp"%>
								</td>
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
					  		<th width="4%">&nbsp;</th>
			  		  	  	<th width="20%" nowrap="nowrap"><spring:message code="product.code"/></th>
		  		  	  	  	<th width="40%" nowrap="nowrap"><spring:message code="product.name"/></th>
		  		  	  	  	<th width="20%" nowrap="nowrap"><spring:message code="container"/></th>
		  		  	  	  	<th width="20%" nowrap="nowrap"><spring:message code="product.lot"/></th>
                          	<th width="19%" nowrap="nowrap"><spring:message code="product.available"/></th>
				  		</tr>
						<c:forEach items="${onhands}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td nowrap="nowrap"><c:out value='${com.product.code}'/></td> 
							<td nowrap="nowrap"><c:out value='${com.product.name}'/></td>
							<td nowrap="nowrap"><c:out value='${com.container.name}'/></td>
							<td nowrap="nowrap"><c:out value='${com.lot.code}'/></td>
                            <td nowrap="nowrap"><fmt:formatNumber value='${com.availableSale}' pattern='${com.product.unitOfMeasure.name}'/></td>
                        </tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
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
<script type="text/javascript">
	function setclient(id)
	{
		Product.inventory(id);
		
		var inventory = Product.data;

		if(!jQuery.isEmptyObject(inventory))
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				var _opt = document.createElement('option');
				_opt.value = inventory.product.id;
				_opt.text = inventory.product.name;

				_client.appendChild(_opt);
				_client.dispatchEvent(new Event('change'));
			}

			var _code = self.opener.document.getElementById('productCode[${param.index}]');
			if(_code)
				_code.value = inventory.product.productCode;

			var _cat = self.opener.document.getElementById('productCategory[${param.index}]');
			if(_cat)
				_cat.value = inventory.product.categoryName;

			var _onhand = self.opener.document.getElementById('onHand[${param.index}]');
			if(_onhand)
				_onhand.value = parseFloat(inventory.available).numberFormat('#,##.00000');
			
			var _qtyPcs = self.opener.document.getElementById('qtyPcs[${param.index}]');
			if(_qtyPcs){
				_qtyPcs.value = parseFloat(inventory.available).numberFormat('#,##');
			}

			var _base = self.opener.document.getElementById('base[${param.index}]');
			if(_base)
				_base.value = inventory.product.qtyToBase;

			var _quantity = self.opener.document.getElementById('quantity[${param.index}]');
			if(_quantity)
			{
                _quantity.setAttribute('decimal', inventory.product.isDecimal);
                _quantity.setAttribute('pattern', inventory.product.pattern);
                $(_quantity).change();

				if(inventory.product.isDecimal)
					_quantity.value = '0.000';
				else
					_quantity.value = '0';		

			}

			var _container = self.opener.document.getElementById('container[${param.index}]');
			if(_container)
			{
				_container.remove(_container.selectedIndex);
				var _ctr = document.createElement('option');
				_ctr.value = inventory.container.containerId;
				_ctr.text = inventory.container.containerName;

				_container.appendChild(_ctr);
				_container.dispatchEvent(new Event('change'));
			}

			var _uom = self.opener.document.getElementById('uom[${param.index}]');
			if(_uom)
				_uom.value = inventory.product.unitOfMeasure.name;
			
			/*var _lot= self.opener.document.getElementById('code[${param.index}]');
			if(_lot)
				_lot.value = inventory.lotCode;

			var _price = self.opener.document.getElementById('price[${param.index}]');
			if(_price)
				_price.value = parseFloat(inventory.price).numberFormat('#,##.00000');*/			
		}
		
		window.close();
	}
</script>