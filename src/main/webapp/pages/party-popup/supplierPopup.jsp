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
										<td width="15%" align="right" nowrap="nowrap"><spring:message code="supplier.name"/> : </td>
										<td width="40%"><input id="name" name="name" value="${filterCriteria.name}" size="35"/></td>
										<td width="45%"><%@ include file="/common/button.jsp"%></td>
									</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>
						<br/>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%">&nbsp;</th>
					  		<th width="25%"><spring:message code="supplier.code"/></th>
				  		  	<th width="65%"><spring:message code="supplier.name"/></th>
				  		</tr>
						<c:forEach items="${suppliers}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.partyFrom.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td nowrap="nowrap">${com.partyFrom.code}</td> 
							<td>${com.partyFrom.fullName}</td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
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
		Party.load(id);
		var supplier = Party.data;
		
		if(!jQuery.isEmptyObject(supplier))
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = supplier.partyId;
				_opt.text = supplier.partyName;
				
				_client.appendChild(_opt);

				_client.focus();
			}

			$ref = $('#refFrom');
			if($ref)
			{
				$ref.empty();
				if(!$.isEmptyObject(supplier.partyId))
				{
					$optaddr = $('<option></option>');
					$optaddr.attr('value',supplier.partyName);
					$optaddr.html(supplier.partyName);
					$optaddr.appendTo($ref);
				}
			}
		}

		window.close();
	}
</script>