<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/goodsreceiptview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <c:if test='${access.edit}'>
        <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
    </c:if>
  <a class="item-button-print"  href="<c:url value='/page/goodsreceiptprintoption.htm?id=${goodsReceipt_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="editForm" name="editForm" method="post" modelAttribute="adapter">
        <table width="100%" style="border:none">
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.code"/> :</td>
                <td width="74%"><input value="${goodsReceipt_edit.code}" class='input-disabled' size='25' disabled/></td>
            </tr>
            <tr>
                <td align="right"><spring:message code="goodsreceipt.organization"/> :</td>
                <td>
                    <select id="org" name="organization" class="combobox-ext input-disabled" disabled>
                        <option value='${goodsReceipt_edit.organization.id}'><c:out value='${goodsReceipt_edit.organization.fullName}'/></option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
                <td width="74%">
                    <select id='facility' name="facility" class="combobox-ext input-disabled" disabled>
                        <option value="${goodsReceipt_edit.facility.id}"><c:out value='${goodsReceipt_edit.facility.name}'/></option>
                    </select>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.date"/> :</td>
                <td>
                    <input id="date" name="date" size="10" value="<fmt:formatDate value='${goodsReceipt_edit.date}' pattern='dd-MM-yyyy'/>" class="input-disabled" disabled/>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right" valign="top"><spring:message code="goodsreceipt.note"/> :</td>
                <td><form:textarea path="note" cols="45" rows="7"/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="sirius.reference"/> :</td>
                <td width="74%"><input value="<spring:message code='warehousetransactionsource.${transactionSource.message}'/>"
                                       class='input-disabled' size='25' disabled/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="accreport.referenceno"/> :</td>
                <td width="74%"><input value="${ref.referenceCode}" class='input-disabled' size='25' disabled/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.from"/> :</td>
                <td width="74%"><input value="${ref.referenceFrom}" class='input-disabled' size='25' disabled/></td>
            </tr>
            <tr>
                <td width="26%" nowrap="nowrap" align="right"><spring:message code="goodsreceipt.do.no"/> :</td>
                <td width="74%"><input size='25' id="invoiceNo" name="invoiceNo" value="${goodsReceipt_edit.invoiceNo}"/></td>
            </tr>
        </table>
        <br/>
        <div style="overflow-x: auto;">
            <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
                <thead>
                <tr>
                    <th width="10%"><spring:message code="product"/></th>
                    <th width="10%" style="white-space: normal; line-height: 10px; text-align: center"><spring:message code="invoiceverificationitem.receivedqty"/></th>
                    <th width="5%"><spring:message code="goodsreceiptitem.uom"/></th>
                    <th width="5%"><spring:message code="barcode"/></th>
                    <th width="5%"><spring:message code="container"/></th>
                    <th width="5%"><spring:message code="grid"/></th>
                    <th width="5%"><spring:message code="pricecategory.price"/></th>
                    <th width="5%"><spring:message code="sirius.total"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items='${goodsReceipt_edit.items}' var='item' varStatus='status'>
                    <tr>
                        <td>
                            <select class="combobox-ext product input-disabled" id="product[${status.index}]" index="${status.index}" disabled>
                                <option value="${item.product.id}"><c:out value='${item.product.name}'/></option>
                            </select>
                        </td>
                        <td>
                            <input class="input-number receipt input-disabled" index="${status.index}" id="receipted[${status.index}]" name='items[${status.index}].receipted'
                                   size='6' value="<fmt:formatNumber value='${item.receipted}' pattern=',##0'/>" disabled/>
                        </td>
                        <td>
                            <input class="input-disabled" size="5" value="${item.warehouseTransactionItem.referenceItem.measureName}" disabled/>
                        </td>
                        <td>
                            <input class="input-disabled" size="10" value="${item.warehouseTransactionItem.lot.serial}" disabled/>
                        </td>
                        <td>
                            <form:select id='container[${status.index}]' path='items[${status.index}].container' cssClass='combobox containers input-disabled' disabled="true">
                                <c:if test="${not empty item.container}">
                                    <option value="${item.container.id}"><c:out value='${item.container.name}'/></option>
                                </c:if>
                            </form:select>
                        </td>
                        <td>
                            <form:select id='grid[${status.index}]' path='items[${status.index}].grid' cssClass='combobox grids input-disabled' disabled="true">
                                <c:if test="${not empty item.grid}">
                                    <option value="${item.grid.id}"><c:out value='${item.grid.name}'/></option>
                                </c:if>
                            </form:select>
                        </td>
                        <td>
                            <input class="input-disabled" size='6' value="<fmt:formatNumber value='${item.warehouseTransactionItem.money.amount}' pattern=',##0'/>" disabled/>
                        </td>
                        <td>
                            <input class="input-disabled" size='6' value="<fmt:formatNumber value='${item.receipted*item.warehouseTransactionItem.money.amount}' pattern=',##0'/>" disabled/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot><tr class="end-table"><td colspan="10">&nbsp;</td></tr></tfoot>
            </table>
        </div>
    </sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${goodsReceipt_edit.createdBy.fullName}'/> (<fmt:formatDate value='${goodsReceipt_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${goodsReceipt_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${goodsReceipt_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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

            $('.product').each(function(){
                let $idx = $(this).attr('index');
                $product = $('#product\\['+$idx+'\\]').text();
                $container = $("#container\\["+$idx+"\\]").val();

                if(!$container) {
                    alert('<spring:message code="goodsreceiptitem.container"/> '+$product+' <spring:message code="notif.empty"/> !');
                    return $send = false;
                }
            });

            if($send)
            {
                $.ajax({
                    url:"<c:url value='/page/goodsreceiptedit.htm'/>",
                    data:$('#editForm').serialize(),
                    type : 'POST',
                    dataType : 'json',
                    beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
                    success : function(json) {
                        if(json)
                        {
                            if(json.status == 'OK')
                            {
                                $dialog.dialog('close');
                                window.location="<c:url value='/page/goodsreceiptview.htm'/>";
                                <%--window.location="<c:url value='/page/goodsreceiptpreedit.htm?id='/>"+json.id;--%>
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

        $('.allgrid').change(function(){
            $ops = $(this).find("option");

            $grd = $(".grids");
            $grd.empty();

            $ops.clone().appendTo($grd);
        });

        $('.allcontainer').change(function(){
            $opt = $(this).find("option");

            $cont = $(".containers");
            $cont.empty();

            $opt.clone().appendTo($cont);

            $('.allgrid').change();
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
</script>
