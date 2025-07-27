<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<c:if test="${empty product_edit.parent}"><a class="item-button-list" href="<c:url value='/page/productview.htm'/>"><span><spring:message code="sirius.list"/></span></a></c:if>
	<c:if test="${not empty product_edit.parent}"><a class="item-button-back" href="<c:url value='/page/productpreedit.htm?id=${product_edit.parent.id}'/>"><span><spring:message code="sirius.back"/></span></a></c:if>
	<c:if test='${access.edit}'>
		<a class="item-button-save" value="${product_edit.id}" ><span><spring:message code="sirius.save"/></span></a>								
	</c:if>
</div>

<div class="main-box">
<sesform:form id="editForm" name="editForm" method="post" modelAttribute="product_form" enctype="multipart/form-data">
	<table width="100%" border="0" >
	<tr>
		<td width="24%" align="right"><spring:message code="product.id"/> :</td>
  	  	<td width="56%">
	  	  	<form:input id='code' path="code" disabled='true' class='input-disabled'/>
	  	</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.name"/> :</td>
		<td nowrap="nowrap">
			<form:input id='name' path="name" size="43" disabled='true' class='input-disabled'/>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.type"/> :</td>
		<td>
			<form:select path='productType' id="type" cssClass="input-disabled" disabled='true'>
			    <form:options value='${product_edit.productType}' label="${product_edit.productType}"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.category"/> :</td>
		<td>
			<form:select id="productCategory" path="productCategory" cssClass="combobox-ext input-disabled" disabled='true'>
			    <c:if test='${not empty product_edit.productCategory}'>
					<form:option value='${product_edit.productCategory.id}' label='${product_edit.productCategory.name}'/>
			    </c:if>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="brand"/> :</td>
		<td>
			<form:select id="brand" path="brand" cssClass="combobox-ext input-disabled" disabled='true'>
				<c:if test='${not empty product_edit.brand}'>
					<form:option value='${product_edit.brand.id}' label='${product_edit.brand.name}'/>
				</c:if>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="uom"/> :</td>
		<td>
			<form:select id="unitOfMeasure" path="unitOfMeasure" cssClass="combobox input-disabled" disabled='true'>
				<c:if test='${not empty product_edit.unitOfMeasure}'>
					<form:option value='${product_edit.unitOfMeasure.id}' label='${product_edit.unitOfMeasure.measureId}'/>
			    </c:if>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.saleable"/> :</td>
		<td>
			<form:radiobutton path="saleable" value="true"/><spring:message code="sirius.yes"/>
			<form:radiobutton path="saleable" value="false"/><spring:message code="sirius.no"/>	
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.status"/> :</td>
		<td>
			<form:radiobutton path="enabled" value="true"/><spring:message code="sirius.active"/>
			<form:radiobutton path="enabled" value="false"/><spring:message code="sirius.inactive"/>	
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.serial"/> :</td>
		<td>
			<form:radiobutton path="serial" value="true" disabled="true"/><spring:message code="sirius.yes"/>
			<form:radiobutton path="serial" value="false" disabled="true"/><spring:message code="sirius.no"/>	
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.picture"/> :</td>
		<td>
			<input name="file" type="file" src="${product_edit.picture}"/>
			<c:if test='${product_edit.picture != null}'>
			&nbsp;&nbsp;&nbsp;<a href="javascript:openpopup('<c:url value='/page/productshowimage.htm?id=${product_edit.id}'/>');"><spring:message code="sirius.show"/></a>
			</c:if>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.note"/> :</td>
		<td><form:textarea path="note" rows="6" cols="45"/></td>
	</tr>
	<tr style="display: none;">
		<td colspan="3">
			<select id="uom">
				<c:forEach items='${uoms}' var='uom'>
				<option value='${uom.id}' base="false">${uom.measureId}</option>
			</c:forEach>	
			</select>
		</td>	
	</tr>
	</table>
	<br/>
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 350px;">
	<c:if test="${product_edit.base}">
		<div id="packaging" dojoType="ContentPane" label="<spring:message code="product.variant"/>" class="tab-pages" refreshOnShow="true" selected="true">
			<div class="toolbar-clean">
			<c:if test='${access.add}'>
   				<a class="item-button-new" target="package" style="cursor: pointer;"><span><spring:message code="sirius.row.new"/></span></a> 
        		<a class="item-button-delete" target="package" style="cursor: pointer;"><span><spring:message code="sirius.row.delete"/></span></a>
			</c:if>
			</div>
			<table class="table-list" id="varia" cellspacing="0" cellpadding="0" align="center" width="100%">
			<thead>
		    <tr>
				<th width="1%"><input class="checkall" type="checkbox"/></th>
				<th nowrap="nowrap" width="10%"><spring:message code="product.code"/></th>
				<th nowrap="nowrap" width="15%"><spring:message code="product.name"/></th>
				<th nowrap="nowrap" width="5%"><spring:message code="product.uom"/></th>
				<th nowrap="nowrap" width="75%"><spring:message code="product.variant"/></th>
		    </tr>
			</thead>
			<tbody id="iBody">
			<c:forEach items='${product_edit.variants}' var='prd' varStatus='status'>
			<tr>
				<td class="tools"><a class="item-button-edit" href="<c:url value='/page/productpreedit.htm?id=${prd.id}'/>" title="<spring:message code="sirius.edit"/>"><span><spring:message code="sirius.edit"/></span></a></td>
				<td><input readonly='true' value="${prd.code}" size="20" class='input-disabled'/></td>
				<td><input readonly='true' value="${prd.name}" size="50" class='input-disabled product'/></td>
				<td>
					<select class="combobox-min3 input-disabled" readonly="true">
						<option value='${prd.unitOfMeasure.id}'>${prd.unitOfMeasure.measureId}</option>
					<select>
				</td>
				<td>&nbsp;</td>
			</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
			</tfoot>
			</table>
		</div>
	</c:if>
	</div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${product_edit.createdBy.fullName}'/> (<fmt:formatDate value='${product_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${product_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${product_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
$(function(){
	$('#code').focus();

	var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

	$('.item-button-save').click(function(){
		$('[type="checkbox"]').each(function(){
			$id = $(this).attr('class');
			$idx = $(this).attr('index');

			if($id && $idx)
				$('#'+$id+'\\['+$idx+'\\]').val($(this).prop('checked'));
		});

		if(validation()) {
			$id = $(this).attr('value');
			var xhr = new XMLHttpRequest();
			xhr.open('POST', "<c:url value='/page/productedit.htm'/>");
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
							window.location="<c:url value='/page/productpreedit.htm?id='/>"+$id;
						}
						else
							afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			};
			
			xhr.send(new FormData($('#editForm')[0]));
		}
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

function validation()
{
	var next = true;
	
	var duplicate = checkDuplicates('colours');
	if(duplicate) {
		alert('<spring:message code="productcolour"/> ('+duplicate+') <spring:message code="notif.duplicate"/> !!!');
		next = false;
			
		return false;
	}
	
	return next;
}

function checkDuplicates(value) 
{
  	var $elems = $('.'+value);

  	var values = [];
  	var isDuplicated = "";

  	$elems.each(function () {
	    if(!this.value) return true;

	    if(values.indexOf(this.value) !== -1) {
	       isDuplicated = this.innerText;
	       return false;
	    }
    	values.push(this.value);
  	});   
 
	return isDuplicated;     
}

var $index = 0;
$('.item-button-new').click(function(){
	$tbody = $('#iBody');

    $tr = $('<tr/>');
    $cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);
    $code = List.get('<input disabled class="input-disabled" size="20" readonly="true"/>','productCode'+$index, '<spring:message code="sirius.auto.generated"/>');
    $name = List.get('<input class="input-disabled" size="50" readonly="true"/>','productName'+$index, '<spring:message code="sirius.auto.generated"/>');
    
    $uom = List.get('<select class="combobox-min3 uom"/>','uom['+$index+']');
    $('#uom').find('option[base="false"]').clone().appendTo($uom);
    
    $variant = List.get('<input size="50"/>','variantName['+$index+']');

    $tr.append(List.col([$cbox]));
    $tr.append(List.col([$code]));
    $tr.append(List.col([$name]));
    $tr.append(List.col([$uom]));
    $tr.append(List.col([$variant]));        

    $tbody.append($tr);

    $index++;
});
</script>