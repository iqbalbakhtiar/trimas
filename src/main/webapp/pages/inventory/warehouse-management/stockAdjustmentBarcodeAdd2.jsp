<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/stockadjustmentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
    <sesform:form id="addForm" name="addForm" method="post" modelAttribute="adjustment_add">
        <table width="100%" style="border:none">
            <tr>
                <td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.id"/> :</td>
                <td width="80%"><input value="Auto Generated" class='input-disabled' size='25' disabled/></td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                <td><input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker input-disabled"/></td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
                <td>
                    <form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
                        <c:if test='${not empty adjustment_add.organization}'>
                            <form:option value='${adjustment_add.organization.id}' label='${adjustment_add.organization.fullName}' />
                        </c:if>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
                <td>
                    <form:select id="facility" path="facility" cssClass="combobox-ext input-disabled">
                        <c:if test='${not empty barcodeGroup.facility}'>
                            <form:option value='${barcodeGroup.facility.id}' label='${barcodeGroup.facility.name}' />
                        </c:if>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="barcode"/> :</td>
                <td>
                    <form:select id="barcodeGroup" path="barcodeGroup" cssClass="combobox-ext input-disabled">
                        <c:if test='${not empty barcodeGroup}'>
                            <form:option value='${barcodeGroup.id}' label='${barcodeGroup.code}' />
                        </c:if>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="sirius.reason"/> :</td>
                <td><form:textarea path="reason" cols="55" rows="7"/></td>
            </tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="grid"/> & <spring:message code="container"/> :</td>
				<td>
					<select id="grid[-1]" class="combobox allgrid input-disabled" style="display: none;">
					</select>
					<select id="container[-1]" class="combobox allcontainer">
					</select>
					<a class="item-popup" onclick="opencontainerpopup('-1');" title='<spring:message code="container"/>'/>
				</td>
			</tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
        </table>
        <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
            <thead>
            <tr>
                <th ><spring:message code="grid"/></th>
                <th ><spring:message code="container"/></th>
                <th width="12"><spring:message code="sirius.code"/></th>
                <th ><spring:message code="product.name"/></th>
                <th width="12%"><spring:message code="product.category"/></th>
                <th width="8%"><spring:message code="product.uom"/></th>
                <th width="8%"><spring:message code="barcode"/></th>
                <th width="5%"><spring:message code="product.lot"/></th>
                <th width="12%"><spring:message code="product.onhand"/></th>
                <th width="12%"><spring:message code="product.quantity"/></th>
                <th width="14%"><spring:message code="sirius.price"/></th>
            </tr>
            </thead>
            <tbody id="iBody">
            <c:forEach items='${items}' var='item' varStatus="status">
                <tr>
                    <td nowrap="nowrap">
                        <select class="combobox-min2 grids" id="grid[${status.index}]" name="items[${status.index}].grid" index="${status.index}" next="grid"></select>
                    </td>
                    <td nowrap="nowrap">
                        <select class="combobox-min2 containers" onchange="checkOnHand(${status.index});" id="container[${status.index}]" name="items[${status.index}].container" index="${status.index}" next="container"></select>
                        <a class="item-popup" title="Container" index="${status.index}" onclick="opencontainerpopup(${status.index})"></a>
                    </td>
                    <td nowrap="nowrap"><input class="input-disabled" disabled size="13" value="${item.product.code}"></td>
                    <td nowrap="nowrap">
                        <select class="combobox-ext product input-disabled" onchange="checkOnHand(${status.index});" id="product[${status.index}]" name="items[${status.index}].product" index="${status.index}" next="product" readonly="">
                            <option value="${item.product.id}">${item.product.name}</option>
                        </select>
                    </td>
                    <td nowrap="nowrap"><input class="input-disabled" disabled size="10" value="${item.product.productCategory.name}"></td>
                    <td nowrap="nowrap"><input class="input-disabled" disabled size="5" value="${item.product.unitOfMeasure.measureId}"></td>
                    <td nowrap="nowrap"><input class="input-disabled" readonly="true" size="13" name="items[${status.index}].serial" index="${status.index}" next="serial" value="${item.serial}"></td>
                    <td nowrap="nowrap"><input class="input-disabled" readonly="true" size="5" name="items[${status.index}].lotCode" index="${status.index}" next="lotCode" value="${item.lotCode}"></td>
                    <td nowrap="nowrap"><input class="number-disabled" disabled size="12" id="onhand[${status.index}]" index="${status.index}" next="onhand" value="0.00"></td>
                    <td nowrap="nowrap"><input class="input-number negative" onchange="checkQuantity(${status.index});" size="12" id="quantity[${status.index}]" name="items[${status.index}].quantity" index="${status.index}" next="quantity" value="${item.quantityReal}"></td>
                    <td nowrap="nowrap"><input class="input-number" size="12" id="price[${status.index}]" name="items[${status.index}].price" index="${status.index}" next="price" value="<fmt:formatNumber value='${item.price}' pattern=',##0.00'/>"></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr class="end-table"><td colspan="11">&nbsp;</td></tr>
            </tfoot>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    $(function(){
        var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

        $('.item-button-save').click(function(){
            var total = $("#iBody tr").length;
            if(total < 1)
            {
                alert('<spring:message code="notif.line"/> !');
                return;
            }

            $.ajax({
                url:"<c:url value='/page/stockadjustmentbarcodeadd.htm'/>",
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
                        if(json.status == 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/stockadjustmentview.htm'/>";
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

        $('#facility').focus(function(){
            $('.check').prop("checked", true);
            $('.item-button-delete').click();
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
    });

    function opengridpopup(index)
    {
        const fac = document.getElementById('facility');

        if(!fac.value)
        {
            alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
            return;
        }

        openpopup("<c:url value='/page/popupgridview.htm?target=grid['/>"+index+"]&facility="+fac.value);
    }

    function opencontainerpopup(index)
    {
    	var facility = document.getElementById('facility')
    	if(facility.value == null || facility.value == '')
        {
            alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
            return;
        }
    	
        /* var grid = document.getElementById('grid['+index+']');
        if(grid.value == null || grid.value == '')
        {
            alert('<spring:message code="grid"/> <spring:message code="notif.empty"/> !');
            return;
        } */

        //openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]&grid="+grid.value);
        openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]&index="+index+"&facility="+facility.value);
    }

    function changeGrid(index) {
        document.getElementById("container["+index+"]").innerHTML = "";
        $('#onhand\\['+index+'\\]').val(0.00);
    }

    function checkOnHand(index) {
        let prodId = $('#product\\['+index+'\\]').val();
        let conId = $('#container\\['+index+'\\]').val();

        $('#onhand\\['+index+'\\]').val(0.00);
        if(prodId) {
            $.ajax({
                url:"<c:url value='/page/onhandquantityviewonhandjson.htm'/>",
                data:{product:prodId, container:conId},
                method : 'GET',
                dataType : 'json',
                success : function(json) {
                    if(json)
                    {
                        if(json.status == 'OK')
                            $('#onhand\\['+index+'\\]').val(parseFloat(json.onHand).numberFormat('#,##0.00'));
                    }
                }
            });
        }

        if(prodId) {
            $.ajax({
                url:"<c:url value='/page/stockadjustmentbyproductjson.htm'/>",
                data:{productId:prodId},
                method : 'GET',
                dataType : 'json',
                success : function(json) {
                    if(json)
                    {
                        if(json.status == 'OK'){
                            let amount = document.getElementsByName('items['+index+'].price')[0];
                            if(amount)
                                amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');
                        }
                    }
                }
            });
        }

    }

    function checkQuantity(index)
    {
        var onHand = document.getElementById('onhand[' + index + ']').value.toNumber();
        var qty = document.getElementById('quantity[' + index + ']').value.toNumber();

        if((onHand+qty) < 0)
        {
            alert('Qty Adjustment (-) cannot greater than On Hand !!!');
            document.getElementById('quantity[' + index + ']').value = 0.00;
            return;
        }
    }
</script>