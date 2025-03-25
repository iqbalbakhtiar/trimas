<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"  href="<c:url value='/page/deliveryorderprint.htm?id=${deliveryOrder_form.deliveryOrder.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="deliveryOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%" valign="top">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.id"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty deliveryOrder_form.organization}'>
								<form:option value='${deliveryOrder_form.organization.id}' label='${deliveryOrder_form.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.do.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" size="10" name="date" class="input-disabled" disabled value="<fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="customerName" name="customerName" class="inputbox input-disabled" value="${deliveryOrder_form.customer.fullName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty deliveryOrder_form.facility}'>
								<form:option value="${deliveryOrder_form.facility.id}">${deliveryOrder_form.facility.name}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.note"/></td>
					<td width="1%" align="center">:</td>
					<td><form:textarea path="note" rows="6" cols="45" value="${deliveryOrder_form.note}"/></td>
					<td>&nbsp;</td>
				</tr>
				</table>
			</td>
			<td width="30%" valign="top">
				<table width="100%" style="border: none">
					<tr>
						<td>&nbsp;</td>
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
							<c:if test='${not empty deliveryOrder_form.shippingAddress}'>
								<form:option value="${deliveryOrder_form.shippingAddress.id}">${deliveryOrder_form.shippingAddress.addressName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressDetail" class="inputbox input-disabled" disabled value="${deliveryOrder_form.shippingAddress.address}"/></td>
				</tr>
  				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressPostalCode" class="inputbox input-disabled" disabled value="${deliveryOrder_form.shippingAddress.postalCode}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressCity" class="inputbox input-disabled" disabled value="${deliveryOrder_form.shippingAddress.city.name}"/></td>
				</tr>
  			</table>
		</div>
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="1" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" ><spring:message code="sirius.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="container"/></th>
							<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${deliveryOrder_form.deliveryOrder.items}" var="item" varStatus="idx">
						<c:if test="${item.deliveryItemType eq 'BASE'}">
						<tr>
							<td></td>
							<td style="text-align: right;"><input size="30" value="${item.product.name}" class="input-disabled" disabled/></td>
							<td><input size="8" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="5" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
							<c:if test="${!item.product.serial}">
								<td><input size="25" value="${item.container.name}" class="input-disabled" disabled/></td>
								<td><input type="text" size="35" value="${item.note}"/></td>
							</c:if>
							<c:if test="${item.product.serial}">
								<td colspan="2">&nbsp;</td>
							</c:if>
						</tr>
						</c:if>
						<c:forEach items="${item.serials}" var="serial" varStatus="idxSerial">
						<tr>
							<td></td>
							<td style="text-align: right;"><input size="15" value="${serial.lot.serial}" class="input-disabled" disabled/></td>
							<td><input size="8" value="<fmt:formatNumber value='${serial.quantity}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="5" value="${serial.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
							<td><input size="25" value="${serial.container.name}" class="input-disabled" disabled/></td>
							<td><input type="text" size="35" value="${item.note}"/></td>
						</tr>
						</c:forEach>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${deliveryOrder_form.createdBy.fullName}'/> (<fmt:formatDate value='${deliveryOrder_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${deliveryOrder_form.updatedBy.fullName}'/> (<fmt:formatDate value='${deliveryOrder_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		$.ajax({
			url:"<c:url value='/page/deliveryorderedit.htm'/>",
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
						window.location="<c:url value='/page/deliveryorderpreedit.htm?id='/>"+json.id;
					}
					else
					{
						$dialog.empty();
						$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
					}
				}
			}
		});
	});
});
</script>