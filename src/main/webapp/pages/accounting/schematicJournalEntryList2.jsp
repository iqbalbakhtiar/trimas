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
	<title>Schematic Journal Entry</title>
	<script type="text/javascript" src="assets/dialog.js"></script>
	<%@ include file="/common/sirius-header.jsp"%><!-- /top menu -->
	<%@ include file="/filter/schematicJournalEntryFilter.jsp"%>
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>

</head>

<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	<!-- main menu -->
	<%@ include file="/common/sirius-menu.jsp"%>
	
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="60%">Financial Accounting > Journal Entrys > Schematic Journal Entry</td>
				<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
			</tr>
		</table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">

						<h1 class="page-title"></h1>
						<h1 class="page-title">F41 - Schematic Journal Entry</h1>
						<div class="clears">&nbsp;</div>
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="30%" height="30" align="left" valign="middle">															
									<div class="toolbar-clean">
										<a class="item-button-new" href="<c:url value='/page/schematicjournalentrypreadd.htm'/>"><span>New Entry</span></a>
							  			<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span>Filter</span></a>
										</div>
									</div>
								</td>
								<td width="70%" align="right" height="20"><a href="javascript:step('first');">First</a> | <a href="javascript:step('prev');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> of <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next');">Next</a> | <a href="javascript:step('last');">Last</a></td>
							</tr>
							</table>
					  	</div>
					  
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="8%"><div style="width:40px"></div></th>
					  	  <th width="12%">ID</th>
					  	  <th width="27%">Name</th>
					  	  <th width="7%">Date</th>
                          	<th width="5%"> Type</th>
						  	<th width="24%">Company</th>
						  	<th width="9%">Period</th>
						  	<th width="8%">Amount</th>
						</tr>
						<c:forEach var="journal" items="${journalEntrys}" varStatus="i" >
						<tr>
							<td class="tools">
                            	<a class="item-button-search" href="<c:url value='/page/schematicjournalentrypreview.htm?id=${journal.id}'/>"><span>View</span></a>
                            	<c:if test="${access.edit}">
									<a class="item-button-edit" href="<c:url value='/page/schematicjournalentrypreedit.htm?id=${journal.id}'/>"><span>Edit</span></a>
                                </c:if>
								<c:if test="${journal.entryStatus != 'POSTED' and access.delete}">
									<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/schematicjournalentrydelete.htm?id=${journal.id}'/>');" title="Del"><span>Del</span></a>
								</c:if>
							</td>
							<td><c:out value="${journal.code}"/></td>
							<td><c:out value="${journal.name}"/></td>
   							<td><fmt:formatDate value="${journal.entryDate}" pattern="dd-MM-yyyy"/></td> 
							<td><c:out value="${journal.entryType}"/></td>
							<td><c:out value="${journal.organization.firstName} ${journal.organization.middleName} ${journal.organization.lastName}"/></td>
							<td><c:out value="${journal.accountingPeriod.code}"/></td>
							<td align="right"><fmt:formatNumber value="${journal.amount}" pattern=",##0.00"/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><a href="javascript:step('first');">First</a> | <a href="javascript:step('prev');">Prev</a> | Page <c:out value="${filterCriteria.page}"/> of <c:out value="${filterCriteria.totalPage}"/> | <a href="javascript:step('next');">Next</a> | <a href="javascript:step('last');">Last</a></td>
						</tr>
						</table>
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
<%@ include file="/common/dialog.jsp"%>
</html>
