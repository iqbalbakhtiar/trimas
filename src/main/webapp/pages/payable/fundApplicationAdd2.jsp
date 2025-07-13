<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/purchaseorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="purchase_form" enctype="multipart/form-data">
	<table width="100%" border="0">
	<tr>
        <td colspan="5" class="pageTitle">AR Ledger Summary</td>
    </tr>
    <tr>
        <td colspan="5" class="pageTitle">&nbsp;</td>
    </tr>
    <tr>
        <td ><spring:message code="organization"/></td>
        <td >:&nbsp;<c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td ><spring:message code="sirius.date"/></td>
        <td >:&nbsp;<fmt:formatDate value='${report.dateFrom}' pattern='dd MMM yyyy' /> - <fmt:formatDate value='${report.dateTo}' pattern='dd MMM yyyy' /></td>
    </tr>
    <tr>
        <td colspan="5" class="pageTitle">&nbsp;</td>
    </tr>
    <tr height="28">
    	<td><input class="checkall" type="checkbox" index=""/></td>
        <td width="28%" align="center" valign="middle" style="height:28px;" class="bordered"><strong>Customer</strong></td>
        <td width="18%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Opening Balance</strong></td>
        <td width="17%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Debit</strong></td>
        <td width="18%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Credit</strong></td>
        <td width="19%" align="center" valign="middle" class="border-top border-bottom border-right"><strong>Closing Balance</strong></td>
    </tr>
    <c:set var="totalOpeningBalance" value="0"/>
    <c:set var="totalDebit" value="0"/>
    <c:set var="totalCredit" value="0"/>
    <c:set var="totalClosingBalance" value="0"/>
    <c:forEach items='${report.details}' var='detail' varStatus="stat">
        <c:if test='${detail.openingBalance > 0 || detail.debit > 0 || detail.credit > 0}'>
        <tr>
        	<td><input id="check${stat.index}" type="checkbox" class="check"/></td>
            <td align="left" valign="top" class="border-bottom border-right border-left"><a href="<c:url value='/page/ledgerdetailreportview.htm?customer=${detail.customer.id}&organization=${criteria.organization}&date='/><fmt:formatDate value='${report.dateTo}' pattern='dd-MM-yyyy'/>"><c:out value='${detail.customer.fullName}'/></a></td>
            <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.openingBalance}' pattern=',##0.00' groupingUsed='true'/></td>
            <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.debit}' pattern=',##0.00' groupingUsed='true'/></td>
            <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.supremeCredit}' pattern=',##0.00' groupingUsed='true'/></td>
            <td align="right" valign="top" class="border-bottom border-right"><fmt:formatNumber value='${detail.closingBalance}' pattern=',##0.00' groupingUsed='true'/></td>
        </tr>
        <c:set var="totalOpeningBalance" value="${totalOpeningBalance+detail.openingBalance}"/>
        <c:set var="totalDebit" value="${totalDebit+detail.debit}"/>
        <c:set var="totalCredit" value="${totalCredit+detail.supremeCredit}"/>
        <c:set var="totalClosingBalance" value="${totalClosingBalance+detail.closingBalance}"/>
        </c:if>
    </c:forEach>
    <tr>
        <td align="right" valign="top" class="border-bottom border-right border-left"><strong><spring:message code="sirius.total"/></strong></td>
        <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${totalOpeningBalance}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${totalDebit}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${totalCredit}' pattern=',##0.00' groupingUsed='true'/></strong></td>
        <td align="right" valign="top" class="border-bottom border-right"><strong><fmt:formatNumber value='${totalClosingBalance}' pattern=',##0.00' groupingUsed='true'/></strong></td>
    </tr>
	</table>
	</sesform:form>

<%@ include file="/common/sirius-general-bottom.jsp"%>