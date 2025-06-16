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
						<c:set var="url" value="/page/popupproductfortransferview.htm?target=${target}&index=${index}&organization=${filterCriteria.organization}"/>
						<form id="filterForm" name="filterForm" method="post" >
							<table width="100%" cellspacing="0" cellpadding="1" align="center">
                                <tr>
                                    <td width="460" align="right">Code</td>
                                    <td width="4">:</td>
                                    <td width="765" height="28" align="left"><input id="code" name="code" value="${filterCriteria.code}" size="35"/></td>
                                </tr>
                                <tr>
                                    <td width="460" align="right">Name</td>
                                    <td width="4">:</td>
                                    <td height="28" align="left"><input id="name" name="name" value="${filterCriteria.name}" size="35"/></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td><input type="submit" value="Search" alt="Search" style="WIDTH:60px; HEIGHT:25px" onclick="search('<c:url value='${url}'/>');" class="btn"></td>
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
			  		  	  	<th width="12%"><spring:message code="product.code"/></th>
		  		  	  	  	<th width="20%"><spring:message code="product.name"/></th>
			  		  	  	<th width="10%"><spring:message code="product.category"/></th>
			  		  	  	<th width="7%"><spring:message code="container"/></th>
                          	<th width="7%"><spring:message code="product.onhand"/></th>
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
			
			var _onhand = self.opener.document.getElementById('onhand[${index}]');
			var _serial = self.opener.document.getElementById('serialCheck[${index}]');
			var _uom = self.opener.document.getElementById('uom[${index}]');
			var _qtyBase = self.opener.document.getElementById('qtyToBase[${index}]');
			var _code = self.opener.document.getElementById('codeprod[${index}]');
			var _category = self.opener.document.getElementById('category[${index}]');
			var _gridFrom = self.opener.document.getElementById('gridFrom[${index}]');
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
			
			if(_source)
			{
				_source.remove(_source.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = containerId;
				_opt.text = containerName.replaceAll("*", "\"");;
				
				_source.appendChild(_opt);
			}

			var event = new Event('change');
			_client.dispatchEvent(event);
		}
		
		window.close();
	}
</script>