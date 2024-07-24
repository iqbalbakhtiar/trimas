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
									<table width="100%" cellspacing="0" cellpadding="3" align="center">
										<tr>
											<td width="15%" align="right" nowrap="nowrap"><spring:message code="customer.name"/> : </td>
											<td width="40%"><input id="name" name="name" value="${filterCriteria.name}" size="35"/></td>
											<td width="45%"><%@ include file="/common/button.jsp"%></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>
						<br/>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="1%">&nbsp;</th>
					  		<th width="15%"><spring:message code="customer.code"/></th>
					  		<th width="24%"><spring:message code="customer.name"/></th>
					  		<th width="10%"><spring:message code="customer.note"/></th>
					  	</tr>
						<c:forEach items="${customers}" var="client">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-edit" href="javascript:setclient('${client.relationship.id}')"  title="Select"><span>Select</span></a>
					  		</td>
							<td nowrap="nowrap"><c:out value='${client.customer.code}'/></td> 
							<td nowrap="nowrap"><c:out value='${client.customer.printedName}'/></td>
							<td><c:out value='${client.customer.note}'/></td>
					  	</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
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
	Party.relation(id);
	var relation = Party.data;
	 
	if(!$.isEmptyObject(relation))
	{
		var _client = self.opener.document.getElementById('${param.target}');
		if(_client)
		{
			_client.remove(_client.selectedIndex);
			var opt = document.createElement('option');
			opt.value = relation.partyFrom.partyId;
			opt.text = relation.partyFrom.partyName;
			
			_client.appendChild(opt);

			_client.focus();
		}

		$npwp = $('#npwp_number');
		if($npwp)
			$npwp.val(relation.partyFrom.taxCode);

		$plafon = $('#plafon');
		if($plafon)
		{
			$plafon.empty();
			$optplafon = $('<option></option>');
			$optplafon.attr('value',relation.plafonId);
			$optplafon.html(relation.plafon);
						
			$optplafon.appendTo($plafon);
		}
		
		$relation = $('#relation');
		if($relation)
			$relation.val(relation.relationId);
		
		$checkMaster = $('#checkMaster');
		if($checkMaster)
		{
			$('.checkall').click();
	    	$('.item-button-delete').click();
		}

		window.close();
	}
}
</script>