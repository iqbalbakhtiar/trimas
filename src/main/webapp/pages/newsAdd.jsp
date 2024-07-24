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
							<td width="8%" nowrap="nowrap" align="right"><div align="right"><spring:message code="news.date"/> :</div></td>
							<td width="50%">
								<input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
							</td>		
						</tr>	
						<tr>
							<td nowrap="nowrap" align="right"><div align="right"><spring:message code="news.title"/> :</div></td>
							<td><div class="form-value"><input name="title" id="title" class="inputbox" size="60" value=""/></div></td>			
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><div align="right"><spring:message code="news.content"/> :</div></td>
							<td>
								<div class="form-value">
									<textarea id="content" name="content" cols="50" rows="7">     
										<c:out value='${data.news.content}'/>
									</textarea>								
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
		if(!$('#date').val())
		{
			alert('<spring:message code="news.date"/> <spring:message code="notif.empty"/> !!!');
			return;
		}

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
		
		document.expeditionAddForm.action = "<c:url value='/page/newsAdd.htm'/>";
		document.expeditionAddForm.submit();
	}
</script>	