<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/purchaserequisitionview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
  <sesform:form id="addForm" name="addForm" method="post" modelAttribute="requisition_form" enctype="multipart/form-data">
    <table width="100%" border="0">
      <tr>
        <td width="60%">
          <table style="border:none" width="100%">
            <tr>
              <td width="34%" align="right"><spring:message code="sirius.id"/></td>
              <td width="1%" align="center">:</td>
              <td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
              <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="organization"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="org" path="organization" cssClass="combobox-ext">
                  <c:if test='${not empty requisition_form.organization}'>
                    <form:option value='${requisition_form.organization.id}' label='${requisition_form.organization.fullName}'/>
                  </c:if>
                </form:select>
                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="sirius.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaserequisition.requisitioner"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="requisitioner" path="requisitioner" cssClass="combobox-ext">
                  <c:if test='${not empty requisition_form.requisitioner}'>
                    <form:option value='${requisition_form.requisitioner.id}' label='${requisition_form.requisitioner.fullName}'/>
                  </c:if>
                </form:select>
                <a class="item-popup" onclick="openRequisitioner()" title="<spring:message code='purchaserequisition.requisitioner'/>" />
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaserequisition.reason"/></td>
              <td width="1%" align="center">:</td>
              <td><form:textarea path="reason" rows="6" cols="45"/></td>
              <td>&nbsp;</td>
            </tr>
          </table>
        </td>
		<td width="40%" valign="top">
		</td>
      </tr>
    </table>
    <br/>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
      <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <div class="toolbar-clean">
          <div class="toolbar-clean">
            <a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
            <a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
            <div class="item-navigator">&nbsp;</div>
          </div>
          <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
              <th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
              <th width="60%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
  $(function(){
    var $index = 0; // For Line Item Index

    $('.item-button-save').click(function(){
      if(validation())
        save();
    });

    $('.item-button-new').click(function() {
      addLine($index);
      $index++;
    });

    $('.checkall').click(function () {
      $('.check').prop("checked", this.checked);
    });

    $('.item-button-delete').click(function () {
      $('.check').each(function(){
        if(this.checked){
          this.checked = false;
          $(this).parent().parent().remove();
        }
      });
      $('.checkall').prop("checked", false);
    });
  });

  function validation() {
    var organization = $('#org').val();
    if (organization == null || organization === "") {
      alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
      return false;
    }

    var date = $('#date').val();
    if (date == null || date === "") {
      alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
      return false;
    }

    var requisitioner = $('#requisitioner').val();
    if (requisitioner == null || requisitioner === "") {
      alert('<spring:message code="sirius.requisitioner"/> <spring:message code="notif.empty"/> !');
      return false;
    }

    if ($('#lineItem tr').length === 0) {
      alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
      return false;
    }

    // Validasi Line Items
    var isValid = true;
    $('#lineItem tr').each(function(index){
      var $row = $(this);

      // Validasi product
      var product = $row.find('select[id^="product["]').val();
      if (product == null || product === "") {
        alert('<spring:message code="product"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
        isValid = false;
        return false; // Keluar dari each
      }

      // Validasi quantity
      var qtyStr = $row.find('input[id^="quantity["]').val().replace(/,/g, '');
      var qty = parseFloat(qtyStr);
      if (isNaN(qty) || qty <= 0) {
        alert('<spring:message code="sirius.qty"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
        isValid = false;
        return false;
      }
    });

    if (!isValid)
      return false;

    return true;
  }

  function save() {
    $.ajax({
      url:"<c:url value='/page/purchaserequisitionadd.htm'/>",
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
            window.location="<c:url value='/page/purchaserequisitionpreedit.htm?id='/>"+json.id;
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

  function addLine($index) {
    $tbody = $('#lineItem');
    $tr = $('<tr/>');

    $cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);
    $product = List.get('<select class="combobox productInput" onchange="checkDuplicate(this);"/>','product['+$index+']');
    $productImg = List.img('<spring:message code="product"/>', $index, 'openProduct("'+$index+'")');
    $qty = List.get('<input type="text" class="input-decimal" size="6"/>','quantity['+$index+']', '0.00');
    $uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+$index+']');
    $note = List.get('<input type="text" size="40"/>','note['+$index+']');

    $tr.append(List.col([$cbox]));
    $tr.append(List.col([$product, $productImg]));
    $tr.append(List.col([$uom]));
    $tr.append(List.col([$qty]));
    $tr.append(List.col([$note]));

    $tbody.append($tr);

    $(".input-decimal").bind(inputFormat);
  }

  function openProduct(index) {
    openpopup("<c:url value='/page/popupproductview.htm?&target=product['/>"+index+"]&index="+index);
  }

  function checkDuplicate(element) {
    // Memanggil String.duplicate untuk mengecek duplikasi pada kelas 'productInput'
    var isDuplicated = String.duplicate('productInput');

    if (isDuplicated) {
      alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
      $(element).closest('tr').remove();
    }
  }

  function openRequisitioner() {
    if (!$('#org').val()) {
      alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
      return;
    }

    const orgId = $('#org').val();
    const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
    const params = {
      target: 'requisitioner', // Id Dropdown (Select) element
      organization: orgId, // Org (PartyTo)
      fromRoleType: 3, // Employee
      toRoleType: 2, // Company
      relationshipType: 2 // Employment Relationship
    };

    openpopup(buildUrl(baseUrl, params));
  }
</script>