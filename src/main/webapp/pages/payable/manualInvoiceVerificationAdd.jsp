<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/manualinvoiceverificationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="verification_form" name="verification_form" method="post" modelAttribute="verification_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%" valign="top">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="code" value="Auto Generated" disabled class='input-disabled'/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext">
                                    <c:if test='${not empty verification_form.organization}'>
                                        <form:option value='${verification_form.organization.id}' label='${verification_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="invoiceverification.billfrom"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="supplier" path="supplier" cssClass="combobox-ext">
                                </form:select>
                                <a class="item-popup" onclick="opensupplier();" title="Supplier" />
                            </td>
                        </tr>
<%--						<tr>--%>
<%--							<td align="right"><spring:message code="invoiceverification.currency"/></td>--%>
<%--                            <td width="1%" align="center">:</td>--%>
<%--							<td>--%>
<%--								<form:select id='currency' path='currency' disabled='true' cssClass="input-disabled">--%>
<%--									<form:option value='${verification_edit.money.currency.id}' label='${verification_edit.money.currency.symbol}'/>--%>
<%--								</form:select>--%>
<%--								<form:select id='type' path='exchangeType' disabled='true' cssClass="input-disabled">--%>
<%--									<form:option value='SPOT' label='SPOT' onclick='display();'/>--%>
<%--									<form:option value='MIDDLE' label='MIDDLE' onclick='display();'/>--%>
<%--									<form:option value='TAX' label='TAX' onclick='display();'/>--%>
<%--								</form:select>--%>
<%--								<input size="10" class="input-disabled" disabled value="<fmt:formatNumber value='${verification_edit.money.rate}' pattern=',##0.00'/>"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
                        <tr>
                            <td align="right"><spring:message code="sirius.tax"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="tax" path="tax" onchange="updateDisplay();">
                                    <c:forEach var="tax" items="${taxes}">
                                        <option value="${tax.id}" data-taxrate="${tax.taxRate}">${tax.taxName}</option>
                                    </c:forEach>
                                </form:select>
                                <input size="7" id="taxRate" class="input-number input-disabled" disabled />&nbsp;%
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
                                    <legend><strong><spring:message code="invoiceverification.transaction"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="purchaseorder.purchase"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="totalLineAmount" value="0.00" class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="sirius.tax"/>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="taxAmount" value="0.00" class="number-disabled" readonly="readonly" size="20" disabled/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><strong><spring:message code="sirius.total"/></strong>:&nbsp;</td>
                                            <td width="20%">
                                                <input id="totalAfterTax" name="amount" value="0.00" class="number-disabled" size="20" readonly/>
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
        <div class="toolbar-clean">
            <a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
            <a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
            <div class="item-navigator">&nbsp;</div>
        </div>
        <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
                <th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
                <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
                <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
                <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
                <th width="8%" nowrap="nowrap"><spring:message code="sirius.amount"/></th>
                <th width="8%" nowrap="nowrap"><spring:message code="sirius.discount"/></th>
                <th nowrap="nowrap"><spring:message code="sirius.total"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
            </tfoot>
        </table>
    </sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        $('.item-button-save').click(function(){
            save();
        });

        $('.item-button-new').click(function() {
            addLine();
        });

        $('.checkall').click(function () {
            $('.check').prop("checked", this.checked);
        });

        $('.item-button-delete').click(function () {
            $('.check').each(function(){
                if(this.checked){
                    this.checked = false;
                    $(this).parent().parent().remove();
                    updateDisplay();
                }
            });
            $('.checkall').prop("checked", false);
        });

        // Trigger when page loaded
        updateDisplay();
    });

    function save() {
        $.ajax({
            url:"<c:url value='/page/manualinvoiceverificationadd.htm'/>",
            data:$('#verification_form').serialize(),
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
                        window.location="<c:url value='/page/manualinvoiceverificationpreedit.htm?id='/>"+json.id;
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

    function opensupplier()
    {
        var org = document.getElementById('org');
        if(org.value == '')
        {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&active=true&organization='/>"+org.value);
    }

    function updateDisplay() {
        // 1. Update Tax Rate display
        var taxRate = Number.parse($('#tax option:selected').data('taxrate')) || 0;
        $('#taxRate').val(taxRate ? taxRate.toFixed(2) : '');

        // 2. Hitung total per line item, dan akumulasi ke totalLineAmount
        var totalLineAmount = 0;
        $('#lineItem tr').each(function(){
            var $row = $(this);

            // Ambil nilai numerik (diasumsikan ada helper toNumber())
            var qty      = $row.find('input[id^="quantity["]').val().toNumber()    || 0;
            var amount   = $row.find('input[id^="amount["]').val().toNumber()      || 0;
            var discount = $row.find('input[id^="discount["]').val().toNumber()    || 0;

            // Hitung line total
            var lineTotal = qty * amount - discount;
            totalLineAmount += lineTotal;

            // Tampilkan dengan formatting #,##0.00
            $row.find('input[id^="totalAmount["]').val(
                lineTotal.numberFormat('#,##0.00')
            );
        });

        // 3. Set totalLineAmount
        $('#totalLineAmount').val(
            totalLineAmount.numberFormat('#,##0.00')
        );

        // 4. Hitung dan set taxAmount
        var taxAmount = totalLineAmount * (taxRate / 100);
        $('#taxAmount').val(
            taxAmount.numberFormat('#,##0.00')
        );

        // 5. Hitung dan set totalAfterTax
        var totalAfterTax = totalLineAmount + taxAmount;
        $('#totalAfterTax').val(
            totalAfterTax.numberFormat('#,##0.00')
        );
    }

    var index = 0;
    function addLine() {
        $tbody = $('#lineItem');
        $tr = $('<tr/>');

        $cbox = List.get('<input type="checkbox" class="check"/>','check'+index);

        $product = List.get('<select class="combobox productInput" onchange="checkDuplicate(this);updateDisplay();"/>','product['+index+']');
        $productImg = List.img('<spring:message code="product"/>', index, 'openProduct("'+index+'")');

        $qty = List.get('<input type="text" class="input-decimal" size="6" onchange="updateDisplay();"/>','quantity['+index+']', '0.00');

        $uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+index+']');

        $amount = List.get('<input type="text" class="input-decimal inputbox-medium" onchange="updateDisplay()"/>','amount['+index+']', '0.00');

        $discount = List.get('<input type="text" class="input-decimal inputbox-medium" onchange="updateDisplay()"/>','discount['+index+']', '0.00');

        $totalAmount = List.get('<input type="text" class="input-decimal input-disabled inputbox-medium" disabled/>','totalAmount['+index+']', '0.00');

        $tr.append(List.col([$cbox]));
        $tr.append(List.col([$product, $productImg]));
        $tr.append(List.col([$qty]));
        $tr.append(List.col([$uom]));
        $tr.append(List.col([$amount]));
        $tr.append(List.col([$discount]));
        $tr.append(List.col([$totalAmount]));
        // $tr.append(List.col([$packNote]));

        $tbody.append($tr);
        index++;
        $(".input-decimal").bind(inputFormat);
    }

    function openProduct(index) {
        const baseUrl = '<c:url value="/page/popupproductview.htm"/>';
        const params = {
            target: 'product[' + index + ']', // Id Dropdown (Select) element
            index: index,
            status: true // Filter Only Active Products
        };
        openpopup(buildUrl(baseUrl, params));
    }

    function checkDuplicate(element) {
        var isDuplicated = String.duplicate('productInput');

        if (isDuplicated) {
            alert('<spring:message code="product"/> <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
            $(element).closest('tr').remove();
        }
    }
</script>