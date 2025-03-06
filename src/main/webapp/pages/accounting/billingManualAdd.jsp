<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/salesorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="billingManual_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.id"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext" onchange="updateTaxAddress(this)">
                                    <c:if test='${not empty billingManual_form.organization}'>
                                        <form:option value='${billingManual_form.organization.id}' label='${billingManual_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="billing.taxaddress"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="taxAddress" path="taxAddress" cssClass="combobox-ext">
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="customer"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="customer" path="customer" cssClass="combobox-ext" onchange="updateCustomerAddress();updateCreditTerm();">
                                </form:select>
                                <a class="item-popup" onclick="openCustomer()" title="Customer" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="billing.credit.term"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input size="7" id="creditTerm" name="term" class="input-disabled" readonly value=""/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="salesorder.tax.type"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="tax" path="tax" onchange="updateDisplay();">
                                    <c:forEach var="tax" items="${taxes}">
                                        <option value="${tax.id}" data-taxrate="${tax.taxRate}">${tax.taxName}</option>
                                    </c:forEach>
                                </form:select>
                                <spring:message code="salesorder.tax.rate"/>
                                <input size="7" id="taxRate" class="input-disabled" disabled />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="billing.currency"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="currency" path="currency" class="rate" onchange="initCurrency(this)">
                                    <form:options items='${currencys}' itemLabel='symbol' itemValue='id'/>
                                </form:select>
                                <form:select id='type' path="exchangeType" class="rate">
                                    <form:option value='SPOT'>SPOT</form:option>
                                    <form:option value='MIDDLE'>MIDDLE</form:option>
                                    <form:option value='TAX'>TAX</form:option>
                                </form:select>
                                <input id="rate" name="rate" size="10" class="input-disabled input-number" readonly value="1.00" onchange="updateDisplay()"/>
                                <input id="defaultCurrency" type="hidden" value="${defaultCurrency.id}"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="billing.invoice.tax.no"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <input size="7" id="invoiceTaxHeader" name="invoiceTaxHeader" />
                                &nbsp;
                                <input size="28" id="invoiceTaxNo" name="invoiceTaxNo" />
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
                                    <legend><strong><spring:message code="salesorder.recapitulation"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <th width="60%">&nbsp;</th>
                                            <th width="20%"><spring:message code="sirius.amount"/>&nbsp;(<span id="trx"></span>)</th>
                                            <th width="20%"><spring:message code="sirius.amount"/>&nbsp;(<span id="trxDef">${defaultCurrency.symbol}</span>)</th>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="salesorder.total"/></td>
                                            <td width="20%">:&nbsp;<input id="totalSales" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                            <td width="20%">&nbsp;<input id="totalSalesDef" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="salesorder.tax.amount"/></td>
                                            <td width="20%">:&nbsp;<input id="totalTax" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                            <td width="20%">&nbsp;<input id="totalTaxDef" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><strong><spring:message code="salesorder.total.transaction"/></strong></td>
                                            <td width="20%">:&nbsp;<input id="totalTransaction" value="0.00" name="amount" class="number-disabled" readonly="readonly" size="20"/></td>
                                            <td width="20%">&nbsp;<input id="totalTransactionDef" value="0.00" name="amountDef" class="number-disabled" readonly="readonly" size="20"/></td>
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
        <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
            <div id="address" dojoType="ContentPane" label="<spring:message code='postaladdress.detail'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
                    <tr>
                        <td width="50%">
                            <table style="border:none" width="100%">
                                <tr>
                                    <td align="right"><spring:message code="salesorder.shipping.name"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td>
                                        <form:select id="shippingAddress" path="shippingAddress" cssClass="combobox-ext" onchange="updatedAddressDetail('shipping', this.value)">
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.detail"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="shippingAddressDetail" class="inputbox input-disabled" disabled/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.postalcode"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="shippingAddressPostalCode" class="inputbox input-disabled" disabled/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.city"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="shippingAddressCity" class="inputbox input-disabled" disabled/></td>
                                </tr>
                            </table>
                        </td>
                        <td width="50%">
                            <table style="border:none" width="100%">
                                <tr>
                                    <td align="right"><spring:message code="billing.address"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td>
                                        <form:select id="billingAddress" path="billingAddress" cssClass="combobox-ext" onchange="updatedAddressDetail('billing', this.value)">
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.detail"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="billingAddressDetail" class="inputbox input-disabled" disabled/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.postalcode"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="billingAddressPostalCode" class="inputbox input-disabled" disabled/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="postaladdress.city"/></td>
                                    <td width="1%" align="center">:</td>
                                    <td><input id="billingAddressCity" class="inputbox input-disabled" disabled/></td>
                                </tr>
                            </table>
                        </td>

                    </tr>
                </table>
            </div>

            <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
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
                            <th width="8%" nowrap="nowrap"><spring:message code="sirius.unitprice"/></th>
                            <th width="8%" nowrap="nowrap"><spring:message code="sirius.total"/> <spring:message code="sirius.amount"/></th>
                            <th width="8%" nowrap="nowrap"><spring:message code="billing.note"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
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
        var $index = 0; // For Line Item Index

        updateDisplay();

        $('.item-button-save').click(function(){
            if(validateForm()) {
                save();
            }
        });

        $('.item-button-new').click(function() {
            addLine($index);
            $index++;
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

        // Trigger Manual Org Onchange because Org is prefilled
        $('#org').trigger('change');
        $('#currency').trigger('change');
    });

    function validateForm() {
        // Validasi organisasi
        var organization = $('#org').val();
        if (organization == null || organization === "") {
            alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi date
        var date = $('#date').val();
        if (date == null || date === "") {
            alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi customer
        var customer = $('#customer').val();
        if (customer == null || customer === "") {
            alert('<spring:message code="customer"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi credit term
        var creditTerm = $('#creditTerm').val();
        if (creditTerm == null || creditTerm === "") {
            alert('<spring:message code="creditterm"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Validasi tax
        var tax = $('#tax').val();
        if (tax == null || tax === "") {
            alert('<spring:message code="salesorder.tax.type"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // **Tambahkan validasi untuk memastikan setidaknya ada satu line item**
        if ($('#lineItem tr').length === 0) {
            alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
            return false;
        }

        // Validasi Line Items
        var isValid = true;
        $('#lineItem tr').each(function(index){
            var $row = $(this);

            // Validasi product
            var product = $row.find('select[id^="product["]').val();
            if (product == null || product === "") {
                alert('<spring:message code="product"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
                isValid = false;
                return false; // Keluar dari each
            }

            // Validasi quantity
            var qtyStr = $row.find('input[id^="quantity["]').val().replace(/,/g, '');
            var qty = parseFloat(qtyStr);
            if (isNaN(qty) || qty <= 0) {
                alert('<spring:message code="sirius.qty"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
                isValid = false;
                return false;
            }

            // Validasi price
            var priceStr = $row.find('input[id^="amount["]').val().replace(/,/g, '');
            var price = parseFloat(priceStr);
            if (isNaN(price) || price <= 0) {
                alert('<spring:message code="sirius.unitprice"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
                isValid = false;
                return false;
            }
        });

        if (!isValid) {
            return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/billingmanualadd.htm'/>",
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
                        window.location="<c:url value='/page/billingmanualpreedit.htm?id='/>"+json.id;
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

    function updateDisplay() {
        let taxRate = Number.parse($('#tax option:selected').data('taxrate')) || 0;
        $('#taxRate').val(taxRate ? taxRate.toFixed(2) : '');

        let totalSales = 0, totalBeforeTax = 0;

        let currencyRate = parseFloat($('#rate').val().replace(/,/g, '')) || 1.00;

        function getNumericValue($input) {
            return $input.val().toNumber() || 0;
        }

        $('#lineItem tr').each(function(){
            let $row = $(this);

            let qty = getNumericValue($row.find('input[id^="quantity["]'));
            let price = getNumericValue($row.find('input[id^="amount["]'));

            let amount = qty * price;
            let totalAmount = amount;

            totalSales += amount;
            totalBeforeTax += totalAmount;

            $row.find('input[id^="amountInput["]').val(amount.numberFormat('#,##0.00'));
            $row.find('input[id^="totalAmount["]').val(totalAmount.numberFormat('#,##0.00'));
        });

        let totalTax = totalBeforeTax * (taxRate / 100);
        let totalTransaction = totalBeforeTax + totalTax;

        let totalSalesDef = totalSales * currencyRate;
        let totalTaxDef = totalTax * currencyRate;
        let totalTransactionDef = totalTransaction * currencyRate;

        $('#totalSales').val(totalSales.numberFormat('#,##0.00'));
        $('#totalTax').val(totalTax.numberFormat('#,##0.00'));
        $('#totalTransaction').val(totalTransaction.numberFormat('#,##0.00'));

        $('#totalSalesDef').val(totalSalesDef.numberFormat('#,##0.00'));
        $('#totalTaxDef').val(totalTaxDef.numberFormat('#,##0.00'));
        $('#totalTransactionDef').val(totalTransactionDef.numberFormat('#,##0.00'));
    }

    function addLine($index) {
        $tbody = $('#lineItem');
        $tr = $('<tr/>');

        $cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);

        $product = List.get('<select class="combobox productInput" onchange="checkDuplicate(this);updateDisplay();"/>','product['+$index+']');
        $productImg = List.img('<spring:message code="product"/>', $index, 'openProduct("'+$index+'")');

        $qty = List.get('<input type="text" class="input-number" size="6" onchange="updateDisplay();"/>','quantity['+$index+']', '0.00');

        $uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+$index+']');

        $price = List.get('<input type="text" class="input-number" size="12" onchange="updateDisplay()"/>','amount['+$index+']', '0.00');

        $totalAmount = List.get('<input type="text" class="input-number input-disabled" disabled size="12"/>','totalAmount['+$index+']', '0.00');

        $note = List.get('<input type="text"/>','note['+$index+']');

        $tr.append(List.col([$cbox]));
        $tr.append(List.col([$product, $productImg]));
        $tr.append(List.col([$qty]));
        $tr.append(List.col([$uom]));
        $tr.append(List.col([$price]));
        $tr.append(List.col([$totalAmount]));
        $tr.append(List.col([$note]));

        $tbody.append($tr);

        $(".input-number").bind(inputFormat);
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
        var productId = $(element).val();
        var index = element.getAttribute('index');

        var isDuplicated = String.duplicate('productInput');

        if (isDuplicated) {
            alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
            $(element).closest('tr').remove();
        }

        // Bug, return price is always IDR
        // TODO create filter / flag currency to return correct price amount
        <%--if(productId) {--%>
        <%--    $.ajax({--%>
        <%--        url:"<c:url value='/page/salesorderbyproductjson.htm'/>",--%>
        <%--        data:{productId:productId},--%>
        <%--        method : 'GET',--%>
        <%--        dataType : 'json',--%>
        <%--        success : function(json) {--%>
        <%--            if(json)--%>
        <%--            {--%>
        <%--                if(json.status == 'OK'){--%>
        <%--                    let amount = document.getElementsByName('items['+index+'].amount')[0];--%>
        <%--                    if(amount && json.product != null) {--%>
        <%--                        amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');--%>
        <%--                    }--%>
        <%--                }--%>
        <%--            }--%>
        <%--        }--%>
        <%--    });--%>
        <%--}--%>
    }

    function openCustomer() {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
        const params = {
            target: 'customer', // Id Dropdown (Select) element
            organization: orgId, // Org (PartyTo)
            fromRoleType: 4, // Customer
            toRoleType: 5, // Supplier
            relationshipType: 3, // Customer Relationship
            base: false // Filter Only Customer (Not Group)
        };

        openpopup(buildUrl(baseUrl, params));
    }

    function openApprover() {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
        const params = {
            target: 'approver', // Id Dropdown (Select) element
            organization: orgId, // Org (PartyTo)
            fromRoleType: 8, // Sales Approver
            toRoleType: 2, // Company
            relationshipType: 2, // Employment Relationship
        };

        openpopup(buildUrl(baseUrl, params));
    }

    function updateTaxAddress(element) {
        let companyId = $('#org').val();

        Party.load(companyId);

        let _taxAddress = $('#taxAddress');
        if (_taxAddress.find('option').length > 0) { // Clear options if any
            _taxAddress.empty();
        }

        let addresses = Party.data.partyAddresses;

        addresses.forEach(address => {
            // Periksa apakah postalTypes memiliki tipe 'TAX' dengan enabled == true
            let hasShippingEnabled = address.postalTypes.some(postalType =>
                postalType.type === 'TAX' && postalType.enabled === true
            );

            if (hasShippingEnabled) {
                let option = $('<option></option>')
                    .val(address.postalId)
                    .text(address.addressName);

                if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
                    option.attr('selected', 'selected');
                }

                _taxAddress.append(option); // Tambahkan opsi ke elemen _taxAddress
            }
        });
    }

    function initCurrency(element) {
        let selectedText = $(element).find('option:selected').text();

        $('#trx').text(selectedText);

        let selectedCurrencyId = $(element).val();
        let defaultCurrencyId = $('#defaultCurrency').val();

        let $rateInput = $('#rate');

        if (selectedCurrencyId === defaultCurrencyId) {
            $rateInput.addClass('input-disabled');
            $rateInput.attr('readonly', true);
            $rateInput.val('1.00');
        } else {
            $rateInput.removeClass('input-disabled');
            $rateInput.attr('readonly', false);
            $rateInput.val('1.00');
        }

        updateDisplay();
    }

    function updateCustomerAddress() {
        let customerId = $('#customer').val();

        // Already Load in customer set client. So Object Party already filled with selected customer data
        // Un-comment if needed, comment to avoid call 2 times
        // Party.load(customerId);

        // Shipping Address
        let _shippingAddress = $('#shippingAddress');
        if (_shippingAddress.find('option').length > 0) { // Clear options if any
            _shippingAddress.empty();
        }

        // Billing Address
        let _billingAddress = $('#billingAddress');
        if (_billingAddress.find('option').length > 0) { // Clear options if any
            _billingAddress.empty();
        }

        let addresses = Party.data.partyAddresses;

        addresses.forEach(address => {
            // Shipping
            // Periksa apakah postalTypes memiliki tipe 'SHIPPING' dengan enabled == true
            let hasShippingEnabled = address.postalTypes.some(postalType =>
                postalType.type === 'SHIPPING' && postalType.enabled === true
            );

            if (hasShippingEnabled) {
                let option = $('<option></option>')
                    .val(address.postalId)
                    .text(address.addressName);

                if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
                    option.attr('selected', 'selected');
                }

                _shippingAddress.append(option); // Tambahkan opsi ke elemen _shippingAddress
            }

            // Billing
            // Periksa apakah postalTypes memiliki tipe 'OFFICE' dengan enabled == true
            let hasBillingEnabled = address.postalTypes.some(postalType =>
                postalType.type === 'OFFICE' && postalType.enabled === true
            );

            if (hasBillingEnabled) {
                let option = $('<option></option>')
                    .val(address.postalId)
                    .text(address.addressName);

                if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
                    option.attr('selected', 'selected');
                }

                _billingAddress.append(option); // Tambahkan opsi ke elemen _billingAddress
            }
        });

        updatedAddressDetail('shipping', _shippingAddress.val());
        updatedAddressDetail('billing', _billingAddress.val());
    }

    function updatedAddressDetail(type, selectedId) {
        // Bersihkan field detail berdasarkan type (shipping atau billing)
        $('#' + type + 'AddressDetail').val('');
        $('#' + type + 'AddressPostalCode').val('');
        $('#' + type + 'AddressCity').val('');

        if (!selectedId || selectedId.trim() === "") {
            return;
        }

        PostalAddress.load(selectedId);

        if (PostalAddress.data) {
            var addressDetail = PostalAddress.data.postalAddress || '';
            var postalCode = PostalAddress.data.postalCode || '';
            var city = PostalAddress.data.postalCity ? PostalAddress.data.postalCity.name : '';

            // Update field dengan menggunakan parameter type
            $('#' + type + 'AddressDetail').val(addressDetail);
            $('#' + type + 'AddressPostalCode').val(postalCode);
            $('#' + type + 'AddressCity').val(city);
        } else {
            // Jika tidak ada data, pastikan field dikosongkan
            $('#' + type + 'AddressDetail').val('');
            $('#' + type + 'AddressPostalCode').val('');
            $('#' + type + 'AddressCity').val('');
        }
    }

    function updateCreditTerm() {
        let customerId = $('#customer').val();
        let orgId = $('#org').val();
        let date = $('#date').val();

        $.ajax({
            url: "<c:url value='/page/popupcredittermjson.htm'/>",
            data: {
                id: customerId,
                org: orgId,
                type: 3,             // hardcode from "PartyRelationshipType" table (CUSTOMER_RELATIONSHIP)
                date: date
            },
            method: 'GET',
            dataType: 'json',
            success: function(json) {
                if (json) {
                    if (json.status === 'OK') {
                        if (json.creditTerm && json.creditTerm.term) {
                            $('#creditTerm').val(json.creditTerm.term);
                        } else {
                            alert("Customer doesn't have active Credit Term, please set it first on customer page.");
                            $('#creditTerm').val(null);
                        }
                    } else {
                        alert("Error: " + json.message);
                    }
                }
            }
        });
    }
</script>