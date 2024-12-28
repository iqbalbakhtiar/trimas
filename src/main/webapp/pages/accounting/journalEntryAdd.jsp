<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp" %>
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
							<a class="item-button-list" href="<c:url value='/page/journalentryview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save btn_add" ><span><spring:message code="sirius.save"/></span></a>
							<!-- <a class="item-button-save-new btn_add" ><span>Save &amp; New</span></a> -->
					  	</div>

						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="journalEntry_add">
							<table width="100%" border="0" cellpadding="0" cellspacing="20" id="tableRecord">
							<tr valign="top">
								<td width="50%">
									<table width="100%" style="border:none">
									<tr>
										<td width="32%" nowrap="nowrap" align="right"><spring:message code="journalentry.code"/> :</td>
										<td width="68%"><input value="Auto Number" size="30" disabled class="input-disabled"/><input name="flag" type="hidden" value="${flag}" size="4"/></td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.name"/> :</td>
										<td><form:input id="name" path="name" size="55"/> </td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.date"/> :</td>
										<td><input id="date" name="entryDate" value="<fmt:formatDate value='${journalEntry_add.entryDate}' pattern='dd-MM-yyyy'/>" class="datepicker rate"/></td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.type"/> :</td>
										<td>
											<form:select path="entryType">
												<form:option value="ENTRY">ENTRY</form:option>
                                                <form:option value="CORRECTION">CORRECTION</form:option>
                                                <form:option value="ADJUSTMENT">ADJUSTMENT</form:option>
											</form:select>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.currency"/> :</td>
										<td>
											<form:select id="currency" path="currency" class="rate">
                                            	<form:options items='${currencys}' itemLabel='symbol' itemValue='id'/>
											</form:select>
                                            <form:select id='type' path="exchangeType" class="rate">
                                            	<form:option value='SPOT'>SPOT</form:option>
                                                <form:option value='MIDDLE'>MIDDLE</form:option>
                                                <form:option value='TAX'>TAX</form:option>
											</form:select>
											<input id="trxrate" size="10" class="input-disabled" disabled/>
             		   						<input id="default" type="hidden" value="${defaultCurrency.id}"/>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.organization"/> :</td>
										<td>
											<form:select id="org" path="organization" cssClass="combobox-ext">
                                            	<c:if test='${not empty organization}'>
                                                	<form:option value='${organization.id}' label='${organization.fullName}' />
                                                </c:if>
											</form:select>
											<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="coa"/> :</td>
										<td>
											<select id="coa" class="combobox-ext">
                                            	<c:if test='${not empty accountingSchema}'>
                                                	<option value='${accountingSchema.id}'>${accountingSchema.code} ${accountingSchema.name}</option>
                                                </c:if>
											</select>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.note"/> :</td>
										<td><form:textarea path="note" cols="55" rows="7"/></td>
									</tr>
									</table>
								</td>
								<td width="50%">
									<table width="100%">
                                    <%-- <tr>
                                    	<td>
                                        	<fieldset>
												<legend><strong>Index</strong></legend>
												<table width="100%" border="0" cellpadding="0">
												<c:forEach items='${indexTypes}' var='type'>
                                                <tr>
													<td align="right">${type.name}</td>
													<td align="center">:</td>
													<td>
                                                    	<input id="hidden-id[${type.id}]" type="hidden" name="indexs" value="${type.id}"/>
                                                        <input id="text-id[${type.id}]" name="text" size="15"/>
                                                    </td>
												</tr>
                                                </c:forEach>
												</table>
											</fieldset>
                                        </td>
                                    </tr> --%>
                                    <tr>
                                    	<td>
                                        	<fieldset>
												<legend><strong><spring:message code="journalentry.entrybalance"/></strong></legend>
												<table width="100%" border="0" cellpadding="0" cellspacing="1">
												<tr>
													<td width="30%">&nbsp;</td>
													<td width="35%" align="center" bgcolor="#B7C9E3"><spring:message code="journaldetail.amount"/></td>
													<td width="35%" align="center" bgcolor="#B7C9E3"><spring:message code="journaldetail.amount"/> (Rp)</td>
												</tr>
												<tr>
													<td align="center"><strong><spring:message code="journalentry.debet"/></strong></td>
													<td><input value="0" id="txtBalanceDebet" size="25" disabled class="number-disabled"/></td>
													<td><input value="0" id="txtBalanceDebetRp" size="25" disabled class="number-disabled"/></td>
												</tr>
												<tr>
													<td align="center"><strong><spring:message code="journalentry.credit"/></strong></td>
													<td><input value="0" id="txtBalanceCredit" size="25" disabled class="number-disabled"/></td>
													<td><input value="0" id="txtBalanceCreditRp" size="25" disabled class="number-disabled"/></td>
												</tr>
												</table>
											</fieldset>
                                        </td>
                                    </tr>
                                    </table>
								</td>
								</tr>
								</table>

								<div class="toolbar-clean">
									<div class="item-navigator">&nbsp;</div>
									<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
									<a class="item-button-delete" href="javascript:deleteRow()"><span><spring:message code="sirius.row.delete"/></span></a>
						  		</div>
								<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
								<thead>
                                <tr>
								  	<th width="1%"><input type="checkbox" name="checkMaster" id="checkMaster" onclick="clickAll();"/></th>
									<th width="10%"><spring:message code="journaldetail.code"/></th>
                                    <th colspan="2" width="12%"><spring:message code="journaldetail.account"/></th>
								  	<th width="10%"><spring:message code="journaldetail.amount"/></th>
								  	<th width="10%"><spring:message code="journaldetail.amount"/> (Rp)</th>
                                    <th width="5%"><spring:message code="journaldetail.type"/></th>
									<th width="55%"><spring:message code="journaldetail.note"/></th>
								</tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr class="end-table">
									<td colspan="8"><div class="toolbar-clean" style="padding-left:45%"></div></td>
                                </tr>
                                </tfoot>
								</table>
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
<script src="<c:url value='/js/currency.js'/>"></script>
<script type="text/javascript">
	$(function(){
		Currency.init();

		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="journalentry"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

		$('.btn_add').click(function(e)
    	{
			$debet = $('#txtBalanceDebet').val();
			$credit = $('#txtBalanceCredit').val();

			if(!$('#name').val())
			{
				alert('<spring:message code="journalentry.name"/> <spring:message code="notif.empty"/> !!!');
				return;
			}

			if(!$('#date').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="journalentry.date"/> <spring:message code="notif.select2"/> !!!');
				return;
			}

			if(!$('#org').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="journalentry.organization"/> <spring:message code="notif.select2"/> !!!');
				return;
			}

			if(!$('#coa').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="coa"/> <spring:message code="notif.select2"/> !!!');
				return;
			}

			if(!$('#trxrate').val())
			{
				alert('<spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.empty"/> !!!');
				return;
			}

			if($debet.toNumber() == 0 || $credit.toNumber() == 0 || $debet != $credit)
			{
				alert('<spring:message code="journalentry.amount"/> <spring:message code="notif.balance"/> !!!');
				return;
			}

			$send = true;
			$date = $('#date').val();

			if($date.substring(3,$date.length) != "<fmt:formatDate value='${now}' pattern='MM-yyyy'/>")
				$send = confirm('<spring:message code="journalentry.date"/> <spring:message code="notif.currentmonth"/>, <spring:message code="notif.continue"/> ?');

			if($send)
			{
				$.ajax({
					url:"<c:url value='/page/journalentryadd.htm'/>",
					data:$('#addForm').serialize(),
					type : 'POST',
					dataType : 'json',
					beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$dialog.dialog('close');

								//if($(e.target).html() == 'Save')
									window.location="<c:url value='/page/journalentryview.htm'/>";
								//else
									//window.location="<c:url value='/page/journalentrypreadd.htm'/>";
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				});
			}
		});

		$(".rate").change(function(){
			Currency.init();

			var trxRate = $('#trxrate');

			if(trxRate == null || trxRate.val() == '')
			{
				alert('<spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.notexist"/>, <spring:message code="notif.input"/> <spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.select2"/>!');
			}

		});
	});

	function amountView()
	{
		var tbl = document.getElementById("lineItemTable");
		if(tbl)
		{
			var _credit = document.getElementById('txtBalanceCredit');
			var _debet = document.getElementById('txtBalanceDebet');
			var _creditRp = document.getElementById('txtBalanceCreditRp');
			var _debetRp = document.getElementById('txtBalanceDebetRp');

			var rate = parseFloat(document.getElementById('trxrate').value.replace(/,/g,''));

			var debet = 0.00;
			var credit = 0.00;

			var t_section = tbl.tBodies[0];
			var len = t_section.rows.length;

			for(var idx=0;idx<len;idx++)
			{
				var _amnt = t_section.rows[idx].cells[4].firstChild;
				var _amntRp = t_section.rows[idx].cells[5].firstChild;
				var _type = t_section.rows[idx].cells[6].firstChild;

				if(_amnt && _type && _amnt.value && _type.value)
				{
					if(_type.value == 'CREDIT')
						credit = credit + parseFloat(_amnt.value);
					else
						debet = debet + parseFloat(_amnt.value);
				}

				_amntRp.value = (parseFloat(_amnt.value) * rate).numberFormat('#,#.00');
			}

			if(_credit && _debet)
			{
				_credit.value = credit.numberFormat('#,#.00');
				_debet.value = debet.numberFormat('#,#.00');

				_creditRp.value = (credit * rate).numberFormat('#,#.00');
				_debetRp.value = (debet * rate).numberFormat('#,#.00');
			}
		}
	}

	function openvehicle(target)
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="journalentry.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		openpopup("<c:url value='/page/popupvehicleview.htm?organization='/>"+org.value+"&target="+target);
	}

	var tbl = dojo.byId("lineItemTable");

	var index = 0;

	var t_section = tbl.tBodies[0];
	if(t_section)
	{
		if(t_section.rows.length > 0 )
			index = t_section.rows.length+1;
	}

	function addLineItem()
	{
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				var row = t_section.insertRow(t_section.rows.length);

				row.insertCell(0).appendChild(genCbx(index));
				row.insertCell(1).appendChild(genCode(index));
				row.insertCell(2).appendChild(genAcc(index));
				row.insertCell(3).appendChild(genPopup(index));
				row.insertCell(4).appendChild(genAmt(index));
				row.insertCell(5).appendChild(genAmtRp(index));
				row.insertCell(6).appendChild(genType(index));
				row.insertCell(7).appendChild(genNote(index));

				index++;
			}
		}
	}

	function genCbx(index)
	{
		var check = document.createElement("input");
		check.type = "checkbox";
		check.name = "check";
		check.id = "check";

		return check;
	}

	function genCode(index)
	{
		$code = $('<input/>').attr('id','code['+index+']').attr('size','20').change(function(e){
			if(e.target.value)
			{
				$target = $(e.target);

				$.ajax({
					url:"<c:url value='/page/popupglaccountjsonview.htm'/>",
					data:{code:e.target.value},
					method : 'GET',
					dataType : 'json',
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$account = $(document.getElementById('glaccounts['+index+']'));
								$account.empty();

								$('<option/>').val(json.account.id).html(json.account.name).appendTo($account);
							}
						}
					}
				});
			}
		});

		return $code.get(0);
	}

	function genAcc(index)
	{
		var accounts = document.createElement("select");
		accounts.name="accounts";
		accounts.id="glaccounts["+index+"]";
		accounts.setAttribute('class','combobox-ext');

		return accounts;
	}

	function popup(target,index)
	{
		var coa = document.getElementById('coa');
		if(coa.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="coa"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		openpopup("<c:url value='/page/popupglaccountview.htm?split=true&coa='/>"+coa.value+"&target="+target+"&index="+index);
	}

	function openperiod()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="journalentry.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		url = "<c:url value='/page/popupaccountingperiodview.htm?target=period&openonly=true&organization='/>"+org.value;

		var date = document.getElementById('date');
		if(date && date.firstChild.value != '')
		{
			url = url+"&date="+date.firstChild.value;
		}

		openpopup(url);
	}

	function openschema()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="journalentry.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}

		openpopup("<c:url value='/page/popupjournalschemaview.htm?target=schema&organization='/>"+org.value);
	}

	function genPopup(index)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.onclick = function()
		{
			popup("glaccounts["+index+"]",index);
		}
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='GL Account';

		return _popup;
	}

	function genAmt(index)
	{
		var amounts = document.createElement("input");
		amounts.type = "text";
		amounts.name = "amounts";
		amounts.id = "amounts["+index+"]";
		amounts.setAttribute('size',"20");
		amounts.setAttribute('style',"text-align:right;");
		amounts.value = "0";

		var action = function()
		{
			amountView();
		};

		amounts.onchange = action;
		amounts.onblur = action;
		amounts.onkeyup = action;
		amounts.onkeypress = action;

		return amounts;
	}

	function genType(index)
	{
		var action = function()
		{
			amountView();
		};

		var postingTypes = document.createElement("select");
		postingTypes.name = "postingTypes";
		postingTypes.id = "postingTypes["+index+"]";
		postingTypes.onchange = action;

		var opt1 = document.createElement("option");
		opt1.value = "DEBET";
		opt1.text = "DEBET";
		opt1.onclick = action;

		postingTypes.appendChild(opt1);

		var opt2 = document.createElement("option");
		opt2.value = "CREDIT";
		opt2.text = "CREDIT";
		opt2.onclick = action;

		postingTypes.appendChild(opt2);

		return postingTypes;
	}

	function genAmtRp(index)
	{
		var amounts = document.createElement("input");
		amounts.type = "text";
		amounts.setAttribute('size',"20");
		amounts.setAttribute('style',"text-align:right;");
		amounts.setAttribute('class','input-disabled');
		amounts.setAttribute('disabled','true');
		amounts.value = "0";

		return amounts;
	}

	function genNote(index)
	{
		var note = document.createElement("input");
		note.type = "text";
		note.name = "notes";
		note.id = "notes";
		note.setAttribute('size',"50");

		return note;
	}

	function clickAll()
	{
		var checked = false;
		if(document.getElementById("checkMaster").checked == true)
			checked = true;

		var tbl = document.getElementById("lineItemTable");
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			var len = t_section.rows.length;

			for(var idx=len-1;idx>=0;idx--)
				t_section.rows[idx].cells[0].firstChild.setAttribute('checked',true);
		}
		else
			alert('Table doesnot exsist!');
	}

	function deleteRow()
	{
		var tbl = document.getElementById("lineItemTable");

		var t_section = tbl.tBodies[0];
		var len = t_section.rows.length;

		for(var idx=len-1;idx>=0;idx--)
		{
			if(t_section.rows[idx].cells[0].firstChild.checked == true)
				t_section.deleteRow(idx);
		}

		var check = document.getElementById("checkMaster");
		if(check)
			check.checked = false;

		amountView();
	}

	function showaccount(index)
	{
		var code = dojo.byId('code['+index+']');
		var account = dojo.byId('glaccounts['+index+']');
		if(!account || !code)
		{
			alert('Target account or code does not exist!');
			return;
		}

		GLAccountDWRRemote.load(code.value,function(map){
			if(map.status != 'ok')
			{
				alert(map.message);
				return;
			}

			for(var idx=account.options.length;idx>=0;idx--)
				account.remove(account.selectedIndex);

			var opt = document.createElement('option');
			opt.value = map.id;
			opt.text = map.name;

			account.appendChild(opt);
		});
	}
</script>