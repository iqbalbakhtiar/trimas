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
    <a class="item-button-next" ><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="barcode_form">
        <table style="border:none" width="100%">
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
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="line" dojoType="ContentPane" label="<spring:message code='purchaserequisition.line'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <div class="toolbar-clean">
                    <c:if test="${empty items}">
                        <a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
                        <a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
                    </c:if>
                    <div class="item-navigator">&nbsp;</div>
                    </div>
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
                        <thead>
                        <tr>
                            <th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
                            <%-- <th width="12%" nowrap="nowrap"><spring:message code="product.code"/></th> --%>
                            <th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
                            <c:if test="${not empty items}">
                            	<th width="5%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
                            </c:if>
                            <th width="4%" nowrap="nowrap"><spring:message code="product.uom"/></th>
                            <th width="55%" nowrap="nowrap"><spring:message code="barcode.quantity"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        <c:forEach items="${items}" var="item" varStatus="stat">
                        <tr>
                        	<td>&nbsp;</td>
                        	<%-- <td><input value="${item.product.code}" type="text" disabled class="input-disabled" size="30"/></td> --%>
                        	<td>
                        		<select class="combobox-ext input-disabled" name="items[${stat.index}].product" readonly="true">
                        			<option value="${item.product.id}">${item.product.name}</option>
                        		</select>
                        	</td>
                            <c:if test="${not empty items}">
                               	<td><input value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>"" name="items[${stat.index}].quantity" type="text" readonly="true" class="number-disabled" size="10"/></td>
                            </c:if>
                        	<td><input value="${item.product.unitOfMeasure.measureId}" type="text" disabled class="input-disabled" size="5"/></td>
                        	<td>
                        		<input value="0" name="items[${stat.index}].roll" type="text" class="input-number quan" size="7"/>
                        		<input style="display: none;" value="${item.reference}" name="items[${stat.index}].reference" type="text" readonly="true" size="7"/>
                        	</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    $(function(){
        var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

        $('.item-button-next').click(function()
        {
            $status = true;

            if($(".quan").length < 1)
            {
                alert('<spring:message code="notif.line"/> !');
                return;
            }

            $.each($(".products"), function(i, obj)
            {
                var idx = obj.getAttribute('index');

                if(obj.value == '')
                {
                    alert('<spring:message code="product"/> <spring:message code="notif.empty"/> !!!');
                    return $status = false;
                }

                var roll = parseFloat($("#roll\\["+idx+"\\]").val().toNumber());

                if(roll == '' || roll == '0')
                {
                    alert('<spring:message code="product.quantity"/> <spring:message code="notif.empty"/> !!!');
                    return $status = false;
                }
            });

            if ($status)
            {
                var url = "<c:url value='/page/barcodegrouppreadd3.htm'/>";
                // Jika referenceId dan barcodeType tidak kosong, tambahkan sebagai query string
                <c:if test="${not empty referenceId and not empty barcodeType}">
                url += "?referenceId=${referenceId}&barcodeType=${barcodeType}";
                </c:if>

                document.addForm.action = url;
                document.addForm.submit();
            }
        });

        var $index = 0;

        $('.item-button-new').click(function()
        {
            $tbody = $('#lineItem');

            $tr = $('<tr/>');

            $cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);

            //$productCode = List.get('<input type="text" disabled class="input-disabled" size="30"/>','productCode['+$index+']');
            $product = List.get('<select class="combobox-ext products"/>','product['+$index+']');
            $img = List.img('<spring:message code="product"/>', $index, 'openproduct("product['+$index+']","'+$index+'")');
            $uom = List.get('<input type="text" disabled class="input-disabled" size="5"/>','uom['+$index+']');

            $roll = List.get('<input type="text" class="input-number input-dynamic quan" size="7"/>','roll['+$index+']', '0');
            $roll.change(function(){
                setInputDynamic();
            });

            $tr.append(List.col([$cbox]));
            //$tr.append(List.col([$productCode]));
            $tr.append(List.col([$product, $img]));
            $tr.append(List.col([$uom]));
            $tr.append(List.col([$roll]));

            $tbody.append($tr);

            $index++;

            setInputDynamic();
        });
    });

    $('.checkall').click(function () {
        $('.check').prop("checked", this.checked);
    });

    $('.item-button-delete').click(function () {
        $('.check').each(function(){
            if(this.checked){
                this.checked = false;
                $(this).parent().parent().remove();
            }
        });
        $('.checkall').prop("checked", false);
    });

    function openproduct(target, index)
    {
        openpopup("<c:url value='/page/popupproductview.htm?target='/>"+target+"&index="+index+"&grouping=false&serial=true");
    }
</script>

