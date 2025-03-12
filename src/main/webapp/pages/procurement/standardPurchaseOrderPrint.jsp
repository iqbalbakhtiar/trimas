<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
  <a class="item-button-back" href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${purchase_edit.id}'/>"><span><spring:message code="sirius.back"/></span></a>
  <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
  <a class="item-button-export-xls" download="standardpurchaseorder.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Sales Order');"><span>Export</span></a>
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
          <td colspan="6" align="right" class="CSS3" width="96%"><strong><spring:message code="po.capital"/></strong></td>
          <td colspan="2" width="2%">&nbsp;</td>
        </tr>
        <tr><td colspan="10" height="20">&nbsp;</td></tr>
        <tr>
          <td colspan="2">&nbsp;</td>
          <td colspan="6">
            <table border="0" width="100%" cellpadding="0" cellspacing="0">
              <tr>
                <td width="19%" align="left"><spring:message code="po.number"/></td>
                <td width="1%">:</td>
                <td width="33"><c:out value='${purchase_edit.code}'/></td>
                <td width="19%"  align="left"><spring:message code="paymenttype"/></td>
                <td width="1%">:</td>
                <td width="33">
<%--                  <c:out value='${purchase_edit.paymentMethodType}'/>--%>
                </td>
              </tr>
              <tr>
                <td align="left"><spring:message code="sirius.date"/></td>
                <td width="1%">:</td>
                <td width="33"><fmt:formatDate value='${purchase_edit.date}' pattern='dd - MMM - yyyy'/></td>
                <td align="left"><spring:message code="dpo.delivery.date"/></td>
                <td width="1%">:</td>
                <td width="33"><fmt:formatDate value='${purchase_edit.shippingDate}' pattern='dd - MMM - yyyy'/></td>
              </tr>
              <tr>
                <td align="left"><spring:message code="po.supplier.no"/></td>
                <td width="1%">:</td>
                <td width="33">
<%--                  <c:out value='${purchase_edit.requisition.supplierNumber}'/>--%>
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
                <th align="left" width="33%"><spring:message code="po.vendor.capital"/> : </th>
                <th align="left" width="33%"><spring:message code="po.billto.capital"/> : </th>
              </tr>
              <tr valign="top">
                <td><c:out value='${purchase_edit.supplier.fullName}'/></td>
                <td><c:out value='${purchase_edit.organization.fullName}'/></td>
              </tr>
              <tr valign="top">
                <td><c:out value='${purchase_edit.supplierAddress.address}'/></td>
                <td><c:out value='${purchase_edit.billTo.address}'/></td>
              </tr>
              <tr valign="top">
                <td><c:out value='${purchase_edit.supplierPhone.contact}'/></td>
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
                <th rowspan="2" width="2%" align="left" class="bordered">&nbsp;<spring:message code="sirius.no"/>.</th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom"><spring:message code="product.name"/></th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom"><spring:message code="po.fromprocuct"/></th>
                <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.color"/></th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.process"/></th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.amount"/></th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.price"/>(${purchase_edit.currency.symbol})</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.total"/>(${purchase_edit.currency.symbol})</th>
                <th rowspan="2" width="10%" align="center" class="border-right border-top border-bottom"><spring:message code="sirius.note"/></th>
                <c:if test="${feature}">
                  <th rowspan="2" width="15%" align="center" class="border-right border-top border-bottom"><spring:message code="product.feature"/></th>
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
                <td align="left" class="border-top"><spring:message code="purchaseorderitem.subtotal"/></td>
                <td align="right" class="border-top" style="padding-right:5px;"><fmt:formatNumber value='${adapter.totalItemAmount}' pattern=',##0.00'/></td>
                <td align="left" colspan="2" class="border-top">&nbsp;</td>
              </tr>
              <c:if test='${adapter.taxAmount > 0}'>
                <tr>
                  <td colspan="6">&nbsp;</td>
                  <td align="left"><spring:message code="tax.ppn.long"/> (<spring:message code="tax.ppn"/>)</td>
                  <td align="right" style="padding-right:5px;"><fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/></td>
                </tr>
              </c:if>
              <tr>
                <td colspan="6">&nbsp;</td>
                <td align="left" class="border-top"><strong><spring:message code="sirius.total"/></strong></td>
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
                <td align="center" colspan="3" width="20%"><spring:message code="approval"/>,</td>
                <td width="2%">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="center" colspan="3" width="20%"><spring:message code="supplier"/>,</td>
                <td width="10%">&nbsp;</td>
              </tr>
              <tr height="70">
                <td width="2%" align="right" valign="bottom">(&nbsp;</td>
                <td width="20%" valign="bottom" align="center"><c:out value=''/></td>
                <td width="2%" valign="bottom">&nbsp;)</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td width="2%" align="right" valign="bottom">(&nbsp;</td>
                <td width="20%" valign="bottom" align="center"><c:out value='${purchase_edit.supplier.fullName}'/></td>
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

    <%@include file="standardPurchaseOrderExcel.jsp"%>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>