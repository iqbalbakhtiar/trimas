<%@ include file="/pages/includes/sirius-head.jsp"%>	

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/onhandquantityview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
</div>

<div class="main-box">
    <table style="border:none" width="100%">
    <tbody>
    <tr>
        <td width="11%" align="left"><spring:message code="product.code"/></td>
        <td width="1%" align="center">:</td>
        <td width="38%" align="left"><c:out value='${product.code}'/></td>
        <td width="16%" align="left"><spring:message code="product.onhand"/></td>
        <td width="1%" align="center">:</td>
        <td width="33%" align="left" id="onHand"></td>
    </tr>
    <tr>
        <td align="left"><spring:message code="product.name"/></td>
        <td align="center">:</td>
        <td align="left"><c:out value='${product.name}'/></td>
        <td align="left"><spring:message code="product.available"/></td>
        <td align="center">:</td>
        <td align="left" id="available"></td>
    </tr>
    <tr>
        <td align="left"><spring:message code="product.category"/></td>
        <td align="center">:</td>
        <td align="left"><c:out value='${product.productCategory.name}'/></td>
        <td align="left"><spring:message code="product.reserved"/></td>
        <td align="center">:</td>
        <td align="left" id="reserved"></td>
    </tr>
    </tbody>
    <tfoot>
        <tr><td colspan="6">&nbsp;</td></tr>
    </tfoot>
    </table>
    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
    <thead>
        <tr>
            <%-- <th width="15%"><spring:message code="organization"/></th> --%>
            <th width="8%"><spring:message code="facility"/></th>
            <th width="10%"><spring:message code="grid"/></th>
            <th width="10%"><spring:message code="container"/></th>
            <th width="8%"><spring:message code="barcode"/></th>
            <th width="8%"><spring:message code="product.lot"/></th>
            <th width="8%"><spring:message code="product.onhand"/></th>
            <th width="8%"><spring:message code="product.reserved"/></th>
            <th width="8%"><spring:message code="product.ontransfer"/></th>
            <th width="10%"><spring:message code="product.available"/></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${details}" var="detail" varStatus="status">
            <tr class="details" data-index="${status.index}">
                <%-- <td>
                    <c:if test="${status.index == 0 || detail.organization.id != details[status.index-1].organization.id}">
                        <c:out value='${detail.organization.fullName}'/>
                    </c:if>
                </td> --%>
                <td>
                    <c:if test="${status.index == 0 || detail.container.grid.facility.id != details[status.index-1].container.grid.facility.id}">
                        <c:out value='${detail.container.grid.facility.name}'/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${status.index == 0 || detail.container.grid.id != details[status.index-1].container.grid.id}">
                        <c:out value='${detail.container.grid.name}'/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${status.index == 0 || detail.container.id != details[status.index-1].container.id}">
                        <c:out value='${detail.container.name}'/>
                    </c:if>
                </td>
                <td><c:out value='${detail.lot.serial}'/></td>
                <td><c:out value='${detail.lot.code}'/></td>
                <td id="onHand${status.index}"><fmt:formatNumber value='${detail.onHand}' pattern=',##0.00'/></td>
                <td id="reserved${status.index}"><fmt:formatNumber value='${detail.reserved}' pattern=',##0.00'/></td>
                <td><fmt:formatNumber value='${detail.onTransfer}' pattern=',##0.00'/></td>
                <td id="available${status.index}"><fmt:formatNumber value='${detail.availableSale}' pattern=',##0.00'/></td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot>
        <tr class="end-table"><td colspan="9">&nbsp;</td></tr>
    </tfoot>
    </table>
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">

    const details = document.getElementsByClassName('details');

    let onHand = 0;
    let reserved = 0;
    let available = 0;

    for(const detail of details)
    {
        const index = detail.getAttribute('data-index');
        
        onHand += document.getElementById('onHand' + index).innerHTML.toNumber();
        reserved += document.getElementById('reserved' + index).innerHTML.toNumber();
        available += document.getElementById('available' + index).innerHTML.toNumber();
    }

    document.getElementById('onHand').innerHTML = onHand.numberFormat('#,##0.00');
    document.getElementById('reserved').innerHTML = reserved.numberFormat('#,##0.00');
    document.getElementById('available').innerHTML = available.numberFormat('#,##0.00');

</script>