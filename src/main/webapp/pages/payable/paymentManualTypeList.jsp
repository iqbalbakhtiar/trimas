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
	<%@ include file="/filter/payable/paymentManualTypeFilter.jsp"%>
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
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                            <tr>
                            	<td width="35%" height="30" align="left" valign="middle">															
                            		<div class="toolbar-clean">
                                		<a class="item-button-new" href="<c:url value='/page/paymentmanualtypepreadd.htm'/>"><span><spring:message code="paymenttype.new"/></span></a>
                                		<div dojoType="Toggler" targetId="filter">
                                    		<a class="item-button-search" href="javascript:return true;"><span><spring:message code="sirius.paging.filter"/></span></a>
                                		</div>
                           			</div>
                            	</td>
                            	<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                            </tr>
							</table>
					  	</div>
                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <th width="1%"><div style="width:45px"></div></th>
                          	<th width="10%"><spring:message code="sirius.code"/></th>
                          	<th width="20%"><spring:message code="sirius.name"/></th>
                          	<th width="5%"><spring:message code="sirius.active"/></th>
                          	<th width="20%"><spring:message code="paymentreferencetype"/></th>
                          	<th width="35%"><spring:message code="glaccount.account.name"/></th>
                        </tr>
                        <c:forEach items="${paymentTypes}" var="payment">
                        <tr>
                             <td class="tools">
                                <c:if test='${access.edit}'>
                                	<a class="item-button-edit" href="<c:url value='/page/paymentmanualtypepreedit.htm?id=${payment.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
                                </c:if>
                                <c:if test="${access.delete and payment.deleteable}">
                                	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/paymentmanualtypedelete.htm?id=${payment.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
                                </c:if>
                             </td>
                             <td><c:out value="${payment.code}"/></td>
                             <td><c:out value="${payment.name}"/></td>
							 <td><spring:message code="sirius.${payment.enabled ? 'yes' : 'no'}"/></td>
                             <td><spring:message code="paymentreferencetype.${payment.referenceType.messageName}"/></td>
                             <td><c:out value="${payment.account.code} - ${payment.account.name}"/></td>
                        </tr>
                        </c:forEach>
                        <tr class="end-table"><td colspan="6">&nbsp;</td></tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
                        <tr>
                            <td align="right"><%@ include file="/common/navigate.jsp"%></td>
                        </tr>
                        </table>
					</div>
				</div>
			</div>
		</div>
	</div>
  	<%@ include file="/common/sirius-footer.jsp"%>
</div>
</body>
</html>

