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
		<td width="40%" align="right">
			<%@ include file="/common/welcome.jsp"%>
		</td>
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
					  		<a class="item-button-back" href="<c:url value='/page/userprofilepreedit.htm?id=${user_edit.profile.id}'/>"><span><spring:message code="sirius.back"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute='user_form'>
								<table style="border:none" width="100%">
								<tr>
	                                <td align="right"><spring:message code="user.id"/> :</td>
	                                <td>
	                                    <form:select id="user" path="user" cssClass="combobox">
	                                    	<c:if test='${not empty user_edit}'>
	                                        	<form:option value='${user_edit.id}' label='${user_edit.username}'/>
	                                        </c:if>
	                                    </form:select>
	                                </td>							
	                            </tr>
								<tr>
	                                <td align="right"><spring:message code="user.name"/> :</td>
	                                <td>
	                                    <form:select id="person" path="person" cssClass="combobox">
	                                    	<c:if test='${not empty user_edit.person}'>
	                                        	<form:option value='${user_edit.person.id}' label='${user_edit.person.fullName}'/>
	                                        </c:if>
	                                    </form:select>
	                                </td>							
	                            </tr>
	                            <tr>
	                                <td align="right"><spring:message code="user.role"/> :</td>
	                                <td >
	                                    <form:select id="role" path="role" cssClass="combobox">
	                                    	<c:if test='${not empty user_edit.role}'>
	                                        	<form:option value='${user_edit.role.id}' label='${user_edit.role.name}'/>
	                                        </c:if>
	                                    </form:select>
	                                </td>							
	                            </tr>
								<tr>
									<td width="24%" nowrap="nowrap" align="right"><spring:message code="password.old"/> :</td>
									<td width="76%">
										<input id="old" name="legend" class="masking"/>
										<a id="oldimg" href="javascript:hideseek('old');"  class="item-show-password"/>
									</td>
				    			</tr>	
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="password.new"/> :</td>
									<td>
										<input id="typed" class="masking" />
										<a id="typedimg" href="javascript:hideseek('typed');" class="item-show-password"/>
									</td>							
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="password.retype"/> :</td>
									<td>
										<input id="retyped" name="reason" class="masking" />
										<a id="retypedimg" href="javascript:hideseek('retyped');"  class="item-show-password"/>
									</td>
								</tr>
								</table>
							</sesform:form>
						</div>
						<div class="info" style="display: none;"><spring:message code="sirius.createdby"/> : <c:out value='${user_edit.createdBy.fullName}'/> (<fmt:formatDate value='${user_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${user_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${user_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="user"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(e){
			let old = document.getElementById('old');
			let typed = document.getElementById('typed');
			let retyped = document.getElementById('retyped');
			
			if(old.value == "" || typed.value == "" || retyped.value == '')
			{
				alert('<spring:message code="password.old"/>/<spring:message code="password.new"/>/<spring:message code="password.retype"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			if(typed.value != retyped.value)
			{
				alert('<spring:message code="password.new"/> <spring:message code="notif.different"/> <spring:message code="password.retype"/> !!!');
				return;
			}
			
			old.value = Base64.encode(old.value);
			retyped.value = Base64.encode(retyped.value);

			$.ajax({
				url:"<c:url value='/page/useredit.htm'/>",
				data:$('#addForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');

							window.location="<c:url value='/page/userprofilepreedit.htm'/>";
						}
						else
						{
							old.value = "";
							typed.value = "";
							retyped.value = "";
							
							afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				}
			});		
		});
	});
</script>