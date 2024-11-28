<tr class="strong">
    <td class="tools">
        <input type="checkbox" class="check" index="${item.item.transactionSource.message}${item.item.referenceItem.warehouseTransaction.id}${adapter.reference.message == 'reference' ? '' : status.index}"/>
    </td>
    <td colspan="${adapter.reference.message == 'item' ? 0 : 3}">
        <a href="<c:url value='/page/${item.item.transactionSource.uri}preedit.htm?id=${item.item.referenceItem.warehouseTransaction.id}${not empty item.item.referenceItem.warehouseTransaction.ref ? "&type=item.item.referenceItem.warehouseTransaction.ref" : ""}'/>"><c:out value='${item.item.referenceItem.referenceCode}'/></a>
        &nbsp;
        <form:checkbox hidden="hidden" cssClass="${item.item.transactionSource.message}${item.item.referenceItem.warehouseTransaction.id} ${item.item.transactionSource.message}${item.item.referenceItem.warehouseTransaction.id}${status.index}" path='adapter.adapters[${status.index}].enabled'/>
    </td>
    <td width="20%"><c:out value='${item.item.product.code} - ${item.item.product.name}'/></td>
    <td align="right"><fmt:formatNumber value='${item.item.unreceipted}' pattern=',##0'/></td>
    <td><fmt:formatDate value='${item.item.referenceItem.date}' pattern='dd-MM-yyyy'/></td>
    <td><c:out value='${item.item.referenceItem.destination.name}'/></td>
    <td><c:out value='${item.item.referenceItem.tax.taxId}'/></td>
    <td><c:out value='${item.item.referenceItem.referenceFrom}'/></td>
    <td><spring:message code='warehousetransactionsource.${item.item.transactionSource.message}'/></td>
</tr>