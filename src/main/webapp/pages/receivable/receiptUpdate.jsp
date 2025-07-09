<%--This Page Copied From salesOrderAdd.jsp(COCINA) and receiptAdd.jsp (MML)--%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <c:if test="${empty redirectUri}">
        <a class="item-button-list" href="<c:url value='/page/receiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    </c:if>
    <c:if test="${not empty redirectUri}">
        <a class="item-button-back" href="<c:url value='/page/${redirectUri}'/>"><span><spring:message code="sirius.back"/></span></a>
        <a class="item-button-list" href="<c:url value='/page/receiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    </c:if>
    <c:if test='${access.edit}'>
        <a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
    </c:if>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="receipt_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right">Sales Order ID</td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><input class="inputbox input-disabled" value="${receipt_form.code}" disabled/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" class="input-disabled" disabled size="10"
                                       value="<fmt:formatDate value='${receipt_form.date}' pattern='dd-MM-yyyy'/>"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty receipt_form.organization}'>
                                        <form:option value='${receipt_form.organization.id}' label='${receipt_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="customer"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty receipt_form.customer}'>
                                        <form:option value='${receipt_form.customer.id}' label='${receipt_form.customer.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.note"/></td>
                            <td width="1%" align="center">:</td>
                            <td><form:textarea path="receipt.note" rows="6" cols="45"/></td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </td>

                <td width="40%" valign="top">
                    <table width="100%" style="border: none">
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="receipt.information"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="receipt.total"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalAmount"
                                                value="<fmt:formatNumber value='${adapter.totalPaidAmount}' pattern=',##0.00'/>"
                                                class="number-disabled" readonly="readonly" size="20"/>
                                            </td>
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
            <div id="receiptInformation" dojoType="ContentPane" label="<spring:message code='receipt.information'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
                <tr>
                    <td width="50%" align="left" valign="top">
                        <table width="100%">
                        <tr>
                            <td width="25%" align="right"><spring:message code="receipt.payment.type"/> : </td>
                            <td width="75%">
                                <select id='methodType' class="input-disabled" disabled>
                                    <c:if test="${receipt_form.receiptInformation.paymentMethodType != null}" >
                                        <option value="${receipt_form.receiptInformation.paymentMethodType}">
                                                ${receipt_form.receiptInformation.paymentMethodType}
                                        </option>
                                    </c:if>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="receipt.reference"/> : </td>
                            <td>
                                <input id='clearing' size='30' disabled class="input-disabled"
                                       value="${receipt_form.receiptInformation.reference}"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="bankaccount"/> : </td>
                            <td>
                                <select id='account' class="combobox input-disabled">
                                    <c:if test='${not empty receipt_form.receiptInformation.bankAccount}'>
                                        <option value='${receipt_form.receiptInformation.bankAccount.id}'
                                                label='${receipt_form.receiptInformation.bankAccount.code}'>
                                        </option>
                                    </c:if>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                            <td><input id='accountName' disabled class='input-disabled' size="30"
                                    value="${receipt_form.receiptInformation.bankAccount.accountName}"/>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                            <td><input id='bankName' disabled class='input-disabled' size="30"
                                       value="${receipt_form.receiptInformation.bankAccount.bankName}"/>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                            <td><input id='accountNo' disabled class='input-disabled' size="30"
                                       value="${receipt_form.receiptInformation.bankAccount.accountNo}"/>
                            </td>
                        </tr>
                        </table>
                    </td><%--Akhir Dari Panel Receipt Information Bagian Kiri--%>

                    <td width="50%" align="left" valign="top">
                        <table width="100%">
                        <tr>
                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="receipt.duedate"/> :</td>
                            <td width="61%">
                                <input id="duedate" name="receiptInformation.dueDate" class="input-disabled" disabled size="10"
                                       value="<fmt:formatDate value='${receipt_form.receiptInformation.dueDate}' pattern='dd-MM-yyyy'/>"/>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
                            <td>
                                <input id='receiptAmount' class="number-disabled" disabled
                                       value="<fmt:formatNumber value='${receipt_form.receiptInformation.amount}' pattern=',##0.00'/>" />
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="receipt.charge"/> : </td>
                            <td>
                                <input id='receiptBankCharges' class="number-disabled" disabled
                                       value="<fmt:formatNumber value='${receipt_form.receiptInformation.bankCharges}' pattern=',##0.00'/>" />
                            </td>
                        </tr>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.note"/> : </td>
                            <td><form:textarea path='receipt.receiptInformation.note' cols='40' rows='6'/></td>
                        </tr>
                        </table>
                    </td><%--Akhir Dari Panel Receipt Information Bagian Kanan--%>
                </tr>
                </table>
            </div>

            <div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='receipt.application'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                        <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th width="10%"><spring:message code="billing"/></th>
                            <th width="10%"><spring:message code="billing.date"/></th>
                            <th width="20%"><spring:message code="payment.writeoff.type"/></th>
                            <th width="16%"><spring:message code="payment.writeoff"/></th>
                            <th width="60%"><spring:message code="receipt.paid"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItemBody">
                        <c:forEach items='${receipt_edit.applications}' var='app'>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a href="<c:url value='/page/${app.billing.billingType.url}?id=${app.billing.id}'/>"><c:out value='${app.billing.code}'/></a></td>
                                <td><fmt:formatDate value='${app.billing.date}' pattern='dd-MM-yyyy'/></td>
                                <td>
                                    <select id="customer" class="combobox" disabled="true">
                                        <c:if test='${not empty app.writeOffType}'>
                                            <option value='${app.writeOffType}' label='${app.writeOffType}'/>
                                        </c:if>
                                    </select>
                                </td>
                                <td><fmt:formatNumber value='${app.writeOff}' pattern=',##0.00'/></td>
                                <td><fmt:formatNumber value='${app.paidAmount}' pattern=',##0.00'/></td>
                                <td>&nbsp;</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="7">&nbsp;</td></tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${receipt_form.createdBy.fullName}'/> (<fmt:formatDate value='${receipt_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${receipt_form.updatedBy.fullName}'/> (<fmt:formatDate value='${receipt_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
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
        var customer = $('#customer').val();
        if (customer == null || customer === "") {
            alert('<spring:message code="customer"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/receiptedit.htm'/>",
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
                        window.location="<c:url value='/page/receiptview.htm'/>";
                        // Or Can use This
                        <%--window.location="<c:url value='/page/receiptpreedit.htm?id='/>"+json.data.id;--%>
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