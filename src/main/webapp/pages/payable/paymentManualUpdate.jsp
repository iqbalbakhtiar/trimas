<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
                            <a class="item-button-list" href="<c:url value='/page/paymentmanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.edit}'>
                            	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
							<%-- <c:if test="${access.edit and paymentManual_edit.rejournalable}">
								<a class="item-button-doc" style="cursor: pointer;"><span>Rejournal</span></a>
							</c:if> --%>
			    			<c:if test="${access.delete}">
                            	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/paymentmanualdelete.htm?id=${paymentManual_edit.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
                            </c:if>
			    			<c:if test="${access.print}">
								<a class="item-button-print" href="<c:url value='/page/paymentmanualprint.htm?id=${paymentManual_edit.id}&invType=1'/>"><span><spring:message code="sirius.print"/></span></a>
							</c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute='paymentManual_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="70%" align="left" valign="top">
                                   	  <table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> : </td>
                                            <td width="76%"><input size="20" value="${paymentManual_edit.code}" class="input-disabled" disabled/></td>					
                                        </tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="organization"/> : </td>
                              				<td>
                                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${paymentManual_edit.organization.id}' label='${paymentManual_edit.organization.fullName}'/>
                                                </form:select>
                                            </td>					
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                                            <td><input size="8" disabled class="input-disabled" value="<fmt:formatDate value='${paymentManual_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
                                        </tr>
                                        <tr style="display: none;">
											<td align="right"><spring:message code="sirius.facility"/> :</td>
											<td>
												<form:select id="facility" path="facility" cssClass="combobox-ext input-disabled" readonly="true">
													<form:option value='${paymentManual_edit.facility.id}' label='${paymentManual_edit.facility.name}'/>
	                                            </form:select>
											</td>
										</tr>
										<tr style="display: none;">
                                            <td nowrap="nowrap" align="right"><spring:message code="paymentreferencetype"/> :</td>
                                            <td>
												<select id="paymentRefType" class="input-disabled" readonly="true">
													<option value='${paymentManual_edit.paymentManualType.referenceType}'><spring:message code="paymentreferencetype.${paymentManual_edit.paymentManualType.referenceType.messageName}"/></option>
												</select>
											</td>	
										</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="supplier"/> :</td>
                                            <td>
                                                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${paymentManual_edit.supplier.id}' label='${paymentManual_edit.supplier.fullName}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="paymenttype"/> :</td>
                                            <td>
                                                <form:select id="paymentManualType" path="paymentManualType" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${paymentManual_edit.paymentManualType.id}' label='${paymentManual_edit.paymentManualType.name}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        <%-- <c:if test="${not empty paymentManual_edit.salesMemoable}">
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                                            <td>
                                                <form:select id="salesMemoable" path="salesMemoable" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${paymentManual_edit.salesMemoable.id}' label='${paymentManual_edit.salesMemoable.code}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        </c:if> --%>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="currencymanagement"/> : </td>
                              				<td>
                                                <form:select id='currency' path='currency' readonly='true' cssClass="input-disabled">
                                                    <form:option value='${paymentManual_edit.currency.id}' label='${paymentManual_edit.currency.symbol}'/>
                                                </form:select>
                                                <%-- <form:select id='type' path='exchangeType' readonly="true" cssClass="input-disabled">
                                                    <form:option value='${paymentManual_edit.exchangeType}' label='${paymentManual_edit.exchangeType}'/>
                                                </form:select> --%>
                                                <input id="trxrate" class="number-disabled" disabled value="<fmt:formatNumber value='${paymentManual_edit.rate}' pattern=',##0.00'/>" size="10"/>
                                            </td>					
                                        </tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.note"/> : </td>
                                            <td><form:textarea path='note' cols='45' rows='6'/></td>					
                                        </tr>
                                        </table>
                                    </td>
                             		<td width="30%" align="center" valign="top">
                   						<table style="border:none" width="100%">
                                        <tr>
                                        	<td align="left" valign="top">
												<%-- <fieldset>
                                                	<legend><strong><spring:message code="payment.journal"/></strong></legend>
                                                    <c:forEach items='${paymentManual_edit.postings}' var='bridge' varStatus='status'>
                                                        <a href="<c:url value='/page/journalentrypreview.htm?id=${bridge.journalEntry.id}'/>"><c:out value='${bridge.journalEntry.code}'/></a><br/>
                                                    </c:forEach>
                                                </fieldset> --%>
                                            </td>
                                        </tr>
                                        </table>
                                  	</td>
                                </tr>
                                </table>
                                <br/>
                                <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 300px;">
                                	<div id="info" dojoType="ContentPane" label="<spring:message code='payment.information'/>" class="tab-pages" refreshOnShow="true">
                                        <table width="100%">
                                        <tr>
                                        	<td width="53%" align="left" valign="top">
                                           	  <table width="100%">
                                            	<tr>
                                                    <td width="27%" align="right"><spring:message code="payment.payment.type"/> : </td>
                                       	  	  		<td width="73%">
														<form:select id='methodType' path='paymentInformation.paymentMethodType' cssClass="input-disabled" disabled='true'>
                                                            <spring:message var="src" code="payment.${paymentManual_edit.paymentInformation.paymentMethodType.message}"/>
                                                            <form:option value='${paymentManual_edit.paymentInformation.paymentMethodType}' label='${fn:toUpperCase(src)}'/>
                                                        </form:select>
                                               	    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right"><spring:message code="payment.reference"/> : </td>
                                                    <td><form:input path='paymentInformation.reference' size='30' disabled='true' cssClass='input-disabled'/></td>
                                                </tr>
                                                <tr style="display: none;">
                                                    <td align="right"><spring:message code="bankaccount"/> : </td>
                                                    <td>
                                                        <form:select id='account' path='paymentInformation.bankAccount' cssClass='combobox input-disabled' disabled='true'>
                                                            <c:if test='${not empty paymentManual_edit.paymentInformation.bankAccount}'>
                                                                <form:option value='${paymentManual_edit.paymentInformation.bankAccount.id}' label='${paymentManual_edit.paymentInformation.bankAccount.code}' />
                                                            </c:if>
                                                        </form:select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                                                    <td><input id='accountName' value="${paymentManual_edit.paymentInformation.bankAccount.accountName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                                                    <td><input id='bankName' value="${paymentManual_edit.paymentInformation.bankAccount.bankName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.branch"/> : </td>
                                                    <td><input id='bankBranch' value="${paymentManual_edit.paymentInformation.bankAccount.bankBranch}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                                                    <td><input id='accountNo' value="${paymentManual_edit.paymentInformation.bankAccount.accountNo}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                </table>
                                            </td>
                                         	<td width="47%" align="left" valign="top">
											<table width="100%">
                                                <tr>
                                                    <td width="39%" align="right" nowrap="nowrap"><spring:message code="payment.duedate"/> :</td>
                                                    <td width="61%"><input size="8" value="<fmt:formatDate value='${paymentManual_edit.paymentInformation.dueDate}' pattern='dd-MM-yyyy'/>" class="input-disabled" disabled/></td>
                                            	</tr>
                                                <tr>
                                                  	<td nowrap="nowrap" align="right"><spring:message code="payment.amount"/> : </td>
                                                    <td><input size="15" disabled class="input-disabled" style="text-align:right;" value="<fmt:formatNumber value='${paymentManual_edit.paymentInformation.amount}' pattern=',##0.00'/>"/></td>
                                                </tr>
                                                <tr>
                                                  	<td nowrap="nowrap" align="right"><spring:message code="payment.charge"/> : </td>
                                                    <td><input size="15" disabled class="input-disabled" style="text-align:right;" value="<fmt:formatNumber value='${paymentManual_edit.paymentInformation.bankCharges}' pattern=',##0.00'/>"/></td>
                                                </tr>
                                                <tr>
                                                  	<td align="right"><spring:message code="sirius.note"/> : </td>
                                                    <td><form:textarea path='paymentInformation.note' cols='40' rows='6'/></td>					
                                                </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        </table>
                                	</div>
                                	<div id="infoTo" dojoType="ContentPane" label="<spring:message code='payment.information'/> <spring:message code='sirius.to'/>" class="tab-pages" refreshOnShow="true">
                                        <table width="100%">
                                        <tr>
                                        	<td width="53%" align="left" valign="top">
                                           	  <table width="100%">
                                            	<tr>
                                                    <td width="27%" align="right"><spring:message code="payment.payment.type"/> : </td>
                                       	  	  		<td width="73%">
														<form:select id='methodTypeTo' path='paymentInformation.paymentMethodTypeTo' cssClass="input-disabled" disabled='true'>
                                                            <spring:message var="src" code="payment.${paymentManual_edit.paymentInformation.paymentMethodTypeTo.message}"/>
                                                            <form:option value='${paymentManual_edit.paymentInformation.paymentMethodTypeTo}' label='${fn:toUpperCase(src)}'/>
                                                        </form:select>
                                               	    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right"><spring:message code="payment.reference"/> : </td>
                                                    <td><form:input path='paymentInformation.referenceTo' size='30' disabled='true' cssClass='input-disabled'/></td>
                                                </tr>
                                                <tr style="display: none;">
                                                    <td align="right"><spring:message code="bankaccount"/> : </td>
                                                    <td>
                                                        <form:select id='account' path='paymentInformation.bankAccount' cssClass='combobox input-disabled' disabled='true'>
                                                            <c:if test='${not empty paymentManual_edit.paymentInformation.bankAccountTo}'>
                                                                <form:option value='${paymentManual_edit.paymentInformation.bankAccountTo.id}' label='${paymentManual_edit.paymentInformation.bankAccountTo.code}' />
                                                            </c:if>
                                                        </form:select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                                                    <td><input id='accountName' value="${paymentManual_edit.paymentInformation.bankAccountTo.accountName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                                                    <td><input id='bankName' value="${paymentManual_edit.paymentInformation.bankAccountTo.bankName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.branch"/> : </td>
                                                    <td><input id='bankBranch' value="${paymentManual_edit.paymentInformation.bankAccountTo.bankBranch}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                                                    <td><input id='accountNo' value="${paymentManual_edit.paymentInformation.bankAccountTo.accountNo}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                </table>
                                            </td>
                                         	<td width="47%" align="left" valign="top">
                                            </td>
                                        </tr>
                                        </table>
                                	</div>
                            	</div>
                                </table>
							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${paymentManual_edit.createdBy.fullName}'/> (<fmt:formatDate value='${paymentManual_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${paymentManual_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${paymentManual_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="payment.manual"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/paymentmanualedit.htm'/>",
				data:$('#editForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/paymentmanualpreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});		
		});

		$('.item-button-doc').click(function(e) {
			const confirmDialog = $('<div><spring:message code="notif.rejournal"/> ?</div>').dialog(
			{
				autoOpen: false, title: 'Rejournal', modal:true,
				buttons: {
					'<spring:message code="sirius.yes"/>': function() {
						$(this).dialog('close');
						rejournal();	
					},
					'<spring:message code="sirius.no"/>': function() {
						$('.item-button-doc').show();
						$(this).dialog('close');
					}
				}
			});

			confirmDialog.dialog('open');
		});
	});
	
	function rejournal() {
		$.ajax({
			url:"<c:url value='/page/paymentmanualrejournal.htm'/>",
			data:{id:'${paymentManual_edit.id}'},
			method : 'POST',
			dataType : 'json',
			beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
			success : function(json) {
				if(json)
				{
					if(json.status == 'OK')
					{
						$dialog.dialog('close');
						window.location="<c:url value='/page/paymentmanualpreedit.htm?id='/>"+json.id;
					}
					else
							afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		});  
	}
</script>
