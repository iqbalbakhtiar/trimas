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
                        <div class="item-navigator">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
                                <tr>
                                    <td width="35%" height="30" align="left" valign="middle">

                                        <div class="toolbar-clean">
                                            <a class="item-button-list" href="<c:url value='/page/goodsreceiptview.htm' />"><span><spring:message code="sirius.list"/></span></a>
                                            <div dojoType="Toggler" targetId="filter">
                                                <a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
                                            </div>
                                        </div>

                                    </td>
                                    <td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                                </tr>
                            </table>
                        </div>
                        <form id="addForm" name="addForm" method="post" modelAttribute="adapter">
                            <strong><spring:message code="goodsreceipt.reference"/> :</strong>
                            <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
                                <thead>
                                <tr>
                                    <th width="3%">&nbsp;</th>
                                    <th width="10%"><spring:message code="organization"/></th>
                                    <th colspan="2"><spring:message code="goodsreceipt.refdoc"/> / <spring:message code="goodsreceiptitem"/></th>
                                    <th width="10%"><spring:message code="goodsreceipt.date"/></th>
                                    <th width="10%"><spring:message code="makloonandsteam.deliverydate"/></th>
                                    <th width="10%"><spring:message code="goodsreceipt.source"/></th>
<%--                                    <th width="10%"><spring:message code="goodsreceipt.warehouse"/></th>--%>
                                    <th width="10%" style="text-align: center"><spring:message code="goodsissueitem.qty"/></th>
                                    <th width="10%"><spring:message code="deliveryrealization.uom"/></th>
<%--                                    <th width="8%"><spring:message code="goodsreceipt.tax"/></th>--%>
<%--                                    <th width="10%"><spring:message code="goodsreceipt.reftype"/></th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${adapter.adapters}" var="item" varStatus="status">
                                    <c:if test='${adapter.reference.message == "reference"}'>
                                        <%@ include file="goodsReceiptReferenceAdd.jsp"%>
                                    </c:if>
<%--                                    <c:if test='${adapter.reference.message == "item"}'>--%>
<%--                                        <%@ include file="goodsReceiptItemAdd.jsp"%>--%>
<%--                                    </c:if>--%>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr class="end-table"><td colspan="9">&nbsp;</td></tr>
                                </tfoot>
                            </table>
                        </form>
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

    <div style="display: none">
        <%@ include file="/common/dialog.jsp"%>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function()
    {
        $(".item-button-back").click(function() {
            window.location ="<c:url value='/page/goodsreceiptview.htm'/>";
        });

        $('.item-button-edit').click(function(event) {
            event.preventDefault();

            var refId = $(this).data('refid');

            // Set 'enabled' to 'false' for all adapters
            $('.enabled-input').val('false');

            // Set 'enabled' to 'true' for adapters belonging to the selected reference
            $('.enabled-input.ref-' + refId).val('true');

            // Submit form ke URL tujuan
            $('#addForm').attr('action', '<c:url value="/page/goodsreceiptpreadd2.htm"/>');
            $('#addForm').submit();
        });
    });
</script>
</body>
<!-- END OF BODY -->
</html>
