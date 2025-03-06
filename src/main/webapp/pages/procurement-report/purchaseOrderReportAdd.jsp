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
    	<script type="text/javascript">
		function generate()
		{
			var org = document.getElementById('org');
			if(org.value == null || org.value == '')
			{
				alert('Please select company first!');
				return;
			}
			
			if(!$('#from').val() || !$('#to').val() )
			{
				alert('Date from/to cannot be empty!');
				return;
			}
		
			document.reportForm.action = "<c:url value='/page/purchaseorderreportview.htm'/>";
			document.reportForm.submit();
		}

		function opensupplier()
		{
			var org = document.getElementById('org');
			if(org.value == '')
			{
				alert('Please select Organization first!');
				return;
			}

			openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&organization="+org.value+"&active=true'/>");
		}
		
		function resetform()
		{
			window.location = "<c:url value='/page/purchaseorderreportpre.htm'/>";
		}
	</script>
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
							<a class="item-button-cancel" href="javascript:resetform();"><span>Reset</span></a>
							<a class="item-button-generate-report" href="javascript:generate();"><span>Generate</span></a>
					  	</div>
					
                    	<div class="main-box">
							<sesform:form name="reportForm" id="reportForm" method="post">
								<table width="100%" style="border:none">
								<tr>
                                    <td><div align="right">Organization :</div></td>
                                    <td>
                                        <select id="org" name="organization" class="combobox-ext">
                                        	<c:if test='${not empty organization}'>
                                            	<option value="${organization.id}"><c:out value='${organization.fullName}'/></option>
                                            </c:if>
                                        </select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">From Date Po :</td>
                                    <td>
                                        <input id="from" name="dateFrom" class="datepicker" />
                                        &nbsp;To Date Po &nbsp;:&nbsp;
                                        <input id="to" name="dateTo" class="datepicker" />
                                    </td>
                                </tr>
                                    <tr>
									<td align="right">Supplier Name&nbsp;:&nbsp;</td>
									<td>
						            	<select id="supplier" name="supplier" class="combobox-ext">
										</select>
						            	&nbsp;
										<img src="<c:url value='/assets/icons/list_extensions.gif'/>" onclick="opensupplier();" style="CURSOR:pointer;" title="Supplier" />
						            </td>
								</tr>
                                <tr>
									<td align="right">Sort By&nbsp;:&nbsp;</td>
									<td>
						            	<select id="sortBy" name="sortBy" class="combobox-ext">
			                                <option value='date' label='Date'/>
			                                <option value='supplier.fullName' label='Supplier'/>
										</select>
						            	&nbsp;
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
