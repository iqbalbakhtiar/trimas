<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/costcenterview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="costcenter_add" enctype="multipart/form-data">
	<table width="100%" border="0" >
	<tr>
		<td width="24%" align="right"><spring:message code="costcenter.id"/> :</td>
  	  	<td width="56%">
	  	  	<input id="costCenterId" value="Auto Generated" disabled="true" class='input-disabled'/>
	  	</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="costcenter.name"/> :</td>
		<td nowrap="nowrap">
			<form:input id='name' path="name" size="43"/>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.type"/> :</td>
		<td>
			<form:select path='type' id="type">
			    <form:options items='${types}'/>
			</form:select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.note"/> :</td>
		<td><form:textarea path="note" rows="6" cols="45"/></td>
	</tr>
	</table>
</sesform:form>
</div>
				
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
		xhr.open('POST', "<c:url value='/page/costcenteradd.htm'/>");
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
						
						let url = "<c:url value='/page/costcenterpreedit.htm?id='/>"+json.id;;
						
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
</script>