<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title }</title>
    <%@ include file="/common/sirius-header.jsp"%>
</head>

<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

	<%@ include file="/common/sirius-menu.jsp"%>

	<div id="se-navigator">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="60%">${breadcrumb}</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle}</h1>

					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/customerview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  	
                        <div class="main-box">
                        <sesform:form id="editForm" name="editForm" method="post" modelAttribute="customer_edit">
                            <table width="100%" style="border:none">
                            <tr>
                                <td width="20%" align="right"><spring:message code="customer.code"/> :</td>
                                <td width="80%">
                                	<input type="text" value="${party.code}" readonly="readonly" class="input-disabled"/>
                                	<input style="visibility: hidden;" name="relationshipId" value="${relationship.id}" readonly="readonly"/>
                                </td>			
                            </tr>
 							<tr>
								<td align="right"><spring:message code="sirius.facility"/> :</td>
								<td>
									<form:select id="facility" path="facility" cssClass="combobox-ext input-disabled">
                                        <c:if test='${not empty party.facility}'>
                                            <form:option value='${party.facility.id}' label='${party.facility.name}' />
                                        </c:if>
                                    </form:select>
								</td>
							</tr>
                            <tr>
                              <td align="right"><spring:message code="customer.salutation"/> :</td>
                              <td><form:input path="salutation" /></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="customer.name"/> :</td>
                                <td><input type="text" value="${party.firstName}" size="40" readonly="readonly" class="input-disabled"/></td>
                            </tr>
                            <tr>
								<td align="right"><spring:message code="customer.taxcode"/></td>
								<td><form:input path="taxCode"/></td>
 							</tr>
                            <tr>
                              	<td align="right"><spring:message code="customer.status"/> :</td>
                            	<td>
                            		<form:radiobutton path='active' value='true'/><spring:message code="sirius.active"/>
                            		<form:radiobutton path='active' value='false'/><spring:message code="sirius.inactive"/>
                            	</td>
                            </tr>
							<tr>
								<td align="right"><spring:message code="customer.note"/> :</td>
								<td><form:textarea path="note" rows="6" cols="40"/></td>
							</tr>
                            </table>
                         </sesform:form>
                            <div class="clears">&nbsp;</div>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="address" dojoType="ContentPane" label="<spring:message code="postaladdress"/>" class="tab-pages" refreshOnShow="true">
                                	<div class="toolbar-clean">
									<c:if test='${postalAccess.add}'>
                    						<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${customer_edit.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
									  </c:if>
                                        <div class="item-navigator">&nbsp;</div>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                    	<th width="5%">&nbsp;</th>
                                        <th width="20%"><spring:message code="postaladdress.address"/></th>
                                        <th width="10%"><spring:message code="postaladdress.country"/></th>
                                        <th width="10%"><spring:message code="postaladdress.province"/></th>
                                        <th width="10%"><spring:message code="postaladdress.city"/></th>
                                        <th width="10%"><spring:message code="postaladdress.district"/></th>
                                        <th width="10%"><spring:message code="postaladdress.subdistrict"/></th>
                                      	<th width="10%"><spring:message code="postaladdress.type"/></th>
                                      	<th width="5%"><spring:message code="postaladdress.status"/></th>
                                      	<th width="30%"><spring:message code="postaladdress.default"/></th>
                                    </tr>
                                    <c:forEach items="${party.postalAddresses}" var="postal">
                                    <tr>
                                    	<td class="tools">
										    <c:if test='${postalAccess.edit}'>
                                                <a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					                        </c:if>
										     <c:if test='${postalAccess.delete}'>
                                                <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>');"><span><spring:message code="sirius.delete"/></span></a>
										     </c:if>
										</td>
                                        <td nowrap="nowrap"><c:out value='${postal.address}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.country.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.province.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.city.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.district}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.subdistrict}'/></td>
                                        <td nowrap="nowrap">
                                            <c:forEach items='${postal.addressTypes}' var='type'>
                                                <c:if test='${type.enabled}'><c:out value='${type.type}'/>,</c:if>
                                            </c:forEach>
                                        </td>          
                                        <td nowrap="nowrap">
                                            <c:if test='${postal.enabled}'><spring:message code="sirius.active"/></c:if>
                                            <c:if test='${!postal.enabled}'><spring:message code="sirius.inactive"/></c:if>
                                        </td>
                                        <td nowrap="nowrap">
                                            <c:if test='${postal.selected}'><spring:message code="sirius.yes"/></c:if>
                                            <c:if test='${!postal.selected}'><spring:message code="sirius.no"/></c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="contactPerson" dojoType="ContentPane" label="<spring:message code="contactmechanism"/>" class="tab-pages">
                                <div class="toolbar-clean">
                                        <c:if test='${contactAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${customer_edit.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>"><span><spring:message code="contactmechanism"/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                    	<th width="5%">&nbsp;</th>
                                        <th width="32%"><spring:message code="contactmechanism.contact"/></th>
                                      	<th width="15%"><spring:message code="contactmechanism.type"/></th>
                                      	<th width="8%"><spring:message code="contactmechanism.status"/></th>
                                      	<th width="38%"><spring:message code="contactmechanism.note"/></th>
                                    </tr>
                                    <c:forEach items="${party.contactMechanisms}" var="con">
                                    <tr>
                                    	<td class="tools">
										<c:if test='${contactAccess.edit}'>
                                                <a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${con.id}&party=${customer_edit.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>"><span><spring:message code="sirius.edit"/></span></a>
										</c:if>
										 <c:if test='${contactAccess.delete}'>
                                                <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${con.id}&party=${customer_edit.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>');"><span><spring:message code="sirius.delete"/></span></a>
									     </c:if>
                                    	</td> 
                                        <td><c:out value="${con.contact}"/></td> 
                                        <td>
											<c:if test="${con.contactMechanismType == 'PHONE'}"><spring:message code="contactmechanism.phone"/></c:if>
											<c:if test="${con.contactMechanismType == 'MOBILE'}"><spring:message code="contactmechanism.mobile"/></c:if>
											<c:if test="${con.contactMechanismType == 'FAX'}"><spring:message code="contactmechanism.fax"/></c:if>
											<c:if test="${con.contactMechanismType == 'EMAIL'}"><spring:message code="contactmechanism.email"/></c:if>
											<c:if test="${con.contactMechanismType == 'WEBSITE'}"><spring:message code="contactmechanism.website"/></c:if>								
                                        </td>          
                                        <td>
                                            <c:if test='${con.active}'><spring:message code="sirius.active"/></c:if>
                                            <c:if test='${not con.active}'><spring:message code="sirius.inactive"/></c:if>
                                        </td>
                                        <td><c:out value="${con.note}"/></td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="term" class="tab-pages" dojoType="ContentPane" label="<spring:message code="creditterm"/>">
                                    <div class="toolbar-clean">
                                        <c:if test='${creditAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/credittermpreaddcustomer.htm?parent=${relationship.id}'/>"><span><spring:message code="creditterm"/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="12%"><spring:message code="creditterm.term"/></th>
                                        <th width="14%"><spring:message code="creditterm.validfrom"/></th>
                                        <th width="14%"><spring:message code="creditterm.validto"/></th>
                                        <th width="55%"><spring:message code="creditterm.organization"/></th>
                                    </tr>
                                    <c:forEach items='${relationship.creditTerms}' var='term'>
                                    <tr>
                                        <td class="tools">
										<c:if test='${creditAccess.edit}'>
											<a class="item-button-edit" href="<c:url value='/page/credittermpreeditcustomer.htm?id=${term.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
										</c:if>
                                        </td>
                                        <td><c:out value='${term.term}'/></td>
                                        <td><fmt:formatDate value='${term.validFrom}' pattern='dd-MM-yyyy'/></td>
                                        <td><fmt:formatDate value='${term.validTo}' pattern='dd-MM-yyyy'/></td>
                                        <td><c:out value='${term.partyRelationship.toRole.party.fullName}'/></td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="plafon" class="tab-pages" dojoType="ContentPane" label="<spring:message code="plafon"/>">
                                    <div class="toolbar-clean">
                                        <c:if test='${plafonAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/plafonpreadd.htm?id=${relationship.id}&uri=customerpreedit.htm'/>"><span><spring:message code="plafon.new"/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="12%"><spring:message code="plafon.plafon"/></th>
                                        <th width="14%"><spring:message code="plafon.validfrom"/></th>
                                        <th width="14%"><spring:message code="plafon.validto"/></th>
                                        <th width="55%"><spring:message code="plafon.organization"/></th>
                                    </tr>
                                    <c:forEach items='${relationship.plafons}' var='plafon'>
                                    <tr>
                                        <td class="tools">
										<c:if test='${plafonAccess.edit}'>
											<a class="item-button-edit" href="<c:url value='/page/plafonpreedit.htm?id=${plafon.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
										</c:if>
                                        </td>
                                        <td><fmt:formatNumber value='${plafon.plafon}' pattern=',##0.00'/></td>
                                        <td><fmt:formatDate value='${plafon.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                        <td><fmt:formatDate value='${plafon.validTo}' pattern='dd-MM-yyyy'/></td> 
                                        <td><c:out value='${plafon.partyRelationship.toRole.party.fullName}'/></td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
							</div>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${party.createdBy.fullName}'/> (<fmt:formatDate value='${party.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${party.updatedBy.fullName}'/> (<fmt:formatDate value='${party.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</body>
</html>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="customer"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/customeredit.htm'/>",
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
							window.location="<c:url value='/page/customerview.htm'/>";
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				},
				error : function(xhr) {
					console.log(xhr.responseText);
				}
			});
		});
	});
</script>