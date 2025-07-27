<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/brandview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="brand_form" enctype="multipart/form-data">
		<table width="100%" border="0" >
			<tr>
				<td width="24%" align="right"><spring:message code="brand.code"/> :</td>
				<td width="56%">
					<form:input id="code" path="code" disabled="true" cssClass='input-disabled'/>
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="brand.name"/> :</td>
				<td nowrap="nowrap">
					<form:input id='name' path="name" size="30"/>
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="sirius.note"/> :</td>
				<td><form:textarea path="note" rows="6" cols="45"/></td>
			</tr>
		</table>
	</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${brand_edit.createdBy.fullName}'/> (<fmt:formatDate value='${brand_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${brand_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${brand_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
	$(function(){
		$('#name').focus();
		
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

		$('.item-button-save').click(function(){
			if(validation())
				save();
		});

		function save() {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', "<c:url value='/page/brandedit.htm'/>");
			xhr.responseType = 'json';

			if(xhr.readyState == 1)
			{
				$dialog.empty();
				$dialog.html('<spring:message code="notif.updating"/>');
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

							let url = "<c:url value='/page/brandpreedit.htm?id='/>"+json.id;;

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

	function validation()
	{
		if(!$('#name').val())
		{
			alert('<spring:message code="brand.name"/> <spring:message code="notif.empty"/> !');
			return false;
		}

		return true;
	}
</script>