<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/mutationreportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="mutationreport.xls" href="#" onclick="return ExcellentExport.excel(this, 'mutation', 'Mutation Report');"><span><spring:message code="sirius.export"/></span></a>
    <a class="item-button-rprev" href="<c:url value='/page/mutationreportview.htm?organization=${organization.id}&facility=${criteria.facility}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>"><span><spring:message code="sirius.prev"/></span></a>
    <a class="item-button-rnext" href="<c:url value='/page/mutationreportview.htm?organization=${organization.id}&facility=${criteria.facility}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <%@include file="mutationReportPrint.jsp" %>
    <%@include file="mutationReportExcel.jsp" %>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
</script>