<%@ include file="/common/sirius-general-top.jsp"%>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/facilityview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.edit}'>
                            	<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="facility_form">
								<table width="100%" style="border:none">
                                <tr>
                                	<td width="65%" valign="top">
										<table width="100%" style="border:none">
		                                <tr>
											<td width="25%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
										  	<td width="75%"><form:input path='code' size="35" disabled='true' cssClass='input-disabled'/></td>
										</tr>
		                                <tr>
											<td nowrap="nowrap" align="right"><spring:message code="sirius.name"/> :</td>
										  	<td><form:input path='name' size="35" disabled='true' cssClass='input-disabled'/></td>
										</tr>
		                                <tr>
											<td nowrap="nowrap" align="right"><spring:message code="sirius.type"/> :</td>
											<td>
												<form:select id="facilityType" path="facilityType" cssClass="combobox" disabled='true'>
		                                        	<form:option value='${facility_edit.facilityType.id}' label='${facility_edit.facilityType.name}' />
												</form:select>
											</td>
										</tr>
		                                <tr>
		                                    <td nowrap="nowrap" align="right"><spring:message code="productcategory"/> :</td>
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
		                                            <c:if test='${not empty facility_edit.owner}'>
		                                                <form:option value='${facility_edit.owner.id}' label='${facility_edit.owner.fullName}' />
		                                            </c:if>
		                                        </form:select>
		                                       <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
									  		</td>
		                                </tr>
		                                <tr>
											<td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
											<td><form:textarea path="note" cols="55" rows="7"/></td>
										</tr>
				                        <tr>
				                            <td align="right"><spring:message code="postaladdress.country"/> :</td>
				                            <td>
				                                <select name="country" id="country" class="combobox-ext" onchange="getprovince('country')">>
				                                  	<c:forEach items="${countries}" var="coun">
										  				<option ${coun.id eq facility_edit.postalAddress.country.id ? 'selected':''} value="${coun.id}">${coun.name}</option>
										  			</c:forEach>
				                                </select>                                    
				                            </td>
				                        </tr>
				                        <tr>
				                            <td align="right"><spring:message code="postaladdress.province"/> :</td>
				                            <td>
												<select name="province" id="province" onchange="javascript:getcity('province')" class="combobox-ext">
										  			<c:forEach items="${province.items}" var="prov">
										  				<option ${prov.id eq facility_edit.postalAddress.province.id ? 'selected':''} value="${prov.id}">${prov.name}</option>
										  			</c:forEach>
										  		</select>
				                            </td>
				                        </tr>
				                        <tr>
											<td align="right"><spring:message code="postaladdress.city"/> : </td>
											<td>
												<select id="city" name="city" class="combobox-ext">										
													<c:forEach items="${city.items}" var="city">
										  				<option ${city.id == facility_edit.postalAddress.city.id ? 'selected':''} value="${city.id}">${city.name}</option>
										  			</c:forEach>
												</select>
											</td>
										</tr>
		                                <tr>
		                                    <td align="right"><spring:message code="postaladdress.address"/> :</td>
		                                    <td><form:textarea path="postalAddress.address" cols="55" rows="6"/></td>
		                                </tr>
		                                <tr>
		                                    <td align="right" valign="top"><spring:message code="postaladdress.type"/> :</td>
		                                    <td>
		                                        <c:forEach items='${facility_edit.postalAddress.addressTypes}' var='type' varStatus='status'>
		                                            <c:if test='${not empty facility_edit.postalAddress}'>
		                                            	<form:checkbox path="postalAddress.addressTypes[${status.index}].enabled" value="true" label="${type.type}"/><br/>
		                                            </c:if>
		                                        </c:forEach>
		                                    </td>
		                                </tr>
										</table>
									</td>
								</tr>
								</table>
								</br>
	                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">                                
	                                <div id="grid" dojoType="ContentPane" label="Grid" class="tab-pages">
	                                    <div class="toolbar-clean">
	                                        <a class="item-button-new" href="<c:url value='/page/gridpreadd.htm?id=${facility_edit.id}'/>"><span>New Grid</span></a>
	                                        <div class="item-navigator">&nbsp;</div>
	                                    </div>
	                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
	                                    <tr>
	                                      	<th width="1%"><div style="width: 45px;">&nbsp;</div></th>
	                                        <th width="20%"><spring:message code="sirius.code"/></th>
	                                        <th width="80%"><spring:message code="sirius.name"/></th>
	                                    </tr>
	                                    <c:forEach items="${facility_edit.grids}" var="grid">
	                                    <tr>
	                                        <td class="tools">
	                                            <a class="item-button-edit" href="<c:url value='/page/gridpreedit.htm?id=${grid.id}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
											    <c:if test='${grid.deletable}'>
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/griddelete.htm?id=${grid.id}'/>');"><span><spring:message code="sirius.delete"/></span></a>
											    </c:if>
	                                        </td>
	                                        <td>${grid.code}</td> 
	                                        <td>${grid.name}</td>
	                                    </tr>
	                                    </c:forEach>
	                                    <tr class="end-table"><td colspan="3">&nbsp;</td></tr>
	                                    </table>
	                                </div>
	                            </div>
							</sesform:form>
							<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${facility_edit.createdBy.fullName}'/> (<fmt:formatDate value='${facility_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${facility_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${facility_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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

	function save()
	{
		$.ajax({
			url:"<c:url value='/page/facilityedit.htm'/>",
			data:$('#editForm').serialize(),
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
					if(json.status == 'OK')
					{
						$dialog.dialog('close');
						window.location="<c:url value='/page/facilitypreedit.htm?id='/>"+json.id;
					}
					else
					{
						$dialog.empty();
						$dialog.html('<spring:message code="notif.profailed"/> : <br/>'+json.message);
					}
				}
			}
		});
	}
</script>