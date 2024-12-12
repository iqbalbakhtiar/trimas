<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/ledgerdetailreportprepare.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="ledgerdetailreport.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'AR Ledger Detail');"><span><spring:message code="sirius.export"/></span></a>
    <a class="item-button-rprev" href="<c:url value='/page/ledgerdetailreportview.htm?organization=${organization.id}&customer=${criteria.customer}&date='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>"><span>Prev</span></a>
    <a class="item-button-rnext" href="<c:url value='/page/ledgerdetailreportview.htm?organization=${organization.id}&customer=${criteria.customer}&date='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <%@ include file="/pages/accounting/report/arLedgerDetailReportPrint.jsp"%>
    </div>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>