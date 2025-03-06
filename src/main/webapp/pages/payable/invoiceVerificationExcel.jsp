<table border="0" width="100%" cellpadding="0" cellspacing="0" id="size" style="display: none">
    <tr>
        <td colspan="5"><strong><c:out value=' ${verification_edit.organization.initial} ${verification_edit.organization.fullName}'/></strong></td>
        <td colspan="4"></td>
        <td align="right"><strong>VERIFIKASI</strong></td>
    </tr>
    <tr>
        <td colspan="5">
        <c:forEach items="${verification_edit.organization.postalAddresses}" var="postal">
            <c:forEach items="${postal.addressTypes}" var="types">
                <c:if test='${types.type == "TAX" and types.enabled}'>
                    <c:out value="${types.postalAddress.address}"/><br/>
                </c:if>
            </c:forEach>
        </c:forEach>
        </td>
        <td colspan="5"></td>
    </tr>
    <tr>
        <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
        <td>ID</td>
        <td colspan="4">: <c:out value=' ${verification_edit.code}'/></td>
        <td></td>
        <td colspan="2">No. Invoice</td>
        <td colspan="2">: <c:out value='${verification_edit.invoice}'/></td>
    </tr>
    <tr>
        <td align="left" valign="top">Pemasok</td>
        <td colspan="4">: <c:out value='${verification_edit.supplier.fullName} '/></td>
        <td></td>
        <td colspan="2">Tanggal Invoice</td>
        <td colspan="2">: <fmt:formatDate value='${verification_edit.date}' pattern='dd - MM - yyyy'/></td>
    </tr>
    <tr>
        <td></td>
        <td colspan="4">
            <c:forEach items="${verification_edit.supplier.postalAddresses}" var="postal">
                <c:forEach items="${postal.addressTypes}" var="types">
                    <c:if test='${types.type == "TAX" and types.enabled}'>
                        <c:out value="${types.postalAddress.address}"/><br/>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </td>
        <td></td>
        <td colspan="2">Tanggal Jatuh Tempo</td>
        <td colspan="2">: <fmt:formatDate value='${verification_edit.dueDate}' pattern='dd - MM - yyyy'/></td>
    </tr>
    <tr>
        <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
        <td align="center"><strong>No.</strong></td>
        <td align="center" colspan="4"><strong>Nama Barang</strong></td>
        <td align="center"><strong>Jumlah</strong></td>
        <td align="center" colspan="2"><strong>Harga Satuan</strong></td>
        <td align="center" colspan="2"><strong>Total</strong></td>
    </tr>
    <c:forEach items="${verification_edit.items}" var="item" varStatus='status'>
        <tr>
            <td align="center"><c:out value="${status.count}"/></td>
            <td align="left" colspan="4"><c:out value='${item.product.code}'/> <c:out value='${item.product.name}'/></td>
            <td align="center"><fmt:formatNumber value='${item.quantity}' pattern=',##0'/></td>
            <td align="right" colspan="2"><fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/></td>
            <td align="right" colspan="2"><fmt:formatNumber value='${(item.money.amount * item.quantity)}' pattern=',##0.00'/></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="5">&nbsp;</td>
        <td colspan="3">Sub Total</td>
        <td colspan="2"><fmt:formatNumber value='${adapter.subTotal}' pattern=',##0.00'/></td>
    </tr>
    <tr>
        <td colspan="5">&nbsp;</td>
        <td colspan="3">Pajak Pertambahan Nilai (PPN)</td>
        <td colspan="2"><fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/></td>
    </tr>
    <tr>
        <td colspan="5">&nbsp;</td>
        <td colspan="3">Total</td>
        <td colspan="2"><fmt:formatNumber value='${adapter.totalAfterTax}' pattern=',##0.00'/></td>
    </tr>
    <tr>
        <td colspan="5">&nbsp;</td>
        <td colspan="1">Terbilang</td>
        <td colspan="4">: <c:out value='${fn:toLowerCase(string)}'/></td>
    </tr>
    <tr>
        <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
        <td><strong>Lampiran</strong></td>
        <td colspan="9"></td>
    </tr>
    <tr>
        <td>- Lainnya</td>
        <td colspan="9"></td>
    </tr>
    <tr>
        <td colspan="10"></td>
    </tr>
    <tr>
        <td colspan="7"></td>
        <td>Hormat kami,</td>
        <td colspan="2"></td>
    </tr>
    <tr>
        <td colspan="10"></td>
    </tr>
    <tr>
        <td colspan="10"></td>
    </tr>
    <tr>
        <td colspan="10"></td>
    </tr>
    <tr>
        <td colspan="6"></td>
        <td align="left">(</td>
        <td></td>
        <td align="right">)</td>
        <td></td>
    </tr>
</table>