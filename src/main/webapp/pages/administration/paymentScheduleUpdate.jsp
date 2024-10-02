<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" /><span><spring:message code="sirius.back"/></span></a>
						<c:if test='${access.add}'>
							<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
						</c:if>
					</div>					  
					<div class="main-box">
						<sesform:form id='addForm' name="addForm" method="post" modelAttribute="paymentSchedule_edit">
						<table style="border:none" width="100%">
                        <tr>
							<td align="right"><spring:message code="payment.schedule"/> :</td>
							<td><form:textarea path='schedule' cols='55' rows='6'/></td>
						</tr>
 						</table>
						</sesform:form>
					</div>
				<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	$('.item-button-save').click(function(){
		if(!$('#schedule').val())
		{
			alert('Schedule cannot be empty!');
			return;
		}
		
		save();
		
	});
	
	$('.item-button-back').click(function()
	{
		<c:if test='${empty redirectURL}'>
			window.location="<c:url value='/page/partypreedit.htm?id=${paymentSchedule_add.party.id}'/>";
		</c:if>
		<c:if test='${not empty redirectURL}'>
			window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=payment'/>";
		</c:if>
	});
});

function save() {
	$.ajax({
		url:"<c:url value='/page/paymentscheduleedit.htm'/>",
		data:$('#addForm').serialize(),
		type : 'POST',
		dataType : 'json',
		beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
		success : function(json) {
			if(json)
			{
				if(json.status == 'OK')
				{
					$dialog.dialog('close');
					<c:if test='${empty redirectURL}'>
						window.location="<c:url value='/page/partypreedit.htm?id=${paymentSchedule_add.party.id}'/>";
					</c:if>
					<c:if test='${not empty redirectURL}'>
						window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=payment'/>";
					</c:if>
				}
				else
						afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
			}
		}
	});
}
</script>
