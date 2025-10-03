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
						<c:set var="url" value="/page/popupproductforadjustmentview.htm?target=${target}&index=${index}&container=${filterCriteria.container}&organization=${filterCriteria.organization}"/>
						<form id="filterPopup" name="filterPopup" method="post" >
							<table width="100%" cellspacing="0" cellpadding="1" align="center">
                                 <tr>
									<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.code"/></td>
									<td width="7">&nbsp;:&nbsp;</td>
									<td width="295" height="28" align="left">
										<input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/>
									</td>
                                <tr>
									<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.name"/></td>
									<td width="7">&nbsp;:&nbsp;</td>
									<td width="295" height="28" align="left">
										<input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/>
									</td>
								</tr>
                                <tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><%@ include file="/common/button.jsp"%></td>
								</tr>
							</table>
						</form>
							
                        <div class="clears">&nbsp;</div>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
						</tr>
						</table>						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="4%">&nbsp;</th>
			  		  	  	<th width="12%">Code</th>
		  		  	  	  	<th width="20%">Name</th>
			  		  	  	<th width="10%">Category</th>
                          	<th width="7%">Available</th>
				  		</tr>
							<c:forEach items="${products}" var="com">
								<tr>
									<td class="tools">
										<a class="item-button-add-row"
										   href="javascript:setclient(
							                   '${com.product.id}',
							                   '${com.product.code}',
							                   '${com.product.name}',
							                   '${com.product.qtyToBase}',
							                   '${com.onHand}',
							                   '${com.product.unitOfMeasure.measureId}',
							                   '${com1.buyingPrice}',
							                   '${com.product.productCategory.name}',
							                   '${com.product.serial}')"
										   title="Edit"><span>Edit</span></a>
									</td>
									<td nowrap="nowrap"><c:out value='${com.product.code}'/></td>
									<td nowrap="nowrap"><c:out value='${com.product.name}'/></td>
									<td nowrap="nowrap"><c:out value='${com.product.productCategory.name}'/></td>
									<td><fmt:formatNumber value='${com.onHand}' pattern=',##0.00000'/></td>
								</tr>
							</c:forEach>
							<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
					  	</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
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
<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
<script type="text/javascript">
	function setclient(id,code,name,qtyBase,onhand,uom,price,category, serial)
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
			
			var _onhand = self.opener.document.getElementById('onhand[${index}]');
			var _serial = self.opener.document.getElementById('serialCheck[${index}]');
			var _uom = self.opener.document.getElementById('uom[${index}]');
			var _price = self.opener.document.getElementById('price[${index}]');
			var _qtyBase = self.opener.document.getElementById('qtyToBase[${index}]');
			var _code = self.opener.document.getElementById('codeprod[${index}]');
			var _category = self.opener.document.getElementById('category[${index}]');
			
			var _pricePcs = self.opener.document.getElementById('amountPcs[${index}]');
			var _qtyPcs = self.opener.document.getElementById('qtyPcs[${index}]');
			
			if(_pricePcs)
				_pricePcs.value = parseFloat(price).numberFormat('#,##.00000');
				
			if(_qtyPcs)
				_qtyPcs.value = parseFloat(onhand).numberFormat('#,##.00000');
			
			if(_onhand)
				_onhand.value = parseFloat(onhand).numberFormat('#,##.00000');
				
			if(_serial)
				_serial.value = serial;
			
			if(_uom)
				_uom.value = uom;
				
			if(_price && price)
				_price.value = parseFloat(price).numberFormat('#,##0.00');
				
			if(_qtyBase && qtyBase)
				_qtyBase.value = parseFloat(qtyBase).numberFormat('#,##0.00');

			if(_code)
				_code.value = code;
			
			if(_category)
				_category.value = category;

			var event = new Event('change');
			_client.dispatchEvent(event);
		}
		
		window.close();
	}
</script>