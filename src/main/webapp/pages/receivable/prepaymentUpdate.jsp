<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/prepaymentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <c:if test="${access.edit}">
    	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
    </c:if>
    <c:if test="${access.add and prepayment_edit.applied}">
    	<a class="item-button-new" href="<c:url value='/page/prepaymentpreapply.htm?id=${prepayment_edit.id}'/>"><span><spring:message code="sirius.apply"/></span></a>
    </c:if>
</div>

<div class="main-box">
    <sesform:form id="editForm" name="editForm" method="post" modelAttribute="prepayment_form">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><input class="inputbox input-disabled" value="${prepayment_edit.code}" disabled/></td>
                            <td width="1%"><form:errors path="code"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" size="8" value="<fmt:formatDate value='${prepayment_edit.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext" disabled="true">
									<form:option value='${prepayment_edit.organization.id}' label='${prepayment_edit.organization.fullName}'/>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="customer"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="customer" path="customer" cssClass="combobox-ext" disabled="true">
                                	<form:option value='${prepayment_edit.customer.id}' label='${prepayment_edit.customer.fullName}'/>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.note"/></td>
                            <td width="1%" align="center">:</td>
                            <td><form:textarea path="note" rows="6" cols="45"/></td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </td>
                <td width="40%" valign="top">
                </td>
            </tr>
        </table>
        <br/>
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="receiptInformation" dojoType="ContentPane" label="<spring:message code='receipt.information'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
	                <tr>
	                    <td width="50%" align="left" valign="top">
	                        <table width="100%">
		                        <tr>
		                            <td width="25%" align="right"><spring:message code="receipt.payment.type"/> : </td>
		                            <td width="75%">
		                                <form:select id='methodType' path='receiptInformation.paymentMethodType' disabled="true">
		                                	<form:option value="${prepayment_edit.receiptInformation.paymentMethodType}"/>
		                                </form:select>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td align="right"><spring:message code="receipt.reference"/> : </td>
		                            <td><form:input id='clearing' path='receiptInformation.reference' size='30' disabled='true'/></td>
		                        </tr>
		                        <tr>
		                            <td align="right"><spring:message code="bankaccount"/> : </td>
		                            <td>
		                                <form:select id='account' path='receiptInformation.bankAccount' cssClass="combobox" disabled="true">
		                                	<form:option value="${prepayment_edit.receiptInformation.bankAccount.id}" label="${prepayment_edit.receiptInformation.bankAccount.bankName}"/>
		                                </form:select>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
		                            <td><input id='accountName' value="${prepayment_edit.receiptInformation.bankAccount.accountName}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
		                            <td><input id='bankName' value="${prepayment_edit.receiptInformation.bankAccount.bankName}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
		                            <td><input id='accountNo' value="${prepayment_edit.receiptInformation.bankAccount.accountNo}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
	                        </table>
	                    </td>
	                    <td width="50%" align="left" valign="top">
	                        <table width="100%">
		                        <tr>
		                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="receipt.duedate"/> :</td>
		                            <td width="61%"><input id="duedate" value="${prepayment_edit.receiptInformation.dueDate}" size="8" class="input-disabled" disabled/></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
		                            <td>
		                                <input id='amount' value="<fmt:formatNumber value='${prepayment_edit.receiptInformation.amount}' pattern='#,##0.00'/>" class='number-disabled' disabled/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.charge"/> : </td>
		                            <td>
		                                <input id='bankCharges' value="<fmt:formatNumber value='${prepayment_edit.receiptInformation.bankCharges}' pattern='#,##0.00'/>" class='number-disabled' disabled/>
		                            </td>
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
			<div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='receipt'/>" class="tab-pages" refreshOnShow="true" selected="true">
	            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
	                <thead>
	                <tr>
	                    <th width="1%">&nbsp;</th>
	                    <th width="20%"><spring:message code="receipt"/></th>
	                    <th width="10%"><spring:message code="prepayment.apply.date"/></th>
	                    <th width="60%"><spring:message code="receipt.paid"/></th>
	                </tr>
	                </thead>
	                <tbody id="lineItemBody">
	                <c:forEach items='${prepayment_edit.receipts}' var='receipt'>
	                    <tr>
	                        <td>&nbsp;</td>
	                        <td><a href="<c:url value='/page/receiptpreedit?id=${receipt.id}'/>"><c:out value='${receipt.code}'/></a></td>
	                        <td><fmt:formatDate value='${receipt.realDate}' pattern='dd-MM-yyyy'/></td>
	                        <td><fmt:formatNumber value='${receipt.receiptInformation.amount}' pattern=',##0.00'/></td>
	                    </tr>
	                </c:forEach>
	                </tbody>
	                <tfoot>
	                <tr class="end-table"><td colspan="4">&nbsp;</td></tr>
	                </tfoot>
	            </table>
            </div>
        </div>
        </div>
    </sesform:form>
    <div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${facility_edit.createdBy.fullName}'/> (<fmt:formatDate value='${facility_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${facility_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${facility_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        $('.item-button-save').click(function(){
        	$.ajax({
                url:"<c:url value='/page/prepaymentedit.htm'/>",
                data:$('#editForm').serialize(),
                type : 'POST',
                dataType : 'json',
                beforeSend:function()
                {
                    $dialog.empty();
                    $dialog.html('<spring:message code="notif.saving"/>');
                    $dialog.dialog('open');
                },
                success : function(json) {
                    if(json)
                    {
                        if(json.status === 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/prepaymentpreedit.htm?id='/>"+json.id;
                        }
                        else
                        {
                            $dialog.empty();
                            $dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
                        }
                    }
                }
            });
        });
    });
</script>