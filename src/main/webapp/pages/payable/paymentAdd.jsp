<%--This Page Copied From receipt.add--%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/receiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="payment_add" enctype="multipart/form-data">
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
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext">
                                    <c:if test='${not empty payment_add.organization}'>
                                        <form:option value='${payment_add.organization.id}' label='${payment_add.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="supplier"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="supplier" path="supplier" cssClass="combobox-ext">
                                </form:select>
                                <a class="item-popup" onclick="openSupplier();" title="Supplier" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
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
                                    <legend><strong><spring:message code="payment.information"/></strong></legend>
                                    <table width="100%" style="border: none">
                                        <tr>
                                            <th width="80%">&nbsp;</th>
                                            <th width="20%" align="right"><spring:message code="sirius.amount"/> (<c:out value='${defaultCurrency.symbol}'/>)</th>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.total.unapplied"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalUnapplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.total.applied"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalApplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.writeoff"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalWriteOff" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
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
            <div id="paymentInformation" dojoType="ContentPane" label="<spring:message code='payment.information'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <table width="100%">
                    <tr>
                        <td width="25%" align="right"><spring:message code="sirius.type"/> : </td>
                        <td width="75%">
                            <form:select id='methodType' path='paymentInformation.paymentMethodType' onchange="paymentTypeChange(this)">
                                <c:forEach items='${types}' var='type' varStatus="status">
                                    <spring:message var="src" code="receipt.${type.message}"/>
                                    <form:option value='${type}' label='${fn:toUpperCase(src)}'/>
                                </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="receipt.reference"/> : </td>
                        <td><form:input id='clearing' path='paymentInformation.reference' size='30' disabled='true' cssClass='input-disabled'/></td>
                    </tr>
                    <tr>
                        <td align="right"><spring:message code="bankaccount"/> : </td>
                        <td>
                            <form:select id='account' path='paymentInformation.bankAccount' cssClass="combobox">
                                <c:if test='${not empty payment_add.paymentInformation.bankAccount}'>
                                    <form:option value='${payment_add.paymentInformation.bankAccount.id}' label='${payment_add.paymentInformation.bankAccount.code}' />
                                </c:if>
                            </form:select>
                            <a class="item-popup" onclick="openBankAccount();" title='<spring:message code="bankaccount"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" align="right"><spring:message code="payment.amount"/> : </td>
                        <td>
                            <form:input id='amount' path='paymentInformation.amount' value="0.00" cssClass='applied input-number' onchange="updateDisplay()"/>
                        </td>
                    </tr>
                    <tr style="display: none"><%--This Field Hidden because used in addLineItem function--%>
                        <td colspan="2">
                            <select id="wtype" style="display: none;">
                                <c:forEach items='${writes}' var='type' varStatus="status">
                                    <option value='${type}'>${type}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='payment.application'/>" class="tab-pages" refreshOnShow="true" selected="true">
                <div class="toolbar-clean">
                    <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                        <thead>
                        <tr>
                            <th width="20%"><spring:message code="invoiceverification"/></th>
                            <th width="10%"><spring:message code="invoiceverification.unpaid"/></th>
                            <th width="10%"><spring:message code="payment.writeoff.type"/></th>
                            <th width="10%"><spring:message code="payment.writeoff"/></th>
                            <th ><spring:message code="payment.paid.amount"/>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody id="lineItem">
                        </tbody>
                        <tfoot>
                        <tr class="end-table"><td colspan="8">&nbsp;</td></tr>
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

        $('.item-button-save').click(function(){
            if(validateForm()) {
                save();
            }
        });

        $('.item-button-new').click(function() {
            if (!$('#supplier').val()) {
                alert('<spring:message code="notif.select1"/> <spring:message code="supplier"/> <spring:message code="notif.select2"/> !!!');
                return;
            }
            addLine($index);
            $index++;
            updateDisplay();
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

        $('#methodType').trigger('change');

        updateDisplay();
    });

    function paymentTypeChange(element) {
        if($(element).val() != 'CASH')
        {
            $('#clearing').attr('class', 'inputbox');
            $('#clearing').removeAttr('disabled');
        }
        else
        {
            $('#clearing').attr('class', 'input-disabled');
            $('#clearing').attr('disabled', 'true');
        }
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
        var supplier = $('#supplier').val();
        if (supplier == null || supplier === "") {
            alert('<spring:message code="supplier"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        var bankAccount = $('#account').val();
        if (bankAccount == null || bankAccount === "") {
            alert('<spring:message code="bankaccount"/> <spring:message code="notif.empty"/> !');
            return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/paymentadd.htm'/>",
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
                        window.location="<c:url value='/page/paymentview.htm'/>";
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
        const tbl = document.getElementById("lineItemTable");
        if(tbl)
        {
        	var totalUnapplied = document.getElementById('totalUnapplied');
        	var totalApplied = document.getElementById('totalApplied');
        	var totalWriteOff = document.getElementById('totalWriteOff');

            var $unapplied = parseFloat($('#amount').val().toNumber());
    		var $applied = 0.0;
    		var $writeoff = 0.0;

            // Loop through Paid Amount Element
            $('.payables').each(function () {
                let $idx = $(this).attr('index');
                let $value = $(this).text();

                let _unpaid = $('#unpaid\\['+$idx+'\\]');
                let _writeoff = $('#writeOff\\['+$idx+'\\]');
                let _paid = $('#paidAmount\\['+$idx+'\\]');
                
    			if(parseFloat(_writeoff.val().toNumber() + _unpaid.val().toNumber()) < 0)
    			{
    				alert("<spring:message code='payment.writeoff'/> ["+$value+"] <spring:message code='notif.greater'/> "+ _unpaid.val());
    				_writeoff.value = "0";
    			}
    			
    			if(_paid.val().toNumber() > _unpaid.val().toNumber()) {
    				_writeoff.val(_paid.val().toNumber() - _unpaid.val().toNumber());
    				_writeoff.readOnly = true;
    			}
    			else
    				_writeoff.readOnly = false;

    			if(_writeoff.val().toNumber() < 0 && (_writeoff.val().toNumber() + _unpaid.val().toNumber() < _paid.val().toNumber()))
    			{	
    				alert("<spring:message code='payment.paid.amount'/> + <spring:message code='payment.writeoff'/> ["+$value+"] <spring:message code='notif.greater'/> "+_unpaid.val());
    				_paid.val(_writeoff.val().toNumber() + _unpaid.val().toNumber());
    			}
    			
                $unapplied -= _paid.val().toNumber();
    			$writeoff += _writeoff.val().toNumber();
    			$applied += _paid.val().toNumber();
            });
    			
            totalUnapplied.value = $unapplied.numberFormat('#,##0.00');
    		totalApplied.value =  $applied.numberFormat('#,##0.00');
    		totalWriteOff.value =  $writeoff.numberFormat('#,##0.00');
        }
    }

    function clearLineItem() {
        $('#lineItemBody').empty();
        index = 0;
        updateDisplay();
    }

    // TODO Change to Invoice Popup
    function openInvoice(index) {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        if (!$('#supplier').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="supplier"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const supplierId = $('#supplier').val();
        const baseUrl = '<c:url value="/page/popupinvoiceverificationview.htm"/>';
        const params = {
            target: 'invoiceVerification[' + index + ']', // Id Dropdown (Select) element
            index: index,
            organization: orgId, // Org (Party)
            supplier: supplierId, // Supplier (Party)
            financialStatus: 'UNPAID', // Filter Unpaid Only
        };

        openpopup(buildUrl(baseUrl, params));
    }

    function openSupplier() {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
        const params = {
            target: 'supplier', // Id Dropdown (Select) element
            organization: orgId, // Org (PartyTo)
            fromRoleType: 5, // Supplier
            toRoleType: 4, // Customer
            relationshipType: 4 // Supplier Relationship
        };

        openpopup(buildUrl(baseUrl, params));
    }

    function checkDuplicate(element) {
        const isDuplicated = String.duplicate('payables'); // Check Duplicate for Class "payables"

        if (isDuplicated) {
            alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
            $(element).closest('tr').remove();
        }
    }
    
    $('#supplier').change(function(e)
    {
    	getVerifications();
       
    });
    
    function getVerifications()
    {
        clearLineItem(); // Clear the line item first before add & reset index to 0
        $org = $('#org').val();
        $supplier = $('#supplier').val();

        $.get("<c:url value='/page/popupinvoiceverificationviewjson.htm'/>",{organization:$org, supplier:$supplier},function(json)
        {
            $.each(json.verifications,function(idx,val)
            {
                addLineItem();

                 var _client = document.getElementById('invoiceVerification['+idx+']');
                _client.remove(_client.selectedIndex);

                var _opt = document.createElement('option');
                _opt.value = val.verId;
                _opt.text = val.verCode;

                _client.appendChild(_opt);
                _client.dispatchEvent(new Event('change'));

                var _unpaid = document.getElementById('unpaid['+idx+']');
                _unpaid.value = val.unpaid.numberFormat('#,##0.00');

                updateDisplay(); 
            });
        });
    }
    
    let index = 0; // For Line Item ID (Index)
    function addLineItem()
    {
    	 $tbody = $('#lineItem');
         $tr = $('<tr/>');

         $invoice = List.get('<select class="combobox-ext payables" onchange="checkDuplicate(this);updateDisplay();"/>','invoiceVerification['+index+']');
         $invoiceImg = List.img('<spring:message code="invoiceverification"/>', index, 'openInvoice("'+index+'")');

         $unpaid = List.get('<input type="text" class="input-number input-disabled" disabled size="20"/>','unpaid['+index+']', '0.00');

         $writeofftype = List.get('<select class="comboboxt-ext"/>', 'writeOffType['+index+']');
         $('#wtype').find('option').clone().appendTo($writeofftype);

         $writeoff = List.get('<input type="text" class="input-number" size="20" onchange="updateDisplay()"/>','writeOff['+index+']', '0.00');

         $paid = List.get('<input type="text" class="input-number paid" size="20" onchange="updateDisplay()"/>','paidAmount['+index+']', '0.00');

         $tr.append(List.col([$invoice, $invoiceImg]));
         $tr.append(List.col([$unpaid]));
         $tr.append(List.col([$writeofftype]));
         $tr.append(List.col([$writeoff]));
         $tr.append(List.col([$paid]));
         index++;
         
         $tbody.append($tr);

         $(".input-number").bind(inputFormat);
    }

</script>