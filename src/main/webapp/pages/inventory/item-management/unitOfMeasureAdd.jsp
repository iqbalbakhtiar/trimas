<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>	
	<%@ include file="/common/sirius-header.jsp"%>
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
						
						<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/uomview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test="${access.add}">
								<a class="item-button-save" style="cursor: pointer;"><span><spring:message code="sirius.save"/></span></a>
							</c:if>
					 	</div>
					  
						<div class="main-box">
							<sesform:form id="uomForm" name="uomForm" method="post" modelAttribute="unitOfMeasure_add">
 							<table width="100%" border="0" cellpadding="3" cellspacing="0">
 							<tr>
 								<td width="19%" align="right"><spring:message code="uom.code"/></td>
                              	<td width="1%" align="center">:</td>
							  	<td width="80%"><form:input id="uomId" path="measureId" cssClass="inputbox" maxlength="15"/></td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="uom.name"/></td>
                                <td align="center">:</td>
								<td><form:input id="uomName" path="name" cssClass="inputbox" maxlength="30"/></td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="uom.type"/></td>
                                <td align="center">:</td>
								<td>
	    							<form:select path="type" cssClass="combobox">
		    							<c:forEach items="${types}" var="type">
		    								<spring:message code="uom.${fn:toLowerCase(type)}" var="unit"/>
	                                        <form:option value="${type}" label='${unit}'/>
	                                    </c:forEach>
                                    </form:select>
								</td>
							</tr>
 							<tr>
								<td align="right"><spring:message code="uom.packaging"/></td>
								<td align="center">:</td>
								<td>
									<form:radiobutton path="pack" value="true"/><spring:message code="sirius.yes"/>
									<form:radiobutton path="pack" value="false"/><spring:message code="sirius.no"/>
								</td>
 							</tr>
 							</table>
 							</sesform:form>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="uom"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/uomadd.htm'/>",
				data:$('#uomForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/uomview.htm'/>";
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				},
				error : function(xhr) {
					console.log(xhr.responseText);
				},
			});		
		});
	});
</script>