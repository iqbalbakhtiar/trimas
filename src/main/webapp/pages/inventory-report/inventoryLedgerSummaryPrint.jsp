<%@ include file="/common/tld-common.jsp"%>		
<table cellpadding="3" cellspacing="0" width="100%">
    <tr>
        <td colspan="3"><strong>INVENTORY LEDGER SUMMARY</strong></td>
    </tr>
    <tr>
        <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
        <td width="109"><spring:message code="sirius.organization"/> </td>
        <td width="10">&nbsp;:</td>
      	<td width="1110"><c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td><spring:message code="sirius.periode"/></td>
        <td>&nbsp;:</td>
        <td><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/>&nbsp;<spring:message code="sirius.to2"/>&nbsp;<fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/></td>
    </tr>
     <tr>
        <td colspan="3">&nbsp;</td>
    </tr>
</table>
<c:forEach items='${reports}' var='repo'>
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<thead>
<tr style="height: 25px;">
    <th width="9%" align="left"><spring:message code="directsalesorder.warehouse"/> </th>
  	<th width="1%">&nbsp;:</th>
  	<th width="90%" align="left"><c:out value='${repo.facilityName}'/></th>
</tr>
</thead>
</table>
<table style="border-top:solid 1px black;" cellpadding="3" cellspacing="0" width="100%">
<thead>
<tr style="height: 30px;">
	<th width="10%" style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="container"/></th>
  	<th width="20%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.name"/></th>
 	<th width="10%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="productcategory"/></th>
 	<th width="5%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.uom"/></th>
 	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.opening.report"/></th>
 	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.in"/></th>
  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.out"/></th>
    <th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.closing.report"/></th>
    <%-- <c:if test="${criteria.showBale}">
		<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.opening.report"/> (Bale)</th>
		<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.in"/> (Bale)</th>
		<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.out"/> (Bale)</th>
	  	<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.closing.report"/> (Bale)</th>
 	</c:if> --%>
</tr>                   
</thead>
<tbody>
<c:forEach items='${repo.adapters}' var='adapter'>
    <c:if test='${adapter.quantity > 0 || adapter.in > 0 || adapter.out > 0 || adapter.sum > 0}'>
    	<c:set var="dateFrom"><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/></c:set>
    	<c:set var="dateTo"><fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/></c:set>
        <tr>
        	<td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><c:out value='${adapter.containerName}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">
            	<a href="<c:url value='/page/inventoryledgerdetailview.htm?organization=${organization.id}&facility=${criteria.facility}&showLot=${criteria.showLot}&showBale=${criteria.showBale}&containerId=${adapter.containerId}&product=${adapter.productId}&lotCode=${criteria.showLot ? adapter.lotCode : ""}&dateFrom=${dateFrom}&dateTo=${dateTo}'/>">
            		<c:out value='${adapter.productName}'/>
           			<c:if test="${not empty adapter.lotCode and criteria.showLot}"> LOT <c:out value='${adapter.lotCode}'/></c:if>
            	</a>
            </td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="center"><c:out value='${adapter.categoryName}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="center"><c:out value='${adapter.uom}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">
            	<fmt:formatNumber value='${adapter.quantity}' pattern=',##0.00'/>
            	<c:if test="${criteria.showBale}"><strong>(<fmt:formatNumber value='${adapter.quantityBale}' pattern=',##0.00'/> BALE)</strong></c:if>
            </td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">
            	<fmt:formatNumber value='${adapter.in}' pattern=',##0.00'/>
            	<c:if test="${criteria.showBale}"><strong>(<fmt:formatNumber value='${adapter.inBale}' pattern=',##0.00'/> BALE)</strong></c:if>
            </td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">
            	<fmt:formatNumber value='${adapter.out}' pattern=',##0.00'/>
            	<c:if test="${criteria.showBale}"><strong>(<fmt:formatNumber value='${adapter.outBale}' pattern=',##0.00'/> BALE)</strong></c:if>
            </td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">
            	<fmt:formatNumber value='${adapter.sum}' pattern=',##0.00'/>
            	<c:if test="${criteria.showBale}"><strong>(<fmt:formatNumber value='${adapter.sumBale}' pattern=',##0.00'/> BALE)</strong></c:if>
            </td>
            <%-- <c:if test="${criteria.showBale}">
            	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.quantityBale}' pattern=',##0.00'/></td>
	          	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.inBale}' pattern=',##0.00'/></td>
	          	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.outBale}' pattern=',##0.00'/></td>
	          	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.sumBale}' pattern=',##0.00'/></td>
          </c:if> --%>
        </tr>
   </c:if>
</c:forEach>
<tr>
    <td colspan="9">&nbsp;</td>
</tr>
</tbody>
</table>
</c:forEach>
