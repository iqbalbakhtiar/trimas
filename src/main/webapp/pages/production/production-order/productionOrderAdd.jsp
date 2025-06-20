<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/productionorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="order_add" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="organization"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="org" path="productionOrder.organization" cssClass="combobox-ext">
									<c:if test='${not empty order_add.organization}'>
										<form:option value='${order_add.organization.id}' label='${order_add.organization.fullName}'/>
									</c:if>
								</form:select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="<spring:message code="organization"/>" />
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" name="productionOrder.date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.threadtype"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="product" name="productionOrder.product" onchange="addOutput(this);" class="combobox-ext">
								</select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupproductview.htm?target=product&categoryId=4'/>');" title="<spring:message code="product"/>" />
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.lotnumber"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="lotNumber" path="productionOrder.lotNumber"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.note"/></td>
							<td width="1%" align="center">:</td>
							<td><form:textarea path="productionOrder.note" rows="6" cols="45"/></td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
				<td width="40%" valign="top">
				</td>
	    	</tr>
	    </table>
    	<br/>
    	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
			<div id="costCenterGroupLine" dojoType="ContentPane" label="<spring:message code='costgroup'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<div class="toolbar-clean">
						<a class="item-button-new cost-center-new"><span><spring:message code="sirius.row.new"/></span></a>
						<a class="item-button-delete" body="cost"><span><spring:message code="sirius.row.delete"/></span></a>
						<div class="item-navigator">&nbsp;</div>
					</div>
					<table class="table-list" id="costCenterGroupTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap"><input class="checkallcost" type="checkbox"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="costgroup.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="costgroup.name"/></th>
							</tr>
						</thead>
						<tbody id="costCenterGroupItem">
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="outputMaterialLine" dojoType="ContentPane" label="<spring:message code='productionorder.outputmaterial'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<div class="toolbar-clean">
						<a id="addOutput" class="item-button-new item-new" body="outputItem"><span><spring:message code="sirius.row.new"/></span></a>
						<a class="item-button-delete" body="output"><span><spring:message code="sirius.row.delete"/></span></a>
						<div class="item-navigator">&nbsp;</div>
					</div>
					<table class="table-list" id="outputMaterialTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap"><input class="checkalloutput" type="checkbox"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="product.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="product.name"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="productionorder.cogsweight"/></th>
							</tr>
						</thead>
						<tbody id="outputItem">
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="wipLine" dojoType="ContentPane" label="WIP" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<div class="toolbar-clean">
						<a id="addWip" class="item-button-new item-new" body="wipItem"><span><spring:message code="sirius.row.new"/></span></a>
						<a class="item-button-delete" body="wip"><span><spring:message code="sirius.row.delete"/></span></a>
						<div class="item-navigator">&nbsp;</div>
					</div>
					<table class="table-list" id="wipTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap"><input class="checkallwip" type="checkbox"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="product.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="product.name"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="uom"/></th>
							</tr>
						</thead>
						<tbody id="wipItem">
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	var index = 0;
	$(function(){
		$('.item-button-save').click(function(){
			if (validation())
				save();
		});
	
	    $('.cost-center-new').click(function() {
	    	addCost(index);
	    });
	    
	    $('.item-new').click(function() {
	    	addMaterial(index, $(this).attr('body'));
	    });
	
	    $('.checkallcost').click(function () {
			$('.checkcost').prop("checked", this.checked);
	    });
	    
	    $('.checkalloutput').click(function () {
			$('.checkoutput').prop("checked", this.checked);
	    });
	    
	    $('.checkallwip').click(function () {
			$('.checkwip').prop("checked", this.checked);
	    });
	
	    $('.item-button-delete').click(function () {
			$('.check' + $(this).attr('body')).each(function(){
	        	if(this.checked) {
	          		this.checked = false;
	      			$(this).parent().parent().remove();
	        	}
			});
			
			$('.checkall' + $(this).attr('body')).prop("checked", false);
		});
	});

	function validation() 
	{
	    var organization = $('#org').val();
	    if (organization == null || organization === "") 
	    {
			alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
			return false;
	    }

	    var date = $('#date').val();
	    if (date == null || date === "") 
	    {
			alert('<spring:message code="sirius.date"/> <spring:message code="notif.empty"/> !');
			return false;
	    }

	    var product = $('#product').val();
	    if (product == null || product === "") 
	    {
			alert('<spring:message code="product"/> <spring:message code="notif.empty"/> !');
			return false;
	    }

	    if ($('#costCenterGroupItem tr').length === 0) {
			alert('<spring:message code="notif.add"/> <spring:message code="costgroup"/> <spring:message code="notif.select2"/> !');
			return false;
		}
	    
		if ($('#outputItem tr').length === 0) {
			alert('<spring:message code="notif.add"/> <spring:message code="productionorder.outputmaterial"/> <spring:message code="notif.select2"/> !');
			return false;
		}
		
		//Check COGS Weight %
		var weight = 0;
		$(".weight").each((i,e) => {
			weight += parseFloat(e.value.toNumber());
		});
		
		if (weight != 100)
		{
			alert('<spring:message code="productionorder.cogsweight"/> <spring:message code="notif.mustbe"/> 100 !');
			return false;
		}
		
		var isValid = true;
		$('#costCenterGroupItem tr').each((index, element) => {
			var costGroup = $(element).find('select');
			
			if (!costGroup.val())
			{
				alert('<spring:message code="costgroup"/> <spring:message code="notif.empty"/> !');
				isValid = false;
				return false;
			}
		});
		
		$('.products').each((index, element) => {
			console.log($(element));
			if (!$(element).val())
			{
				alert('<spring:message code="product"/> <spring:message code="notif.empty"/> !');
				isValid = false;
				return false;
			}
		});

		if (!isValid)
			return false;

    	return true;
	}
    
	function save() {
		$.ajax({
		    url:"<c:url value='/page/productionorderadd.htm'/>",
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
		          		window.location="<c:url value='/page/productionorderpreedit.htm?id='/>"+json.id;
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

	function checkWeight(obj)
	{
		var weight = 0;
		
		$(".weight").each((i,e) => {
			weight += parseFloat(e.value.toNumber());
		});
		
		if (weight > 100)
		{
			alert('<spring:message code="productionorder.cogsweight"/> <spring:message code="notif.greater"/> 100 !');
			obj.value = "0.00";
			
			return;
		}
	}
	
	function addOutput(obj)
	{
		var val  = obj.value;
		var txt  = obj.options[obj.selectedIndex].text;
		var code = obj.options[obj.selectedIndex].getAttribute("code");
		
		$(".checkalloutput").click();
		$(".item-button-delete").click();
		
		$tbody = $("#outputItem");
		$tr = $('<tr/>');
		
		$cbox = List.get('<input type="checkbox" class="checkoutput"/>', 'check'+index);
		$code = List.get('<input type="text" class="input-disabled" size="20" disabled/>', 'productCode['+index+']');
		$product = List.get('<select class="combobox products"/>', 'product['+index+']');
		$productImg = List.img('<spring:message code="product"/>', index, 'openProduct("'+index+'")');
		$weight = List.get('<input type="text" class="input-decimal weight" onchange='+"checkWeight(this);"+' size="6"/>', 'weight['+index+']', '100.00');
		$type = List.get('<input type="hidden" class="input-disabled" size="6" />', 'materialType['+index+']', "OUTPUT");
		
		$tr.append(List.col([$cbox]));
		$tr.append(List.col([$code, $type]));
		$tr.append(List.col([$product, $productImg]));
		$tr.append(List.col([$weight]));
		
		$code.val(code);
		$product.append(new Option(txt, val))
		
		$tbody.append($tr);
		index++;
		
		$(".input-decimal").bind(inputFormat);
	}

	function addCost($index) 
	{
		$tbody = $('#costCenterGroupItem');
		$tr = $('<tr/>');
		
		$cbox = List.get('<input type="checkbox" class="checkcost"/>', 'check'+$index);
		$code = List.get('<input type="text" class="input-disabled" size="20" disabled/>', 'costGroupCode['+$index+']');
		$costGroup = List.getCost('<select class="combobox costGroups"/>', 'costCenterGroup['+$index+']');
		$costGroupImg = List.img('<spring:message code="costgroup"/>', $index, 'openCostGroup("'+$index+'")');
		
		$tr.append(List.col([$cbox]));
		$tr.append(List.col([$code]));
		$tr.append(List.col([$costGroup, $costGroupImg]));
		
		$tbody.append($tr);
		index++;
	}
	
	function addMaterial($index, body)
	{
		$tbody = $("#"+body);
		$tr = $('<tr/>');
		
		$cbox = List.get('<input type="checkbox"/>', 'check'+$index);
		$cbox.addClass(body == "outputItem" ? "checkoutput" : "checkwip");
		
		$code = List.get('<input type="text" class="input-disabled" size="20" disabled/>', 'productCode['+$index+']');
		$product = List.get('<select class="combobox products"/>', 'product['+$index+']');
		$productImg = List.img('<spring:message code="product"/>', $index, 'openProduct("'+$index+'")');
		$qty = List.get('<input type="text" class="input-decimal" size="6"/>', 'quantity['+$index+']', '0.00');
		$weight = List.get('<input type="text" class="input-decimal weight" onchange='+"checkWeight(this);"+' size="6"/>', 'weight['+$index+']', '0.00');
		$uom = List.get('<input type="text" class="input-disabled" disabled size="6" />', 'uom['+$index+']');
		$type = List.get('<input type="hidden" class="input-disabled" size="6" />', 'materialType['+$index+']', body == "outputItem" ? "OUTPUT" : "WIP");
		
		$tr.append(List.col([$cbox]));
		$tr.append(List.col([$code, $type]));
		$tr.append(List.col([$product, $productImg]));
		
		if (body == "outputItem")
			$tr.append(List.col([$weight]));
		
		if (body == "wipItem")
		{
			$tr.append(List.col([$qty]));
			$tr.append(List.col([$uom]));
		}
		
		$tbody.append($tr);
		index++;
		
		$(".input-decimal").bind(inputFormat);
	}
	
	List.getCost = function(gen, id, value)
	{
	    $obj = $(gen);
	    $obj.attr('id', id);

	    var start = id.indexOf('[');
	    var idex = id.substring(start+1, id.length-1);
	    var pram = id.substring(0, start);

	    if(start > 0) {
	        if(!$obj.attr('disabled'))
	            $obj.attr('name', 'costCenterGroupProductions['+idex+'].'+pram);
	        
	        $obj.attr('index', idex);
	        $obj.attr('next', pram);
	    }

	    if(value)
	        $obj.attr('value', value);

	    return $obj;
	}

	function openProduct(index) {
		const baseUrl = '<c:url value="/page/popupproductview.htm"/>';
		const params = {
			target: 'product['+index+']',
			index: index,
			status: true
		};
	
		openpopup(buildUrl(baseUrl, params));
	}
	
	function openCostGroup(index) {
		const baseUrl = '<c:url value="/page/popupcostcentergroupview.htm"/>';
		const params = {
			target: 'costCenterGroup['+index+']',
			index: index,
			status: true
		};
	
		openpopup(buildUrl(baseUrl, params));
	}

	function checkDuplicate(element) 
	{
		// Memanggil String.duplicate untuk mengecek duplikasi pada kelas 'productInput'
	   	var isDuplicated = String.duplicate('productInput');
	
	    if (isDuplicated) 
	    {
			alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
			$(element).closest('tr').remove();
		}
	}
</script>