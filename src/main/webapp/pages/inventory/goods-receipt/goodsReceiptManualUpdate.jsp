<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/goodsreceiptmanualview.htm'/>"><span><spring:message code="sirius.list" /></span></a>
	<c:if test="${access.edit}">
		<a class="item-button-save"><span><spring:message code="sirius.save" /></span></a>
	</c:if>
	<c:if test="${empty receiptManual_edit.receipts and not fn:contains(activerole, 'Stock')}">
		<a class="item-button-doc"><span><spring:message code="sirius.finish" /></span></a>
	</c:if>
</div>

<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="receiptManual_edit">
		<table width="100%" style="border: none">
			<tr>
				<td width="53%">
					<table width="100%" style="border: none">
						<tr>
							<td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.code" /> :</td>
							<td width="74%"><input value="${receiptManual_edit.code}" class='input-disabled' disabled="true" size='25' /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.organization" /> :</td>
							<td><form:select id="org" path="organization" cssClass="combobox-ext">
									<c:if test='${not empty receiptManual_edit.organization}'>
										<form:option value='${receiptManual_edit.organization.id}' label='${receiptManual_edit.organization.fullName}' />
									</c:if>
								</form:select></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.date" /> :</td>
							<td><input id="date" name="date" class="input-disabled" size="8" disabled="true"
								value="<fmt:formatDate value='${receiptManual_edit.date}' pattern='dd-MM-yyyy'/>" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="facility" /> :</td>
							<td><select id='facility' name="facility" class="combobox-ext">
									<option value="${receiptManual_edit.facility.id}">${receiptManual_edit.facility.name}</option>
							</select></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.from" /> :</td>
							<td><form:select id="supplier" path="supplier" cssClass="combobox-ext">
									<c:if test='${not empty receiptManual_edit.supplier}'>
										<form:option value='${receiptManual_edit.supplier.id}' label='${receiptManual_edit.supplier.fullName}' />
									</c:if>
								</form:select></td>
						</tr>
						<%-- <tr>
                    <td nowrap="nowrap" align="right">Reference Type :</td>
                    <td><input value="${receiptManual_edit.transactionSource.getNormalizedName()}" class="input-disabled" size="25" disabled="true"/></td>
                </tr>
                <tr>
	                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.supplier.invoice"/> :</td>
	                <td width="74%"><input size='25' id="invoiceNo" name="invoiceNo" value="${receiptManual_edit.invoiceNo}"/></td>
	            </tr>--%>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="sirius.note" /> :</td>
							<td><form:textarea path="note" rows="6" cols="45" /></td>
						</tr>
					</table>
				</td>
				<td width="47%" valign="top" align="left">
					<fieldset>
						<legend>
							<strong><spring:message code="sirius.transaction.recap" /></strong>
						</legend>
						<table id="recapTable" width="100%">
							<thead>
								<tr>
									<td style="text-align:left;"><strong><spring:message code="product"/></strong></td>
				            		<td style="text-align:right; padding:4px 14px;"><strong><spring:message code="sirius.qty"/></strong></td>
				            		<td style="text-align:right; padding:4px 14px;"><strong><spring:message code="sirius.meter"/></strong></strong></td>
								</tr>
							</thead>
							<tbody id="recapBody">
							</tbody>
						</table>
						<table width="100%">
							<tr>
								<th width="75%">&nbsp;</th>
								<th style="text-align:right; padding:4px 14px;" width="25%"><spring:message code="sirius.qty"/>&nbsp;</th>
							</tr>
							<tr>
								<td align="right"><spring:message code="sirius.total"/> :</td>
								<td><input id="totalQty" value="<fmt:formatNumber value='${receiptManual_edit.totalQuantity}' pattern=',##0.00'/>"
									class="number-disabled" disabled size="15" /></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>
							<strong><spring:message code="sirius.reference" /></strong>
						</legend>
						<table width="100%">
							<tr>
								<td align="right" width="45%"><spring:message code="goodsreceipt" /></td>
								<td>:&nbsp; <c:forEach items="${receiptManual_edit.receipts}" var="receipt" varStatus="status">
										<c:if test="${status.index > 0}">, </c:if>
										<a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${receipt.id}'/>"><c:out value="${receipt.code}" /></a>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td width="45%" align="right">Barcode No</td>
								<td>:&nbsp; <a href="<c:url value='/page/barcodegrouppreedit.htm?id=${receiptManual_edit.barcodeGroup.id}'/>"><c:out
											value='${receiptManual_edit.barcodeGroup.code}' /></a><br />
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		<br />
		<table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0" class="table-list">
			<thead>
				<tr>
					<th width="1%">&nbsp;</th>
					<th width="8%"><spring:message code="grid" /></th>
					<th width="8%"><spring:message code="container" /></th>
					<th width="8%"><spring:message code="sirius.code" /></th>
					<th width="8%"><spring:message code="sirius.name" /></th>
					<th width="8%"><spring:message code="product.category" /></th>
					<th width="8%"><spring:message code="barcode" /></th>
					<th width="5%"><spring:message code="goodsreceiptitem.container.no" /></th>
					<th width="6%"><spring:message code="product.uom" /></th>
					<th width="6%"><spring:message code="sirius.qty" /></th>
					<th width="6%"><spring:message code="sirius.price" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${receiptManual_edit.items}" var="item" varStatus="status">
					<tr>
						<td></td>
						<td>${item.container.grid.name}</td>
						<td>${item.container.name}</td>
						<td>${item.product.code}</td>
						<td>${item.product.name}</td>
						<td>${item.product.productCategory.name}</td>
						<td>${item.lot.serial}</td>
						<td>${item.containerNo}</td>
						<td>${item.product.unitOfMeasure.measureId}</td>
						<td><fmt:formatNumber value='${item.quantity}' pattern=',##0.00' /></td>
						<td><fmt:formatNumber value='${item.amount}' pattern=',##0.00' /></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="end-table">
					<td colspan="${fn:contains(activerole, 'Stock') ? 13 : 15}">&nbsp;</td>
				</tr>
			</tfoot>
		</table>
	</sesform:form>
