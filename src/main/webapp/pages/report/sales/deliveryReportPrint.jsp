<%@ include file="/common/tld-common.jsp"%>
<table id="delivery" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
 	<tr>
 		<td colspan="15" align="center"><strong>REKAP PENGELUARAN BARANG</strong></td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px">
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="10%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="customer"/></th>
		<th width="8%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="deliveryorder"/></th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesorder.contract.no"/></th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/>(Bale)</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/>(Kg)</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.price"/>(Bale)</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.price"/>(Kg)</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;">DPP</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;">PPN</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.total"/></th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.note2"/></th>
        <th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;">Angkutan</th>
		<th width="5%" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;">Benang</th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='tBale' value='0'/>
 	<c:set var='tKg' value='0'/>
 	<c:set var='tPriceBale' value='0'/>
 	<c:set var='tPriceKg' value='0'/>
 	<c:set var='tDpp' value='0'/>
 	<c:set var='tTax' value='0'/>
 	<c:set var='tTotal' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<c:set var="bale" value="${repo.deliveryOrderItem.quantity / 181.44}"/>
		<c:set var="priceBale" value="${repo.deliveryOrderItem.money.amount / 181.44}"/>
		<c:set var="dpp" value="${repo.deliveryOrderItem.quantity * repo.deliveryOrderItem.money.amount}"/>
		<c:set var="tax" value="${dpp * (repo.deliveryOrderItem.deliveryOrder.tax.taxRate/100)}"/>
		<c:set var="total" value="${dpp+tax}"/>
		<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.deliveryOrderItem.deliveryOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.deliveryOrderItem.deliveryOrder.customer.fullName}'/></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/deliveryorderpreedit.htm?id=${repo.deliveryOrderItem.deliveryOrder.id}'/>"><c:out value="${fn:replace(repo.deliveryOrderItem.deliveryOrder.code, 'SJ', '')}" /></a></td>
		<td align="left" nowrap="nowrap"><a href="<c:url value='/page/salesorderpreedit.htm?id=${repo.salesOrderItem.salesOrder.id}'/>">${repo.salesOrderItem.salesOrder.code}</a></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.deliveryOrderItem.product.name}'/><c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}">&nbsp;${repo.deliveryOrderItem.referenceLot}</c:if></td>
  		<td align="right">
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}">
  			<fmt:formatNumber value='${bale}' pattern=',##0.00'/>
		</c:if>
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'WASTE'}">-</c:if>
		</td>
  		<td align="right"><fmt:formatNumber value='${repo.deliveryOrderItem.quantity}' pattern=',##0.00'/></td>
  		<td align="right">
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}">
  			<fmt:formatNumber value='${priceBale}' pattern=',##0.00'/>
  		</c:if>
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'WASTE'}">-</c:if>
  		</td>
  		<td align="right"><fmt:formatNumber value='${repo.deliveryOrderItem.money.amount}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${dpp}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${tax}' pattern=',##0.00'/></td>
  		<td align="right"><fmt:formatNumber value='${total}' pattern=',##0.00'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.deliveryOrderItem.deliveryOrder.plateNumber}'/></td>
		<td align="left" nowrap="nowrap"><c:out value='${repo.deliveryOrderItem.deliveryOrder.vehicle}'/></td>
		<td align="left" nowrap="nowrap">&nbsp;</td>
	</tr>
 	<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}"><c:set var='tBale' value='${tBale+bale}'/></c:if>
 	<c:set var='tKg' value='${tKg+repo.deliveryOrderItem.quantity}'/>
 	<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}"><c:set var='tPriceBale' value='${tPriceBale+priceBale}'/></c:if>
 	<c:set var='tPriceKg' value='${tPriceKg+repo.deliveryOrderItem.money.amount}'/>
 	<c:set var='tDpp' value='${tDpp+dpp}'/>
 	<c:set var='tTax' value='${tTax+tax}'/>
 	<c:set var='tTotal' value='${tTotal+total}'/>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr style="height: 30px">
		<td align="right" colspan="5" style="border-top:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tBale}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tKg}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tPriceBale}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tPriceKg}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDpp}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTax}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
		<td colspan="3" style="border-top:solid 1px black;" align="right"><strong>&nbsp;</strong></td>
	</tr>
	</tfoot>
</table>
