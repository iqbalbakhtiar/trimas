<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
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
							<a class="item-button-list" href="<c:url value='/page/schematicjournalentryview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test="${access.edit}">
								<a class="item-button-save btn_save" ><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
                            <%-- <c:if test="${access.edit and journalEntry_edit.accountingPeriod.status == 'OPEN'}">
								<a class="item-button-copy" href="<c:url value='/page/journalentryprecopy.htm?id=${journalEntry_edit.id}'/>"><span><spring:message code="sirius.copy"/></span></a>
                            </c:if> --%>
                             <c:if test="${access.edit and journalEntry_edit.accountingPeriod.status == 'OPEN'}">
								<a class="item-button-reverse" href="<c:url value='/page/journalentryprereverse.htm?id=${journalEntry_edit.id}'/>"><span><spring:message code="sirius.reverse"/></span></a>
                            </c:if>   
                            <c:if test="${access.edit and journalEntry_edit.accountingPeriod.status == 'OPEN' and journalEntry_edit.entryStatus == 'POSTED'}">
								<a class="item-button-unlock" href="<c:url value='/page/schematicjournalentryunpost.htm?id=${journalEntry_edit.id}'/>"><span><spring:message code="sirius.unpost"/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="journalEntry_edit">
							<table width="100%" border="0" cellpadding="0" cellspacing="20">
							<tr valign="top">
								<td width="50%">
									<table width="100%" style="border:none">
									<tr>
										<td width="32%" nowrap="nowrap" align="right"><spring:message code="journalentry.code"/> :</td>
										<td width="68%"><input value="${journalEntry_edit.code}" size="25" disabled class="input-disabled"/></td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.name"/> :</td>
										<td><form:input path="name" value="${journalEntry_edit.name}" size="55"/> </td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.date"/> :</td>
										<td>
											<c:if test="${access.edit and journalEntry_edit.entryStatus == 'POSTED'}">
												<input size="11" class="input-disabled" disabled value="<fmt:formatDate value='${journalEntry_edit.entryDate}' pattern='dd-MM-yyyy'/>"/>
											</c:if>
											<c:if test="${access.edit and journalEntry_edit.entryStatus != 'POSTED'}">
												<input id="date" name="entryDate" value="<fmt:formatDate value='${journalEntry_edit.entryDate}' pattern='dd-MM-yyyy'/>" class="datepicker rate"/>
											</c:if>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.type"/> :</td>
										<td>
											<form:select path="entryType" disabled="true" class="combobox-min input-disabled">
												<form:option value="ENTRY">ENTRY</form:option>
                                                <form:option value="CORRECTION">CORRECTION</form:option>
                                                <form:option value="ADJUSTMENT">ADJUSTMENT</form:option>
											</form:select>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.currency"/> :</td>
										<td>
											<c:if test="${access.edit and journalEntry_edit.entryStatus != 'POSTED'}">
												<form:select id="currency" path="currency" class="rate">
	                                            	<form:options items='${currencys}' itemLabel='symbol' itemValue='id'/>
												</form:select>
	                                            <form:select id='type' path="exchangeType" class="rate">
	                                            	<form:option value='SPOT'>SPOT</form:option>
	                                                <form:option value='MIDDLE'>MIDDLE</form:option>
	                                                <form:option value='TAX'>TAX</form:option>
												</form:select>
												<input id="trxrate" size="10" class="input-disabled" disabled value="<fmt:formatNumber value='${journalEntry_edit.exchange.rate}' groupingUsed='true' maxFractionDigits='0'/>"/>
	             		   						<input id="default" type="hidden" value="${default.id}"/>
             		   						</c:if>
             		   						<c:if test="${access.edit and journalEntry_edit.entryStatus == 'POSTED'}">
             		   							<form:select id="currency" path="currency" class="rate input-disabled">
	                                            	<form:option value="${journalEntry_edit.currency.id}" label="${journalEntry_edit.currency.symbol}"/>
												</form:select>
	                                            <form:select id='type' path="exchangeType" class="rate input-disabled">
	                                            	<form:option value='${journalEntry_edit.exchangeType}' label="${journalEntry_edit.exchangeType}"/>
												</form:select>
												<input id="trxrate" size="10" class="input-disabled" disabled value="<fmt:formatNumber value='${journalEntry_edit.exchange.rate}' groupingUsed='true' maxFractionDigits='0'/>"/>
	             		   						<input id="default" type="hidden" value="${default.id}"/>
											</c:if>
										</td>
									</tr>
                                    <tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalentry.organization"/> :</td>
										<td>
											<select id="org" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.organization.firstName} ${journalEntry_edit.organization.middleName} ${journalEntry_edit.organization.lastName}'/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="journalschema"/> :</td>
										<td>
											<select id="schema" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.journalSchema}'/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap" align="right"><spring:message code="accountingperiod"/> :</td>
										<td>
											<select id="period" class="combobox input-disabled">
												<option><c:out value='${journalEntry_edit.accountingPeriod.name}'/></option>
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
                                    <c:if test='${not empty journalEntry_edit.indexes}'>
                                    <tr>
                                    	<td>
                                        	<fieldset>
												<legend><strong><spring:message code="journalentry.index"/></strong></legend>
												<table width="100%" border="0" cellpadding="0">
												<c:forEach items='${journalEntry_edit.indexes}' var='type'>
                                                <tr>
													<td width="57%" align="right">${type.indexType.name}</td>
												  <td width="1%" align="center">:</td>
												  <td width="42%"><input value="${type.content}" size="25" disabled class="input-disabled"/></td>
												</tr>
                                                </c:forEach>
												</table>
											</fieldset>
                                        </td>
                                    </tr>
                                    </c:if>
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
													<td><input class="number-disabled" id='labelDebet' value="<fmt:formatNumber value='${display.debet}' pattern=',##0.00'/>" size="25" disabled /></td>
													<td><input class="number-disabled" id='labelDebetRp' value="<fmt:formatNumber value='${display.debetRp}' pattern=',##0.00'/>" size="25" disabled /></td>
												</tr>
												<tr>
													<td align="center"><strong><spring:message code="journalentry.credit"/></strong></td>
													<td><input class="number-disabled" id='labelCredit' value="<fmt:formatNumber value='${display.credit}' pattern=',##0.00'/>" size="25" disabled /></td>
													<td><input class="number-disabled" id='labelCreditRp' value="<fmt:formatNumber value='${display.creditRp}' pattern=',##0.00'/>" size="25" disabled /></td>
												</tr>
												</table>
											</fieldset>
                                        </td>
                                    </tr>
                                    </table>
								</td>
								</tr>
								</table>
                            <div class="clears">&nbsp;</div>
                            <c:if test="${access.edit and journalEntry_edit.accountingPeriod.status == 'OPEN' and journalEntry_edit.entryStatus != 'POSTED' and journalEntry_edit.entrySourceType != 'AUTOMATIC'}">
                            <div class="toolbar-clean">
								<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
								<a class="item-button-delete" href="javascript:deleteRow()"><span><spring:message code="sirius.row.delete"/></span></a>
                            </div>
                            </c:if>
                            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
                            <thead>
                            <tr>
                               	<th width="1%"><input type="checkbox" name="checkMaster" id="checkMaster" onclick="clickAll();"/></th>
                                <th width="10%"><spring:message code="journaldetail.code"/></th>
						  	  	<th width="12%" colspan="2"><spring:message code="journaldetail.account"/></th>
					  	  	  	<th width="10%"><spring:message code="journaldetail.amount"/></th>
					  	  	  	<th width="10%"><spring:message code="journaldetail.amount"/> (Rp)</th>
                           	  	<th width="5%"><spring:message code="journaldetail.type"/></th>
					  	  	  	<th width="55%"><spring:message code="journaldetail.note"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${journalEntry_edit.details}" var="detail" varStatus='status'>
                            <tr>
                                <td><input type="checkbox"/></td>
                                <td nowrap="nowrap"><input id='code[${status.index}]' onchange="showaccount(${status.index})" value="${detail.account.code}" size="20"/></td>
                                <td nowrap="nowrap">
                                    <select id='account[${status.index}]' name='accounts' class="combobox-ext">
                                        <option value='${detail.account.id}'><c:out value='${detail.account.name}'/></option>
                                    </select>
                                </td>
                                <td nowrap="nowrap"><a class="item-popup" onclick="javascript:popup('account[${status.index}]',${status.index});"  title="Search Account" /></td>
                                <td nowrap="nowrap"><input id='amount[${status.index}]' name="amounts" onchange="display();" value="<fmt:formatNumber value='${detail.amount}' pattern=',##0.00'/>" class="input-number" size="20"/></td>
                                <td nowrap="nowrap"><input value="<fmt:formatNumber value='${detail.amount * journalEntry_edit.exchange.rate}' pattern=',##0.00'/>" class="input-disabled" size="20" disabled style="text-align:right;"/></td>
                                <td nowrap="nowrap"><select id='type[${status.index}]' name='types' class="combobox-min">
                                        <option value='${detail.postingType}' onclick="display();"><c:out value='${detail.postingType}'/></option>
                                    </select>
                                </td>
                                <td><input name='notes' size='50' value="${detail.note}"/></td>
                            </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr class="end-table"><td colspan="8">&nbsp;</td></tr>
                            </tfoot>
                            </table>
                            </sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${journalEntry_edit.createdBy.firstName} ${journalEntry_edit.createdBy.middleName} ${journalEntry_edit.createdBy.lastName}'/> (<fmt:formatDate value='${journalEntry_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${journalEntry_edit.updatedBy.firstName} ${journalEntry_edit.updatedBy.middleName} ${journalEntry_edit.updatedBy.lastName}'/> (<fmt:formatDate value='${journalEntry_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="journalentry"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.btn_save').click(function(e)
		{
			if(!$('#trxrate').val())
			{
				alert('<spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			if(dojo.byId('labelDebet').value != dojo.byId('labelCredit').value)
			{
				alert('<spring:message code="journalentry.amount"/> <spring:message code="notif.balance"/> !!!');
				return;
			}

			$.ajax({
				url:"<c:url value='/page/schematicjournalentryedit.htm'/>",
				data:$('#editForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/schematicjournalentryview.htm'/>";
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});

		$(".rate").change(function(){
			Currency.init();

			var trxRate = $('#trxrate');

			if(trxRate == null || trxRate.val() == '')
			{
				alert('<spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.notexist"/>, <spring:message code="notif.input"/> <spring:message code="journalentry.currencyvalue"/> <spring:message code="notif.select2"/>!');
			}

			display();
		});
	});

	function save()
	{		
		if(dojo.byId('labelDebet').value != dojo.byId('labelCredit').value)
		{
			alert('Journal unbalance,fix the error first before continue.');
			return;
		}
		
		document.editForm.action = "<c:url value='/page/schematicjournalentryedit.htm'/>";
		document.getElementById('_b_save').style.visibility='hidden';
		document.editForm.submit();
	}
	
	/*$(function(){
		$('#_b_save').click(function(){
			document.editForm.action = "<c:url value='/page/schematicjournalentryedit.htm'/>";
			document.editForm.submit();
		});
	});*/
	
	var tbl = dojo.byId("lineItemTable");
	var index = 0;

	function addLineItem()
	{
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				var t_section = tbl.tBodies[0];
				if(t_section)
				{
					if(t_section.rows.length > 0 && t_section.rows.length > index)
						index = t_section.rows.length+1;
				}
			
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
		$code = $('<input/>').attr('id','code['+index+']').attr('size','10').change(function(e){
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
								$account = $(document.getElementById('account['+index+']'));
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
		accounts.id="account["+index+"]";
		accounts.setAttribute('class','combobox');
					
		return accounts;
	}
	
	function genPopup(index)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.onclick = function()
		{
			popup('account['+index+']',index);
		}
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='GL Account';

		return _popup;
	}
	
	function genAmt(index)
	{
		var amt = document.createElement("input");
		amt.name = "amounts";
		amt.id = "amounts["+index+"]";
		amt.value = "0";
		amt.setAttribute('size',"20");
		amt.setAttribute('style',"text-align:right;");
		amt.setAttribute('onchange',"display()");
		
		return amt;
	}
	
	function genType(index)
	{
		var postingTypes = document.createElement("select");
		postingTypes.name = "types";
		postingTypes.id = "type["+index+"]";
		postingTypes.setAttribute('style','width:85px;');
		
		var opt1 = document.createElement("option");
		opt1.value = "DEBET";
		opt1.text = "DEBET";
		opt1.setAttribute('onclick','display()');
		
		postingTypes.appendChild(opt1);
		
		var opt2 = document.createElement("option");
		opt2.value = "CREDIT";
		opt2.text = "CREDIT";
		opt2.setAttribute('onclick','display()');
		
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
			
		display();
	}
	
	function showaccount(index)
	{
		var code = dojo.byId('code['+index+']');
		var account = dojo.byId('account['+index+']');
		if(!account || !code)
		{
			alert('Target account or code does not exist!');
			return;
		}
		
		if(code.value)
		{
			$target = $(code);
			
			$.ajax({
				url:"<c:url value='/page/popupglaccountjsonview.htm'/>",
				data:{code:code.value},
				method : 'GET',
				dataType : 'json',
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$account = $(account);
							$account.empty();
							
							$('<option/>').val(json.account.id).html(json.account.name).appendTo($account);
						}
					}
				}
			});	
		}
	}
	
	function display()
	{
		var _debet = 0.0;
		var _credit = 0.0;

		var rate = parseFloat(document.getElementById('trxrate').value.replace(/,/g,''));
	
		for(var idx=tbl.tBodies[0].rows.length-1;idx>=0;idx--)
		{
			var amt = tbl.tBodies[0].rows[idx].cells[4].firstChild.value;
			var amtRp = tbl.tBodies[0].rows[idx].cells[5].firstChild;
			var form = document.editForm.types;
			var type = form[idx].value;
		
			if(amt && type)
			{
				if(type == 'DEBET')
					_debet += parseFloat(amt.replace(/,/gi,''));
				else
					_credit += parseFloat(amt.replace(/,/gi,''));

				amtRp.value = (parseFloat(amt.replace(/,/gi,'')) * rate).numberFormat('#,#.00');
			}
		}
		
		document.getElementById('labelDebet').value = _debet.numberFormat('#,#.00');
		document.getElementById('labelCredit').value = _credit.numberFormat('#,#.00');

		document.getElementById('labelDebetRp').value = (_debet * rate).numberFormat('#,#.00');
		document.getElementById('labelCreditRp').value = (_credit * rate).numberFormat('#,#.00');	
	}
	
	function popup(target,index)
	{
		openpopup("<c:url value='/page/popupjournalschemaaccountview.htm?schema=${journalEntry_edit.schemaId}&split=true&target='/>"+target+"&index="+index);
	}
</script>