<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/goodsreceiptmanualview.htm'/>"><span>List</span></a>
    <c:if test="${access.edit}">
        <a class="item-button-save"><span>Update</span></a>
    </c:if>
</div>

<div class="main-box">
    <sesform:form id="editForm" name="editForm" method="post" modelAttribute="receiptManual_edit">
        <table width="100%" style="border:none">
        <tr>
            <td width="53%">
                <table width="100%" style="border:none">
                <tr>
                    <td width="26%" nowrap="nowrap" align="right"><spring:message
									code="goodsreceipt.code" /> :</td>
                    <td width="74%"><input value="${receiptManual_edit.code}" class='input-disabled' disabled="true" size='25'/></td>
                </tr>
                <tr>
                    <td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.organization" /> :</td>
                    <td>
                        <form:select id="org" path="organization" cssClass="combobox-ext">
                            <c:if test='${not empty receiptManual_edit.organization}'>
                                <form:option value='${receiptManual_edit.organization.id}' label='${receiptManual_edit.organization.fullName}' />
                            </c:if>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.date" /> :</td>
                    <td><input id="date" name="date" class="input-disabled" size="8" disabled="true" value="<fmt:formatDate value='${receiptManual_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
                </tr>
                <tr>
                    <td nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
                    <td>
                        <select id='facility' name="facility" class="combobox-ext">
                            <option value="${receiptManual_edit.facility.id}">${receiptManual_edit.facility.name}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.supplier.invoice" />:</td>
                    <td>
                        <form:select id="supplier" path="supplier" cssClass="combobox-ext">
                            <c:if test='${not empty receiptManual_edit.supplier}'>
                                <form:option value='${receiptManual_edit.supplier.id}' label='${receiptManual_edit.supplier.fullName}' />
                            </c:if>
                        </form:select>
                    </td>
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
                    <td><form:textarea path="note" cols="55" rows="7"/></td>
                </tr>
                </table>
            </td>
            <td width="47%" valign="top" align="left">
                <fieldset>
                    <legend><strong><spring:message code="sirius.transaction.recap"/></strong></legend>
                    <table width="100%">
                        <tr>
                            <th width="50%">&nbsp;</th>
                            <th width="25%">Qty</th>
                            <th width="25%">Qty (Pcs)</th>
                        </tr>
                        <tr>
                            <td align="right">Total : </td>
                            <td><input id="totalQty" value="<fmt:formatNumber value='${receiptManual_edit.totalQuantity}' pattern=',##0.00'/>" class="number-disabled" disabled size="15"/></td>
                            <td><input id="totalQtyPcs" value="<fmt:formatNumber value='${receiptManual_edit.totalQuantityPcs}' pattern=',##0.00'/>" class="number-disabled" disabled size="15"/></td>
                        </tr>
                    </table>
                </fieldset>
                <fieldset>
                    <legend><strong><spring:message code="sirius.reference"/></strong></legend>
                    <table width="100%">
                        <tr>
                            <td align="right" width="45%">Goods Receipt</td>
                            <td> :&nbsp;
                                <c:forEach items="${receiptManual_edit.receipts}" var="receipt" varStatus="status">
                                    <c:if test="${status.index > 0}">, </c:if>
                                    <a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${receipt.id}'/>"><c:out value="${receipt.code}"/></a>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>
                         	<td width="45%" align="right">Barcode No</td>
                             <td>:&nbsp; 
                            	<a href="<c:url value='/page/barcodegrouppreedit.htm?id=${receiptManual_edit.barcodeGroup.id}'/>"><c:out value='${receiptManual_edit.barcodeGroup.code}'/></a><br/>
      	               	  	 </td>
                         </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
        </table>
        <br/>
		<div class="toolbar-clean">
			<div class="item-navigator">&nbsp;</div>
			<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
			<a class="item-button-delete" ><span><spring:message code="sirius.row.delete"/></span></a>
		</div>
        <table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0" class="table-list">
        <thead>
            <tr>
                <th width="2%">&nbsp;</th>
                <th width="8%"><spring:message code="grid"/></th>
				<th width="8%"><spring:message code="container"/></th>
                <th width="8%"><spring:message code="sirius.code" /></th>
                <th width="8%"><spring:message code="sirius.name" /></th>
                <th width="8%"><spring:message code="product.category" /></th>
                <th width="8%"><spring:message code="barcode" /></th>
                <th width="6%"><spring:message code="product.uom" /></th>
                <th width="6%"><spring:message code="sirius.qty"/></th>
                <th width="6%"><spring:message code="sirius.qty"/> (Pcs)</th>
                <th width="6%"><spring:message code="sirius.price"/> (Pcs)</th>
                <th width="6%"><spring:message code="goodsreceiptitem.priceadjust"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${receiptManual_edit.items}" var="item">
            <tr>
                <td></td>
                <td>${item.container.grid.name}</td>
                <td>${item.container.name}</td>
                <td>${item.product.code}</td>
                <td>${item.product.name}</td>
                <td>${item.product.productCategory.name}</td>
                <td>${item.lot.serial}</td>
                <td>${item.product.unitOfMeasure.measureId}</td>
                <td><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
                <td><fmt:formatNumber value='${item.product.qtyToBase * item.quantity}' pattern=',##0.00'/></td>
                <td><fmt:formatNumber value='${item.amount / item.product.qtyToBase}' pattern=',##0.00'/></td>
                <td><fmt:formatNumber value='${item.amount}' pattern=',##0.00'/></td>
            </tr>
            </c:forEach>
        </tbody>
        <tfoot><tr class="end-table"><td colspan="15">&nbsp;</td></tr></tfoot>
        </table>
    </sesform:form>
</div>
<div class="info">Created by : <c:out value='${receiptManual_edit.createdBy.fullName}'/> (<fmt:formatDate value='${receiptManual_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | Last update by : <c:out value='${receiptManual_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${receiptManual_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function() {
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Goods Issue Manual',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

		$('.item-button-save').click(function(){

            $.ajax({
                url:"<c:url value='/page/goodsreceiptmanualedit.htm'/>",
                data:$('#editForm').serialize(),
                type : 'POST',
                dataType : 'json',
                beforeSend:function()
                {
                    $dialog.empty();
                    $dialog.html('Updating Goods Receipt Manual data......');
                    $dialog.dialog('open');
                },
                success : function(json) {
                    if(json)
                    {
                        if(json.status == 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/goodsreceiptmanualview.htm'/>";
                        }
                        else
                        {
                            $dialog.empty();
                            $dialog.html('Proccess fail,reason:<br/>'+json.message);
                        }
                    }
                }
            });
		});

    });
</script>