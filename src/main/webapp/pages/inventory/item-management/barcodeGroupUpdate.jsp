<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/barcodegroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <c:if test="${access.edit}">
        <a class="item-button-save b_entry" ><span><spring:message code="sirius.save"/></span></a>
        <%--                                <c:if test="${barcode_edit.purchaseOrderFromBarcodes.size() eq 0 && barcode_edit.active}">--%>
        <%--                                    <a class="item-button-new" href="<c:url value='/page/purchaseorderfrombarcodepreadd.htm?id=${barcode_edit.id}'/>"><span><spring:message code="barcode.createpo"/></span></a>--%>
        <%--                                </c:if>--%>
    </c:if>
    <c:if test="${!barcode_edit.active}">
        <a class="item-button-edit" title="Complete"><span>Complete</span></a>
    </c:if>
    <a class="item-button-print" href="<c:url value='/page/barcodegroupprint.htm?id=${barcode_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-print" href="<c:url value='/page/barcodegrouppackinglistprint.htm?id=${barcode_edit.id}'/>"><span>
							<spring:message code="sirius.print"/>&nbsp;<spring:message code="product.packing"/>&nbsp;<spring:message code="sirius.list"/></span></a>
</div>

<div class="main-box">
<sesform:form id="editForm" name="editForm" method="post" modelAttribute="barcode_add">
    <table style="border:none" width="100%">
        <tr>
            <td width="59%">
                <table style="border:none" width="100%">
                    <tr>
                        <td width="27%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
                        <td width="73%">
                            <input value="${barcode_edit.code}" class='input-disabled' size='25'/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="organization"/> :</td>
                        <td>
                            <form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
                                <c:if test='${not empty barcode_edit.organization}'>
                                    <form:option value='${barcode_edit.organization.id}' label='${barcode_edit.organization.fullName}' />
                                </c:if>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                        <td>
                            <input id="date" name="date" value="<fmt:formatDate value='${barcode_edit.date}' pattern='dd-MM-yyyy'/>" class="input-disabled" size="10" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="sirius.facility"/> :</td>
                        <td>
                            <form:select id="facility" path="facility" cssClass="combobox-ext input-disabled">
                                <c:if test='${not empty barcode_edit.facility}'>
                                    <form:option value='${barcode_edit.facility.id}' label='${barcode_edit.facility.name}' />
                                </c:if>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="sirius.type"/> :</td>
                        <td>
                            <select id="barcodeGroupType" name="barcodeGroupType" class="combobox input-disabled">
                                <option value="${barcode_add.barcodeGroupType}" label="${barcode_add.barcodeGroupType.normalizedName}"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
                        <td>
                            <form:textarea path='note' cols='45' rows='5'/>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="41%" valign="top">
                <fieldset>
                    <legend><strong><spring:message code="barcode.info"/></strong></legend>
                    <table width="100%" style="border:none">
                        <tr>
                            <td align="right">Total <spring:message code="barcode.roll"/> : </td>
                            <td><input id="totalRoll" value="<fmt:formatNumber value='${barcode_edit.barcodes.size()}' pattern=',##0'/>" class="number-disabled" disabled size="15"/></td>
                        </tr>
                        <tr>
                            <td align="right">Total <spring:message code="product.quantity"/> : </td>
                            <td><input id="totalQuantity" value="0.00" class="number-disabled" disabled size="15"/></td>
                        </tr>
                    </table>
                </fieldset>
                    <%--                                        <c:if test="${not empty barcode_edit.purchaseOrderFromBarcodes}">--%>
                    <%--                                            </br>--%>
                    <%--                                            <fieldset>--%>
                    <%--                                                <legend><strong><spring:message code="purchaseorder.reference"/></strong></legend>--%>
                    <%--                                                <table width="100%" style="border:none">--%>
                    <%--                                                    <tr>--%>
                    <%--                                                        <th align="right" class="highlight"><spring:message code="purchaseorder"/></th>--%>
                    <%--                                                    </tr>--%>
                    <%--                                                    <c:forEach items="${barcode_edit.purchaseOrderFromBarcodes}" var="pob" varStatus="status">--%>
                    <%--                                                        <tr>--%>
                    <%--                                                            <td align="right"><a href="<c:url value='/page/purchaseorderfrombarcodepreedit.htm?id=${pob.id}'/>"><c:out value="${pob.code}"/></a></td>--%>
                    <%--                                                        </tr>--%>
                    <%--                                                    </c:forEach>--%>
                    <%--                                                </table>--%>
                    <%--                                            </fieldset>--%>
                    <%--                                        </c:if>--%>
            </td>
        </tr>
    </table>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
        <div id="line" dojoType="ContentPane" label="<spring:message code='purchaserequisition.line'/>" class="tab-pages" refreshOnShow="true" selected="true">
            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                <thead>
                <tr>
                    <th width="5%" nowrap="nowrap">&nbsp;</th>
                    <th width="10%" nowrap="nowrap"><spring:message code="sirius.code"/></th>
                    <th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
                    <th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
                    <th width="4%" nowrap="nowrap"><spring:message code="product.uom"/></th>
                    <th width="13%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
                </tr>
                </thead>
                <tbody id="lineItem">
                <c:forEach items="${barcode_edit.barcodes}" var="barcode" varStatus="status">
                    <tr>
                        <td class="tools">
                                <%--                                                    <c:if test='${access.edit && barcode_edit.purchaseOrderFromBarcodes.size() ne 0}'>--%>
                                <%--                                                        <a class="item-button-print" href="<c:url value='/page/barcodeprint.htm?id=${barcode.id}'/>" title="Edit"><span>Print</span></a>--%>
                                <%--                                                    </c:if>--%>
                            <c:if test="${access.delete && !barcode_edit.active}">
                                <a class="item-button-delete" href="javascript:deleteDialog('<c:url value='/page/barcodedelete.htm?id=${barcode.id}'/>');" title="Delete"><span>Delete</span></a>
                            </c:if>

                        </td>
                        <td><c:out value="${barcode.code}"/></td>
                        <td><c:out value="${barcode.product.code}"/></td>
                        <td>
                            <select class="combobox-ext input-disabled" disabled>
                                <option value="${barcode.product.id}">${barcode.product.name}</option>
                            </select>
                        </td>
                        <td><input class="input-disabled" value="${barcode.product.unitOfMeasure.measureId}" disabled/></td>
                        <td>
                            <c:if test="${!barcode_edit.active}">
                                <input class="input-decimal quan" name="items[${status.index}].quantity" value="<fmt:formatNumber value='${barcode.quantity}' pattern=',##0.00'/>" onchange="countQuan()" next="quan"/>
                            </c:if>
                            <c:if test="${barcode_edit.active}">
                                <input class="number-disabled quan" disabled name="items[${status.index}].quantity" value="<fmt:formatNumber value='${barcode.quantity}' pattern=',##0.00'/>" onchange="countQuan()"/>
                            </c:if>
                            <input type="hidden" name="items[${status.index}].reference" value="${barcode.id}"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4" align="right"></td>
                    <td><strong>TOTAL</strong></td>
                    <td><input id="totalQuantity2" class="input-disabled input-number" value="<fmt:formatNumber value='' pattern=',##0.00'/>" disabled/></td>
                </tr>
                </tbody>
                <tfoot>
                <tr class="end-table"><td colspan="13">&nbsp;</td></tr>
                </tfoot>
            </table>
        </div>
    </div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${barcode_edit.createdBy.fullName}'/> (<fmt:formatDate value='${barcode_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${barcode_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${barcode_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    $(function(){
        countQuan();
        var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

        $('.b_entry').click(function(e){
            $status = true;

            if($status) {
                $.ajax({
                    url:"<c:url value='/page/barcodegroupedit.htm'/>",
                    data:$('#editForm').serialize(),
                    type : 'POST',
                    dataType : 'json',
                    beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
                    success : function(json) {
                        if(json)
                        {
                            if(json.status == 'OK')
                            {
                                $dialog.dialog('close');
                                window.location="<c:url value='/page/barcodegrouppreedit.htm?id='/>"+json.id;
                            }
                            else
                                afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
                        }
                    }
                });
            }
        });

        $(".input-number").bind(inputFormat);
        $(".input-decimal").bind(inputFormat);
    });

    function countQuan()
    {
        var total = 0;
        $.each($(".quan"), function(i, obj)
        {
            total += parseFloat(obj.value.replace(/,(?=.*\.\d+)/g, ''));
        });

        document.getElementById('totalQuantity').value = total.numberFormat('#,###.##');
        document.getElementById('totalQuantity2').value = total.numberFormat('#,###.##');
    }

    $('.item-button-edit').click(function(e){
        $status = true;

        if($status) {
            $.ajax({
                url:"<c:url value='/page/barcodegroupedit.htm'/>",
                data:{active: true},
                type : 'POST',
                dataType : 'json',
                beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
                success : function(json) {
                    if(json)
                    {
                        if(json.status == 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/barcodegrouppreedit.htm?id='/>"+json.id;
                        }
                        else
                            afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
                    }
                }
            });
        }
    });
</script>
