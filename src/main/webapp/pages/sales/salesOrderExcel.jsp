<table border="0" width="100%" cellpadding="0" cellspacing="0" id="size" style="display: none">
  <tr>
    <td colspan="2"><strong><c:out value='${salesOrder_form.organization.fullName} ${salesOrder_form.organization.salutation}'/></strong></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td colspan="2" align="right"><strong>SALES ORDER</strong></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Pelanggan&nbsp;:</td>
    <td colspan="2">
      <c:out value='${salesOrder_form.customer.salutation} ${salesOrder_form.customer.fullName}'/>
    </td>
    <td></td>
    <td></td>
    <td></td>
    <td colspan="2">Sales Order No</td>
    <td colspan="2">:&nbsp;<c:out value='${salesOrder_form.code}'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"><c:out value="${salesOrder_form.shippingAddress.addressName}"/></td>
    <td></td>
    <td></td>
    <td></td>
    <td colspan="2">Tanggal Order</td>
    <td colspan="2">:&nbsp;<fmt:formatDate value='${salesOrder_form.date}' pattern='dd - MM - yyyy'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"><c:out value="${salesOrder_form.shippingAddress.city.name}"/> - <c:out value="${salesOrder_form.shippingAddress.postalCode}"/></td>
    <td></td>
    <td></td>
    <td></td>
    <td colspan="2">Note</td>
    <td colspan="2">:&nbsp;<c:out value='${salesOrder_form.note}'/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><strong>No.</strong></td>
    <td colspan="3"><strong>Nama Barang</strong></td>
    <td><strong>Jumlah</strong></td>
    <td><strong>Satuan</strong></td>
    <td><strong>Harga Satuan</strong></td>
    <td><strong>Diskon(%)</strong></td>
    <td><strong>Harga Nett</strong></td>
    <td><strong>Total</strong></td>
  </tr>
  <c:forEach items='${adapter.items}' var='item' varStatus='status'>
    <tr>
      <td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
      <td colspan="3" align="left" width="30%" nowrap="nowrap">&nbsp;${item.product.name}</td>
      <td align="right" width="5%" style="padding-right:5px;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
      <td align="center" width="5%">${item.product.unitOfMeasure.measureId}</td>
      <td align="right" width="12%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.amount}' pattern=',##0.00'/></td>
      <td align="right" width="8%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.discount}' pattern=',##0.00'/></td>
      <td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.priceNett}' pattern=',##0.00'/></td>
      <td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.subTotal}' pattern=',##0.00'/></td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan="9" align="right"><strong>Total :</strong></td>
    <td align="right" style="padding-right:5px;"><strong><fmt:formatNumber value='${adapter.totalAmount}' pattern=',##0.00'/></strong></td>
  </tr>
</table>