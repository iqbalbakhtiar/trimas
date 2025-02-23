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
  <%@ include file="/filter/procurement/standardPurchaseOrderFilter.jsp"%>
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
              <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
                <tr>
                  <td width="35%" height="30" align="left" valign="middle">
                    <div class="toolbar-clean">
                      <c:if test='${access.add}'>
                        <a class="item-button-new" href="<c:url value='/page/standardpurchaseorderpreadd.htm' />"><span><spring:message code="purchaseorder.new"/></span></a>
                      </c:if>
                      <div dojoType="Toggler" targetId="filter">
                        <a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
                      </div>
                    </div>
                  </td>
                  <td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                </tr>
              </table>
            </div>
            <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <th width="1%"><div style="width: 45px;">&nbsp;</div></th>
                <th width="8%"><spring:message code="sirius.code"/></th>
                <th width="8%"><spring:message code="sirius.date"/></th>
                <th width="10%"><spring:message code="organization"/></th>
                <th width="10%"><spring:message code="supplier"/></th>
                <th width="10%"><spring:message code="salesorder.approvalstatus"/></th>
                <th width="10%"><spring:message code="sirius.approver"/></th>
                <th width="50%"><spring:message code="sirius.note"/></th>
              </tr>
              <c:forEach items="${purchaseOrders}" var="purchaseOrder">
                <tr>
                  <td class="tools">
                    <a class="item-button-edit" href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${purchaseOrder.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code="sirius.edit"/></span></a>
                    <c:if test='${access.delete}'>
                    	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/purchaserequisitiondelete.htm?id=${purchaseOrder.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
                   	</c:if>
                  </td>
                  <td nowrap="nowrap">${purchaseOrder.code}</td>
                  <td nowrap="nowrap"><fmt:formatDate value='${purchaseOrder.date}' pattern='dd-MM-yyyy'/></td>
                  <td nowrap="nowrap">${purchaseOrder.organization.fullName}</td>
                  <td nowrap="nowrap">${purchaseOrder.supplier.fullName}</td>
                  <td nowrap="nowrap">${dpo.approvable.approvalDecision.approvalDecisionStatus.normalizedName}</td>
                  <td nowrap="nowrap">${dpo.approvable.approvalDecision.forwardTo.fullName}</td>
                  <td nowrap="nowrap">${purchaseOrder.note}</td>
                </tr>
              </c:forEach>
              <tr class="end-table"><td colspan="8">&nbsp;</td></tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
              <tr>
                <td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
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
