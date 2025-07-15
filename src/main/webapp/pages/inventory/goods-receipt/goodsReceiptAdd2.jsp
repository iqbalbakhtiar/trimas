<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/goodsreceiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <c:if test='${access.add}'>
        <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
    </c:if>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="goodsReceipt_add">
        <table width="100%" style="border:none">
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.code"/> :</td>
                <td width="74%"><input value="<spring:message code="sirius.auto.generated"/>" class='input-disabled' size='25'/></td>
            </tr>
            <tr>
                <td align="right"><spring:message code="goodsreceipt.organization"/> :</td>
                <td>
                    <select id="org" name="organization" class="combobox-ext input-disabled">
                        <option value='${goodsReceipt_add.organization.id}'><c:out value='${goodsReceipt_add.organization.fullName}'/></option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
                <td width="74%">
                    <select id='facility' name="facility" class="combobox-ext input-disabled">
                        <option value="${goodsReceipt_add.facility.id}"><c:out value='${goodsReceipt_add.facility.name}'/></option>
                    </select>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.date"/> :</td>
                <td>
                    <input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right" valign="top"><spring:message code="goodsreceipt.note"/> :</td>
                <td><form:textarea path="note" cols="45" rows="7"/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                <td width="74%"><input value="<spring:message code='warehousetransactionsource.${transactionSource.message}'/>"
                                       class='input-disabled' size='25'/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="accreport.referenceno"/> :</td>
                <td width="74%"><input value="${ref.referenceCode}" class='input-disabled' size='25'/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.from"/> :</td>
                <td width="74%"><input value="${ref.referenceFrom}" class='input-disabled' size='25'/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.do.no"/> :</td>
                <td width="74%"><input size='25' id="invoiceNo" name="invoiceNo"/></td>
            </tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="grid"/> & <spring:message code="container"/> :</td>
				<td>
					<select id="grid[-1]" class="combobox allgrid input-disabled" style="display: none;">
					</select>
					<select id="container[-1]" class="combobox allcontainer">
					</select>
					<a class="item-popup" onclick="opencontainerall('-1');" title='<spring:message code="container"/>'/>
				</td>
			</tr>
        </table>
        <br/>
        <div style="overflow-x: auto;">
            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                <thead>
                <tr>
                    <th width="10%"><spring:message code="product"/></th>
                    <th width="5%" style="white-space: normal; line-height: 10px; text-align: center"><spring:message code="invoiceverificationitem.receivedqty"/></th>
                    <th width="5%"><spring:message code="goodsreceiptitem.uom"/></th>
                    <th width="5%"><spring:message code="barcode"/></th>
                    <th width="5%"><spring:message code="grid"/></th>
                    <th width="5%"><spring:message code="container"/></th>
                    <th width="5%"><spring:message code="pricecategory.price"/></th>
                    <th width="5%"><spring:message code="sirius.total"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items='${goodsReceipt_add.items}' var='item' varStatus='status'>
                    <tr>
                        <td>
                            <select class="combobox-ext product input-disabled" id="product[${status.index}]" index="${status.index}">
                                <option value="${item.product.id}"><c:out value='${item.product.name}'/></option>
                            </select>
                        </td>
                        <td>
                            <input class="receipt input-number"
                                   index="${status.index}"
                                   id="receipted[${status.index}]"
                                   name="items[${status.index}].receipted"
                                   size="6"
                                   value="<fmt:formatNumber value='${item.warehouseTransactionItem.unreceipted}' pattern=',##0'/>"
                                    <c:if test="${item.product.serial}">
                                        onchange="checkQtySerial(${status.index});"
                                    </c:if>
                            />
                            <input id="av[${status.index}]" type="hidden" size="10"
                                   value="<fmt:formatNumber value='${item.warehouseTransactionItem.unreceipted}' pattern=',##0'/>"/>
                        </td>
                        <td>
                            <input class="input-disabled" size="7" value="${item.warehouseTransactionItem.referenceItem.measureName}" disabled/>
                        </td>
                        <td>
                            <input class="input-disabled" size="10" value="${item.warehouseTransactionItem.referenceItem.lot.serial}" disabled/>
                        </td>
                        <td>
                            <form:select id='grid[${status.index}]' path='items[${status.index}].grid' cssClass='combobox grids input-disabled'>
                                <c:if test="${not empty item.warehouseTransactionItem.destinationGrid}">
                                    <option value="${item.warehouseTransactionItem.destinationGrid.id}"><c:out value='${item.warehouseTransactionItem.destinationGrid.name}'/></option>
                                </c:if>
                            </form:select>
                        </td>
                        <td>
                            <form:select id='container[${status.index}]' path='items[${status.index}].container' cssClass='combobox containers' index="${status.index}">
                                <c:if test="${not empty item.warehouseTransactionItem.destinationContainer}">
                                    <option value="${item.warehouseTransactionItem.destinationContainer.id}"><c:out value='${item.warehouseTransactionItem.destinationContainer.name}'/></option>
                                </c:if>
                            </form:select>
                            <c:if test="${empty item.warehouseTransactionItem.destinationContainer}">
                                <a class="item-popup" onclick="opencontainerall('${status.index}');" title='<spring:message code="container"/>'/>
                            </c:if>
                        </td>
                        <td>
                            <input id="price[${status.index}]" size="10" class="input-disabled" value="<fmt:formatNumber value='${item.amount}' pattern=',##0'/>" disabled/>
                        </td>
                        <td>
                            <input id="total[${status.index}]" size="10" class="input-disabled" value="<fmt:formatNumber value='${item.amount*item.warehouseTransactionItem.unreceipted}' pattern=',##0'/>" disabled/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot><tr class="end-table"><td colspan="10">&nbsp;</td></tr></tfoot>
            </table>
        </div>
    </sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    $(function(){
        var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

        $('.item-button-save').click(function(e){
            $send = true;

            if(!$('#date').val())
            {
                alert('<spring:message code="goodsreceipt.date"/> <spring:message code="notif.empty"/> !');
                return;
            }

            /* if(!$('#invoiceNo').val())
            {
                alert('<spring:message code="goodsreceipt.supplier.invoice"/> <spring:message code="notif.empty"/> !');
                return;
            } */

            $('.product').each(function(){
                let $idx = $(this).attr('index');
                $product = $('#product\\['+$idx+'\\]').text();
                $container = $("#container\\["+$idx+"\\]").val();
                $grid = $("#grid\\["+$idx+"\\]").val();

                if(!$container) {
                    alert('<spring:message code="goodsreceiptitem.container"/> '+$product+' <spring:message code="notif.empty"/> !');
                    return $send = false;
                }

                if(!$grid) {
                    alert('<spring:message code="goodsreceiptitem.grid"/> '+$product+' <spring:message code="notif.empty"/> !');
                    return $send = false;
                }
            });

            if($send)
            {
                $.ajax({
                    url:"<c:url value='/page/goodsreceiptadd.htm'/>",
                    data:$('#addForm').serialize(),
                    type : 'POST',
                    dataType : 'json',
                    beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
                    success : function(json) {
                        if(json)
                        {
                            if(json.status == 'OK')
                            {
                                $dialog.dialog('close');
                                window.location="<c:url value='/page/goodsreceiptpreedit.htm?id='/>"+json.id;
                            }
                            else
                                afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
                        }
                    }
                });
            }
        });

        $(".receipt").change(function(e) {
            $index = $(this).attr('index');

            var onhand = parseFloat($('#av\\['+$index+'\\]').val().toNumber());
            var qty = parseFloat($(this).val().toNumber());

            if(qty >= onhand)
                $(this).val(onhand);
        });

        $('.allcontainer').change(function(){
        	$opt = $(this).find("option");
            $cont = $(".containers");
            $cont.empty();
            
            $opt.clone().appendTo($cont);
            
            $('.allgrid').change();
        });

        $('.allgrid').change(function(){
        	$opt = $(this).find("option");
            $grid = $(".grids");
            $grid.empty();

            $opt.clone().appendTo($grid);
        });

        $('.input-number').blur();
    });

    function opencontainerall(index) {
        if (!$('#facility').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="facility"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const facilityId = $('#facility').val();
        const baseUrl = '<c:url value="/page/popupcontainerview.htm"/>';
        const params = {
            target: 'container[' + index + ']',
            index: index,
            facility: facilityId
        }

        openpopup(buildUrl(baseUrl, params));
    }

    function checkQtySerial(index) {
        var receiptVal = parseFloat($('#receipted\\[' + index + '\\]').val());
        var avVal = parseFloat($('#av\\[' + index + '\\]').val());

        // Jika nilai receipt tidak 0 dan tidak sama dengan nilai serial (av)
        if (receiptVal !== 0 && receiptVal !== avVal) {
            alert("Untuk barang serial, Receipt Quantity hanya bisa 0 atau <strong>quantity serialnya.</strong>");
            // Set nilai receipt kembali ke nilai serial
            $('#receipted\\[' + index + '\\]').val(avVal);
        }
    }
    
    $(document).on('input', '.receipt', function () {

        const $input = $(this);
        const index = $input.attr('index');

        const priceText = $('#price\\[' + index + '\\]').val().replace(/,/g, '');
        const amount = parseFloat(priceText) || 0;

        const qtyText = $input.val().replace(/,/g, '');
        const qty = parseFloat(qtyText) || 0;

        const total = qty * amount;

        $('#total\\[' + index + '\\]').val(total.toLocaleString('en-US'));
        
    });
</script>
