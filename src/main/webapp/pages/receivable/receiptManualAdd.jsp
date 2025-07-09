<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
</head>
<body>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">
	<%@ include file="/common/sirius-menu.jsp"%>
	<div id="se-navigator">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="60%">${breadcrumb}</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<h1 class="page-title">${pageTitle}</h1>
						<div class="toolbar">
                            <a class="item-button-list" href="<c:url value='/page/receiptmanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.add}'>
                            	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute='receiptManual_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="53%" align="left" valign="top">
                                   	  	<table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> :</td>
                                            <td width="76%"><input size="20" value='<spring:message code="sirius.auto.generated"/>' class="input-disabled" disabled/></td>					
                                        </tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="organization"/> : </td>
                                            <td>
                                                <form:select id="org" path="organization" cssClass="combobox-ext">
                                                    <c:if test='${not empty receiptManual_form.organization}'>
                                                        <form:option value='${receiptManual_form.organization.id}' label='${receiptManual_form.organization.fullName}'/>
                                                    </c:if>
                                                </form:select>
                                                <a class="item-popup" onclick="opencompany();" title='<spring:message code="organization.structure"/>'/>
                                            </td>					
                                        </tr>
                                        <tr style="display: none;">
											<td align="right"><spring:message code="sirius.facility"/> :</td>
											<td>
												<form:select id="facility" path="facility" cssClass="combobox-ext">
	                                            </form:select>
												<a class="item-popup" onclick="openfacility();" title="<spring:message code='sirius.facility'/>"/>
											</td>
										</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                                            <td><input id="date" name="date" value="<fmt:formatDate value='${receiptManual_form.date}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
                                        </tr>
										<tr style="display: none;">
                                            <td nowrap="nowrap" align="right"><spring:message code="receiptreferencetype"/> :</td>
                                            <td>
												<select id="paymentRefType">
													<c:forEach items="${referenceTypes}" var="refType">
														<option value='${refType}'><spring:message code="receiptreferencetype.${refType.messageName}"/></option>
													</c:forEach>
												</select>
											</td>	
										</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="customer"/> / <spring:message code="supplier"/> :</td>
                                            <td>
                                                <form:select id="customer" path="customer" cssClass="combobox-ext">
                                                    <c:if test='${not empty receiptManual_form.customer}'>
                                                        <form:option value='${receiptManual_form.customer.id}' label='${receiptManual_form.customer.fullName}'/>
                                                    </c:if>
                                                </form:select>
                                                <a class="item-popup" onclick="openCustomer();" title='<spring:message code="customer"/> / <spring:message code="customer"/>'/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="receipttype"/> :</td>
                                            <td>
                                                <form:select id="receiptManualType" path="receiptManualType" cssClass="combobox-ext">
                                                </form:select>
                                                <a class="item-popup" onclick="openPaymentType();"  title="<spring:message code='paymenttype'/>" />
                                            </td>
                                        </tr>
                                        <%-- <tr id="rowReference" style="display: none;">
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                                            <td>
                                                <form:select id="purchaseMemoable" path="purchaseMemoable" cssClass="combobox-ext">
                                                </form:select>
                                                <a class="item-popup" onclick="openReference();"  title="<spring:message code='paymenttype'/>" />
                                            </td>
                                        </tr> --%>
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
                                            <td width="24%" align="right"><spring:message code="sirius.note"/> : </td>
                                            <td><form:textarea path='note' cols='45' rows='6'/></td>					
                                        </tr>
                                        </table>
                                    </td>
                                </tr>
                                </table>
                                </br>
                                <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 300px;">
									<div id="info" dojoType="ContentPane" label='<spring:message code="receipt.information"/>' class="tab-pages" refreshOnShow="true">
										<table width="100%">
										<tr>
											<td width="53%" align="left" valign="top">
												<table width="100%">
						                    	<tr>
						                            <td width="27%" align="right"><spring:message code="receipt.payment.type"/> : </td>
						       	  	  		  		<td width="73%">
						       	  	  		  			<form:select id='methodType' path='receiptInformation.paymentMethodType'>
                                                            <c:forEach items='${types}' var='type' varStatus="status">
                                                                <spring:message var="src" code="receipt.${type.message}"/>
                                                                <form:option value='${type}' label='${fn:toUpperCase(src)}'/>
                                                            </c:forEach>
                                                    	</form:select>
						                       	  	</td>
						                        </tr>
						                        <tr>
						                            <td align="right">Transfer/Clearing No : </td>
						                            <td><input id='clearing' name='receiptInformation.reference' disabled='true' class='input-disabled' size="30"/></td>
						                        </tr>
						                        <tr>
						                            <td align="right"><spring:message code="bankaccount"/> : </td>
						                            <td>
						                                <form:select id='account' path='receiptInformation.bankAccount' cssClass='combobox'>
						                                    <c:if test='${not empty receipt_add.receiptInformation.bankAccount}'>
						                                        <form:option value='${receipt_add.receiptInformation.bankAccount.id}' label='${receipt_add.receiptInformation.bankAccount.code}' />
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
												</table>
											</td>
											<td width="47%" align="left" valign="top">
											<table width="100%">
												<tr>
						                            <td width="39%" align="right" nowrap="nowrap"><spring:message code="payment.duedate"/> :</td>
						                            <td width="61%">
						                                <input type="text" id="dueDate" name="receiptInformation.dueDate" class="datepicker"/>
						                            </td>
						                    	</tr>
						                        <tr>
						                          	<td nowrap="nowrap" align="right"><spring:message code="receipt.amount"/> : </td>
						                            <td>
						                          		<input id='amount' name='receiptInformation.amount' class="input-currency" value="<fmt:formatNumber value='0.00' pattern=',##0.00'/>"/>                
						                            </td>
						                        </tr>
						                        <tr>
						                          	<td nowrap="nowrap" align="right"><spring:message code="payment.bankcharge"/> : </td>
						                            <td>
						                            	<input id='bankCharges' name='receiptInformation.bankCharges' class="input-currency" value="<fmt:formatNumber value='0.00' pattern=',##0.00'/>"/>
						                            </td>
						                        </tr>
						                        <tr>
						                            <td align="right"><spring:message code="sirius.note"/> :&nbsp; </td>
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
					</div>
				</div>
			</div>
		</div>
	</div>
  	<%@ include file="/common/sirius-footer.jsp"%>
