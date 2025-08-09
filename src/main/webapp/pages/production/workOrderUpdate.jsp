<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/workorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
  	<c:if test="${workOrder_edit.closeable and (approvalDecision == null or approvalDecision.approvalDecisionStatus == 'APPROVE_AND_FINISH')}">
    	<a class="item-button-doc finish"><span><spring:message code="workorder.finish"/></span></a>
  	</c:if>
</div>
<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="workOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.code"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="${workOrder_edit.code}" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.organization.id}' label='${workOrder_edit.organization.fullName}'/>
						</form:select>
						<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="<spring:message code='organization'/>" />
					</td>
				</tr>
				<tr style="display: none;">
					<td align="right"><spring:message code="facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox-ext">
						<c:if test='${not empty workOrder_edit.facility}'>
							<form:option value='${workOrder_edit.facility.id}' label='${workOrder_edit.facility.fullName}'/>
						</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.start.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="startDate" name="startDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.startDate}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workStart" name="workStart" size="4" class="input-disabled" disabled value="${workOrder_edit.workStart}"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.finish.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="finishDate" name="finishDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.finishDate}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workEnd" name="workEnd" size="4" class="input-disabled" disabled value="${workOrder_edit.workEnd}"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="machine"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="machine" path="machine" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.machine.id}' label='${workOrder_edit.machine.name}'/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.operator"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="operator" path="operator" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.operator.id}' label='${workOrder_edit.operator.fullName}'/>
						</form:select>
					</td>
				</tr>
				<c:if test="${not empty workOrder_edit.approver}">
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.approver.id}' label='${workOrder_edit.approver.fullName}'/>
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
							<legend><strong><spring:message code="sirius.reference"/></strong></legend>
							<table width="100%" style="border:none">
							<tr>
								<td colspan="2" align="right">
									<h1>
									<c:if test="${workOrder_edit.productionStatus eq 'CLOSED' or workOrder_edit.productionStatus eq 'CANCELED'}"><div style="color: red;"><spring:message code="workorder.status.${workOrder_edit.productionStatus.messageName}"/></div></c:if>
									<c:if test="${workOrder_edit.productionStatus eq 'IN_PROGRESS'}"><div style="color: blue;"><spring:message code="workorder.status.${workOrder_edit.productionStatus.messageName}"/></div></c:if>
									<c:if test="${workOrder_edit.productionStatus eq 'OPEN' or workOrder_edit.productionStatus eq 'FINISH'}"><div style="color: green;"><spring:message code="workorder.status.${workOrder_edit.productionStatus.messageName}"/></div></c:if>
									</h1>
								</td>
							</tr>
							<c:if test="${not empty workOrder_edit.issueds}">
							<tr>
                                <th colspan="2" align="right" class="highlight"><spring:message code="goodsissue"/></th>
                            </tr>
                            <c:forEach items="${workOrder_edit.issueds}" var="issue">
                            <tr>
								<td colspan="2" align="right"><a href="<c:url value='/page/goodsissuepreedit.htm?id=${issue.id}'/>">${issue.code}</a></td>
							</tr>
                          	</c:forEach>
                          	</c:if>
                            <tr><td colspan="2">&nbsp;</td></tr>
							<c:if test="${not empty workOrder_edit.receipts}">
                            <tr>
                                <th colspan="2" align="right" class="highlight"><spring:message code="goodsreceipt"/></th>
                            </tr>
                            <c:forEach items="${workOrder_edit.receipts}" var="receipt">
                            <tr>
								<td colspan="2" align="right"><a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${receipt.id}'/>">${receipt.code}</a></td>
							</tr>
                          	</c:forEach>
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
		<div id="convert" dojoType="ContentPane" label="<spring:message code='workorder.convert'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemConvert" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemConvert">
				<c:forEach items="${workOrder_edit.convertItems}" var="item" varStatus="stat">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="result" dojoType="ContentPane" label="<spring:message code='workorder.result'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemResult" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemResult">
				<c:forEach items="${workOrder_edit.resultItems}" var="item" varStatus="stat">
				<c:if test="${item.conversionType eq 'RESULT'}">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
                </c:if>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="waste" dojoType="ContentPane" label="<spring:message code='workorder.waste'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemWaste" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemWaste">
				<c:forEach items="${workOrder_edit.resultItems}" var="item" varStatus="stat">
				<c:if test="${item.conversionType eq 'WASTE'}">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
                </c:if>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="reject" dojoType="ContentPane" label="<spring:message code='workorder.reject'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemReject" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemWaste">
				<c:forEach items="${workOrder_edit.resultItems}" var="item" varStatus="stat">
				<c:if test="${item.conversionType eq 'REJECT'}">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
                </c:if>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
 	</div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${workOrder_edit.createdBy.fullName}'/> (<fmt:formatDate value='${workOrder_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${workOrder_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${workOrder_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function()
{
	$('.item-button-save').click(function(){
		save();
	});
	
	$('.finish').click(function(){
		const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="workorder.finish"/>) ?</div>').dialog(
		{
			autoOpen: false, title: '<spring:message code="workorder.finish"/>', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');
					finishWorkOrder();
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
	    url:"<c:url value='/page/workorderedit.htm'/>",
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
					window.location="<c:url value='/page/workorderpreedit.htm?id='/>"+json.id;
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

function finishWorkOrder() {
	$.ajax({
		url:"<c:url value='/page/workorderfinish.htm?id=${workOrder_edit.id}'/>",
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
					window.location="<c:url value='/page/workorderpreedit.htm?id='/>"+json.id;
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