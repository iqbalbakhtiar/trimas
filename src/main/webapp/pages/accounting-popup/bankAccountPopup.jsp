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
	<script type="text/javascript">
		function setclient(id,code,name)
		{
			if(id && code && name)
			{
				var _client = self.opener.document.getElementById('${target}');
				if(_client)
				{
					_client.remove(_client.selectedIndex);
					
					var _opt = document.createElement('option');
					_opt.value = id;
					_opt.text = code+" "+name;
					
					_client.appendChild(_opt);
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
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;">Code / Name&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="name" name="name" value="${filterCriteria.bankName}" size="35" class="inputbox"/>
												<%@ include file="/common/button.jsp"%>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right" height="20"><a href="javascript:step('first','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> of <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Last</a></td>
						</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%"><div style="width:10px"></div></th>
					  		<th width="10%"><spring:message code="bankaccount.code"/></th>
                          	<th width="12%"><spring:message code="bankaccount.bankname"/></th>
                          	<th width="12%"><spring:message code="bankaccount.accountname"/></th>
                            <th width="12%"><spring:message code="bankaccount.accountno"/></th>
                          	<th width="45%"><spring:message code="bankaccount.holder"/></th>
				  		</tr>
						<c:forEach items="${accounts}" var="account">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${account.id}','${account.code}','${account.bankName}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td nowrap="nowrap"><c:out value="${account.code}" /></td> 
                            <td nowrap="nowrap"><c:out value="${account.bankName}" /></td>      
                            <td nowrap="nowrap"><c:out value="${account.accountName}" /></td>
                            <td nowrap="nowrap"><c:out value="${account.accountNo}" /></td>
                            <td nowrap="nowrap"><c:out value="${account.holder.fullName}" /></td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
					  	</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><a href="javascript:step('first','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> of <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='/page/popuppartyview.htm?target=${target}'/>');">Last</a></td>
						</tr>
						</table>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file='/common/sirius-footer.jsp' %>
	
</div>
</body>
</html>