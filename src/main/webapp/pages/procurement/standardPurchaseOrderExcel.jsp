<table border="0" width="100%" cellpadding="0" cellspacing="0" id="size" style="display: none">
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="3" align="right"><strong>PURCHASE ORDER</strong></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>PO Number</td>
        <td colspan="2"><c:out value='${purchase_edit.code}'/></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="2">Payment Type</td>
        <td>: </td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>Date</td>
        <td colspan="2">: <fmt:formatDate value='${purchase_edit.date}' pattern='dd - MMM - yyyy'/></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="2">Delivery Date</td>
        <td>: <fmt:formatDate value='${purchase_edit.shippingDate}' pattern='dd - MMM - yyyy'/></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>Supplier No</td>
        <td colspan="2">: </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><strong>VENDOR</strong></td>
        <td>:</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><strong>BILL TO</strong></td>
        <td>:</td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td colspan="4"><c:out value='${purchase_edit.supplier.fullName}'/></td>
        <td></td>
        <td></td>
        <td colspan="5"><c:out value='${purchase_edit.organization.fullName}'/></td>
    </tr>
    <tr>
        <td colspan="4"><c:out value='${purchase_edit.supplierAddress.address}'/></td>
        <td></td>
        <td></td>
        <td colspan="5"><c:out value='${purchase_edit.billTo.address}'/></td>
    </tr>
    <tr>
        <td colspan="4">
            <c:out value="=\"${purchase_edit.supplierPhone.contact}\""/>
        </td>
        <td></td>
        <td></td>
        <td colspan="5"></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="center"><strong>No</strong></td>
        <td colspan="3"><strong>Nama Barang</strong></td>
        <td><strong>Dari Barang</strong></td>
        <td><strong>Warna</strong></td>
        <td><strong>Proses</strong></td>
        <td align="right"><strong>Jumlah</strong></td>
        <td align="right"><strong>Harga (${purchase_edit.currency.symbol})</strong></td>
        <td align="right"><strong>Total (${purchase_edit.currency.symbol})</strong></td>
        <td><strong>Keterangan</strong></td>
    </tr>
    <c:forEach items='${adapter.purchaseOrder.items}' var='item' varStatus='status'>
        <tr>
            <td align="center"><c:out value="${status.index+1}"/></td>
            <td colspan="3">${item.product.code} - ${item.product.name}</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td align="right"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
            <td align="right"><fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/></td>
            <td align="right"><fmt:formatNumber value='${item.totalAmount}' pattern=',##0.00'/></td>
            <td><c:out value="${item.note}"/></td>
        </tr>
    </c:forEach>
    <c:if test="${fn:length(adapter.purchaseOrder.items) < 5}">
        <c:forEach begin="0" end="${5 - fn:length(adapter.purchaseOrder.items)}" step="1">
            <tr>
                <td align="center"></td>
                <td colspan="3"></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
    </c:if>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="3" align="left">Sub total</td>
        <td align="right"><fmt:formatNumber value='${adapter.totalItemAmount}' pattern=',##0.00'/></td>
        <td></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="3" align="left">Pajak Pertambahan nilai (PPN)</td>
        <td align="right"><fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/></td>
        <td></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td colspan="3" align="left">Total</td>
        <td align="right"><strong><fmt:formatNumber value='${adapter.totalTransaction}' pattern=',##0.00'/></strong></td>
        <td></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td></td>
        <td>Approval,</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Supplier,</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>(</td>
        <td></td>
        <td align="right">)</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>(</td>
        <td><c:out value='${purchase_edit.supplier.fullName}'/></td>
        <td align="right">)</td>
    </tr>
</table>