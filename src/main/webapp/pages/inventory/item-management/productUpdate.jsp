<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/productview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
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
	</table>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${product_edit.createdBy.fullName}'/> (<fmt:formatDate value='${product_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${product_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${product_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
$(function(){
	$('#code').focus();

	var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

	$('.item-button-save').click(function(){
		save();
	});

	function save() {
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
						
						let url = "<c:url value='/page/productpreedit.htm?id='/>"+json.id;

						if($("#purchasePlan").val())
							url += "&purchasePlan="+$("#purchasePlan").val()+"&converted=true";
						
						window.location=url;
					}
					else
							afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		};
		
		xhr.send(new FormData($('#editForm')[0]));
	}
});
</script>