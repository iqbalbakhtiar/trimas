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
	<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
	<%@ include file="/common/filterandpaging.jsp"%>
	<script type="text/javascript">
		function setclient(id,name)
		{
			if(id && name)
			{
				var _client = self.opener.document.getElementById('${param.target}');
				if(_client)
				{
					for(var idx=_client.options.length;idx>=0;idx--)
						_client.remove(_client.selectedIndex);
					
					var _opt = document.createElement('option');
					_opt.value = id;
					_opt.text = name;
					
					_client.appendChild(_opt);
					
					_client.dispatchEvent(new Event('change'));
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
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<form id="filterForm" name="filterForm" method="post" action="/page/.htm?target=${target}">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="347" align="right">Code&nbsp;&nbsp;</td>
									  	<td width="10">:&nbsp;</td>
								  	  	<td width="888" height="28" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right">Name&nbsp;&nbsp;</td>
									  	<td>:&nbsp;</td>
										<td width="888" height="28" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td><%@ include file="/common/button.jsp"%></td>
									</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
						</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%"><div style="width:10px"></div></th>
					  		<th width="30%"><spring:message code="sirius.code"/></th>
				  		  	<th width="30%"><spring:message code="sirius.name"/></th>
				  		</tr>
						<c:forEach items="${machines}" var="machine">
							<tr>
						  		<td class="tools">
						  			<a class="item-button-add-row" href="javascript:setclient('${machine.id}','${machine.name}')"  title="Add"><span>Add</span></a>
						  		</td>
								<td>${machine.code}</td> 
								<td>${machine.name}</td>
						  	</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
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