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
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<form id="filterPopup" name="filterPopup" method="post" action="/page/.htm?target=${target}">
							<table width="100%" cellspacing="0" cellpadding="0" align="center">
							<tr>
								<td width="130" align="right" ><spring:message code="sirius.code"/> :</td>
								<td width="295" height="28" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td width="130" align="right" ><spring:message code="sirius.name"/> :</td>
								<td width="295" height="28" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
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
							 <td width="66%" align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="4%">&nbsp;</th>
					  		<th width="15%"><spring:message code="sirius.code"/></th>
				  		  	<th width="21%"><spring:message code="sirius.name"/></th>
				  		</tr>
						<c:forEach items="${employees}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.partyFrom.id}', '${com.partyFrom.code}', '${com.partyFrom.fullName}')" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					  		</td>
							<td>${com.partyFrom.code}</td>
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
	function setclient(id, code, fullName)
	{
		let _client = self.opener.document.getElementById('${param.target}');
		if(_client)
		{
			_client.remove(_client.selectedIndex);

			let _opt = document.createElement('option');
			_opt.value = id;
			_opt.text = code + ' - ' + fullName;

			_client.appendChild(_opt);

			const event = new Event('change');
			_client.dispatchEvent(event);
		}

		window.close();
	}
</script>