<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/productionorderdetailbarcodepreedit.htm?id=${data.id}'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <div class="table-container">
        	<c:forEach items="${data.barcodes}" var="item" varStatus="index">
        		<div class="table-cell">
	        		<table cellpadding="2" cellspacing="0" border="0">
		                <tr>
		                    <td align="left" colspan="2" align="center" class="border-left border-right border-top border-bottom" style="padding: 2px 2px 2px 2px;"><img style="color:#000000" src="barcode4j?type=Code39&msg=<c:out value='${item.code}'/>" class="tblprint" /></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom">Barcode ID</td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${item.code}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom">No Karung</td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${fn:substring(item.code, 6, 9)}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="barcoding.lotno"/></td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${item.lotCode}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="productionorder.threadtype"/></td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${item.product.name}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="barcoding.conemark"/></td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${item.coneMark}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="barcoding.coneqty"/></td>
		                	<td align="right" class="border-left border-right border-bottom"><fmt:formatNumber value='${item.quantityCone}' pattern="#,##0"/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="barcoding.bruto"/></td>
		                	<td align="right" class="border-left border-right border-bottom"><fmt:formatNumber value='${item.quantity}' pattern="#,##0.00"/> <c:out value="${item.product.unitOfMeasure.measureId}"/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="barcoding.netto"/></td>
		                	<td align="right" class="border-left border-right border-bottom"><fmt:formatNumber value='${item.quantityReal}' pattern="#,##0.00"/> <c:out value="${item.product.unitOfMeasure.measureId}"/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom">Penimbang</td>
		                	<td align="left" class="border-left border-right border-bottom"><c:out value='${item.barcodeGroup.productionOrderDetailBarcode.pic}'/></td>
		                </tr>
		                <tr>
		                	<td align="right" class="border-left border-bottom"><spring:message code="sirius.date"/></td>
		                	<td align="left" class="border-left border-right border-bottom"><fmt:formatDate value='${item.barcodeGroup.productionOrderDetailBarcode.date}' pattern='dd-MM-yyyy'/></td>
		                </tr>
		            </table>
	           	</div>
        	</c:forEach>
        </div>
    </div>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<style>
  .table-container {
    display: flex;
    flex-wrap: wrap;
    max-width: 100%; /* Set your desired row width */
  }

  .table-cell {
    width: 200px; /* Set cell width */
    display: flex;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;
  }
</style>