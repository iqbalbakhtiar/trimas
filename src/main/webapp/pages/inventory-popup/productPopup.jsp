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
							<tr><td>
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
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
							</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="34%" height="30" align="left" valign="middle"></td>
								<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>

						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
							<tr>
								<th width="3%">&nbsp;</th>
								<th width="3%"><spring:message code="product.code"/></th>
								<th width="5%"><spring:message code="product.name"/></th>
								<th width="2%"><spring:message code="sirius.uom"/></th>
								<th width="4%"><spring:message code="productcategory"/></th>
							</tr>
							<c:forEach items="${products}" var="product">
								<tr>
									<td class="tools">
										<a class="item-button-add-row" href="javascript:setclient('${product.id}')"  title="Edit"><span><spring:message code="sirius.edit"/></span></a>
									</td>
									<td nowrap="nowrap">${product.code}</td>
									<td nowrap="nowrap">${product.name}</td>
									<td nowrap="nowrap">${product.unitOfMeasure.measureId}</td>
									<td nowrap="nowrap">${product.productCategory.name}</td>
								</tr>
							</c:forEach>
							<tr class="end-table"><td colspan="11">&nbsp;</td></tr>
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
		Product.load(id);
		var product = Product.data;
	
		if(!jQuery.isEmptyObject(product))
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				var _opt = document.createElement('option');
				_opt.value = product.productId;
				_opt.text = product.productName;

				_client.appendChild(_opt);
				_client.setAttribute("serial", product.isSerial);
				_client.setAttribute("lot", product.isLot);
			}

			var _code = self.opener.document.getElementById('productCode[${param.index}]');
			if(_code)
				_code.value = product.productCode;

			var _productId = self.opener.document.getElementById('productId${param.index}');
			if(_productId)
				_productId.value = product.productId;

			var _name = self.opener.document.getElementById('productName${param.index}');
			if(_name)
				_name.value = product.productName;

			var _packaging = self.opener.document.getElementById('packaging[${param.index}]');
			if(_packaging)
				_packaging.value = product.factoryCode;

			var _cat = self.opener.document.getElementById('productCategory[${param.index}]');
			if(_cat)
			{	
				if(_cat.tagName.toLowerCase() === 'select') {
					_cat.remove(_cat.selectedIndex);
					var _opt = document.createElement('option');
					_opt.value = product.productCategory.id;
					_opt.text = product.productCategory.name;

					_cat.appendChild(_opt);
				} else {
					_cat.setAttribute("lot",product.isLot);
					_cat.value = product.unitOfMeasure.name;
				}
			}
				
			var _uom = self.opener.document.getElementById('uom[${param.index}]');
			if(_uom)
				_uom.value = product.unitOfMeasure.measureId;
			
			//Call after all set
			if(_client) {
				var event = new Event('change');
				_client.dispatchEvent(event);
			}
		}

		window.close();
	}
</script>