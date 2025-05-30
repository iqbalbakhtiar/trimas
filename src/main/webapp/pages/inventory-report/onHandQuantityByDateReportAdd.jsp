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
							<a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/>&nbsp;</span></a>
							<a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
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
                                    <td nowrap="nowrap" align="right"><spring:message code="product.category"/>&nbsp;:</td>
                                    <td>
                                       	<select id="productCategory" name="productCategory" class="combobox-ext">
                                        </select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupproductcategoryview.htm?target=productCategory'/>');" title="Product Category" />
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
  	<%@ include file="/common/sirius-footer.jsp"%>
</div>
</body>
</html>
<script type="text/javascript">
	function generate()
	{
		var org = document.getElementById('org');
		if (org.value == '')
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		if (!$("#dateFrom").val() || !$("#dateTo").val())
		{
			alert('<spring:message code="sirius.datefrom"/> & <spring:message code="sirius.dateto"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		document.reportForm.action = "<c:url value='/page/onhandquantitybydatereportview.htm'/>";
		document.reportForm.submit();
	}
	
	function resetform()
	{
		window.location = "<c:url value='/page/onhandquantitybydatereportpre.htm'/>";
	}
	
	function openfacility(target)
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?type=1&organization='/>"+org.value+"&target="+target);
	}
</script>