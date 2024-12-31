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
							<a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/></span></a>
							<a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
					  	</div>
						<div class="main-box">
							<sesform:form name="reportForm" method="post" modelAttribute="filterCriteria">
								<table width="100%" style="border:none">
								<tr>
									<td width="32%" align="right"><spring:message code="accreport.organization"/> & <spring:message code="accountingperiod"/></td>
								  	<td width="1%">:</td>
                              		<td width="66%" colspan="2" valign="top" align="left">
						  				<div class="toolbar-clean">
											<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
											<a class="item-button-delete" href="javascript:deleteRow()"><span><spring:message code="sirius.row.delete"/></span></a>
						  				</div>
                                    </td>
								</tr>
								<tr>
									<td width="32%" align="right">&nbsp;</td>
								  	<td width="1%">&nbsp;</td>
                              		<td width="66%" colspan="2" valign="top">
						  				<table id="lineItemTable" cellspacing="0" cellpadding="1" align="center" width="100%">
							  				<th width="1%">&nbsp;</th>
							  				<th width="1%">&nbsp;</th>
							  				<th width="12%">&nbsp;</th>
							  				<th width="1%">&nbsp;</th>
							  				<th width="12%">&nbsp;</th>
							  				<th width="39%">&nbsp;</th>
                                        </table>
                                    </td>
								</tr>
                                <tr>
									<td colspan="4">&nbsp;</td>
								</tr>
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
<script type="text/javascript">
	function generate()
	{
		//Row > 1 coz header, need header for better view
		var rowCount = $('#lineItemTable tr').length;

		if(rowCount <= 1)
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="accreport.organization"/> & <spring:message code="accountingperiod"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		var status = false;

		$('.orgs').each(function(){
			if($(this).val() == null)
			{
				status = true;
				return;
			}
		});

		$('.periods').each(function(){
			if($(this).val() == null)
			{
				status = true;
				return;
			}
		});

		if(status)
		{
			alert('<spring:message code="accreport.organization"/> / <spring:message code="accountingperiod"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		document.reportForm.action = "<c:url value='/page/prepostingtrialbalancereportview.htm'/>";
		document.reportForm.submit();
	}
	
	function resetform()
	{
		window.location = "<c:url value='/page/prepostingtrialbalancereportpre.htm'/>";
	}
	
	function addLineItem()
	{
		var tbl = document.getElementById("lineItemTable");
		//Row -1 coz header, need header for better view
		var row = tbl.insertRow(tbl.rows.length-1);
	
		row.insertCell(0).appendChild(generateIndex(row.rowIndex));
		row.insertCell(1).appendChild(generateCheckBox(row.rowIndex));
		row.insertCell(2).appendChild(genOrg(row.rowIndex));
		row.insertCell(3).appendChild(genOrgPopup(row.rowIndex));
		row.insertCell(4).appendChild(genPeriod(row.rowIndex));
		row.insertCell(5).appendChild(genPeriodPopup(row.rowIndex));
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
	
	function genOrg(index)
	{
		var organization = document.createElement("select");
		organization.name="organizationIds["+index+"]";
		organization.id="org["+index+"]";
		organization.setAttribute('class','combobox-ext orgs');
					
		return organization;
	}
	
	function genPeriod(index)
	{
		var period = document.createElement("select");
		period.name="accountingPeriodIds["+index+"]";
		period.id="period["+index+"]";
		period.setAttribute('class','combobox-ext periods');
					
		return period;
	}
	
	function popup(target,index)
	{
		var period = dojo.byId('period['+index+']');
		if(period)
			period.remove(period.selectedIndex);
			
		openpopup("<c:url value='/page/popupcompanystructurerolebasedview.htm?target='/>"+target);
	}
	
	function openperiod(index)
	{
		var org = document.getElementById('org['+index+']');
		if(org.value == '')
		{
			alert('Please select valid Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupaccountingperiodreport.htm?target=period["+index+"]&openonly=false&organization='/>"+org.value);
	}
	
	function genOrgPopup(index)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.onclick = function()
		{
			popup("org["+index+"]",index);
		}
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='Company';

		return _popup;
	}
	
	function genPeriodPopup(index)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.setAttribute('onclick','openperiod('+index+')');
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='Accounting Period';

		return _popup;
	}
	
	function clickAll()
	{
		var checked = false;
		if(document.getElementById("checkMaster").checked == true)
			checked = true;
	
		var tbl = document.getElementById("lineItemTable");
		var rowLen = tbl.rows.length;
		for(var idx=0;idx<rowLen;idx++)
		{
			var row = tbl.rows[idx];
			var cell = row.cells[1];
			var node = cell.lastChild;			
			node.checked = checked;
		}
	}
	
	function deleteAll()
	{
		var tbl = document.getElementById("lineItemTable");
	
		var rowLen = tbl.rows.length-1;
		for(var idx=rowLen;idx > 0;idx--)
			tbl.deleteRow(idx)
	}
	
	function bufferRow(table)
	{
		var tbl = document.getElementById("lineItemTable");
		
		var rowLen = tbl.rows.length;
		for(var idx=0;idx<rowLen;idx++)
		{
			var row = tbl.rows[idx];
			var cell = row.cells[1];
			var node = cell.lastChild;
			if(node)
			{
				if(node.checked == false)
				{
					var _row = table.insertRow(table.rows.length);					
				
					for(var _cellLen=0;_cellLen<row.cells.length;_cellLen++)
						_row.insertCell(_cellLen).appendChild(row.cells[_cellLen].lastChild);
				}
			}
		}
	}
	
	function reIndex(table)
	{
		var tbl = document.getElementById("lineItemTable");
		
		var rowLen = table.rows.length;
		for(var idx=0;idx < rowLen;idx++)
		{
			var row = table.rows[idx];
			var _row = tbl.insertRow(tbl.rows.length);
			
			for(var _cellLen=0;_cellLen<row.cells.length;_cellLen++)
				_row.insertCell(_cellLen).appendChild(row.cells[_cellLen].lastChild);
		}
	}
	
	function deleteRow()
	{
		var tbl = document.getElementById("lineItemTable");
	
		var table = document.createElement("table");
			
		bufferRow(table);
		deleteAll();
		reIndex(table);
	}
</script>
