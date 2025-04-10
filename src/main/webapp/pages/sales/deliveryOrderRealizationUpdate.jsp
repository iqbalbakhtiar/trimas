<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderrealizationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="realization_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.id"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="${realization_edit.code}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" name="organization" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty realization_edit.organization}'>
								<form:option value='${realization_edit.organization.id}' label='${realization_edit.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.dor.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" size="10" name="date" class="input-disabled" disabled
							   value="<fmt:formatDate value='${realization_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.do.no"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="deliveryOrderNo" name="deliveryOrderNo" class="inputbox input-disabled" value="${realization_edit.deliveryOrder.code}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="customerName" name="customerName" class="inputbox input-disabled" value="${realization_edit.customer.fullName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressName" name="addressName" class="inputbox input-disabled" value="${realization_edit.shippingAddress.addressName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.shippingaddress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressDetail" name="addressDetail" size="45" class="input-disabled" value="${realization_edit.shippingAddress.address}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="postalCode" name="postalCode" class="inputbox input-disabled" value="${realization_edit.shippingAddress.postalCode}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="city" name="city" class="inputbox input-disabled" value="${realization_edit.shippingAddress.city.name}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty realization_edit.facility}'>
								<form:option value='${realization_edit.facility.id}' label='${realization_edit.facility.name}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.driver.courier"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="driver" name="driver" class="inputbox input-disabled" disabled value="${realization_edit.driver}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.note"/></td>
					<td width="1%" align="center">:</td>
					<td><form:textarea path="deliveryOrderRealization.note" rows="6" cols="45"/></td>
					<td>&nbsp;</td>
				</tr>
				</table>
			</td>

			<td width="40%" valign="top">
				<table width="100%" style="border: none">
				<tr>
					<td>
						<fieldset>
							<legend><strong><spring:message code="deliveryrealization.do.real"/></strong></legend>
							<table width="100%" style="border: none">
								<tr>
									<td width="50%" align="right"><spring:message code="deliveryrealization.accept.date"/>:</td>
									<td width="50%">&nbsp;<input id="acceptanceDate" name="acceptanceDate" size="10" class="input-disabled" disabled
											   value="<fmt:formatDate value='${realization_edit.acceptanceDate}' pattern='dd-MM-yyyy'/>"/>
									</td>
								</tr>
								<tr>
									<td width="50%" align="right"><spring:message code="deliveryrealization.return.date"/>:</td>
									<td width="50%">&nbsp;<input id="returnDate" name="returnDate" size="10" class="input-disabled" disabled
																 value="<fmt:formatDate value='${realization_edit.returnDate}' pattern='dd-MM-yyyy'/>"/>
									</td>
								</tr>
								<tr>
									<td width="50%" align="right"><spring:message code="sirius.note"/>:</td>
									<td width="50%">&nbsp;<textarea name="deliveryOrderRealization.noteExt" id="noteExt" rows="4" cols="25">${realization_edit.noteExt}</textarea></td>
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
							<c:if test="${not empty billing}">
								<tr>
									<th align="right" class="highlight">Billing</th>
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
				</table>
			</td>

		</tr>
	</table>
	<br/>
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" ><spring:message code="sirius.qty"/></th>
							<th width="5%" ><spring:message code="deliveryorder.accepted.qty"/></th>
							<th width="5%" ><spring:message code="deliveryorder.returned.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${realization_edit.items}" var="item" varStatus="idx">
						<c:if test="${item.deliveryOrderItem.deliveryItemType eq 'BASE'}">
						<tr>
							<td></td>
							<td style="text-align: right;"><input size="30" value="${item.product.name}" class="input-disabled" disabled/></td>
							<td><input size="8" value="<fmt:formatNumber value='${item.deliveryOrderItem.quantity}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<c:if test="${!item.product.serial}">
								<td><input size="10" value="<fmt:formatNumber value='${item.accepted}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
								<td><input size="10" value="<fmt:formatNumber value='${item.returned}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
								<td><input size="5" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
								<td><input type="text" size="35" value="${item.note}"/></td>
							</c:if>
							<c:if test="${item.product.serial}">
								<td colspan="2">&nbsp;</td>
								<td><input size="5" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
							</c:if>
						</tr>
						</c:if>
						<c:if test="${item.deliveryOrderItem.deliveryItemType eq 'SERIAL'}">
						<tr>
							<td></td>
							<td style="text-align: right;"><input size="15" value="${item.lot.serial}" class="input-disabled" disabled/></td>
							<td><input size="8" value="<fmt:formatNumber value='${item.deliveryOrderItem.quantity}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="10" value="<fmt:formatNumber value='${item.accepted}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="10" value="<fmt:formatNumber value='${item.returned}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="5" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
							<td><input type="text" size="35" value="${item.note}"/></td>
						</tr>
						<c:set var="index" value="${index+1}"/>
						</c:if>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${realization_edit.createdBy.fullName}'/> (<fmt:formatDate value='${realization_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${realization_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${realization_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		save();
	});
});

function save() {
	$.ajax({
		url:"<c:url value='/page/deliveryorderrealizationedit.htm'/>",
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
					window.location="<c:url value='/page/deliveryorderrealizationpreedit.htm?id='/>"+json.id;
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