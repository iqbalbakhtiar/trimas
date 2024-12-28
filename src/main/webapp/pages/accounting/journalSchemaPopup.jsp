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
	<%@ include file="/common/filterandpaging.jsp"%>
</head>
<body>
<div id="se-containers_pick">
	<div id="se-r00">
		<div id="se-r01a">&nbsp;</div>
		<div id="se-r03">&nbsp;</div>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="journalentry.code"/>&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td width="295" height="28" align="left"><input id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="journalentry.name"/>&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td width="295" height="28" align="left"><input id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>											
											<td align="left"><%@ include file="/common/button.jsp"%></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="5%"><div style="width:10px"></div></th>
					  		<th width="30%"><spring:message code="journalentry.code"/></th>
				  		  	<th width="65%"><spring:message code="journalentry.name"/></th>
				  		</tr>
						<c:forEach items="${journalSchemas}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}','${com.code}','${com.name}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td>${com.code}</td> 
							<td>${com.name}</td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
					  	</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
<script type="text/javascript">
	function setclient(id,code,name)
	{
		if(id && code && name)
		{
			var _client = self.opener.document.getElementById('${target}');
			if(_client)
			{
				_client.remove(_client.selectedIndex);
				
				var _opt = document.createElement('option');
				_opt.value = id;
				_opt.text = code+" "+name;
				
				_client.appendChild(_opt);
			}
			
			<c:forEach items='${journalSchemas}' var='schema'>
				if('${schema.id}' == id)
				{
					var _debet = self.opener.document.getElementById('txtBalanceDebet');
					var _credit = self.opener.document.getElementById('txtBalanceCredit');
				
					if(_debet)
						_debet.value = 0;
					
					if(_credit)
						_credit.value = 0;
					
					<c:forEach items='${schema.indexes}' var='index'>
						var _index = self.opener.document.getElementById('hidden-id[${index.index.id}]');
						var _text = self.opener.document.getElementById('text-id[${index.index.id}]');
						
						if(_index)
						{
							<c:if test='${index.on}'>
								_index.disabled = false;
							</c:if>
							<c:if test='${not index.on}'>
								_index.disabled = true;
							</c:if>
						}	
						if(_text)
						{
							<c:if test='${index.on}'>
								_text.disabled = false;
								_text.setAttribute('class','');
							</c:if>
							<c:if test='${not index.on}'>
								_text.disabled = true;
								_text.setAttribute('class','input-disabled');
							</c:if>
						}
					</c:forEach>
					
					var __t_target = self.opener.document.getElementById("lineItemTable");
					if(__t_target)
					{
						var _body = __t_target.tBodies[0];
								
						for(var idx=_body.rows.length-1;idx>=0;idx--)
							_body.deleteRow(idx);
					}
					
					<c:forEach items='${schema.accounts}' var='account'>
						<c:if test='${account.mandatory}'>
							<c:if test="${account.postingType == 'CREDIT'}">
								addLineItem('${account.account.id}','${account.account.code}','${account.account.name}',true,id);
							</c:if>
							<c:if test="${account.postingType == 'DEBET'}">
								addLineItem('${account.account.id}','${account.account.code}','${account.account.name}',false,id);
							</c:if>
						</c:if>
					</c:forEach>
				}
			</c:forEach>
		}
		
		window.close();
	}
	
	var tbl = self.opener.document.getElementById("lineItemTable");
	var index = 0;
	
	function addLineItem(id,code,name,credit,schema)
	{
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				if(t_section.rows.length > 0 && t_section.rows.length > index)
					index = t_section.rows.length;
			
				var row = t_section.insertRow(t_section.rows.length);
	
				row.insertCell(0).appendChild(genCbx(index));
				row.insertCell(1).appendChild(genCode(index,code));
				row.insertCell(2).appendChild(genAccount(index,id,name));
				row.insertCell(3).appendChild(genPopup(index,schema));
				row.insertCell(4).appendChild(genAmount(index));
				row.insertCell(5).appendChild(genAmountRp(index));
				row.insertCell(6).appendChild(genPostingType(index,credit));
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
	
	function genCode(index,val)
	{
		return $('<input/>').attr('id','code['+index+']').attr('size','10').val(val).attr('onchange','showaccount('+index+')').get(0);					
	}
	
	function genAccount(index,id,name)
	{
		var accounts = document.createElement("select");
		accounts.name="accounts";
		accounts.id="glaccounts["+index+"]";
		accounts.setAttribute('class','combobox-ext');
		
		var opt = document.createElement('option');
		opt.value=id;
		opt.text=name;
		
		accounts.appendChild(opt);
					
		return accounts;
	}
	
	function genAmount(index)
	{
		var amounts = self.opener.document.createElement("input");
		amounts.type = "text";
		amounts.name = "amounts";
		amounts.id = "amounts["+index+"]";
		amounts.setAttribute('size',"20");
		amounts.value = "0";
		
		amounts.setAttribute('onchange','amountView()');
		amounts.setAttribute('onblur','amountView()');
		amounts.setAttribute('style','text-align:right;');
				
		return amounts;
	}

	function genAmountRp(index)
	{
		var amounts = self.opener.document.createElement("input");
		amounts.type = "text";
		amounts.setAttribute('size',"20");
		amounts.setAttribute('style','text-align:right;');
		amounts.setAttribute('class','input-disabled');
		amounts.setAttribute('disabled','true');
		amounts.value = "0";
				
		return amounts;
	}
	
	function genPostingType(index,credit)
	{
		var postingTypes = document.createElement("select");
		postingTypes.name = "postingTypes";
		postingTypes.id = "postingTypes["+index+"]";
		postingTypes.setAttribute('style','width:70px;');
		postingTypes.setAttribute('onfocus','amountView();');
		
		if(credit)
		{
			var opt1 = document.createElement("option");
			opt1.value = "CREDIT";
			opt1.text = "CREDIT";
			opt1.setAttribute('onclick','amountView();');

			postingTypes.appendChild(opt1);
		}
		else
		{
			var opt2 = document.createElement("option");
			opt2.value = "DEBET";
			opt2.text = "DEBET";
			opt2.setAttribute('onclick','amountView();');
		
			postingTypes.appendChild(opt2);
		}
		
		return postingTypes;
	}
	
	function genNote()
	{
		var note = document.createElement("input");
		note.type = "text";
		note.name = "notes";
		note.id = "notes";
		note.setAttribute('size',"30");
		
		return note;
	}
			
	function genPopup(index,schema)
	{
		var _popup = self.opener.document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.setAttribute('onclick','popup("glaccounts['+index+']","postingTypes['+index+']",null,'+index+');');
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='GL Account';

		return _popup;
	}
</script>