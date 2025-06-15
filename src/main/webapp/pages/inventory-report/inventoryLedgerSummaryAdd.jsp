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
                                        <a class="item-popup" onclick="openfacility('facility');" title="Warehouse" />
                                    </td>
                                </tr>
								<tr>
									<td align="right"><spring:message code="product.category"/> :</td>
									<td>
										<select id="productCategory" name="productCategory" class="combobox">
										</select>
										<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupproductcategoryview.htm?target=productCategory'/>');" title="Product Category" />
									</td>
								</tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="product"/>&nbsp;:</td>
                                    <td>
                                        <select id="product" name="product" class="combobox">
                                        </select>
                                        <a class="item-popup"" onclick="javascript:openProduct();" title="Product" />
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right">Bale&nbsp;:</td>
                                    <td>
                                        <select id="showBale" name="showBale" class="combobox-min3">
                                        	<option value="true"><spring:message code="sirius.yes"/></option>
                                        	<option value="no" selected="true"><spring:message code="sirius.no"/></option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="product.lot"/>&nbsp;:</td>
                                    <td><input id="lotCode" name="lotCode" size="6"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code="sirius.month"/>&nbsp;:</td>
                                    <td>
                                    	<select id="month" name="month">
                                        	<option value="JANUARY" selected><spring:message code="month.january"/></option>
                                            <option value="FEBRUARY"><spring:message code="month.february"/></option>
                                            <option value="MARCH"><spring:message code="month.march"/></option>
                                            <option value="APRIL"><spring:message code="month.april"/></option>
                                            <option value="MAY"><spring:message code="month.may"/></option>
                                            <option value="JUNE"><spring:message code="month.june"/></option>
                                            <option value="JULY"><spring:message code="month.july"/></option>
                                            <option value="AUGUST"><spring:message code="month.august"/></option>
                                            <option value="SEPTEMBER"><spring:message code="month.september"/></option>
                                            <option value="OCTOBER"><spring:message code="month.october"/></option>
                                            <option value="NOVEMBER"><spring:message code="month.november"/></option>
                                            <option value="DECEMBER"><spring:message code="month.december"/></option>
                                        </select>
                                        <select id="year" name="year">
                                            <c:forEach var="year" begin="${years-10}" end="${years}">
                                            	<option value="${(years*2)-year-10}">${(years*2)-year-10}</option>
                                            </c:forEach>
                                        </select>
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
		if(org.value == '')
		{
			alert('Please select company first!');
			return;
		}
		
		document.reportForm.action = "<c:url value='/page/inventoryledgersummaryview.htm'/>";
		document.reportForm.submit();
	}
	
	function resetform()
	{
		window.location = "<c:url value='/page/inventoryledgersummarypre.htm'/>";
	}
	
	function openfacility(target)
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select company first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?type=1&organization='/>"+org.value+"&target="+target);
	}
	
	function openContainer()
	{
		var fac = document.getElementById('facility');
		if(fac.value == '')
		{
			alert('Please select Facility first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupcontainerbyfacilityview.htm?target=container&facility='/>"+fac.value);
	}
	
	function openProduct()
	{
		var category = $("#productCategory option:selected").val();
		
		openpopup("<c:url value='/page/popupproductview.htm?target=product&categoryId='/>"+category);
	}
</script>