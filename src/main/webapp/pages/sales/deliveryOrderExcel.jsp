<table border="0" width="100%" cellpadding="0" cellspacing="0" id="size" style="display: none">
  <tr>
    <td colspan="2"><strong><c:out value='${deliveryOrder_form.organization.salutation} ${deliveryOrder_form.organization.fullName}'/></strong></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td colspan="2" align="right"><strong>DELIVERY ORDER</strong></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Pelanggan&nbsp;:</td>
    <td colspan="2">
      <c:out value='${deliveryOrder_form.customer.salutation} ${deliveryOrder_form.customer.fullName}'/>
    </td>
    <td></td>
    <td></td>
    <td colspan="2">Sales Order No</td>
    <td colspan="2">:&nbsp;<c:out value='${deliveryOrder_form.code}'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"><c:out value="${deliveryOrder_form.shippingAddress.addressName}"/></td>
    <td></td>
    <td></td>
    <td colspan="2">Tanggal Delivery Order</td>
    <td colspan="2">:&nbsp;<fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd - MM - yyyy'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"><c:out value="${deliveryOrder_form.shippingAddress.address}"/></td>
    <td></td>
    <td></td>
    <td colspan="2">Sales Order No</td>
    <td colspan="2">:&nbsp;<c:out value='${referenceCode}'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"><c:out value="${deliveryOrder_form.shippingAddress.city.name}"/> - <c:out value="${deliveryOrder_form.shippingAddress.postalCode}"/></td>
    <td></td>
    <td></td>
    <td colspan="2">PO Pelanggan No</td>
    <td colspan="2">:&nbsp;<c:out value='${poCode}'/></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="2"></td>
    <td></td>
    <td></td>
    <td colspan="2">Note</td>
    <td colspan="2">:&nbsp;<c:out value='${deliveryOrder_form.note}'/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><strong>No.</strong></td>
    <td colspan="6"><strong>Nama Barang</strong></td>
    <td><strong>Jumlah</strong></td>
    <td><strong>Satuan</strong></td>
  </tr>
  <c:forEach items='${deliveryOrder_form.deliveryOrder.items}' var='item' varStatus='status'>
    <tr>
      <td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
      <td colspan="6" align="left" width="30%" nowrap="nowrap">&nbsp;${item.salesReferenceItem.product.name}</td>
      <td align="right" width="5%" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.quantity}' pattern=',##0.00'/></td>
      <td align="center" width="5%">${item.salesReferenceItem.product.unitOfMeasure.measureId}</td>
    </tr>
  </c:forEach>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="1"></td>
    <td colspan="2" valign="top">Barang Diterima Oleh:</td>
    <td></td>
    <td colspan="2" valign="top">Countersigned By:</td>
    <td colspan="1"></td>
    <td rowspan="2" colspan="2">Sopir:<br>No Mobil:</td>
  </tr>
  <tr>
    <td colspan="7"></td>
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
    <td></td>
    <td>(</td>
    <td>)</td>
    <td></td>
    <td colspan="2">(&nbsp;Sales Supervisor&nbsp;)</td>
    <td></td>
    <td>(</td>
    <td>)</td>
  </tr>
</table>