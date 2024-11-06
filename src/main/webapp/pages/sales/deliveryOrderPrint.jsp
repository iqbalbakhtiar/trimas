<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
  <a class="item-button-back" href="<c:url value='/page/deliveryorderpreedit.htm?id=${deliveryOrder_form.deliveryOrder.id}'/>"><span><spring:message code="sirius.back"/></span></a>
  <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
  <a class="item-button-export-xls" download="deliveryorder.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Delivery Order');"><span>Export</span></a>
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
      <td width="44%" colspan="3" align="right" class="CSS3" valign="top"><strong>DELIVERY ORDER</strong></td>
      <td width="2%" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="10" height="15">&nbsp;</td>
    </tr>
  </table>
  <table border="0" width="100%" cellpadding="0" cellspacing="0">
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
            <td width="40%">&nbsp;</td>
            <td width="20%">Delivery Order No</td>
            <td width="1%">:</td>
            <td width="30%"><c:out value='${deliveryOrder_form.code}'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>Tanggal Delivery Order</td>
            <td>:</td>
            <td><fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd - MM - yyyy'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>Sales Order No</td>
            <td>:</td>
            <td><c:out value='${referenceCode}'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>PO Pelanggan No</td>
            <td>:</td>
            <td><c:out value='${poCode}'/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>Note</td>
            <td>:</td>
            <td><c:out value='${deliveryOrder_form.note}'/></td>
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
            <th align="left" class="border-top border-bottom">No.</th>
            <th align="left" class="border-top border-bottom">Nama Barang</th>
            <th align="right" class="border-top border-bottom">Jumlah</th>
            <th align="center" class="border-top border-bottom">Satuan</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items='${deliveryOrder_form.deliveryOrder.items}' var='item' varStatus='status'>
            <tr>
              <td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
              <td align="left" width="60%" nowrap="nowrap">&nbsp;${item.salesReferenceItem.product.name}</td>
              <td align="right" width="10%" style="padding-right:5px;"><fmt:formatNumber value='${item.salesReferenceItem.quantity}' pattern=',##0.00'/></td>
              <td align="center" width="20%">${item.salesReferenceItem.product.unitOfMeasure.measureId}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </td>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="10">&nbsp;</td>
    </tr>

    <tr id="test">
      <td width="2%" colspan="2">&nbsp;</td>
      <td width="96%" colspan="6">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr><%--            Text Line--%>
            <td width="33%" align="center" valign="top">Barang Diterima Oleh:</td>
            <td width="33%" align="center" valign="top">Countersigned By:</td>
            <td width="33%" align="center">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40%">&nbsp;</td>
                  <td align="left" width="60%">Sopir:</td>
                </tr>
                <tr>
                  <td width="40%">&nbsp;</td>
                  <td align="left" width="60%">No Mobil:</td>
                </tr>
              </table>
            </td>
          </tr><%--            Text Line--%>
          <tr><%--            Empty Line--%>
            <td colspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
          </tr><%--            Empty Line--%>
          <tr>
            <td align="center">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right">(</td>
                  <td width="30%">&nbsp;</td>
                  <td align="left">)</td>
                </tr>
              </table>
            </td>
            <td align="center">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right">(</td>
                  <td align="center" width="30%">Sales Supervisor</td>
                  <td align="left">)</td>
                </tr>
              </table>
            </td>
            <td align="center">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right">(</td>
                  <td width="30%">&nbsp;</td>
                  <td align="left">)</td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="2%">&nbsp;</td>
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
  <%@include file="deliveryOrderExcel.jsp"%>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>