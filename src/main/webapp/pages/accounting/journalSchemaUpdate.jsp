<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
</head>

<body onload="showerror();">

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
							<a class="item-button-list" href="<c:url value='/page/standardjournalschemaview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.edit}'>
                            	<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
					  	</div>
						<div class="main-box">
							<sesform:form name="editForm" method="post" modelAttribute="journalSchema_edit">
							<table style="border:none" width="100%">
							<tr>
								<td width="21%" align="right"><spring:message code="journalschema.code"/></td>
							  	<td width="1%">:</td>
						  	  	<td width="33%"><form:input id="code" path="code" cssClass="inputbox" /></td>
    				  	  		<td width="45%" rowspan="6" valign="top" align="left">
									<fieldset>
                                    	<legend><spring:message code="journalschema.index"/></legend>
                              			<table width="100%">
                                        <tr>
                                        	<td width="38%" valign="top">
                                           	  <table>
                                                <c:forEach items='${journalSchema_edit.indexes}' var='index' varStatus='status'>
                                        		<c:if test='${(status.index % 2) == 0}'>
                                                <tr>
                                        			<td width="5%"><form:checkbox path="indexes[${status.index}].on" value="true"/></td>
                                            		<td width="95%">${index.index.name}</td>
                                        		</tr>
                                                </c:if>
                                        		</c:forEach>
                                                </table>
                                            </td>
                                   		  <td width="62%" valign="top">
                   	 				  			<table>
                                                <c:forEach items='${journalSchema_edit.indexes}' var='index' varStatus='status'>
                                        		<c:if test='${(status.index % 2) == 1}'>
                                                <tr>
                                        			<td width="5%"><form:checkbox path="indexes[${status.index}].on" value="true"/></td>
                                            		<td width="95%">${index.index.name}</td>
                                        		</tr>
                                                </c:if>
                                        		</c:forEach>
                                                </table>
                                            </td>
                                		</tr>                                        
                                        </table>
                                    </fieldset>
                           	  	</td>
							</tr>	
							<tr>
								<td><div align="right"><spring:message code="journalschema.name"/></div></td>
								<td width="1%">:</td>
						  	  <td width="33%"><form:input id="name" path="name" cssClass="inputbox" /></td>
		    				</tr>
							<tr>
								<td><div align="right"><spring:message code="journalschema.organization"/></div></td>
								<td width="1%">:</td>
  				  				<td>
									<form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
                                    	<form:option value="${journalSchema_edit.organization.id}"><c:out value='${journalSchema_edit.organization.code} ${journalSchema_edit.organization.firstName} ${journalSchema_edit.organization.middleName} ${journalSchema_edit.organization.lastName}'/></form:option>
									</form:select>
								</td>							
			    			</tr>
							<tr>
								<td align="right"><spring:message code="coa"/></td>
								<td width="1%">:</td>
  				  				<td>
									<form:select id="coa" path="chartOfAccount" cssClass="combobox-ext input-disabled">
                                    	<form:option value="${journalSchema_edit.chartOfAccount.id}"><c:out value='${journalSchema_edit.chartOfAccount.code} ${journalSchema_edit.chartOfAccount.name}'/></form:option>
									</form:select>
								</td>							
			    			</tr>
							<tr>
								<td align="right"><spring:message code="journalschema.note"/></td>
								<td width="1%">:</td>
						  	  <td><form:textarea path="note" cols="55" rows="6"/></td>							
			    			</tr>
							</table>
							<div id="container" dojoType="TabContainer" style="width:100%;height:350px;">
								<div id="debet" dojoType="ContentPane" label="<spring:message code="journalschema.debet"/>" class="tab-pages">
									<div class="toolbar-clean">
										<a class="item-button-new" href="javascript:addLineItem('DEBET','debet');"><span>New Row</span></a>
										<a class="item-button-delete" href="javascript:deleteRow('lineItemTable-debet')"><span>Delete Row</span></a>
			 						</div>
									<table class="table-list" cellspacing="0" cellpadding="0" id="lineItemTable-debet" width="100%">
									<thead>
                                    <tr>
										<th width="1%">&nbsp;</th>
									 	<th width="1%" align="left"><input type="checkbox" name="checkMaster" id="checkMaster-lineItemTable-debet" onClick="clickAll('lineItemTable-debet');"/></th>
									  	<th colspan="2" width="15%"><spring:message code="glaccount"/></th>
										<th width="15%"><spring:message code="journalschema.mandatory"/></th>
                                        <th width="65%">&nbsp;</th>
									</tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items='${journalSchema_edit.accounts}' var='account' varStatus='status'>
                                    <c:if test="${account.postingType == 'DEBET'}">
                                    <tr>
                                   	  	<td align="center"><input type="hidden" id="index" name="index" value="${status.index}"/></td>
                                    	<td align="left" nowrap="nowrap"><input type="checkbox" id="check" name="check"/></td>
                                        <td nowrap="nowrap" colspan="2">
									  		<select id="glaccount[${status.index}]-debet" name="accounts-debet" class="combobox-ext">
                                            	<option value='${account.account.id}'>${account.account.code} ${account.account.name}</option>
                                            </select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]-debet');" />
                                        </td>
										<td nowrap="nowrap">
                                        	<select id="mandatory[${status.index}]-debet" name="mandatorys-debet" class="combobox-ext">
                                            	<option value='true' <c:if test='${account.mandatory}'>selected</c:if>>MANDATORY</option>
                                                <option value='false' <c:if test='${not account.mandatory}'>selected</c:if> >NOT MANDATORY</option>
                                            </select>
                                        </td>
                                        <td width="1%">&nbsp;</td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr class="end-table">
										<td colspan="6">&nbsp;</td>
                               		</tr>    
                                    </tfoot>
									</table>
									</div>
                                    <div id="credit" dojoType="ContentPane" label="<spring:message code="journalschema.credit"/>" class="tab-pages">
									<div class="toolbar-clean">
										<a class="item-button-new" href="javascript:addLineItem('CREDIT','credit');"><span><spring:message code="journalschema.newrow"/></span></a>
										<a class="item-button-delete" href="javascript:deleteRow('lineItemTable-credit')"><span><spring:message code="journalschema.deleterow"/></span></a>
			 						</div>
									<table class="table-list" cellspacing="0" cellpadding="0" id="lineItemTable-credit" width="100%">
                                    <thead>
									<tr>
										<th width="1%">&nbsp;</th>
									 	<th width="1%" align="left"><input type="checkbox" name="checkMaster" id="checkMaster-lineItemTable-credit" onClick="clickAll('lineItemTable-credit');"/></th>
									  	<th colspan="2" width="15%"><spring:message code="glaccount"/></th>
										<th width="15%"><spring:message code="journalschema.mandatory"/></th>
                                        <th width="65%">&nbsp;</th>
									</tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items='${journalSchema_edit.accounts}' var='account' varStatus='status'>
                                    <c:if test="${account.postingType == 'CREDIT'}">
                                    <tr>
                                   	  	<td align="center"><input type="hidden" id="index" name="index" value="${status.index}"/></td>
                                    	<td align="left"><input type="checkbox" id="check" name="check"/></td>
                                        <td nowrap="nowrap" colspan="2">
									  		<select id="glaccount[${status.index}]-credit" name="accounts-credit" class="combobox-ext">
                                            	<option value='${account.account.id}'>${account.account.code} ${account.account.name}</option>
                                            </select>
                                            <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]-credit');" />
                                        </td>
										<td nowrap="nowrap">
                                        	<select id="mandatory[${status.index}]-credit" name="mandatorys-credit" class="combobox-ext">
                                            	<option value='true' <c:if test='${account.mandatory}'>selected</c:if>>MANDATORY</option>
                                                <option value='false' <c:if test='${not account.mandatory}'>selected</c:if> >NOT MANDATORY</option>
                                            </select>
                                        </td>
                                        <td width="1%">&nbsp;</td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                    <tbody>
                                    <tfoot>
                                    <tr class="end-table">
										<td colspan="6">&nbsp;</td>
                               		</tr>    
                                    </tfoot>
									</table>
									</div>
								</div>
							</sesform:form>
							<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${journalSchema_edit.createdBy.firstName} ${journalSchema_edit.createdBy.middleName} ${journalSchema_edit.createdBy.lastName}'/> (<fmt:formatDate value='${journalSchema_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${journalSchema_edit.updatedBy.firstName} ${journalSchema_edit.updatedBy.middleName} ${journalSchema_edit.updatedBy.lastName}'/> (<fmt:formatDate value='${journalSchema_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
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
<script type="text/javascript">
	function save()
	{
		if(!$('#code').val())
		{
			alert('<spring:message code="journalschema.code"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		if(!$('#name').val())
		{
			alert('<spring:message code="journalschema.name"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		document.editForm.action = "<c:url value='/page/standardjournalschemaedit.htm'/>";
		document.editForm.submit();
	}
		
	function popup(target)
	{
		var coa = document.getElementById('coa');
		if(coa.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="coa"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		openpopup("<c:url value='/page/popupglaccountview.htm?level=ACCOUNT&coa='/>"+coa.value+"&target="+target);
	}
		
	var index = 0;
	function addLineItem(type,name)
	{
		var tbl = document.getElementById("lineItemTable-"+name);
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				if(t_section.rows.length > 0 )
					index = t_section.rows.length+1;
			
				var row = t_section.insertRow(t_section.rows.length);
				row.insertCell(0).appendChild(generateIndex(index));
				row.insertCell(1).appendChild(generateCheckBox(index));
				row.insertCell(2).appendChild(generateItem(index,name));
				row.insertCell(3).appendChild(itempopup(index,name));
				row.insertCell(4).appendChild(generatemandatory(index,name));
				row.insertCell(5).appendChild(types(index,type,name));
				
				index++;
			}
		}
	}	
	
	function types(index,type,name)
	{
		var types = document.createElement("input");
		types.type = "hidden";
		types.name = "types-"+name;
		types.value = type;
		
		return types;
	}	
		
	function generateIndex(index)
	{
		var idx = document.createElement("input");
		idx.type = "hidden";
		idx.name = "index";
		idx.id = "index";
		idx.value = index;
		
		return idx;
	}
					
	function generateCheckBox(index)
	{
		var check = document.createElement("input");
		check.type = "checkbox";
		check.name = "check";
		check.id = "check";
		
		return check;
	}
	
	function generateItem(index,name)
	{
		var itm = document.createElement("select");
		itm.name="accounts-"+name;
		itm.id="glaccounts["+index+"]-"+name;
		itm.setAttribute('class','combobox-ext');
					
		return itm;
	}
	
	function itempopup(index,name)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.onclick = function()
		{
			popup("glaccounts["+index+"]-"+name);
		}
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='GL Account';

		return _popup;
	}
	
	function generatemandatory(index,name)
	{
		var _mandatory = document.createElement('select');
		_mandatory.name = 'mandatorys-'+name;
		_mandatory.setAttribute('class','combobox-ext');
		
		var _opt1 = document.createElement('option');
		_opt1.value = 'true';
		_opt1.text = 'MANDATORY';
		_opt1.selected = true;
		
		var _opt2 = document.createElement('option');
		_opt2.value = 'false';
		_opt2.text = 'NOT MANDATORY';
	
		_mandatory.appendChild(_opt1);
		_mandatory.appendChild(_opt2);
		
		return _mandatory;
	}
	
	function clickAll(name)
	{
		var checked = false;
		if(document.getElementById("checkMaster").checked == true)
			checked = true;
		
		var tbl = document.getElementById(name);
		if(tbl)
		{
			var rowLen = tbl.rows.length;
			for(var idx=0;idx<rowLen;idx++)
			{
				var row = tbl.rows[idx];
				var cell = row.cells[1];
				if(cell)
				{
					var node = cell.lastChild;
				
					if(node && node.disabled == false)
						node.checked = checked;
				}
			}
		}
		else
		{
			alert('Table doesnot exsist!');
		}
	}
			
	function deleteRow(name)
	{
		var tbl = document.getElementById(name);
			
		var t_section = tbl.tBodies[0];
		var len = t_section.rows.length;
				
		for(var idx=len-1;idx>=0;idx--)
		{
			if(t_section.rows[idx].cells[1].firstChild.checked == true)
				t_section.deleteRow(idx);
		}
		
		var check = document.getElementById("checkMaster");
		if(check)
			check.checked = false;
	}
	
	function showerror()
	{
		<c:if test='${not empty message}'>
			alert('${message}');
		</c:if>
	}
</script>