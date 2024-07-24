<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
							<a class="item-button-list" href="<c:url value='/page/salesapproverview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.add}'>
								<a class="item-button-next" href="javascript:save();"><span>Next</span></a>
							</c:if>
					  	</div>
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="criteria">
							<table width="100%" style="border:none">
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="salesapprover.organization"/> :</td>
									<td>
										<form:select id="org" path="organization" cssClass="combobox-ext" >
										<c:if test='${not empty organization}'>
											<form:option value='${organization.id}' label='${organization.fullName}' />
										</c:if>
										</form:select>
										<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
									</td>
								</tr>
								<tr>
									<td align="right"><spring:message code="salesapprover.new"/> :</td>
									<td>
										<form:select id='salesPersonType' path='newPerson'>
											<form:option value='true'><spring:message code='sirius.yes'/></form:option>
											<form:option value='false'><spring:message code='sirius.no'/></form:option>
										</form:select>
									</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right"><spring:message code="party"/></td>
									<td>
										<form:select id="approver" path="id" cssClass="combobox-ext">
										</form:select>
										<a class="item-popup" onclick="openPerson('approver');"  title="Person" />
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
  	function save()
	{
		if(!$('#org').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="salesapprover.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
	
		if($("#salesPersonType").prop("selectedIndex") == 1)
		{
			if(!$('#approver').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="party"/> <spring:message code="notif.select2"/> !!!');
				return;
			}
		}
	
	    document.addForm.action = "<c:url value='/page/salesapproverpreadd2.htm'/>";
	    document.addForm.submit();
  	}
	
	function openPerson(target)
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="salesapprover.organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		openpopup("<c:url value='/page/popupsalesapproverview.htm?target="+target+"&organization='/>"+org.value+"&notSalesApprover=true");
	}
</script>
