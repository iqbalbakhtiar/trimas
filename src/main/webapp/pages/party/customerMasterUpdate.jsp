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
     	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<style type="text/css" media="screen">
		@import url("<c:url value='/css/jquery-ui-1.8.2.custom.css'/>");
    </style>
    <script type="text/javascript" src="assets/dialog.js"></script>
	<script type="text/javascript">
		function updateSupplier()
		{
			document.addForm.submit();
		}

		function save(){
				document.addForm.action = "<c:url value='/page/customermasteredit.htm'/>";
				document.addForm.submit();
		}
	</script>
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
            <td width="60%">${breadcrumb }</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle }</h1>

					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/customermasterview.htm'/>"><span><spring:message code='sirius.list'/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code='sirius.save'/></span></a>
					  	</div>
					  	
                        <div class="main-box">
                        	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="customer_edit" enctype="multipart/form-data">
                            <table width="100%" style="border:none">
                          	<tr>
 									<td align="right"><spring:message code='sirius.code'/> : </td>
                        
									<td><form:input cssClass='check_field' id='code' path="code" size='30'/></td>
									<td><form:errors path="code"/></td>
 								</tr>
 								<tr>
 									<td align="right"><spring:message code='customer.name.first'/>:</td>
                          
									<td><form:input cssClass='check_field' id='firstName' path="firstName" size='30'/></td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code='customer.name.middle'/> :</td>
                       
									<td><form:input cssClass='check_field' id='middleName' path="middleName" size='30'/></td>
									<td><form:errors path="middleName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code='customer.name.last'/> :</td>
           
									<td><form:input cssClass='check_field' id='lastName' path="lastName" size='30'/></td>
									<td><form:errors path="lastName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code='customer.taxcode'/> : </td>
                              
									<td><form:input path="taxCode"  /></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code='customer.salutation'/> :</td>
         
									<td><form:input path="salutation"  /></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code='customer.picture'/> :</td>
									<td>
										<input type="file" name="file" src="${party.picture}"/>&nbsp;
										<c:if test='${party.picture != null}'>
										 &nbsp;&nbsp;&nbsp;	<a href="javascript:openpopup('<c:url value='/page/partyshowimage.htm?id=${party.id}'/>');">Show</a>
										</c:if>
									</td>
	 							</tr>
                            <tr>
                                <td align="right" valign="top"><spring:message code='customer.note'/> :</td>
                                <td><textarea  cols="55" rows="6">${party.note}</textarea></td>										
                            </tr>
                            </table>
                            </sesform:form>
                            <div class="clears">&nbsp;</div>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="address" dojoType="ContentPane" label="Postal Address" class="tab-pages" refreshOnShow="true">
                                	<div class="toolbar-clean">
					<c:if test='${postalAccess.add}'>
						<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${party.id}&uri=customermasterpreedit.htm'/>"><span><spring:message code='postaladdress'/></span></a>
                                        </c:if>
					<div class="item-navigator">&nbsp;</div>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                    	<th width="7%">&nbsp;</th>
                                        <th width="48%"><spring:message code='postaladdress.address'/></th>
                                      	<th width="37%"><spring:message code='postaladdress.type'/></th>
                                      	<th width="8%"><spring:message code='postaladdress.status'/></th>
                                      	<th width="7%"><spring:message code='postaladdress.default'/></th>
                                    </tr>
                                    <c:forEach items="${party.postalAddresses}" var="postal">
                                    <tr>
                                    	<td class="tools">
						  <c:if test='${postalAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&uri=customermasterpreedit.htm'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
						</c:if>
						  <c:if test='${postalAccess.delete}'>
							<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&uri=customermasterpreedit.htm'/>');"><span><spring:message code='sirius.delete'/></span></a>
						</c:if>
					</td>
                                        <td><c:out value='${postal.address}'/></td> 
                                        <td>
                                            <c:forEach items='${postal.addressTypes}' var='type'>
                                                <c:if test='${type.enabled}'><c:out value='${type.type}'/>,</c:if>
                                            </c:forEach>
                                        </td>          
                                        <td>
                                            <c:if test='${postal.enabled}'>Active</c:if>
                                            <c:if test='${!postal.enabled}'>Inactive</c:if>
                                        </td>
                                        <td>
                                            <c:if test='${postal.selected}'>Yes</c:if>
                                            <c:if test='${!postal.selected}'>No</c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="contactPerson" class="tab-pages" dojoType="ContentPane" label="Contact Mechanism">
                                <div class="toolbar-clean">
                                         <c:if test='${contactAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${party.id}'/>"><span><spring:message code='contactmechanism.new'/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <th width="5%">&nbsp;</th>
                                        <th width="32%"><spring:message code='contactmechanism.contact'/></th>
                                      	<th width="15%"><spring:message code='contactmechanism.type'/></th>
                                      	<th width="8%"><spring:message code='contactmechanism.status'/></th>
                                      	<th width="38%"><spring:message code='contactmechanism.note'/></th>
                                    </tr>
                                    <c:forEach items="${party.contactMechanisms}" var="con">
                                    <tr>
                                        <td class="tools">
						<c:if test='${contactAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${con.id}'/>"><span><spring:message code='sirius.edit'/></span></a>
						</c:if>
						<c:if test='${contactAccess.delete}'>
							<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${con.id}'/>');"><span><spring:message code='sirius.delete'/></span></a>
					     </c:if>
                                    	</td> 
                                        <td><c:out value="${con.contact}"/></td> 
                                        <td><c:out value="${con.contactMechanismType}"/></td>          
                                        <td>
                                            <c:if test='${con.active}'>Active</c:if>
                                            <c:if test='${not con.active}'>Inactive</c:if>
                                        </td>
                                        <td><c:out value="${con.note}"/></td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="activeProduct" class="tab-pages" dojoType="ContentPane" label="Active Product">
                                    <div class="toolbar-clean">
                                        <c:if test='${productAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/productpricepreaddcustomer.htm?id=${party.id}'/>"><span><spring:message code='productprice.new'/></span></a>
                                        </c:if>
					<a class="item-button-export-xls" href="<c:url value='/page/customeractivereport.xls?id=${party.id}'/>"><span><spring:message code='sirius.export'/></span></a>
                                    </div>
                                   <div style="width:150%;overflow-x:scroll;overflow-y:hidden;">
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="150%">
                                    <thead>
                                    <tr>
                                      	<th width="3%">&nbsp;</th>
                                      	<th width="15%">For Company</th>
                                      	<th width="8%"><spring:message code='productprice.validfrom'/></th>
                                      	<th width="8%"><spring:message code='productprice.validto'/></th>
                                      	<th width="25%"><spring:message code='productprice.product'/></th>
                                      	<th width="8%"><spring:message code='productprice.price'/></th>
                                      	<th width="8%"><spring:message code='sirius.type'/></th>
                                      	<th width="10%"><spring:message code='productprice.discount'/></th>
                                      	<th width="10%"><spring:message code='productprice.afterdiscount'/></th>
                                      	<th width="10%"><spring:message code='productprice.percentdiscount'/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${party.partyRoles}" var="role">
                                    <c:if test='${role.partyRoleType.id == 1}'>
                                    <c:forEach items='${role.relationfroms}' var='from'>
                                    <c:forEach items='${from.prices}' var='price'>
                                    <tr>
                                        <td class="tools">
						<c:if test='${productAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/productpricepreeditcustomer.htm?id=${price.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
						 </c:if>
                                        </td>
                                        <td nowrap="nowrap"><c:out value="${price.partyRelationship.toRole.party.firstName} ${price.partyRelationship.toRole.party.middleName}" /></td>
                                        <td nowrap="nowrap"><fmt:formatDate value='${price.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                        <td nowrap="nowrap"><fmt:formatDate value='${price.validTo}' pattern='dd-MM-yyyy'/></td> 
                                        <td nowrap="nowrap"><c:out value="${price.product.code} ${price.product.name}" /></td> 
                                        <td nowrap="nowrap"><fmt:formatNumber value="${price.money.amount}" pattern=',##0.00'/></td>
                                        <td nowrap="nowrap"><c:out value="${price.product.type}" /></td>
                                        <td nowrap="nowrap"><fmt:formatNumber value="${price.discount}" pattern=',##0.00'/></td>
                                        <td nowrap="nowrap"><fmt:formatNumber value="${price.money.amount-price.discount}" pattern=',##0.00'/></td>
                                        <td nowrap="nowrap" ><fmt:formatNumber value="${(price.discount)*100}" pattern=',##0.00'/></td>
                                       
                                    </tr>
                                    </c:forEach>
                                    </c:forEach>
                                    </c:if>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    	<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
                                    </tfoot>
                                    </table>
                                    </div>
                                </div>
                                <div id="term" class="tab-pages" dojoType="ContentPane" label="Credit Term">
                                    <div class="toolbar-clean">
                                       <c:if test='${creditAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/credittermpreaddcustomer.htm?parent=${party.id}'/>"><span><spring:message code='creditterm.new'/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="12%"><spring:message code='creditterm.term'/></th>
                                        <th width="14%"><spring:message code='creditterm.validfrom'/></th>
                                        <th width="14%"><spring:message code='creditterm.validto'/></th>
                                        <th width="55%"><spring:message code='creditterm.organization'/></th>
                                    </tr>
                                    <c:forEach items="${party.partyRoles}" var="role">
                                    <c:if test='${role.partyRoleType.id == 1}'>
                                    <c:forEach items='${role.relationfroms}' var='from'>
                                    <c:forEach items='${from.creditTerms}' var='term'>
                                    <tr>
                                        <td class="tools">
						<c:if test='${creditAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/credittermpreeditcustomer.htm?id=${term.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
						</c:if>
                                        </td>
                                        <td><c:out value='${term.term}'/></td>
                                        <td><fmt:formatDate value='${term.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                        <td><fmt:formatDate value='${term.validTo}' pattern='dd-MM-yyyy'/></td> 
                                        <td><c:out value='${term.partyRelationship.toRole.party.firstName} ${term.partyRelationship.toRole.party.middleName} ${term.partyRelationship.toRole.party.lastName}'/></td>
                                    </tr>
                                    </c:forEach>
                                    </c:forEach>
                                    </c:if>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="plafon" class="tab-pages" dojoType="ContentPane" label="Plafon">
                                    <div class="toolbar-clean">
                                        <c:if test='${plafonAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/plafonpreadd.htm?id=${party.id}'/>"><span><spring:message code='plafon.new'/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="12%"><spring:message code='plafon'/></th>
                                        <th width="14%"><spring:message code='plafon.validfrom'/></th>
                                        <th width="14%"><spring:message code='plafon.validto'/></th>
                                        <th width="55%"><spring:message code='plafon.organization'/></th>
                                    </tr>
                                    <c:forEach items="${party.partyRoles}" var="role">
                                    <c:if test='${role.partyRoleType.id == 1}'>
                                    <c:forEach items='${role.relationfroms}' var='from'>
                                    <c:forEach items='${from.plafons}' var='plafon'>
                                    <tr>
                                        <td class="tools">
						<c:if test='${plafonAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/plafonpreedit.htm?id=${plafon.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
						</c:if>
                                        </td>
                                        <td><fmt:formatNumber value='${plafon.plafon}' pattern=',##0.00'/></td>
                                        <td><fmt:formatDate value='${plafon.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                        <td><fmt:formatDate value='${plafon.validTo}' pattern='dd-MM-yyyy'/></td> 
                                        <td><c:out value='${plafon.partyRelationship.toRole.party.firstName} ${plafon.partyRelationship.toRole.party.middleName} ${plafon.partyRelationship.toRole.party.lastName}'/></td>
                                    </tr>
                                    </c:forEach>
                                    </c:forEach>
                                    </c:if>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="followUp" class="tab-pages" dojoType="ContentPane" label="Follow Up">
                                    <div class="toolbar-clean">
                                        <c:if test='${followAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/followuphistorypreadd.htm?id=${party.id}'/>"><span><spring:message code='followup.new'/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="10%"><spring:message code='sirius.date'/></th>
										<th width="10%"><spring:message code='followup.time'/></th>
                                        <th width="15%"><spring:message code='customer.salesman'/></th>
										<th width="15%"><spring:message code='sirius.type'/></th>
                                        <th width="20%"><spring:message code='sirius.note'/></th>
                                    </tr>
                                   	<c:forEach items="${party.partyRoles}" var="role">
					<c:if test='${role.partyRoleType.id == 1}'>
					<c:forEach items='${role.relationfroms}' var='from'>
					<c:forEach items="${from.followUps}" var="follow">
                                    <tr>
                                        <td class="tools">
						<c:if test='${followAccess.edit}'>
							<a class="item-button-edit" href="<c:url value='/page/followuphistorypreedit.htm?id=${follow.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
						</c:if>
						 <c:if test='${followAccess.delete}'>
							<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/followuphistorydelete.htm?id=${follow.id}'/>');"><span><spring:message code='sirius.delete'/></span></a>
						</c:if>
                                        </td>
                                        <td><fmt:formatDate value='${follow.date}' pattern='dd-MM-yyyy'/></td> 
					<td><c:out value='${follow.time}'/></td>
                                        <td><c:out value="${follow.salesman.firstName} ${follow.salesman.middleName} ${follow.salesman.lastName}"/></td>
					<td><c:out value="${follow.followType.normalizedName}"/></td>
                                        <td><c:out value="${follow.description}"/></td>
                                    </tr>
                                    </c:forEach>
                                    </c:forEach>
                                    </c:if>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="6">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="salesPerson" class="tab-pages" dojoType="ContentPane" label="Person In Charge">
                                    <div class="toolbar-clean">
                                       <c:if test='${partyAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/partyinchargepreadd.htm?customer=${party.id}'/>"><span><spring:message code='sirius.new'/> POI</span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                    	<th width="10%">&nbsp;</th>
									  	<th width="35%">&nbsp;<spring:message code='sirius.for'/> <spring:message code='organization'/></th>
									  	<th width="35%">&nbsp;<spring:message code='salesperson'/></th>
									  	<th width="15%">&nbsp;POI</th>
                                    </tr>
                                    <c:forEach items='${party.charges}' var='relation'>
                                    <tr>
                                    	<td class="tools" nowrap="nowrap">
                                    	 <c:if test='${partyAccess.edit}'>
				                          	<a class="item-button-edit"   href="<c:url value='/page/partyinchargepreedit.htm?id=${relation.id}'/>"  title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
				                    	 </c:if>
				                    	 <c:if test='${partyAccess.delete}'>
				                          	<a class="item-button-delete"   href="javascript:showDialog('<c:url value='/page/partyinchargedelete.htm?id=${relation.id}'/>');"  title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
				                    	 </c:if>
				                        </td>
                                        <td>&nbsp;${relation.organization.firstName} ${relation.organization.middleName} ${relation.organization.lastName}</td>
										<td>&nbsp;${relation.person.firstName} ${relation.person.middleName} ${relation.person.lastName}</td> 
										<c:if test="${relation.primaryString == 'YES'}"><td>&nbsp;${relation.relationshipCharge.name}</td> </c:if>	
										<c:if test="${relation.primaryString == 'NO'}"><td>&nbsp;${relation.relationshipCharge.name} 2</td> </c:if>																													
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
							</div>
						</div>
							<div class="info"><spring:message code='sirius.createdby'/> : <c:out value='${party.createdBy.firstName} ${party.createdBy.middleName} ${party.createdBy.lastName}'/> (<fmt:formatDate value='${party.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code='sirius.updatedby'/> : <c:out value='${party.updatedBy.firstName} ${party.updatedBy.middleName} ${party.updatedBy.lastName}'/> (<fmt:formatDate value='${party.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
					</div>
				</div>
			</div>
		</div>
	</div>		
	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>
</body>
</html>
<%@ include file="/common/dialog.jsp"%>
</html>
<script src="assets/dialog.js"></script>
<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>