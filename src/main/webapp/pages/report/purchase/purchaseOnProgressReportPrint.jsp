<%@ include file="/common/tld-common.jsp"%>
<table id="purchaseOnProgress" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
 	<tr>
 		<td colspan="13" align="center"><strong>LAPORAN PROGRESS PEMBELIAN</strong></td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px">
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="10%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="supplier"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.code"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.document.type"/></th>
		<th width="12%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product.uom"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="deliveryplanning.delivered"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="deliveryplanning.undelivered"/></th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='qty' value='0'/>
 	<c:set var='tDelivered' value='0'/>
 	<c:set var='tUndelivered' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.purchaseOrderItem.purchaseOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.purchaseOrder.supplier.fullName}'/></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/purchaseorderpreedit.htm?id=${repo.purchaseOrderItem.purchaseOrder.id}'/>">${repo.purchaseOrderItem.purchaseOrder.code}</a></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.purchaseOrder.purchaseDocumentType.normalizedName}'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.name}'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.unitOfMeasure.measureId}'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.purchaseOrderItem.quantity}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.receipted}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.purchaseOrderItem.quantity-repo.receipted}' pattern=',##0.00'/></td>
	</tr>
 	<c:set var='qty' value='${qty+repo.purchaseOrderItem.quantity}'/>
 	<c:set var='tDelivered' value='${tDelivered+repo.receipted}'/>
 	<c:set var='tUndelivered' value='${tUndelivered+(repo.purchaseOrderItem.quantity-repo.receipted)}'/>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr><td align="right" colspan="9" style="border-top:solid 1px black;">&nbsp;</td></tr>
	<tr style="height: 30px; display: none;">
		<td align="right" colspan="6" style="border-top:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${qty}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDelivered}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tUndelivered}' pattern=',##0.00'/></strong></td>
	</tr>
	</tfoot>
</table>
