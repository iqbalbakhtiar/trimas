<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/productionorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="request_add" enctype="multipart/form-data">
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
								<form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
									<form:option value='${detail.productionOrder.organization.id}' label='${detail.productionOrder.organization.fullName}'/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorderdetail"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<input class="input-disabled" value="${detail.code}" disabled/>
								<form:input type="hidden" id="productionOrderDetail" path="productionOrderDetail" class="input-disabled" value="${detail.id}"/>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="machine"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="machine" path="machine" class="combobox-ext"/>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupmachineview.htm?target=machine&status=true'/>');" title="<spring:message code="machine"/>" />
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehousefrom"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="source" path="source" cssClass="combobox-ext"/>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=source'/>');" title="<spring:message code="transferorder.warehousefrom"/>" />
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehouseto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="destination" path="destination" cssClass="combobox-ext"/>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=destination'/>');" title="<spring:message code="transferorder.warehouseto"/>" />
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.containerto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="container" path="container" cssClass="combobox-ext"/>
								<a class="item-popup" onclick="openContainer();" title="<spring:message code="transferorder.containerto"/>" />
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="transferorder.gridto"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="grid[0]" class="combobox-ext input-disabled" disabled/>
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
				</td>
	    	</tr>
	    </table>
    	<br/>
    	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
			<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<div class="toolbar-clean">
						<a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
						<a class="item-button-delete" body="cost"><span><spring:message code="sirius.row.delete"/></span></a>
						<div class="item-navigator">&nbsp;</div>
					</div>
					<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="product.code"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="product"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="transferorder.containerfrom"/></th>
								<th width="12%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							</tr>
						</thead>
						<tbody id="lineItem">
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
			if (validation())
				save();
		});
	
		$('.item-button-new').click(function() {
			addLine($index);
	    });
	
		$('.checkall').click(function () {
		      $('.check').prop("checked", this.checked);
		    });
		
	    $('.item-button-delete').click(function () {
			$('.check').each(function(){
	        	if(this.checked) {
	          		this.checked = false;
	      			$(this).parent().parent().remove();
	        	}
			});
			
			$('.checkall').prop("checked", false);
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
	    
	    if (!$("#source").val())
	    {
	    	alert('<spring:message code="transferorder.warehousefrom"/> <spring:message code="notif.empty"/> !');
	    	return false;
	    }
	    
	    if (!$("#destination").val())
	    {
	    	alert('<spring:message code="transferorder.warehouseto"/> <spring:message code="notif.empty"/> !');
	    	return false;
	    }
	    
	    if (!$("#container").val())
	    {
	    	alert('<spring:message code="transferorder.containerto"/> <spring:message code="notif.empty"/> !');
	    	return false;
	    }

	    if ($('#lineItem tr').length === 0) {
			alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
			return false;
		}
		
		var isValid = true;
		$('.products').each((index, element) => {
			if (!$(element).val())
			{
				alert('<spring:message code="product"/> <spring:message code="notif.empty"/> !');
				isValid = false;
				return false;
			}
		});
		
		$('.quantities').each((index, element) => {
			if ($(element).val().toNumber() < 1)
			{
				alert('<spring:message code="product.quantity"/> <spring:message code="notif.greater.zero"/> !');
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
		    url:"<c:url value='/page/productionorderdetailmaterialrequestadd.htm'/>",
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
	
	function addLine(index) 
	{
		$tbody = $('#lineItem');
		$tr = $('<tr/>');
		
		$cbox = List.get('<input type="checkbox" class="check"/>','check'+index);
		$code = List.get('<input type="text" class="input-disabled" size="20" disabled/>', 'codeprod['+index+']');
		$product = List.get('<select class="combobox products"/>','product['+index+']');
		$productImg = List.img('<spring:message code="product"/>', index, 'openProduct("'+index+'")');
		$container = List.get('<select class="input-disabled combobox" readonly="true"/>','source['+index+']');
		$onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']');
		$qty = List.get('<input type="text" class="input-decimal quantities" size="8"/>','quantity['+index+']', '0.00');
		$uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+index+']');
		
		$tr.append(List.col([$cbox]));
		$tr.append(List.col([$code]));
		$tr.append(List.col([$product, $productImg]));
		$tr.append(List.col([$container]));
		$tr.append(List.col([$onHand]));
		$tr.append(List.col([$qty]));
		$tr.append(List.col([$uom]));
		
		$tbody.append($tr);
		
		$(".input-decimal").bind(inputFormat);
		
		$index++;
	}
	
	function openProduct(index) {
		$facility = $("#source").val();
		if (!$facility)
		{
			alert('<spring:message code="notif.fill"/> <spring:message code="transferorder.warehousefrom"/> <spring:message code="notif.field"/> !');
			return;
		}
		
		const baseUrl = '<c:url value="/page/popupproductfortransfer.htm"/>';
		const params = {
			target: 'product['+index+']',
			index: index,
			status: true,
			facility: $facility
		};
	
		openpopup(buildUrl(baseUrl, params));
	}
	
	function openContainer() {
		var destination = $('#destination').val();
		if (!destination)
		{
			alert('<spring:message code="notif.fill"/> <spring:message code="transferorder.warehouseto"/> <spring:message code="notif.field"/> !');
			return;
		}
		
		const baseUrl = '<c:url value="/page/popupcontainerview.htm"/>';
		const params = {
			target: 'container',
			index: 0,
			facility: destination
		};
	
		openpopup(buildUrl(baseUrl, params));
	}

</script>