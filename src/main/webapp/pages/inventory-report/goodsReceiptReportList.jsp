<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/goodsreceiptreportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-export-xls"
	   download="goodsreceiptreport.xlsx"
	   href="#"
	   onclick="return SheetJS.excel(this, 'report', 'Laporan Penerimaan Barang');">
		<span><spring:message code="sirius.export"/></span>
	</a>
	<a class="item-button-rprev" href="javascript:prev();"><span><spring:message code="sirius.prev"/></span></a>
	<a class="item-button-rnext" href="javascript:next();"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
	<table id="report" style="border:none;" width="100%" cellspacing="0" cellpadding="5">
		<thead>
		<tr>
			<td colspan="2"><spring:message code="organization"/></td>
			<td colspan="11">: ${organization.fullName}</td>
		</tr>
		<tr>
			<td colspan="2"><spring:message code="sirius.document.type"/></td>
			<td colspan="11">:
				<c:if test="${empty criteria.reference}"><spring:message code='sirius.all'/></c:if>
				<c:if test="${not empty criteria.reference}">${criteria.reference}</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2"><spring:message code="supplier"/></td>
			<td colspan=11>: ${supplier.fullName}</td>
		</tr>
		<tr>
			<td colspan="2"><spring:message code="sirius.date"/></td>
			<td colspan="11">: <fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy'/> - <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy'/></td>
		</tr>
		<tr>
			<td colspan="13" align="center"><strong>LAPORAN PEMBELIAN</strong></td>
		</tr>
		<tr style="height: 30px">
			<th width="1%" align="center" class="border-top border-bottom"><strong><spring:message code="sirius.number"/></strong></th>
			<th width="5%" align="left" class="border-top border-bottom"><strong><spring:message code="sirius.date"/></strong></th>
			<th width="5%" align="left" class="border-top border-bottom"><strong><spring:message code="contactmechanism.department"/></strong></th>
			<th width="5%" align="left" class="border-top border-bottom"><strong>No SJ</strong></th>
			<th width="5%" align="left" class="border-top border-bottom"><strong>No PO</strong></th>
			<th width="10%" align="left" class="border-top border-bottom"><strong><spring:message code="supplier"/></strong></th>
			<th width="15%" align="left" class="border-top border-bottom"><strong><spring:message code="product.name"/></strong></th>
			<th width="5%" align="left" class="border-top border-bottom"><strong><spring:message code="sirius.uom"/></strong></th>
			<th width="5%" align="right" class="border-top border-bottom"><strong><spring:message code="sirius.qty"/></strong></th>
			<th width="5%" align="right" colspan="2" class="border-top border-bottom"><strong><spring:message code="purchaseorderitem.unitprice"/></strong></th>
			<th width="8%" align="right" colspan="2" class="border-top border-bottom"><strong><spring:message code="purchaseorder.total"/></strong></th>
		</tr>
		</thead>
		<tbody>
		<c:set var='tTotal' value='0'/>
		<c:forEach items='${reports}' var='repo' varStatus="status">
			<tr>
				<td align="center" nowrap="nowrap"><c:out value='${status.index+1}'/></td>
				<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.purchaseOrderItem.purchaseOrder.date}' pattern='dd MMM yyyy'/></td>
				<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.requisitionItem.purchaseRequisition.department.name}'/></td>
				<td align="left" nowrap="nowrap"><c:out value="${repo.invoiceNo}"/></td>
				<td align="left" nowrap="nowrap"><a href="<c:url value='/page/purchaseorderpreedit.htm?id=${repo.purchaseOrderItem.purchaseOrder.id}'/>">${repo.purchaseOrderItem.purchaseOrder.code}</a></td>
				<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.purchaseOrder.supplier.fullName}'/></td>
				<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.name}'/></td>
				<td align="left" nowrap="nowrap"><c:out value='${repo.purchaseOrderItem.product.unitOfMeasure.measureId}'/></td>
				<td align="right"><fmt:formatNumber value='${repo.quantity}' pattern=',##0.00'/></td>
				<td align="right" colspan="2"><fmt:formatNumber value='${repo.unitPrice}' pattern=',##0.00'/></td>
				<td align="right" colspan="2"><fmt:formatNumber value='${repo.total}' pattern=',##0.00'/></td>
			</tr>
			<c:set var='tTotal' value='${tTotal+repo.total}'/>
		</c:forEach>
		</tbody>
		<tfoot>
		<tr style="height: 30px">
			<td align="right" colspan="11" class="border-top"><strong><spring:message code="sirius.total"/></strong></td>
			<td class="border-top" colspan="2" align="right"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
		</tr>
		</tfoot>
	</table>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
    function prev()
    {
        window.location = "<c:url value='/page/goodsreceiptreportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>";
    }

    function next()
    {
        window.location = "<c:url value='/page/goodsreceiptreportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>";
    }
</script>