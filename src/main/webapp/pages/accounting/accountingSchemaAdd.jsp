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
							<a class="item-button-list" href="<c:url value='/page/accountingschemaview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
						<div class="main-box">
							<sesform:form name="addForm" method="post" modelAttribute="accountingSchema_add">
							<table style="border:none" width="100%">
							<tr>
								<td width="18%" align="right"><spring:message code="accountingschema.code"/></td>
								<td width="1%">:</td>
								<td width="25%"><form:input id="code" path="code" cssClass="inputbox" /></td>
			    				<td width="56%"><form:errors path="code" cssClass="error" /></td>
							</tr>	
							<tr>
								<td align="right"><spring:message code="accountingschema.name"/></td>
								<td width="1%">:</td>
								<td width="25%"><form:input id="name" path="name" size="50"/></td>
			    				<td width="56%"><form:errors path="name" cssClass="error" /></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.organization"/></td>
								<td width="1%">:</td>
								<td>
									<form:select id="org" path="organization" cssClass="combobox-ext">
									</form:select>
									<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
								</td>							
			    				<td><form:errors path="organization" cssClass="error"/></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.note"/></td>
								<td width="1%">:</td>
								<td><form:textarea path="note" cols="50" rows="6"/></td>							
			    				<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td align="right"><strong><spring:message code="accountingschema.setting"/></strong></td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.coa"/></td>
								<td width="1%">:</td>
								<td>
									<form:select id="coax" path="chartOfAccount" cssClass="combobox-ext">
										<c:forEach items="${coas}" var="coa">
											<form:option value="${coa.id}">${coa.code} ${coa.name}</form:option>
										</c:forEach>
									</form:select>
								</td>							
			    				<td><form:errors path="chartOfAccount" cssClass="error" /></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.period"/></td>
								<td width="1%">:</td>
								<td>
									<form:select path="accountingPeriod" cssClass="combobox-ext">
										<c:forEach items="${periods}" var="period">
											<form:option value="${period.id}">${period.code} ${period.name}</form:option>
										</c:forEach>
									</form:select>
								</td>							
			    				<td><form:errors path="accountingPeriod" cssClass="error" /></td>
							</tr>
							<tr><td colspan="4">&nbsp;</td></tr>
							</table>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="closing" dojoType="ContentPane" label="<spring:message code="accountingschema.closing"/>" class="tab-pages" refreshOnShow="true">
                                    <table>
                                    <c:forEach items="${accountingSchema_add.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'NONASSET'}">
                                    <tr>
                                        <td align="right">${closing.closingAccountType.name}</td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}">${closing.account.code} ${closing.account.name}</option>
                                            </form:select>
                                            &nbsp;
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                        <td><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                </div>
                                <div id="receivAndpayable" dojoType="ContentPane" label="<spring:message code="accountingschema.arap"/>" class="tab-pages" refreshOnShow="true">
                                    <table>
                                    <tr>
                                    	<td align="right"><strong><spring:message code="accountingschema.receivables"/></strong></td>
                                    	<td colspan="3">&nbsp;</td>
                                    </tr>
                                    <c:forEach items="${accountingSchema_add.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'RECEIVABLES'}">
                                    <tr>
                                        <td align="right">${closing.closingAccountType.name}</td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}">${closing.account.code} ${closing.account.name}</option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                        <td><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr>
                                    	<td align="right"><strong><spring:message code="accountingschema.payables"/></strong></td>
                                    	<td colspan="3">&nbsp;</td>
                                    </tr>
                                    <c:forEach items="${accountingSchema_add.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'PAYABLES'}">
                                    <tr>
                                        <td align="right">${closing.closingAccountType.name}</td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}">${closing.account.code} ${closing.account.name}</option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                        <td><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                </div>
                                <%-- <div id="mastercost" dojoType="ContentPane" label="<spring:message code="accountingschema.transport"/>" class="tab-pages" refreshOnShow="true">
                                    <table>
                                    <c:forEach items="${accountingSchema_add.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'TRANSPORT_OUTSOURCE'}">
                                    <tr>
                                        <td align="right">${closing.closingAccountType.name}</td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}">${closing.account.code} ${closing.account.name}</option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                        <td><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                </div> --%>
                        	</div>
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
<script type="text/javascript">
	
	function save(e)
	{
		if(!$('#code').val())
		{
			alert('<spring:message code="accountingschema.code"/> <spring:message code="notif.empty"/> !!!');
			return;
		}

		if(!$('#name').val())
		{
			alert('<spring:message code="accountingschema.name"/> <spring:message code="notif.empty"/> !!!');
			return;
		}

		if(!$('#org').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
			
		document.addForm.action = "<c:url value='/page/accountingschemaadd.htm'/>";
		document.addForm.submit();
	}
		
	function popup(target)
	{
		var coax = document.getElementById('coax');
		if(!coax && !coax.value)
		{
			alert('Please select valid Chart of Account first!');
			return;
		}
			
		openpopup("<c:url value='/page/popupglaccountview.htm?level=ACCOUNT&coa='/>"+coax.value+"&target="+target);
	}
</script>