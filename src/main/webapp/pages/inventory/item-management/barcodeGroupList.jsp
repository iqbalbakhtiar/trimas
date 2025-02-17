<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/barcodeGroupFilter.jsp"%>
<div class="item-navigator">
    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
        <tr>
            <td width="30%" height="30" align="left" valign="middle">
                <div class="toolbar-clean">
                    <c:if test='${access.add}'>
                        <a class="item-button-new" href="<c:url value='/page/barcodegrouppreadd1.htm'/>"><span><spring:message code="barcode.new"/></span></a>
                    </c:if>
                    <div dojoType="Toggler" targetId="filter">
                        <a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
                    </div>
                </div>
            </td>
            <td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
        </tr>
    </table>
</div>

<div style="overflow-x: auto;">
    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
        <tr>
            <th width="4%">&nbsp;</th>
            <th width="9%"><spring:message code="goodsreceipt.code"/></th>
            <th width="6%"><spring:message code="goodsreceipt.date"/></th>
            <th width="15%"><spring:message code="sirius.facility"/></th>
            <th width="12%"><spring:message code="sirius.createdby"/></th>
        </tr>
        <c:forEach items="${barcodes}" var="barcode">
            <tr valign="top">
                <td class="tools">
                    <a class="item-button-edit" href="<c:url value='/page/barcodegrouppreedit.htm?id=${barcode.id}'/>"  title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
                    <c:if test='${access.delete}'>
                        <a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/barcodegroupdelete.htm?id=${barcode.id}'/>');" title='<spring:message code="sirius.delete"/>'><span><spring:message code="sirius.delete"/></span></a>
                    </c:if>
                </td>
                <td><c:out value="${barcode.code}"/></td>
                <td><fmt:formatDate value="${barcode.date}" pattern="dd-MM-yyyy"/></td>
                <td><c:out value="${barcode.facility.name}"/></td>
                <td><c:out value="${barcode.createdBy.fullName}"/></td>
            </tr>
        </c:forEach>
        <tr class="end-table"><td colspan="8">&nbsp;</td></tr>
    </table>
</div>

<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
    <tr>
        <td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
    </tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>
