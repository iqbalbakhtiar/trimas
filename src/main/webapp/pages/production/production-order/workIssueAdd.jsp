<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/productionorderdetailpreedit.htm?id=${work_add.productionOrderDetail.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="work_add" enctype="multipart/form-data">
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
							<td align="right"><spring:message code="workissue.reporteddate"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<input id="reportedDate" name="reportedDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" size="8"/>
								<input id="reportedTime" name="reportedTime" class="clockpicker" value="00:00" size="6"/>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="workissue.issue"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="issue" path="issue" size="35"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="workissue.solution"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="solution" path="solution" size="35"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="workissue.resolveddate"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<input id="resolvedDate" name="resolvedDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" size="8"/>
								<input id="resolvedTime" name="resolvedTime" class="clockpicker" value="00:00" size="6"/>
							</td>
						</tr>
					</table>
				</td>
				<td width="40%" valign="top">
				</td>
	    	</tr>
	    </table>
    	<br/>
	</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function(){
			if (!$("#reportedDate").val())
			{
				alert('<spring:message code="workissue.reporteddate"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if (!$("#resolvedDate").val())
			{
				alert('<spring:message code="workissue.resolveddate"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if (!$("#reportedTime").val())
			{
				alert('<spring:message code="workissue.reporteddate"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if (!$("#resolvedTime").val())
			{
				alert('<spring:message code="workissue.resolveddate"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if (!$("#issue").val())
			{
				alert('<spring:message code="workissue.issue"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			if (!$("#solution").val())
			{
				alert('<spring:message code="workissue.solution"/> <spring:message code="notif.empty"/> !');
				return;
			}
			
			$.ajax({
			    url:"<c:url value='/page/workissueadd.htm'/>",
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
			          		window.location="<c:url value='/page/workissuepreedit.htm?id='/>"+json.id;
			        	}
			        	else
			        	{
			          		$dialog.empty();
			          		$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
			        	}
					}
				}
			});
		});
	});
</script>