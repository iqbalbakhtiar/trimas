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
		function _reset()
		{
			window.location="<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}&trxCode=${param.trxCode}'/>";
		}
	
		function setclient(id, code, description, stampCode, stampDesc)
		{
			if(id && code && description)
			{
				var _client = self.opener.document.getElementById('${target}');
				if(_client)
				{
					for(var idx=_client.options.length;idx>=0;idx--)
					_client.remove(_client.selectedIndex);
					
					var _opt = document.createElement('option');
					_opt.value = id;
					_opt.text = code;
					
					_client.appendChild(_opt);
				}
				
				var _desc = self.opener.document.getElementById('addInfoDesc');
				if(_desc)
					_desc.value = description;
				
				var _stamp = self.opener.document.getElementById('facilityStamp');
				if(_stamp)
					_stamp.value = stampCode;
				
				var _stampDesc = self.opener.document.getElementById('facilityStampDesc');
				if(_stampDesc)
					_stampDesc.value = stampDesc;
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
								<form id="filterForm" name="filterForm" method="post">
									<table width="100%" cellspacing="0" cellpadding="3" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;">Code &nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td width="295" height="28" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
					                    </tr>
					                    <tr>
											<td align="right">Description&nbsp;&nbsp;</td>
											<td>:&nbsp;</td>
											<td align="left"><input type="text" id="description" name="description" value="${filterCriteria.description}" size="35" class="inputbox"/></td>
					                    </tr>
					                	<tr>
					                      	<td>&nbsp;</td>
					                      	<td>&nbsp;</td>
											<td align="left">
												<input type="button" value="Search" style="WIDTH:60px; HEIGHT:25px" alt="Search" onclick="search();" class="btn" />
												<input type="button" value="Reset"  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onclick="_reset();" class="btn"/>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td align="right"><a href="javascript:step('first','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">Last</a></td>
						</tr>
						</table>		
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%"><div style="width:10px"></div></th>
					  		<th width="20%">Code</th>
				  		  	<th width="74%">Description</th>
				  		</tr>
				  		<c:forEach items="${products}" var="info">
							<tr>
						  		<td class="tools">
						  			<a class="item-button-add-row" href="javascript:setclient('${info.id}','${info.code}','${info.description}','${info.facilityStampCode}','${info.facilityStampDescription}')" title="Add"><span>Add</span></a>
						  		</td>
								<td>${info.code}</td> 
								<td>${info.description}</td>
						  	</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
					  	</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><a href="javascript:step('first','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">First</a> | <a href="javascript:step('prev','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">Next</a> | <a href="javascript:step('last','<c:url value='/page/popupcoretaxaddinfoview.htm?target=${target}'/>');">Last</a></td>
						</tr>
						</table>
				    </div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
    <%@ include file="/common/sirius-footer.jsp"%>

	<!-- /footer -->
</div><!-- /main containers -->
</body>
<!-- END OF BODY -->
</html>
