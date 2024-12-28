<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
	<head>
	<title>Journal Schema Account >> Popup</title>
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
											<td width="15%" align="right" nowrap="nowrap"><spring:message code="journalentry.code"/> / <spring:message code="journalentry.name"/> : </td>
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

						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="1%"><div style="width:20px"></div></th>
				  		  	<th width="15%"><spring:message code="journalentry.code"/></th>
			  		  	  	<th width="50%"><spring:message code="journalentry.name"/></th>
                          	<th width="15%"><spring:message code="journalentry.type"/></th>
				  		</tr>
						<c:forEach items="${accounts}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.account.id}','${com.account.code}','${com.account.name}','${com.postingType}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td nowrap="nowrap">${com.account.code}</td>
							<td nowrap="nowrap">${com.account.name}</td>
                            <td nowrap="nowrap">${com.postingType}</td>
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
	function setclient(id,code,name,type)
	{
		var _split = '${split}';

		if(id && code && name)
		{
			var _client = self.opener.document.getElementById('${target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);

				var _opt = document.createElement('option');
				_opt.value = id;

				if(_split && _split == 'true')
					_opt.text = name;
				else
					_opt.text = code+" "+name;

				_client.appendChild(_opt);
			}

			if(_split)
			{
				var _code = document.getElementById("code[${index}]");
				if(_code)
					_code.value = code;
			}
		}

		var postingType = self.opener.document.getElementById('${postingType}');
		if(postingType)
		{
			for(var idx=postingType.options.length-1;idx>=0;idx--)
				postingType.remove(postingType.selectedIndex)

			debet = document.createElement('option');
			debet.value = type;
			debet.text = type;
			debet.selected=true;
			debet.setAttribute('onclick','amountView();');
			debet.setAttribute('style','width:75px;');

			postingType.appendChild(debet);

			postingType.focus();
		}

		window.close();
	}
</script>