<%@ include file="/common/sirius-general-top.jsp"%>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/facilityview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.add}'>
                            	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="facility_form">
								<table width="100%" style="border:none">
                                <tr>
                                	<td width="65%" valign="top">
                                   	  	<table width="100%" style="border:none">
                                        <tr>
                                            <td width="25%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
                                          	<td width="75%"><form:input id='code' path='code' size="35"/></td>
                                        </tr>
                                        <tr>
                                            <td width="25%" nowrap="nowrap" align="right"><spring:message code="sirius.name"/> :</td>
                                          <td width="75%"><form:input id='name' path='name' size="35"/></td>
                                        </tr>
		                                <tr>
		 									<td align="right"><spring:message code="sirius.status"/> :</td>
											<td>
												<form:radiobutton path="active" value="true" label="Active"/>
												<form:radiobutton path="active" value="false" label="Inactive"/>										
		                                    </td>
			 							</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.type"/> :</td>
                                            <td>
                                                <form:select id="facilityType" path="facilityType" cssClass="combobox">
                                                    <c:forEach items='${types}' var='type'>
                                                        <form:option value='${type.id}' label='${type.name}' />
                                                    </c:forEach>
                                                </form:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="facility.implementation"/> :</td>
                                            <td>
                                                <form:select id="implementation" path="implementation">
                                                    <form:option value='REAL' label='REAL' />
                                                    <form:option value='VIRTUAL' label='VIRTUAL' />
                                                </form:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="facility.owner"/> :</td>
                                            <td>
                                                <form:select id="org" path="owner" cssClass="combobox-ext">
                                                </form:select>
                                               <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
                                            <td><form:textarea path="note" cols="55" rows="7"/></td>
                                        </tr>
				                         <tr>
				                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.country"/> :</td>
				                             <td>
				                                 <select id="country" class="combobox-ext" onchange="getprovince('country')">
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
												<select id="province" onchange="getcity('province')" class="combobox-ext">
													<option value="">--Provinsi--</option>
										  		</select>          
				                             </td>
				                         </tr>
				                         <tr>
				                             <td nowrap="nowrap" align="right"><spring:message code="postaladdress.city"/> :</td>
											 <td>
												<select id="city" name="postalAddress.city" class="combobox-ext">	
													<option value="">--City--</option>								
												</select>                                                         
											 </td>
				                         </tr>
                                  		<tr>
                                            <td align="right"><spring:message code="postaladdress.address"/> :</td>
                                            <td><textarea id='address' name='postalAddress.address' cols="55" rows="6"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td align="right" valign="top"><spring:message code="postaladdress.type"/> :</td>
                                            <td>
                                                <c:forEach items='${postals}' var='type' varStatus='status'>
                                                    <form:checkbox path="types[${status.index}].enabled" label="${type}"/><br/>
                                                    <form:input type="hidden" id='legend' path='types[${status.index}].legend' size="8" value="${type}"/>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </table>
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
	
	$(function(){
	    var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
	    $('.item-button-save').click(function(){
	        if(!$('#code').val())
	        {
	            alert('Code cannot be empty!');
	            return;
	        }
	        
	        if(!$('#name').val())
	        {
	            alert('Name cannot be empty!');
	            return;
	        }
			
			if(!$('#address').val())
	        {
	            alert('Address cannot be empty!');
	            return;
	        }
	        
	        $.ajax({
	            url:"<c:url value='/page/facilityadd.htm'/>",
	            data:$('#addForm').serialize(),
	            method : 'POST',
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
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
	                        window.location="<c:url value='/page/facilitypreedit.htm?id='/>"+json.id;
	                    }
	                    else
	                    {
	                        $dialog.empty();
	                        $dialog.html('Proccess fail,reason :<br/>'+json.message);
	                    }
	                }
	            }
	        });		
	    });
	});
</script>