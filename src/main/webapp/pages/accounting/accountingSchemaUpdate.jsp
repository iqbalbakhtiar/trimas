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
							<sesform:form name="editForm" method="post" modelAttribute="accountingSchema_edit">
							<table style="border:none" width="100%">
							<tr>
								<td width="18%" align="right"><spring:message code="accountingschema.code"/></td>
								<td width="1%">:</td>
								<td width="25%"><form:input id="code" path="code" cssClass="inputbox input-disabled" disabled="true"/></td>
			    				<td width="56%"><form:errors path="code" cssClass="error" /></td>
							</tr>	
							<tr>
								<td align="right"><spring:message code="accountingschema.name"/></td>
								<td width="1%">:</td>
								<td width="25%"><form:input id="name" path="name" size="50" cssClass="input-disabled" disabled="true"/></td>
			    				<td width="56%"><form:errors path="name" cssClass="error" /></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.organization"/></td>
								<td width="1%">:</td>
								<td>
									<select id="org" class="combobox-ext input-disabled">
										<option><c:out value='${accountingSchema_edit.organization.fullName}'/></option>
									</select>
								</td>							
			    				<td><form:errors path="organization" cssClass="error" /></td>
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
									<select id="coa" class="combobox-ext input-disabled">
										<option value="${accountingSchema_edit.chartOfAccount.id}"><c:out value='${accountingSchema_edit.chartOfAccount.code} ${accountingSchema_edit.chartOfAccount.name}'/></option>
									</select>
								</td>							
			    				<td><form:errors path="chartOfAccount" cssClass="error" /></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="accountingschema.period"/></td>
								<td width="1%">:</td>
								<td>
									<select class="combobox-ext input-disabled">
										<option><c:out value='${accountingSchema_edit.accountingPeriod.code} ${accountingSchema_edit.accountingPeriod.name}'/></option>
									</select>
								</td>							
			    				<td><form:errors path="accountingPeriod" cssClass="error" /></td>
							</tr>
							<tr><td colspan="4">&nbsp;</td></tr>
                            </table>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                            	<%-- <div id="distribution" dojoType="ContentPane" label="<spring:message code="accountingschema.distribution"/>" class="tab-pages" refreshOnShow="true">
                                    <table width="100%">
                                    <c:if test="${distributable and not empty accountingSchema_edit.closingDistribution}">
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr>
                                        <td align="right"><strong><spring:message code="accountingschema.distributionsetting"/></strong></td>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <c:forEach items="${accountingSchema_edit.closingDistribution.destinations}" var="distribution" varStatus="status">
                                    <tr>
                                        <td align="right">To</td>
                                        <td width="1%">:</td>
                                        <td colspan="2">
                                            <select class="combobox-ext input-disabled">
                                                <option><c:out value='${distribution.organization.firstName} ${distribution.organization.middleName} ${distribution.organization.lastName}'/></option>
                                            </select>
                                            <form:input id='distribution_rate[${distribution.id}]' path="closingDistribution.destinations[${status.index}].rate" size="7"/> %
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                    <tr>
                                        <td align="right"><strong><spring:message code="accountingschema.transferaccount"/></strong></td>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="accountingschema.glaccount"/></td>
                                        <td width="1%">:</td>
                                        <td colspan="2">
                                            <form:select id="distibutionAcc" path="closingDistribution.account" cssClass="combobox-ext">
                                                <c:if test='${not empty accountingSchema_edit.closingDistribution.account}'>
                                                    <option value="${accountingSchema_edit.closingDistribution.account.id}"><c:out value='${accountingSchema_edit.closingDistribution.account.code} ${accountingSchema_edit.closingDistribution.account.name}'/></option>
                                                </c:if>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('distibutionAcc');"  title="Search Account" />
                                        </td>							
                                    </tr>
                                    </c:if>
                                    </table>
                                </div> --%>
                                <div id="closing" dojoType="ContentPane" label="<spring:message code="accountingschema.closing"/>" class="tab-pages" refreshOnShow="true">
                                    <table>
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'NONASSET'}">
                                    <tr>
                                        <td align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
                                            </form:select>
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
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'RECEIVABLES'}">
                                    <tr>
                                        <td align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
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
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'PAYABLES'}">
                                    <tr>
                                        <td align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                        <td width="1%">:</td>
                                        <td>
                                            <form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                        <td><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                </div>
                                <%-- <div id="product" dojoType="ContentPane" label="<spring:message code="accountingschema.prodcategory"/>" class="tab-pages" refreshOnShow="true">
                                    <table width="100%">
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'INVENTORY'}">
                                    <tr>
                                        <td width="39%" align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                      	<td width="1%">:</td>
                                        <td width="59%">
                                      		<form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                      <td width="1%"><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                    <br/>
                                    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                                    <tr>
                                        <td width="30%" height="30" align="left" valign="middle">															
                                            <div class="toolbar-clean">
                                                <a class="item-button-new" href="<c:url value='/page/productcategoryaccountingschemapreadd.htm?id=${accountingSchema_edit.id}'/>"><span><spring:message code="accountingschema.prodcategorynew"/></span></a>
                                            </div>
                                        </td>
                                    </tr>
                                    </table>
                                    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
                                    <tr>
                                      	<th width="1%"><div style="width:40px"></div></th>
                                        <th width="12%"><spring:message code="accountingschema.prodcategory"/></th>
                                        <th width="87%"><spring:message code="accountingschema.note"/></th>
                                    </tr>
                                    <c:forEach items='${accountingSchema_edit.products}' var='product'>
                                    <tr>
                                        <td class="tools">
                                            <a class="item-button-edit" href="<c:url value='/page/productcategoryaccountingschemapreedit.htm?id=${product.id}'/>"  title="Edit"><span>Edit</span></a>
                                        </td>
                                        <td nowrap="nowrap"><c:out value='${product.category.code} ${product.category.name}'/></td>
                                        <td><c:out value='${product.note}'/></td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="3">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="mastercost" dojoType="ContentPane" label="<spring:message code="accountingschema.transport"/>" class="tab-pages" refreshOnShow="true">
                                    <table width="100%">
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'TRANSPORT_OUTSOURCE'}">
                                    <tr>
                                        <td width="39%" align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                      	<td width="1%">:</td>
                                        <td width="59%">
                                      		<form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                      <td width="1%"><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                    <br/>
                                </div>
                                <div id="procurementschema" dojoType="ContentPane" label="<spring:message code="accountingschema.procurement"/>" class="tab-pages" refreshOnShow="true">
                                    <table width="100%">
                                    <c:forEach items="${accountingSchema_edit.closingAccounts}" var="closing" varStatus="status">
                                    <c:if test="${closing.closingAccountType.groupType == 'PROCUREMENT'}">
                                    <tr>
                                        <td width="39%" align="right"><c:out value='${closing.closingAccountType.name}'/></td>
                                      	<td width="1%">:</td>
                                        <td width="59%">
                                      		<form:select id="glaccount[${status.index}]" path="closingAccounts[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${closing.account.id}"><c:out value='${closing.account.code} ${closing.account.name}'/></option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                        </td>							
                                      <td width="1%"><form:errors path="closingAccounts[${status.index}].account" cssClass="error" /></td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </table>
                                </div> --%>
                                <div id="taxschema" dojoType="ContentPane" label="<spring:message code="accountingschema.taxschema"/>" class="tab-pages" refreshOnShow="true">
                                    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                                    <tr>
                                        <td width="30%" height="30" align="left" valign="middle">															
                                            <div class="toolbar-clean">
                                                <a class="item-button-new" href="<c:url value='/page/taxaccountschemapreadd.htm?id=${accountingSchema_edit.id}'/>"><span><spring:message code="accountingschema.taxschemanew"/></span></a>
                                            </div>
                                        </td>
                                    </tr>
                                    </table>
                                    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
                                    <thead>
                                    <tr>
                               	  	  <th width="4%"><div style="width:40px"></div></th>
                                        <th width="96%"><spring:message code="accountingschema.taxschemaname"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${accountingSchema_edit.taxSchemas}" var="tax" varStatus="status">
                                   	<tr>
                                    	<td class="tools">
                                            <a class="item-button-edit" href="<c:url value='/page/taxaccountschemapreedit.htm?id=${tax.id}'/>" title="Edit"><span>Edit</span></a>
                                        </td>
                                      <td width="96%" align="right"><c:out value='${tax.tax.taxId}'/></td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr class="end-table"><td colspan="2">&nbsp;</td></tr>
                                    </tfoot>
                                    </table>
                                </div>
                                <%-- <div id="consumptionschema" dojoType="ContentPane" label="<spring:message code="accountingschema.prodconsumption"/>" class="tab-pages" refreshOnShow="true">
                                    <table width="100%">
                                    <c:forEach items="${accountingSchema_edit.consumptions}" var="consumption" varStatus="status">
                                    <tr>
                                        <td width="39%" align="right"><c:out value='${consumption.consumption.name}'/></td>
                                        <td width="1%">:</td>
                                        <td width="59%">
                                            <form:select id="consumption[${status.index}]" path="consumptions[${status.index}].account" cssClass="combobox-ext">
                                                <option value="${consumption.account.id}"><c:out value='${consumption.account.code} ${consumption.account.name}'/></option>
                                            </form:select>
                                            <a class="item-popup" onclick="javascript:popup('consumption[${status.index}]');"  title="Search Account" />
                                        </td>                           
                                        <td width="1%">&nbsp;</td>
                                    </tr>
                                    </c:forEach>
                                    </table>
                                </div> --%>
				 				<div id="cashbank" dojoType="ContentPane" label="<spring:message code="accountingschema.cashbank"/>" class="tab-pages" refreshOnShow="true">
                                    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                                    <tr>
                                        <td width="30%" height="30" align="left" valign="middle">															
                                            <div class="toolbar-clean">
                                                <a class="item-button-new" href="<c:url value='/page/cashbankschemapreadd.htm?schema=${accountingSchema_edit.id}'/>"><span><spring:message code="accountingschema.cashbanknew"/></span></a>
                                            </div>
                                        </td>
                                    </tr>
                                    </table>
                                    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
                                    <thead>
                                    <tr>
										<th width="1%"><div style="width: 30px">&nbsp;</div></th>
                                        <th width="10%"><spring:message code="accountingschema.name"/></th>
										<th width="89%"><spring:message code="accountingschema.cashbank"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${accountingSchema_edit.cashbankes}" var="cashbank" varStatus="status">
                                   	<tr>
                                    	<td class="tools">
                                            <a class="item-button-edit" href="<c:url value='/page/cashbankschemapreedit.htm?id=${cashbank.id}'/>"><span>Edit</span></a>
                                        </td>
                                      	<td nowrap="nowrap"><c:out value='${cashbank.closingAccountType.name}'/></td>
										<td nowrap="nowrap"><c:out value='${cashbank.bankAccount.bankName} - ${cashbank.bankAccount.accountNo}'/></td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr class="end-table"><td colspan="3">&nbsp;</td></tr>
                                    </tfoot>
                                    </table>
                                </div>
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
	function save()
	{
		<c:if test='${not empty accountingSchema_edit.closingDistribution.destinations}'>
			var rate = 0.0;
			
			<c:forEach items="${accountingSchema_edit.closingDistribution.destinations}" var="distribution">
				if(dojo.byId('distribution_rate[${distribution.id}]').value != null && dojo.byId('distribution_rate[${distribution.id}]').value != '')
					rate += parseFloat(dojo.byId('distribution_rate[${distribution.id}]').value);
			</c:forEach>
	
			if(rate != 0 && rate != 100)
			{
				alert('Total Distribution rate must equal to 100%.');
				return;
			}
				
			if(dojo.byId('distibutionAcc').value == null || dojo.byId('distibutionAcc').value == '')
			{
				alert('Distribution account cannot be empty!');
				return;
			}
		</c:if>
		
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
	
		document.editForm.action = "<c:url value='/page/accountingschemaedit.htm'/>";
		document.editForm.submit();
	}
		
	function popup(target)
	{
		var coa = document.getElementById('coa');
		if(!coa && !coa.value)
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.coa"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
			
		openpopup("<c:url value='/page/popupglaccountview.htm?level=ACCOUNT&coa='/>"+coa.value+"&target="+target);
	}
</script>