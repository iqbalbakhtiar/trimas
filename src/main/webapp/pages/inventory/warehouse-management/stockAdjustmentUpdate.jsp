<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/stockadjustmentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="adjustment_edit">
		<table width="100%" style="border:none">
		<tr>
			<td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.code"/> :</td>
			<td width="52%"><input value="${adjustment_edit.code}" disabled class='input-disabled' size='30'/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext">
					<c:if test='${not empty adjustment_edit.organization}'>
						<form:option value='${adjustment_edit.organization.id}' label='${adjustment_edit.organization.fullName}' />
					</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
			<td><input class="input-disabled" size="10" disabled value="<fmt:formatDate value='${adjustment_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
			<td>
				<form:select id="facility" path="facility" cssClass="combobox-ext">
					<c:if test='${not empty adjustment_edit.facility}'>
						<form:option value='${adjustment_edit.facility.id}' label='${adjustment_edit.facility.name}' />
					</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="sirius.reason"/> :</td>
			<td><form:textarea path="reason" cols="55" rows="7"/></td>
		</tr>
	</table>
	<div class="toolbar-clean">
		<div class="item-navigator">&nbsp;</div>
	</div>
	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
	<thead>
	<tr>
		<th width="1%">&nbsp;</th>
		<th width="10%" nowrap="nowrap"><spring:message code="container"/></th>
		<th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
		<th width="15%" nowrap="nowrap"><spring:message code="product.name"/></th>
		<th width="8%" nowrap="nowrap"><spring:message code="product.category"/></th>
		<th width="5%" nowrap="nowrap"><spring:message code="product.uom"/></th>
		<th width="5%" nowrap="nowrap"><spring:message code="barcode"/></th>
		<%-- <th width="5%" nowrap="nowrap"><spring:message code="product.lot"/></th> --%>
		<th width="10%" nowrap="nowrap" style="text-align: right;"><spring:message code="product.quantity"/></th>
		<th width="10%" nowrap="nowrap" style="text-align: right;"><spring:message code="sirius.price"/></th>
		<th width="30%">&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items='${adjustment_edit.items}' var='item'>
	<tr>
		<td>&nbsp;</td>
		<td nowrap="nowrap"><c:out value='${item.container.name}'/></td>
		<td nowrap="nowrap"><c:out value='${item.product.code}'/></td>
		<td nowrap="nowrap"><c:out value='${item.product.name}'/></td>
		<td nowrap="nowrap"><c:out value='${item.product.productCategory.name}'/></td>
		<td nowrap="nowrap"><c:out value='${item.product.unitOfMeasure.measureId}'/></td>
		<td nowrap="nowrap"><c:out value='${item.lot.serial}'/></td>
		<%-- <td nowrap="nowrap"><c:out value='${item.lot.code}'/></td> --%>
		<td nowrap="nowrap" style="text-align: right;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
		<td nowrap="nowrap" style="text-align: right;"><fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/></td>
		<td>&nbsp;</td>
	</tr>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
	</tfoot>
	</table>
	</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${adjustment_edit.createdBy.fullName}'/> (<fmt:formatDate value='${adjustment_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${adjustment_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${adjustment_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/stockadjustmentedit.htm'/>",
				data:$('#editForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend:function()
				{
					$dialog.empty();
					$dialog.html('Updating Stock Adjustment data......');
					$dialog.dialog('open');
				},
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/stockadjustmentpreedit.htm?id='/>"+json.id;
						}
						else
						{
							$dialog.empty();
							$dialog.html('Proccess fail,reason :<br/>'+json.message);
						}
					}
				}
			});		
		});
	});
</script>