<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/customerview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="customer_add" enctype="multipart/form-data">
	<table style="border:none" width="100%">
		<tr>
			<td width="34%" align="right"><spring:message code="sirius.code"/></td>
			<td width="1%" align="center">:</td>
			<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
			<td width="1%"><form:errors path="code"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext">
		    		<c:if test='${not empty customer_add.organization}'>
						<form:option value='${customer_add.organization.id}' label='${customer_add.organization.fullName}'/>
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
		<%-- <tr>
			<td align="right"><spring:message code="customer.group"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="partyGroup" path="partyGroup" cssClass="combobox-ext">
				</form:select>
				<a class="item-popup" onclick="openCustomerGroup()" title="Costomer Group" />
			</td>
		</tr> --%>
		<tr>
			<td align="right"><spring:message code="customer.name"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="fullName" cssClass="inputbox" /></td>
			<td><form:errors path="fullName"/></td>
		</tr>
		<tr>
			<td align="right">NPWP</td>
			<td width="1%" align="center">:</td>
			<td><form:input path="taxCode" cssClass="inputbox" /></td>
			<td><form:errors path="taxCode"/></td>
		</tr>
<%--		<tr>--%>
<%--			<td align="right">SIUP</td>--%>
<%--			<td width="1%" align="center">:</td>--%>
<%--			<td><form:input path="permitCode" cssClass="inputbox" /></td>--%>
<%--			<td><form:errors path="permitCode"/></td>--%>
<%--		</tr>--%>
		<tr>
			<td align="right"><spring:message code="sirius.status"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:radiobutton path="active" value="true" label="Active"/>
				<form:radiobutton path="active" value="false" label="Inactive"/>	
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="customer.status"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="customerStatus" path="customerStatus" cssClass="combobox-min">
					<form:options items="${statuses}" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="customer.initiated.by"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="initiatedBy" path="initiatedBy" cssClass="combobox-ext">
				</form:select>
				<a class="item-popup" onclick="openSalesPerson();" title="Initiated By"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="customer.inspected.by"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="inspectedBy" path="inspectedBy" cssClass="combobox-ext">
				</form:select>
				<a class="item-popup" onclick="openEmployee();" title="Initiated By"/>
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

function openSalesPerson() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'initiatedBy', // Id Dropdown (Select) element
		organization: orgId, // Org (PartyTo)
		fromRoleType: 7, // Sales Person
		toRoleType: 2, // Company
		relationshipType: 2 // Employee Relationship
	};

	openpopup(buildUrl(baseUrl, params));
}

function openEmployee() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'inspectedBy', // Id Dropdown (Select) element
		organization: orgId, // Org (PartyTo)
		fromRoleType: 3, // Employee
		toRoleType: 2, // Company
		relationshipType: 2 // Employee Relationship
	};

	openpopup(buildUrl(baseUrl, params));
}

function validateForm() {
	var organization = $('#org').val();
	var salutation = $('input[name="salutation"]').val();
	var fullName = $('input[name="fullName"]').val();
	var taxCode = $('input[name="taxCode"]').val().trim();
	var permitCode = $('input[name="permitCode"]').val();
	var active = $('input[name="active"]:checked').val();

	if (organization == null || organization === "") {
		alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
		return false;
	}
	
	/* if (salutation == null || salutation.trim() === "") {
		alert('<spring:message code="party.salutation"/> <spring:message code="notif.empty"/> !');
		return false;
	} */
	
	if (fullName == null || fullName.trim() === "") {
		alert('<spring:message code="customer.name"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	/* if (taxCode.length < 15) {
	    alert('NPWP <spring:message code="notif.lower"/> 15 <spring:message code="invoice.digit"/> !');
		return false
	}  */

	if (active == null || active === undefined) {
		alert('<spring:message code="notif.select1"/> <spring:message code="sirius.status"/> <spring:message code="notif.select2"/>');
		return false;
	}
	
	return true;
}

function save() {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "<c:url value='/page/customeradd.htm'/>");
	xhr.responseType = 'json';
	
	if(xhr.readyState == 1) {
		$dialog.empty();
		$dialog.html('<spring:message code="notif.saving"/>');
		$dialog.dialog('open');
	}
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4) {
			var json = xhr.response;
			if(json) {
				if(json.status == 'OK') {
					$dialog.dialog('close');
					
					let url = "<c:url value='/page/customerpreedit.htm?id='/>"+json.data.id;;
					
					window.location=url;
				} else {
					afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		}
	};
	
	xhr.send(new FormData($('#addForm')[0]));
}
</script>