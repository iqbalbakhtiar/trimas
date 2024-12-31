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
	<title>${title }</title>
    <%@ include file="/common/sirius-header.jsp"%>
        <style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
</head>
<!-- BEGIN OF BODY -->
<body>

<!-- top  menu -->
	

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	<!-- main menu -->
		<%@ include file="/common/sirius-menu.jsp"%>

	<div id="se-navigator">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="60%">${breadcrumb }</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle }</h1>
						
					  	<div class="toolbar">
							<a class="item-button-cancel" href="javascript:fillData();"><span><spring:message code='sirius.reset'/></span></a>
							<a id="submitButton"  class="item-button-generate-report" href="javascript:generateReport();" ><span><spring:message code='sirius.generate'/></span></a>
					  	</div>
						<div class="main-box">
							<form name="reportForm" id="reportForm" method="post">
								<table width="100%" style="border:none">
                                <tr>
									<td width="32%" align="right"><spring:message code='organization'/> & <spring:message code='accountingperiod'/></td>
								  	<td width="1%">:</td>
                              		<td width="66%" colspan="2" valign="top" align="left">
						  				<div class="toolbar-clean">
											<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code='sirius.row.new'/></span></a>
											<a class="item-button-delete" href="javascript:deleteRow()"><span><spring:message code='sirius.row.delete'/></span></a>
						  				</div>
                                    </td>
								</tr>
								<tr>
									<td width="32%" align="right">&nbsp;</td>
								  	<td width="1%">&nbsp;</td>
                              		<td width="66%" colspan="2" valign="top">
						  				<table id="lineItemTable" cellspacing="0" cellpadding="1" align="center" width="100%">
                                        <tbody>
                                        </tbody>
                                        </table>
                                    </td>
								</tr>
                                <tr>
									<td colspan="4">&nbsp;</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right"><spring:message code='currencymanagement'/></td>
									<td>:</td>
                                    <td>
										<select name="currency" class="combobox">
                                        	<c:forEach items='${currencys}' var='currency'>
                                        		<option value='${currency.id}'>${currency.symbol}</option>
                                            </c:forEach>
										</select>
									</td>
                                    <td>&nbsp;</td>
								</tr>
                                </table>
							</form>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

	<!-- /footer -->
</div><!-- /main containers -->



</body>
<!-- END OF BODY -->
</html>
<script type="text/javascript" src="<c:url value='/js/format.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>	
<script type="text/javascript">

	function generateReport()
	{
		
		var tbl = document.getElementById("lineItemTable");
		var t_section = tbl.tBodies[0];
		var len = t_section.rows.length;
		
		if(len == 0)
		{
			alert("<spring:message code='organization'/> & <spring:message code='accountingperiod'/> <spring:message code='notif.empty'/> !");
			return;
		}
		
		var org = false;
		var period = false;
		
		for(var idx=len-1;idx>=0;idx--)
		{
			if(t_section.rows[idx].cells[1].firstChild.value != null && t_section.rows[idx].cells[1].firstChild.value != '')
				org = true;
				
			if(t_section.rows[idx].cells[3].firstChild.value != null && t_section.rows[idx].cells[3].firstChild.value != '')
				period = true;
		}
	
		if(!org)
		{
			alert("<spring:message code='organization'/> <spring:message code='notif.empty'/> !");
			return;
		}
		
		if(!period)
		{
			alert("<spring:message code='accountingperiod'/> <spring:message code='notif.empty'/>!");
			return;
		}

		$("#submitButton").fadeOut();
		
		document.reportForm.action = "<c:url value='/page/glregisterreportpagingview.htm'/>";
		document.reportForm.submit();

		
	}
	
	function resetform()
	{
		var org = document.getElementById('org');
		var period = document.getElementById('period');
		
		for(var idx=org.options.length;idx>=0;idx--)
			org.remove(org.selectedIndex);
			
		for(var idx=period.options.length;idx>=0;idx--)
			period.remove(period.selectedIndex);
		
		document.reportForm.reset();
	}
	
	var index = 0;
	
	function addLineItem()
	{
		var tbl = document.getElementById("lineItemTable");
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				if(t_section.rows.length > 0 )
					index = t_section.rows.length+1;
			
				var row = t_section.insertRow(t_section.rows.length);
				
				row.insertCell(0).appendChild(genChk(row.rowIndex));
				row.insertCell(1).appendChild(genOrg(row.rowIndex));
				row.insertCell(2).appendChild(genOrgPopup(row.rowIndex));
				row.insertCell(3).appendChild(genPeriod(row.rowIndex));
				row.insertCell(4).appendChild(genPeriodPopup(row.rowIndex));
			}
		}
	}
					
	function genChk(index)
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
		organization.name="organizations";
		organization.id="org["+index+"]";
		organization.setAttribute('class','combobox-ext');
					
		return organization;
	}
	
	function genPeriod(index)
	{
		var period = document.createElement("select");
		period.name="periods";
		period.id="period["+index+"]";
		period.setAttribute('class','combobox-ext');
					
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
			alert("<spring:message code='notif.select1'/> <spring:message code='organization'/> <spring:message code='notif.select2'/>");
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
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			var len = t_section.rows.length;
				
			for(var idx=len-1;idx>=0;idx--)
				t_section.rows[idx].cells[0].firstChild.setAttribute('checked',true);
		}
		else
		{
			alert('Table doesnot exsist!');
		}
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
	}
</script>
