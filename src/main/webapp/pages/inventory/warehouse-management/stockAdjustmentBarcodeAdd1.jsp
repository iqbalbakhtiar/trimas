<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/barcodeGroupFilter.jsp"%>
<div class="item-navigator">
    <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
        <tr>
            <td width="30%" height="30" align="left" valign="middle">
                <div class="toolbar-clean">
                    <a class="item-button-list" href="<c:url value='/page/stockadjustmentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
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
            <th width="15%"><spring:message code="sirius.type"/></th>
            <th width="12%"><spring:message code="sirius.createdby"/></th>
            <th width="6%"><spring:message code="sirius.qty"/></th>
        </tr>
        <%--@elvariable id="barcode" type="com.siriuserp.sdk.dm.BarcodeGroup"--%>
        <c:forEach items="${barcodes}" var="barcode">
            <tr valign="top">
                <td class="tools">
                    <a class="item-button-new" href="<c:url value='/page/stockadjustmentbarcodepreadd2.htm?id=${barcode.id}'/>"  title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
                </td>
                <td><c:out value="${barcode.code}"/></td>
                <td><fmt:formatDate value="${barcode.date}" pattern="dd-MM-yyyy"/></td>
                <td><c:out value="${barcode.facility.name}"/></td>
                <td><c:out value="${barcode.barcodeGroupType.normalizedName}"/></td>
                <td><c:out value="${barcode.createdBy.fullName}"/></td>
                <td><c:out value="${barcode.barcodes.size()}"/></td>
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
