<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" +request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title }</title>
	<%@ include file="/common/sirius-header.jsp" %>
    	<style type="text/css" media="screen">
    @import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/css/jquery-ui-1.8.2.custom.css'/>");
    </style>
</head>

<body onload="error();">

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

	<%@ include file="/common/sirius-menu.jsp"%>

	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb }</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle }</h1>
						
					  	<div class="toolbar">
                   <a class="item-button-list" href="<c:url value='/page/approvaloverview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="approval">
								<table width="100%" border="0">
                       <c:if test="${not empty person and approvalDecision.approvalDecisionStatus != 'APPROVE_AND_FINISH' and approvalDecision.approvalDecisionStatus != 'REJECTED'}">
                        <tr>
                              <td><%@ include file="/common/approval-history.jsp" %></td>
                        </tr>
                        <tr>
                              <td><%@ include file="/common/approval.jsp" %></td>
                        </tr>
                        <tr>
                          <td>
                              <fieldset>
                                  <legend><strong><spring:message code="app.approval.decision"/></strong></legend>
                                  <table width="100%" style="border:none">
                                  <tr>
                                          <td nowrap="nowrap" align="right"><spring:message code="app.company"/> </td>
                                          <td>:</td>
                                          <td>
                                                <select id="org" name="organization" class="combobox-ext" disabled='true'>
                                                        <option value='${approval.organization.id}'/>${approval.organization.firstName} ${approval.organization.lastName} ${approval.organization.middleName}</option>
                                                <select>
                                          </td>
                                  </tr>
                                  <tr>
                                      <td align="right"><spring:message code="app.decision"/></td>
                                      <td>:</td>
                                      <td>
                                          <select id="decision" name="decision" class="combobox">
                                              <option value="FORWARD" onclick="on();">FORWARD</option>
                                          </select>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td align="right"><spring:message code="app.current"/></td>
                                      <td>:</td>
                                      <td>
                                          <select id="current" name="current" class="combobox">
                                                <option value='${approval.approvalDecision.forwardTo.id}'/>${approval.approvalDecision.forwardTo.firstName} ${approval.approvalDecision.forwardTo.lastName} ${approval.approvalDecision.forwardTo.middleName}</option>
                                          <select>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td align="right"><spring:message code="app.new"/></td>
                                      <td>:</td>
                                      <td>
                                          <select id="forwardTo" name="forwardTo" class="combobox">
                                          </select>
                                          &nbsp;
                                          <img id="f_img" src="assets/icons/list_extensions.gif" onclick="openapprover();"  title="Approver" />
                                      </td>
                                  </tr>
                                  <tr>
                                      <td align="right"><spring:message code="app.remark"/></td>
                                      <td>:</td>
                                      <td valign="top"><textarea id="remark" name="remark" cols="45" rows="3" value="${approvalDecision.remark}"></textarea></td>
                                  </tr>
                                  <tr>
                                      <td>&nbsp;</td>
                                      <td>&nbsp;</td>
                                      <td><img onclick="changeStatus();" src="<c:url value='/assets/images/btn_process.gif'/>"/></td>
                                  </tr>
                                  </c:if>
                                  </table>
                              </fieldset>
                          </td>
                      </tr>
                  </table>
							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/>: <c:out value='${salesOrder_edit.createdBy.firstName} ${salesOrder_edit.createdBy.middleName} ${salesOrder_edit.createdBy.lastName}'/> (<fmt:formatDate value='${salesOrder_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${salesOrder_edit.updatedBy.firstName} ${salesOrder_edit.updatedBy.middleName} ${salesOrder_edit.updatedBy.lastName}'/> (<fmt:formatDate value='${salesOrder_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>	
					</div>
				</div>
			</div>
		</div>
	</div>

  	<div style="clear:both;height:0px">&nbsp;</div>
	<%@ include file="/common/sirius-footer.jsp"%>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>
</body>
</html>
<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
<script type="text/javascript">
	function openapprover()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupsalesapproverview.htm?target=forwardTo&organization='/>"+org.value);
	}
	
	function error()
	{
		<c:if test='${not empty message}'>
			alert('${message}');
		</c:if>
	}
</script>