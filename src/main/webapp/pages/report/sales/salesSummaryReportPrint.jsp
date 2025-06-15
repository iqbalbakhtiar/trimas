<%@ include file="/common/tld-common.jsp"%>
<table id="salessum" style="border:none" width="100%" cellspacing="0" cellpadding="3">
	<thead>
 	<tr>
 		<td colspan="3"><strong>SALES SUMMARY</strong></td>
 	</tr>
 	<tr>
 		<td>&nbsp;</td>
 	</tr>
	<tr>
 		<td><spring:message code="organization"/></td>
     	<td colspan="2">: <c:out value='${organization.fullName}'/></td>
	</tr>
	<tr>
 		<td><spring:message code="customer"/></td>
     	<td colspan="2">: 
     		<c:if test="${not empty customer}"><c:out value='${customer.fullName}'/></c:if>
     		<c:if test="${empty customer}"><spring:message code='sirius.all'/></c:if>
     	</td>
	</tr>
	<tr>
		<td><spring:message code="sirius.period"/></td>
   		<td colspan="2">: <fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy'/> <spring:message code="sirius.to2"/> <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy'/></td>
	</tr>
 	<tr>
 		<td>&nbsp;</td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px">
 		<th width="1%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.number"/></th>
		<th width="70%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="customer"/></th>
		<th width="10%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesorder.total"/></th>
		<th width="10%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesorder.tax.amount"/></th>
		<th width="10%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.total"/></th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='t_amount' value='0'/>
 	<c:set var='t_taxAmount' value='0'/>
 	<c:set var='t_total' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<td align="left" nowrap="nowrap"><c:out value='${status.index+1}'/></td>
		<td align="left" nowrap="nowrap">
			<a href="<c:url value='/page/salesdetailreportview.htm?organization=${organization.id}&facility=${facility.id}&customer=${repo.customer.id}&showBale=${criteria.showBale}&dateFrom='/><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/>&dateTo=<fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/>">
				<c:out value='${repo.customer.fullName}'/>
			</a>
		</td>
  		<td align="right"><fmt:formatNumber value='${repo.amount}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.taxAmount}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.total}' pattern=',##0.00'/></td>
	</tr>
	<c:set var='t_amount' value='${t_amount + repo.amount}'/>
	<c:set var='t_taxAmount' value='${t_taxAmount + repo.taxAmount}'/>
	<c:set var='t_total' value='${t_total + repo.total}'/>
	</c:forEach>
	<tr style="height: 30px">
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" colspan="2"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_amount}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_taxAmount}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_total}' pattern=',##0.00'/></strong></td>
	</tr>
	<c:remove var='t_amount'/>
 	</tbody>
</table>
