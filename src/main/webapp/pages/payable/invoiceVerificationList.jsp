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
<%--    <%@ include file="/filter/accounting/billingFilter.jsp"%>--%>
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

                        <div class="clears">&nbsp;</div>
                        <div class="item-navigator">
                            <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                                <tr>
                                    <td width="30%" height="30" align="left" valign="middle">
                                        <div class="toolbar-clean">
                                            <div dojoType="Toggler" targetId="filter">
                                                <a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
                                            </div>
                                        </div>
                                    </td>
                                    <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                                </tr>
                            </table>
                        </div>
                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <th width="1%"><div style="width:45px;"></div></th>
                                <th width="8%"><spring:message code="sirius.code"/></th>
                                <th width="5%"><spring:message code="sirius.date"/></th>
                                <th width="12%"><spring:message code="supplier"/></th>
                                <%-- <th width="10%"><spring:message code="sirius.reference"/></th> --%>
                                <th width="5%"><spring:message code="sirius.tax"/></th>
                                <th width="8%"><spring:message code="sirius.unpaid"/></th>
                                <!-- <th width="10%">PO Ref</th> -->
                                <th width="13%"><spring:message code="sirius.note"/></th>
                            </tr>
                            <c:forEach items="${verifications}" var="verif">
                                <tr>
                                    <td class="tools">
                                        <a class="item-button-edit"  href="<c:url value='/page/invoiceverificationpreedit.htm?id=${verif.id}'/>"  title="Edit"><span>Edit</span></a>
                                    </td>
                                    <td nowrap="nowrap"><c:out value="${verif.code}" /></td>
                                    <td nowrap="nowrap"><fmt:formatDate value='${verif.date}' pattern='dd-MM-yyyy'/></td>
                                    <td><c:out value='${verif.supplier.fullName}'/></td>
                                    <%-- <td><a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${verif.goodsReceipt.id}'/>"><c:out value='${verif.goodsReceipt.code}'/></a></td> --%>
                                    <td><c:out value='${verif.tax.taxName}'/></td>
                                    <td unpaid="${verif.id}"><fmt:formatNumber value='${verif.unpaid}' pattern=',##0.00'/></td>
                                    <%-- <td align="center">
                                        <c:forEach items="${verif.goodsReceipt.reference}" var="message" varStatus="status">
                                            ${message}<br/>
                                        </c:forEach>
                                    </td> --%>
                                    <td class="break-over">${verif.note}</td>
                                </tr>
                            </c:forEach>
                            <tr class="end-table"><td colspan="9">&nbsp;</td></tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                            <tr><td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td></tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
