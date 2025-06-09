<%--This Page Copied From receipt.add--%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/paymentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="payment_edit" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><form:input path="code" id="code" cssClass="inputbox input-disabled" disabled="true"/></td>
                            <td width="1%"><form:errors path="code"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty payment_edit.organization}'>
                                        <form:option value='${payment_edit.organization.id}' label='${payment_edit.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="supplier"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty payment_edit.supplier}'>
                                        <form:option value='${payment_edit.supplier.id}' label='${payment_edit.supplier.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" size="10" class="input-disabled" value="<fmt:formatDate value='${payment_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
						<tr>
							<td align="right"><spring:message code="billing.invoice.tax.no"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input size="28" id="taxNo" path="taxNo"/></td>
						</tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.note"/></td>
                            <td width="1%" align="center">:</td>
                            <td><form:textarea path="note" rows="6" cols="45"/></td>
                        </tr>
                    </table>
                </td>

                <td width="40%" valign="top">
                    <table width="100%" style="border: none">
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="payment.information"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <th width="80%">&nbsp;</th>
                                            <th width="20%" align="right"><spring:message code="sirius.amount"/> (<c:out value='${payment_edit.currency.symbol}'/>)</th>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.total.unapplied"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalUnapplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.total.applied"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalApplied" value="<fmt:formatNumber value='${adapter.paid}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.writeoff"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalWriteOff" value="<fmt:formatNumber value='${adapter.writeoff}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                    </table>
                </td>

            </tr>
        </table>
        <br/>
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="paymentInformation" dojoType="ContentPane" label="<spring:message code='payment.information'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
                    <tr>
                        <td width="25%" align="right"><spring:message code="sirius.type"/> : </td>
                        <td width="75%">
                            <form:select id='methodType' path='paymentInformation.paymentMethodType' cssClass="input-disabled" disabled='true'>
                                <spring:message var="src" code="payment.${payment_edit.paymentInformation.paymentMethodType.message}"/>
                                <form:option value='${payment_edit.paymentInformation.paymentMethodType}' label='${fn:toUpperCase(src)}'/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="receipt.reference"/> : </td>
                        <td><form:input id='clearing' path='paymentInformation.reference' size='30' disabled='true' cssClass='input-disabled'/></td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="bankaccount"/> : </td>
                        <td>
                            <form:select id='account' path='paymentInformation.bankAccount' cssClass='combobox input-disabled' disabled='true'>
                                <c:if test='${not empty payment_edit.paymentInformation.bankAccount}'>
                                    <form:option value='${payment_edit.paymentInformation.bankAccount.id}' label='${payment_edit.paymentInformation.bankAccount.code}' />
                                </c:if>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" align="right"><spring:message code="payment.amount"/> : </td>
                        <td><input disabled class="input-disabled" style="text-align:right;" value="<fmt:formatNumber value='${payment_edit.paymentInformation.amount}' pattern=',##0.00'/>"/>
                    </tr>
                    <tr style="display: none"><%--This Field Hidden because used in addLineItem function--%>
                        <td colspan="2">
                            <select id="wtype" style="display: none;">
                                <c:forEach items='${writes}' var='type' varStatus="status">
                                    <option value='${type}'>${type}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='payment.application'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                        <thead>
                        <tr>
<%--                            <th width="1%"><input class="checkall" type="checkbox"/></th>--%>
                            <th width="20%"><spring:message code="invoiceverification"/></th>
                            <th width="10%"><spring:message code="payment.writeoff.type"/></th>
                            <th width="10%"><spring:message code="payment.writeoff"/></th>
                            <th><spring:message code="payment.paid.amount"/>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        <c:forEach items='${payment_edit.applications}' var='app'>
                            <tr>
                                <td><a href="<c:url value='/page/${app.payable.uri}?id=${app.payable.id}'/>"><c:out value='${app.payable.code}'/></a></td>
                                <td><c:out value='${app.writeoffType}'/></td>
                                <c:if test="${not empty app.writeOff}">
                                    <td><fmt:formatNumber value='${app.writeOff}' pattern=',##0.00'/></td>
                                </c:if>
                                <td><fmt:formatNumber value='${app.paidAmount}' pattern=',##0.00'/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="9">&nbsp;</td></tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${payment_edit.createdBy.fullName}'/> (<fmt:formatDate value='${payment_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${payment_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${payment_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        var $index = 0; // For Line Item Index

        $('.item-button-save').click(function(){
            if(validateForm()) {
                save();
            }
        });
    });

    function validateForm() {
        // Validasi organisasi (sudah ada sebelumnya)
        var organization = $('#org').val();
        if (organization == null || organization === "") {
            alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi date
        var date = $('#date').val();
        if (date == null || date === "") {
            alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi customer
        var supplier = $('#supplier').val();
        if (supplier == null || supplier === "") {
            alert('<spring:message code="supplier"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        var bankAccount = $('#account').val();
        if (bankAccount == null || bankAccount === "") {
            alert('<spring:message code="bankaccount"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/paymentedit.htm'/>",
            data:$('#addForm').serialize(),
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
                        window.location="<c:url value='/page/paymentpreedit.htm?id='/>"+json.id;
                    }
                    else
                    {
                        $dialog.empty();
                        $dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
                    }
                }
            }
        });
    }
</script>