<%--This Page Copied From Sales Order Update--%>
<%--suppress ALL --%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/goodsissueview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="transaction_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%" valign="top">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.id"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
                            <td width="1%"><form:errors path="code"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty transaction_form.organization}'>
                                        <form:option value='${transaction_form.organization.id}' label='${transaction_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="facility"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="facility" path="facility" cssClass="combobox-ext input-disabled" disabled="true">
                                    <c:if test='${not empty transaction_form.facility}'>
                                        <form:option value="${transaction_form.facility.id}">${transaction_form.facility.code} ${transaction_form.facility.name}</form:option>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="goodsissue.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${transaction_form.date}' pattern='dd-MM-yyyy'/>"/></td>
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
                                    <legend><strong><spring:message code="sirius.reference"/></strong></legend>
                                    <table width="100%" style="border:none">
                                        <tr>
                                            <th align="right" class="highlight"><spring:message code="goodsissue.reftype"/></th>
                                        </tr>
                                        <tr>
                                            <td align="right">
                                                <spring:message code='warehousetransactionsource.${ref.transactionItem.transactionSource.message}'/>
                                            </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <th width="50%" align="right" class="highlight"><spring:message code="goodsissue.refdoc"/></th>
                                        </tr>
                                        <c:forEach items="${goodsIssue_edit.reference}" var="message" varStatus="status">
                                            <tr>
                                                <td align="right">
                                                        ${message}
                                                </td>
                                            </tr>
                                        </c:forEach>
<%--                                        <c:if test="${not empty goodsIssue_edit.journalEntry}">--%>
<%--                                            <tr><td colspan="2">&nbsp;</td></tr>--%>
<%--                                            <tr>--%>
<%--                                                <th width="50%" align="right" class="highlight"><spring:message code="goodsissue.journal"/></th>--%>
<%--                                            </tr>--%>
<%--                                            <tr>--%>
<%--                                                <td align="right">--%>
<%--                                                    <a href="<c:url value='/page/journalentrypreview.htm?id=${goodsIssue_edit.journalEntry.id}'/>">--%>
<%--                                                        <c:out value='${goodsIssue_edit.journalEntry.code}'/>--%>
<%--                                                    </a>--%>
<%--                                                </td>--%>
<%--                                            </tr>--%>
<%--                                        </c:if>--%>
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
            <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                    <thead>
                    <tr>
                        <th width="2%">&nbsp;</th>
                        <th width="10%"><spring:message code="goodsissueitem.code"/></th>
                        <th width="14%"><spring:message code="goodsissueitem.name"/></th>
                        <th width="12%"><spring:message code="goodsissueitem.category"/></th>
                        <th width="5%"><spring:message code="goodsissueitem.ref.qty"/></th>
                        <th width="5%"><spring:message code="sirius.type"/></th>
                        <th width="5%"><spring:message code="goodsissueitem.issue"/></th>
                        <th width="8%"><spring:message code="accreport.cogs"/></th>
                        <th width="8%"><spring:message code="goodsissueitem.uom"/></th>
                        <th width="10%"><spring:message code="goodsissueitem.grid"/></th>
                        <th width="10%"><spring:message code="goodsissueitem.container"/></th>
                    </tr>
                    </thead>
                    <tbody id="lineItem">
                    <c:forEach items='${stockControlls}' var='item' varStatus='status'>
                        <tr>
                            <td>&nbsp;</td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.product.code}'/></td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.product.name}'/></td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.productCategory.name}'/></td>
                            <td nowrap="nowrap">
                                <fmt:formatNumber value='${item.stockable.warehouseTransactionItem.quantity}' pattern='##0.####'/>
                            </td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.warehouseTransactionItem.tag.inventoryType}'/></td>
                            <td nowrap="nowrap"><fmt:formatNumber value='${item.quantity}' pattern='##0.##'/></td>
                            <td nowrap="nowrap"><fmt:formatNumber value='${item.price}' pattern=',##0'/></td>
                            <td nowrap="nowrap">
                                <c:out value='${item.stockable.warehouseTransactionItem.referenceItem.measureName}'/>
                            </td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.warehouseTransactionItem.sourceGrid.name}'/></td>
                            <td nowrap="nowrap"><c:out value='${item.stockable.sourceContainer.name}'/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr class="end-table"><td colspan="11">&nbsp;</td></tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${transaction_form.createdBy.fullName}'/> (<fmt:formatDate value='${transaction_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${transaction_form.updatedBy.fullName}'/> (<fmt:formatDate value='${transaction_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        $('.item-button-save').click(function(){
            save();
        });
    });

    function save() {
        $.ajax({
            url:"<c:url value='/page/goodsissueedit.htm'/>",
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
                        window.location="<c:url value='/page/goodsissuepreedit.htm?id='/>"+json.id;
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