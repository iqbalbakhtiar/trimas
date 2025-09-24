<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/prepaymentpreedit.htm?id=${prepayment_form.prepayment.id}'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="prepayment_form" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
                            <td width="1%" align="center">:</td>
                            <td width="64%"><input class="inputbox input-disabled" value="${prepayment_form.prepayment.code}" disabled/></td>
                            <td width="1%"><form:errors path="code"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="date" name="date" class="input-disabled" size="10" value="<fmt:formatDate value='${prepayment_form.prepayment.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="prepayment.apply.date"/></td>
                            <td width="1%" align="center">:</td>
                            <td><input id="realDate" name="realDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="organization"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
                                    <c:if test='${not empty prepayment_form.organization}'>
                                        <form:option value='${prepayment_form.organization.id}' label='${prepayment_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="customer"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="customer" path="customer" cssClass="combobox-ext input-disabled">
                                	<c:if test='${not empty prepayment_form.customer}'>
                                        <form:option value='${prepayment_form.customer.id}' label='${prepayment_form.customer.fullName}'/>
                                    </c:if>
                                </form:select>
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
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalApplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="payment.writeoff"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalWriteOff" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                                        </tr>
                                        <tr>
                                            <td width="80%" align="right"><spring:message code="receipt.total.remaining"/></td>
                                            <td width="20%">:&nbsp;&nbsp;<input id="totalUnapplied" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
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
		                                	<c:if test='${not empty prepayment_form.receiptInformation.paymentMethodType}'>
		                                        <form:option value='${prepayment_form.receiptInformation.paymentMethodType}' label='${prepayment_form.receiptInformation.paymentMethodType}' />
		                                    </c:if>
		                                </form:select>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td align="right"><spring:message code="receipt.reference"/> : </td>
		                            <td><form:input id='clearing' path='receiptInformation.reference' size='30' disabled='true'/></td>
		                        </tr>
		                        <tr>
		                            <td align="right"><spring:message code="bankaccount"/> : </td>
		                            <td>
		                                <form:select id='account' path='receiptInformation.bankAccount' cssClass="combobox">
		                                    <c:if test='${not empty prepayment_form.receiptInformation.bankAccount}'>
		                                        <form:option value='${prepayment_form.receiptInformation.bankAccount.id}' label='${prepayment_form.receiptInformation.bankAccount.bankName}' />
		                                    </c:if>
		                                </form:select>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
		                            <td><input id='accountName' value="${prepayment_form.receiptInformation.bankAccount.accountName}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
		                            <td><input id='bankName' value="${prepayment_form.receiptInformation.bankAccount.bankName}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
		                            <td><input id='accountNo' value="${prepayment_form.receiptInformation.bankAccount.accountNo}" disabled class='input-disabled' size="30"/></td>
		                        </tr>
	                        </table>
	                    </td>
	                    <td width="50%" align="left" valign="top">
	                        <table width="100%">
		                        <tr>
		                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="receipt.duedate"/> :</td>
		                            <td width="61%"><input id="duedate" value="<fmt:formatDate value='${prepayment_form.receiptInformation.dueDate}' pattern='dd-MM-yyyy'/>" name="receiptInformation.dueDate" class="datepicker" /></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
		                            <td>
		                                <input id='amount' name='receiptInformation.amount' value="<fmt:formatNumber value='${prepayment_form.receiptInformation.amount}' pattern='#,##0.00'/>" class='applied input-number' readonly="true"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.charge"/> : </td>
		                            <td>
		                                <input id='bankCharges' name='receiptInformation.bankCharges' value="<fmt:formatNumber value='${prepayment_form.receiptInformation.bankCharges}' pattern='#,##0.00'/>" class='applied input-number' readonly="true"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td align="right"><spring:message code="sirius.note"/> : </td>
		                            <td><form:textarea path='receiptInformation.note' cols='40' rows='6'/></td>
		                        </tr>
	                        </table>
	                    </td>
	                </tr>
                </table>
            </div>
            <div id="receiptApplication" dojoType="ContentPane" label="<spring:message code='receipt.application'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
				    <thead>
				        <tr>
				            <th width="10%"><spring:message code="billing"/></th>
				            <th width="5%"><spring:message code="billing.date"/></th>
				            <th width="10%"><spring:message code="billing.total"/></th>
				            <th width="16%"><spring:message code="payment.writeoff.type"/></th>
				            <th width="16%"><spring:message code="payment.writeoff"/></th>
				            <th width="60%"><spring:message code="receipt.paid"/></th>
				        </tr>
				    </thead>
				    <tbody id="lineItemBody">
				    </tbody>
				    <tfoot>
				        <tr>
				            <td colspan="2"><div style="text-align: right;font-weight: bold;"><spring:message code="sirius.total"/></div></td>
				            <td><input size="20" id='totalBilling' class="number-disabled" readonly value="0.00"/></td>
				            <td colspan="2">&nbsp;</td>
				            <td><input size='20' id='totalPaid' class="number-disabled" readonly value="0.00"/></td>
				        </tr>
				        <tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				    </tfoot>
				</table>
            </div>
        </div>
        <!-- PREVENTS NO SESSION -->
        <input type="hidden" value="${prepayment_form.createdBy}"/>
        <!-- PREVENTS NO SESSION -->
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
        
        getBillings();
        updateDisplay();

        $('#methodType').trigger('change');
    });

    function receiptTypeChange(element) {
        if($(element).val() != 'CASH')
        {
            $('#bankCharges').attr('class', 'input-number');
            $('#bankCharges').removeAttr('disabled');

            $('#clearing').removeAttr('class', 'input-disabled');
            $('#clearing').removeAttr('disabled');

            $('#duedate').removeClass('input-disabled');
            $('#duedate').removeAttr('disabled');

            $('#duedate').datepicker("enable"); // Show DatePicker Icon
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
            $('#duedate').datepicker("disable");
        }
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
        
        if ($("#amount").val().toNumber() < 0)
        {
        	alert('<spring:message code="receipt.amount"/> <spring:message code="notif.zero"/> !');
        	return false;
        }
        
        if ($("#totalAmount").val().toNumber() < $("#totalApplied").val().toNumber())
        {
        	alert('<spring:message code="receipt.applied.amount"/> <spring:message code="notif.greater"/> <spring:message code="receipt.total"/> !');
        	return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/prepaymentapply.htm'/>",
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
                        window.location="<c:url value='/page/prepaymentpreedit.htm?id='/>"+json.id;
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
        var tbl = document.getElementById("lineItemTable");
        if(tbl)
        {
            var amount = document.getElementById('amount');
            var billing = 0.00;
        	var totalUnapplied = document.getElementById('totalUnapplied');
        	var totalApplied = document.getElementById('totalApplied');
        	var totalWriteOff = document.getElementById('totalWriteOff');

            document.getElementById('totalAmount').value = parseFloat(amount.value.toNumber()-$('#bankCharges').val().toNumber()).numberFormat('#,##0.00');

            var $unapplied = parseFloat($('#amount').val().toNumber());
    		var $applied = 0.0;
    		var $writeoff = 0.0;

            $('.payables').each(function () {
                let $idx = $(this).attr('index');
                let $value = $(this).text();

                let _unpaid = $('#unpaid\\['+$idx+'\\]');
                let _writeoff = $('#writeOff\\['+$idx+'\\]');
                let _paid = $('#price\\['+$idx+'\\]');
                
    			if(parseFloat(_writeoff.val().toNumber() + _unpaid.val().toNumber()) < 0)
    			{
    				alert("<spring:message code='payment.writeoff'/> ["+$value+"] <spring:message code='notif.greater'/> "+ _unpaid.val());
    				_writeoff.value = "0.00";
    			}
    			
    			if(_paid.val().toNumber() > _unpaid.val().toNumber()) {
    				_writeoff.val((_paid.val().toNumber() - _unpaid.val().toNumber()).numberFormat('#,##0.00'));
    				_writeoff.readOnly = true;
    			}
    			else
    				_writeoff.readOnly = false;

    			if(_writeoff.val().toNumber() < 0 && (_writeoff.val().toNumber() + _unpaid.val().toNumber() < _paid.val().toNumber()))
    			{	
    				alert("<spring:message code='payment.paid.amount'/> + <spring:message code='payment.writeoff'/> ["+$value+"] <spring:message code='notif.greater'/> "+_unpaid.val());
    				_paid.val((_writeoff.val().toNumber() + _unpaid.val().toNumber()).numberFormat('#,##0.00'));
    			}
    			
                $unapplied -= _paid.val().toNumber();
    			$writeoff += _writeoff.val().toNumber();
    			$applied += _paid.val().toNumber();

                billing = billing + _unpaid.val().toNumber();
            });

            document.getElementById('billing').value = parseFloat(billing).numberFormat('#,##0.00');
            document.getElementById('totalBilling').value = parseFloat(billing).numberFormat('#,##0.00');
            document.getElementById('totalPaid').value = parseFloat($applied).numberFormat('#,##0.00');
            
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

    let index = 0; // For Line Item ID (Index)
    function addLineItem()
    {
        $tbody = $('#lineItemBody');
        $tr = $('<tr/>');

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

        $writeoffType = List.get('<select/>', 'writeOffType['+index+']');
        $option = List.get('<option>ADJUSTMENT</option>', '', 'ADJUSTMENT');
        $option.appendTo($writeoffType);
        $option = List.get('<option>BANKCHARGE</option>', '', 'BANKCHARGE');
        $option.appendTo($writeoffType);
        $option = List.get('<option>UNDERTABLE</option>', '', 'UNDERTABLE');
        $option.appendTo($writeoffType);

        $writeOff = List.get('<input size="10" class="input-decimal negative"/>', 'writeOff['+index+']', '0.00');
        $writeOff.change(function() {
            updateDisplay();
        });

        // Uncomment bellow if using checkbox
        // $tr.append(List.col([$cbox]));
        $tr.append(List.col([$invoice]));
        $tr.append(List.col([$date]));
        $tr.append(List.col([$unpaid]));
        $tr.append(List.col([$writeoffType]));
        $tr.append(List.col([$writeOff]));
        $tr.append(List.col([$paid]));

        $tbody.append($tr);
        index++;

        $(".input-decimal").bind(inputFormat);
    }
</script>