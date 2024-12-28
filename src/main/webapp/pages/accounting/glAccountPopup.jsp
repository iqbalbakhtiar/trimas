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
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td width="15%" align="right" nowrap="nowrap"><spring:message code="glaccount.name"/> : </td>
											<td width="40%"><input id="name" name="name" value="${filterCriteria.name}" size="35"/></td>
											<td width="45%"><%@ include file="/common/button.jsp"%></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="34%" height="30" align="left" valign="middle"></td>
								<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
					  		<tr>
					  			<th width="1%"><div style="width:30px"></div></th>
				  			  <th width="12%"><spring:message code="glaccount.code"/></th>
			  			  	  <th width="40%"><spring:message code="glaccount.name"/></th>
                              <th width="40%"><spring:message code="glaccount.group"/></th>
				  		  </tr>
							<c:forEach items="${accounts}" var="account">
							<tr>
					  			<td class="tools">
					  				<a class="item-button-add-row" href="javascript:setclient('${account.id}','${account.code}','${account.name}')"  title="${account.note}"><span>Edit</span></a>
					  			</td>
								<td nowra="nowrap">${account.code}</td> 
								<td nowra="nowrap">${account.name}</td>
                                <td nowra="nowrap">${account.parent.name}</td>
					  		</tr>
							</c:forEach>
					  		<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
					  	</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
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
	function setclient(id,code,name)
	{
		if(id && code && name)
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = id;
				
				<c:if test='${empty split}'>
					_opt.text = code+" "+name;
				</c:if>
				<c:if test='${not empty split}'>
					_opt.text = name;
					
					var i_code = self.opener.document.getElementById('code[${param.index}]');
					if(i_code)
						i_code.value = code;
				</c:if>
				
				_client.appendChild(_opt);
			}
			else
			{
				var _id = self.opener.document.getElementById('${param.target}id');
				if(_id)
					_id.value = id;
				
				var _name = self.opener.document.getElementById('${param.target}name');
				if(_name)
					_name.value = code+" "+name;
			}
		}
		
		window.close();
	}
</script>