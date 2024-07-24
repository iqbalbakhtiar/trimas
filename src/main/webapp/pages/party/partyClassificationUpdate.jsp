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
	<title>Party Classification >> Edit</title>
	<%@ include file="/common/sirius-header.jsp"%>

	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<script type="text/javascript">
		function save()
		{
			document.editForm.action = "<c:url value='/page/partyclassificationedit.htm'/>";
			document.editForm.submit();
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
			<td width="60%">Company Administration > Party > Party Classification > Edit</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
					<h1 class="page-title">A06 - Party Classification</h1>
						
					<div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/partypreedit.htm?id=${partyClassification.party.id}'/>"><span>List</span></a>
						<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
					</div>					  
					  
					<div class="main-box">
						<sesform:form name="editForm" method="post" modelAttribute="partyClassification" cssClass="edit-form">
						<table>
 						<tr><td colspan="3">&nbsp;</td></tr>
 						<tr> 	
 							<td align="right">Party :</td>
							<td width="230"><input class="inputbox" value="${partyClassification.party.firstName} ${partyClassification.party.middleName} ${partyClassification.party.lastName}"/></td>
							<td width="331">&nbsp;</td>
 						</tr>
						<tr>
							<td align="right">From Date :</td>
							<td><input name="fromDate" saveformat="dd-MM-yyyy" dojotype="dropdowndatepicker" value="${partyClassification.fromDate}" displayformat="dd-MM-yyyy" lang="en-us" containerToggle="explode" containerToggleDuration="300" /></td>
						</tr>
						<tr>
							<td align="right">To Date :</td>
							<td><input name="toDate" saveformat="dd-MM-yyyy" dojotype="dropdowndatepicker" value="${partyClassification.toDate}" displayformat="dd-MM-yyyy" lang="en-us" containerToggle="explode" containerToggleDuration="300" /></td>
						</tr>
 						<tr>
 							<td align="right">Contact Type :</td>
							<td>
	    						<div class="form-value">
  									<form:select path="type" cssClass="selectbox">
  										<form:options items="${types}" itemLabel="name" itemValue="id"/>
 									</form:select>
 								</div>
							</td>
							<td>&nbsp;</td>
 						</tr>
 						</table>
						</sesform:form>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
  	
  	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>
</body>

</html>
