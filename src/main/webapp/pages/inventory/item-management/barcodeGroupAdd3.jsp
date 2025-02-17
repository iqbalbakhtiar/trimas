<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/barcodegrouppreadd1.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-save b_entry" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="barcode_add">
        <table style="border:none" width="100%">
            <tr>
                <td width="59%">
                    <table width="100%" style="border:none">
                        <tr>
                            <td width="27%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
                            <td width="73%">
                                <input value="Auto Number" class='input-disabled' size='25'/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/> :</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
                                    <c:if test='${not empty barcode_add.organization}'>
                                        <form:option value='${barcode_add.organization.id}' label='${barcode_add.organization.fullName}' />
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                            <td>
                                <input id="date" name="date" value="<fmt:formatDate value='${barcode_add.date}' pattern='dd-MM-yyyy'/>" class="input-disabled" size="10" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.facility"/> :</td>
                            <td>
                                <form:select id="facility" path="facility" cssClass="combobox-ext input-disabled">
                                    <c:if test='${not empty barcode_add.facility}'>
                                        <form:option value='${barcode_add.facility.id}' label='${barcode_add.facility.name}' />
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
                            <td><form:textarea path='note' cols='45' rows='5'/></td>
                        </tr>
                    </table>
                </td>
                <td width="41%" valign="top">
                    <fieldset>
                        <legend><strong><spring:message code="barcode.info"/></strong></legend>
                        <table width="100%" style="border:none">
                            <tr>
                                <td align="right">Total <spring:message code="barcode.roll"/> : </td>
                                <td><input id="totalRoll" value="<fmt:formatNumber value='${barcodes.size()}' pattern=',##0'/>" class="number-disabled" disabled size="15"/></td>
                            </tr>
                            <tr>
                                <td align="right">Total <spring:message code="product.quantity"/> : </td>
                                <td><input id="totalQuantity" value="0.00" class="number-disabled" disabled size="15"/></td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
        </table>
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="line" dojoType="ContentPane" label="<spring:message code='purchaserequisition.line'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                        <thead>
                        <tr>
                            <th width="1%" nowrap="nowrap"><spring:message code="sirius.no"/></th>
                            <th width="15%" nowrap="nowrap"><spring:message code="product.code"/></th>
                            <th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
                            <th width="4%" nowrap="nowrap"><spring:message code="product.uom"/></th>
                            <th width="13%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        <c:forEach items="${barcodes}" var="barcode" varStatus="status">
                            <tr>
                                <td><input value="${status.index + 1}" class="input-disabled" size="5" disabled/></td>
                                <td><input value="${barcode.product.code}" class="input-disabled" size="15" disabled/></td>
                                <td>
                                    <select name="items[${status.index}].product" class="combobox-ext products" index="${status.index}">
                                        <option value="${barcode.product.id}"><c:out value="${barcode.product.name}"/></option>
                                    </select>
                                </td>
                                <td><input value="${barcode.product.unitOfMeasure.measureId}" class="input-disabled" size="5" disabled/></td>
                                <td><input id="quantity[${status.index}]" value="0.00" name="items[${status.index}].quantity" index="${status.index}" class="input-decimal quan" size="10" onchange="countQuan()" next="quan"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="13">&nbsp;</td></tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

    $('.b_entry').click(function(e){
        $status = true;

        $.each($(".products"), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var quantity = parseFloat($("#quantity\\["+idx+"\\]").val().toNumber());

            if(quantity == '' || quantity == '0')
            {
                alert('<spring:message code="product.quantity"/> <spring:message code="notif.empty"/> !!!');
                return $status = false;
            }
        });

        if($status) {
            $.ajax({
                url:"<c:url value='/page/barcodegroupadd.htm'/>",
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
                        if(json.status == 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/barcodegrouppreedit.htm?id='/>"+json.id;
                        }
                        else
                        {
                            $dialog.empty();
                            $dialog.html('<spring:message code="notif.profailed"/> : <br/>'+json.message);
                        }
                    }
                }
            });
        }

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
    }
</script>
