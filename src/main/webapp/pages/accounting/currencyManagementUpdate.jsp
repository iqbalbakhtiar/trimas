<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>siriusERP - Currency Management Edit</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
</head>
<!-- BEGIN OF BODY -->
<body>

<!-- top  menu -->
	
<!-- /top menu -->

<!-- rounded -->
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<!-- /rounded -->

<!-- main containers -->
<div id="se-containers">
	<!-- main menu -->
		<%@ include file="/common/sirius-menu.jsp"%>
	<!-- /main menu -->
	
	<!-- tips & page navigator -->
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">Financial Accounting > Accounting Setting > Currency Management > Edit</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>
	
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
					
					<h1 class="page-title">F14 - Currency Management</h1>
						
					<div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/currencyManagementView.htm'/>"><span>List</span></a>
					</div>					  
					  
					<div class="main-box">
						<sesform:form cssClass="edit-form" id="updateForm" name="updateForm" method="post" modelAttribute="currency">
						<table border="0" cellpadding="0" cellspacing="0">
 						<tr>
 							<td width="104"><div class="form-label">Currency ID :</div></td>
							<td width="240">
	    						<div class="form-value">
  									<form:input id="currencyId" path="symbol" cssClass="inputbox" />
 	   							</div>	
							</td>
							<td width="360"><form:errors path="symbol" cssClass="error"/></td>
 						</tr>
 						<tr>
 							<td><div class="form-label">Currency Name :</div></td>
							<td>
	    						<div class="form-value">
	    							<form:input id="currencyName" path="name" cssClass="inputbox" />
	    						</div>	
							</td>
							<td><form:errors path="name" cssClass="error"/></td>
 						</tr>
 						</table>
						</sesform:form>
						<br/>
						<div id="itemPane" dojoType="TabContainer" style="width:100% ; height: 400px;" doLayout="true">
							<div id="spot" dojoType="ContentPane" label="Spot Rate" class="tab-pages" refreshOnShow="true">						
								<div class="toolbar-clean">
									<a class="item-button-new" href="<c:url value='/page/currencyManagementPrepareAddRate.htm?id=${currency.id}&type=SPOT' />"><span>New Spot Rate</span></a>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
				  				<tr>
				  					<th width="16%">Rate</th>
				  					<th width="24%">Valid From</th>
									<th width="18%">Created By</th>
				  				</tr>
								<c:forEach items='${currency.spots}' var='spot'>								
								<tr>
 									<td><fmt:formatNumber value="${spot.rate}" groupingUsed="true" maxFractionDigits="3"/></td>
 									<td><fmt:formatDate value='${spot.validFrom}' pattern='dd-MM-yyyy hh:mm:ss'/></td>
									<td>${spot.createdBy.firstName} ${spot.createdBy.middleName} ${spot.createdBy.lastName}</td>
								</tr>
								</c:forEach>
								<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
								</table>
							</div>
							<div id="middle" dojoType="ContentPane" label="Middle Rate" class="tab-pages" refreshOnShow="true">			
								<div class="toolbar-clean">
									<a class="item-button-new" href="<c:url value='/page/currencyManagementPrepareAddRate.htm?id=${currency.id}&type=MIDDLE' />"><span>New Middle Rate</span></a>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
				  				<tr>
				  					<th width="16%">Rate</th>
				  					<th width="24%">Valid From</th>
									<th width="18%">Created By</th>
				  				</tr>
								<c:forEach items='${currency.middles}' var='middle'>								
								<tr>
 									<td><fmt:formatNumber value="${middle.rate}" groupingUsed="true" maxFractionDigits="3"/></td>
 									<td><fmt:formatDate value='${middle.validFrom}' pattern='dd-MM-yyyy hh:mm:ss'/></td>
									<td>${middle.createdBy.firstName} ${middle.createdBy.middleName} ${middle.createdBy.lastName}</td>
								</tr>
								</c:forEach>
								<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
								</table>
							</div>
							<div id="tax" dojoType="ContentPane" label="Tax Rate" class="tab-pages" refreshOnShow="true">
								<div class="toolbar-clean">
									<a class="item-button-new" href="<c:url value='/page/currencyManagementPrepareAddRate.htm?id=${currency.id}&type=TAX' />"><span>New Tax Rate</span></a>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
				  				<tr>
				  					<th width="16%">Rate</th>
				  					<th width="24%">Valid From</th>
									<th width="18%">Created By</th>
				  				</tr>
								<c:forEach items='${currency.taxs}' var='tax'>								
								<tr>
 									<td><fmt:formatNumber value="${tax.rate}" groupingUsed="true" maxFractionDigits="3"/></td>
 									<td><fmt:formatDate value='${tax.validFrom}' pattern='dd-MM-yyyy hh:mm:ss'/></td>
									<td>${tax.createdBy.firstName} ${tax.createdBy.middleName} ${tax.createdBy.lastName}</td>
								</tr>
								</c:forEach>
								<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
								</table>	
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

	<!-- /footer -->
</div><!-- /main containers -->



</body>
<!-- END OF BODY -->
</html>
