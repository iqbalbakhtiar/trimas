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
                            <a class="item-button-list" href="<c:url value='/page/receiptmanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.edit}'>
                            	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
							<%-- <c:if test="${access.edit and receiptManual_edit.rejournalable}">
								<a class="item-button-doc" style="cursor: pointer;"><span>Rejournal</span></a>
							</c:if> --%>
			    			<c:if test="${access.delete}">
                            	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/receiptmanualdelete.htm?id=${receiptManual_edit.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
                            </c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute='receiptManual_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="70%" align="left" valign="top">
                                   	  <table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> : </td>
                                            <td width="76%"><input size="20" value="${receiptManual_edit.code}" class="input-disabled" disabled/></td>					
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                                            <td><input size="8" disabled class="input-disabled" value="<fmt:formatDate value='${receiptManual_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
                                        </tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="organization"/> : </td>
                              				<td>
                                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${receiptManual_edit.organization.id}' label='${receiptManual_edit.organization.fullName}'/>
                                                </form:select>
                                            </td>					
                                        </tr>
                                        <tr style="display: none;">
											<td align="right"><spring:message code="sirius.facility"/> :</td>
											<td>
												<form:select id="facility" path="facility" cssClass="combobox-ext input-disabled" readonly="true">
													<form:option value='${receiptManual_edit.facility.id}' label='${receiptManual_edit.facility.name}'/>
	                                            </form:select>
											</td>
										</tr>
										<tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="receiptreferencetype"/> :</td>
                                            <td>
												<select id="paymentRefType" class="input-disabled" readonly="true">
													<option value='${receiptManual_edit.receiptManualType.referenceType}'><spring:message code="receiptreferencetype.${receiptManual_edit.receiptManualType.referenceType.messageName}"/></option>
												</select>
											</td>	
										</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="customer"/> / <spring:message code="supplier"/> :</td>
                                            <td>
                                                <form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${receiptManual_edit.customer.id}' label='${receiptManual_edit.customer.fullName}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="receipttype"/> :</td>
                                            <td>
                                                <form:select id="receiptManualType" path="receiptManualType" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${receiptManual_edit.receiptManualType.id}' label='${receiptManual_edit.receiptManualType.name}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        <%-- <c:if test="${not empty receiptManual_edit.purchaseMemoable}">
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                                            <td>
                                                <form:select id="purchaseMemoable" path="purchaseMemoable" cssClass="combobox-ext input-disabled" readonly="true">
                                                	<form:option value='${receiptManual_edit.purchaseMemoable.id}' label='${receiptManual_edit.purchaseMemoable.code}'/>
                                                </form:select>
                                            </td>
                                        </tr>
                                        </c:if> --%>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="currencymanagement"/> : </td>
                              				<td>
                                                <form:select id='currency' path='currency' readonly='true' cssClass="input-disabled">
                                                    <form:option value='${receiptManual_edit.currency.id}' label='${receiptManual_edit.currency.symbol}'/>
                                                </form:select>
                                                <%-- <form:select id='type' path='exchangeType' readonly="true" cssClass="input-disabled">
                                                    <form:option value='${receiptManual_edit.exchangeType}' label='${receiptManual_edit.exchangeType}'/>
                                                </form:select> --%>
                                                <input id="trxrate" class="number-disabled" disabled value="<fmt:formatNumber value='${receiptManual_edit.rate}' pattern=',##0.00'/>" size="10"/>
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
                   						<c:if test="${receiptManual_edit.receiptInformation.paymentMethodType == 'CLEARING'}">
	                                        <tr>
	                                        	<td align="left" valign="top">
	                                            	<fieldset>
	                                                	<legend><strong><spring:message code="receipt.clear.information"/></strong></legend>
	                                                    <table style="border:none" width="100%">
	                                                    <tr>
	                                                    	<td colspan="3" align="right">
	                                                        	<c:if test="${receiptManual_edit.receiptInformation.paymentMethodType == 'CLEARING' && receiptManual_edit.cleared}">
	                                                                <strong>
	                                                                    <font size="+1"><spring:message var="cleared" code="receipt.cleared"/>${fn:toUpperCase(cleared)}</font>
	                                                                </strong>
	                                                            </c:if>
	                                                            <c:if test="${receiptManual_edit.receiptInformation.paymentMethodType == 'CLEARING' && !receiptManual_edit.cleared}">
	                                                                <strong>
	                                                                    <font size="+1"><spring:message var="clearing" code="receipt.clear"/>${fn:toUpperCase(clearing)}</font>
	                                                                </strong>
	                                                            </c:if>
	                                                        </td>
	                                                    </tr>
	 													<tr>
	                                                        <td width="209" align="right"><spring:message code="receipt.clear.reference"/> : </td>
	                                                        <td width="309" colspan="2" align="right"><a href="<c:url value='/page/clearreceiptpreedit.htm?id=${receiptManual_edit.clearReceipt.id}'/>">${receiptManual_edit.clearReceipt.code}</a></td>
	                                                    </tr>
	                                                    </table>
	                                                </fieldset>
	                                            </td>
	                                        </tr>
                                        </c:if>
                                        <tr>
                                        	<td>
                                            	<%-- <fieldset>
                                                	<legend><strong><spring:message code="receipt.journal"/></strong></legend>
                                                    <c:forEach items='${receiptManual_edit.postings}' var='bridge' varStatus='status'>
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
                                	<div id="info" dojoType="ContentPane" label="<spring:message code='receipt.information'/>" class="tab-pages" refreshOnShow="true">
                                        <table width="100%">
                                        <tr>
                                        	<td width="53%" align="left" valign="top">
                                           	  <table width="100%">
                                            	<tr>
                                                    <td width="27%" align="right"><spring:message code="receipt.payment.type"/> : </td>
                                       	  	  		<td width="73%">
														<form:select id='methodType' path='receiptInformation.paymentMethodType' cssClass="input-disabled" disabled='true'>
                                                            <spring:message var="src" code="receipt.${receiptManual_edit.receiptInformation.paymentMethodType.message}"/>
                                                            <form:option value='${receiptManual_edit.receiptInformation.paymentMethodType}' label='${fn:toUpperCase(src)}'/>
                                                        </form:select>
                                               	    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right"><spring:message code="payment.reference"/> : </td>
                                                    <td><form:input path='receiptInformation.reference' size='30' disabled='true' cssClass='input-disabled'/></td>
                                                </tr>
                                                <tr>
                                                    <td align="right"><spring:message code="bankaccount"/> : </td>
                                                    <td>
                                                        <form:select id='account' path='receiptInformation.bankAccount' cssClass='combobox input-disabled' disabled='true'>
                                                            <c:if test='${not empty receiptManual_edit.receiptInformation.bankAccount}'>
                                                                <form:option value='${receiptManual_edit.receiptInformation.bankAccount.id}' label='${receiptManual_edit.receiptInformation.bankAccount.code}' />
                                                            </c:if>
                                                        </form:select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                                                    <td><input id='accountName' value="${receiptManual_edit.receiptInformation.bankAccount.accountName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                                                    <td><input id='bankName' value="${receiptManual_edit.receiptInformation.bankAccount.bankName}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.branch"/> : </td>
                                                    <td><input id='bankBranch' value="${receiptManual_edit.receiptInformation.bankAccount.bankBranch}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                                                    <td><input id='accountNo' value="${receiptManual_edit.receiptInformation.bankAccount.accountNo}" disabled='true' class='input-disabled' size="30"/></td>
                                                </tr>
                                                </table>
                                            </td>
                                         	<td width="47%" align="left" valign="top">
											<table width="100%">
                                                <tr>
                                                    <td width="39%" align="right" nowrap="nowrap"><spring:message code="payment.duedate"/> :</td>
                                                    <td width="61%"><input size="8" value="<fmt:formatDate value='${receiptManual_edit.receiptInformation.dueDate}' pattern='dd-MM-yyyy'/>" class="input-disabled" disabled/></td>
                                            	</tr>
                                                <tr>
                                                  	<td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
                                                    <td><input size="15" disabled class="input-disabled" style="text-align:right;" value="<fmt:formatNumber value='${receiptManual_edit.receiptInformation.amount}' pattern=',##0.00'/>"/></td>
                                                </tr>
                                                <tr>
                                                  	<td nowrap="nowrap" align="right"><spring:message code="payment.charge"/> : </td>
                                                    <td><input size="15" disabled class="input-disabled" style="text-align:right;" value="<fmt:formatNumber value='${receiptManual_edit.receiptInformation.bankCharges}' pattern=',##0.00'/>"/></td>
                                                </tr>
                                                <tr>
                                                  	<td align="right"><spring:message code="sirius.note"/> : </td>
                                                    <td><form:textarea path='receiptInformation.note' cols='40' rows='6'/></td>					
                                                </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        </table>
                                	</div>
                            	</div>
                                </table>
							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${receiptManual_edit.createdBy.fullName}'/> (<fmt:formatDate value='${receiptManual_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${receiptManual_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${receiptManual_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="receipt.manual"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/receiptmanualedit.htm'/>",
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
							window.location="<c:url value='/page/receiptmanualpreedit.htm?id='/>"+json.id;
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
		
		function rejournal() {
			$.ajax({
				url:"<c:url value='/page/receiptmanualrejournal.htm'/>",
				data:{id:'${receiptManual_edit.id}'},
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/receiptmanualpreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});  
		}
	});
</script>
