<%--This Page Copied From salesOrderAdd.jsp(COCINA) and receiptAdd.jsp (MML)--%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/receiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="receipt_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right">Sales Order ID</td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
                            <td width="1%"><form:errors path="code"/></td>
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
                                    <c:if test='${not empty receipt_form.organization}'>
                                        <form:option value='${receipt_form.organization.id}' label='${receipt_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
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
                </td>

                <td width="40%" valign="top">
                    <table width="100%" style="border: none">
                        <tr>
                            <td>
                                <fieldset>
                                    <legend><strong><spring:message code="receipt.information"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <th width="80%">&nbsp;</th>
                                            <th width="20%" align="right"><spring:message code="sirius.amount"/> (<c:out value='${defaultCurrency.symbol}'/>)</th>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="billing.total"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="billing" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="receipt.total"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalAmount" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="receipt.applied.amount"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="applied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="receipt.total.remaining"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="unapplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
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
            <div id="receiptInformation" dojoType="ContentPane" label="<spring:message code='receipt.information'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
                <tr>
                    <td width="50%" align="left" valign="top">
                        <table width="100%">
                        <tr>
                            <td width="25%" align="right"><spring:message code="receipt.payment.type"/> : </td>
                            <td width="75%">
                                <form:select id='methodType' path='receiptInformation.paymentMethodType' onchange="receiptTypeChange(this)">
                                    <c:forEach items='${types}' var='type' varStatus="status">
                                        <spring:message var="src" code="receipt.${type.message}"/>
                                        <form:option value='${type}' label='${fn:toUpperCase(src)}'/>
                                    </c:forEach>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="receipt.reference"/> : </td>
                            <td><form:input id='clearing' path='receiptInformation.reference' size='30' disabled='true' cssClass='input-disabled'/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="bankaccount"/> : </td>
                            <td>
                                <form:select id='account' path='receiptInformation.bankAccount' cssClass="combobox">
                                    <c:if test='${not empty receiptInformation.bankAccount}'>
                                        <form:option value='${receiptInformation.bankAccount.id}' label='${receiptInformation.bankAccount.code}' />
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="openBankAccount();" title='<spring:message code="bankaccount"/>'/>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                            <td><input id='accountName' disabled class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                            <td><input id='bankName' disabled class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                            <td><input id='accountNo' disabled class='input-disabled' size="30"/></td>
                        </tr>
                        </table>
                    </td><%--Panel Receipt Information Bagian Kiri--%>

                    <td width="50%" align="left" valign="top">
                        <table width="100%">
                        <tr>
                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="receipt.duedate"/> :</td>
                            <td width="61%"><input id="duedate" name="receiptInformation.dueDate" class="datepicker" /></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
                            <td>
                                <form:input id='amount' path='receiptInformation.amount' value="0.00" cssClass='input-currency applied'/>
                                <input type="hidden" id="amountdef" value="0"/>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="receipt.charge"/> : </td>
                            <td>
                                <form:input id='bankCharges' path='receiptInformation.bankCharges' value="0.00" cssClass='input-number applied'/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.note"/> : </td>
                            <td><form:textarea path='receiptInformation.note' cols='40' rows='6'/></td>
                        </tr>
                        </table>
                    </td><%--Panel Receipt Information Bagian Kanan--%>
                </tr>
                </table>
            </div>

            <div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='receipt.application'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                        <thead>
                            <tr>
<%--                                Uncomment bellow if using checkbox--%>
<%--                                <th width="1%">--%>
<%--                                    <div style="width: 30px"><input type="checkbox" id="checkMaster" class="checkall"/></div>--%>
<%--                                </th>--%>
                                <th width="10%"><spring:message code="billing"/></th>
                                <th width="5%"><spring:message code="billing.date"/></th>
                                <th width="10%"><spring:message code="billing.total"/></th>
                                <th width="80%"><spring:message code="receipt.paid"/></th>
                            </tr>
                        </thead>
                        <tbody id="lineItemBody">
                        </tbody>
                        <tfoot>
                            <tr>
