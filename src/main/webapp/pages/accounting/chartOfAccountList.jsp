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
						<div dojoType="SplitContainer" style="width: 100%; height: 600px;" orientation="horizontal" sizerWidth="5" activeSizing="0"	layoutAlign="client">
								<div id="treePane" dojoType="Tree" widgetId="treePane" toggle="wipe" toggleDuration="500" sizeMin="20" sizeShare="3">
									<div dojoType="TreeNode" title="<spring:message code="coa"/>" expandLevel="2" onTitleClick='window.location = "page/chartofaccountview.htm";' childIconSrc="assets/icons/coa-parent.gif">
										<c:forEach items='${chartOfAccounts}' var='parent'>
											<div dojoType="TreeNode" onTitleClick='displayContent("page/chartofaccountpreedit.htm?id=${parent.id}");' title="${parent.name}" childIconSrc="assets/icons/group.gif">
                                            	<c:if test='${not empty parent.acounts}'>
												<c:forEach items="${parent.acounts}" var="account0">
													<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account0.id}&coa=${parent.id}");' title="${account0.code} ${account0.name}" childIconSrc="assets/icons/<c:if test="${account0.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account0.level == 'ACCOUNT'}">child.png</c:if>">
                                                    	<c:if test='${not empty account0.childrens}'>
														<c:forEach items="${account0.childrens}" var="account1">
															<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account1.id}&coa=${parent.id}");' title="${account1.code} ${account1.name}" childIconSrc="assets/icons/<c:if test="${account1.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account1.level == 'ACCOUNT'}">child.png</c:if>">
                                                            	<c:if test='${not empty account1.childrens}'>
																<c:forEach items="${account1.childrens}" var="account2">
																	<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account2.id}&coa=${parent.id}");' title="${account2.code} ${account2.name}" childIconSrc="assets/icons/<c:if test="${account2.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account2.level == 'ACCOUNT'}">child.png</c:if>">
                                                                    	<c:if test='${not empty account2.childrens}'>
																		<c:forEach items="${account2.childrens}" var="account3">
																			<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account3.id}&coa=${parent.id}");' title="${account3.code} ${account3.name}" childIconSrc="assets/icons/<c:if test="${account3.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account3.level == 'ACCOUNT'}">child.png</c:if>">
                                                                            	<c:if test='${not empty account3.childrens}'>
																				<c:forEach items="${account3.childrens}" var="account4">
																					<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account4.id}&coa=${parent.id}");' title="${account4.code} ${account4.name}" childIconSrc="assets/icons/<c:if test="${account4.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account4.level == 'ACCOUNT'}">child.png</c:if>">
                                                                                    	<c:if test='${not empty account4.childrens}'>
																						<c:forEach items="${account4.childrens}" var="account5">
																							<div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account5.id}&coa=${parent.id}");' title="${account5.code} ${account5.name}" childIconSrc="assets/icons/<c:if test="${account5.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account5.level == 'ACCOUNT'}">child.png</c:if>">
                                                                                            	<c:if test='${not empty account5.childrens}'>
                                                                                                <c:forEach items="${account5.childrens}" var="account6">
                                                                                                    <div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account6.id}&coa=${parent.id}");' title="${account6.code} ${account6.name}" childIconSrc="assets/icons/<c:if test="${account6.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account6.level == 'ACCOUNT'}">child.png</c:if>">
                                                                                                    	<c:if test='${not empty account6.childrens}'>
                                                                                                        <c:forEach items="${account6.childrens}" var="account7">
                                                                                                            <div dojoType="TreeNode" onTitleClick='displayContent("page/glaccountpreedit.htm?id=${account7.id}&coa=${parent.id}");' title="${account7.code} ${account7.name}" childIconSrc="assets/icons/<c:if test="${account7.level == 'ACCOUNTGROUP'}">group.gif</c:if><c:if test="${account7.level == 'ACCOUNT'}">child.png</c:if>">
                                                                                                            </div>
                                                                                                        </c:forEach>
                                                                                                        </c:if>
                                                                                                    </div>
                                                                                                </c:forEach>
                                                                                                </c:if>
																							</div>
																						</c:forEach>
                                                										</c:if>
																					</div>
																				</c:forEach>
                                                								</c:if>
																			</div>
																		</c:forEach>
                                                						</c:if>
																	</div>
																</c:forEach>
                                                				</c:if>
															</div>
														</c:forEach>
                                                		</c:if>
													</div>
												</c:forEach>
                                                </c:if>
											</div>
										</c:forEach>
									</div>
								</div>
								<div id="listPane" dojoType="ContentPane" style="padding: 5px; width:100% ; height:430px;" refreshOnShow="true" doLayout="true" overFlow="true">
                                	<div class="toolbar">
										<a class="item-button-add-gl-child" href="javascript:displayContent('page/chartofaccountpreadd.htm');"><span><spring:message code="coa.new"/></span></a>
									</div>
									<div class="main-box" style="height: 370px;">
										<form id="deleteForm" name="deleteForm" method="post">
											<table width="968" cellpadding="0" cellspacing="0" class="table-list" align="center">
											<tr>
												<th>&nbsp;</th>
												<th><spring:message code="coa.code"/></th>
												<th><spring:message code="coa.name"/></th>
												<th><spring:message code="coa.note"/></th>
											</tr>
											<c:forEach items="${chartOfAccounts}" var="coa">
											<tr>
												<td>
													<c:if test="${presentationLevel.delete}">
														<a href="javascript:submitDeleteCoa(${coa.id})"><img src="images/delete.gif" width="16" height="16" align="left" border="0"></a>
													</c:if>
												</td>
												<td><c:out value='${coa.code}'/></td>
												<td>${coa.name}</td>
												<td>${coa.note}</td>
											</tr>
											</c:forEach>
											<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
				  							</table>
				  						</form>
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
	function deletes(id)
	{
        var confirmation = confirm('<spring:message code="notif.delete"/>');
       	if(confirmation==true)
		{
       	    	var url = "<c:url value='/page/companyLevelDelete.htm'/>?id="+id;
       	    	window.location=url;
        }
    }
	function displayContent(url)
	{
		var listPane = dojo.widget.getWidgetById("listPane");
		listPane.setUrl(url);
	}

	function updateGL()
	{
  		document.glForm.action="<c:url value='/page/updateGL.htm'/>";
  		document.glForm.submit();
	}

	function deleteGL()
	{
  		var confirmation =confirm('<spring:message code="notif.delete"/>');
  		if(confirmation==true)
		{
    		document.glForm.action="<c:url value='/page/coaRemoveGl.htm'/>";
    		document.glForm.submit();
  		}
	}

	function submitDeleteCoa(id)
	{
  		var confirmation =confirm('<spring:message code="notif.delete"/>');
  		if(confirmation==true)
		{
			document.deleteForm.action = "<c:url value='/page/coaDelete.htm?id='/>"+id;
			document.deleteForm.submit();
  		}
  	}
	function validate(){
		var name=document.getElementById("name");
		var code=document.getElementById("code");
		var alias=document.getElementById("alias");
		var error=false;
		if(alias){
			if(alias.value==""){
				alert("<spring:message code='glaccount.alias'/> <spring:message code='notif.empty'/>");
				error=true;
			}
		}
		if(name){
			if(name.value==""){
				alert("<spring:message code='coa.name'/> <spring:message code='notif.empty'/>");
				error=true;
			}
		}
		if(code){
			if(code.value==""){
				alert("<spring:message code='coa.code'/> <spring:message code='notif.empty'/>");
				error=true;
			}
		}


		if(!error)
			document.addForm.submit();
		else
			return;
	}
</script>