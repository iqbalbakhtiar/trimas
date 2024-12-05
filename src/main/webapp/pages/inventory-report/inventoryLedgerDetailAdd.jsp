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
							<a class="item-button-cancel" href="javascript:resetform();"><span>Reset</span></a>
							<a class="item-button-generate-report" href="javascript:generate();"><span>Generate</span></a>
					  	</div>
					
                    	<div class="main-box">
							<sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="criteria">
								<table width="100%" style="border:none">
								<tr>
                                    <td><div align="right"><spring:message code="organization"/>&nbsp;:</div></td>
                                    <td>
                                        <select id="org" name="organization" class="combobox-ext">
                                        	<c:if test='${not empty organization}'>
                                            	<option value="${organization.id}"><c:out value='${organization.fullName}'/></option>
                                            </c:if>
                                        </select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="facility"/>&nbsp;:</td>
                                    <td>
                                       	<select id="facility" name="facility" class="combobox-ext">
                                        </select>
                                        <a class="item-popup" onclick="openfacility('facility');" style="CURSOR:pointer;" title="Warehouse" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="product"/>&nbsp;:</td>
                                    <td>
                                        <select id="product" name="product" class="combobox">
                                        </select>
                                        <a class="item-popup"" onclick="javascript:openProduct();" style="CURSOR:pointer;" title="Product"/>
                                    </td>
                                </tr>
                                 <tr>
                                	<td nowrap="nowrap" align="right"><spring:message code="sirius.datefrom"/>&nbsp;:</td>
                                    <td>
								        <input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
								        &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
								        <input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>
								    </td>
                                </tr>
				  				</table>
							</sesform:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    
  	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>

</body>

</html>
<script type="text/javascript">
	function generate()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select company first!');
			return;
		}
		
		document.reportForm.action = "<c:url value='/page/inventoryledgerdetailview.htm'/>";
		document.reportForm.submit();
	}
	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
	}
	function resetform()
	{
		var org = document.getElementById('org');
		
		for(var idx=org.options.length;idx>=0;idx--)
			org.remove(org.selectedIndex);
			
		var product = document.getElementById('product');
		
		for(var idx=product.options.length;idx>=0;idx--)
			product.remove(product.selectedIndex);
		
		document.reportForm.reset();
	}	

	function openProduct()
	{
		var category = $( "#productCategory option:selected" ).text()
		
		openpopup("<c:url value='/page/popupproductview.htm?target=product&category='/>"+category);
	}

</script>