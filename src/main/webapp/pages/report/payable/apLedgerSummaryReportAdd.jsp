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
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
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
							<a class="item-button-generate-report" ><span><spring:message code="sirius.generate"/></span></a>
					  	</div>
					
						<div class="main-box">
							<sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="reportCriteria">
								<table width="100%" style="border:none">
								<tr>
                                    <td><div align="right"><spring:message code="organization"/> :</div></td>
                                    <td>
                                        <select id="org" name="organization" class="combobox-ext">
                                        	<c:if test='${not empty organization}'>
												<option value="${organization.id}">${organization.fullName}</option>
                                            </c:if>
                                        </select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                                    </td>
								</tr>
                                <tr>
                                    <td align="right"><spring:message code="sirius.datefrom2"/> :</td>
                                    <td>
                                        <input name="dateFrom" id='dateFrom' class="datepicker"/>
                                        &nbsp;<spring:message code="sirius.dateto2"/>
                                        <input name="dateTo" id='dateTo' class="datepicker"/>
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
   	$(function()
	{
		$('.item-button-generate-report').click(function()
		{
			if(!$('#org').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
				return
			}
		
			if(!$('#dateFrom').val() || !$('#dateTo').val())
			{
				alert('<spring:message code="sirius.datefrom2"/> <spring:message code="notif.or"/> <spring:message code="sirius.dateto2"/> <spring:message code="notif.empty"/> !!!');
				return
			}
		
			document.reportForm.action = "<c:url value='/page/apledgersummaryview.htm'/>";
			document.reportForm.submit();
		});
	});
	
	function resetform()
	{
		window.location = "<c:url value='/page/apledgersummarypre.htm'/>";
	}
	
	function opencustomer()
	{
		var org = document.getElementById('organization');
		if(org.value == null || org.value == '')
		{
			alert('Please select company first!');
			return;
		}
	
		openpopup("<c:url value='/page/popupcustomerview.htm?target=customer&organization='/>"+org.value);
	}
</script>