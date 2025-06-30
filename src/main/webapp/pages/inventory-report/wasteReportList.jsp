<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/wastereportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="wastereport.xls" href="#" onclick="return ExcellentExport.excel(this, 'waste', 'waste Report');"><span><spring:message code="sirius.export"/></span></a>
    <a class="item-button-rprev" href="javascript:prev();"><span><spring:message code="sirius.prev"/></span></a>
    <a class="item-button-rnext" href="javascript:next();"><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <table cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td colspan="3"><strong>WASTE REPORT</strong></td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td width="109"><spring:message code="sirius.organization"/> </td>
            <td width="10">&nbsp;:</td>
            <td width="1110"><c:out value='${organization.fullName}'/></td>
        </tr>
        <tr>
            <td><spring:message code="sirius.period"/></td>
            <td>&nbsp;:</td>
            <td><fmt:formatDate value='${criteria.dateFrom}' pattern='MMMM yyyy'/></td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
    </table>
    <table cellpadding="3" cellspacing="0" width="100%" id="waste">
        <thead>
        <%--This row only for export excel header--%>
        <tr style="display: none">
            <td colspan="10" align="center">REPORT WASTE</td>
        </tr>
        <tr style="display: none">
            <td colspan="10" align="center"><fmt:formatDate value='${criteria.dateFrom}' pattern='MMMM yyyy'/></td>
        </tr>
        <tr>
            <th class="border-top border-left" rowspan="3" width="3%">No</th>
            <th class="border-top border-left" rowspan="2">Jenis</th>
            <th class="border-top border-left" colspan="2" rowspan="2">Saldo Awal</th>
            <th class="border-top border-left" colspan="2">Masuk</th>
            <th class="border-top border-left" colspan="2" rowspan="2" >Keluar</th>
            <th class="border-top border-left border-right" colspan="2" rowspan="2" align="center">Saldo Akhir<br/>Hasil Press</th>
        </tr>
        <tr>
            <th class="border-top border-left" colspan="2">Hasil Press</th>
        </tr>
        <tr>
            <th class="border-top border-left">Unusable Hasil Press</th>
            <th class="border-top border-left">Krg</th>
            <th class="border-top border-left">Netto</th>
            <th class="border-top border-left">Krg</th>
            <th class="border-top border-left">Netto</th>
            <th class="border-top border-left">Krg</th>
            <th class="border-top border-left">Netto</th>
            <th class="border-top border-left">Krg</th>
            <th class="border-top border-left border-right">Netto</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items='${reports.adapters}' var='adapter' varStatus="loop">
            <c:if test='${adapter.quantity > 0 || adapter.in > 0 || adapter.out > 0 || adapter.sum > 0}'>
                <tr>
                    <td class="border-top border-left" align="center">${loop.count}</td>
                    <td class="border-top border-left">${adapter.productName}</td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.openingSerial}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.quantity}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.inSerial}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.in}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.outSerial}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.out}' pattern=',##0.00'/></td>
                    <td class="border-top border-left" align="right"><fmt:formatNumber value='${adapter.closingSerial}' pattern=',##0.00'/></td>
                    <td class="border-top border-left border-right" align="right"><fmt:formatNumber value='${adapter.sum}' pattern=',##0.00'/></td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td class="border-top border-left border-bottom">&nbsp;</td>
            <td class="border-top border-left border-bottom" align="center"><strong>TOTAL UNUSABLE WASTE HASIL PRESS</strong></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalOpeningSerial}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalQuantity}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalInSerial}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalIn}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalOutSerial}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalOut}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom" align="right"><fmt:formatNumber value='${reports.grandTotalClosingSerial}' pattern=',##0.00'/></td>
            <td class="border-top border-left border-bottom border-right" align="right"><fmt:formatNumber value='${reports.grandTotalSum}' pattern=',##0.00'/></td>
        </tr>
        </tfoot>
    </table>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
    function prev()
    {
        window.location = "<c:url value='/page/wastereportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>";
    }

    function next()
    {
        window.location = "<c:url value='/page/wastereportview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&product=${criteria.product}&dateFrom='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>";
    }
</script>