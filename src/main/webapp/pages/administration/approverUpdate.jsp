<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/approverview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="approver_edit" enctype="multipart/form-data">
	<table style="border:none" width="100%">
		<tr>
			<td width="34%" align="right"><spring:message code="approver.id"/></td>
			<td width="1%" align="center">:</td>
			<td width="64%"><input id="code" disabled class="input-disabled" value="${partyFrom.code}"></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<select id="org" class="combobox-ext input-disabled" disabled>
					<c:if test='${not empty approver_edit.partyRelationship.partyTo}'>
						<option value='${approver_edit.partyRelationship.partyTo.id}' label='${approver_edit.partyRelationship.partyTo.fullName}'/>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="approver.type"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<select name="partayRoleTypeFrom" id="partyRoleTypeFrom" disabled class="input-disabled">
					<c:if test='${not empty approver_edit.partyRelationship.partyRoleTypeFrom}'>
						<option value="${approver_edit.partyRelationship.partyRoleTypeFrom.id}">${approver_edit.partyRelationship.partyRoleTypeFrom.name}</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.salutation"/></td>
			<td width="1%" align="center">:</td>
			<td><input size="10" class="input-disabled" disabled value="${partyFrom.salutation}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="approver.name"/></td>
			<td width="1%" align="center">:</td>
			<td><input class="inputbox input-disabled" disabled value="${partyFrom.fullName}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.npwp"/></td>
			<td width="1%" align="center">:</td>
			<td><input class="inputbox input-disabled" disabled value="${partyFrom.taxCode}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.siup"/></td>
			<td width="1%" align="center">:</td>
			<td><input class="inputbox input-disabled" disabled value="${partyFrom.permitCode}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.status"/></td>
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
	
	<br/>
	
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
		<div id="address" dojoType="ContentPane" label='<spring:message code="customer.address.information"/>' class="tab-pages" ${lastPanel == 'address' ? 'selected="true"' : ''}>
			<div class="toolbar-clean">
				<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${partyFrom.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
				<div class="item-navigator">&nbsp;</div>
			</div>
			<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th width="1%"><div style="width: 45px;">&nbsp;</div></th>
	  	  	  	<th width="10%"><spring:message code="postaladdress.type"/></th>
	  	  	  	<th width="10%"><spring:message code="postaladdress.name"/></th>
	  	  	  	<th width="10%"><spring:message code="postaladdress.detail"/></th>
	  	  	  	<th width="5%"><spring:message code="postaladdress.postalcode"/></th>
	  	  	  	<th width="8%"><spring:message code="postaladdress.city"/></th>
	  	  	  	<th width="5%"><spring:message code="sirius.default"/></th>
	  	  	  	<th width="5%"><spring:message code="sirius.status"/></th>
				<th width="15%"><spring:message code="sirius.note"/></th>
			</tr>
			<c:forEach items="${partyFrom.postalAddresses}" var="postal">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
				</td>
				<td nowrap="nowrap">
					<c:forEach items='${postal.addressTypes}' var='type'>
						<c:if test='${type.enabled}'>
							${type.type.normalizedName},
						</c:if>
					</c:forEach>
				</td>
				<td>${postal.addressName}</td>
				<td>${postal.address}</td>
				<td>${postal.postalCode}</td>
				<td>${postal.city.name}</td>
				<td>
					<c:if test='${postal.selected}'><spring:message code="sirius.yes"/></c:if>
					<c:if test='${!postal.selected}'><spring:message code="sirius.no"/></c:if>
				</td>
				<td>
					<c:if test='${postal.enabled}'><spring:message code="sirius.active"/></c:if>
					<c:if test='${!postal.enabled}'><spring:message code="sirius.inactive"/></c:if>
				</td>
				<td>${postal.note}</td>
			</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="9">&nbsp;</td></tr>
			</table>
		</div>

		<div id="contact" dojoType="ContentPane" label='<spring:message code="sirius.contact.information"/>' class="tab-pages" refreshOnShow="true" ${lastPanel == 'contact' ? 'selected="true"' : ''}>
			<div class="toolbar-clean">
				<a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${partyFrom.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>"><span><spring:message code="contactinformation.new"/></span></a>
				<div class="item-navigator">&nbsp;</div>
			</div>
			<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th width="1%"><div style="width: 45px;">&nbsp;</div></th>
				<th width="15%"><spring:message code="contactmechanism.name"/></th>
				<th width="15%"><spring:message code="contactmechanism.department"/></th>
				<th width="15%"><spring:message code="contactmechanism.type"/></th>
				<th width="15%"><spring:message code="contactmechanism.detail"/></th>
				<th width="15%"><spring:message code="sirius.status"/></th>
				<th width="20%"><spring:message code="sirius.note"/></th>
			</tr>
			<c:forEach items="${partyFrom.contactMechanisms}" var="contact">
				<tr>
					<td class="tools">
						<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${contact.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
						<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${contact.id}&party=${contact.party.id}&relationshipId=${approver_edit.id}&uri=approverpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
					</td>
<!-- 					Isi setiap Baris disini -->
					<td>${contact.contactName}</td>
					<td>${contact.department}</td>
					<td>${contact.contactMechanismType}</td>
					<td>${contact.contact}</td>
					<td>
						<c:if test='${contact.active}'><spring:message code="sirius.active"/></c:if>
						<c:if test='${!contact.active}'><spring:message code="sirius.inactive"/></c:if>
					</td>
					<td>${contact.note}</td>
				</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
			</table>
		</div>

	</div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${approver_edit.createdBy.fullName}'/> (<fmt:formatDate value='${approver_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${approver_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${approver_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		save();
	});
});

function save() {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "<c:url value='/page/approveredit.htm?relationshipId='/>" + ${approver_edit.id});
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
					
					let url = "<c:url value='/page/approverpreedit.htm?id='/>"+json.data.relationshipId;;
					
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