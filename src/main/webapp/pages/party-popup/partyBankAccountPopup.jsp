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
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="347" align="right"><spring:message code="bankaccount.code"/></td>
									  	<td width="10">:&nbsp;</td>
								  		<td width="888" height="28" align="left"><input id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
									</tr>
                                    <tr>
										<td align="right"><spring:message code="bankaccount.accountname"/></td>
									  	<td>:&nbsp;</td>
										<td width="888" height="28" align="left"><input id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right"><spring:message code="bankaccount.bankname"/></td>
									  	<td>:&nbsp;</td>
										<td width="888" height="28" align="left"><input id="bank" name="bank" value="${filterCriteria.bank}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td align="left"><%@ include file="/common/button.jsp"%></td>
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
					  		<th width="4%"><div style="width:10px"></div></th>
				  			<th width="17%"><spring:message code="bankaccount.code"/></th>
				  		  	<th width="24%"><spring:message code="bankaccount.accountname"/></th>
  				  		  	<th width="26%"><spring:message code="bankaccount.bankname"/></th>
                            <th width="18%"><spring:message code="bankaccount.branch"/></th>
                            <th width="11%"><spring:message code="bankaccount.accountno"/></th>
				  		</tr>
						<c:forEach items="${accounts}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td><c:out value='${com.code}'/></td> 
							<td><c:out value='${com.accountName}'/></td>
                            <td><c:out value='${com.bankName}'/></td>
                            <td><c:out value='${com.bankBranch}'/></td>
                            <td><c:out value='${com.accountNo}'/></td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
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
<script type="text/javascript" src="<c:url value='/js/bank.js'/>"></script>
<script type="text/javascript">
	function setclient(id)
	{
		Bank.load(id);
		var bank = Bank.data;

		if(!jQuery.isEmptyObject(bank))
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = bank.bankId;
				_opt.text = bank.bankName; 
					
				_client.appendChild(_opt);
			}

			var accountName = self.opener.document.getElementById('accountName');
			if(accountName)
				accountName.value = bank.bankAccountName;
	
			var bankName = self.opener.document.getElementById('bankName');
			if(bankName)
				bankName.value = bank.bankName;
		
			var bankBranch = self.opener.document.getElementById('bankBranch');
			if(bankBranch)
				bankBranch.value = bank.bankBranch;
		
			var accountNo = self.opener.document.getElementById('accountNo');
			if(accountNo)
				accountNo.value = bank.bankAccountNo;
			
			var accountHolder = self.opener.document.getElementById('accountHolder');
			if(accountHolder)
				accountHolder.value = bank.bankAcountHolder.partyName;
					
			var gl = self.opener.document.getElementById('glAccount');
			if(gl)
				gl.value = bank.glAccount.accountCode+" - "+bank.glAccount.accountCode;

			window.close();
		}
	}
</script>