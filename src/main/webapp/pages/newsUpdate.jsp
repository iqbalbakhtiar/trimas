<%@ include file="/common/sirius-general-top.jsp"%>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/newsView.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  
						<div class="main-box">
							<div class="area" dojoType="Container" id="quick_link_container">
								<sesform:form id="expeditionAddForm" name="expeditionAddForm" method="post" modelAttribute="news">
 								<table width="100%" style="border:none">
								<tr>
									<td width="12%"><div class="form-label"><spring:message code="news.date"/> :</div></td>
									<td width="50%"><input class="input-disabled" disabled size="10" value="<fmt:formatDate value='${news.date}' pattern='dd-MM-yyyy'/>"/></td>		
	    							<td width="38%" align="left"><div class="error" id="exId-error">&nbsp;</div></td>
								</tr>	
								<tr>
									<td><div class="form-label"><spring:message code="news.title"/> :</div></td>
									<td><div class="form-value"><input name="title" id="title" class="inputbox" size="60" value="${news.title}" /></div></td>			
	    							<td><div class="error" id="name-error">&nbsp;</div></td>
								</tr>
								<tr>
									<td><div class="form-label"><spring:message code="news.content"/> :</div></td>
									<td colspan="2">
										<div class="form-value">
											<textarea id="content" name="content" class="mceEditor" cols="50" rows="7" wrap="hard" class="inputbox">${news.content}</textarea>
										</div>
									</td>		
								</tr>
								</table>
 								</sesform:form>
 							</div>
						</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	function save()
	{
		if(!$('#title').val())
		{
			alert('<spring:message code="news.title"/> <spring:message code="notif.empty"/> !!!');
			return;
		}

		if(!$('#content').val())
		{
			alert('<spring:message code="news.content"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		document.expeditionAddForm.action = "<c:url value='/page/newsUpdate.htm?id=${news.id}'/>";
		document.expeditionAddForm.submit();
	}
</script>