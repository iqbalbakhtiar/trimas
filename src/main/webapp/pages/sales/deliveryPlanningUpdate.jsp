<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryplanningview.htm'/>"><span>List</span></a>
	<c:if test="${access.edit and planning_edit.planable and !planning_edit.salesOrder.locked and planning_edit.salesOrder.soStatus ne 'CLOSE' and planning_edit.salesOrder.soStatus ne 'CANCELED'}">
		<a class="item-button-add-gl-child" href="<c:url value='/page/deliveryplanningsequencepreadd.htm?id=${planning_edit.id}'/>"><span>Add Sequence</span></a>
	</c:if>
</div>

<div class="main-box">

	<table width="100%" style="border:none">
	<tr>
		<td width="51%">
			<table width="100%" style="border:none">
			<tr>
				<td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
				<td width="80%"><input value="${planning_edit.code}" class='input-disabled' size='30'/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
				<td>
					<select class="combobox-ext" disabled='true'>
						<option><c:out value='${planning_edit.salesOrder.organization.fullName}'/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="deliveryplanning.date"/> :</td>
				<td><input size="15" class="input-disabled" disabled value="<fmt:formatDate value='${planning_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
			</tr>
			<tr>
				<td width="20%" nowrap="nowrap" align="right"><spring:message code="customer"/> :</td>
				<td width="80%">
					<a href="<c:url value='/page/customerpreedit.htm?id=${planning_edit.salesOrder.customer.id}'/>"><input value="${planning_edit.salesOrder.customer.fullName}" class='input-disabled' size='45'/></a>
				</td>
			</tr>
			<tr>
				<td width="20%" nowrap="nowrap" align="right"><spring:message code="deliveryplanning.salesorder"/> :</td>
				<td width="80%"><a href="<c:url value='/page/${planning_edit.salesOrder.uri}?id=${planning_edit.salesOrder.id}'/>"><c:out value='${planning_edit.salesOrder.code}'/></a></td>
			</tr>
			</table>
		</td>
		<td width="49%" valign="top">
			<fieldset>
				<legend><spring:message code="deliveryplanning.productinfo"/></legend>
				<table width="100%" style="border:none">
				<tr>
					<th width="15%" nowrap="nowrap"><spring:message code="deliverysequence.product"/></th>
					<th width="8%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
					<th width="8%" nowrap="nowrap"><spring:message code="deliveryplanning.unassigned"/></th>
					<th width="8%" nowrap="nowrap"><spring:message code="deliveryplanning.delivered"/></th>
					<th width="8%" nowrap="nowrap"><spring:message code="deliveryplanning.undelivered"/></th>
				</tr>
				<c:forEach items='${planning_edit.salesOrder.items}' var='item'>
					<c:set var="qtyToBase" value="${item.product.qtyToBase}"/>
					<tr>
						<td><c:out value='${item.product.name}'/></td>
						<td><input value="<fmt:formatNumber value='${item.quantity / qtyToBase}' pattern=',##0.00'/>" class="input-disabled" disabled size='5'/></td>
						<td><input value="<fmt:formatNumber value='${(item.quantity - item.assigned) / qtyToBase}' pattern=',##0.00'/>" class="input-disabled" disabled size='5'/></td>
						<td><input value="<fmt:formatNumber value='${item.delivered / qtyToBase}' pattern=',##0.00'/>" class="input-disabled" disabled size='5'/></td>
						<td><input value="<fmt:formatNumber value='${(item.quantity - item.delivered) / qtyToBase}' pattern=',##0.00'/>" class="input-disabled" disabled size='5'/></td>
					</tr>
				</c:forEach>
				</table>
			</fieldset>
		</td>
	</tr>
	</table>
	<div class="clears">&nbsp;</div>
	<c:if test='${not empty planning_edit.sequences}'>
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
		<c:forEach items='${planning_edit.sequences}' var='seq' varStatus="apprIdx">
		<div id="${seq.id}" dojoType="ContentPane" label="${planning_edit.code}.${seq.no}" class="tab-pages" refreshOnShow="true" >
			<div class="toolbar">
				<c:if test='${access.delete and empty seq.deliveryOrders}'>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/deliveryplanningsequencedelete.htm?id=${seq.id}&plan=${planning_edit.id}'/>');" title="Delete"><span>Delete</span></a>
				</c:if>
				<div class="item-navigator">&nbsp;</div>
			</div>
			<table style="border:none" width="100%">
			<tr>
				<td width="63%" valign="top">
					<table width="100%" style="border:none">
					<tr>
						<td width="26%" nowrap="nowrap" align="right"><spring:message code="deliverysequence.sequence"/> :</td>
						<td width="74%"><input class='input-disabled' size='5' value="${seq.no}" title="#${seq.id}"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap" align="right"><spring:message code="deliverysequence.date"/> :</td>
						<td><input class="input-disabled" disabled size="10" value="<fmt:formatDate value='${seq.date}' pattern='dd-MM-yyyy'/>"/></td>
					</tr>
					<tr>
						<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
						<td>
							<select class="combobox-ext" disabled='true'>
								<option><c:out value='${seq.deliveryPlanning.salesOrder.organization.fullName}'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" align="right"><spring:message code="sirius.tax"/> :</td>
						<td>
							<select class="combobox-ext" disabled='true'>
								<option><c:out value='${seq.tax.taxName}'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><spring:message code="facility"/> : </td>
						<td>
							<select class="combobox-ext" disabled='true'>
								<option><c:out value='${seq.facility.name}'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><spring:message code="deliverysequence.shippingaddress"/> : </td>
						<td>
							<select class="combobox-ext">
								<option><c:out value='${seq.postalAddress.address}'/></option>
							</select>
						</td>
					</tr>
					</table>
				</td>
				<td width="37%" valign="top">
					<fieldset>
						<legend><spring:message code="sirius.reference"/></legend>
						<table width="100%" style="border:none">
							<tr>
								<td width="47%" align="right"><spring:message code="deliveryorder"/> :</td>
								<td width="53%">
                                <c:forEach items="${seq.deliveryOrders}" var="delivery" varStatus="status">
                                    <a href="<c:url value='/page/deliveryorderpreedit.htm?id=${delivery.id}'/>">
                                    	<c:out value="${delivery.code}"></c:out>
                                    	<c:if test="${delivery.status eq 'CANCELED'}">(<x style="color: red;"><spring:message code="deliveryorder.status.${delivery.status.messageName}"/></x>)</c:if>
                                    </a>
                                    <br>
                                </c:forEach>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			</table>
			<br/>
			<table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0" class="table-list">
				<thead>
				<tr>
					<th width="1%"><div style="width: 10px;">&nbsp;</div></th>
					<th width="8%" align="left"><spring:message code="product.code"/></th>
					<th width="20%" align="left"><spring:message code="product.name"/></th>
					<th width="10%" align="left"><spring:message code="productcategory"/></th>
					<th width="5%" align="center"><spring:message code="product.uom"/></th>
					<th width="40%"><spring:message code="deliverysequence.quantity"/></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${seq.sequenceItems}" var="seqItem" varStatus='status'>
				<tr>
					<td>&nbsp;</td>
					<td nowrap="nowrap"><c:out value='${seqItem.product.code}'/></td>
					<td nowrap="nowrap"><c:out value='${seqItem.product.name}'/></td>
					<td nowrap="nowrap"><c:out value='${seqItem.product.productCategory.name}'/></td>
					<td nowrap="nowrap"><c:out value='${seqItem.product.unitOfMeasure.measureId}'/></td>
					<td><fmt:formatNumber value='${seqItem.quantity}' pattern=',##0.00'/></td>
				</tr>
				</c:forEach>
				</tbody>
				<tfoot><tr class="end-table"><td colspan="6">&nbsp;</td></tr></tfoot>
			</table>
		</div>
		</c:forEach>
	</div>
	</c:if>
</div>
<div class="info">Created by : <c:out value='${planning_edit.createdBy.fullName}'/> (<fmt:formatDate value='${planning_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | Last update by : <c:out value='${planning_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${planning_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
						
<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">

	function save()
	{
		document.editForm.action = "<c:url value='/page/deliveryplanningupdate.htm'/>";
		document.editForm.submit();
	}

</script>