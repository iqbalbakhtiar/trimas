<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/costcentergroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="costgroup_add" enctype="multipart/form-data">
	<table width="100%" border="0" >
	<tr>
		<td width="24%" align="right"><spring:message code="costgroup.id"/> :</td>
  	  	<td width="56%">
	  	  	<input id="costCenterId" value="Auto Generated" disabled="true" class='input-disabled'/>
	  	</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="costgroup.name"/> :</td>
		<td nowrap="nowrap">
			<form:input id='name' path="name" size="43"/>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="uom"/> :</td>
		<td>
			<form:select id="unitOfMeasure" path="unitOfMeasure" cssClass="combobox">
				<form:options items="${uoms}" itemValue="id" itemLabel="measureId" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.note"/> :</td>
		<td><form:textarea path="note" rows="6" cols="45"/></td>
	</tr>
	</table>
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
		<div id="costCenterLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.code"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="costcenter.name"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="product.type"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.unitcost"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
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

	var $index = 0;
	$('#code').focus();

	var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

	$('.item-button-save').click(function(){
		save();
	});
	
	$('.item-button-new').click(function() {
		addLine($index);
		$index++;
	});
	
	$('.checkall').click(function () {
        $('.check').prop("checked", this.checked);
    });
	
	$('.item-button-delete').click(function () {
        $('.check').each(function(){
            if(this.checked){
                this.checked = false;
                $(this).parent().parent().remove();
                updateDisplay();
            }
        });
        $('.checkall').prop("checked", false);
    });

	function save() {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', "<c:url value='/page/costcentergroupadd.htm'/>");
		xhr.responseType = 'json';
		
		if(xhr.readyState == 1)
		{
			$dialog.empty();
			$dialog.html('<spring:message code="notif.saving"/>');
			$dialog.dialog('open');
		}
		
		xhr.onreadystatechange = function () {
			if(xhr.readyState == 4)
			{
				var json = xhr.response;
				if(json)
				{
					if(json.status == 'OK')
					{
						$dialog.dialog('close');
						
						let url = "<c:url value='/page/costcentergrouppreedit.htm?id='/>"+json.id;;
						
						window.location=url;
					}
					else
							afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		};
		
		xhr.send(new FormData($('#addForm')[0]));
	}
});

function addLine($index) {

	$tbody = $('#lineItem');
    $tr = $('<tr/>');
    
	$cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);
	
	$code = List.get('<input type="text" class="input-disabled" disabled size="30"/>','code['+$index+']');
	
	$costCenter = List.get('<select class="combobox-ext costCenter"/>','costCenter['+$index+']');
	$costCenterImg = List.img('<spring:message code="costcenter"/>', $index, 'openCostCenter("'+$index+'")');
	
	$type = List.get('<input type="text" class="input-disabled" disabled size="22"/>','type['+$index+']');
	
	$unitCost = List.get('<input type="text" class="input-number" size="22"/>','unitCost['+$index+']', '0.00');
	
	$tr.append(List.col([$cbox]));
	$tr.append(List.col([$code]));
	$tr.append(List.col([$costCenter, $costCenterImg]));
	$tr.append(List.col([$type]));
	$tr.append(List.col([$unitCost]));

	$tbody.append($tr);
	
	$(".input-number").bind(inputFormat);
	
}

function openCostCenter(index) {
	const baseUrl = '<c:url value="/page/popupcostcenterview.htm"/>';
	const params = {
		target: 'costCenter[' + index + ']', // Id Dropdown (Select) element
		index: index,
		status: true // Filter Only Active Products
	};
	openpopup(buildUrl(baseUrl, params));
}

</script>