<%@ include file="/common/tld-common.jsp"%>
<table id="purchase" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
	<thead>
	<tr>
 		<td colspan="18" align="center"><strong>PT. SAN STAR MANUNGGAL</strong></td>
 	</tr>
	<tr>
 		<td colspan="18" align="center"><strong>LAPORAN PEMBELIAN</strong></td>
 	</tr>
 	<tr>
 		<td colspan="18" align="center">
 			<strong><fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy'/> - <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy'/></strong>
 		</td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px;text-transform: uppercase;">
		<th colspan="16" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;border-left:solid 1px black;">PEMBELIAN</th>
		<th colspan="3" align="center" style="border-top:solid 1px black;border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;">CORETAX</th>
	</tr>
	<tr style="height: 30px;text-transform: uppercase;">
		<th width="1%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.number"/></th>
		<th width="3%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="purchaseorder.arrival.date"/></th>
		<th width="3%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.document.type"/></th>
		<th width="3%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="invoice.tax.date"/></th>
		<th width="10%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="billing.invoice.tax.no"/></th>
		<th width="10%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="supplier"/></th>
		<th width="10%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="receipt.delivery"/></th>
		<th width="15%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="supplier.note"/></th>
		<th width="5%" align="center" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="product.name"/></th>
		<th width="5%" align="left" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.qty"/></th>
		<th width="5%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.price"/> / <spring:message code="uom.unit"/></th>
		<th width="5%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="purchaseorder.dpp"/></th>
		<th width="8%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.discount"/></th>
		<th width="8%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="purchaseorder.afterdiscount"/></th>
		<th width="5%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="tax.ppn"/> 11%</th>
		<th width="8%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="sirius.total"/></th>
		<th width="8%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="purchaseorder.dpp.other"/></th>
		<th width="5%" align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><spring:message code="tax.ppn"/> 12%</th>
		<th width="8%" align="right" style="border-bottom:solid 1px black;border-right:solid 1px black;black;border-left:solid 1px black;"><spring:message code="sirius.total"/></th></tr>
	</thead>
	<tbody>
 	<c:set var='tKg' value='0'/>
 	<c:set var='tPriceKg' value='0'/>
 	<c:set var='tDpp' value='0'/>
 	<c:set var='tTax' value='0'/>
 	<c:set var='tTotal' value='0'/>
 	<c:set var='tDppCoreTax' value='0'/>
 	<c:set var='tDppOther' value='0'/>
 	<c:set var='tTax12' value='0'/>
 	<c:set var='tTotalCoreTax' value='0'/>
 	<c:set var='tDiscount' value='0'/>
 	<c:set var='tAfterDiscount' value='0'/>
	<c:set var="lastKey" value="" />
	<c:forEach items='${reports}' var='repo' varStatus="status">
	<tr>
		<c:set var="dpp" value="${repo.nettPrice}"/>
		<c:set var="price" value="${repo.purchaseOrderItem.quantity * repo.purchaseOrderItem.money.amount}"/>
		<c:set var="tax" value="${dpp * (11/100)}"/>
		<c:set var="total" value="${dpp+tax}"/>
		<c:set var="dppOther" value="${dpp * 11 / 12}"/>
		<c:set var="tax12" value="${dppOther * 12 / 100}"/>
		<c:set var="totalCoreTax" value="${dpp+tax12}"/>
		<c:set var='tDiscount' value='${tDiscount+repo.purchaseOrderItem.discount}'/>
 		<c:set var='tAfterDiscount' value='${tAfterDiscount+repo.nettPrice}'/>
		
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;">${status.index+1}</td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatDate value='${repo.purchaseOrderItem.purchaseOrder.date}' pattern='dd MMM yyyy'/></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.purchaseOrderItem.purchaseOrder.purchaseDocumentType.getNormalizedName()}'/></td>
		
		<c:forEach var="inv" items="${repo.purchaseOrderItem.invoiceReferences}" varStatus="loop">
	      <c:if test="${loop.index == 0}">
	        <c:set var="currDate" value="${inv.invoiceVerificationItem.invoiceVerification.date}" />
	        <c:set var="currTax" value="${inv.invoiceVerificationItem.invoiceVerification.taxNo}" />
	        <c:set var="currKey" value="${currDate}_${currTax}" />
	      </c:if>
	    </c:forEach>
	
	    <c:if test="${currKey != lastKey}">
	      <c:set var="rowCount" value="0" />
	      <c:forEach var="r" items="${reports}">
	        <c:forEach var="i" items="${r.purchaseOrderItem.invoiceReferences}" varStatus="s">
	          <c:if test="${s.index == 0}">
	            <c:set var="loopDate" value="${i.invoiceVerificationItem.invoiceVerification.date}" />
	            <c:set var="loopTax" value="${i.invoiceVerificationItem.invoiceVerification.taxNo}" />
	            <c:set var="loopKey" value="${loopDate}_${loopTax}" />
	          </c:if>
	        </c:forEach>
	        <c:if test="${loopKey == currKey}">
	          <c:set var="rowCount" value="${rowCount + 1}" />
	        </c:if>
	      </c:forEach>
		
	      <td rowspan="${rowCount}" style="border-bottom:solid 1px black;border-left:solid 1px black;" align="center">
	        <fmt:formatDate value="${currDate}" pattern="dd MMM yyyy" />
	      </td>
	      <td rowspan="${rowCount}" style="border-bottom:solid 1px black;border-left:solid 1px black;" align="center">
	        ${currTax}
	      </td>
	      
	      <c:set var="lastKey" value="${currKey}" />
	    </c:if>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.purchaseOrderItem.purchaseOrder.supplier.fullName}'/></td>
        <c:forEach var="invo" items="${repo.purchaseOrderItem.invoiceReferences}">
			<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;">${invo.goodsReceiptItem.goodsReceipt.invoiceNo}</td>
		</c:forEach>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.purchaseOrderItem.purchaseOrder.note}'/></td>
		<td align="left" nowrap="nowrap" style="border-bottom:solid 1px black;border-left:solid 1px black;"><c:out value='${repo.purchaseOrderItem.product.name}'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.purchaseOrderItem.quantity}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.purchaseOrderItem.money.amount}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${dpp}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.purchaseOrderItem.discount}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${repo.nettPrice}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${tax}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${total}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${dppOther}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;"><fmt:formatNumber value='${tax12}' pattern=',##0.00'/></td>
  		<td align="right" style="border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;"><fmt:formatNumber value='${totalCoreTax}' pattern=',##0.00'/></td>
	</tr>
 	<c:set var='tKg' value='${tKg+repo.purchaseOrderItem.quantity}'/>
 	<c:set var='tPriceKg' value='${tPriceKg+repo.nettPrice}'/>
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
		<td align="right" colspan="9"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tKg}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tPriceKg}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDpp}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDiscount}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tAfterDiscount}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTax}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tDppOther}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTax12}' pattern=',##0.00'/></strong></td>
		<td style="border-bottom:solid 1px black;border-left:solid 1px black;border-right:solid 1px black;" align="right"><strong><fmt:formatNumber value='${tTotalCoreTax}' pattern=',##0.00'/></strong></td>
	</tr>
	</tfoot>
</table>
