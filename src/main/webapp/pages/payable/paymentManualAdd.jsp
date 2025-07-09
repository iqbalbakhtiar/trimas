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
                            <a class="item-button-list" href="<c:url value='/page/paymentmanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.add}'>
                            	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute='paymentManual_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="53%" align="left" valign="top">
                                   	  	<table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> :</td>
                                            <td width="76%"><input size="20" value='<spring:message code="sirius.auto.generated"/>' class="input-disabled" disabled/></td>					
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                                            <td><input id="date" name="date" value="<fmt:formatDate value='${paymentManual_form.date}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
                                        </tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="organization"/> : </td>
                                            <td>
                                                <form:select id="org" path="organization" cssClass="combobox-ext">
                                                    <c:if test='${not empty paymentManual_form.organization}'>
                                                        <form:option value='${paymentManual_form.organization.id}' label='${paymentManual_form.organization.fullName}'/>
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
										<tr style="display: none;">
                                            <td nowrap="nowrap" align="right"><spring:message code="paymentreferencetype"/> :</td>
                                            <td>
												<select id="paymentRefType">
													<c:forEach items="${referenceTypes}" var="refType">
														<option value='${refType}'><spring:message code="paymentreferencetype.${refType.messageName}"/></option>
													</c:forEach>
												</select>
											</td>	
										</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="supplier"/> :</td>
                                            <td>
                                                <form:select id="supplier" path="supplier" cssClass="combobox-ext">
                                                    <c:if test='${not empty paymentManual_form.supplier}'>
                                                        <form:option value='${paymentManual_form.supplier.id}' label='${paymentManual_form.supplier.fullName}'/>
                                                    </c:if>
                                                </form:select>
                                                <a class="item-popup" onclick="openSupplier();" title='<spring:message code="supplier"/> / <spring:message code="customer"/>'/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="paymenttype"/> :</td>
                                            <td>
                                                <form:select id="paymentManualType" path="paymentManualType" cssClass="combobox-ext">
                                                </form:select>
                                                <a class="item-popup" onclick="openPaymentType();"  title="<spring:message code='paymenttype'/>" />
                                            </td>
                                        </tr>
                                        <%-- <tr id="rowReference" style="display: none;">
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                                            <td>
                                                <form:select id="salesMemoable" path="salesMemoable" cssClass="combobox-ext">
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
						                          		<input id='amount' name='paymentInformation.amount' class="input-currency" value="<fmt:formatNumber value='0.00' pattern=',##0.00'/>"/>                
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
						                                    <c:if test='${not empty paymentManual_form.paymentInformation.bankAccountTo}'>
						                                        <form:option value='${paymentManual_form.paymentInformation.bankAccountTo.id}' label='${paymentManual_form.paymentInformation.bankAccountTo.code}' />
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
					url:"<c:url value='/page/paymentmanualadd.htm'/>",
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
								window.location="<c:url value='/page/paymentmanualpreedit.htm?id='/>"+json.id;
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
			$('#supplier').empty();
		});

        $('.exchange').change(function(e){
           Currency.init();
        });

        $('.exchange').change();
        $('#methodType').trigger('change');
        $('#methodTypeTo').trigger('change');
		
		$('#paymentRefType').change(function()
		{
			checkReference();
		});

        $('#methodType').change(function(e){
            checkInfo('');
        });

        $('#methodTypeTo').change(function(e){
            checkInfo('To');
        });
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

		/* if(!$('#supplier').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="supplier"/> <spring:message code="notif.select2"/> !!!');
			return;
		} */

		if(!$('#paymentManualType').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="paymenttype"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		if(!$('#account').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="bankaccount"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		if(!$('#accountTo').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="bankaccount"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		var methodType = document.getElementById('methodType');
		if(methodType.value == 'CLEARING')
		{
			if(!$('#duedate').val())
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
        $('#supplier').empty();
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
	
	function openSupplier()
	{
		var referenceType = document.getElementById('paymentRefType').value;

		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		if(referenceType == 'CREDIT_MEMO_RETURN')
		{
			var fac = document.getElementById('facility');
			if(fac.value == '')
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="sirius.facility"/> <spring:message code="notif.select2"/> !!!');
				return;
			}

			openpopup("<c:url value='/page/popupcustomerview.htm?target=supplier&organization='/>"+org.value+"&facility="+fac.value);
		} else
			openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&organization='/>"+org.value);
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

	function openReference()
	{
		var cust = document.getElementById('supplier');
		if(cust.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="customer"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		var referenceType = document.getElementById('paymentRefType').value;
		if(referenceType == 'CREDIT_MEMO_RETURN')
			openpopup("<c:url value='/page/popupcreditmemoview.htm?target=salesMemoable&party='/>"+cust.value);
	}
	
	function checkReference()
	{
        $('#supplier').empty();
		$('#paymentManualType').empty();
		$('#salesMemoable').empty();
		$('#amount').val(0.00);
		$('#bankCharges').val(0.00);

		var referenceType = document.getElementById('paymentRefType').value;
		if(referenceType == 'CREDIT_MEMO_RETURN') {
			$('#rowReference').removeAttr('style');
			$('#amount').attr('class','number-disabled');
			$('#amount').attr('readonly','true');
		} else {
			$('#rowReference').attr('style','display:none;');
			$('#amount').attr('class','input-number');
			$('#amount').removeAttr('readonly');
		}
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
</script>