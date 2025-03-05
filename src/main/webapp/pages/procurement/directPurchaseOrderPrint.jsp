<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
  <a class="item-button-back" href="<c:url value='/page/directpurchaseorderpreedit.htm?id=${dpo_form.purchaseOrder.id}'/>"><span><spring:message code="sirius.back"/></span></a>
  <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
  <a class="item-button-export-xls" download="directpurchaseorder.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Direct Purchase Order');"><span>Export</span></a>
</div>

<div class="main-box">
  <div class="main_container">
    <table border="0" width="100%" cellpadding="0" cellspacing="0">
      <table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
        <tr>
          <td style="background: black" width="1%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td colspan="6" width="96%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td style="background: black" width="1%">&nbsp;</td>
        <tr>
      </table>
      <table border="0" width="100%" cellpadding="0" cellspacing="0" id="printTable">
        <tr>
          <td colspan="2" width="2%">&nbsp;</td>
          <td colspan="6" align="right" class="CSS3" width="96%"><strong>PURCHASE ORDER</strong></td>
          <td colspan="2" width="2%">&nbsp;</td>
        </tr>
        <tr><td colspan="10" height="20">&nbsp;</td></tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td colspan="6">
            <table border="0" width="100%" cellpadding="0" cellspacing="0">
              <tr>
                <td width="19%" align="left">PO Number</td>
                <td width="1%">:</td>
                <td width="33"><c:out value='${dpo_form.purchaseOrder.code}'/></td>
                <td width="19%"  align="left">Payment Type</td>
                <td width="1%">:</td>
                <td width="33">
                  <%--                  <c:out value='${dpo_form.purchaseOrder.paymentMethodType}'/>--%>
                </td>
              </tr>
              <tr>
                <td align="left">Date</td>
                <td width="1%">:</td>
                <td width="33"><fmt:formatDate value='${dpo_form.purchaseOrder.date}' pattern='dd - MMM - yyyy'/></td>
                <td align="left">Delivery Date</td>
                <td width="1%">:</td>
                <td width="33"><fmt:formatDate value='${dpo_form.purchaseOrder.shippingDate}' pattern='dd - MMM - yyyy'/></td>
              </tr>
              <tr>
                <td align="left">Supplier No</td>
                <td width="1%">:</td>
                <td width="33">
                  <%--                  <c:out value='${dpo_form.purchaseOrder.requisition.supplierNumber}'/>--%>
                </td>
                <td align="left"></td>
                <td width="1%"></td>
                <td width="33"></td>
              </tr>
            </table>
          </td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr><td colspan="10">&nbsp;</td></tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td colspan="6">
            <table border="0" width="100%" cellpadding="0" cellspacing="0">
              <tr>
                <th align="left" width="33%">VENDOR : </th>
                <th align="left" width="33%">BILL TO : </th>
              </tr>
              <tr valign="top">
                <td><c:out value='${dpo_form.purchaseOrder.supplier.fullName}'/></td>
                <td><c:out value='${dpo_form.purchaseOrder.organization.fullName}'/></td>
              </tr>
              <tr valign="top">
                <td><c:out value='${dpo_form.purchaseOrder.supplierAddress.address}'/></td>
                <td><c:out value='${dpo_form.purchaseOrder.billTo.address}'/></td>
              </tr>
              <tr valign="top">
                <td><c:out value='${dpo_form.purchaseOrder.supplierPhone.contact}'/></td>
              </tr>
            </table>
          </td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr><td colspan="10">&nbsp;</td></tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td colspan="6">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead>
              <tr>
                <th rowspan="2" width="2%" align="left" class="bordered">&nbsp;No.</th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom">Nama Barang</th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom">Dari Barang</th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom">Warna</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom">Proses</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom">Jumlah</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom">Harga(${dpo_form.purchaseOrder.currency.symbol})</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom">Total(${dpo_form.purchaseOrder.currency.symbol})</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom">Keterangan</th>
                <c:if test="${feature}">
                  <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom">Product Feature</th>
                </c:if>
              </tr>
              </thead>
              <tbody>
              <c:forEach items='${adapter.purchaseOrder.items}' var='item' varStatus='status'>
                <tr>
                  <td align="center" style="padding-left:5px;">
                    <c:out value="${status.index+1}"/>
                  </td>
                  <td >
                      ${item.product.code} - ${item.product.name}
                  </td>
                  <td align="left" style="padding-left:5px;">
                    -
                  </td>
                  <td align="left" style="padding-left:5px;">
                    -
                  </td>
                  <td align="left" style="padding-left:5px;">
                    -
                  </td>
                  <td align="right" style="padding-right:5px;">
                    <fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>
                  </td>
                  <td align="right" style="padding-right:5px;">
                    <fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/>
                  </td>
                  <td align="right" style="padding-right:5px;">
                    <fmt:formatNumber value='${item.totalAmount}' pattern=',##0.00'/>
                  </td>
                  <td >
                    <c:out value="${item.note}"/>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
              <tfoot>
              <c:if test="${fn:length(adapter.purchaseOrder.items) < 5}">
                <c:forEach begin="0" end="${5 - fn:length(adapter.purchaseOrder.items)}" step="1">
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="4">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </c:forEach>
              </c:if>
              <tr>
                <td colspan="6" class="border-top">&nbsp;</td>
                <td align="left" class="border-top">Sub total</td>
                <td align="right" class="border-top" style="padding-right:5px;"><fmt:formatNumber value='${adapter.totalItemAmount}' pattern=',##0.00'/></td>
                <td align="left" colspan="2" class="border-top">&nbsp;</td>
              </tr>
              <c:if test='${adapter.taxAmount > 0}'>
                <tr>
                  <td colspan="6">&nbsp;</td>
                  <td align="left">Pajak Pertambahan Nilai (PPN)</td>
                  <td align="right" style="padding-right:5px;"><fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/></td>
                </tr>
              </c:if>
              <tr>
                <td colspan="6">&nbsp;</td>
                <td align="left" class="border-top"><strong>Total</strong></td>
                <td align="right" class="border-top" style="padding-right:5px;"><strong><fmt:formatNumber value='${adapter.totalTransaction}' pattern=',##0.00'/></strong></td>
                <td align="left" class="border-top" colspan="2">&nbsp;</td>
              </tr>
              </tfoot>
            </table>
          </td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr><td colspan="10" height="22">&nbsp;</td></tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td colspan="6">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="center" colspan="3" width="20%">Approval,</td>
                <td width="2%">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="center" colspan="3" width="20%">Supplier,</td>
                <td width="10%">&nbsp;</td>
              </tr>
              <tr height="70">
                <td width="2%" align="right" valign="bottom">(&nbsp;</td>
                <td width="20%" valign="bottom" align="center"><c:out value=''/></td>
                <td width="2%" valign="bottom">&nbsp;)</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td width="2%" align="right" valign="bottom">(&nbsp;</td>
                <td width="20%" valign="bottom" align="center"><c:out value='${dpo_form.purchaseOrder.supplier.fullName}'/></td>
                <td width="2%" valign="bottom">&nbsp;)</td>
                <td width="10%">&nbsp;</td>
              </tr>
            </table>
          </td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr><td colspan="10" height="22">&nbsp;</td></tr>
      </table>
      <table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
        <tr><td colspan="10" height="22">&nbsp;</td></tr>
        <tr>
          <td style="background: black" width="1%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td colspan="6" width="94%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td style="background: black" width="1%">&nbsp;</td>
        <tr>
      </table>
    </table>
  </div>

  <%@include file="directPurchaseOrderExcel.jsp"%>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>