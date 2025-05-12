<%@ include file="/common/tld-common.jsp"%>
<table id="purchase" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
 	<tr>
 		<td colspan="2"><spring:message code="organization"/></td>
 		<td colspan="13">: ${organization.fullName}</td>
 	</tr>
 	<tr>
 		<td colspan="2"><spring:message code="sirius.document.type"/></td>
 		<td colspan="13">:
 			<c:if test="${empty criteria.documentType}"><spring:message code='sirius.all'/></c:if>
 			<c:if test="${not empty criteria.documentType}">${criteria.documentType}</c:if>
 		</td>
 	</tr>
 	<tr>
 		<td colspan="2"><spring:message code="supplier"/></td>
 		<td colspan="13">: ${supplier.fullName}</td>
 	</tr>
 	<tr>
 		<td colspan="2"><spring:message code="sirius.date"/></td>
 		<td colspan="13">: <fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy'/> - <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy'/></td>
 	</tr>
 	<tr>
 		<td colspan="15" align="center"><strong>LAPORAN PEMBELIAN</strong></td>
 	</tr>
	<tr style="height: 30px">
		<th width="1%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.number"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="10%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="contactmechanism.department"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;">No SJ</th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;">No PO</th>
		<th width="10%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="supplier"/></th>
		<th width="15%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.uom"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="purchaseorderitem.unitprice"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="purchaseorderitem.subtotal"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.discount"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="purchaseorder.afterdiscount"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="purchaseorder.tax"/></th>
		<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="purchaseorder.total"/></th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='tTotal' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<td align="left" nowrap="nowrap"><c:out value='${status.index+1}'/></td>
		<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.purchaseOrderItem.purchaseOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.requisitionItem.purchaseRequisition.department.name}'/></td>
		<td align="left" nowrap="nowrap"></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/purchaseorderpreedit.htm?id=${repo.purchaseOrderItem.purchaseOrder.id}'/>">${repo.purchaseOrderItem.purchaseOrder.code}</a></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.purchaseOrder.supplier.fullName}'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.name}'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.unitOfMeasure.measureId}'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.purchaseOrderItem.quantity}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.price}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.dpp}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.purchaseOrderItem.discount}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.nettPrice}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.taxAmount}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${repo.total}' pattern=',##0.00'/></td>
	</tr>
 	<c:set var='tTotal' value='${tTotal+repo.total}'/>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr style="height: 30px">
		<td align="left" colspan="14" style="border-top:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
	</tr>
	</tfoot>
</table>
