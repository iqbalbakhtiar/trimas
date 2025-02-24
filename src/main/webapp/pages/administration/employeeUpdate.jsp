<%@ include file="/common/sirius-general-top.jsp"%>					  	
						<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/employeeview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.edit}'>
								<a class="item-button-save save-employee" ><span><spring:message code="sirius.save"/></span></a>
							</c:if>
					  	</div>
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="employee_edit" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="24%" align="right"><spring:message code="employee.code"/> :</td>
							  	  	<td width="70%">
							  	  		<input class='input-disabled' value="${employee_edit.code}" disabled/>
                                		<input style="visibility: hidden;" name="relationshipId" value="${relationship.id}" readonly="readonly"/>
                               		</td>
 								</tr>
								<tr>
 									<td align="right"><spring:message code="organization"/> :</td>
									<td>
										<select id="org" class="combobox-ext input-disabled">
									    <c:if test='${not empty relationship}'>
											<option value='${relationship.partyTo.id}'>${relationship.partyTo.fullName}</option>
									    </c:if>
										</select>
									</td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="party.salutation"/> :</td>
									<td><form:input path="salutation" cssClass="inputbox input-disabled" readonly="true"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="employee.name"/> :</td>
									<td><form:input id="name" path="fullName" cssClass="inputbox input-disabled" readonly="true"/></td>
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
									<td><input id="birthDate" name="birthDate" value="<fmt:formatDate value='${employee_edit.birthDate}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
	 							</tr>
	                            <tr>
	                              	<td align="right"><spring:message code="sirius.active"/> :</td>
	                            	<td>
	                            		<input type="radio" name='active' value='true' ${relationship.active ? "checked='checked'":""}><spring:message code="sirius.yes"/></input>
	                            		<input type="radio" name='active' value='false' ${!relationship.active ? "checked='checked'":""}><spring:message code="sirius.no"/></input>
	                            	</td>
	                            </tr>
                                <tr>
 									<td align="right"><spring:message code="party.picture"/> :</td>
									<td>
										<input type="file" name="file"/>
										<c:if test='${employee_edit.picture != null}'>
											<a href="javascript:openpopup('<c:url value='/page/partyshowimage.htm?picture=${employee_edit.picture}'/>');"><spring:message code="sirius.show"/></a>
										</c:if>
									</td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="sirius.note"/> :</td>
									<td><form:textarea path="note" rows="6" cols="45"/></td>
 								</tr>
 								</table>
 								<br/>
 								<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
									<div id="address" dojoType="ContentPane" label='<spring:message code="postaladdress"/>' class="tab-pages">
										<div class="toolbar-clean">
											<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${employee_edit.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
											<div class="item-navigator">&nbsp;</div>
										</div>
										<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
										<tr>
											<th width="1%"><div style="width: 45px;">&nbsp;</div></th>
								  	  	  	<th width="30%"><spring:message code="postaladdress.address"/></th>
								  	  	  	<th width="15%"><spring:message code="postaladdress.province"/></th>
								  	  	  	<th width="15%"><spring:message code="postaladdress.city"/></th>
								  	  	  	<th width="15%"><spring:message code="sirius.type"/></th>
									  	  	<th width="8%"><spring:message code="sirius.status"/></th>
											<th width="20%"><spring:message code="sirius.default"/></th>
										</tr>
										<c:forEach items="${employee_edit.postalAddresses}" var="postal">
										<tr>
											<td class="tools">
												<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
											</td>
											<td>${postal.address}</td> 
											<td>${postal.city.name}</td>
											<td>${postal.province.name}</td>
											<td nowrap="nowrap">
												<c:forEach items='${postal.addressTypes}' var='type'>
													<c:if test='${type.enabled}'>
														${type.type.normalizedName},
													</c:if>
												</c:forEach>
											</td>          
											<td>
												<c:if test='${postal.enabled}'><spring:message code="sirius.active"/></c:if>
												<c:if test='${!postal.enabled}'><spring:message code="sirius.inactive"/></c:if>
											</td>
											<td>
												<c:if test='${postal.selected}'><spring:message code="sirius.yes"/></c:if>
												<c:if test='${!postal.selected}'><spring:message code="sirius.no"/></c:if>
											</td>
										</tr>
										</c:forEach>
										<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
										</table>
									</div>
									<div id="contact" dojoType="ContentPane" label='<spring:message code="contactmechanism"/>' class="tab-pages" refreshOnShow="true">
										<div class="toolbar-clean">
											<a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${employee_edit.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>"><span><spring:message code="contactmechanism.new"/></span></a>
											<div class="item-navigator">&nbsp;</div>
										</div>
										<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
										<tr>
											<th width="4%">&nbsp;</th>
											<th width="15%"><spring:message code="contactmechanism.contact"/></th>
											<th width="8%"><spring:message code="sirius.type"/></th>
											<th width="8%"><spring:message code="sirius.status"/></th>
											<th width="20%"><spring:message code="sirius.note"/></th>
										</tr>
										<c:forEach items="${employee_edit.contactMechanisms}" var="contact">
										<tr>
											<td class="tools">
												<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${contact.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>			
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${contact.id}&party=${contact.party.id}&relationshipId=${relationship.id}&uri=employeepreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
											</td>
											<td nowrap="nowrap">${contact.contact}</td> 
											<td>${contact.contactMechanismType}</td>          
											<td>
												<c:if test='${contact.active}'><spring:message code="sirius.active"/></c:if>
												<c:if test='${!contact.active}'><spring:message code="sirius.inactive"/></c:if>
											</td>
											<td>${contact.note}</td>
										</tr>
										</c:forEach>
										<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
										</table>
									</div>
								</div>
 							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${employee_edit.createdBy.fullName}'/> (<fmt:formatDate value='${employee_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${employee_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${employee_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
					<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.save-employee').click(function()
	{
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		var xhr = new XMLHttpRequest();
		xhr.open('POST', "<c:url value='/page/employeeedit.htm'/>");
		xhr.responseType = 'json';
		
		if(xhr.readyState == 1)
		{
			$dialog.empty();
			$dialog.html('<spring:message code="notif.updating"/>');
			$dialog.dialog('open');
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
						window.location="<c:url value='/page/employeepreedit.htm?id='/>"+json.id;
					}
					else
					{
						$dialog.empty();
						$dialog.html('Proccess fail,reason :<br/>'+json.message);
					}
				}
			}
		};
		
		xhr.send(new FormData($('#editForm')[0]));
	});

    $('.checkall').click(function () {
        $('.check').prop("checked", this.checked);
    });
});
</script>