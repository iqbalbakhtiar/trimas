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
    <%@ include file="/common/sirius-header.jsp" %>
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
							<a class="item-button-list" href="<c:url value='/page/journalentryview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <a class="item-button-list" href="<c:url value='/page/schematicjournalentryview.htm'/>"><span><spring:message code="sirius.listschema"/></span></a>
							<c:if test='${access.edit}'>
                            	<a class="item-button-edit" href="<c:url value='/page/journalentrypreedit.htm?id=${journalEntry_edit.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
                            </c:if>
							<span>${journalEntry_edit.entryStatus}</span>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="journalEntry_edit">
							<table width="100%" border="0" cellpadding="0" cellspacing="20">
							<tr valign="top">
								<td width="50%">
									<table width="100%" style="border:none">
									<tr>
										<td width="32%" nowrap="nowrap" align="right"><spring:message code="journalentry.code"/> :</td>
										<td width="68%"><input value="${journalEntry_edit.code}" size="25" disabled class="input-disabled"/></td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.name"/> :</td>
										<td><input value="${journalEntry_edit.name}" size="55" disabled class="input-disabled"/></td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.date"/> :</td>
										<td><input size="11" class="input-disabled" value="<fmt:formatDate value='${journalEntry_edit.entryDate}' pattern="dd-MM-yyyy"/>"/></td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.type"/> :</td>
										<td>
											<form:select path="entryType" disabled="true" class="input-disabled">
												<form:option value="ENTRY">ENTRY</form:option>
                                                <form:option value="CORRECTION">CORRECTION</form:option>
                                                <form:option value="ADJUSTMENT">ADJUSTMENT</form:option>
											</form:select>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.currency"/> :</td>
										<td>
											<select class="input-disabled">
                                            	<option><c:out value='${journalEntry_edit.currency.symbol}'/></option>
											</select>
                                            <select class="input-disabled">
                                            	<option><c:out value='${journalEntry_edit.exchange.type}'/></option>
											</select>
                                            <input size="9" value="<fmt:formatNumber value='${journalEntry_edit.exchange.rate}' groupingUsed='true' maxFractionDigits='0'/>" disabled class="input-disabled"/>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.organization"/> :</td>
										<td>
											<select id="org" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.organization.firstName} ${journalEntry_edit.organization.middleName} ${journalEntry_edit.organization.lastName}'/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalschema"/> :</td>
										<td>
											<select id="schema" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.journalSchema}'/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="accountingperiod"/> :</td>
										<td>
											<select id="period" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.accountingPeriod.name}'/></option>
											</select>
										</td>
									</tr>									
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.note"/> :</td>
										<td><form:textarea path="note" cols="55" rows="7"/></td>
									</tr>
									</table>
								</td>
								<td width="50%">
									<table width="100%">
                                    <c:if test='${not empty journalEntry_edit.indexes}'>
                                    <tr>
                                    	<td>
                                        	<fieldset>
												<legend><strong><spring:message code="journalentry.index"/></strong></legend>
												<table width="100%" border="0" cellpadding="0">
												<c:forEach items='${journalEntry_edit.indexes}' var='type'>
                                                <tr>
													<td align="right">${type.indexType.name}</td>
													<td align="center">:</td>
													<td><input value="${type.content}" size="15" disabled/></td>
												</tr>
                                                </c:forEach>
												</table>
											</fieldset>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <tr>
                                    	<td>
                                        	<fieldset>
												<legend><strong><spring:message code="journalentry.entrybalance"/></strong></legend>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">&nbsp;</td>
													<td width="35%" align="center" bgcolor="#B7C9E3"><spring:message code="journalentry.debet"/></td>
													<td width="35%" align="center" bgcolor="#B7C9E3"><spring:message code="journalentry.credit"/></td>
												</tr>
												<tr>
													<td align="center"><strong>Total</strong></td>
													<td><input id='labelDebet' value="<fmt:formatNumber value='${display.debet}' pattern=',##0.00'/>" size="25" disabled class="number-disabled"/></td>
													<td><input id='labelCredit' value="<fmt:formatNumber value='${display.credit}' pattern=',##0.00'/>" size="25" disabled class="number-disabled"/></td>
												</tr>
												</table>
											</fieldset>
                                        </td>
                                    </tr>
                                    </table>
								</td>
								</tr>
								</table>
                                <div class="clears">&nbsp;</div>
								<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                                <thead>
								<tr>
                                    <th width="10%"><spring:message code="journaldetail.code"/></th>
						  	  	  	<th width="12%"><spring:message code="journaldetail.account"/></th>
					  	  	  	  	<th width="8%"><spring:message code="journaldetail.amount"/></th>
                           	  	  	<th width="5%"><spring:message code="journaldetail.type"/></th>
					  	  	  	  	<th width="55%"><spring:message code="journaldetail.note"/></th>
								</tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${journalEntry_edit.details}" var="detail" varStatus='status'>
                            <tr>
                                <td><c:out value='${detail.account.code}'/></td>
                                <td><c:out value='${detail.account.name}'/></td>
                                <td><fmt:formatNumber value='${detail.amount}' pattern=',##0.00'/></td>
                                <td><c:out value='${detail.postingType}'/></td>
                                <td><c:out value='${detail.note}'/></td>
                            </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr class="end-table"><td colspan="7">&nbsp;</td></tr>
                            </tfoot>
                            </table>
                            </sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${journalEntry_edit.createdBy.firstName} ${journalEntry_edit.createdBy.middleName} ${journalEntry_edit.createdBy.lastName}'/> (<fmt:formatDate value='${journalEntry_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${journalEntry_edit.updatedBy.firstName} ${journalEntry_edit.updatedBy.middleName} ${journalEntry_edit.updatedBy.lastName}'/> (<fmt:formatDate value='${journalEntry_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
