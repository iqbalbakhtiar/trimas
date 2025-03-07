<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <c:set var="url" value="/page/apageingdetailview.htm?organization=${criteria.organization}&supplier=${criteria.supplier}"/>
    <a class="item-button-back" href="<c:url value='/page/apageingdetailpre.htm'/>"><span>Back</span></a>
    <a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
    <a class="item-button-export-xls" download="ageingdetailreportexcell.xls" href="#" onclick="return ExcellentExport.excel(this, 'ageingDetail', 'AP Ageing Detail');"><span>Export</span></a>
    <a class="item-button-rprev" href="<c:url value='${url}&date='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>"><span>Prev</span></a>
    <a class="item-button-rnext" href="<c:url value='${url}&date='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>"><span>Next</span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <table border="0" cellspacing="0" width="100%" id="ageingDetail">
            <thead>
            <tr>
                <td colspan="9" class="CSS3"><strong>REPORT AP AGEING DETAIL</strong></td>
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
            <c:forEach items='${list}' var='report'>
            <tr>
                <td align="left" colspan="9"><strong><c:out value='${report.supplier.code} - ${report.supplier.fullName}'/></strong></td>
            </tr>
            <tr>
                <th width="9%" align="center" class="border-top border-bottom" rowspan="2">Date</th>
                <th width="9%" align="center" class="border-top border-bottom" rowspan="2">No Inv Supplier</th>
                <th width="18%" align="center" class="border-top border-bottom" rowspan="2">Ref No.</th>
                <th width="9%" align="center" class="border-top border-bottom" rowspan="2">Due Date</th>
                <th width="10%" align="center" class="border-top border-bottom" rowspan="2">Balance</th>
                <th width="11%" align="center" class="border-top border-bottom" rowspan="2">Not Yet Due</th>
                <th align="center" class="border-top" colspan="4">Over Due</th>
            </tr>
            <tr>
                <th width="11%" align="center" class="border-top border-bottom">0-30 Days</th>
                <th width="11%" align="center" class="border-top border-bottom">31-60 Days</th>
                <th width="11%" align="center" class="border-top border-bottom">61-90 Days</th>
                <th width="10%" align="center" class="border-top border-bottom">&gt; 90 Days</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items='${report.items}' var='item'>
                <tr>
                    <td align="left"><fmt:formatDate value='${item.invoice.date}' pattern='dd MMM yyyy'/></td>
                    <td align="left"><a href="<c:url value='/page/${item.invoice.uri}?id=${item.invoice.id}'/>"><c:out value='${item.invoice.invoice}'/></a></td>
                    <td align="left"><a href="<c:url value='/page/${item.invoice.uri}?id=${item.invoice.id}'/>"><c:out value='${item.invoice.code}'/></a></td>
                    <td align="left"><fmt:formatDate value='${item.invoice.dueDate}' pattern='dd MMM yyyy'/></td>
                    <td align="right">
                        <fmt:formatNumber value='${item.balance}' pattern=',##0.00'/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value='${item.notYetDue}' pattern=',##0.00'/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value='${item.fstOverDue}' pattern=',##0.00'/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value='${item.sndOverDue}' pattern=',##0.00'/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value='${item.thdOverDue}' pattern=',##0.00'/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value='${item.fthOverDue}' pattern=',##0.00'/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tr>
                <td class="border-top" colspan="4" align="right"><strong>Sub Total</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.balance}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.notYetDue}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.fstOverDue}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.sndOverDue}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.thdOverDue}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.fthOverDue}' pattern=',##0.00'/></strong></td>
            </tr>
            <tr>
                <td class="border-top" colspan="4" align="right"><strong>Sub Percentage</strong></td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.notYetDue/report.balance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.fstOverDue/report.balance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.sndOverDue/report.balance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.thdOverDue/report.balance*100}' pattern=',##0.00'/>%</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${report.fthOverDue/report.balance*100}' pattern=',##0.00'/>%</strong></td>
            </tr>
            <tr><td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
                <td class="border-top">&nbsp;</td>
            </tr>
            </c:forEach>
            <tr>
                <td class="border-top" colspan="4" align="right"><strong>Grand Total</strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totBalance}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totNyd}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFsod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totSod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totTod}' pattern=',##0.00'/></strong></td>
                <td class="border-top" align="right"><strong><fmt:formatNumber value='${totFtod}' pattern=',##0.00'/></strong></td>
            </tr>
            <tr>
                <td class="border-top" colspan="4" align="right"><strong>Grand Percentage</strong></td>
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