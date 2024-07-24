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
	<title>Party Relationship >> Popup</title>
	<%@ include file="/common/filterandpaging.jsp"%>
	<script type="text/javascript">
		function setclient(id,name)
		{
			if(id && name)
			{
				var _client = self.opener.document.getElementById('${target}');
				
				if(_client)
				{
					for(var idx=_client.options.length;idx>=0;idx--)
						_client.remove(_client.selectedIndex);
						
					var opt = document.createElement('option');
					opt.value = id;
					opt.text = name;
					
					_client.appendChild(opt);
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
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="7%"><div style="width:10px"></div></th>
					  		<th width="93%"><spring:message code="organization.name"/></th>
					  	</tr>
						<c:forEach items="${relationships}" var="rel">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-edit" href="javascript:setclient('${rel.id}','${rel.toRole.party.firstName} ${rel.toRole.party.middleName} ${rel.toRole.party.lastName}')"  title="Pilih"><span>Pilih</span></a>
					  		</td>
							<td>${rel.toRole.party.firstName} ${rel.toRole.party.middleName} ${rel.toRole.party.lastName}</td>
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