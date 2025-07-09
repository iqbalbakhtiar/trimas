<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/paymentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="payment_add" enctype="multipart/form-data">
        <table width="100%" border="0">
            <tr>
                <td width="60%">
                    <table style="border:none" width="100%">
                        <tr>
                            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
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
                            <td width="24%" align="right"><spring:message code="currencymanagement"/> : </td>
              				<td>
                                <form:select id='currency' path='currency'>
                                    <c:forEach items='${currencys}' var='currency'>
                                        <form:option value='${currency.id}' label='${currency.symbol}'/>
                                    </c:forEach>
                                </form:select>
                                <form:input id='exchange' path='exchange' type="hidden"/>
                                <%-- <form:select id='type' path='exchangeType'>
                                    <form:option value='SPOT' label='SPOT'/>
                                    <form:option value='MIDDLE' label='MIDDLE'/>
                                    <form:option value='TAX' label='TAX'/>
                                </form:select> --%>
                                <form:input id="trxrate" path='rate' cssClass="input-number" value="1" size="10"/>
                                <input id="default" type="hidden" value="${defaultCurrency.id}"/>
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
            <div id="info" dojoType="ContentPane" label='<spring:message code="payment.information"/>' class="tab-pages" refreshOnShow="true">
				<table width="100%">
				<tr>
					<td width="53%" align="left" valign="top">
						<table width="100%">
	                   	<tr>
	                   		<td width="27%" align="right"><spring:message code="payment.payment.type"/> : </td>
      	  	  		  		<td width="73%">
      	  	  		  			<form:select id='methodType' path='paymentInformation.paymentMethodType'>
                                	<form:option value='CASH' label='CASH' selected="true"/>
                                	<form:option value='TRANSFER' label='TRANSFER'/>
                                	<%-- <form:option value='CLEARING' label='CLEARING'/> --%>
                           		</form:select>
                      	  	</td>
                       	</tr>
                       	<tr>
                           	<td align="right">Transfer/Clearing No : </td>
                           	<td><input id='ref' name='paymentInformation.reference' disabled='true' class='input-disabled' size="30"/></td>
                       	</tr>
                       	<tr>
                           	<td align="right"><spring:message code="bankaccount"/> : </td>
                           	<td>
                               	<form:select id='account' path='paymentInformation.bankAccount' cssClass='combobox'>
                                   <c:if test='${not empty paymentManual_form.paymentInformation.bankAccount}'>
                                       <form:option value='${paymentManual_form.paymentInformation.bankAccount.id}' label='${paymentManual_form.paymentInformation.bankAccount.code}' />
                                   </c:if>
                               	</form:select>
                              	<a class="item-popup" onclick="openaccount();" title="Bank Account" />
                           	</td>
                       	</tr>
                       	<tr>
                           	<td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                           	<td><input id='accountName' disabled='true' class='input-disabled' size="30"/></td>
                       	</tr>
                       	<tr>
                           	<td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                           	<td><input id='bankName' disabled='true' class='input-disabled' size="30"/></td>
                       	</tr>
                       	<tr>
                           	<td nowrap="nowrap" align="right"><spring:message code="bankaccount.branch"/> : </td>
                           	<td><input id='bankBranch' disabled='true' class='input-disabled' size="30"/></td>
                       	</tr>
                       	<tr>
                           	<td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                           	<td><input id='accountNo' disabled='true' class='input-disabled' size="30"/></td>
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
					</td>
					<td width="47%" align="left" valign="top">
					<table width="100%">
						<tr>
	                           <td width="39%" align="right" nowrap="nowrap"><spring:message code="payment.duedate"/> :</td>
	                           <td width="61%">
	                               <input type="text" id="dueDate" name="paymentInformation.dueDate" class="datepicker"/>
	                           </td>
	                   	</tr>
	                       <tr>
	                         	<td nowrap="nowrap" align="right"><spring:message code="payment.amount"/> : </td>
	                           <td>
	                         		<input id='amount' name='paymentInformation.amount' class="input-currency" value="<fmt:formatNumber value='0.00' pattern=',##0.00'/>" onchange="updateDisplay()"/>                
	                           </td>
	                       </tr>
	                       <tr>
	                         	<td nowrap="nowrap" align="right"><spring:message code="payment.bankcharge"/> : </td>
	                           <td><input id='bankCharges' name='paymentInformation.bankCharges' class="number-disabled" readonly="true" value="<fmt:formatNumber value='0' pattern=',##0'/>"/></td>
	                       </tr>
	                       <tr>
	                           <td align="right"><spring:message code="sirius.note"/> :&nbsp; </td>
	                           <td><form:textarea path='paymentInformation.note' cols='40' rows='6'/></td>					
	                       </tr>
						</table>
					</td>
				</tr>
				</table>
			</div>
			<div id="infoTo" dojoType="ContentPane" label='<spring:message code="payment.information"/> <spring:message code="sirius.to"/>' class="tab-pages" refreshOnShow="true">
				<table width="100%">
				<tr>
					<td width="53%" align="left" valign="top">
						<table width="100%">
                    	<tr>
                            <td width="27%" align="right"><spring:message code="payment.payment.type"/> : </td>
       	  	  		  		<td width="73%">
       	  	  		  			<form:select id='methodTypeTo' path='paymentInformation.paymentMethodTypeTo'>
       	  	  		  				<form:option value='CASH' label='CASH' selected="true"/>
       	  	  		  				<form:option value='TRANSFER' label='TRANSFER'/>
       	  	  		  				<%-- <form:option value='CLEARING' label='CLEARING'/> --%>
                            	</form:select>
                       	  	</td>
                        </tr>
                        <tr>
                            <td align="right">Transfer/Clearing No : </td>
                            <td><input id='refTo' name='paymentInformation.referenceTo' disabled='true' class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="bankaccount"/> : </td>
                            <td>
                                <form:select id='accountTo' path='paymentInformation.bankAccountTo' cssClass='combobox'>
                                    <c:if test='${not empty payment_add.paymentInformation.bankAccountTo}'>
                                        <form:option value='${payment_add.paymentInformation.bankAccountTo.id}' label='${payment_add.paymentInformation.bankAccountTo.code}' />
                                    </c:if>
                                </form:select>
                               	<a class="item-popup" onclick="openaccountto();" title="Bank Account" />
                            </td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountname"/> : </td>
                            <td><input id='accountNameTo' disabled='true' class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.bankname"/> : </td>
                            <td><input id='bankNameTo' disabled='true' class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.branch"/> : </td>
                            <td><input id='bankBranchTo' disabled='true' class='input-disabled' size="30"/></td>
                        </tr>
                        <tr>
                            <td nowrap="nowrap" align="right"><spring:message code="bankaccount.accountno"/> : </td>
                            <td><input id='accountNoTo' disabled='true' class='input-disabled' size="30"/></td>
                        </tr>
						</table>
					</td>
					<td width="47%" align="left" valign="top">
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
        
        $('#supplier').change(function() {
        	$("#lineItem tr").remove();
        	getVerifications();
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
        $('#methodTypeTo').trigger('change');

        $('#methodType').change(function(e){
            checkInfo('');
        });

        $('#methodTypeTo').change(function(e){
            checkInfo('To');
        });

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

	function openaccount()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
	
		var methodType = document.getElementById('methodType');
		var method = 'CASH';
		
		if(methodType.value != 'CASH')
			method = 'BANK';
		
		openpopup("<c:url value='/page/popupbankaccountview.htm?target=account&organization='/>"+org.value+'&type='+method);
	}

	function openaccountto()
	{
		var supplier = document.getElementById('supplier');
		if(supplier.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="supplier"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
	
		var methodType = document.getElementById('methodTypeTo');
		var method = 'CASH';
		
		if(methodType.value != 'CASH')
			method = 'BANK';
		
		openpopup("<c:url value='/page/popupbankaccountview.htm?target=accountTo&organization='/>"+supplier.value+'&type='+method);
	}

	function openPaymentType()
	{
		var referenceType = document.getElementById('paymentRefType');
		openpopup("<c:url value='/page/popuppaymentmanualtypeview.htm?target=paymentManualType&status=true&referenceType='/>"+referenceType.value);
	}
	
	function checkInfo(info)
	{
		$class = 'input-disabled';
        $class2 = 'input-disabled';
        $status = true;

        if($('#methodType'+info).val() != 'CASH')
        {
            $class = 'input-currency';
         	$class2 = '';
            $status = false;
        }

        if(info == '') {
	        $('#bankCharges').attr('class', $class);
	        $('#bankCharges').attr('disabled', $status);
	
	        $('#duedate').attr('disabled', $status);
	        $("#bankCharges").val("0");
        }
        
        $('#ref'+info).attr('class', $class2);
        $('#ref'+info).attr('disabled', $status);

        $('#clearing'+info).attr('class', $class);
        $('#clearing'+info).attr('disabled', $status);

        $("#account"+info).html("");   
        $("#accountName"+info).val("");  
        $("#bankName"+info).val(""); 
        $("#bankBranch"+info).val("");   
        $("#accountNo"+info).val("");
        $('#ref'+info).val("");
        
	    $(".input-decimal").bind(inputFormat);
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
                        window.location="<c:url value='/page/paymentpreedit.htm?id='/>"+json.id;
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
    
    function getVerifications()
    {
        clearLineItem(); // Clear the line item first before add & reset index to 0
        $org = $('#org').val();
        $supplier = $('#supplier').val();

        $.get("<c:url value='/page/popupinvoiceverificationviewjson.htm'/>",{organization:$org, supplier:$supplier, financialStatus:'UNPAID'},function(json)
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

         $unpaid = List.get('<input type="text" class="input-decimal input-disabled" disabled size="20"/>','unpaid['+index+']', '0.00');

         $writeofftype = List.get('<select class="comboboxt-ext"/>', 'writeOffType['+index+']');
         $('#wtype').find('option').clone().appendTo($writeofftype);

         $writeoff = List.get('<input type="text" class="input-decimal" size="20" onchange="updateDisplay()"/>','writeOff['+index+']', '0.00');

         $paid = List.get('<input type="text" class="input-decimal paid" size="20" onchange="updateDisplay()"/>','paidAmount['+index+']', '0.00');

         $tr.append(List.col([$invoice, $invoiceImg]));
         $tr.append(List.col([$unpaid]));
         $tr.append(List.col([$writeofftype]));
         $tr.append(List.col([$writeoff]));
         $tr.append(List.col([$paid]));
         
         $tbody.append($tr);
         index++;

         $(".input-number").bind(inputFormat);
    }

</script>