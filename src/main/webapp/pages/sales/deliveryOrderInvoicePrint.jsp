<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
  <a class="item-button-back" href="<c:url value='/page/deliveryorderpreedit.htm?id=${deliveryOrder_form.id}'/>"><span><spring:message code="sirius.back"/></span></a>
  <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
  <a class="item-button-export-xls" download="deliveryorderinvoice.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Delivery Order Invoice');"><span>Export</span></a>
</div>

<div class="main-box">

  <table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
    <tr>
      <td style="background: black" width="1%">&nbsp;</td>
      <td width="1%">&nbsp;</td>
      <td width="96%">&nbsp;</td>
      <td width="1%">&nbsp;</td>
      <td style="background: black" width="1%">&nbsp;</td>
    <tr>
  </table>
  <table border="0" width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td width="2%" colspan="2">&nbsp;</td>
      <td width="52%" colspan="3" class="CSS3" align="left">
        <strong><c:out value='${deliveryOrder_form.organization.salutation} ${deliveryOrder_form.organization.fullName}'/></strong>
      </td>
      <td width="44%" colspan="3" align="right" class="CSS3" valign="top"><strong>INVOICE</strong></td>
      <td width="2%" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="10" height="15">&nbsp;</td>
    </tr>
  </table>
  <table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
    <tr>
      <td width="2%" colspan="2">&nbsp;</td>
      <td width="48%" colspan="3" valign="top">
        <table width="100%" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="15%" valign="top">Pelanggan</td>
            <td width="1%" valign="top">:</td>
            <td width="84%">
              <c:out value='${deliveryOrder_form.customer.salutation} ${deliveryOrder_form.customer.fullName}'/><br/>
              <c:out value="${deliveryOrder_form.shippingAddress.addressName}"/><br/>
              <c:out value="${deliveryOrder_form.shippingAddress.address}"/><br/>
              <c:out value="${deliveryOrder_form.shippingAddress.city.name}"/> - <c:out value="${deliveryOrder_form.shippingAddress.postalCode}"/><br/>
            </td>
          </tr>
        </table>
      </td>
      <td width="48%" colspan="3" valign="top">
        <table width="100%" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="25%">&nbsp;</td>
            <td width="20%">Invoice No</td>
            <td width="1%">:</td>
            <td width="30%"><c:out value='${deliveryOrder_form.code}'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>Tanggal Invoice</td>
            <td>:</td>
            <td><fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd - MM - yyyy'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td valign="top">Note</td>
            <td valign="top">:</td>
            <td style="white-space: normal"><c:out value='${deliveryOrder_form.note}'/></td>
          </tr>
        </table>
      </td>
      <td width="2%" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
      <td colspan="6">
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <thead>
                        <tr>
                            <th align="center" class="border-top border-bottom">No.</th>
                            <th align="center" class="border-top border-bottom">Nama Barang</th>
                            <th align="right" class="border-top border-bottom">Jumlah</th>
                            <th align="center" class="border-top border-bottom">Satuan</th>
                            <th align="right" class="border-top border-bottom">Harga Satuan</th>
                            <th align="right" class="border-top border-bottom">Diskon(%)</th>
                            <th align="right" class="border-top border-bottom">Harga Nett</th>
                            <th align="right" class="border-top border-bottom">Total</th>
                        </tr>
          </thead>
          <tbody>
          <c:forEach items='${deliveryOrder_form.deliveryOrder.items}' var='item' varStatus='status'>
            <tr>
              <td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
              <td align="left" width="30%" nowrap="nowrap">&nbsp;${item.salesReferenceItem.product.name}</td>
              <td align="right" width="5%" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.quantity}' pattern=',##0.00'/></td>
              <td align="center" width="5%">${item.salesReferenceItem.product.unitOfMeasure.measureId}</td>
              <td align="right" width="12%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.money.amount}' pattern=',##0.00'/></td>
              <td align="right" width="8%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.discount}' pattern=',##0.00'/></td>
              <td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.discountedPricePerItem}' pattern=',##0.00'/></td>
              <td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.totalAmountPerItemDiscounted}' pattern=',##0.00'/></td>
            </tr>
          </c:forEach>
          </tbody>
          <tfoot>
            <tr>
              <td class="border-top" colspan="7" align="right"><strong>Total :</strong></td>
              <td class="border-top" align="right" style="padding-right:5px;"><fmt:formatNumber value='${deliveryOrder_form.totalLineItemAmountForPrint}' pattern=',##0.00'/></td>
            </tr>
          </tfoot>
        </table>
      </td>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="10">&nbsp;</td>
    </tr>
    <tr>
      <td width="2%" colspan="2">&nbsp;</td>
      <td width="48%" colspan="3" valign="top">
        <table width="100%" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td>- Barang belum lunas adalah barang titipan</td>
          </tr>
          <tr>
            <td>- Tidak menerima pembayaran Cash dan Giro</td>
          </tr>
          <tr>
            <td>- Transfer dan BG Giro melalui rekening di Invoice</td>
          </tr>
          <tr>
            <td>- Transfer dan BG Giro dianggap lunas apabila sudah dicairkan ke Bank</td>
          </tr>
          <tr>
            <td>- Komplain hanya dilayani di tanggal Invoice</td>
          </tr>
          <tr>
            <td>- Dengan segala hormat kami tidak melayani retur yang melewati</td>
          </tr>
          <tr>
            <td>&nbsp;&nbsp;batas hari retur</td>
          </tr>
          <tr>
            <td>- Nomor rekening:</td>
          </tr>
          <tr>
            <td>
              &nbsp;&nbsp;${bankAccount.bankName}: ${bankAccount.accountNo}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;&nbsp;A/N ${bankAccount.holder.fullName}
            </td>
          </tr>
        </table>
      </td>
      <td width="48%" colspan="3" valign="top">
        <table width="100%" cellpadding="0" cellspacing="0" align="left">
          <tr>
            <td width="4%">&nbsp;</td>
            <td width="26%">&nbsp;</td>
            <td width="4%">&nbsp;</td>
<%--            Pembatas--%>
            <td width="32%">&nbsp;</td>
<%--            Pembatas--%>
            <td width="4%">&nbsp;</td>
            <td width="26%">&nbsp;</td>
            <td width="4%">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center">Pengirim:</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="center">Penerima:</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="center">Tanggal terima:</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
          </tr>
          <tr>
            <td align="right">(</td>
            <td align="center">
              <c:out value='${deliveryOrder_form.organization.salutation} ${deliveryOrder_form.organization.fullName}'/>
            </td>
            <td align="left">)</td>
            <td>&nbsp;</td>
            <td align="right">(</td>
            <td align="center">&nbsp;</td><%--Spacing Kurung--%>
            <td align="left">)</td>
          </tr>
        </table>
      </td>
      <td width="2%" colspan="2">&nbsp;</td>
    </tr>
  </table>

  <br>
  <table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
    <tr>
      <td style="background: black" width="1%">&nbsp;</td>
      <td width="1%">&nbsp;</td>
      <td width="96%">&nbsp;</td>
      <td width="1%">&nbsp;</td>
      <td style="background: black" width="1%">&nbsp;</td>
    </tr>
  </table>
  <%@include file="deliveryOrderInvoiceExcel.jsp"%>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>