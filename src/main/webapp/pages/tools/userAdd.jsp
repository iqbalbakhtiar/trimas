<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
							<a class="item-button-list" href="<c:url value='/page/userview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  	
						<div class="main-box">			
							<sesform:form name="addForm" id="addForm" method="post" modelAttribute="user_form">
							<table width="100%" style="border:none">
					 		<tr>
                                <td width="20%" align="right"><spring:message code="user.id"/></td>
                                <td width="3%">:</td>
                                <td width="77%" valign="top">
                                	<form:input cssClass="inputbox" id="username" path="username" size="35" maxlength="15"/>
                                </td>
                            </tr>
                            <tr>
								<td align="right"><spring:message code="password"/></td>
								 <td width="3%">:</td>
						  	  	<td>
							  	  	<form:input id="password" path="code" cssClass="masking inputbox-medium"/>
							  	  	<a id="passwordimg" class="item-show-password" onclick="hideseek('password');"/>
						  	  	</td>
							</tr>
                            <tr>
                                <td align="right"><spring:message code="party.personal"/></td>
                                <td width="3%">:</td>
                                <td >
                                    <form:select id="person" path="person" cssClass="combobox">
                                        <c:forEach items="${persons}" var="person">
                                            <option value="${person.id}">${person.fullName}</option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="user.role"/></td>
                                <td width="3%">:</td>
                                <td >
                                    <form:select id="role" path="role" cssClass="combobox">
                                    	<form:options items='${roles}' itemLabel='name' itemValue='id'/>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" align="right"><spring:message code="sirius.status"/></td>
                                <td width="3%">:</td>
                                <td valign="top">
                                	<form:radiobutton path="enabled" value="true"/><spring:message code="sirius.active"/>
									<form:radiobutton path="enabled" value="false"/><spring:message code="sirius.inactive"/>
								</td>
                            </tr>
                            <tr>
                            	<td colspan="3">&nbsp;</td>
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
<script type="text/javascript" src="js/webtoolkit.base64.js"></script>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});	
		$('.item-button-save').click(function(){
			if(!$("#username").val()) {
				alert('<spring:message code="user.id"/> <spring:message code="notif.empty"/> !!!');
				return;
			}

			let old = document.getElementById('password');
			old.value = Base64.encode(old.value);

			$.ajax({
				url:"<c:url value='/page/useradd.htm'/>",
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
							window.location="<c:url value='/page/userpreedit.htm?id='/>"+json.id;
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