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
							<a class="item-button-list" href="<c:url value='/page/salespersonview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="salesPerson_add" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="34%" align="right"><spring:message code="customer.company"/></td>
                                    <td width="1%" align="center">:</td>
							  		<td width="64%">
                                        <form:select id="org" path="organization" cssClass="combobox-ext">
										<c:if test='${not empty salesPerson_add.organization}'>
											<form:option value='${salesPerson_add.organization.id}' label='${salesPerson_add.organization.firstName}'/>
										</c:if>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
									</td>
							  	  	<td width="1%">&nbsp;</td>
 								</tr>
 								<tr>
 									<td align="right"><spring:message code="party.name.exist"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<input type="radio" name="existing" value="true" onclick="changeExisting(true);"><spring:message code="sirius.yes"/></input>	
										<input type="radio" name="existing" value="false" checked="checked" onclick="changeExisting(false);"><spring:message code="sirius.no"/></input>								
									</td>  
									<td>&nbsp;</td>
	 							</tr>
	 							<tr>
 									<td align="right"><spring:message code="sirius.type"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<select id="type" class="combobox-min input-disabled" onchange="resetDatas();">
											<option value="0"><spring:message code="sirius.new"/></option>
										</select>
									</td>
									<td>&nbsp;</td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="sirius.code"/></td>
                                    <td width="1%" align="center">:</td>
							  	  	<td>
							  	  		<input id="code" class='input-disabled' value="<spring:message code="sirius.auto.generated"/>" disabled/>
							  	  		<img id="btnBrowse" style="visibility: hidden;cursor: pointer;" src="assets/icons/list_extensions.gif" onclick="javascript:openExistingName();" title="Existing Name" />
							  	  		<input style="visibility: hidden;" id="partyId" name="personId" value="0" readonly="readonly"/>
							  	  	</td>
									<td><form:errors path="code"/></td>
 								</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.salutation"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="salutation"/></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="salesperson.name"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input cssClass='check_field' id='firstName' path="firstName" size='36'/></td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="salesperson.taxcode"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="taxCode"/></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
	 							<tr>
 									<td align="right"><spring:message code="party.birthdate"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input id="birthDate" name="birthDate" class="datepicker"/></td>
									<td>&nbsp;</td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="salesperson.note"/></td>
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
	
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="salesperson"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			if(!$first.val())
			{
				alert('<spring:message code="salesperson.name"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/salespersonadd.htm'/>",
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
							window.location="<c:url value='/page/salespersonpreedit.htm?id='/>"+json.data.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
	});
	
	function changeExisting(exist)
	{
		if(exist)
		{
			$("#type option").remove();
			$("<option></option>").text("EMPLOYEE").val("EMPLOYEE").appendTo("#type");
			$("#type").attr("class","combobox-min");
			
			$("#btnBrowse").attr("style","cursor: pointer;");
			$("#salutation").attr("class","inputbox input-disabled");
			$("#salutation").attr("disabled","true");
			$("#firstName").attr("class","inputbox input-disabled");
			$("#firstName").attr("disabled","true");
			$("#taxCode").attr("class","inputbox input-disabled");
			$("#taxCode").attr("disabled","true");
			$("#birthDate").attr("class","input-disabled");
			$("#birthDate").attr("disabled","true");
			$("#birthDate").attr("size","8");
			$(".ui-datepicker-trigger").attr("style","visibility:hidden");
			$("#file").removeAttr("type");
			$("#file").attr("class","inputbox input-disabled");
			$("#file").attr("disabled","true");
			$("#note").attr("class","input-disabled");
			$("#note").attr("disabled","true");

			resetDatas();
		} else
		{
			$("#type option").remove();
			$("<option></option>").text("<spring:message code="sirius.new"/>").val("<spring:message code="sirius.new"/>").appendTo("#type");
			$("#type").attr("class","combobox-min input-disabled");
			
			$("#btnBrowse").attr("style","visibility: hidden;cursor: pointer;");
			$("#salutation").attr("class","inputbox");
			$("#salutation").removeAttr("disabled");
			$("#firstName").attr("class","inputbox");
			$("#firstName").removeAttr("disabled");
			$("#nik").attr("class","inputbox");
			$("#nik").removeAttr("disabled");
			$("#taxCode").attr("class","inputbox");
			$("#taxCode").removeAttr("disabled");
			$("#birthDate").attr("class","datepicker");
			$("#birthDate").removeAttr("disabled");
			$(".ui-datepicker-trigger").removeAttr("style");
			$("#file").attr("type","file");
			$("#file").attr('accept',"image/*");
			$("#file").removeAttr("disabled");
			$("#file").removeAttr("class");
			$("#note").removeAttr("disabled");
			$("#note").removeAttr("class");
			
			resetDatas();
		}
	}

	function openExistingName()
	{
		var type = $("#type").val();
		
		if(!$('#org').val())
		{
			alert('Please select Organization first !!!');
			return;
		}
		
		if(type == 'EMPLOYEE')
			openpopup("<c:url value='/page/employeepopupview.htm?organization='/>"+$('#org').val()+"&source=SALESPERSON");
	}

	function resetDatas()
	{
		$("#partyId").val("0");
		$("#code").val("<spring:message code='sirius.auto.generated'/>");
		$("#salutation").val("");
		$("#firstName").val("");
		$("#nik").val("");
		$("#taxCode").val("");
		$("#birthDate").val("");
		$("#note").val("");
	}

	function setPartyInfo(partyId, code, salutation, name, initial, nik, taxCode, birthDate, note)
	{
		$("#partyId").val(partyId);
		$("#code").val(code);
		$("#salutation").val(salutation);
		$("#firstName").val(name);
		$("#nik").val(nik);
		$("#taxCode").val(taxCode);
		$("#birthDate").val(birthDate);
		$("#note").val(note);
	}
</script>