</div>
</body>
</html>
<script type="text/javascript" language="javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="payment.manual"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(e){
			if(validation())
			{
				$.ajax({
					url:"<c:url value='/page/receiptmanualadd.htm'/>",
					data:$('#addForm').serialize(),
					method : 'POST',
					dataType : 'json',
					beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$dialog.dialog('close');
								window.location="<c:url value='/page/receiptmanualpreedit.htm?id='/>"+json.id;
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				});
			}
		});
		
		$('#facility').change(function()
		{
			$('#customer').empty();
		});

        $('.exchange').change(function(e){
           Currency.init();
        });

        $('.exchange').change();
        $('#methodType').trigger('change');
		
		$('#paymentRefType').change(function()
		{
			checkReference();
		});

		$('#methodType').trigger('change');
	});
	
	function validation()
	{
		if(!$('#org').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		/* if(!$('#facility').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="sirius.facility"/> <spring:message code="notif.select2"/> !!!');
			return;
		} */

		/* if(!$('#customer').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="customer"/> <spring:message code="notif.select2"/> !!!');
			return;
		} */

		if(!$('#receiptManualType').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="receipttype"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		if(!$('#account').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="bankaccount"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		var methodType = document.getElementById('methodType');
		if(methodType.value == 'CLEARING')
		{
			if(!$('#dueDate').val())
			{
				alert('<spring:message code="payment.duedate"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
		}
		
		if(!$('#amount').val() || $('#amount').val() <= 0)
		{
			alert('<spring:message code="payment.amount"/> <spring:message code="notif.empty"/> !!!');
			return;
		}

		return true;
	}
		
	function opencompany()
	{
		openpopup("<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>");
        $('#customer').empty();
	}
	
	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&active=true&organization='/>"+org.value);
	}
	
	function openCustomer()
	{
		var referenceType = document.getElementById('paymentRefType').value;

		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		if(referenceType == 'DEBIT_MEMO_RETURN')
			openpopup("<c:url value='/page/popupsupplierview.htm?target=customer&organization='/>"+org.value);
		else {
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

	function openPaymentType()
	{
		var referenceType = document.getElementById('paymentRefType');
		openpopup("<c:url value='/page/popupreceiptmanualtypeview.htm?target=receiptManualType&status=true&referenceType='/>"+referenceType.value);
	}

	function openReference()
	{
		var cust = document.getElementById('customer');
		if(cust.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="customer"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		var referenceType = document.getElementById('paymentRefType').value;
		if(referenceType == 'DEBIT_MEMO_RETURN')
			openpopup("<c:url value='/page/popupdebitmemoview.htm?target=salesMemoable&party='/>"+cust.value);
	}
	
	function checkReference()
	{
        $('#customer').empty();
		$('#receiptManualType').empty();
		$('#salesMemoable').empty();
		$('#amount').val(0.00);
		$('#bankCharges').val(0.00);

		var referenceType = document.getElementById('paymentRefType').value;
		if(referenceType == 'DEBIT_MEMO_RETURN') {
			$('#rowReference').removeAttr('style');
			$('#amount').attr('class','number-disabled');
			$('#amount').attr('readonly','true');
		} else {
			$('#rowReference').attr('style','display:none;');
			$('#amount').attr('class','input-number');
			$('#amount').removeAttr('readonly');
		}
	}
	
/* 	function checkInfo()
	{
		$class = 'input-disabled';
        $class2 = 'input-disabled';
        $status = true;

        if($('#methodType').val() != 'CASH')
        {
            $class = 'input-currency';
         	$class2 = '';
            $status = false;
        }

        $('#bankCharges').attr('class', $class);
        $('#bankCharges').attr('disabled', $status);
        
        $('#ref').attr('class', $class2);
        $('#ref').attr('disabled', $status);

        $('#clearing').attr('class', $class);
        $('#clearing').attr('disabled', $status);

        $('#duedate').attr('disabled', $status);

        $("#account").html("");   
        $("#accountName").val("");  
        $("#bankName").val(""); 
        $("#bankBranch").val("");   
        $("#accountNo").val("");
        $("#bankCharges").val("0");
        $('#ref').val("");
        
	    $(".input-decimal").bind(inputFormat);
	} */
	
    $('#methodType').change(function(e){

        if($(this).val() != 'CASH')
        {
            $('#bankCharges').attr('class', 'input-currency');
            $('#bankCharges').removeAttr('disabled');

            $('#clearing').attr('class', 'inputbox');
            $('#clearing').removeAttr('disabled');

            $('#duedate').removeClass('input-disabled');
            $('#duedate').removeAttr('disabled');
        }
        else
        {
            $('#bankCharges').attr('class', 'number-disabled');
            $('#bankCharges').attr('disabled', 'true');

            $('#clearing').attr('class', 'input-disabled');
            $('#clearing').attr('disabled', 'true');

            $('#duedate').addClass('input-disabled');
            $('#duedate').attr('disabled', 'true');
        }

        $('#duedate').val("");
        $("#account").html("");   
        $("#accountName").val("");  
        $("#bankName").val(""); 
        $("#bankBranch").val("");   
        $("#accountNo").val("");
        $("#bankCharges").val("0.00");
    });
</script>