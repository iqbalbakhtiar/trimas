<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/supplierview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="supplier_add" enctype="multipart/form-data">
	<table style="border:none" width="100%">
		<tr>
			<td width="34%" align="right">Supplier ID</td>
			<td width="1%" align="center">:</td>
			<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
			<td width="1%"><form:errors path="code"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext">
		    		<c:if test='${not empty supplier_add.organization}'>
						<form:option value='${supplier_add.organization.id}' label='${supplier_add.organization.fullName}'/>
		    		</c:if>
				</form:select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.salutation"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="salutation" size="10"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="supplier.name"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="fullName" cssClass="inputbox" /></td>
			<td><form:errors path="fullName"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.pkp"/> :</td>
			<td width="1%" align="center">:</td>
			<td>
				<form:radiobutton path="taxable" value="true" label="Yes"/>
				<form:radiobutton path="taxable" value="false" label="No"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.npwp"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="taxCode" cssClass="inputbox" /></td>
			<td><form:errors path="taxCode"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.siup"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="permitCode" cssClass="inputbox" /></td>
			<td><form:errors path="permitCode"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.status"/> :</td>
			<td width="1%" align="center">:</td>
			<td>
				<form:radiobutton path="active" value="true" label="Active"/>
				<form:radiobutton path="active" value="false" label="Inactive"/>	
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.note"/></td>
			<td width="1%" align="center">:</td>
			<td><form:textarea path="note" rows="6" cols="45"/></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
});

function validateForm() {
	var organization = $('#org').val();
	var salutation = $('input[name="salutation"]').val();
	var fullName = $('input[name="fullName"]').val();
	var taxable = $('input[name="taxable"]:checked').val();
	var taxCode = $('input[name="taxCode"]').val();
	var active = $('input[name="active"]:checked').val();

	if (organization == null || organization === "") {
		alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
		return false;
	}
	
	if (salutation == null || salutation.trim() === "") {
		alert('<spring:message code="party.salutation"/> <spring:message code="notif.empty"/> !');
		return false;
	}
	
	if (fullName == null || fullName.trim() === "") {
		alert('<spring:message code="supplier.name"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if (taxable === "true") {
		if (taxCode == null || taxCode.trim() === "") {
			alert('<spring:message code="party.npwp"/> <spring:message code="notif.empty"/> !');
			return false;
		}
	}

	if (active == null || active === undefined) {
		alert('<spring:message code="notif.select1"/> <spring:message code="sirius.status"/> <spring:message code="notif.select2"/>');
		return false;
	}
	
	return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/supplieradd.htm'/>",
		data:$('#addForm').serialize(),
		type : 'POST',
		dataType : 'json',
		beforeSend:function()
		{
			$dialog.empty();
			$dialog.html('<spring:message code="notif.saving"/>');
			$dialog.dialog('open');
		},
		success : function(json) {
			if(json)
			{
				if(json.status === 'OK')
				{
					$dialog.dialog('close');
					<%--window.location="<c:url value='/page/supplierview.htm'/>";--%>
					// Or Can use This
					window.location="<c:url value='/page/supplierpreedit.htm?id='/>"+json.data.id;
				}
				else
				{
					$dialog.empty();
					$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
				}
			}
		}
	});
}
</script>