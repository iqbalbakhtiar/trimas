<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/salesorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"><span><spring:message code="sirius.print"/></span></a>
	<c:if test="${approvalDecision.approvalDecisionStatus eq 'APPROVE_AND_FINISH' and salesOrder_edit.soStatus ne 'CLOSE' and salesOrder_edit.soStatus ne 'CANCELED'}">
		<a class="item-button-close close"><span><spring:message code="sirius.close"/></span></a>
	</c:if>
	<c:if test="${salesOrder_edit.soStatus eq 'OPEN' or salesOrder_edit.soStatus eq 'PLANNING'}">
		<a class="item-button-close canceled"><span><spring:message code="sirius.canceled"/></span></a>
	</c:if>
</div>
<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="salesOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%" valign="top">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.code"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.organization}'>
								<form:option value='${salesOrder_edit.organization.id}' label='${salesOrder_edit.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.expired.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.expDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.customer}'>
								<form:option value="${salesOrder_edit.customer.id}">${salesOrder_edit.customer.fullName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesperson"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="salesPerson" path="salesPerson" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.salesPerson}'>
								<form:option value="${salesOrder_edit.salesPerson.id}">${salesOrder_edit.salesPerson.fullName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.invoice"/></td>
	              	<td width="1%" align="center">:</td>
					<td>
						<form:radiobutton id="invoiceTrue" path="directInvoice" value="true" disabled="true"/><spring:message code="salesorder.invoice.direct"/>
						<form:radiobutton id="invoiceFalse" path="directInvoice" value="false" disabled="true"/><spring:message code="salesorder.invoice.delivery"/>	
					</td>
				</tr>
				<tr>
					<td width="19%" align="right">PO No :</td>
					<td width="1%" align="center">:</td>
					<td>
						<form:input id='poCode' path='poCode' cssClass='inputbox' size="15"/>
					</td>
				</tr>
				<tr>
					<td width="19%" align="right"><spring:message code="creditterm.term"/> :</td>
					<td width="1%" align="center">:</td>
					<td>
						<form:input id='term' path='term' cssClass='number-disabled' size="5" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="shippingDate" name="shippingDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.shippingDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.tax.type"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="tax" path="tax" onchange="updateDisplay();" cssClass="input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.tax}'>
								<option value="${salesOrder_edit.tax.id}" data-taxrate="${salesOrder_edit.tax.taxRate}">${salesOrder_edit.tax.taxName}</option>
							</c:if>
						</form:select>
						<input size="5" id="taxRate" class="number-disabled" disabled />&nbsp;%
					</td>
				</tr>
            	<c:if test="${not empty salesOrder_edit.approvable}">
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.approver}'>
								<form:option value="${approvalDecision.forwardTo.id}">${approvalDecision.forwardTo.code} ${approvalDecision.forwardTo.fullName}</form:option>
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
								<legend><strong><spring:message code="sirius.status"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td colspan="2" align="right">
											<h1>
											<c:if test="${salesOrder_edit.soStatus eq 'CLOSE' or salesOrder_edit.soStatus eq 'CANCELED'}"><div style="color: red;"><spring:message code="salesorder.status.${salesOrder_edit.soStatus.messageName}"/></div></c:if>
											<c:if test="${salesOrder_edit.soStatus eq 'PLANNING'}"><div style="color: blue;"><spring:message code="salesorder.status.${salesOrder_edit.soStatus.messageName}"/></div></c:if>
											<c:if test="${salesOrder_edit.soStatus eq 'OPEN'}"><div style="color: green;"><spring:message code="salesorder.status.${salesOrder_edit.soStatus.messageName}"/></div></c:if>
											<c:if test="${salesOrder_edit.soStatus eq 'DELIVERED'}"><div style="color: green;"><spring:message code="salesorder.status.${salesOrder_edit.soStatus.messageName}"/></div></c:if>
											</h1>
										</td>
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
									<tr>
										<th align="right" class="highlight"><spring:message code="deliveryplanning"/></th>
									</tr>
									<tr>
										<td align="right"><a href="<c:url value='/page/deliveryplanningpreedit.htm?id=${salesOrder_edit.deliveryPlanning.id}'/>"><c:out value='${salesOrder_edit.deliveryPlanning.code}'/></a></td>
									</tr>
									<tr><td colspan="2">&nbsp;</td></tr>
									<tr>
										<th align="right" class="highlight"><spring:message code="deliveryorder"/></th>
									</tr>
									<c:forEach items="${salesOrder_edit.deliveryPlanning.sequences}" var="seq">
										<c:forEach items="${seq.deliveryOrders}" var="delivery">
										<tr>
											<td align="right"><a href="<c:url value='/page/deliveryorderpreedit.htm?id=${delivery.id}'/>"><c:out value='${delivery.code}'/></a></td>
										</tr>
										</c:forEach>
									</c:forEach>
									<tr><td colspan="2">&nbsp;</td></tr>
									<tr>
										<th align="right" class="highlight"><spring:message code="deliveryrealization"/></th>
									</tr>
									<c:forEach items="${salesOrder_edit.deliveryPlanning.sequences}" var="seq">
										<c:forEach items="${seq.deliveryOrders}" var="delivery">
										<tr>
											<td align="right"><a href="<c:url value='/page/deliveryorderrealizationpreedit.htm?id=${delivery.deliveryOrderRealization.id}'/>"><c:out value='${delivery.deliveryOrderRealization.code}'/></a></td>
										</tr>
										</c:forEach>
									</c:forEach>
									<tr><td colspan="2">&nbsp;</td></tr>
									<c:if test="${not empty billing}">
									<tr>
										<th align="right" class="highlight"><spring:message code="billing"/></th>
									</tr>
									<tr>
										<td align="right"><a href="<c:url value='/page/billingpreedit.htm?id=${billing.id}'/>"><c:out value='${billing.code}'/></a></td>
									</tr>
									<tr><td colspan="2">&nbsp;</td></tr>
									</c:if>
								</table>
							</fieldset>
						</td>
					</tr>
					<tr>
						<td>
							<fieldset>
								<legend><strong><spring:message code="salesorder.recapitulation"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.total"/> :</td>
										<td width="20%"><input id="totalSales" value="<fmt:formatNumber value='${adapter.totalAmount}' pattern='#,##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.total.discount"/> :</td>
										<td width="20%"><input id="totalDiscount" value="<fmt:formatNumber value='${adapter.totalDiscount}' pattern='#,##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.total.afterdiscount"/> :</td>
										<td width="20%"><input id="totalAfterDiscount" value="<fmt:formatNumber value='${adapter.totalAfterDiscount}' pattern='#,##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.total.tax"/> :</td>
										<td width="20%"><input id="taxAmount" value="<fmt:formatNumber value='${adapter.taxAmount}' pattern='#,##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><strong><spring:message code="salesorder.total.transaction"/> :</strong></td>
										<td width="20%"><input id="totalTransaction" value="<fmt:formatNumber value='${adapter.totalTransaction}' pattern='#,##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
		            <tr>
		            	<td>
		                <c:if test="${not empty salesOrder_edit.approvable}">
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
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="shippingAddress" path="shippingAddress" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.shippingAddress}'>
								<form:option value="${salesOrder_edit.shippingAddress.id}">${salesOrder_edit.shippingAddress.addressName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressDetail" class="input-disabled" size="45" disabled value="${salesOrder_edit.shippingAddress.address}"/></td>
				</tr>
  				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressPostalCode" class="inputbox input-disabled" disabled value="${salesOrder_edit.shippingAddress.postalCode}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressCity" class="inputbox input-disabled" disabled value="${salesOrder_edit.shippingAddress.city.name}"/></td>
				</tr>
  			</table>
		</div>
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
          		<c:set var="isApprover" value="${not empty person and person.id == approvalDecision.forwardTo.id and approvalDecision.approvalDecisionStatus != 'APPROVE_AND_FINISH' and approvalDecision.approvalDecisionStatus != 'REJECTED'}" />
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
              				<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.unitprice"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.total"/> <spring:message code="sirius.amount"/></th>
							<th width="50%" nowrap="nowrap"><spring:message code="salesorder.packing.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${salesOrder_edit.items}" var="item" varStatus="idx">
						<tr>
                			<td>&nbsp;</td>
			                <td><input id="product[${idx.index}]" size="26" value="${item.product.name}" class="input-disabled" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/></td>
			                <td>
			                	<input id="quantity[${idx.index}]"
			                	size="6"
	                          	value="${item.quantity}"
	                          	name="salesOrder.items[${idx.index}].quantity"
	                          	index="${idx.index}"
	                          	next="quantity"
	                          	onchange="updateDisplay();"
	                          	class="${isApprover ? 'input-decimal' : 'input-disabled input-decimal'}"
	                    		${isApprover ? '' : 'disabled="disabled"'} />
			                </td>
			                <td><input id="uom[${idx.index}]" size="6" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/></td>
			                <td>
			                	<input id="amount[${idx.index}]"
			                	size="12"
	                          	value="${item.money.amount}"
	                          	name="salesOrder.items[${idx.index}].money.amount"
	                          	index="${idx.index}"
	                          	next="amount"
	                          	onchange="updateDisplay();"
	                          	class="${isApprover ? 'input-decimal' : 'input-disabled input-decimal'}"
	                    		${isApprover ? '' : 'disabled="disabled"'} />
			                </td>
			                <td><input id="totalAmount[${idx.index}]" size="12" class="input-number input-disabled" disabled value="<fmt:formatNumber value='${item.totalAmount}' pattern=',##0.00'/>"/></td>
			                <td><input id="note[${idx.index}]" type="text" value="${item.note}" name="salesOrder.items[${idx.index}].note"index="${idx.index}" next="note" size="40"/></td>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${salesOrder_edit.createdBy.fullName}'/> (<fmt:formatDate value='${salesOrder_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${salesOrder_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${salesOrder_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	var $index = 0; // For Line Item Index

	updateDisplay();
	
	$('.item-button-save').click(function(){
		save();
	});
	
	$('.item-button-print').click(function(){
		printSO();
	});

	$('.close').click(function(){
		const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.close"/>) ?</div>').dialog(
		{
			autoOpen: false, title: '<spring:message code="sirius.close"/>', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');
					closeSO('CLOSE');
				},
				'<spring:message code="sirius.no"/>': function() {
					$(this).dialog('close');
				}
			}
		});

		confirmDialog.dialog('open');
	});

	$('.canceled').click(function(){
		const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.canceled"/>) ?</div>').dialog(
		{
			autoOpen: false, title: '<spring:message code="sirius.canceled"/>', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');
					closeSO('CANCELED');
				},
				'<spring:message code="sirius.no"/>': function() {
					$(this).dialog('close');
				}
			}
		});

		confirmDialog.dialog('open');
	});
});

function save() {
	$.ajax({
		url:"<c:url value='/page/salesorderedit.htm'/>",
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
					window.location="<c:url value='/page/salesorderpreedit.htm?id='/>"+json.id;
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

function printSO() {
	var taxRate = parseFloat($('#taxRate').val().toNumber());
	if(taxRate > 0)
		window.location="<c:url value='/page/salesorderprint.htm?id=${salesOrder_edit.id}&printType=1'/>";
	else
		window.location="<c:url value='/page/salesorderprint.htm?id=${salesOrder_edit.id}&printType=2'/>";
}

function closeSO(soStatus) {
	$.ajax({
		url:"<c:url value='/page/salesorderclose.htm?id=${salesOrder_edit.id}&soStatus='/>"+soStatus,
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
					window.location="<c:url value='/page/salesorderpreedit.htm?id='/>"+json.id;
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
</script>