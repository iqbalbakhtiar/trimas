<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/billingbatchview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
    <a class="item-button-print"  href="<c:url value='/page/billingbatchprint.htm?id=${billing_batch_form.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="editForm" name="editForm" method="post" modelAttribute="billing_batch_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="23%" align="right"><spring:message code="sirius.id"/></td>
                <td width="1%" align="center">:</td>
                <td width="77%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
                <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.date"/></td>
                <td width="1%" align="center">:</td>
                <td><input id="date" name="date" size="10" class="inputbox-date input-disabled" readonly value="<fmt:formatDate value='${billing_batch_form.date}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
                <td align="right"><spring:message code="customer"/></td>
                <td width="1%" align="center">:</td>
                <td>
                    <form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" disabled="true">
                        <option value="${billing_batch_form.customer.id}"><c:out value="${billing_batch_form.customer.fullName}"/></option>
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
        <br/>
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='billing.line.item'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                        <thead>
                        <tr>
                            <th width="1%" nowrap="nowrap"></th>
                            <th width="15%" nowrap="nowrap"><spring:message code="sirius.id"/> <spring:message code="billing"/></th>
                            <th width="10%" nowrap="nowrap"><spring:message code="sirius.date"/></th>
                            <th width="20%" nowrap="nowrap"><spring:message code="customer"/></th>
                            <th width="55%" nowrap="nowrap"><spring:message code="sirius.amount"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        <c:forEach items="${billing_batch_form.billingBatch.items}" var="item" varStatus="idx">
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <strong>
                                        <a href="<c:url value='/page/${item.billing.billingType.url}?id=${item.billing.id}'/>"><c:out value='${item.billing.code}'/></a>
                                    </strong>
                                </td>
                                <td>
                                    <fmt:formatDate value='${item.billing.date}' pattern='dd-MM-yyyy'/>
                                </td>
                                <td>
                                        ${item.billing.customer.fullName}
                                </td>
                                <td>
                                    <fmt:formatNumber value='${item.amount}' pattern=',##0.00'/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td></td>
                            <td><strong><spring:message code="sirius.total"/></strong></td>
                            <td colspan="2"></td>
                            <td>
                                <strong><fmt:formatNumber value='${billing_batch_form.amount}' pattern=',##0.00'/></strong>
                            </td>
                        </tr>
                        <tr class="end-table">
                            <td colspan="5">&nbsp;</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${billing_batch_form.createdBy.fullName}'/> (<fmt:formatDate value='${billing_batch_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${billing_batch_form.updatedBy.fullName}'/> (<fmt:formatDate value='${billing_batch_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
        var date = $('#date').val();
        if (date == null || date === "") {
            alert('<spring:message code="sirius.date"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/billingbatchedit.htm'/>",
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
                        <%--window.location="<c:url value='/page/billingbatchview.htm'/>";--%>
                        // Or Can use This
                        window.location="<c:url value='/page/billingbatchpreedit.htm?id='/>"+json.id;
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