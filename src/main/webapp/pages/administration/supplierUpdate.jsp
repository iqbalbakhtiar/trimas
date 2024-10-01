<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/supplierview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="supplier_edit" enctype="multipart/form-data">
	<table style="border:none" width="100%">
		<tr>
			<td width="34%" align="right"><span>Supplier ID</td>
			<td width="1%" align="center">:</td>
			<td width="64%"><form:input id='code' path="code" disabled='true' class='input-disabled'/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled='true'>
		    		<c:if test='${not empty relationship.partyTo.id}'>
						<form:option value='${relationship.partyTo.id}' label='${relationship.partyTo.fullName}'/>
		    		</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="party.salutation"/></td>
			<td width="1%" align="center">:</td>
			<td><form:input path="salutation" size="10" class="input-disabled" disabled='true'/></td>
		</tr>
		<tr>
			<td align="right"><span>Supplier Name</td>
			<td width="1%" align="center">:</td>
			<td><form:input path="fullName" cssClass="inputbox input-disabled" disabled='true'/></td>
			<td><form:errors path="fullName"/></td>
		</tr>
		<tr>
			<td align="right"><span>NPWP</td>
			<td width="1%" align="center">:</td>
			<td><form:input path="taxCode" cssClass="inputbox input-disabled" disabled='true'/></td>
			<td><form:errors path="taxCode"/></td>
		</tr>
		<tr>
			<td align="right"><span>SIUP</td>
			<td width="1%" align="center">:</td>
			<td><form:input path="permitCode" cssClass="inputbox input-disabled" disabled='true'/></td>
			<td><form:errors path="permitCode"/></td>
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
			<td align="right"><span>Keterangan Tambahan</td>
			<td width="1%" align="center">:</td>
			<td><form:textarea path="note" rows="6" cols="45"/></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	<br/>
	
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
		<div id="address" dojoType="ContentPane" label='<spring:message code="customer.address.information"/>' class="tab-pages" ${lastPanel == 'address' ? 'selected="true"' : ''}>
			<div class="toolbar-clean">
				<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${supplier_edit.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
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
			<c:forEach items="${supplier_edit.postalAddresses}" var="postal">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
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
				<a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${supplier_edit.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>"><span><spring:message code="contactinformation.new"/></span></a>
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
			<c:forEach items="${supplier_edit.contactMechanisms}" var="contact">
				<tr>
					<td class="tools">
						<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${contact.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>			
						<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${contact.id}&party=${contact.party.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
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
		
		<div id="bank" dojoType="ContentPane" label='<spring:message code="customer.bank.information"/>' class="tab-pages" refreshOnShow="true" ${lastPanel == 'bank' ? 'selected="true"' : ''}>
			<div class="toolbar-clean">
				<a class="item-button-new" href="<c:url value='/page/partybankaccountpreadd.htm?party=${supplier_edit.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>"><span><spring:message code="bankaccount.information.new"/></span></a>
				<div class="item-navigator">&nbsp;</div>
			</div>
			<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th width="1%"><div style="width: 45px;">&nbsp;</div></th>
				<th width="10%"><spring:message code="bankaccount.name"/></th>
				<th width="10%"><spring:message code="bankaccount.account.holder"/></th>
				<th width="10%"><spring:message code="bankaccount.accountno"/></th>
				<th width="10%"><spring:message code="sirius.status"/></th>
				<th width="10%"><spring:message code="sirius.note"/></th>
			</tr>
			<c:forEach items="${supplier_edit.partyBankAccounts}" var="bank">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="<c:url value='/page/partybankaccountpreedit.htm?id=${bank.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>			
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/partybankaccountdelete.htm?id=${bank.id}&party=${contact.party.id}&relationshipId=${relationship.id}&uri=supplierpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
				</td>
				<td>${bank.bankAccount.bankName}</td> 
				<td>${bank.bankAccount.holder.fullName}</td>
				<td>${bank.bankAccount.accountNo}</td>          
				<td>
					<c:if test='${bank.enabled}'><spring:message code="sirius.active"/></c:if>
					<c:if test='${!bank.enabled}'><spring:message code="sirius.inactive"/></c:if>
				</td>
				<td>${bank.bankAccount.note}</td>
			</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
			</table>
		</div>
			
	</div>
</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		save();
	});
	
    // Call the function on page load to set the initial state
    togglePaymentSchedule();
    
    // Attach the function to the change event of the radio buttons
    $('input[name="paymentMethod.paymentCollectingType"]').change(function() {
        togglePaymentSchedule();
    });
});

function save() {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "<c:url value='/page/supplieredit.htm?relationshipId='/>" + ${relationship.id});
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
					
					let url = "<c:url value='/page/supplierpreedit.htm?id='/>"+json.data.relationshipId;;
					
					window.location=url;
				} else {
					afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		}
	};
	
	xhr.send(new FormData($('#addForm')[0]));
}

function togglePaymentSchedule() { // For show / hide Payment Schedule depend on Payment Collection
    // Get the value of the selected paymentCollectingType radio button
    var collectingType = $('input[name="paymentMethod.paymentCollectingType"]:checked').val();
    if (collectingType === 'CONTRA_BON') {
        // Show the div and table if the value is 'CONTRA_BON'
        $('#paymentScheduleDiv').show();
        $('#paymentScheduleTable').show();
    } else {
        // Hide the div and table for other values
        $('#paymentScheduleDiv').hide();
        $('#paymentScheduleTable').hide();
    }
}
</script>