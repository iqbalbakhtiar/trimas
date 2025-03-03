<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/invoiceverificationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"  href="<c:url value='/page/invoiceverificationprint.htm?id=${verification_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="editForm" name="editForm" method="post" modelAttribute="verification_form" enctype="multipart/form-data">
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
							<td align="right"><spring:message code="invoiceverification.currency"/></td>
                            <td width="1%" align="center">:</td>
							<td>
								<form:select id='currency' path='currency' disabled='true' cssClass="input-disabled">
									<form:option value='${verification_edit.money.currency.id}' label='${verification_edit.money.currency.symbol}'/>
								</form:select>
								<form:select id='type' path='exchangeType' disabled='true' cssClass="input-disabled">
									<form:option value='SPOT' label='SPOT' onclick='display();'/>
									<form:option value='MIDDLE' label='MIDDLE' onclick='display();'/>
									<form:option value='TAX' label='TAX' onclick='display();'/>
								</form:select>
								<input size="10" class="input-disabled" disabled value="<fmt:formatNumber value='${verification_edit.money.rate}' pattern=',##0.00'/>"/>
							</td>
						</tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.tax"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="tax" path="tax" cssClass="input-disabled" disabled="true">
                                    <c:if test='${not empty verification_edit.tax}'>
                                        <option value="${verification_edit.tax.id}" data-taxrate="${verification_edit.tax.taxRate}">${verification_edit.tax.taxName}</option>
                                    </c:if>
                                </form:select>
                                <input size="5" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${verification_edit.tax.taxRate}' pattern=',##0.00'/>"/>
                                %
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
                                                <c:if test="${verification_edit.status eq 'UNPAID'}"><h2 style="color: red;"><c:out value="${verification_edit.status.formattedName}"/></h2></c:if>
                                                <c:if test="${verification_edit.status eq 'PAID'}"><h2 style="color: green;"><c:out value="${verification_edit.status.formattedName}"/></h2></c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="billing.unpaidamount"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="unpaid" value="<fmt:formatNumber value='${verification_edit.unpaid}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="sirius.reference"/></strong></legend>
                                    <table width="100%" style="border: none">
                                    <c:if test="${not empty verification_edit.goodsReceipts}">
                                    <tr>
                                        <td width="80%" align="right" valign="top"><spring:message code="goodsreceipt"/>:&nbsp;</td>
                                        <td width="20%" align="right">
                                            <c:forEach items="${verification_edit.goodsReceipts}" var="rec" varStatus="status">
                                                <a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${rec.id}'/>"><c:out value="${rec.code}"></c:out></a>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <c:if test="${not empty verification_edit.purchaseOrders}">
                                    <tr>
                                        <td width="80%" align="right" valign="top"><spring:message code="purchaseorder"/>:&nbsp;</td>
                                        <td width="20%" align="right">
                                            <c:forEach items="${verification_edit.purchaseOrders}" var="purchase" varStatus="status">
                                                <a href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${purchase.id}'/>"><c:out value="${purchase.code}"></c:out></a>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <c:if test="${not empty verification_edit.applications}">
                                    <tr>
                                        <td width="80%" align="right" valign="top"><spring:message code="receipt"/>:&nbsp;</td>
                                        <td width="20%" align="right">
                                            <c:forEach items="${verification_edit.applications}" var="app" varStatus="status">
                                                <a href="<c:url value='/page/receiptpreedit.htm?id=${app.payment.id}'/>"><c:out value="${app.payment.code}"></c:out></a>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <c:if test="${not empty verification_edit.debitMemoManuals}">
                                    <tr>
                                        <td width="80%" align="right" valign="top"><spring:message code="invoiceverification.memo.manual"/>:&nbsp;</td>
                                        <td width="20%" align="right">
                                        	<c:forEach items="${verification_edit.debitMemoManuals}" var="memo" varStatus="status">
                                                <a href="<c:url value='/page/debitmemomanualpreedit.htm?id=${memo.id}'/>"><c:out value="${memo.code}"></c:out></a>
                                                <br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    </c:if>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="invoiceverification.transaction"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="purchaseorder.purchase"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="purchase" value="<fmt:formatNumber value='${adapter.purchase}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="sirius.tax"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="taxAmount" value="<fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><strong><spring:message code="sirius.total"/></strong>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="unpaid" value="<fmt:formatNumber value='${adapter.total}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20" disabled/>
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
                <th width="8%" nowrap="nowrap"><spring:message code="sirius.price"/></th>
                <th nowrap="nowrap"><spring:message code="sirius.amount"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${verification_edit.items}" var="item" varStatus="idx">
                <tr>
                    <td></td>
                    <td><input value="${item.product.name}" size="30" class="input-disabled" disabled/></td>
                    <td><input value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" size="8" class="number-disabled" disabled/></td>
                    <td><input value="${item.product.unitOfMeasure.measureId}" class="input-disabled" size="5" disabled/></td>
                    <td><input value="<fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/>" size="12" class="number-disabled" disabled/></td>
                    <td><input value="<fmt:formatNumber value='${(item.money.amount-item.discount)*item.quantity}' pattern=',##0.00'/>" size="15" class="number-disabled" disabled/></td>
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
                save();
        });
    });

    function save() {
        $.ajax({
            url:"<c:url value='/page/invoiceverificationedit.htm'/>",
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