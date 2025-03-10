<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="
    <c:url value='/page/barcodegrouppreadd1.htm'>
        <c:if test="${not empty referenceId and not empty barcodeType}">
            <c:param name='referenceId' value='${referenceId != null ? referenceId : ""}'/>
            <c:param name='barcodeType' value='${barcodeType != null ? barcodeType : ""}'/>
        </c:if>
    </c:url>"><span><spring:message code="sirius.back"/></span>
    </a>
    <a class="item-button-save b_entry" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="barcode_form">
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
                                    <c:if test='${not empty barcode_form.organization}'>
                                        <form:option value='${barcode_form.organization.id}' label='${barcode_form.organization.fullName}' />
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                            <td>
                                <input id="date" name="date" value="<fmt:formatDate value='${barcode_form.date}' pattern='dd-MM-yyyy'/>" class="input-disabled" size="10" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.facility"/> :</td>
                            <td>
                                <form:select id="facility" path="facility" cssClass="combobox-ext input-disabled">
                                    <c:if test='${not empty barcode_form.facility}'>
                                        <form:option value='${barcode_form.facility.id}' label='${barcode_form.facility.name}' />
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
			            <c:if test="${barcode_form.barcodeGroupType eq 'PURCHASE_ORDER'}">
			            <tr>
			                <td align="right"><spring:message code="sirius.reference"/> :</td>
			                <td>
			                    <select id="purchaseOrder" name="purchaseOrder" class="combobox input-disabled">
			                        <option value="${barcode_form.purchaseOrder.id}" label="${barcode_form.purchaseOrder.code}"></option>
			                    </select>
			                </td>
			            </tr>
			            </c:if>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
                            <td><form:textarea path='note' cols='45' rows='5'/></td>
                        </tr>
                    </table>
                </td>
                <td width="40%" valign="top">
                    <fieldset>
                        <legend><strong><spring:message code="barcode.info"/></strong></legend>
                        <table width="100%" style="border:none">
	                        <tr style="font-weight: bold;">
	                        	<td width="30%;">&nbsp;</td>
	                        	<td width="10%;"><spring:message code="sirius.total"/></td>
	                        	<td width="60%;"><spring:message code="sirius.total.real"/></td>
	                        </tr>
                            <c:forEach items="${itemReferences}" var="item" varStatus="status">
                            <tr>
                                <td align="right" nowrap="nowrap"><div id="productRef${item.reference}">${item.product.name}</div></td>
                                <td>: <input id="gTotalRef${item.reference}" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="number-disabled references" disabled size="15" reference="${item.reference}"/></td>
                                <td><input id="gTotalReal${item.reference}" value="0.00" class="number-disabled" disabled size="15" reference="${item.reference}"/></td>
                            </tr>
                            </c:forEach>
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
                            <th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
                            <th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
                            <th width="4%" nowrap="nowrap"><spring:message code="product.uom"/></th>
                            <th width="5%" nowrap="nowrap"><spring:message code="barcode.quantity.base"/></th>
                            <th width="55%" nowrap="nowrap"><spring:message code="barcode.quantity.real"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        <c:set var="number" value="1"/>
                        <c:forEach items="${barcodes}" var="barcode" varStatus="status">
                        <tr>
                        <c:if test="${not empty barcode.product}">
                            <td><input value="${number}" class="input-disabled" size="3" disabled/></td>
                            <td><input value="${barcode.product.code}" class="input-disabled" size="20" disabled/></td>
                            <td>
                                <select name="items[${status.index}].product" class="combobox-ext input-disabled products" index="${status.index}">
                                    <option value="${barcode.product.id}"><c:out value="${barcode.product.name}"/></option>
                                </select>
                            </td>
                            <td><input value="${barcode.product.unitOfMeasure.measureId}" class="input-disabled" size="5" disabled/></td>
                            <td><input id="quantity[${status.index}]" value="0.00" name="items[${status.index}].quantity" index="${status.index}" class="input-decimal quantities ref${barcode.referenceId}" size="10" onchange="countQuantities(${status.index})" next="quan" group="${barcode.referenceId}"/></td>
                            <td>
                            	<input id="quantityReal[${status.index}]" value="0.00" name="items[${status.index}].quantityReal" index="${status.index}" class="input-decimal realQuantities refReal${barcode.referenceId}" size="10" onchange="countRealQuantities(${status.index})" next="realQuan" group="${barcode.referenceId}"/>
                            	<c:if test="${not empty barcode.referenceId}">
                            		<input style="display: none;" value="${barcode.referenceId}" name="items[${status.index}].reference" index="${status.index}" readonly="true"/>
                            	</c:if>
                            </td>
                        	<c:set var="number" value="${number+1}"/>
                        </c:if>
                        <c:if test="${empty barcode.product}">
                        	<td colspan="4"></td>
                        	<td><input id="total${barcode.referenceId}" value="0.00" class="number-disabled totals" size="10" disabled="true" group="${barcode.referenceId}"/></td>
                        	<td><input id="totalReal${barcode.referenceId}" value="0.00" class="number-disabled" size="10" disabled="true" group="${barcode.referenceId}"/></td>
                        </c:if>
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

        $.each($(".quantities"), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var quantity = parseFloat($("#quantity\\["+idx+"\\]").val().toNumber());

            if(quantity == '' || quantity == '0')
            {
                alert('<spring:message code="barcode.quantity.base"/> <spring:message code="notif.empty"/> !!!');
                return $status = false;
            }
        });

        $.each($(".realQuantities"), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var quantity = parseFloat($("#quantityReal\\["+idx+"\\]").val().toNumber());

            if(quantity == '' || quantity == '0')
            {
                alert('<spring:message code="barcode.quantity.real"/> <spring:message code="notif.empty"/> !!!');
                return $status = false;
            }
        });
        
        $.each($(".totals"), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var group = obj.getAttribute('group');
            var total = parseFloat(obj.value.toNumber());
            var totalRef = parseFloat($("#gTotalRef"+group).val().toNumber());
            var product = $("#productRef"+group).text();

            if(total != totalRef)
            {
                alert('<spring:message code="sirius.total.quantity"/> <spring:message code="notif.different"/> <spring:message code="sirius.total"/> <spring:message code="sirius.reference"/> ('+product+') !!!');
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
    });

    function countQuantities(index)
    {
        var qty = $("#quantity\\["+index+"\\]");
        var total = 0;
        
        $.each($(".ref"+qty.attr('group')), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var quantity = parseFloat($("#quantity\\["+idx+"\\]").val().toNumber());
            
            total = total + quantity;
        });

        document.getElementById('total'+qty.attr('group')).value = total.numberFormat('#,###.##');
    }

    function countRealQuantities(index)
    {
        var qty = $("#quantityReal\\["+index+"\\]");
        var total = 0;
        
        $.each($(".refReal"+qty.attr('group')), function(i, obj)
        {
            var idx = obj.getAttribute('index');
            var quantity = parseFloat($("#quantityReal\\["+idx+"\\]").val().toNumber());
            
            total = total + quantity;
        });

        document.getElementById('gTotalReal'+qty.attr('group')).value = total.numberFormat('#,###.##');
        document.getElementById('totalReal'+qty.attr('group')).value = total.numberFormat('#,###.##');
    }
</script>
