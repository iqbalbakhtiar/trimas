<%@ include file="/pages/includes/sirius-head.jsp"%>

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
			<td><input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext">
					<c:if test='${not empty adjustment_add.organization}'>
						<form:option value='${adjustment_add.organization.id}' label='${adjustment_add.organization.fullName}' />
					</c:if>
				</form:select>
				<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" style="CURSOR:pointer;" title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
			<td>
				<form:select id="facility" path="facility" cssClass="combobox-ext">
				</form:select>
				<img src="assets/icons/list_extensions.gif" onclick="openfacility();" style="cursor: pointer;" title="Facility" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="sirius.reason"/> :</td>
			<td><form:textarea path="reason" cols="55" rows="7"/></td>
		</tr>
	</table>
		<div class="toolbar-clean">
			<div class="item-navigator">&nbsp;</div>
			<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
			<a class="item-button-delete" ><span><spring:message code="sirius.row.delete"/></span></a>
		</div>
		<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
		<thead>
			<tr>
				<th width="2%"><input id ="checkline" value="line" class="checkall" type="checkbox"/></th>
				<th ><spring:message code="grid"/></th>
				<th ><spring:message code="container"/></th>
				<th width="12"><spring:message code="sirius.code"/></th>
				<th ><spring:message code="product.name"/></th>
				<th width="12%"><spring:message code="product.category"/></th>
				<th width="8%"><spring:message code="product.uom"/></th>
				<th width="12%"><spring:message code="product.onhand"/></th>
				<th width="12%"><spring:message code="product.quantity"/></th>
				<th width="14%"><spring:message code="sirius.price"/></th>
			</tr>
		</thead>
		<tbody id="iBody">
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
    
		$('.item-button-save').click(function(){
			var total = $("#iBody tr").length;
			if(total < 1)
			{
				alert('<spring:message code="notif.line"/> !');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/stockadjustmentadd.htm'/>",
				data:$('#addForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend:function()
				{
					$dialog.empty();
					$dialog.html('Saving Stock Adjustment data......');
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
							$dialog.html('Proccess fail,reason :<br/>'+json.message);
						}
					}
				}
			});	
		});

		$('#facility').focus(function(){
			$('.check').prop("checked", true);
			$('.item-button-delete').click();
		});

		$('.checkall').click(function () {
            $('.check').prop("checked", this.checked);
        });

        $('.item-button-delete').click(function () {
            $('.check').each(function(){
                if(this.checked){
                    this.checked = false;

                    $(this).parent().parent().remove();
                }
            });
                
            $('.checkall').prop("checked", false);   
		});
	});
		
	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
	}

	var index = 0;
	function addLineItem()
	{
		$tbody = $('#lineItemTable');
		$tr = $('<tr/>');
		
		const checkbox = List.get('<input type="checkbox" class="check"/>','check['+index +']');	
		const grid = List.get('<select class="combobox-min" onchange='+"changeGrid("+index+");"+'/>','grid['+index+']');
		const gridImg = List.img('Grid', index, 'opengridpopup("'+index+'")');
		const container = List.get('<select class="combobox-min" onchange='+"checkOnHand("+index+");"+'/>','container['+index+']');
		const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
		const product = List.get('<select class="combobox-ext product" onchange='+"checkOnHand("+index+");"+'/>','product['+index+']');
		const productImg = List.img('Product', index, 'openproduct("'+index+'")');
		const codeProduct = List.get('<input class="input-disabled" disabled="true" size="10"/>','codeprod['+index+']');
		const category = List.get('<input class="input-disabled" disabled="true" size="10"/>','category['+index+']');
		const uom = List.get('<input class="input-disabled" disabled="true" size="5"/>','uom['+index+']');
		const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']', '0.00');
		const quantity = List.get('<input class="input-number negative" onchange='+"checkQuantity("+index+");"+' size="12"/>','quantity['+index+']', '0.00');
		const price = List.get('<input class="input-number" size="12"/>','price['+index+']', '0.00');
		/* const currency = List.get('<select hidden="hidden"/>','currency['+index+']');
		const currencies = JSON.parse('${jsonUtil:toJson(currencies)}');

		for(const curr of currencies)
			currency.append('<option value="'+curr.id+'">'+curr.symbol+'</option>');

		const type = List.get('<select hidden="hidden"/>','info['+index+']');
		type.append('<option value="MIDDLE">MIDDLE</option>');
		type.append('<option value="SPOT">SPOT</option>');
		type.append('<option value="TAX">TAX</option>'); */
		
		$tr.append(List.col([checkbox]));
		$tr.append(List.col([grid, gridImg]));
		$tr.append(List.col([container, containerImg]));
		$tr.append(List.col([codeProduct]));
		$tr.append(List.col([product, productImg]));
		$tr.append(List.col([category]));
		$tr.append(List.col([uom]));
		$tr.append(List.col([onHand]));
		$tr.append(List.col([quantity]));
		$tr.append(List.col([price]));

		$tbody.append($tr);
		
		index++;

		$(".input-number").bind(inputNumber);
		$(".input-decimal").bind(inputNumber);
	}
					
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
		var grid = document.getElementById('grid['+index+']');
		if(grid.value == null || grid.value == '')
		{
			alert('<spring:message code="grid"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]&grid="+grid.value);
	}
	
	function openproduct(index)
	{
		const con = document.getElementById('container['+index+']');
		if(!con.value)
		{
			alert('<spring:message code="container"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		const org = document.getElementById('org');
		if(!org.value)
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupproductforadjustmentview.htm?target=product['/>"+index+"]&index="+index+"&container="+con.value+"&organization="+org.value);
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