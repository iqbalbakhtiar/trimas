<%@ include file="/common/sirius-general-top.jsp"%>					  	<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/salespersonview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="person_add" enctype="multipart/form-data">
		<table style="border:none" width="100%">
			<tr>
				<td align="right"><spring:message code="organization"/> :</td>
				<td>
					<form:select id="org" path="organization" cssClass="combobox-ext">
						<c:if test='${not empty person_add.organization}'>
							<form:option value='${person_add.organization.id}' label='${person_add.organization.fullName}'/>
						</c:if>
					</form:select>
					<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"   title="Company Structure" />
				</td>
			</tr>
				<%-- <tr>
					 <td align="right"><spring:message code="party.name.exist"/> :</td>
					<td>
						<input type="radio" name="existing" value="true" onclick="changeExisting(true);"><spring:message code="sirius.yes"/></input>
						<input type="radio" name="existing" value="false" checked="checked" onclick="changeExisting(false);"><spring:message code="sirius.no"/></input>
					</td>
				 </tr> --%>
			<tr>
				<td align="right"><spring:message code="employee.code"/> :</td>
				<td>
					<input id="code" class='input-disabled' value="<spring:message code="sirius.auto.generated"/>" disabled/>
					<img id="btnBrowse" style="visibility: hidden;cursor: pointer;" src="assets/icons/list_extensions.gif" onclick="javascript:openExistingName();" title="Existing Name" />
					<input style="visibility: hidden;" id="partyId" name="employeeId" value="0" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="party.salutation"/> :</td>
				<td><form:input path="salutation" size="10"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="employee.name"/> :</td>
				<td><form:input id="name" path="fullName" cssClass="inputbox"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="party.code.reg"/> :</td>
				<td><form:input path="nik" cssClass="inputbox"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="party.code.tax"/> :</td>
				<td><form:input path="taxCode" cssClass="inputbox"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="party.birthdate"/> :</td>
				<td><input id="birthDate" name="birthDate" class="datepicker"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="party.picture"/> :</td>
				<td><input id="file" type="file" name="file" accept="image/*"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="sirius.note"/> :</td>
				<td><form:textarea path="note" rows="6" cols="45"/></td>
			</tr>
		</table>
	</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function()
		{
			if(validation())
			{
				var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
				var xhr = new XMLHttpRequest();
				xhr.open('POST', "<c:url value='/page/salespersonadd.htm'/>");
				xhr.responseType = 'json';

				if(xhr.readyState == 1)
				{
					beforeSend($dialog, '<spring:message code="notif.saving"/>');
				}

				xhr.onreadystatechange = function () {
					if(xhr.readyState == 4)
					{
						var json = xhr.response;
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
				};

				xhr.send(new FormData($('#addForm')[0]));
			}
		});
	});

	function validation()
	{
		if(!$('#org').val())
		{
			alert('Please select Organization first !!!');
			return;
		}

		if(!$('#name').val())
		{
			alert('Sales Person Name cannot be empty !!!');
			return;
		}

		return true;
	}
</script>