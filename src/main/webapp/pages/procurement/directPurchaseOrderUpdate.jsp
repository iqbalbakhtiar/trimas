<%@ include file="/common/sirius-general-top.jsp"%>
<%--This Page Copied From SalesOrderAdd--%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/directpurchaseorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
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
              <td width="64%"><input class="inputbox input-disabled" value="${dpo_form.code}" disabled/></td>
              <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="organization"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" onchange="updateBillShipAddress(this);" disabled="true">
                  <c:if test='${not empty dpo_form.organization}'>
                    <form:option value='${dpo_form.organization.id}' label='${dpo_form.organization.fullName}'/>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="dpo.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" size="10" class="input-disabled" value="<fmt:formatDate value='${dpo_form.date}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="dpo.delivery.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="deliveryDate" name="deliveryDate" size="10" class="input-disabled" value="<fmt:formatDate value='${dpo_form.purchaseOrder.shippingDate}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="supplier"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty dpo_form.supplier}'>
                    <form:option value="${dpo_form.supplier.id}">${dpo_form.supplier.code} ${dpo_form.supplier.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="salesorder.tax.type"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="tax" path="tax" onchange="updateDisplay();" cssClass="input-disabled" disabled="true">
                  <c:if test='${not empty dpo_form.tax}'>
                    <form:option value="${dpo_form.tax.id}" data-taxrate="${dpo_form.tax.taxRate}">${dpo_form.tax.taxName}</form:option>
                  </c:if>
                </form:select>
                <spring:message code="salesorder.tax.rate"/>
                <input size="7" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${dpo_form.tax.taxRate}' pattern=',##0.00'/>"/>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="approver"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="approver" path="approver" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty dpo_form.approver}'>
                    <form:option value="${dpo_form.approver.id}">${dpo_form.approver.code} ${dpo_form.approver.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
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
                <%@ include file="/pages/sales/approval-history.jsp" %>
                <%@ include file="/pages/sales/approval.jsp" %>
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
            <td align="right"><spring:message code="dpo.billto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="billingToAddress" name="billTo" class="combobox-ext input-disabled" disabled>
                <option value="${dpo_form.billTo.id}">${dpo_form.billTo.addressName}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="dpo.shipto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <form:select id="shippingToAddress" path="shipTo" cssClass="combobox-ext input-disabled" disabled="true">
                <form:option value="${dpo_form.shipTo.id}">${dpo_form.shipTo.name}</form:option>
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
              <th width="8%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${dpo_form.purchaseOrder.items}" var="item" varStatus="idx">
              <tr>
                <td></td>
                <td>
                  <input id="product[${idx.index}]" size="26" value="${item.product.name}" class="input-disabled productInput"
                         name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/>
                </td>
                <td>
                  <input id="quantity[${idx.index}]" size="6" value="${item.quantity}" class="input-disabled input-decimal"
                         name="items[${idx.index}].quantity" index="${idx.index}" next="quantity" disabled/>
                </td>
                <td>
                  <input id="uom[${idx.index}]" size="6" value="${item.product.unitOfMeasure.measureId}" class="input-disabled"
                         name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
                </td>
                <td>
                  <input id="amount[${idx.index}]" size="12" value="<fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/>"
                         class="input-disabled input-decimal" name="items[${idx.index}].amount"
                         index="${idx.index}" next="amount" disabled/>
                </td>
                <td>
                  <input id="totalAmount[${idx.index}]" size="12" class="input-number input-disabled" disabled value="<fmt:formatNumber value='${item.totalAmount}' pattern=',##0.00'/>"/>
                </td>
                <td>
                  <input id="note[${idx.index}]" type="text" value="${item.note}" name="purchaseOrder.items[${idx.index}].note"
                         index="${idx.index}" next="note" size="40"/>
                </td>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${dpo_form.createdBy.fullName}'/> (<fmt:formatDate value='${dpo_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${dpo_form.updatedBy.fullName}'/> (<fmt:formatDate value='${dpo_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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

  function openapprover() {
    if (!$('#org').val()) {
      alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
      return;
    }

    const orgId = $('#org').val();
    const approver = $('#approver').val();
    const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
    const params = {
      target: 'forwardTo', // Id Dropdown (Select) element
      organization: orgId, // Org (PartyTo)
      fromRoleType: 9, // Purchase Approver
      toRoleType: 2, // Company
      relationshipType: 2, // Employment Relationship
      except: approver // Except this or current approver
    };

    openpopup(buildUrl(baseUrl, params));
  }
</script>