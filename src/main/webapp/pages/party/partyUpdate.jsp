<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp" %>
<%@ include file="/common/tld-spring.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
    <title>${title}</title>
    <%@ include file="/common/sirius-header.jsp" %>
    <script type="text/javascript">
        function save() {
            document.editForm.action = "<c:url value='/page/partyedit.htm'/>";
            document.editForm.submit();
        }
    </script>
</head>

<body>

<div id="se-r00">
    <div id="se-r01">&nbsp;</div>
    <div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
    <%@ include file="/common/sirius-menu.jsp" %>

    <div id="se-navigator">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="60%">${breadcrumb}</td>
                <td width="40%" align="right">
                    <%@ include file="/common/welcome.jsp" %>
                </td>
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
                            <a class="item-button-list" href="<c:url value='/page/partyview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
                        </div>

                        <div class="main-box">
                            <sesform:form id="editForm" name="editForm" modelAttribute="party_edit" method="post" enctype="multipart/form-data">
                                <table style="border:none" width="100%">
                                    <tr>
                                        <td width="27%" align="right"><spring:message code="party.code"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td width="71%"><form:input path="code" cssClass="inputbox" disabled="true"/></td>
                                        <td width="2%"><form:errors path="code"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="party.salutation"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td><form:input path="salutation"/></td>
                                        <td><form:errors path="salutation"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">Party Name</td>
                                        <td width="1%" align="center">:</td>
                                        <td><form:input path="fullName" size='45'/></td>
                                        <td><form:errors path="fullName"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="party.code.tax"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td><form:input path="taxCode" cssClass="inputbox"/></td>
                                        <td><form:errors path="taxCode"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="party.birthdate"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td><input id="birthDate" name="birthDate" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="${party_edit.birthDate}" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/></td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="party.picture"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td>
                                            <input type="file" name="file" src="${party_edit.picture}"/>&nbsp;
                                            <c:if test='${party_edit.picture != null}'>
                                                &nbsp;&nbsp;&nbsp; <a href="javascript:openpopup('<c:url value='/page/partyshowimage.htm?id=${party_edit.id}'/>');">Show</a>
                                            </c:if>
                                        </td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><spring:message code="party.note"/></td>
                                        <td width="1%" align="center">:</td>
                                        <td><form:textarea path="note" rows="6" cols="45"/></td>
                                        <td>&nbsp;</td>
                                    </tr>
                                </table>

                                <br/>
                                <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                    <div id="address" dojoType="ContentPane" label="<spring:message code="postaladdress"/>" class="tab-pages">
                                        <div class="toolbar-clean">
                                            <a class="item-button-new" href="<c:url value='/page/postaladdresspreadd.htm?party=${party_edit.id}'/>"><span><spring:message code="postaladdress.new"/></span></a>
                                            <div class="item-navigator">&nbsp;</div>
                                        </div>
                                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <th width="1%"><div style="width: 45px;">&nbsp;</div></th>
                                                <th width="10%"><spring:message code="postaladdress.type"/></th>
                                                <th width="8%"><spring:message code="postaladdress.name"/></th>
                                                <th width="10%"><spring:message code="postaladdress.detail"/></th>
                                                <th width="5%"><spring:message code="postaladdress.postalcode"/></th>
                                                <th width="5%"><spring:message code="postaladdress.city"/></th>
                                                <th width="5%"><spring:message code="sirius.default"/></th>
                                                <th width="5%"><spring:message code="sirius.status"/></th>
                                                <th width="10%"><spring:message code="sirius.note"/></th>
                                            </tr>
                                            <c:forEach items="${party_edit.postalAddresses}" var="postal">
                                                <tr valign="top">
                                                    <td class="tools">
                                                        <a class="item-button-edit" href="<c:url value='/page/postaladdresspreedit.htm?id=${postal.id}&relationshipId=${party_edit.id}&uri=partypreedit.htm'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
                                                        <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/postaladdressdelete.htm?id=${postal.id}&party=${postal.party.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>');" title="Del"><span><spring:message code="sirius.delete"/></span></a>
                                                    </td>
                                                    <td nowrap="nowrap">
                                                        <c:forEach items='${postal.addressTypes}' var='type'>
                                                            <c:if test='${type.enabled}'>
                                                                ${type.type.normalizedName},
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>${postal.addressName}</td>
                                                    <td style="white-space: normal">${postal.address}</td>
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
                                                    <td style="white-space: normal">${postal.note}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr class="end-table">
                                                <td colspan="9">&nbsp;</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="contact" dojoType="ContentPane" label="<spring:message code="contactmechanism"/>" class="tab-pages" refreshOnShow="true">
                                        <div class="toolbar-clean">
                                            <a class="item-button-new" href="<c:url value='/page/contactmechanismpreadd.htm?party=${party_edit.id}'/>"><span><spring:message code="contactmechanism.new"/></span></a>
                                            <div class="item-navigator">&nbsp;</div>
                                        </div>
                                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <th>&nbsp;</th>
                                                <th><spring:message code="contactmechanism.contact"/></th>
                                                <th><spring:message code="contactmechanism.type"/></th>
                                                <th><spring:message code="contactmechanism.status"/></th>
                                                <th><spring:message code="contactmechanism.note"/></th>
                                            </tr>
                                            <c:forEach items="${party_edit.contactMechanisms}" var="contact">
                                                <tr>
                                                    <td class="tools">
                                                        <a class="item-button-edit" href="<c:url value='/page/contactmechanismpreedit.htm?id=${contact.id}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
                                                        <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/contactmechanismdelete.htm?id=${contact.id}&party=${contact.party.id}'/>');"
                                                           title="Del"><span><spring:message code="sirius.delete"/></span></a>
                                                    </td>
                                                    <td>${contact.contact}</td>
                                                    <td>${contact.contactMechanismType}</td>
                                                    <td>
                                                        <c:if test='${contact.active}'>Active</c:if>
                                                        <c:if test='${!contact.active}'>Inactive</c:if>
                                                    </td>
                                                    <td>${contact.note}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr class="end-table">
                                                <td colspan="5">&nbsp;</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="structure" dojoType="ContentPane" label="<spring:message code="relationship.information"/>" class="tab-pages" refreshOnShow="true">
                                        <div class="toolbar-clean">
                                            <c:if test='${relAccess.add}'>
                                                <a class="item-button-new" href="<c:url value='/page/partyrelationshippreadd.htm?party=${party_edit.id}'/>"><span><spring:message code="relationship.new"/></span></a>
                                            </c:if>
                                            <div class="item-navigator">&nbsp;</div>
                                        </div>
                                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <th width="1%">&nbsp;</th>
                                                <th width="28%"><spring:message code="sirius.type"/></th>
                                                <th width="22%"><spring:message code="sirius.from"/></th>
                                                <th width="23%"><spring:message code="sirius.to"/></th>
                                                <th width="13%"><spring:message code="partyrole.from"/></th>
                                                <th width="14%"><spring:message code="partyrole.to"/></th>
                                            </tr>
                                            <c:forEach items="${party_edit.relationships}" var="relation">
                                                <tr>
                                                    <td class="tools">
                                                        <c:if test='${relAccess.delete}'>
                                                            <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/partyrelationshipdelete.htm?id=${relation.id}'/>');" title="Del"><span><spring:message
                                                                    code="sirius.delete"/></span></a>
                                                        </c:if>
                                                    </td>
                                                    <td>${relation.relationshipType.name}</td>
                                                    <td>${relation.partyFrom.fullName}</td>
                                                    <td>${relation.partyTo.fullName}</td>
                                                    <td>${relation.partyRoleTypeFrom.name}</td>
                                                    <td>${relation.partyRoleTypeTo.name}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr class="end-table">
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="bank" dojoType="ContentPane" label='<spring:message code="customer.bank.information"/>' class="tab-pages" refreshOnShow="true" ${lastPanel == 'bank' ? 'selected="true"' : ''}>
                                        <div class="toolbar-clean">
                                            <a class="item-button-new" href="<c:url value='/page/partybankaccountpreadd.htm?party=${party_edit.id}&relationshipId=${relation.id}&uri=customerpreedit.htm'/>"><span><spring:message
                                                    code="bankaccount.information.new"/></span></a>
                                            <div class="item-navigator">&nbsp;</div>
                                        </div>
                                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <th width="1%">
                                                    <div style="width: 45px;">&nbsp;</div>
                                                </th>
                                                <th width="10%"><spring:message code="bankaccount.name"/></th>
                                                <th width="10%"><spring:message code="bankaccount.account.holder"/></th>
                                                <th width="10%"><spring:message code="bankaccount.accountno"/></th>
                                                <th width="10%"><spring:message code="sirius.status"/></th>
                                                <th width="10%"><spring:message code="sirius.note"/></th>
                                            </tr>
                                            <c:forEach items="${party_edit.partyBankAccounts}" var="bank">
                                                <tr>
                                                    <td class="tools">
                                                        <a class="item-button-edit" href="<c:url value='/page/partybankaccountpreedit.htm?id=${bank.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>"
                                                           title="Edit"><span><spring:message code="sirius.edit"/></span></a>
                                                        <a class="item-button-delete"
                                                           href="javascript:showDialog('<c:url value='/page/partybankaccountdelete.htm?id=${bank.id}&party=${contact.party.id}&relationshipId=${relationship.id}&uri=customerpreedit.htm'/>');"
                                                           title="Del"><span><spring:message code="sirius.delete"/></span></a>
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
                                            <tr class="end-table">
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </sesform:form>
                        </div>
                        <div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${party_edit.createdBy.fullName}'/> (<fmt:formatDate value='${party_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message
                                code="sirius.updatedby"/> : <c:out value='${party_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${party_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/sirius-footer.jsp" %>
</div>
</body>
</html>