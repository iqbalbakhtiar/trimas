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
	<%@ include file="/filter/ar/receiptManualFilter.jsp"%>
	<%-- <%@ include file="/filter/ar/receiptManualRecalculateJournalFilter.jsp"%> --%>
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
                                		<a class="item-button-new" href="<c:url value='/page/receiptmanualpreadd.htm'/>"><span><spring:message code="receipt.new"/></span></a>
                                		<div dojoType="Toggler" targetId="filter">
                                    		<a class="item-button-search" href="javascript:return true;"><span><spring:message code="sirius.paging.filter"/></span></a>
                                		</div>
										<c:if test='${not empty rejournal}'>
											<div dojoType="Toggler" targetId="create">
	                                        	<a class="item-button-doc" href="javascript:return;"><span>Rejournal</span></a>
	                                        </div>
										</c:if>
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
                          	<th width="8%"><spring:message code="sirius.date"/></th>
                          	<th width="12%"><spring:message code="customer"/> / <spring:message code="supplier"/></th>
                          	<th width="5%"><spring:message code="sirius.paymentmethodtype"/></th>
                          	<th width="10%"><spring:message code="receipttype"/></th>
                          	<%-- <th width="10%"><spring:message code="sirius.reference"/></th> --%>
                          	<th width="8%" style="text-align: right;padding-right: 10px;"><spring:message code="sirius.amount"/></th>
                          	<th width="10%"><spring:message code="receiptreferencetype"/></th>
                        </tr>
                        <c:forEach items="${receipts}" var="receipt">
                        <tr>
                             <td class="tools">
                                <c:if test='${access.edit}'>
                                	<a class="item-button-edit" href="<c:url value='/page/receiptmanualpreedit.htm?id=${receipt.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
                                </c:if>
                                <c:if test="${access.delete}">
                                	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/receiptmanualdelete.htm?id=${receipt.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
                                </c:if>
                             </td>
                             <td><c:out value="${receipt.code}"/></td> 
                             <td><fmt:formatDate value="${receipt.date}" pattern="dd-MM-yyyy"/></td> 
                             <td><c:out value="${receipt.customer.fullName}"/></td>
                             <td><c:out value="${receipt.receiptInformation.paymentMethodType}"/></td>
                             <td><c:out value="${receipt.receiptManualType.name}"/></td>
                             <%-- <td><a href="<c:url value='/page/${receipt.purchaseMemoable.referenceUri}?id=${receipt.purchaseMemoable.id}'/>"><c:out value="${receipt.purchaseMemoable.code}"/></a></td> --%>
                             <td style="text-align: right;padding-right: 10px;"><fmt:formatNumber value="${receipt.receiptInformation.amount}" pattern="#,##0.00"/></td>
                             <td><spring:message code="paymentreferencetype.${receipt.receiptManualType.referenceType.messageName}"/></td>
                        </tr>
                        </c:forEach>
                        <tr class="end-table"><td colspan="9">&nbsp;</td></tr>
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
