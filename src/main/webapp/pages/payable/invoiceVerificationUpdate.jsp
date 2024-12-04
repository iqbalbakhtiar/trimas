<%--This Page Copied From billingUpdate.jsp--%>
<%--suppress ALL --%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/invoiceverificationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
<%--    <a class="item-button-print"  href="<c:url value='/page/invoiceverificationprint.htm?id=${verification_edit.invoiceVerification.id}'/>"><span><spring:message code="sirius.print"/></span></a>--%>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="verification_edit" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%" valign="top">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
                            <td width="1%"><form:errors path="code"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="billing.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${verification_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty verification_edit.organization}'>
                                        <form:option value='${verification_edit.organization.id}' label='${verification_edit.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="invoiceverification.billfrom"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty verification_edit.supplier}'>
                                        <form:option value="${verification_edit.supplier.id}">${verification_edit.supplier.code} ${verification_edit.supplier.fullName}</form:option>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="invoiceverification.po.no"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%">
                                <input id="poNo" name="poNo" class="inputbox input-disabled" disabled value="${poNo}"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="34%" align="right"><spring:message code="invoiceverification.gr.no"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%">
                                <input id="grNo" name="grNo" class="inputbox input-disabled" disabled value="${verification_edit.goodsReceipt.code}"/>
                            </td>
                        </tr>
                        <tr>
                            <td width="34%" align="right"><spring:message code="goodsreceipt.supplier.invoice"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%">
                                <input id="invoiceno" name="invoiceno" class="inputbox input-disabled" disabled value="${verification_edit.goodsReceipt.invoiceNo}"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="payment.duedate"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="dueDate" name="dueDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${verification_edit.dueDate}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
<%--                        Maybe in the future this field is need to show--%>
<%--                        <tr>--%>
<%--                            <td align="right"><spring:message code="billing.paiddate"/></td>--%>
<%--                            <td width="1%" align="center">:</td>--%>
<%--                            <td><input id="paidDate" name="paidDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${verification_edit.paidDate}' pattern='dd-MM-yyyy'/>"/></td>--%>
<%--                        </tr>--%>
                        <tr>
                            <td align="right"><spring:message code="salesorder.tax.type"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="tax" path="tax" cssClass="input-disabled" disabled="true">
                                    <c:if test='${not empty verification_edit.tax}'>
                                        <option value="${verification_edit.tax.id}" data-taxrate="${verification_edit.tax.taxRate}">${verification_edit.tax.taxName}</option>
                                    </c:if>
                                </form:select>
                                <spring:message code="billing.rate.percent"/>
                                <input size="7" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${verification_edit.tax.taxRate}' pattern=',##0.00'/>"/>
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
                    <table width="100%" style="border: none">
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="billing.receiptinformation"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <td colspan="2" align="right">
                                                <c:if test="${verification_edit.invoiceVerification.status eq 'UNPAID'}"><h2 style="color: red;"><c:out value="${verification_edit.invoiceVerification.status.formattedName}"/></h2></c:if>
                                                <c:if test="${verification_edit.invoiceVerification.status eq 'PAID'}"><h2 style="color: green;"><c:out value="${verification_edit.invoiceVerification.status.formattedName}"/></h2></c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="billing.unpaidamount"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="unpaid" value="<fmt:formatNumber value='${verification_edit.invoiceVerification.unpaid}' pattern=',##0.00'/>"
                                                       class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right" valign="top"><spring:message code="receipt.references"/>:&nbsp;</td>
                                            <td width="20%" align="right">
                                                <c:forEach items="${verification_edit.invoiceVerification.applications}" var="app" varStatus="status">
                                                    <a href="<c:url value='/page/receiptpreedit.htm?id=${app.payment.id}'/>"><c:out value="${app.payment.code}"></c:out></a>
                                                    <br>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right" valign="top"><spring:message code="billing.creditmemo.reference"/>:&nbsp;</td>
                                            <td width="20%" align="right"></td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="directsalesorder.transaction"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="purchaseorder.purchase"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="purchase" value="<fmt:formatNumber value='${verification_adapter.totalAmount}' pattern=',##0.00'/>"
                                                       class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="sirius.tax"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="taxAmount" value="<fmt:formatNumber value='${verification_adapter.taxAmount}' pattern=',##0.00'/>"
                                                       class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><strong><spring:message code="dpo.transaction.amount"/></strong>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="unpaid" value="<fmt:formatNumber value='${verification_adapter.totalTransaction}' pattern=',##0.00'/>"
                                                       class="number-disabled" readonly="readonly" size="20" disabled/>
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
        <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
                <th width="1%" nowrap="nowrap"></th>
                <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
                <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
                <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
                <th width="8%" nowrap="nowrap"><spring:message code="dpo.buying.price"/></th>
                <th nowrap="nowrap"><spring:message code="sirius.amount"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${verification_adapter.items}" var="item" varStatus="idx">
                <tr>
                    <td></td>
                    <td>
                        <input id="product[${idx.index}]" value="${item.invoiceVerificationReceipt.goodsReceiptItem.warehouseTransactionItem.referenceItem.product.name}"
                               class="input-disabled productInput inputbox" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/>
                    </td>
                    <td>
                        <input id="receipted[${idx.index}]" size="6" value="<fmt:formatNumber
										value='${item.invoiceVerificationReceipt.goodsReceiptItem.receipted}' pattern=',##0.00'/>" class="input-disabled input-decimal"
                               name="items[${idx.index}].receipted" index="${idx.index}" next="receipted" disabled/>
                    </td>
                    <td>
                        <input id="uom[${idx.index}]" value="${item.invoiceVerificationReceipt.goodsReceiptItem.warehouseTransactionItem.referenceItem.product.unitOfMeasure.measureId}"
                               class="input-disabled productInput inputbox-small" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
                    </td>
                    <td>
                        <input id="price[${idx.index}]" value="<fmt:formatNumber value='${item.invoiceVerificationReceipt.goodsReceiptItem.warehouseTransactionItem.money.amount}' pattern=',##0.00'/>"
                               size="12" class="input-disabled input-decimal" name="items[${idx.index}].price" index="${idx.index}" next="price" disabled/>
                    </td>
                    <td>
                        <input id="subTotal[${idx.index}]" value="<fmt:formatNumber value='${item.subTotal}' pattern=',##0.00'/>"
                               size="12" class="input-disabled input-decimal" name="items[${idx.index}].subTotal" index="${idx.index}" next="subTotal" disabled/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
            </tfoot>
        </table>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${verification_edit.createdBy.fullName}'/> (<fmt:formatDate value='${verification_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${verification_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${verification_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
        // No Validation Needed
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/invoiceverificationedit.htm'/>",
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
                        <%--window.location="<c:url value='/page/deliveryorderview.htm'/>";--%>
                        // Or Can use This
                        window.location="<c:url value='/page/invoiceverificationpreedit.htm?id='/>"+json.id;
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