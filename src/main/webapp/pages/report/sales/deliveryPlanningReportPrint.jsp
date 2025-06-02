<%@ include file="/common/tld-common.jsp"%>
<style type="text/css">
.hidden-row{
    display: none;
}
</style>
<table cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td colspan="3"><strong>DELIVERY PLANNING REPORT</strong></td>
    </tr>
    <tr><td colspan="3">&nbsp;</td></tr>
    <tr>
        <td width="109"><spring:message code="sirius.organization"/></td>
        <td width="10">&nbsp;:</td>
        <td width="1110"><c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td><spring:message code="salesorder.type"/></td>
        <td>&nbsp;:</td>
        <td><c:out value="${not empty criteria.salesInternalType ? criteria.salesInternalType : 'ALL'}"/></td>
    </tr>
    <c:if test="${not empty criteria.facility}">
	    <tr>
	        <td><spring:message code="facility"/></td>
	        <td>&nbsp;:</td>
	        <td><c:out value="${facility.name}"/></td>
	    </tr>
    </c:if>
    <c:if test="${not empty criteria.customer}">
	    <tr>
	        <td><spring:message code="customer"/></td>
	        <td>&nbsp;:</td>
	        <td><c:out value="${customer.fullName}"/></td>
	    </tr>
    </c:if>
    <c:if test="${not empty criteria.salesOrderCode}">
	    <tr>
	        <td><spring:message code="salesorder.contract.no"/></td>
	        <td>&nbsp;:</td>
	        <td><c:out value="${criteria.salesOrderCode}"/></td>
	    </tr>
    </c:if>
    <tr>
        <td><spring:message code="sirius.period"/></td>
        <td>&nbsp;:</td>
        <td><fmt:formatDate value="${criteria.dateFrom}" pattern="dd MMMM yyyy"/> - <fmt:formatDate value="${criteria.dateTo}" pattern="dd MMMM yyyy"/></td>
    </tr>
    <tr><td colspan="3">&nbsp;</td></tr>
</table>
<table width="100%" cellpadding="5" cellspacing="0" class="report-table" id="delivery">
	<tr class="hidden-row">
		<td><strong>DELIVERY PLANNING REPORT</strong></td>
	</tr>
	<tr class="hidden-row">
		<td><spring:message code="sirius.organization"/></td>
		<td>:</td>
		<td><c:out value='${organization.fullName}'/></td>
	</tr>
	<tr class="hidden-row">
        <td><spring:message code="salesorder.type"/></td>
        <td>:</td>
        <td><c:out value="${not empty criteria.salesInternalType ? criteria.salesInternalType : 'ALL'}"/></td>
    </tr>
	<c:if test="${not empty criteria.facility}">
	    <tr class="hidden-row">
	        <td><spring:message code="facility"/></td>
	        <td>:</td>
	        <td><c:out value="${facility.name}"/></td>
	    </tr>
    </c:if>
    <c:if test="${not empty criteria.customer}">
	    <tr class="hidden-row">
	        <td><spring:message code="customer"/></td>
	        <td>:</td>
	        <td><c:out value="${customer.fullName}"/></td>
	    </tr>
    </c:if>
    <c:if test="${not empty criteria.salesOrderCode}">
	    <tr class="hidden-row">
	        <td><spring:message code="salesorder.contract.no"/></td>
	        <td>:</td>
	        <td><c:out value="${criteria.salesOrderCode}"/></td>
	    </tr>
    </c:if>
    <tr class="hidden-row">
        <td><spring:message code="sirius.period"/></td>
        <td>:</td>
        <td><fmt:formatDate value="${criteria.dateFrom}" pattern="dd MMMM yyyy"/> - <fmt:formatDate value="${criteria.dateTo}" pattern="dd MMMM yyyy"/></td>
    </tr>
    <tr class="hidden-row"></tr>
	<tr>
		<th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.date"/></th>
		<th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="deliveryplanning"/></th>
		<th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product"/></th>
		<th width="20%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product.quantity"/> (Kg)</th>
		<th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product.quantity"/> (Bale)</th>
	</tr>
	<c:set var="totalKg" value="0"/>
	<c:set var="totalBale" value="0"/>
	<c:forEach items="${reports}" var="sequence" varStatus="status">
		<tr>
			<td><fmt:formatDate value="${sequence.deliveryPlanningSequence.date}" pattern="dd-MM-yyyy"/></td>
			<td><c:out value="${sequence.deliveryPlanningSequence.deliveryPlanning.code}"/></td>
			<td><c:out value="${sequence.product.name}"/></td>
			<td align="right"><fmt:formatNumber value="${sequence.quantity}" pattern="#,##0.00"/></td>
			<td align="right"><fmt:formatNumber value="${sequence.bale}" pattern="#,##0.00"/></td>
		</tr>
		<c:set var="totalKg" value="${totalKg + sequence.quantity}"/>
		<c:set var="totalBale" value="${totalBale + sequence.bale}"/>
	</c:forEach>
	<tr>
		<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></td>
		<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></td>
		<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><strong>Total</strong></td>
		<td align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><fmt:formatNumber value="${totalKg}" pattern="#,##0.00"/></td>
		<td align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><fmt:formatNumber value="${totalBale}" pattern="#,##0.00"/></td>
	</tr>
</table>