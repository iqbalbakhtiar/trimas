<c:if test='${adapter.adapters[status.index-1].item.referenceItem.warehouseTransaction.id != item.item.referenceItem.warehouseTransaction.id}'>
    <tr class="strong">
        <td class="tools">
            <a href="#" class="item-button-edit" data-refid="${item.item.referenceItem.warehouseTransaction.id}"></a>
        </td>
        <td>
            <c:out value="${item.item.referenceItem.organization.fullName}"/>
        </td>
        <td colspan="${adapter.reference.message == 'item' ? 0 : 2}">
            <a href="<c:url value='/page/${item.item.transactionSource.uri}preedit.htm?id=${item.item.referenceItem.warehouseTransaction.id}${not empty item.item.referenceItem.warehouseTransaction.ref ? "&type=item.item.referenceItem.warehouseTransaction.ref" : ""}'/>"><c:out value='${item.item.referenceItem.referenceCode}'/></a>
        </td>
        <td><fmt:formatDate value='${item.item.referenceItem.date}' pattern='dd-MM-yyyy'/></td>
        <td>
            <c:if test="${not empty item.item.referenceItem.deliveryDate}">
                <fmt:formatDate value="${item.item.referenceItem.deliveryDate}" pattern="dd-MM-yyyy"/>
            </c:if>
            <c:if test="${empty item.item.referenceItem.deliveryDate}">
                N/A
            </c:if>
        </td>
        <td colspan="3"><c:out value='${item.item.referenceItem.referenceFrom}'/></td>
    </tr>
</c:if>
<tr>
    <td>&nbsp;</td>
    <td width="3%">&nbsp;</td>
    <td width="20%">&nbsp;&nbsp;&nbsp;<c:out value='${item.item.product.code} - ${item.item.product.name}'/></td>
    <td colspan="4">&nbsp;</td>
    <td style="text-align: center"><fmt:formatNumber value='${item.item.unreceipted}' pattern=',##0.00'/></td>
    <td><c:out value="${item.item.product.unitOfMeasure.measureId}"></c:out></td>
    <input type="hidden" name="adapters[${status.index}].enabled" id="enabled_${status.index}" class="enabled-input ref-${item.item.referenceItem.warehouseTransaction.id}" value="false"/>
</tr>
<c:if test='${adapter.adapters[status.index+1].item.referenceItem.warehouseTransaction.id != item.item.referenceItem.warehouseTransaction.id}'>
    <tr><td colspan="9">&nbsp;</td></tr>
</c:if>