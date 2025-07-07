<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/purchasereportmonthlypre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-export-xls" download="purchasemonthly.xls" href="#" onclick="return ExcellentExport.excel(this, 'po', 'Purchase Order Monthly Report');"><span><spring:message code="sirius.export"/></span></a>
	<a class="item-button-rprev" href="javascript:prev();"><span><spring:message code="sirius.prev"/></span></a>
	<a class="item-button-rnext" href="javascript:next();"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="3"><strong>TOTAL PEMBELIAN TAHUN <fmt:formatDate value='${criteria.dateFrom}' pattern='yyyy'/></strong></td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td width="10%"><spring:message code="sirius.organization"/> </td>
			<td width="1%">&nbsp;:</td>
			<td><c:out value='${organization.fullName}'/></td>
		</tr>
		<tr>
			<td><spring:message code="sirius.period"/></td>
			<td>&nbsp;:</td>
			<td><fmt:formatDate value='${criteria.dateFrom}' pattern='MMMM yyyy'/></td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table cellpadding="5" cellspacing="0" width="100%">
		<tr align="center">
			<td width="5%" class="border-left border-top">No</td>
			<td width="10%" class="border-left border-top">Tipe Pembelian</td>
			<td width="40%" class="border-left border-top">Pembelian</td>
			<td width="10%" class="border-left border-top">Qty</td>
			<td width="20%" class="border-left border-top border-right">Total</td>
		</tr>
		<c:set var="totalQty" value="0.0" />
		<c:set var="totalAmount" value="0.0" />
		<c:forEach items='${reports}' var='repo' varStatus="status">
			<c:set var="totalQty" value="${totalQty + repo.quantity}" />
			<c:set var="totalAmount" value="${totalAmount + repo.amount}" />
			<tr>
				<td class="border-left border-top" align="center"><c:out value="${status.index+1}"/></td>
				<td class="border-left border-top" align="left"><c:out value="${repo.purchaseDocumentType}"/></td>
				<td class="border-left border-top" align="left"><c:out value="${repo.product.name}"/></td>
				<td class="border-left border-top" align="right"><fmt:formatNumber value='${repo.quantity}' pattern=',##0.00'/></td>
				<td class="border-left border-top border-right" align="right"><fmt:formatNumber value='${repo.amount}' pattern=',##0.00'/></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="3" align="right" class="border-left border-top border-bottom"><strong>Total</strong></td>
			<td align="right" class="border-left border-top border-bottom">
				<strong>
					<fmt:formatNumber value="${totalQty}" pattern=",##0.00"/>
				</strong>
			</td>
			<td align="right" class="border-left border-top border-right border-bottom">
				<strong>
					<fmt:formatNumber value="${totalAmount}" pattern=",##0.00"/>
				</strong>
			</td>
		</tr>
	</table>

	<%--TABLE FOR EXPORT EXCEL--%>
	<table id="po" cellpadding="5" cellspacing="0" width="100%" style="display: none">
		<tr>
			<td colspan="9" align="center">TOTAL PEMBELIAN TAHUN 2025</td>
		</tr>
		<tr>
			<td colspan="9">&nbsp;</td>
		</tr>
		<tr>
			<td>Company</td>
			<td colspan="2">: <c:out value='${organization.fullName}'/></td>
		</tr>
		<tr>
			<td>Period</td>
			<td colspan="2">: <fmt:formatDate value='${criteria.dateFrom}' pattern='MMMM yyyy'/></td>
		</tr>
		<tr>
			<td colspan="9"></td>
		</tr>
		<tr align="center">
			<td>No</td>
			<td colspan="2">Tipe Pembelian</td>
			<td colspan="3">Pembelian</td>
			<td>Qty</td>
			<td colspan="2">Total</td>
		</tr>
		<c:forEach items='${reports}' var='repo' varStatus="status">
			<tr>
				<td align="center"><c:out value="${status.index+1}"/></td>
				<td colspan="2" align="left"><c:out value="${repo.purchaseDocumentType}"/></td>
				<td colspan="3" align="left"><c:out value="${repo.product.name}"/></td>
				<td align="right"><fmt:formatNumber value='${repo.quantity}' pattern=',##0.00'/></td>
				<td colspan="2" align="right"><fmt:formatNumber value='${repo.amount}' pattern=',##0.00'/></td>
			</tr>
		</c:forEach>
		<tr align="right">
			<td colspan="6"><strong>Total</strong></td>
			<td><strong><fmt:formatNumber value="${totalQty}" pattern=",##0.00"/></strong></td>
			<td colspan="2"><strong><fmt:formatNumber value="${totalAmount}" pattern=",##0.00"/></strong></td>
		</tr>
	</table>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
    function prev()
    {
        window.location = "<c:url value='/page/purchasemonthlyreportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>";
    }

    function next()
    {
        window.location = "<c:url value='/page/purchasemonthlyreportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>";
    }
</script>