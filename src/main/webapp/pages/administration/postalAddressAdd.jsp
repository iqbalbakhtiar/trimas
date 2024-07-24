<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" ><span><spring:message code="sirius.back"/></span></a>
						<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
					</div>					  
					<div class="main-box">
						<sesform:form id='addForm' name="addForm" method="post" modelAttribute="postalAddress_add">
						<table width="100%" style="border:none">
                        <tr> 	
                             <td align="right"><spring:message code="sirius.name"/> :</td>
                             <td><input class="input-disabled" size="36" disabled value="${postalAddress_add.party.fullName}"/></td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.country"/> :</td>
                             <td>
                                 <select name="country" id="country" class="combobox-ext" onchange="getprovince('country')">
                                   	<option value="">--Country--</option>
                                   	<c:forEach items="${countries}" var="coun">
						  				<option value="${coun.id}">${coun.name}</option>
						  			</c:forEach>
                                 </select>                                    
                             </td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.province"/> :</td>
                             <td>
								<select name="province" id="province" onchange="getcity('province')" class="combobox-ext">
									<option value="">--Provinsi--</option>
						  		</select>          
                             </td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.city"/> :</td>
							 <td>
								<select id="city" name="city" class="combobox-ext">	
									<option value="">--City--</option>								
								</select>                                                         
							 </td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.address"/> :</td>
                             <td><form:textarea id='address' path="address" cols="45" rows="6"/></td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
                             <td><form:textarea path='note' cols='45' rows='5'/></td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="sirius.status"/> :</td>
                             <td>
                             	 <form:radiobutton path="enabled" value='true' label='Active'/>
                                 <form:radiobutton path="enabled" value='false' label='Inactive'/>
                             </td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right"><spring:message code="sirius.default"/> :</td>
                             <td>
                             	 <form:radiobutton path="selected" value='true' label='Yes'/>
                                 <form:radiobutton path="selected" value='false' label='No'/>
                             </td>
                         </tr>
                         <tr>
                             <td nowrap="nowrap" align="right" valign="top"><spring:message code="postaladdress.type"/> :</td>
                             <td>
                                 <c:forEach items='${types}' var='type' varStatus='status'>
                                     <form:checkbox path="items[${status.index}].actived" value="true" label="${type}"/><br/>
                                     <form:input type="hidden" path="items[${status.index}].postalType" size="36" value="${type}"/>
                                 </c:forEach>
                             </td>
                         </tr>
                        </table>
						</sesform:form>
					</div>
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
	
	$(function()
	{
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function()
		{
			if(!$('#province').val())
			{
				alert('<spring:message code="postaladdress.province"/> <spring:message code="notif.empty"/> !');
				return;
			}

			if(!$('#city').val())
			{
				alert('<spring:message code="postaladdress.city"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if(!$('#address').val())
			{
				alert('<spring:message code="postaladdress.address"/> <spring:message code="notif.empty"/> !');
				return;
			}

			$.ajax({
				url:"<c:url value='/page/postaladdressadd.htm'/>",
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
							<c:if test='${empty redirectURL}'>
								window.location="<c:url value='/page/partypreedit.htm?id=${postalAddress_add.party.id}'/>";
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
		
		$('.item-button-back').click(function()
		{
			<c:if test='${empty redirectURL}'>
				window.location="<c:url value='/page/partypreedit.htm?id=${postalAddress_add.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}'/>";
			</c:if>
		});
	});
	
	$('#province').change(function(){
		getCity($(this).val());
	});

	getProvince();
	
</script>