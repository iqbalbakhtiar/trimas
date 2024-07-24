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
	<title>${title}>> Popup</title>
	<%@ include file="/common/filterandpaging.jsp"%>
	<script type="text/javascript">
		function setclient(id,name)
		{
			if(id && name)
			{
				var _customer = self.opener.document.getElementById('${target}');
				if(_customer)
				{
					_customer.remove(_customer.selectedIndex);
							
					var _opt = document.createElement('option');
					_opt.value = id;
					_opt.text = name;
							
					_customer.appendChild(_opt);
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
						<form id="filterPopup" name="filterPopup" method="post" >
									<table width="100%" cellspacing="0" cellpadding="1" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;">Name&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td height="28" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
										</tr>
										<tr>
											<td colspan="2">&nbsp;</td>
											<td align="left">
													<input type="button" value="Search" style="WIDTH:60px; HEIGHT:25px" alt="Search" onclick="search('<c:url value='${reset}'/>');" class="btn" />
													<input type="button" value="Cancel" style="WIDTH:60px; HEIGHT:25px" alt="Cancel" onclick="reload('<c:url value='${url}'/>');;" class="btn" />
											</td>
										</tr>
									</table>
								</form>
							<br/>
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="4%">&nbsp;</th>
							<th width="17%">Name</th>
							<th width="23%">Note</th>
						</tr>
						<c:forEach items="${alls}" var="module">
						<tr>
							<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${module.id}','${module.name}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td><c:out value='${module.name}'/></td>
							<td><c:out value='${module.note}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
							<tr>
							<td align="right"><a href="javascript:step('first','<c:url value='${url}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='${url}'/>');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> from <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next','<c:url value='${url}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='${url}'/>');">Last</a></td>
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