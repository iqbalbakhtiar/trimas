<%@ include file="/common/tld-common.jsp"%>
<table id="delivery" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
 	<tr>
 		<td colspan="13" align="center"><h3>LAPORAN TAGIHAN</h3></td>
 	</tr>
	</thead>
	<thead>
	<tr>
		<td><spring:message code="organization"/></td>
		<td colspan="5">: ${organization.fullName}</td>
	</tr>
	<tr>
		<td><spring:message code="customer"/></td>
		<td colspan="5">: ${customer.fullName}</td>
	</tr>
	<tr>
		<td><spring:message code="sirius.date"/></td>
		<td colspan="5">: <fmt:formatDate value='${dateFrom}' pattern='dd MMM yyyy' /> - <fmt:formatDate value='${dateTo}' pattern='dd MMM yyyy' /></td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
	<tr style="height: 30px">
		<th width="8%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="8%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="billing"/></th>
		<th width="8%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="receipt"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="billing.total.amount"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="receipt.total"/></th>
		<th width="60%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="billing.unpaidamount"/></th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='totalBilling' value='0'/>
 	<c:set var='totalReceipt' value='0'/>
	<c:forEach items='${reports}' var='repo'>
 	<c:set var='total' value='0'/>
	<tr>
		<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.date}' pattern='dd-MM-yyyy'/></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/billingpreedit.htm?id=${repo.id}'/>">${repo.code}</a></td>
  		<td>&nbsp;</td>
  		<td align="right"><fmt:formatNumber value='${repo.money.amount}' pattern=',##0.00'/></td>
  		<td>&nbsp;</td>
  		<td align="right"><fmt:formatNumber value='${repo.money.amount}' pattern=',##0.00'/></td>
 		<c:set var='total' value='${total+repo.money.amount}'/>
	 	<c:set var='totalBilling' value='${totalBilling+repo.money.amount}'/>
	</tr>
		<c:forEach items='${repo.receipts}' var='rec'>
	 	<c:set var='paid'>${rec.paidAmount-rec.writeOff}</c:set>
	 	<c:set var='totalReceipt' value='${totalReceipt+paid}'/>
	 	<c:set var='total' value='${total-paid}'/>
		<tr>
			<td align="left" nowrap="nowrap"><fmt:formatDate value='${rec.receipt.date}' pattern='dd-MM-yyyy'/></td>
			<td>&nbsp;</td>
			<td align="left" nowrap="nowrap"><a href="<c:url value='/page/receiptpreedit.htm?id=${rec.receipt.id}'/>">${rec.receipt.code}</a></td>
  			<td>&nbsp;</td>
	  		<td align="right"><fmt:formatNumber value='${rec.paidAmount-rec.writeOff}' pattern=',##0.00'/></td>
	  		<td align="right"><fmt:formatNumber value='${total < 0 ? -total : total}' pattern=',##0.00'/></td>
		</tr>
		</c:forEach>
		<tr style="height: 30px">
			<td align="left" colspan="5" style="border-top:solid 1px black;border-bottom:solid 1px black;"><strong><spring:message code="billing.remainingamount"/></strong></td>
			<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${total < 0 ? -total : total}' pattern=',##0.00'/></strong></td>
		</tr>
		<tr>
			<td align="left" colspan="6" style="border-bottom:solid 1px black;">&nbsp;</td>
		</tr>
	</c:forEach>
	<tr style="height: 30px">
		<td align="left" colspan="3" style="border-bottom:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${totalBilling}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${totalReceipt}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${totalBilling-totalReceipt}' pattern=',##0.00'/></strong></td>
	</tr>
	</tbody>
	<tfoot>
	</tfoot>
</table>
