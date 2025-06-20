<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/purchaserequisitionview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
  <a class="item-button-print"  href="<c:url value='/page/purchaserequisitionprint.htm?id=${requisition_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>
<div class="main-box">
  <sesform:form id="editForm" name="editForm" method="post" modelAttribute="requisition_form" enctype="multipart/form-data">
    <table width="100%" border="0">
      <tr>
        <td width="60%">
          <table style="border:none" width="100%">
            <tr>
              <td width="34%" align="right"><spring:message code="sirius.id"/></td>
              <td width="1%" align="center">:</td>
              <td width="64%"><input class="inputbox input-disabled" value="${requisition_edit.code}" disabled/></td>
              <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="organization"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" readonly="true">
                    <form:option value='${requisition_edit.organization.id}' label='${requisition_edit.organization.fullName}'/>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="sirius.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" size="10" class="input-disabled" value="<fmt:formatDate value='${requisition_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
				<td align="right"><spring:message code="sirius.type"/></td>
				<td width="1%" align="center">:</td>
				<td>
					<form:select id="type" path="type" cssClass="combobox input-disabled" disabled='true'>
						<form:option value="STOCK">STOCK</form:option>
						<form:option value="SERVICE">SERVICE</form:option>
					</form:select>
				</td>							
			</tr>
            <tr>
              <td align="right"><spring:message code="purchaserequisition.requisitioner"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="requisitioner" path="requisitioner" cssClass="combobox-ext input-disabled" readonly="true">
                    <form:option value='${requisition_edit.requisitioner.id}' label='${requisition_edit.requisitioner.fullName}'/>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="contactmechanism.department"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="department" path="department" cssClass="combobox input-disabled" readonly="true">
                	<form:option value="${requisition_edit.department.id}" label="${requisition_edit.department.name}"/>
                </form:select>
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
            <div class="item-navigator">&nbsp;</div>
          </div>
          <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
              <th width="1%" nowrap="nowrap"><div style="width: 30px;">&nbsp;</div></th>
              <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
              <th width="10%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
              <th width="50%" nowrap="nowrap"><spring:message code="purchaseorder"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${requisition_edit.items}" var="item">
              <tr>
                <td class="tools">
                	<c:if test='${access.edit and item.available}'>
                    	<a class="item-button-lock" href="javascript:lockItem(${item.id});" title="<spring:message code='sirius.lock'/>"><span><spring:message code='sirius.lock'/></span></a>
                   	</c:if>
                </td>
                <td><input size="25" value="${item.product.name}" class="input-disabled" disabled/></td>
                <td><input size="8" value="${item.quantity}" class="input-disabled input-decimal" disabled/></td>
                <td><input size="10" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
                <td><input type="text" value="${item.note}" name="purchaseRequisition.items[${idx.index}].note" size="40" ${item.available ? "":"disabled='true' class='input-disabled'"}/></td>
                <td><a href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${item.purchaseOrderItem.purchaseOrder.id}'/>"><c:out value='${item.purchaseOrderItem.purchaseOrder.code}'/></a></td>
              </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="6">&nbsp;</td></tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${requisition_form.createdBy.fullName}'/> (<fmt:formatDate value='${requisition_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${requisition_form.updatedBy.fullName}'/> (<fmt:formatDate value='${requisition_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
  $(function(){
    $('.item-button-save').click(function(){
        save();
    });
  });

  function save() {
    $.ajax({
      url:"<c:url value='/page/purchaserequisitionedit.htm'/>",
      data:$('#editForm').serialize(),
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
  
  function lockItem(id)
  {
	const confirmDialog = $('<div><spring:message code="notif.proceed"/> ?</div>').dialog(
	{
		autoOpen: false, title: '<spring:message code="notif.confirmation"/>', modal:true,
		buttons: {
			'<spring:message code="sirius.yes"/>': function() {
				$(this).dialog('close');

				var url = "<c:url value='/page/purchaserequisitionlockitem.htm?id='/>"+id;
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