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
							<a class="item-button-list" href="<c:url value='/page/salesapproverview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  	
                        <div class="main-box">
                        <sesform:form id="editForm" name="editForm" method="post" modelAttribute="salesApprover_edit">
                            <table width="100%" style="border:none">
                            <tr>
                                <td width="20%" align="right"><spring:message code="salesapprover.code"/> :</td>
                                <td width="80%"><input type="text" value="${salesApprover_edit.code}" readonly="readonly" class="input-disabled"/></td>			
                            </tr>
                            <tr>
                              <td align="right"><spring:message code="salesapprover.salutation"/> :</td>
                              <td><form:input path="salutation"/></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="salesapprover.name"/> :</td>
                                <td><input type="text" value="${salesApprover_edit.firstName}" size="40" readonly="readonly" class="input-disabled"/></td>
                            </tr>
                            <tr>
                              	<td align="right"><spring:message code="salesapprover.status"/> :</td>
                            	<td>
                            		<form:radiobutton path='active' value='true'/><spring:message code="sirius.active"/>
                            		<form:radiobutton path='active' value='false'/><spring:message code="sirius.inactive"/>
                            	</td>
                            </tr>
							<tr>
								<td align="right"><spring:message code="salesapprover.note"/> :</td>
								<td><form:textarea path="note" rows="6" cols="40"/></td>
							</tr>
                            </table>
                         </sesform:form>
                            <div class="clears">&nbsp;</div>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="address" dojoType="ContentPane" label="<spring:message code="postaladdress"/>" class="tab-pages" refreshOnShow="true">
                                	<div class="toolbar-clean">
									<c:if test='${postalAccess.add}'>
										<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${salesApprover_edit.id}&uri=salesapproverpreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
									  </c:if>
                                        <div class="item-navigator">&nbsp;</div>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                    	<th width="1%"><div style="width: 45px">&nbsp;</div></th>
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
                                    <c:forEach items="${salesApprover_edit.postalAddresses}" var="postal">
                                    <tr>
                                    	<td class="tools">
										    <c:if test='${postalAccess.edit}'>
												<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&uri=salesapproverpreedit.htm'/>" title="Edit"><span>Edit</span></a>
					                        </c:if>
										     <c:if test='${postalAccess.delete}'>
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&uri=salesapproverpreedit.htm'/>', '<spring:message code="notif.delete"/>');"><span>Del</span></a>
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
                                <div id="contactPerson" class="tab-pages" dojoType="ContentPane" label="<spring:message code="contactmechanism"/>">
                                <div class="toolbar-clean">
                                        <c:if test='${contactAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${salesApprover_edit.id}&uri=salesapproverpreedit.htm'/>"><span><spring:message code="contactmechanism.new"/></span></a>
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
                                    <c:forEach items="${salesApprover_edit.contactMechanisms}" var="con">
                                    <tr>
                                    	<td class="tools">
										<c:if test='${contactAccess.edit}'>
											<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${con.id}&party=${salesApprover_edit.id}&uri=salesapproverpreedit.htm'/>"><span>Edit</span></a>
										</c:if>
										 <c:if test='${contactAccess.delete}'>
											<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${con.id}&party=${salesApprover_edit.id}&uri=salesapproverpreedit.htm'/>', '<spring:message code="notif.delete"/>');"><span>Del</span></a>
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
							</div>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${salesApprover_edit.createdBy.firstName} ${salesApprover_edit.createdBy.middleName} ${salesApprover_edit.createdBy.lastName}'/> (<fmt:formatDate value='${salesApprover_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${salesApprover_edit.updatedBy.firstName} ${salesApprover_edit.updatedBy.middleName} ${salesApprover_edit.updatedBy.lastName}'/> (<fmt:formatDate value='${salesApprover_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="salesapprover"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/salesapproveredit.htm'/>",
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
							window.location="<c:url value='/page/salesapproverview.htm'/>";
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