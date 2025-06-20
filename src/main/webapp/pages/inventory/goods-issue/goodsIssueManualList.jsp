<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/goodsIssueManualFilter.jsp"%>
<div class="item-navigator">
    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
        <tr>
            <td width="30%" height="30" align="left" valign="middle">
                <div class="toolbar-clean">
                    <c:if test='${access.add}'>
                        <a class="item-button-new" href="<c:url value='/page/goodsissuemanualpreadd.htm' />"><span><spring:message code="goodsissue.manual.new"/></span></a>
                    </c:if>
                    <div dojoType="Toggler" targetId="filter">
                        <a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
                    </div>
                </div>
            </td>
            <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
        </tr>
    </table>
</div>
<div style="overflow-x: auto;">
    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <th width="1%"><div style="width:45px;"></div></th>
            <th width="8%"><spring:message code="sirius.code"/></th>
            <th width="5%"><spring:message code="sirius.date"/></th>
            <th width="8%"><spring:message code="organization"/></th>
            <th width="8%"><spring:message code="sirius.from"/></th>
            <th width="8%"><spring:message code="sirius.to"/></th>
            <th width="8%"><spring:message code="sirius.createdby"/></th>
            <th width="5%"><spring:message code="sirius.type"/></th>
        </tr>
        <c:forEach items="${issues}" var="issue">
            <tr>
                <td class="tools">
                    <a class="item-button-edit"  href="<c:url value='/page/goodsissuemanualpreedit.htm?id=${issue.id}'/>"  title="Edit"><span>Edit</span></a>
                </td>
                <td><c:out value="${issue.code}" /></td>
                <td><fmt:formatDate value='${issue.date}' pattern='dd-MM-yyyy'/></td>
                <td><c:out value="${issue.organization.fullName}" /></td>
                <td><c:out value="${issue.source.name}" /></td>
                <td><c:out value="${issue.recipient.fullName}" /></td>
                <td><c:out value="${issue.createdBy.fullName}" /></td>
                <td><c:out value="${issue.issueType.capitalizedName}" /></td>
            </tr>
        </c:forEach>
        <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
    </table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
    <tr>
        <td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
    </tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>