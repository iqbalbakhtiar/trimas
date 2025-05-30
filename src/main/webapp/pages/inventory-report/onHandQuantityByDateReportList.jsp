<%@ include file="/common/sirius-general-top.jsp" %>
<style type="text/css">
        .hidden-row{
            display: none;
        }
</style>
<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/onhandquantitybydatereportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="OnHandQuantityByDateReport.xls" href="#" onclick="return ExcellentExport.excel(this, 'onhand', 'On Hand Quantity By Date Report');"><span><spring:message code="sirius.export"/></span></a>
</div>

<div class="main-box">
    <table cellpadding="0" cellspacing="0" width="100%">
	    <tr>
	        <td colspan="3"><strong>MUTATION REPORT</strong></td>
	    </tr>
	    <tr><td colspan="3">&nbsp;</td></tr>
	    <tr>
	        <td width="109"><spring:message code="sirius.organization"/></td>
	        <td width="10">&nbsp;:</td>
	        <td width="1110"><c:out value='${organization.fullName}'/></td>
	    </tr>
	    <c:if test="${not empty criteria.facility}">
		    <tr>
		        <td><spring:message code="facility"/></td>
		        <td>&nbsp;:</td>
		        <td><c:out value="${facility.name}"/></td>
		    </tr>
	    </c:if>
	    <c:if test="${not empty criteria.productCategory}">
		    <tr>
		        <td><spring:message code="product.category"/></td>
		        <td>&nbsp;:</td>
		        <td><c:out value="${productCategory.name}"/></td>
		    </tr>
	    </c:if>
	    <tr>
	        <td><spring:message code="sirius.period"/></td>
	        <td>&nbsp;:</td>
	        <td><fmt:formatDate value="${criteria.dateFrom}" pattern="dd MMMM yyyy"/> - <fmt:formatDate value="${criteria.dateTo}" pattern="dd MMMM yyyy"/></td>
	    </tr>
	    <tr><td colspan="3">&nbsp;</td></tr>
	</table>
	<c:set var="totalKg" value="0"/>
	<c:set var="totalBale" value="0"/>
	<table width="100%" cellpadding="5" cellspacing="0" class="report-table" id="onhand">
		<tr class="hidden-row">
			<td><spring:message code="sirius.organization"/></td>
			<td>:</td>
			<td><c:out value='${organization.fullName}'/></td>
		</tr>
		<c:if test="${not empty criteria.facility}">
		    <tr class="hidden-row">
		        <td><spring:message code="facility"/></td>
		        <td>:</td>
		        <td><c:out value="${facility.name}"/></td>
		    </tr>
	    </c:if>
	    <c:if test="${not empty criteria.productCategory}">
		    <tr class="hidden-row">
		        <td><spring:message code="product.category"/></td>
		        <td>:</td>
		        <td><c:out value="${productCategory.name}"/></td>
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
			<th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product"/></th>
			<th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="container"/></th>
			<th width="20%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product.onhand"/> (Kg)</th>
			<c:if test="${productCategory.categoryType eq 'MATERIAL'}">
				<th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="product.onhand"/> (Bale)</th>
			</c:if>
		</tr>
		<c:forEach items="${reports}" var="onhand" varStatus="status">
			<tr>
				<td><fmt:formatDate value="${onhand.date}" pattern="dd-MM-yyyy"/></td>
				<td><c:out value="${onhand.productName}"/></td>
				<td><c:out value="${onhand.containerName}"/></td>
				<td align="right"><fmt:formatNumber value="${onhand.quantity}" pattern="#,##0.00"/></td>
				<c:if test="${productCategory.categoryType eq 'MATERIAL'}">
					<td align="right"><fmt:formatNumber value="${onhand.bale}" pattern="#,##0.00"/></td>
				</c:if>
			</tr>
			<c:set var="totalKg" value="${totalKg + onhand.quantity}"/>
			<c:set var="totalBale" value="${totalBale + onhand.bale}"/>
		</c:forEach>
		<tr>
			<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></td>
			<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></td>
			<td align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><strong>Total</strong></td>
			<td align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><fmt:formatNumber value="${totalKg}" pattern="#,##0.00"/></td>
			<c:if test="${productCategory.categoryType eq 'MATERIAL'}">
				<td align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><fmt:formatNumber value="${totalBale}" pattern="#,##0.00"/></td>
			</c:if>
		</tr>
	</table>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
</script>