<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/barcodegroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <c:if test="${access.edit}">
        <a class="item-button-save b_entry" ><span><spring:message code="sirius.save"/></span></a>
    </c:if>
    <%-- <c:if test="${!barcode_edit.active}">
        <a class="item-button-edit" title="Complete"><span>Complete</span></a>
    </c:if> --%>
    <a class="item-button-print" href="<c:url value='/page/barcodegroupprint.htm?id=${barcode_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-print" href="<c:url value='/page/barcodegrouppackinglistprint.htm?id=${barcode_edit.id}'/>"><span>
	<spring:message code="sirius.print"/>&nbsp;<spring:message code="product.packing"/>&nbsp;<spring:message code="sirius.list"/></span></a>
</div>

<div class="main-box">
<sesform:form id="editForm" name="editForm" method="post" modelAttribute="barcode_form">
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
                                <option value="${barcode_form.barcodeGroupType}" label="${barcode_form.barcodeGroupType.normalizedName}"></option>
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
                            <td align="right"><spring:message code="barcode.quantity"/> : </td>
                            <td><input id="totalRoll" value="<fmt:formatNumber value='${barcode_edit.barcodes.size()}' pattern=',##0'/>" class="number-disabled" disabled size="5"/></td>
                        </tr>
                        <tr style="font-weight: bold;">
                        	<td width="30%;">&nbsp;</td>
                        	<td width="10%;"><spring:message code="sirius.total"/></td>
                        	<td width="60%;"><spring:message code="sirius.total.real"/></td>
                        </tr>
                        <c:forEach items="${products}" var="prod" varStatus="status">
                            <c:set var="totalQuantity" value="${0}"/>
                            <c:set var="totalQuantityReal" value="${0}"/>
                            <c:forEach items="${prod.value}" var="det">
	                            <c:set var="totalQuantity" value="${totalQuantity+det.quantity}"/>
	                            <c:set var="totalQuantityReal" value="${totalQuantityReal+det.quantityReal}"/>
                            </c:forEach>
                            <tr>
	                            <td align="right">${prod.key.name} : </td>
	                            <td><input id="totalQuantity" value="<fmt:formatNumber value='${totalQuantity}' pattern=',##0.00'/> ${prod.key.unitOfMeasure.measureId}" class="number-disabled" disabled size="15"/></td>
	                            <td><input id="totalQuantityReal" value="<fmt:formatNumber value='${totalQuantityReal}' pattern=',##0.00'/> ${prod.key.unitOfMeasure.measureId}" class="number-disabled" disabled size="15"/></td>
	                        </tr>
                        </c:forEach>
                    </table>
                </fieldset>
            </td>
        </tr>
    </table>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
        <div id="line" dojoType="ContentPane" label="<spring:message code='purchaserequisition.line'/>" class="tab-pages" refreshOnShow="true" selected="true">
            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                <thead>
                <tr>
                    <th width="1%" nowrap="nowrap"><div style="width: 30px;">&nbsp;</div></th>
                    <th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
                    <th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
                    <th width="4%" nowrap="nowrap"><spring:message code="product.uom"/></th>
                    <th width="8%" nowrap="nowrap"><spring:message code="barcode"/></th>
                    <th width="5%" nowrap="nowrap"><spring:message code="barcode.quantity.base"/></th>
                    <th width="55%" nowrap="nowrap"><spring:message code="barcode.quantity.real"/></th>
                </tr>
                </thead>
                <tbody id="lineItem">
                <c:set var="prd" value=""/>
                <c:forEach items="${barcode_edit.barcodes}" var="barcode" varStatus="status">
                    <c:if test="${not empty prd and barcode.product.id ne prd}">
                    <tr>
                    	<td colspan="7">&nbsp;</td>
                    </tr>
                    </c:if>
                    <tr>
                        <td class="tools">
                            <%-- <c:if test="${access.delete && !barcode_edit.active}">
                                <a class="item-button-delete" href="javascript:deleteDialog('<c:url value='/page/barcodedelete.htm?id=${barcode.id}'/>');" title="Delete"><span>Delete</span></a>
                            </c:if> --%>
                        </td>
                        <td><c:out value="${barcode.product.code}"/></td>
                        <td>
                            <select class="combobox-ext input-disabled" disabled>
                                <option value="${barcode.product.id}">${barcode.product.name}</option>
                            </select>
                        </td>
                        <td><input class="input-disabled" value="${barcode.product.unitOfMeasure.measureId}" disabled size="5"/></td>
                        <td><input class="input-disabled" value="${barcode.code}" disabled size="12"/></td>
                        <td><input class="number-disabled quan" disabled value="<fmt:formatNumber value='${barcode.quantity}' pattern=',##0.00'/>" size="10"/></td>
                        <td>
                        	<input class="number-disabled quanReal" disabled value="<fmt:formatNumber value='${barcode.quantityReal}' pattern=',##0.00'/>" size="10"/>
                            <input type="hidden" name="items[${status.index}].reference" value="${barcode.id}"/>
                        </td>
                    </tr>
                    <c:set var="prd" value="${barcode.product.id}"/>
                </c:forEach>
                <%-- <tr>
                    <td colspan="4" align="right"></td>
                    <td><strong>TOTAL</strong></td>
                    <td><input id="totalQuantity2" class="input-disabled input-number" value="<fmt:formatNumber value='' pattern=',##0.00'/>" disabled size="10"/></td>
                    <td><input id="totalQuantity3" class="input-disabled input-number" value="<fmt:formatNumber value='' pattern=',##0.00'/>" disabled size="10"/></td>
                </tr> --%>
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
        //countQuan();
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
        var totalReal = 0;
        
        $.each($(".quan"), function(i, obj)
        {
            total += parseFloat(obj.value.replace(/,(?=.*\.\d+)/g, ''));
        });
        
        $.each($(".quanReal"), function(i, obj)
        {
        	totalReal += parseFloat(obj.value.replace(/,(?=.*\.\d+)/g, ''));
        });

        document.getElementById('totalQuantity').value = total.numberFormat('#,###.##');
        document.getElementById('totalQuantity2').value = total.numberFormat('#,###.##');
        document.getElementById('totalQuantity3').value = totalReal.numberFormat('#,###.##');
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
