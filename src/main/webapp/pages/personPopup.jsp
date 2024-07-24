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
	<title>Person >> Popup</title>
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<%@ include file="/common/filterandpaging.jsp"%>
	<script type="text/javascript">
		function setclient(id,name)
		{
			if(id && name)
			{
				var _client = self.opener.document.getElementById('${target}');
				if(_client)
				{
					_client.remove(_client.selectedIndex);

					var _opt = document.createElement('option');

					if('${param.target}' == 'createdBy')
						_opt.value = name;
					else
						_opt.value = id;

					_opt.text = name;

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
								<form id="filterPopup" name="filterPopup" method="post" >
									<table width="100%" cellspacing="0" cellpadding="3" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;">Name&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td height="28" align="left">
												<input id="name" name="name" value="${filterCriteria.name}" size="35"/>
											</td>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>
												<input type="button" value="Search" class="btn" style="WIDTH:60px; HEIGHT:25px" onclick="search('<c:url value='/page/popuppersonview.htm?target=${target}&organization=${filterCriteria.organization}&partyRole=${filterCriteria.partyRole}'/>');"/>
												<input type="button" value="Reset" class="btn" style="WIDTH:60px; HEIGHT:25px" onclick="_reset();"/>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>
						<br/><c:set var='url' value='/page/popuppersonview.htm?target=${target}&organization=${filterCriteria.organization}&partyRole=${filterCriteria.partyRole}'/><br/>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><a href="javascript:step('first','<c:url value='${url}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='${url}'/>');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> from <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next','<c:url value='${url}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='${url}'/>');">Last</a></td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%"><div style="width:10px"></div></th>
				  		  	<th width="74%">Name</th>
				  		</tr>
						<c:forEach items="${persons}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}','${com.firstName} ${com.middleName} ${com.lastName}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td>${com.firstName} ${com.middleName} ${com.lastName}</td>
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

    <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer-pick">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>
</body>

</html>