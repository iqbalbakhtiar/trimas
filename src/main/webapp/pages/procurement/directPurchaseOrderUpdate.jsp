<%@ include file="/common/sirius-general-top.jsp"%>
<%--This Page Copied From SalesOrderAdd--%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/directpurchaseorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
  <a class="item-button-print"  href="<c:url value='/page/directpurchaseorderprint.htm?id=${dpo_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
  <c:if test="${access.add and dpo_edit.barcodeable}">
  	<a class="item-button-doc" href="javascript:createBarcode(${dpo_edit.id});"><span><spring:message code="sirius.create"/>&nbsp;<spring:message code="barcode"/></span></a>
  </c:if>
</div>

<div class="main-box">
  <sesform:form id="addForm" name="addForm" method="post" modelAttribute="dpo_form" enctype="multipart/form-data">
    <table width="100%" border="0">
      <tr valign="top">
        <td width="60%">
          <table style="border:none" width="100%">
            <tr>
              <td width="34%" align="right"><spring:message code="sirius.id"/></td>
              <td width="1%" align="center">:</td>
              <td width="64%"><input class="inputbox input-disabled" value="${dpo_edit.code}" disabled/></td>
              <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="organization"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" onchange="updateBillShipAddress(this);" disabled="true">
                  <c:if test='${not empty dpo_edit.organization}'>
                    <form:option value='${dpo_edit.organization.id}' label='${dpo_edit.organization.fullName}'/>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="sirius.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" size="10" class="input-disabled" value="<fmt:formatDate value='${dpo_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="dpo.delivery.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="deliveryDate" name="deliveryDate" size="10" class="input-disabled" value="<fmt:formatDate value='${dpo_edit.shippingDate}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="supplier"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty dpo_edit.supplier}'>
                    <form:option value="${dpo_edit.supplier.id}">${dpo_edit.supplier.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="salesorder.tax.type"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="tax" path="tax" onchange="updateDisplay();" cssClass="input-disabled" disabled="true">
                  <c:if test='${not empty dpo_edit.tax}'>
                    <form:option value="${dpo_edit.tax.id}" data-taxrate="${dpo_edit.tax.taxRate}">${dpo_edit.tax.taxName}</form:option>
                  </c:if>
                </form:select>
                <input size="5" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${dpo_edit.tax.taxRate}' pattern=',##0.00'/>"/>&nbsp;%
              </td>
            </tr>
            <c:if test="${not empty dpo_edit.approvable}">
            <tr>
              <td align="right"><spring:message code="approver"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="approver" path="approver" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty dpo_edit.approver}'>
                    <form:option value="${dpo_edit.approver.id}">${dpo_edit.approver.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
            </c:if>
            <tr>
              <td align="right"><spring:message code="sirius.note"/></td>
              <td width="1%" align="center">:</td>
              <td><form:textarea path="note" rows="6" cols="45"/></td>
              <td>&nbsp;</td>
            </tr>
          </table>
        </td>

        <td width="40%" valign="top">
          <table width="100%" style="border: none">
            <tr>
              <td>
                <fieldset>
                  <legend><strong><spring:message code="salesorder.recapitulation"/></strong></legend>
                  <table width="100%" style="border: none">
                    <tr>
                      <td width="80%" align="right"><spring:message code="dpo.purchase"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalSales" value="<fmt:formatNumber value='${adapter.totalItemAmount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><spring:message code="salesorder.tax.amount"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTax" value="<fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><strong><spring:message code="salesorder.total.transaction"/></strong></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTransaction" value="<fmt:formatNumber value='${adapter.totalTransaction}' pattern=',##0.00'/>" name="amount" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                  </table>
                </fieldset>
              </td>
            </tr>
            <tr>
                <td>
                    <fieldset>
                        <legend><strong><spring:message code="sirius.reference"/></strong></legend>
                        <table width="100%" style="border: none">
                        <c:if test="${not empty dpo_edit.receipts}">
                        <tr>
                            <td width="80%" align="right" valign="top"><spring:message code="goodsreceipt"/>:&nbsp;</td>
                            <td width="20%" align="right" nowrap="nowrap">
                                <c:forEach items="${dpo_edit.receipts}" var="rec" varStatus="status">
                                    <a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${rec.id}'/>"><c:out value="${rec.code}"></c:out></a>
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty dpo_edit.invoiceVerifications}">
                        <tr>
                            <td width="80%" align="right" valign="top"><spring:message code="invoiceverification"/>:&nbsp;</td>
                            <td width="20%" align="right" nowrap="nowrap">
                                <c:forEach items="${dpo_edit.invoiceVerifications}" var="ver" varStatus="status">
                                    <a href="<c:url value='/page/invoiceverificationpreedit.htm?id=${ver.id}'/>"><c:out value="${ver.code}"></c:out></a>
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <tr>
            	<td>
                <c:if test="${not empty dpo_edit.approvable}">
	                <%@ include file="/pages/sales/approval-history.jsp" %>
	                <%@ include file="/pages/sales/approval.jsp" %>
                </c:if>
                </td>
            </tr>
          </table>
        </td>

      </tr>
    </table>
    <br/>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
      <div id="address" dojoType="ContentPane" label="<spring:message code='postaladdress.detail'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <table width="100%">
          <tr>
            <td align="right"><spring:message code="purchaseorder.supplieraddress"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="supplierAddress" name="supplierAddress" class="combobox-ext input-disabled" disabled>
                <c:if test="${dpo_edit.supplierAddress != null}">
                  <option value="${dpo_edit.supplierAddress.id}">${dpo_edit.supplierAddress.addressName}</option>
                </c:if>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="supplier.contact"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="supplierPhone" name="supplierPhone" class="combobox-ext input-disabled" disabled>
                <c:if test="${dpo_edit.supplierPhone != null}">
                  <option value="${dpo_edit.supplierPhone.id}">${dpo_edit.supplierPhone.contact}</option>
                </c:if>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="dpo.billto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="billingToAddress" name="billTo" class="combobox-ext input-disabled" disabled>
                <option value="${dpo_edit.billTo.id}">${dpo_edit.billTo.addressName}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="dpo.shipto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <form:select id="shippingToAddress" path="shipTo" cssClass="combobox-ext input-disabled" disabled="true">
                <form:option value="${dpo_edit.shipTo.id}">${dpo_edit.shipTo.name}</form:option>
              </form:select>
            </td>
          </tr>
        </table>
      </div>
      <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <div class="toolbar-clean">
          <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
              <th width="1%" nowrap="nowrap">&nbsp;</th>
              <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="dpo.buying.price"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="sirius.amount"/></th>
              <th width="50%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${dpo_edit.items}" var="item" varStatus="idx">
              <tr>
                <td>&nbsp;</td>
                <td><input id="product[${idx.index}]" size="26" value="${item.product.name}" class="input-disabled" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/></td>
                <td><input id="quantity[${idx.index}]" size="6" value="${item.quantity}" class="input-disabled input-decimal" name="items[${idx.index}].quantity" index="${idx.index}" next="quantity" disabled/></td>
                <td><input id="uom[${idx.index}]" size="6" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/></td>
                <td><input id="amount[${idx.index}]" size="12" value="<fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/>" class="input-disabled input-decimal" name="items[${idx.index}].amount" index="${idx.index}" next="amount" disabled/></td>
                <td><input id="totalAmount[${idx.index}]" size="12" class="input-number input-disabled" disabled value="<fmt:formatNumber value='${item.totalAmount}' pattern=',##0.00'/>"/></td>
                <td><input id="note[${idx.index}]" type="text" value="${item.note}" name="purchaseOrder.items[${idx.index}].note"index="${idx.index}" next="note" size="40"/></td>
              </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="10">&nbsp;</td></tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${dpo_edit.createdBy.fullName}'/> (<fmt:formatDate value='${dpo_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${dpo_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${dpo_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
  $(function(){
    $('.item-button-save').click(function(){
        save();
    });
  });

  function save() {
    $.ajax({
      url:"<c:url value='/page/directpurchaseorderedit.htm'/>",
      data:$('#addForm').serialize(),
      type : 'POST',
      dataType : 'json',
      beforeSend:function()
      {
        $dialog.empty();
        $dialog.html('<spring:message code="notif.saving"/>');
        $dialog.dialog('open');
      },
      success : function(json) {
        if(json)
        {
          if(json.status === 'OK')
          {
            $dialog.dialog('close');
            window.location="<c:url value='/page/directpurchaseorderpreedit.htm?id='/>"+json.id;
          }
          else
          {
            $dialog.empty();
            $dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
          }
        }
      }
    });
  }
  
  function createBarcode(id)
  {
	const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.create"/> <spring:message code="barcode"/>) ?</div>').dialog(
	{
		autoOpen: false, title: '<spring:message code="notif.confirmation"/>', modal:true,
		buttons: {
			'<spring:message code="sirius.yes"/>': function() {
				$(this).dialog('close');

				var url = "<c:url value='/page/barcodegrouppreadd1.htm?barcodeType=PURCHASE_ORDER&referenceId='/>"+id;
				window.location = url;
			},
			'<spring:message code="sirius.no"/>': function() {
				$(this).dialog('close');
			}
		}
	});

	confirmDialog.dialog('open');
  }
</script>