</div>
<div class="info">
	Created by :
	<c:out value='${receiptManual_edit.createdBy.fullName}' />
	(
	<fmt:formatDate value='${receiptManual_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss' />
	) | Last update by :
	<c:out value='${receiptManual_edit.updatedBy.fullName}' />
	(
	<fmt:formatDate value='${receiptManual_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss' />
	)
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function() {
		
		recap();
		
		$('.item-button-save').click(function(){
            $.ajax({
    		    url:"<c:url value='/page/goodsreceiptmanualedit.htm'/>",
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
    		          		window.location="<c:url value='/page/goodsreceiptmanualpreedit.htm?id='/>"+json.id;
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
	
	$('.item-button-doc').click(function(e) {
	    let isValid = true;
	
	    $('.amounts').each(function() {
	      const rawVal = $(this).val().replace(/,/g, '');
	      const amount = parseFloat(rawVal);
	
	      if (isNaN(amount) || amount <= 0) {
	    	alert('<spring:message code="retailitem.price"/> <spring:message code="notif.greater.zero"/> !');
	        isValid = false;
	        return false; 
	      }
	    });
	
	    if (isValid) {
	    	$.ajax({
	            url: '<c:url value="/page/goodsreceiptmanualedit.htm?finish=true"/>',
	            data: $('#editForm').serialize(),
	            type: 'POST',
	            dataType: 'json',
	            beforeSend: function () {
	                $dialog.empty();
	                $dialog.html('<spring:message code="notif.updating"/>');
	                $dialog.dialog('open');
	            },
	            success: function (json) {
	                if (json) {
	                    if (json.status === 'OK') {
	                        $dialog.dialog('close');
	                        window.location = '<c:url value="/page/goodsreceiptmanualpreedit.htm?id="/>' + json.id;
	                    } else {
	                        $dialog.empty();
	                        $dialog.html('<spring:message code="notif.profailed"/> :<br/>' + json.message);
	                    }
	                }
	            }
	        });
    	}
	
	    return false;
	});

	function recap() {
	    let totalQty = 0;
	    const recap = {};

	    $('#lineItemTable tbody tr').each(function(){
	        const product = $(this).find('td:nth-child(5)').text().trim();
	        const uom = $(this).find('td:nth-child(9)').text().trim().toLowerCase();
	        const qty = parseFloat($(this).find('td:nth-child(10)').text().replace(/,/g,'')) || 0;

	        if (uom !== 'm') return; 

	        if (!recap[product]) {
	            recap[product] = { roll: 0, meter: 0 };
	        }

	        recap[product].roll += 1;   
	        recap[product].meter += qty;  
	        totalQty += qty;
	    });

	    const $recapBody = $('#recapBody').empty();
	    $.each(recap, function(product, data){
	        $recapBody.append(
	            '<tr>' +
	                '<td><input class="input" size="45" type="text" style="text-align:left;" value="'+product+'" readonly></td>' +
	                '<td><input size="8" class="input-disabled" type="text" style="text-align:right;" value="'+data.roll+'" readonly></td>' +
	                '<td><input size="8" class="input-disabled" type="text" style="text-align:right;" value="'+data.meter.toFixed(2)+'" readonly></td>' +
	            '</tr>'
	        );
	    });

	}



</script>