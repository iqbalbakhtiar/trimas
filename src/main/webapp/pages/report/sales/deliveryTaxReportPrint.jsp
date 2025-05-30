<%@ include file="/common/tld-common.jsp"%>
<table id="delivery" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
 	<tr>
 		<td colspan="18" align="center"><strong>LAPORAN PENJUALAN</strong></td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px;text-transform: uppercase;">
		<th colspan="14" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;border-left:solid 1px black;">PENJUALAN</th>
		<th colspan="4" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;">CORETAX</th>
	</tr>
	<tr style="height: 30px;text-transform: uppercase;">
		<th width="1%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.number"/></th>
		<th width="10%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">No. FK</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="10%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="customer"/></th>
		<th width="8%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="deliveryorder"/></th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="salesorder.contract.no"/></th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.qty"/>(Bale)</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.qty"/>(Kg)</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.price"/>(Bale)</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.price"/>(Kg)</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">DPP</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">PPN 11%</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.total"/></th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">DPP CoreTax</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">DPP Nilai Lain</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;">PPN 12%</th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;"><spring:message code="sirius.total"/></th>
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
 	<c:set var='tDppCoreTax' value='0'/>
 	<c:set var='tDppOther' value='0'/>
 	<c:set var='tTax12' value='0'/>
 	<c:set var='tTotalCoreTax' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<c:set var="bale" value="${repo.deliveryOrderItem.quantity / 181.44}"/>
		<c:set var="priceBale" value="${repo.deliveryOrderItem.money.amount / 181.44}"/>
		<c:set var="dpp" value="${repo.deliveryOrderItem.quantity * repo.deliveryOrderItem.money.amount}"/>
		<c:set var="tax" value="${dpp * (repo.deliveryOrderItem.deliveryOrder.tax.taxRate/100)}"/>
		<c:set var="total" value="${dpp+tax}"/>
		<c:set var="dppCoreTax" value="0"/>
		<c:set var="dppOther" value="0"/>
		<c:set var="tax12" value="0"/>
		<c:set var="totalCoreTax" value="0"/>
			
		<c:if test="${tax gt 0}">
			<c:set var="dppCoreTax" value="${dpp+tax}"/>
			<c:set var="dppOther" value="${dpp * 11 / 12}"/>
			<c:set var="tax12" value="${dppOther * 12 / 100}"/>
			<c:set var="totalCoreTax" value="${dppOther+tax12}"/>
		</c:if>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;">${status.index+1}</td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatDate value='${repo.deliveryOrderItem.deliveryOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.deliveryOrderItem.deliveryOrder.customer.fullName}'/></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><a href="<c:url value='/page/deliveryorderpreedit.htm?id=${repo.deliveryOrderItem.deliveryOrder.id}'/>"><c:out value="${fn:replace(repo.deliveryOrderItem.deliveryOrder.code, 'SJ', '')}" /></a></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><a href="<c:url value='/page/salesorderpreedit.htm?id=${repo.salesOrderItem.salesOrder.id}'/>">${repo.salesOrderItem.salesOrder.code}</a></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.deliveryOrderItem.product.name} ${repo.deliveryOrderItem.referenceLot}'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;">
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}">
  			<fmt:formatNumber value='${bale}' pattern=',##0.00'/>
		</c:if>
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'WASTE'}">-</c:if>
		</td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.deliveryOrderItem.quantity}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;">
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}">
  			<fmt:formatNumber value='${priceBale}' pattern=',##0.00'/>
  		</c:if>
  		<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'WASTE'}">-</c:if>
  		</td></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.deliveryOrderItem.money.amount}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${dpp}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${tax}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${total}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${dppCoreTax}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${dppOther}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${tax12}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;"><fmt:formatNumber value='${totalCoreTax}' pattern=',##0.00'/></td>
	</tr>
 	<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}"><c:set var='tBale' value='${tBale+bale}'/></c:if>
 	<c:set var='tKg' value='${tKg+repo.deliveryOrderItem.quantity}'/>
 	<c:if test="${repo.salesOrderItem.salesOrder.salesInternalType eq 'YARN'}"><c:set var='tPriceBale' value='${tPriceBale+priceBale}'/></c:if>
 	<c:set var='tPriceKg' value='${tPriceKg+repo.deliveryOrderItem.money.amount}'/>
 	<c:set var='tDpp' value='${tDpp+dpp}'/>
 	<c:set var='tTax' value='${tTax+tax}'/>
 	<c:set var='tTotal' value='${tTotal+total}'/>
 	<c:set var='tDppOther' value='${tDppOther+dppOther}'/>
 	<c:set var='tTax12' value='${tTax12+tax12}'/>
 	<c:set var='tTotalCoreTax' value='${tTotalCoreTax+totalCoreTax}'/>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr style="height: 30px">
		<td align="right" colspan="7"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tBale}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tKg}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tPriceBale}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tPriceKg}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDpp}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTax}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDpp}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDppOther}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTax12}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotalCoreTax}' pattern=',##0.00'/></strong></td>
	</tr>
	</tfoot>
</table>
