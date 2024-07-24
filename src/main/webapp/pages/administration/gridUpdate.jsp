<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp" %>
</head>
<body>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

	<%@ include file="/common/sirius-menu.jsp"%>

	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb}</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle}</h1>
                        <div class="clears">&nbsp;</div>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/facilitypreedit.htm?id=${grid.facility.id}'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.add}'>
								<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
							</c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="grid">
								<table width="100%" style="border:none">
								<tr>
									<td width="20%" nowrap="nowrap" align="right">Code :</td>
								  	<td width="80%"><form:input path='code' size="48" disabled='true' cssClass='input-disabled'/></td>
								</tr>
								<tr>
									<td width="20%" nowrap="nowrap" align="right">Name :</td>
								  	<td width="80%"><form:input path='name' size="48" cssClass='input-disabled' disabled='true'/></td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right">Note :</td>
									<td><form:textarea path="note" cols="55" rows="7"/></td>
								</tr>
							</table>
							</sesform:form>
                            <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 250px;">
                                <div id="container" dojoType="ContentPane" label="Container" class="tab-pages">
                                    <div class="toolbar-clean">
                                        <a class="item-button-new" href="<c:url value='/page/containerpreadd.htm?id=${grid.id}'/>"><span>New Container</span></a>
                                        <div class="item-navigator">&nbsp;</div>
                                    </div>
                                    <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                      <th width="1%"><div style="width:45px"></div></th>
                                        <th width="20%">Code</th>
                                        <th width="80%">Name</th>
                                    </tr>
                                    <c:forEach items="${grid.containers}" var="con">
                                    <tr>
                                        <td class="tools">
                                            <a class="item-button-edit" href="<c:url value='/page/containerpreedit.htm?id=${con.id}'/>" title="Edit"><span>Edit</span></a>
                                            <%-- <c:if test='${con.deletable}'>
												<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/containerdelete.htm?id=${con.id}'/>');" title="Del"><span>Del</span></a>
										    </c:if> --%>
                                        </td>
                                        <td>${con.code}</td> 
                                        <td>${con.name}</td>
                                    </tr>
                                    </c:forEach>
                                    <tr class="end-table"><td colspan="4">&nbsp;</td></tr>
                                    </table>
                                </div>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Grid',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(e){
			$.ajax({
				url:"<c:url value='/page/gridedit.htm'/>",
				data:$('#editForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/facilitypreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});		
		});
	});
</script>