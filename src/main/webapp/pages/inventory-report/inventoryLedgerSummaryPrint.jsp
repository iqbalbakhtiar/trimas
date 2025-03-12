<%@ include file="/common/tld-common.jsp"%>		
<table cellpadding="0" cellspacing="0" width="100%">
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
        <td><spring:message code="sirius.datefrom"/></td>
        <td>&nbsp;:</td>
        <td><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/>&nbsp;<spring:message code="sirius.to"/>&nbsp;<fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/></td>
    </tr>
     <tr>
        <td colspan="3">&nbsp;</td>
    </tr>
</table>
<c:forEach items='${reports}' var='repo'>
<table border="0" cellspacing="0" width="100%">
<thead>
<tr>
    <th width="9%" align="left"><spring:message code="directsalesorder.warehouse"/> </th>
  	<th width="1%">&nbsp;:</th>
  	<th width="90%" align="left"><c:out value='${repo.facilityName}'/></th>
</tr>
</thead>
</table>
<table style="border-top:solid 1px black;" cellpadding="3" cellspacing="0" width="100%">
<thead>
<tr>
	<th width="13%" style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="grid"/></th>
    <th width="13%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="container"/></th>
	  	<th width="9%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.code"/></th>
	  	<th width="23%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.name"/></th>
	  	  	<th width="9%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.uom"/></th>
  	  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.opening"/></th>
  	  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.in"/></th>
  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.out"/></th>
    <th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><spring:message code="sirius.closing"/></th>
</tr>                   
</thead>
<tbody>
<c:forEach items='${repo.adapters}' var='adapter'>
    <c:if test='${adapter.quantity > 0 || adapter.in > 0 || adapter.out > 0 || adapter.sum > 0}'>
        <tr>
        	<td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><c:out value='${adapter.gridName}'/></td>
        	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><c:out value='${adapter.containerName}'/></td>
            </td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">&nbsp;<c:out value='${adapter.productCode}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">&nbsp;<c:out value='${adapter.productName}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="center"><c:out value='${adapter.uom}'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.quantity}' pattern=',##0.00'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.in}' pattern=',##0.00'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.out}' pattern=',##0.00'/></td>
            <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.sum}' pattern=',##0.00'/></td>
        </tr>
   </c:if>
</c:forEach>
<tr>
    <td colspan="9">&nbsp;</td>
</tr>
</tbody>
</table>
</c:forEach>