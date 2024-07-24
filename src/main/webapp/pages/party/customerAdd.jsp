<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
							<a class="item-button-list" href="<c:url value='/page/customerview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="customer_add" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="34%" align="right"><spring:message code="customer.type"/></td>
                                    <td width="1%" align="center">:</td>
							  		<td width="64%">
			  					  		<form:select path="type" class="combobox">
											<form:option value="personal"><spring:message code="party.personal"/></form:option>
											<form:option value="group"><spring:message code="party.group"/></form:option>
										</form:select>
									</td>
							  	  	<td width="1%">&nbsp;</td>
 								</tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="customer.company"/></td>
                                    <td>:</td>
                                    <td>
                                        <form:select id="org" path="organization" cssClass="combobox-ext">
										<c:if test='${not empty customer_add.organization}'>
											<form:option value='${customer_add.organization.id}' label='${customer_add.organization.firstName}'/>
										</c:if>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                                    </td>
                                </tr>
	 							<tr>
									<td align="right"><spring:message code="sirius.facility"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<form:select id="facility" path="facility" cssClass="combobox-ext">
                                        <c:if test='${not empty customer_add.facility}'>
                                            <form:option value='${customer_add.facility.id}' label='${customer_add.facility.name}' />
                                        </c:if>
                                        </form:select>
										<a class="item-popup" onclick="openfacility();"  title="Facility" />
									</td>
								</tr>
 								<tr>
 									<td align="right"><spring:message code="customer.code"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input value='<spring:message code="sirius.auto.generated"/>' disabled class="input-disabled"/></td>
									<td><form:errors path="code"/></td>
 								</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.salutation"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="salutation"/></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="customer.name"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input cssClass='check_field' id='firstName' path="firstName" size='36'/></td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.taxcode"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="taxCode"/></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="customer.note"/></td>
                                    <td width="1%" align="center" valign="top">:</td>
									<td><form:textarea path="note" rows="6" cols="45"/></td>
									<td>&nbsp;</td>
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
	$(function(){
		$first = $('#firstName');
	
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="customer"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			
			if (!$('#facility').val())
			{
				alert('<spring:message code="sirius.facility"/> <spring:message code="notif.empty"/>!');
                return;
			}
			
			if(!$first.val())
			{
				alert('<spring:message code="customer.name"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/customeradd.htm'/>",
				data:$('#addForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/customerpreedit.htm?id='/>"+json.data.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
	});
	
	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&active=true&organization='/>"+org.value);
	}
</script>