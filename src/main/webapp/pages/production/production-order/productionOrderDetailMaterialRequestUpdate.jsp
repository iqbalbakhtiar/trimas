<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/productionorderdetailpreedit.htm?id=${request_edit.productionOrderDetail.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="request_edit" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="${request_edit.code}" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="organization"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="org" class="combobox-ext input-disabled" disabled>
									<option value='${request_edit.productionOrderDetail.productionOrder.organization.id}' label='${request_edit.productionOrderDetail.productionOrder.organization.fullName}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorderdetail"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<input class="input-disabled" value="${request_edit.productionOrderDetail.code}" disabled/>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" class="input-disabled" size="8" value="<fmt:formatDate value='${request_edit.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="machine"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="machine" class="combobox-ext">
									<option value='${request_edit.machine.id}' label='${request_edit.machine.name}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehousefrom"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="source" class="combobox-ext">
									<option value='${request_edit.source.id}' label='${request_edit.source.name}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehouseto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="destination" class="combobox-ext">
									<option value='${request_edit.destination.id}' label='${request_edit.destination.name}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.containerto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="container" class="combobox-ext">
									<option value='${request_edit.container.id}' label='${request_edit.container.name}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.gridto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="grid" class="combobox-ext">
									<option value='${request_edit.container.grid.id}' label='${request_edit.container.grid.name}'/>
								</select>
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
					<fieldset>
						<legend><strong><spring:message code="sirius.reference"/></strong></legend>
						<table width="100%" style="border:none">
							<tr>
							    <th align="right" class="highlight"><spring:message code="goodsissue"/></th>
							</tr>
							<c:forEach items="${request_edit.issueds}" var="issue" varStatus="status">
								<tr>
								    <td align="right"><a href="<c:url value='/page/goodsissuepreedit.htm?id=${issue.id}'/>"><c:out value="${issue.code}"/></a></td>
								</tr>
							</c:forEach>
							<tr><td colspan="2">&nbsp;</td></tr>
							<tr>
							    <th align="right" class="highlight"><spring:message code="goodsreceipt"/></th>
							</tr>
							<c:forEach items="${request_edit.receipts}" var="receipt" varStatus="status">
								<tr>
								    <td align="right"><a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${receipt.id}'/>"><c:out value="${receipt.code}"/></a></td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>
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
								<th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="transferorder.containerfrom"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							</tr>
						</thead>
						<tbody id="lineItem">
							<c:forEach items="${request_edit.items}" var="item" varStatus="status">
								<tr>
									<td>&nbsp;</td>
									<td nowrap="nowrap"><c:out value='${item.product.code}'/></td>
									<td nowrap="nowrap"><c:out value='${item.product.name}'/></td>
									<td nowrap="nowrap"><c:out value='${item.sourceContainer.name}'/></td>
									<td nowrap="nowrap"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
									<td nowrap="nowrap"><c:out value='${item.product.unitOfMeasure.measureId}'/></td>
								</tr>
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	var $index = 0;
	$(function(){
		$('.item-button-save').click(function(){
			save();
		});
	});

	function save() {
		$.ajax({
		    url:"<c:url value='/page/productionorderdetailmaterialrequestedit.htm'/>",
		    data:$('#editForm').serialize(),
		    type : 'POST',
		    dataType : 'json',
		    beforeSend:function()
		    {
		      $dialog.empty();
		      $dialog.html('<spring:message code="notif.updating"/>');
		      $dialog.dialog('open');
		    },
		    success : function(json) {
		    	if(json)
		    	{
		        	if(json.status === 'OK')
		        	{
		          		$dialog.dialog('close');
		          		window.location="<c:url value='/page/productionorderdetailmaterialrequestpreedit.htm?id='/>"+json.id;
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