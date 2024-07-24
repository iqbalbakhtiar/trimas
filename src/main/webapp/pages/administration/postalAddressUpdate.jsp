<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" ><span><spring:message code="sirius.back"/></span></a>
						<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
					</div>					  
					<div class="main-box">
						<sesform:form id='editForm' name="editForm" method="post" modelAttribute="postalAddress_edit">
						<table width="100%" style="border:none">
                        <tr> 	
                            <td width="225" align="right"><spring:message code="party"/> :</td>
                          	<td width="486"><input class="input-disabled" disabled value="${postalAddress_edit.party.fullName}" size="36"/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="postaladdress.country"/> :</td>
                            <td>
                                <select name="country" id="country" class="combobox-ext" onchange="getprovince('country')">>
                                  	<c:forEach items="${countries}" var="coun">
						  				<option ${coun.id eq country.id ? 'selected':''} value="${coun.id}">${coun.name}</option>
						  			</c:forEach>
                                </select>                                    
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="postaladdress.province"/> :</td>
                            <td>
								<select name="province" id="province" onchange="javascript:getcity('province')" class="combobox-ext">
						  			<c:forEach items="${province.items}" var="prov">
						  				<option ${prov.id eq postalAddress_edit.province.id ? 'selected':''} value="${prov.id}">${prov.name}</option>
						  			</c:forEach>
						  		</select>
                            </td>
                        </tr>
                        <tr>
							<td align="right"><spring:message code="postaladdress.city"/> : </td>
							<td>
								<select id="city" name="city" class="combobox-ext">										
									<c:forEach items="${city.items}" var="city">
						  				<option ${city.id == postalAddress_edit.city.id ? 'selected':''} value="${city.id}">${city.name}</option>
						  			</c:forEach>
								</select>
							</td>
						</tr>
                        <tr>
                            <td align="right"><spring:message code="postaladdress.address"/> :</td>
                            <td><form:textarea path="address" cols="45" rows="6"/></td>
                        </tr>
                        <tr>
                            <td align="right" valign="top"><spring:message code="sirius.note"/> :</td>
                            <td><form:textarea path='note' cols='45' rows='5'/></td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.status"/> :</td>
                            <td>
                                <form:radiobutton path="enabled" label="Active" value="true"/>
                                <form:radiobutton path="enabled" label="Inactive" value="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.default"/> :</td>
                            <td>
                                <form:radiobutton path="selected" value='true' label='Yes'/>
                                <form:radiobutton path="selected" value='false' label='No'/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right" valign="top"><spring:message code="postaladdress.type"/> :</td>
                            <td>
                                <c:forEach items='${postalAddress_edit.addressTypes}' var='type' varStatus='status'>
                                	<c:if test="${not empty type.type}">
                                    	<form:checkbox path="addressTypes[${status.index}].enabled" value="true" label="${type.type}"/><br/>
                                   </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        </table>
						</sesform:form>
					</div>
				<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${postalAddress_edit.createdBy.fullName}'/> (<fmt:formatDate value='${postalAddress_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${postalAddress_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${postalAddress_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
		<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	function getprovince(target)
	{
		$.get("<c:url value='/page/geographicsremote.getprovince.json'/>",{id:$("#"+target).val()},function(json)
		{
			$temp = $("#province").val();
			$("#province option").remove();
			$.each(json.items,function(idx,val){
				$("<option value='" + val.id + "'>" + val.name + "</option>").appendTo($("#province"));
			});
	
			$("#province").val($temp);
	
			if(!$("#province").val())
			{
				$("#province").val($("#province option:first").val());
				getcity('province');
			}
		});
	}
	
	function getcity(target)
	{
		$.get("<c:url value='/page/geographicsremote.getcity.json'/>",{id:$("#"+target).val()},function(json)
		{
			$temp = $("#city").val();
			$("#city option").remove();
			$.each(json.items,function(idx,val){
				$("<option value='" + val.id + "'>" + val.name + "</option>").appendTo($("#city"));
			});
	
			$("#city").val($temp);
	
			if(!$("#city").val())
			{
				$("#city").val($("#city option:first").val());
			}
		});
	}

	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function()
		{
			if(!$('#province').val())
			{
				alert('Province cannot be empty!');
				return;
			}

			if(!$('#address').val())
			{
				alert('Address cannot be empty!');
				return;
			}
				
			$.ajax({
				url:"<c:url value='/page/postaladdressedit.htm'/>",
				data:$('#editForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							<c:if test='${empty redirectURL}'>
								window.location="<c:url value='/page/partypreedit.htm?id=${postalAddress_edit.party.id}'/>";
							</c:if>
							<c:if test='${not empty redirectURL}'>
								window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}'/>";
							</c:if>
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
		
		$('.item-button-back').click(function(){
			<c:if test='${empty redirectURL}'>
				window.location="<c:url value='/page/partypreedit.htm?id=${postalAddress_edit.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}'/>";
			</c:if>
		});
	});
</script>