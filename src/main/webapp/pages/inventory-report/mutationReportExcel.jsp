<table id="mutation" style="display: none">
    <tr>
        <td colspan="2"><strong>MUTATION REPORT</strong></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><spring:message code="organization"/></td>
        <td>: <c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td><spring:message code="sirius.month"/></td>
        <td>: <fmt:formatDate value="${criteria.dateFrom}" pattern="MMMM yyyy"/></td>
    </tr>
    <tr>
        <td><spring:message code="contactmechanism.department"/></td>
        <td>: </td>
    </tr>

    <tr>
        <td>&nbsp;</td>
    </tr>

    <tr>
        <th rowspan="2" align="left"><spring:message code="sirius.code"/></th>
        <th rowspan="2" align="left"><spring:message code="product.name"/></th>
        <th colspan="2" align="center"><spring:message code="sirius.in"/></th>
        <th colspan="2" align="center"><spring:message code="sirius.out"/></th>
        <th rowspan="2" align="center"><spring:message code="sirius.note"/></th>
    </tr>
    <tr>
        <th align="center"><spring:message code="sirius.qty"/></th>
        <th align="center"><spring:message code="sirius.price"/></th>
        <th align="center"><spring:message code="sirius.qty"/></th>
        <th align="center"><spring:message code="sirius.price"/></th>
    </tr>

    <c:forEach items="${reports}" var="report">
        <tr>
            <td><c:out value="${report.productCode}"/></td>
            <td><c:out value="${report.productName}"/></td>
            <td align="right"><fmt:formatNumber value="${report.in}" pattern=",##0.00"/></td>
            <td align="right"><fmt:formatNumber value="${report.in > 0 ? report.cogs : 0.00}" pattern=",##0.00"/></td>
            <td align="right"><fmt:formatNumber value="${report.out}" pattern=",##0.00"/></td>
            <td align="right"><fmt:formatNumber value="${report.out > 0 ? report.cogs : 0.00}" pattern=",##0.00"/></td>
            <td>&nbsp;</td> <%-- Note --%>
        </tr>
    </c:forEach>
</table>