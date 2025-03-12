<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <c:set var="url" value="/page/apageingsummaryview.htm?organization=${criteria.organization}"/>
    <a class="item-button-back" href="<c:url value='/page/apageingsummarypre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="ageingsummaryreportexcell.xls" href="#" onclick="return ExcellentExport.excel(this, 'ageingSummary', 'AP Ageing Summary');"><span><spring:message code="sirius.export"/></span></a>
    <a class="item-button-rprev" href="<c:url value='${url}&date='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>"><span><spring:message code="sirius.prev"/></span></a>
    <a class="item-button-rnext" href="<c:url value='${url}&date='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <table border="0" cellspacing="0" width="100%" id="ageingSummary">
            <thead>
            <tr>
                <td colspan="9" class="CSS3"><strong>REPORT AP AGEING SUMMARY</strong></td>
            </tr>
            <tr>
                <td>Company</td>
                <td colspan="8" align="left">:&nbsp;&nbsp;<c:out value='${organization.fullName}'/></td>
            </tr>
            <tr>
                <td>Period</td>
                <td colspan="8" align="left">:&nbsp;&nbsp;<fmt:formatDate value='${criteria.date}' pattern='dd MMM yyyy' /></td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <th width="18%" align="center" class="border-top border-bottom" rowspan="2"><spring:message code="supplier"/></th>
                <th width="10%" align="center" class="border-top border-bottom" rowspan="2"><spring:message code="accreport.balance"/></th>
                <th width="11%" align="center" class="border-top border-bottom" rowspan="2"><spring:message code="ageing.notyet"/></th>
                <th align="center" class="border-top" colspan="4"><spring:message code="ageing.over"/></th>
            </tr>
            <tr>
                <th width="11%" align="center" class="border-top border-bottom">0-30 <spring:message code="sirius.days"/></th>
                <th width="11%" align="center" class="border-top border-bottom">31-60 <spring:message code="sirius.days"/></th>
                <th width="11%" align="center" class="border-top border-bottom">61-90 <spring:message code="sirius.days"/></th>
                <th width="10%" align="center" class="border-top border-bottom">&gt; 90 <spring:message code="sirius.days"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items='${list}' var='report'>
            <tr>
                <td align="left">
                    <a href="<c:url value='/page/apageingdetailview.htm?organization=${organization.id}&supplier=${report.supplier.id}'/>"><c:out value='${report.supplier.fullName}'/></a>
                </td>
                <td align="right"><fmt:formatNumber value='${report.balance}' pattern=',##0.00'/></td>
                <td align="right"><fmt:formatNumber value='${report.notYetDue}' pattern=',##0.00'/></td>
                <td align="right"><fmt:formatNumber value='${report.fstOverDue}' pattern=',##0.00'/></td>
                <td align="right"><fmt:formatNumber value='${report.sndOverDue}' pattern=',##0.00'/></td>
                <td align="right"><fmt:formatNumber value='${report.thdOverDue}' pattern=',##0.00'/></td>
                <td align="right"><fmt:formatNumber value='${report.fthOverDue}' pattern=',##0.00'/></td>
            </tr>
            </tbody>
            </c:forEach>
            <tr><td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="border-top" align="right"><strong><spring:message code="ageing.grand.total"/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totBalance}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totNyd}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFsod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totSod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totTod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFtod}' pattern=',##0.00'/></strong></td>
            </tr>
            <tr>
                <td class="border-top" align="right"><strong><spring:message code="ageing.grand.percentage"/></strong></td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totNyd/totBalance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFsod/totBalance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totSod/totBalance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totTod/totBalance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFtod/totBalance*100}' pattern=',##0.00'/>%</strong></td>
            </tr>
        </table>
    </div>
</div>


<%@ include file="/common/sirius-general-bottom.jsp"%>