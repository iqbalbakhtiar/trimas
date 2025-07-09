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
								<td width="15%" align="right"><spring:message code="sirius.code"/></td>
							  	<td width="5%" align="center">:</td>
						  	  	<td width="75%" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td width="15%" align="right"><spring:message code="sirius.name"/></td>
							  	<td width="5%" align="center">:</td>
						  	  	<td width="75%" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
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
					  		<th width="1%">&nbsp;</th>
			  		  	  	<th width="15%"><spring:message code="sirius.code"/></th>
			  		  	  	<th width="15%"><spring:message code="sirius.name"/></th>
                          	<th width="20%"><spring:message code="paymentreferencetype"/></th>
                          	<th width="35%"><spring:message code="glaccount.account.name"/></th>
				  		</tr>
						<c:forEach items="${paymentTypes}" var="payment">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${payment.id}','${payment.name}','<spring:message code="paymentreferencetype.${payment.referenceType.messageName}"/>')" title="Edit"><span>Edit</span></a>
					  		</td>
                            <td><c:out value="${payment.code}"/></td>
                            <td><c:out value="${payment.name}"/></td>
                            <td><spring:message code="paymentreferencetype.${payment.referenceType.messageName}"/></td>
                            <td><c:out value="${payment.account.code} - ${payment.account.name}"/></td>
					  	</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
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

function setclient(id, name, reference)
{
	var _client = self.opener.document.getElementById('${param.target}');
	if(_client)
	{
		_client.remove(_client.selectedIndex);
		var _opt = document.createElement('option');
		_opt.value = id;
		_opt.text = name;

		_client.appendChild(_opt);
	}
	
	var _reference = self.opener.document.getElementById('referenceType');
	if(_reference)
		_reference.value = reference;

	window.close();
}
</script>