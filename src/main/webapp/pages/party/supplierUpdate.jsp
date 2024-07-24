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
	<title>${title}</title>
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
							<a class="item-button-list" href="<c:url value='/page/supplierview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <a class="item-button-save"><span><spring:message code="sirius.update"/></span></a>
					  	</div>
					  	
                        <div class="main-box">
                        <sesform:form id="editForm" name="editForm" method="post" modelAttribute="supplier_edit">
                            <table width="100%" style="border:none">
                            <tr>
                                <td width="20%" align="right"><spring:message code="supplier.code"/> :</td>
                                <td width="80%"><input type="text" value="${party.code}" readonly="readonly" class="input-disabled"/></td>			
                            </tr>		
                            <tr>
                              <td align="right"><spring:message code="customer.salutation"/> :</td>
                              <td><form:input path="salutation" size='5'/></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="supplier.name"/> : </td>
                                <td><input type="text" value="${party.firstName}" size="40" disabled class="input-disabled"/></td>
                            </tr>
                            <tr>
								<td align="right"><spring:message code="supplier.taxcode"/> :</td>
								<td><form:input path="taxCode"/></td>
 							</tr>
                            <tr>
                                <td align="right"><spring:message code="supplier.accountnumber"/> :</td>
                                <td><form:input type="text" path="accountNumber"/></td>
                            </tr>
                            <tr>
                              	<td align="right"><spring:message code="supplier.statuspkp"/> :</td>
                            	<td>
                            		<form:radiobutton path='pkp' value='true'/><spring:message code="supplier.pkp"/>
                            		<form:radiobutton path='pkp' value='false'/><spring:message code="supplier.nonpkp"/>
                            	</td>
                            </tr>
                            <tr>
                              	<td align="right"><spring:message code="supplier.status"/> :</td>
                            	<td>
                            		<form:radiobutton path='active' value='true'/><spring:message code="sirius.yes"/>
                            		<form:radiobutton path='active' value='false'/><spring:message code="sirius.no"/>
                            	</td>
                            </tr>
							<tr>
								<td align="right"><spring:message code="supplier.note"/> :</td>
								<td><form:textarea path="note" rows="6" cols="45"/></td>
							</tr>
                            </table>
                         </sesform:form>
                            <br/>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="address" dojoType="ContentPane" label="<spring:message code="postaladdress"/>" class="tab-pages" refreshOnShow="true">
									<div class="toolbar-clean">
									<c:if test='${postalAccess.add}'>
										<a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${party.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>"><span><spring:message code="postaladdress.new"/></span></a>
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
                                      	<th width="5%"><spring:message code="postaladdress.default"/></th>
                                    </tr>
                                    <c:forEach items="${party.postalAddresses}" var="postal">
                                    <tr valign="top">
                                    	<td class="tools">
										    <c:if test='${postalAccess.edit}'>
												<a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>" title="Edit"><span>Edit</span></a>
					                        </c:if>
										     <c:if test='${postalAccess.delete}'>
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>');"><span>Del</span></a>
										     </c:if>
										</td>
                                        <td><c:out value='${postal.address}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.country.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.province.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.city.name}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.district}'/></td>
                                        <td nowrap="nowrap"><c:out value='${postal.subdistrict}'/></td>
                                        <td nowrap="nowrap">
                                            <c:forEach items='${postal.addressTypes}' var='type'>
                                                <c:if test='${type.enabled}'><c:out value='${type.type.normalizedName}'/><br/></c:if>
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
                                            <a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${party.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>"><span><spring:message code="contactmechanism.new"/></span></a>
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
											<a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${con.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>"><span>Edit</span></a>
										</c:if>
										 <c:if test='${contactAccess.delete}'>
											<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${con.id}&relationshipId=${partyRelationship.id}&partyUri=supplierpreedit.htm'/>', '<spring:message code="notif.delete"/>');"><span>Del</span></a>
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
                                            <a class="item-button-new" href="<c:url value='/page/credittermpreaddsupplier.htm?parent=${partyRelationship.id}'/>"><span><spring:message code="creditterm.new"/></span></a>
                                        </c:if>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      	<th width="5%">&nbsp;</th>
                                        <th width="12%"><spring:message code="creditterm.term"/></th>
                                        <th width="13%"><spring:message code="creditterm.validfrom"/></th>
                                      	<th width="14%"><spring:message code="creditterm.validto"/></th>
                                        <th width="56%"><spring:message code="creditterm.organization"/></th>
                                    </tr>
                                    <c:forEach items="${party.partyRoles}" var="role">
                                    <c:if test='${role.partyRoleType.id == 2}'>
                                        <c:forEach items='${role.relationfroms}' var='from'>
                                            <c:forEach items='${from.creditTerms}' var='term'>                                    
                                            <tr>
                                                <td class="tools">
        										<c:if test='${creditAccess.edit}'>
        											<a class="item-button-edit" href="<c:url value='/page/credittermpreeditsupplier.htm?id=${term.id}'/>" title="Edit"><span>Edit</span></a>
        										</c:if>
                                                </td>
                                                <td><c:out value='${term.term}'/></td>
                                                <td><fmt:formatDate value='${term.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                                <td><fmt:formatDate value='${term.validTo}' pattern='dd-MM-yyyy'/></td>
                                                <td><c:out value='${term.partyRelationship.toRole.party.fullName}'/></td>
                                            </tr>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:if>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                                    </table>
                                </div>
                                <div id="activeProduct" class="tab-pages" dojoType="ContentPane" label="Active Product">
                                    <div class="toolbar-clean">
                                        <c:if test='${productPriceAccess.add}'>
                                            <a class="item-button-new" href="<c:url value='/page/productpricepreaddsupplier.htm?id=${partyRelationship.id}'/>"><span><spring:message code="productprice"/></span></a>
                                        </c:if>
                                        <%-- <a class="item-button-export-xls" href="<c:url value='/page/customeractivereport.xls?id=${party.id}'/>"><span>Export</span></a> --%>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="150%">
                                    <thead>
                                    <tr>
                                        <th width="4%">&nbsp;</th>
                                        <th width="10%"><spring:message code="productprice.organization"/></th>
                                        <th width="8%"><spring:message code="productprice.validfrom"/></th>
                                        <th width="8%"><spring:message code="productprice.validto"/></th>
                                        <th width="12%"><spring:message code="productprice.product"/></th>
                                        <th width="8%"><spring:message code="productprice.price"/></th>
                                        <th width="8%"><spring:message code="productprice.discount"/></th>
                                        <th width="8%"><spring:message code="productprice.afterdiscount"/></th>
                                        <th width="10%"><spring:message code="productprice.percentdiscount"/></th>
                                        <th width="15%"><spring:message code="currencymanagement"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${party.partyRoles}" var="role">
                                            <c:if test='${role.partyRoleType.id == 2}'>
                                            <c:forEach items='${role.relationfroms}' var='from'>
                                                <c:forEach items='${from.prices}' var='price'>
                                                <tr>
                                                    <td class="tools">
                                                    <c:if test='${productPriceAccess.edit}'>
                                                        <a class="item-button-edit" href="<c:url value='/page/productpricepreeditsupplier.htm?id=${price.id}'/>" title="Edit"><span>Edit</span></a>
                                                    </c:if>
                                                    </td>
                                                    <td nowrap="nowrap"><c:out value="${price.partyRelationship.toRole.party.fullName}" /></td>
                                                    <td nowrap="nowrap"><fmt:formatDate value='${price.validFrom}' pattern='dd-MM-yyyy'/></td> 
                                                    <td nowrap="nowrap"><fmt:formatDate value='${price.validTo}' pattern='dd-MM-yyyy'/></td> 
                                                    <td nowrap="nowrap"><c:out value="${price.product.code} ${price.product.name}" /></td> 
                                                    <td nowrap="nowrap"><fmt:formatNumber value="${price.money.amount}" pattern=',##0.000'/></td>
                                                    <td nowrap="nowrap" ><fmt:formatNumber value="${price.money.amount*(price.discount/100)}" pattern=',##0.00'/></td>
                                                    <td nowrap="nowrap"><fmt:formatNumber value="${price.money.amount-(price.money.amount*price.discount/100)}" pattern=',##0.000'/></td>
                                                    <td nowrap="nowrap"><fmt:formatNumber value="${price.discount}" pattern=',##0.00'/></td>
                                                    <td nowrap="nowrap"><c:out value="${price.money.currency.symbol}" /></td>
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
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : ${party.createdBy.fullName} (<fmt:formatDate value='${party.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : ${party.updatedBy.fullName} (<fmt:formatDate value='${party.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
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

    var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="supplier"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
    $('.item-button-save').click(function()
    {
        $.ajax({
            url:"<c:url value='/page/supplieredit.htm'/>",
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
                        window.location="<c:url value='/page/supplierpreedit.htm?id=${partyRelationship.id}'/>";
                    }
                    else
                    {
                        $dialog.empty();
                        $dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
                    }
                }
            }
        });
    });
});
</script>
