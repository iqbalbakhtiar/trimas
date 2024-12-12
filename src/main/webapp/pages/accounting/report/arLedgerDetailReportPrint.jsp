<%@ include file="/common/tld-common.jsp"%>
<table border="0" width="100%" cellpadding="3" cellspacing="0" id="size">
    <tr>
        <td colspan="6" class="pageTitle">AR Ledger Detail</td>
    </tr>
    <tr>
        <td colspan="6" class="pageTitle">&nbsp;</td>
    </tr>
    <tr>
        <td><spring:message code="organization"/></td>
        <td>:&nbsp;<c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td><spring:message code="sirius.date"/></td>
        <td>:&nbsp;<fmt:formatDate value='${report.dateFrom}' pattern='dd MMM yyyy' /> - <fmt:formatDate value='${report.dateTo}' pattern='dd MMM yyyy' /></td>
    </tr>
    <tr>
        <td><spring:message code="customer"/></td>
        <td>:&nbsp;<c:out value='${not empty criteria.customer ? customer.fullName : "All Customer"}'/></td>
    </tr>
    <tr>
        <td colspan="6" class="pageTitle">&nbsp;</td>
    </tr>
    <c:forEach  items='${report.reports}' var='list'>
        <tr>
            <td colspan="6"><strong><c:out value='${list.customer}'/></strong></td>
        </tr>
        <tr height="28">
            <td width="8%" align="center" valign="middle" class="bordered"><strong>Date</strong></td>
            <td width="12%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Type</strong></td>
            <td width="45%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Description</strong></td>
            <td width="10%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Debit</strong></td>
            <td width="10%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Credit</strong></td>
            <td width="15%" align="center" valign="middle" class="border-top border-bottom border-right"><strong><spring:message code="sirius.amount"/></strong></td>
        </tr>
        <tr>
            <td align="right" valign="top" class="border-bottom border-right border-left"><fmt:formatDate value='${report.dateFrom}' pattern="dd MMM yyyy"/></td>
            <td align="left" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="left" valign="top" class="border-bottom border-right"><strong>Opening Balance</strong></td>
            <td align="right" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="right" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${list.opening}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        </tr>
        <c:set var='amount' value='${list.opening}'/>
        <c:forEach  items='${list.details}' var='detail'>
            <tr>
                <td align="right" valign="top" class="border-bottom border-right border-left"><fmt:formatDate value='${detail.date}' pattern="dd MMM yyyy"/></td>
                <c:if test='${not empty detail.billing}'>
                    <td align="left" valign="top" class="border-bottom border-right">Billing</td>
                    <td align="left" valign="top" class="border-bottom border-right"><a href="<c:url value='/page/billingpreedit.htm?id=${detail.billing.id}'/>">&nbsp;<c:out value='${detail.billing.code}'/></a></td>
                </c:if>
                <c:if test='${not empty detail.creditMemoManual}'>
                    <td align="left" valign="top" class="border-bottom border-right">Credit Memo <c:out value='${empty detail.creditMemoManual.memoable ? "Manual" : ""}'/></td>
                    <td align="left" valign="top" class="border-bottom border-right"><a href="<c:url value='/page/${detail.creditMemoManual.uri}id=${detail.creditMemoManual.id}'/>">&nbsp;<c:out value='${detail.creditMemoManual.code}'/></a></td>
                </c:if>
                <c:if test='${not empty detail.receiptApplication}'>
                    <td align="left" valign="top" class="border-bottom border-right">Receipt</td>
                    <td align="left" valign="top" class="border-bottom border-right">
                        <a href="<c:url value='/page/receiptpreedit.htm?id=${detail.receiptApplication.receipt.id}'/>">&nbsp;<c:out value='${detail.receiptApplication.receipt.code }'/></a>
                        -
                        <a href="<c:url value='/page/billingpreedit.htm?id=${detail.receiptApplication.billing.id}'/>">&nbsp;<c:out value='${detail.receiptApplication.billing.code}'/></a>
                    </td>
                </c:if>
                <c:set var='amount' value='${amount+detail.debit-detail.credit}'/>
                <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.debit}' pattern=',##0.00' groupingUsed='true'/></td>
                <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.credit}' pattern=',##0.00' groupingUsed='true'/></td>
                <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${amount}' pattern=',##0.00' groupingUsed='true'/></td>
            </tr>
        </c:forEach>
        <tr>
            <td align="right" valign="top" class="border-bottom border-right border-left"><fmt:formatDate value='${report.dateTo}' pattern="dd MMM yyyy"/></td>
            <td align="left" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="left" valign="top" class="border-bottom border-right"><strong>Closing Balance</strong></td>
            <td align="right" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="right" valign="top" class="border-bottom border-right">&nbsp;</td>
            <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${list.closing}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        </tr>
        <tr>
            <td colspan="6">&nbsp;</td>
        </tr>
    </c:forEach>
    <tr>
        <td width="65%" colspan="3" align="left" valign="top" class="bordered">&nbsp;<strong>Total for ${organization.fullName}</strong></td>
        <td width="10%" align="right" valign="top" class="border-top border-bottom border-right"><strong><fmt:formatNumber value='${report.debit}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        <td width="10%" align="right" valign="top" class="border-top border-bottom border-right"><strong><fmt:formatNumber value='${report.credit}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        <td width="15%" align="right" valign="top" class="border-top border-bottom border-right"><strong><fmt:formatNumber value='${report.total}'  pattern=',##0.00' groupingUsed='true'/></strong></td>
    </tr>
</table>