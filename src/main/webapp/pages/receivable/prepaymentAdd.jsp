<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/prepaymentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
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
                                    <c:if test='${not empty prepayment_form.organization}'>
                                        <form:option value='${prepayment_form.organization.id}' label='${prepayment_form.organization.fullName}'/>
                                    </c:if>
                                </form:select>
                                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="<spring:message code="organization"/>" />
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="customer"/></td>
                            <td width="1%" align="center">:</td>
                            <td>
                                <form:select id="customer" path="customer" cssClass="combobox-ext">
                                </form:select>
                                <a class="item-popup" onclick="openCustomer();" title="<spring:message code="customer"/>"/>
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
		                            <td><form:input id='clearing' path='receiptInformation.reference' size='30' disabled='true'/></td>
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
	                    </td>
	                    <td width="50%" align="left" valign="top">
	                        <table width="100%">
		                        <tr>
		                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="receipt.duedate"/> :</td>
		                            <td width="61%"><input id="duedate" name="receiptInformation.dueDate" class="datepicker" /></td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
		                            <td>
		                                <form:input id='amount' path='receiptInformation.amount' value="0.00" cssClass='applied input-currency' onchange="updateDisplay()"/>
		                                <input type="hidden" id="amountdef" value="0"/>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td nowrap="nowrap" align="right"><spring:message code="receipt.charge"/> : </td>
		                            <td>
		                                <form:input id='bankCharges' path='receiptInformation.bankCharges' value="0.00" cssClass='applied input-currency' onchange="updateDisplay()"/>
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
        </div>
    </sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        $('.item-button-save').click(function(){
			if (validateForm())
				save();
        });

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

        $('#duedate').val("");
        $("#account").html("");
        $("#accountName").val("");
        $("#bankName").val("");
        $("#bankBranch").val("");
        $("#accountNo").val("");
        $("#bankCharges").val("0.00");
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

    function openBankAccount() 
    {
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
        
        if ($("#amount").val().toNumber() < 1)
        {
        	alert('<spring:message code="receipt.amount"/> <spring:message code="notif.zero"/> !');
        	return false;
        }

        // Jika semua validasi lolos
        return true;
    }

    function save() {
        $.ajax({
            url:"<c:url value='/page/prepaymentadd.htm'/>",
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
</script>