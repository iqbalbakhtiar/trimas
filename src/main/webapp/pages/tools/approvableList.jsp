<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/tools/approvableFilter.jsp"%>
<div class="item-navigator">
    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
        <tr>
            <td width="30%" height="30" align="left" valign="middle">
                <div class="toolbar-clean">
                    <div dojoType="Toggler" targetId="filter">
                        <a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
                    </div>
                </div>
            </td>
            <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
        </tr>
    </table>
</div>
<div style="overflow-x: auto;">
    <table style="width:100%;" cellpadding="0" cellspacing="0" class="table-list">
        <tr valign="top">
            <th width="2%">&nbsp;</th>
            <th width="20%"><spring:message code="sirius.code"/></th>
            <th width="10%"><spring:message code="sirius.date"/></th>
            <th width="20%"><spring:message code="organization"/></th>
            <th width="20%"><spring:message code="sirius.type"/></th>
<%--            <th width="15%"><spring:message code="sirius.amount"/></th>--%>
        </tr>
        <c:forEach items="${approvables}" var="app">
            <tr valign="top">
                <td class="tools">
                    <c:if test='${access.edit}'>
                        <a class="item-button-edit" href="<c:url value='/page/${app.uri}?id=${app.reviewID}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
                    </c:if>
                </td>
                <td><c:out value='${app.code}'/></td>
                <td><fmt:formatDate value='${app.date}' pattern='dd-MM-yyyy'/></td>
                <td><c:out value="${app.organization.fullName}"/></td>
                <td><c:out value='${app.approvableType.normalizedName}'/></td>
<%--                <td align="right"><fmt:formatNumber value="${app.total}" pattern=',##0'/></td>--%>
            </tr>
        </c:forEach>
        <tr class="end-table"><td colspan="18">&nbsp;</td></tr>
    </table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
    <tr>
        <td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
    </tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>