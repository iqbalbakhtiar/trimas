<table cellpadding="3" cellspacing="0" width="100%">
    <tr>
        <td colspan="3"><strong>MUTATION REPORT</strong></td>
    </tr>
    <tr><td colspan="3">&nbsp;</td></tr>
    <tr>
        <td width="109"><spring:message code="sirius.organization"/></td>
        <td width="10">&nbsp;:</td>
        <td width="1110"><c:out value='${organization.fullName}'/></td>
    </tr>
    <tr>
        <td><spring:message code="sirius.month"/></td>
        <td>&nbsp;:</td>
        <td><fmt:formatDate value="${criteria.dateFrom}" pattern="MMMM yyyy"/></td>
    </tr>
    <tr>
        <td><spring:message code="contactmechanism.department"/></td>
        <td>&nbsp;:</td>
        <td><c:out value='${container.name}'/></td>
    </tr>
    <tr><td colspan="3">&nbsp;</td></tr>
</table>

<table cellpadding="3" cellspacing="0" width="100%" style="border-spacing:0;">
    <colgroup>
        <col width="12%"/>  <%-- Code --%>
        <col width="32%"/>  <%-- Product Name --%>
        <col width="11%"/>   <%-- In Qty --%>
        <col width="11%"/>   <%-- In Price --%>
        <col width="11%"/>   <%-- Out Qty --%>
        <col width="11%"/>   <%-- Out Price --%>
        <col width="12%"/>  <%-- Note --%>
    </colgroup>

    <thead>
    <!-- baris header 1 -->
    <tr>
        <th rowspan="2"
            align="left"
            style="
            border-top:    1px solid #000;
            border-left:   1px solid #000;
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.code"/>
        </th>
        <th rowspan="2"
            align="left"
            style="
            border-top:    1px solid #000;
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="product.name"/>
        </th>
        <th colspan="2" align="center"
            style="
            border-top:    1px solid #000;
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.in"/>
        </th>
        <th colspan="2" align="center"
            style="
            border-top:    1px solid #000;
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.out"/>
        </th>
        <th rowspan="2" align="center"
            style="
            border-top:    1px solid #000;
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.note"/>
        </th>
    </tr>
    <!-- baris header 2 (sub-kolom In/Out) -->
    <tr>
        <th align="center"
            style="
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.qty"/>
        </th>
        <th align="center"
            style="
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.price"/>
        </th>
        <th align="center"
            style="
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.qty"/>
        </th>
        <th align="center"
            style="
            border-right:  1px solid #000;
            border-bottom: 1px solid #000;
            padding: 4px;">
            <spring:message code="sirius.price"/>
        </th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${reports}" var="report">
        <tr>
            <td style="
              border-left:   1px solid #000;
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <c:out value="${report.productCode}"/>
            </td>
            <td style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <c:out value="${report.productName}"/>
            </td>
            <td align="right" style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <fmt:formatNumber value="${report.in}" pattern=",##0.00"/>
            </td>
            <td align="right" style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <fmt:formatNumber value="${report.in > 0 ? report.cogs : 0.00}" pattern=",##0.00"/>
            </td>
            <td align="right" style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <fmt:formatNumber value="${report.out}" pattern=",##0.00"/>
            </td>
            <td align="right" style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                <fmt:formatNumber value="${report.out > 0 ? report.cogs : 0.00}" pattern=",##0.00"/>
            </td>
            <td style="
              border-right:  1px solid #000;
              border-bottom: 1px solid #000;
              padding: 4px;">
                &nbsp;
            </td> <%-- Note --%>
        </tr>
    </c:forEach>
    </tbody>
</table>
