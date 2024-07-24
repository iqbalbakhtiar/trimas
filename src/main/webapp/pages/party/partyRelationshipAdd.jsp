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
	<%@ include file="/common/sirius-header.jsp"%>
	<script type="text/javascript">
		function save()
		{
			document.addForm.action = "<c:url value='/page/partyrelationshipadd.htm'/>";
			document.addForm.submit();
		}
	</script>
</head>

<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	
	<%@ include file="/common/sirius-menu.jsp"%>
	
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb}</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
					<h1 class="page-title">${pageTitle}</h1>
						
					<div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/partypreedit.htm?id=${partyRelationship.partyFrom.id}'/>"><span>List</span></a>
						<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
					</div>					  
					  
					<div class="main-box">
						<sesform:form name="addForm" method="post" modelAttribute="partyRelationship">
						<table width="100%">
 						<tr><td colspan="3">&nbsp;</td></tr>
 						<tr> 	
 							<td width="296" align="right">From Party :</td>
					  	  	<td width="707">
  				  				<form:select id="partyFrom" path="partyFrom" class="combobox-ext input-disabled">
  				  					<form:option value="${partyRelationship.partyFrom.id}" label="${partyRelationship.partyFrom.fullName}"/>
 								</form:select>
					  	  	</td>
					  	  	<td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">From Role :</td>
							<td width="707">
  				  				<form:select path="partyRoleTypeFrom">
									<form:options items="${roleTypes}" itemValue="id" itemLabel="name"/>
 								</form:select>
							</td>
						  <td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">To Party :</td>
							<td width="707">
  				  				<form:select id="partyTo" path="partyTo" class="combobox-ext">
 								</form:select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructureview.htm?target=partyTo'/>');"  title="Company Structure" />
							</td>
						  <td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">To Role :</td>
							<td width="707">
  				  				<form:select path="partyRoleTypeTo">
									<form:options items="${roleTypes}" itemValue="id" itemLabel="name"/>
 								</form:select>
							</td>
						  <td width="225">&nbsp;</td>
 						</tr>
						<tr>
 							<td align="right">Relationship Type :</td>
							<td>
	    						<form:select path="relationshipType" class="combobox-ext">
									<form:options items="${relationshipTypes}" itemValue="id" itemLabel="name"/>
 								</form:select>
							</td>
							<td>&nbsp;</td>
 						</tr>
 						<tr>
							<td align="right">From Date :</td>
							<td><input name="fromDate" class="datepicker"/></td>
						</tr>
						<tr>
							<td align="right">To Date :</td>
							<td><input name="toDate" class="datepicker"/></td>
						</tr>
 						</table>
						</sesform:form>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>