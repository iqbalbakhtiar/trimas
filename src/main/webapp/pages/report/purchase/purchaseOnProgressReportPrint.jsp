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
		<th width="10%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="customer"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesorder.contract.no"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/>(Bale)</th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/>(Kg)</th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="deliveryplanning.delivered"/>(Bale)</th>
		<th width="5%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="deliveryplanning.delivered"/>(Kg)</th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='tBale' value='0'/>
 	<c:set var='tKg' value='0'/>
 	<c:set var='tUnassignedBale' value='0'/>
 	<c:set var='tUnassignedKg' value='0'/>
 	<c:set var='tDeliveredBale' value='0'/>
 	<c:set var='tDeliveredKg' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.purchaseOrderItem.purchaseOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.purchaseOrder.customer.fullName}'/></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/purchaseorderpreedit.htm?id=${repo.purchaseOrderItem.purchaseOrder.id}'/>">${repo.purchaseOrderItem.purchaseOrder.code}</a></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.salesOrderItem.product.name}'/></td>
  		<td align="right">
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType eq 'FINISH_GOODS'}">
  			<fmt:formatNumber value='${repo.bale}' pattern=',##0.00'/>
		</c:if>
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType ne 'FINISH_GOODS'}">-</c:if>
  		</td>
  		<td align="right"><fmt:formatNumber value='${repo.kg}' pattern=',##0.00'/></td>
  		<td align="right">
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType eq 'FINISH_GOODS'}">
  			<fmt:formatNumber value='${repo.deliveredBale}' pattern=',##0.00'/>
  		</c:if>
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType ne 'FINISH_GOODS'}">-</c:if>
  		</td>
  		<td align="right"><fmt:formatNumber value='${repo.deliveredKg}' pattern=',##0.00'/></td>
  		<td align="right">
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType eq 'FINISH_GOODS'}">
  			<fmt:formatNumber value='${repo.undeliveredBale}' pattern=',##0.00'/>
  		</c:if>
  		<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType ne 'FINISH_GOODS'}">-</c:if>
  		</td>
  		<td align="right"><fmt:formatNumber value='${repo.undeliveredKg}' pattern=',##0.00'/></td>
  		<%-- <td>
			<c:if test="${repo.purchaseOrderItem.purchaseOrder.soStatus eq 'CLOSE' or repo.purchaseOrderItem.purchaseOrder.soStatus eq 'CANCELED'}"><div style="color: red;"><spring:message code="salesorder.status.${repo.purchaseOrderItem.purchaseOrder.soStatus.messageName}"/></div></c:if>
			<c:if test="${repo.purchaseOrderItem.purchaseOrder.soStatus eq 'PLANNING'}"><div style="color: blue;"><spring:message code="salesorder.status.${repo.purchaseOrderItem.purchaseOrder.soStatus.messageName}"/></div></c:if>
			<c:if test="${repo.purchaseOrderItem.purchaseOrder.soStatus eq 'OPEN'}"><div style="color: green;"><spring:message code="salesorder.status.${repo.purchaseOrderItem.purchaseOrder.soStatus.messageName}"/></div></c:if>
  		</td> --%>
	</tr>
 	<c:set var='tBale' value='${tBale+repo.bale}'/>
 	<c:set var='tKg' value='${tKg+repo.kg}'/>
 	<c:set var='tDeliveredKg' value='${tDeliveredKg+repo.deliveredKg}'/>
 	<c:set var='tUndeliveredKg' value='${tUndeliveredKg+repo.undeliveredKg}'/>
	<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType eq 'FINISH_GOODS'}">
 		<c:set var='tDeliveredBale' value='${tDeliveredBale+repo.deliveredBale}'/>
	</c:if>
	
	<c:if test="${repo.purchaseOrderItem.product.productCategory.categoryType eq 'FINISH_GOODS'}">
 		<c:set var='tUndeliveredBale' value='${tUndeliveredBale+repo.undeliveredBale}'/>
	</c:if>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr style="height: 30px">
		<td align="right" colspan="4" style="border-top:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tBale}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tKg}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDeliveredBale}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDeliveredKg}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tUndeliveredBale}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tUndeliveredKg}' pattern=',##0.00'/></strong></td>
		<!-- <td style="border-top:solid 1px black;" align="right"><strong>&nbsp;</strong></td> -->
	</tr>
	</tfoot>
</table>
