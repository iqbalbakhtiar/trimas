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
							<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
							<a class="item-button-edit" href="<c:url value='/page/userpreeditpassword.htm?id=${profile.user.id}'/>"><span><spring:message code="sirius.update"/>&nbsp;<spring:message code="password"/></span></a>
					  	</div>
						<div class="main-box">
				 			<sesform:form cssClass="edit-form" id="editForm" name="editForm" method="post" modelAttribute="profile">
						 		<table width="87%" cellpadding="2" cellspacing="0">
						 		  	<tr>
										<td width="35%" align="right">Row / Page (Normal Grid)</td>
										<td>:</td>
										<td width="63%">
											<select name="rowperpage" class="combobox">
												<option ${profile.rowperpage==20?"selected":""}>20</option>
												<option ${profile.rowperpage==40?"selected":""}>40</option>
												<option ${profile.rowperpage==60?"selected":""}>60</option>
												<option ${profile.rowperpage==80?"selected":""}>80</option>
											</select>
										</td>
										<td width="1%"><form:errors cssClass="error" path="rowperpage" /></td>
								 	</tr>
							 		<tr>
										<td align="right">Row / Page (Popup)</td>
										<td>:</td>
										<td>
											<select name="popuprow" class="combobox">
												<option ${profile.popuprow==20?"selected":""}>20</option>
												<option ${profile.popuprow==40?"selected":""}>40</option>
												<option ${profile.popuprow==60?"selected":""}>60</option>
												<option ${profile.popuprow==80?"selected":""}>80</option>
											</select>
										</td>
										<td><form:errors cssClass="error" path="popuprow"/></td>
							 		</tr>
							 		<tr>
										<td align="right">Locale</td>
										<td>:</td>
										<td>
							  				<form:select path="locale" cssClass="combobox">
							  					<form:options items="${locales}" itemLabel="name" itemValue="id"/>
							  				</form:select>
										</td>
										<td><form:errors cssClass="error" path="locale"/></td>
							 		</tr>
							 		<tr>
										<td align="right"><spring:message code="organization"/></td>
										<td width="1%">:</td>
		  				  				<td>
											<form:select id="org" path="organization" cssClass="combobox-ext">
												<c:if test="${not empty profile.organization}">
													<form:option value='${profile.organization.id}' label='${profile.organization.fullName}'/>
												</c:if>
											</form:select>
											<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
										</td>
									</tr>
									<tr>
										<td align="right"><spring:message code="facility"/></td>
										<td width="1%">:</td>
		  				  				<td>
											<form:select id="facility" path="facility" cssClass="combobox-ext">
												<c:if test="${not empty profile.facility}">
													<form:option value='${profile.facility.id}' label='${profile.facility.code} - ${profile.facility.name}'/>
												</c:if>
											</form:select>
											<a class="item-popup" onclick="javascript:openfacility();" title="<spring:message code="facility"/>" />
										</td>
									</tr>
		                            <tr>
										<td align="right"><spring:message code="container"/></td>
										<td width="1%">:</td>
		  				  				<td>
											<form:select id="container" path="container" cssClass="combobox-ext">
												<c:if test="${not empty profile.container}">
													<form:option value='${profile.container.id}' label='${profile.container.code} - ${profile.container.name}'/>
												</c:if>
											</form:select>
											<a class="item-popup" onclick="javascript:opencontainer();" title="<spring:message code="container"/>" />
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
<script type="text/javascript">
	function save()
	{
		document.editForm.action = "<c:url value='/page/userprofileedit.htm'/>";
		document.editForm.submit();
	}

	function openbank()
	{
		var org = document.getElementById('org').value;
		if(org == '')
		{
			alert('Please select default organization first!');
			return
		}
		
		openpopup("<c:url value='/page/popupbankaccountview.htm?target=bankAccount&organization='/>"+org);
	}
	
	function opencontainer()
	{
		var org = document.getElementById('org').value;
		if(org == '')
		{
			alert('Please select default organization first!');
			return
		}
		
		openpopup("<c:url value='/page/popupcontainerorganizationview.htm?target=container&organization='/>"+org);
	}

	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select company first!');
			return;
		}

		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
	}

</script>