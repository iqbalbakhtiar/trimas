<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/costcentergroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="editForm" name="editForm" method="post" modelAttribute="costgroup_edit" enctype="multipart/form-data">
	<table width="100%" border="0" >
	<tr>
		<td width="24%" align="right"><spring:message code="costgroup.id"/> :</td>
  	  	<td width="56%">
	  	  	<form:input id='code' path="code" disabled='true' class='input-disabled'/>
	  	</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="costgroup.name"/> :</td>
		<td nowrap="nowrap">
			<form:input id='name' path="name" size="43" class='input'/>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="uom"/> :</td>
		<td>
			<form:select id="unitOfMeasure" path="unitOfMeasure" cssClass="combobox input-disabled" disabled='true'>
				<c:if test='${not empty costgroup_edit.unitOfMeasure}'>
					<form:option value='${costgroup_edit.unitOfMeasure.id}' label='${costgroup_edit.unitOfMeasure.measureId}'/>
			    </c:if>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.note"/> :</td>
		<td><form:textarea path="note" rows="6" cols="45"/></td>
	</tr>
	</table>
	
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.code"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="costcenter.name"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="product.type"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.unitcost"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${costgroup_edit.items}" var="item" varStatus="idx">
						<tr>
							<td>
								<input size="26" value="${item.costCenter.code}" class="input-disabled" index="${idx.index}" disabled/>
							</td>
							<td>
								<input id="costCenter[${idx.index}]" size="26" value="${item.costCenter.name}" class="input-disabled" name="items[${idx.index}].costCenter" index="${idx.index}" disabled/>
							</td>
							<td>
								<input size="26" value="${item.costCenter.type}" class="input-disabled" index="${idx.index}" disabled/>
							</td>
							<td>
								<input id="unitCost[${idx.index}]" size="26" value="<fmt:formatNumber value='${item.unitCost}' pattern='#,##0.00'/>" class="number-disabled" name="items[${idx.index}].unitCost" index="${idx.index}" disabled/>
							</td>
						</tr>
					</c:forEach>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${machine_edit.createdBy.fullName}'/> (<fmt:formatDate value='${product_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${product_form.updatedBy.fullName}'/> (<fmt:formatDate value='${machine_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
		xhr.open('POST', "<c:url value='/page/costcentergroupedit.htm'/>");
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
						
						let url = "<c:url value='/page/costcentergrouppreedit.htm?id='/>"+json.id;
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