<%--                                Uncomment bellow if using checkbox--%>
<%--                                <td>&nbsp;</td>--%>
                                <td colspan="2"><div style="text-align: right;font-weight: bold;"><spring:message code="sirius.total"/></div></td>
                                <td><input size="20" id='totalBilling' class="number-disabled" readonly value="0.00"/></td>
                                <td><input size='20' id='totalPaid' class="number-disabled" readonly value="0.00"/></td>
                            </tr>
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
        $('.item-button-save').click(function(){
            if(validateForm()) {
                save();
            }
        });

        // $('.checkall').click(function () {
        //     $('.check').prop("checked", this.checked);
        // });

        // Trigger OnChange Customer Field
        $('#customer').change(function(e)
        {
            getBillings();
            updateDisplay();
        });

        // Trigger OnChange "Receipt Amount" field
        $('.applied').change(function(e)
        {
            var amount = document.getElementById('amount');
            var unapplied = document.getElementById('unapplied');
            // var rate = $("#trxrate").val().toNumber(); // Unused

            unapplied.value = amount.value.toNumber().numberFormat('#,##0.00');

            updateDisplay();
        });

        $('#methodType').trigger('change');

        updateDisplay();
    });

    function receiptTypeChange(element) {
        if($(element).val() != 'CASH')
        {
            $('#bankCharges').attr('class', 'input-currency');
            $('#bankCharges').removeAttr('disabled');

            $('#clearing').attr('class', 'inputbox');
            $('#clearing').removeAttr('disabled');

            $('#duedate').removeClass('input-disabled');
            $('#duedate').removeAttr('disabled');

            $('#duedate').next("img.ui-datepicker-trigger").show(); // Show DatePicker Icon
        }
        else
        {
            $('#bankCharges').attr('class', 'number-disabled');
            $('#bankCharges').attr('disabled', 'true');

            $('#clearing').attr('class', 'input-disabled');
            $('#clearing').attr('disabled', 'true');

            $('#duedate').addClass('input-disabled');
            $('#duedate').attr('disabled', 'true');

            // Sembunyikan pemicu datepicker (img) untuk tipe 'CASH'
            $('#duedate').next("img.ui-datepicker-trigger").hide();
        }

        $('#duedate').val("");
        $("#account").html("");
        $("#accountName").val("");
        $("#bankName").val("");
        $("#bankBranch").val("");
        $("#accountNo").val("");
        $("#bankCharges").val("0.00");
    }

    function openBankAccount() {
        $org = $('#org').val();
        if(!$org)
        {
            alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !!!');
            return;
        }

        var methodType = document.getElementById('methodType');
        var method = 'CASH';

        if(methodType.value !== 'CASH')
            method = 'BANK';

        openpopup("<c:url value='/page/popupbankaccountview.htm?&target=account&organization='/>" + $org + '&type=' + method);
    }

    function validateForm() {
        // Validasi organisasi (sudah ada sebelumnya)
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

        var bankAccount = $('#account').val();
        if (bankAccount == null || bankAccount === "") {
            alert('<spring:message code="bankaccount"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        if($('#unapplied').val().toNumber() != 0) {
            alert('<spring:message code="receipt.applied.amount"/> <spring:message code="notif.invalid"/> !!!');
            return false;
        }

        if($('#applied').val().toNumber() <= 0)
        {
            alert('<spring:message code="receipt.applied.amount"/> <spring:message code="notif.empty"/> !!!');
            return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/receiptadd.htm'/>",
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
                        window.location="<c:url value='/page/receiptview.htm'/>";
                        // Or Can use This
                        <%--window.location="<c:url value='/page/receiptpreedit.htm?id='/>"+json.data.id;--%>
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
        // Unused
        // var _rate = document.getElementById('trxrate').value.replace(/,/g,'').toNumber();

        var tbl = document.getElementById("lineItemTable");
        if(tbl)
        {
            var amount = document.getElementById('amount');
            var billing = 0.00;
            var unapplied = document.getElementById('unapplied');
            var applied = document.getElementById('applied');

            document.getElementById('totalAmount').value = parseFloat(amount.value.toNumber()-$('#bankCharges').val().toNumber()).numberFormat('#,##0.00');

            unapplied.value = parseFloat(amount.value.toNumber()-$('#bankCharges').val().toNumber()).numberFormat('#,##0.00');
            applied.value = 0.0.numberFormat('#,##0.00');

            $('.paid').each(function () {
                idx = $(this).attr('index');

                $unpaid = parseFloat($('#unpaid\\['+idx+'\\]').val().toNumber());
                $paid = parseFloat($('#price\\['+idx+'\\]').val().toNumber());

                if($paid > $unpaid) {
                    $paid = $unpaid;
                    $('#price\\['+idx+'\\]').val($paid.numberFormat('#,##0'));
                }

                billing = billing + $unpaid;
                unapplied.value = parseFloat(unapplied.value.toNumber()-$paid).numberFormat('#,##0.00');
                applied.value = parseFloat(applied.value.toNumber()+$paid).numberFormat('#,##0.00');
            });

            document.getElementById('billing').value = parseFloat(billing).numberFormat('#,##0.00');
            document.getElementById('totalBilling').value = parseFloat(billing).numberFormat('#,##0.00');
            document.getElementById('totalPaid').value = parseFloat(applied.value.toNumber()).numberFormat('#,##0.00');
        }
    }

    let index = 0; // For Line Item ID (Index)
    function addLineItem()
    {
        $tbody = $('#lineItemBody');
        $tr = $('<tr/>');

        // Uncomment bellow if using checkbox
        // $cbox = List.get('<input type="checkbox" class="check"/>','check'+index);

        $invoice = List.get('<select class="input-disabled combobox payables"/>', 'reference['+index+']');
        $invoice.change(function() {
            updateDisplay();
        });

        $date = List.get('<input size="8" class="input-disabled" disabled/>', 'date['+index+']');
        $unpaid = List.get('<input size="20" class="number-disabled" disabled/>', 'unpaid['+index+']', '0.00');
        $paid = List.get('<input size="20" class="input-decimal paid"/>', 'price['+index+']', '0.00');
        $paid.change(function() {
            updateDisplay();
        });

        // Uncomment bellow if using checkbox
        // $tr.append(List.col([$cbox]));
        $tr.append(List.col([$invoice]));
        $tr.append(List.col([$date]));
        $tr.append(List.col([$unpaid]));
        $tr.append(List.col([$paid]));

        $tbody.append($tr);
        index++;

        $(".input-decimal").bind(inputFormat);
    }

    function clearLineItem() {
        $('#lineItemBody').empty();
        index = 0;
        updateDisplay();
    }

    function getBillings()
    {
        clearLineItem(); // Clear the line item first before add & reset index to 0
        $org = $('#org').val();
        $customer = $('#customer').val();

        $.get("<c:url value='/page/popupbillingviewjson.htm'/>",{organization:$org, customer:$customer},function(json)
        {
            $.each(json.billings,function(idx,val)
            {
                addLineItem();

                var _client = document.getElementById('reference['+idx+']');
                _client.remove(_client.selectedIndex);

                var _opt = document.createElement('option');
                _opt.value = val.billId;
                _opt.text = val.billCode;

                _client.appendChild(_opt);
                _client.dispatchEvent(new Event('change'));

                var _date = document.getElementById('date['+idx+']');
                _date.value = val.date;

                var _unpaid = document.getElementById('unpaid['+idx+']');
                _unpaid.value = val.unpaid.numberFormat('#,##0.00');

                updateDisplay();
            });
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
</script>