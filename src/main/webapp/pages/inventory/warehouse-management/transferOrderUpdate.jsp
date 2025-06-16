<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/transferorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<c:if test='${access.add}'>
		<a class="item-button-save b_save"><span><spring:message code="sirius.save"/></span></a>
    </c:if>				
</div>

<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="transfer_edit">
		<table width="100%" style="border:none">
			<tr>
                <td width="60%" valign="top">
                    <table style="border:none" width="100%">
						<tr>
							<td width="40%" nowrap="nowrap" align="right"><spring:message code="sirius.id"/> :</td>
							<td width="60%"><input value="${transfer_edit.code}" class='input-disabled' size='25' disabled/></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
							<td>
								<form:select id="org" path="organization" cssClass="combobox-ext">
									<form:option value='${transfer_edit.organization.id}' label='${transfer_edit.organization.fullName}' />
								</form:select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
							<td><input id="date" name="date" value="<fmt:formatDate value='${transfer_edit.date}' pattern='dd-MM-yyyy'/>" size="8" disabled/></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="sirius.type"/> :</td>
							<td>
								<form:select id="transferType" path="transferType" cssClass="combobox-ext">
									<form:option value="${transfer_edit.transferType}" label="${transfer_edit.transferType}"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehousefrom"/> :</td>
							<td>
								<form:select id="source" path="source" cssClass="combobox-ext">
									<form:option value="${transfer_edit.source.id}" label="${transfer_edit.source.name}"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehouseto"/> :</td>
							<td>
								<form:select id="destination" path="destination" cssClass="combobox-ext">
									<form:option value="${transfer_edit.destination.id}" label="${transfer_edit.destination.name}"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
							<td><form:textarea path="note" cols="55" rows="7"/></td>
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
                                            <th align="right" class="highlight"><spring:message code="goodsissue"/></th>
                                        </tr>
                                        <c:forEach items="${transfer_edit.issueds}" var="issue">
	                                   		<tr>
	                                            <td align="right">
	                                            	<a href="<c:url value='/page/goodsissuepreedit.htm?id=${issue.id}'/>">${issue.code}</a>
	                                            </td>
	                                        </tr>
                                      	</c:forEach>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <th align="right" class="highlight"><spring:message code="goodsreceipt"/></th>
                                        </tr>
                                        <c:forEach items="${transfer_edit.receipts}" var="receipt">
	                                   		<tr>
	                                            <td align="right">
	                                            	<a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${receipt.id}'/>">${receipt.code}</a>
	                                            </td>
	                                        </tr>
                                      	</c:forEach>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<br/>
		<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" width="100%"> 
			<thead>
				<tr>
					<th><spring:message code="product.code"/></th>
					<th><spring:message code="product.name"/></th>
					<th><spring:message code="product.barcode"/></th>
					<th><spring:message code="product.category"/></th>
					<th><spring:message code="transferorder.gridfrom"/></th>
					<th><spring:message code="transferorder.containerfrom"/></th>
					<th><spring:message code="transferorder.gridto"/></th>
					<th><spring:message code="transferorder.containerto"/></th>
					<th><spring:message code="product.uom"/></th>
					<th><spring:message code="transferorder.qty"/></th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${transfer_edit.items}" var="item" varStatus="status">
					<tr>
						<td nowrap="nowrap"><c:out value="${item.product.code}"/></td>
						<td nowrap="nowrap"><c:out value="${item.product.name}"/></td>
						<td nowrap="nowrap"><c:out value='${item.lot.serial}'/></td>
						<td nowrap="nowrap"><c:out value='${item.product.productCategory.name}'/></td>
						<td nowrap="nowrap"><c:out value="${item.sourceContainer.grid.name}"/></td>
						<td nowrap="nowrap"><c:out value="${item.sourceContainer.name}"/></td>
						<td nowrap="nowrap"><c:out value="${item.destinationContainer.grid.name}"/></td>
						<td nowrap="nowrap"><c:out value="${item.destinationContainer.name}"/></td>
						<td nowrap="nowrap"><c:out value='${item.product.unitOfMeasure.measureId}'/></td>
						<td nowrap="nowrap"><fmt:formatNumber value="${item.quantity}" pattern="#,##0.00"/></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="end-table"><td colspan="16">&nbsp;</td></tr>
			</tfoot>
		</table>
	</sesform:form>
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
    
		$('.b_save').click(function(e){
			$status = true;
			
			if($status) {
				$.ajax({
					url:"<c:url value='/page/transferorderedit.htm'/>",
					data:$('#editForm').serialize(),
					type : 'POST',
					dataType : 'json',
					beforeSend : beforeSend($dialog, '<spring:message code="notif.updating"/>'),
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$dialog.dialog('close');
								window.location="<c:url value='/page/transferorderpreedit.htm?id='/>"+json.id;
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				});	
			}
		});
	});
</script>