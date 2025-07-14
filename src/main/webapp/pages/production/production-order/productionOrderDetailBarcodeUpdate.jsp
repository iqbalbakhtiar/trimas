<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/productionorderdetailbarcodeview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<c:if test="${access.edit}">
		<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
	</c:if>
	<c:if test="${access.print}">
		<a class="item-button-print" href="<c:url value='/page/productionorderdetailbarcodeprint.htm?id=${barcode_edit.id}&type=GROUP'/>"><span><spring:message code="sirius.print"/></span></a>
	</c:if>
</div>
<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="barcode_edit" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="${barcode_edit.code}" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" size="8" class="input-disabled" value="<fmt:formatDate value='${barcode_edit.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorderdetail"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="productionOrderDetail" name="productionOrderDetail" class="combobox-ext">
									<option value="${barcode_edit.productionOrderDetail.id}" label="${barcode_edit.productionOrderDetail.code}"/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.output"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="product" name="product" class="combobox-ext">
									<option value="${barcode_edit.product.id}" label="${barcode_edit.product.code}"/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.conemark"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="coneMark" path="coneMark" cssClass="input-disabled" disabled="true"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.qty"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="quantity" class="number-disabled" value="<fmt:formatNumber value='${barcode_edit.quantity}' pattern='#,##0'/>" disabled="true"/></td>
						</tr>
						<tr>
							<td align="right">PIC</td>
							<td width="1%" align="center">:</td>
							<td><form:input id="pic" path="pic" cssClass="input-disabled" disabled="true"/></td>
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
					<table width="100%">
                         <tr>
                        	<td>
                            	<fieldset>
									<legend><strong><spring:message code="sirius.reference"/></strong></legend>
									<table width="100%" style="border:none">
										<tr>
										    <th align="right" class="highlight"><spring:message code="productionorderdetail"/></th>
										</tr>
										<tr>
										    <td align="right"><a href="<c:url value='/page/productionorderdetailpreedit.htm?id=${barcode_edit.productionOrderDetail.id}'/>"><c:out value="${barcode_edit.productionOrderDetail.code}"/></a></td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
										    <th align="right" class="highlight"><spring:message code="barcode"/></th>
										</tr>
										<tr>
										    <td align="right"><a href="<c:url value='/page/barcodegrouppreedit.htm?id=${barcode_edit.barcodeGroup.id}'/>"><c:out value="${barcode_edit.barcodeGroup.code}"/></a></td>
										</tr>
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
								<th width="1%"></th>
								<th width="10%" nowrap="nowrap"><spring:message code="barcode"/></th>
								<th width="5%" nowrap="nowrap">No</th>
								<th width="8%" nowrap="nowrap"><spring:message code="barcoding.lotno"/></th>
								<th width="12%" nowrap="nowrap"><spring:message code="productionorder.threadtype"/></th>
								<th width="12%" nowrap="nowrap"><spring:message code="barcoding.conemark"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="barcoding.coneqty"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="barcoding.bruto"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="barcoding.netto"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							</tr>
						</thead>
						<tbody id="lineItem">
							<c:forEach items="${barcode_edit.barcodeGroup.barcodes}" var="barcode" varStatus="status">
								<tr>
									<td class="tools">
										<c:if test='${access.print}'>
											<a class="item-button-print" href="<c:url value='/page/productionorderdetailbarcodeprint.htm?id=${barcode.id}&type=SINGLE'/>"><span><spring:message code="sirius.print"/></span></a>
										</c:if>
									</td>
									<td><input value="${barcode.code}" class="input-disabled" disabled/></td>
									<td><input value="${fn:substring(barcode.code, 6, 9)}" class="input-disabled" size="4" disabled/></td>
									<td><input id="lotCode[${status.index}]" class="input-disabled inputbox" value="${barcode.lotCode}" disabled/></td>
									<td>
										<select id="product[${status.index}]" class="input-disabled combobox" disabled>
											<option value="${barcode.product.id}" label="${barcode.product.name}">
										</select>
									</td>
									<td><input id="coneMark[${status.index}]" class="input-disabled inputbox-min" value="${barcode.coneMark}" disabled/></td>
									<td><input id="quantityCone[${status.index}]" class="number-disabled" value="<fmt:formatNumber value='${barcode.quantityCone}' pattern='#,##0'/>" size="12" disabled/></td>
									<td><input id="quantity[${status.index}]" class="number-disabled" value="<fmt:formatNumber value='${barcode.quantityCone}' pattern='#,##0.00'/>" size="12" disabled/></td>
									<td><input id="quantityReal[${status.index}]" class="number-disabled" value="<fmt:formatNumber value='${barcode.quantityReal}' pattern='#,##0.00'/>" size="12" disabled/></td>
									<td><input class="input-disabled" size="8" value="${barcode.product.unitOfMeasure.measureId}" disabled/></td>
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function() {
			if (validation())
				save();
        });
	});
	
	function validation()
	{
		return true;
	}
	
	function save() {
		$.ajax({
		    url:"<c:url value='/page/productionorderdetailbarcodeedit.htm'/>",
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
		          		window.location="<c:url value='/page/productionorderdetailbarcodepreedit.htm?id='/>"+json.id;
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