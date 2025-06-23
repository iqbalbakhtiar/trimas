<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/billingbatchview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="billing_batch_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="23%" align="right"><spring:message code="sirius.id"/></td>
                <td width="1%" align="center">:</td>
                <td width="77%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
                <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.date"/></td>
                <td width="1%" align="center">:</td>
                <td><input id="date" name="date" size="10" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr style="display: none">
                <td align="right"><spring:message code="organization"/></td>
                <td width="1%" align="center">:</td>
                <td>
                    <form:select id="org" path="organization" cssClass="combobox-ext">
                        <c:if test='${not empty billing_batch_form.organization}'>
                            <form:option value='${billing_batch_form.organization.id}' label='${billing_batch_form.organization.fullName}'/>
                        </c:if>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="customer"/></td>
                <td width="1%" align="center">:</td>
                <td>
                    <form:select id="customer" path="customer" cssClass="combobox-ext">
                    </form:select>
                    <a class="item-popup" onclick="openCustomer();" title="Customer" />
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
                    <div class="item-navigator">&nbsp;</div>
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                        <thead>
                        <tr>
                            <th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
                            <th width="10%" nowrap="nowrap"><spring:message code="sirius.id"/> <spring:message code="billing"/></th>
                            <th width="10%" nowrap="nowrap"><spring:message code="sirius.date"/></th>
                            <th width="20%" nowrap="nowrap"><spring:message code="customer"/></th>
                            <th width="60%" nowrap="nowrap"><spring:message code="sirius.amount"/></th>
                        </tr>
                        </thead>
                        <tbody id="lineItemBody">
                        </tbody>
                        <tfoot>
                        <tr>
                            <td></td>
                            <td><strong><spring:message code="sirius.total"/></strong></td>
                            <td colspan="2"></td>
                            <td>
                                <input id="amountGrandTotal" class="input-decimal input-disabled" value="0.00" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><strong><spring:message code="sirius.total"/> <spring:message code="billing.line.select"/></strong></td>
                            <td colspan="2"></td>
                            <td>
                                <input id="amountGrandTotalSelect" name="amount" class="input-decimal input-disabled" value="0.00" readonly>
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        $('.item-button-save').click(function(){
            if(validateForm()) {
                save();
            }
        });

        $('.checkall').click(function () {
            $('.check').prop("checked", this.checked);
            updateDisplay();
        });

        // Trigger OnChange Customer Field
        $('#customer').change(function(e)
        {
            getBillings();
            updateDisplay();
        });
    });

    function getBillings()
    {
        clearLineItem(); // Clear the line item first before add & reset index to 0
        $org = $('#org').val();
        $customer = $('#customer').val();

        // To Create Receipt Line Item after select "Customer / Group"
        $.get("<c:url value='/page/popupbillingviewjson.htm'/>",{organization:$org, customer:$customer},function(json)
        {
            $.each(json.billings,function(idx,val)
            {
                addLineItem();

                // Billing Code / Reference
                var _client = document.getElementById('billing['+idx+']');
                _client.remove(_client.selectedIndex);

                var _opt = document.createElement('option');
                _opt.value = val.billId;
                _opt.text = val.billCode;

                _client.appendChild(_opt);
                _client.dispatchEvent(new Event('change'));

                // Date
                var _date = document.getElementById('date['+idx+']');
                _date.value = val.date;

                // Customer
                var _customerSelect = document.getElementById('customer['+idx+']');
                _customerSelect.remove(_customerSelect.selectedIndex);

                var _customerOpt = document.createElement('option');
                _customerOpt.value = val.customer.partyId;
                _customerOpt.text = val.customer.partyName;

                _customerSelect.appendChild(_customerOpt);

                // Billing Unpaid
                var _unpaid = document.getElementById('unpaid['+idx+']');
                _unpaid.value = val.unpaid.numberFormat('#,##0.00');

                updateDisplay();
            });
        });
    }

    function updateDisplay() {
        var total = 0;

        // Calculate Total / GrandTotalAmount
        $('input.billingAmounts').each(function(){
            var num = $(this).val().toNumber() || 0;
            total += num;
        });

        $('#amountGrandTotal').val(total.numberFormat('#,##0.00'));

        // Calculate Total Selected Items
        var totalSelected = 0;
        $('.check:checked').closest('tr').find('input.billingAmounts').each(function(){
            totalSelected += $(this).val().toNumber()||0;
        });
        $('#amountGrandTotalSelect').val(totalSelected.numberFormat('#,##0.00'));
    }

    function validateForm() {
        var date = $('#date').val();
        if (date == null || date === "") {
            alert('<spring:message code="sirius.date"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        if ($('#lineItemBody tr').length === 0) {
            alert('<spring:message code="billing.line.item"/> <spring:message code="notif.empty"/>!');
            return false;
        }
        if ($('.check:checked').length === 0) {
            alert('<spring:message code="notif.select1"/> <spring:message code="billing.line.item"/> <spring:message code="notif.select2"/>');
            return false;
        }

        return true;
    }

    let index = 0; // For Line Item ID (Index)
    function addLineItem()
    {
        $tbody = $('#lineItemBody');
        $tr = $('<tr/>');

        $cbox = List.get('<input type="checkbox" class="check" onchange="updateDisplay();"/>','enabled['+index+']');

        $billing = List.get('<select class="input-disabled combobox payables"/>', 'billing['+index+']');

        $customer = List.get('<select class="input-disabled combobox customers" disabled/>', 'customer['+index+']');

        $date = List.get('<input class="input-disabled inputbox-date" disabled/>', 'date['+index+']');

        $unpaid = List.get('<input size="20" class="number-disabled billingAmounts" disabled/>', 'unpaid['+index+']', '0.00');

        $tr.append(List.col([$cbox]));
        $tr.append(List.col([$billing]));
        $tr.append(List.col([$date]));
        $tr.append(List.col([$customer]));
        $tr.append(List.col([$unpaid]));

        $tbody.append($tr);
        index++;

        $(".input-number").bind(inputFormat);
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/billingbatchadd.htm'/>",
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
        };

        openpopup(buildUrl(baseUrl, params));
    }

    function clearLineItem() {
        $('#lineItemBody').empty();
        index = 0;
        updateDisplay();
    }

</script>