<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/barcodegrouppreedit.htm?id=${barcode_edit.id}'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
            <tr>
                <td style="background: black" width="1%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td width="96%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td style="background: black" width="1%">&nbsp;</td>
            <tr>
        </table>
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="2%" colspan="2">&nbsp;</td>
                <td width="48%" colspan="3">
                    <strong><c:out value="${barcode_edit.organization.salutation} ${barcode_edit.organization.fullName}"/></strong><br/>
                    <c:forEach items="${barcode_edit.organization.postalAddresses}" var="postal">
                        <c:out value="${types.postalAddress.address}"/><br/>
                    </c:forEach>
                </td>
                <td  width="48%" colspan="3" align="right" class="CSS3" valign="top" nowrap="nowrap"><strong><spring:message code="barcode"/> Packing List</strong></td>
                <td width="2%" colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td width="2%" colspan="2">&nbsp;</td>
                <td colspan="3" valign="top" align="left">
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td nowrap="nowrap"><spring:message code="sirius.id"/></td>
                            <td>:</td>
                            <td><c:out value='${barcode_edit.code}'/></td>
                        </tr>
                        <tr>
                            <td width="27%" nowrap="nowrap"><spring:message code="sirius.date"/></td>
                            <td width="3%">:</td>
                            <td width="70%"><fmt:formatDate value='${barcode_edit.date}' pattern='dd - MM - yyyy'/></td>
                        </tr>
                        <tr>
                            <td width="27%" nowrap="nowrap"><spring:message code="sirius.facility"/></td>
                            <td width="3%">:</td>
                            <td width="70%"><c:out value="${barcode_edit.facility.name}"/></td>
                        </tr>
                        <tr>
                            <td width="27%" nowrap="nowrap"><spring:message code="sirius.type"/></td>
                            <td width="3%">:</td>
                            <td width="70%"><c:out value="${barcode_edit.barcodeGroupType.normalizedName}"/></td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr><td colspan="10">&nbsp;</td></tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="6">
                    <table width="100%" cellspacing="0" cellpadding="1">
                        <tr>
                            <th width="15%" align="center" class="border-left border-top"><spring:message code="product"/></th>
                            <th width="70%"align="center" class="border-left border-top">&nbsp;</th>
                            <th width="5%" align="center" class="border-left border-top"><spring:message code="barcode.roll"/></th>
                            <th width="5%" align="center" class="border-left border-top border-right">Yards</th>
                        </tr>
                        <c:set var="totalRoll" value="${0}"/>
                        <c:set var="totalQty" value="${0}"/>
                        <c:forEach items="${products}" var="prod" varStatus="status">
                            <c:set var="countRow" value="${1}"/>
                            <c:set var="totalData" value="${fn:length(prod.value)}"/>
                            <c:set var="totalRow"><fmt:formatNumber value="${(totalData/10)+(1-(totalData/10%1))%1}" pattern="####"/></c:set>
                            <c:set var="roll" value="${0}"/>
                            <c:set var="quantity" value="${0}"/>
                            <c:set var="start" value="${0}"/>
                            <c:set var="end" value="${9}"/>
                            <tr>
                                <td class="border-left border-top" style="white-space:pre-wrap; word-wrap:break-word"><font size="1">&nbsp;<c:out value="${prod.key.name}"/></font></td>
                                <td width="25%" class="border-left border-top">
                                    <table>
                                        <c:forEach var="ttl" begin="${countRow}" end="${totalRow}" step="1">
                                            <tr align="center">
                                                <c:forEach items="${prod.value}" var="det" varStatus="detStatus" begin="${start}" end="${end}" step="1">
                                                    <td><font size="1">(<fmt:formatNumber value='${det.quantity}' pattern=',##0.00'/>)</br>&nbsp;${det.code}&nbsp;</font></td>
                                                    <c:set var="roll" value="${roll+1}"/>
                                                    <c:set var="quantity" value="${quantity+det.quantity}"/>
                                                </c:forEach>
                                            </tr>
                                            <c:set var="start" value="${start+10}"/>
                                            <c:set var="end" value="${end+10}"/>
                                        </c:forEach>
                                    </table>
                                </td>
                                <td width="3%" align="right" class="border-left border-top"><fmt:formatNumber value='${roll}' pattern=',##0.00'/>&nbsp;</td>
                                <td width="3%" align="right" class="border-left border-top border-right"><fmt:formatNumber value='${quantity}' pattern=',##0.00'/>&nbsp;</td>
                            </tr>
                            <c:set var="totalRoll" value="${totalRoll+roll}"/>
                            <c:set var="totalQty" value="${totalQty+quantity}"/>
                        </c:forEach>
                        <tr>
                            <td colspan="2" class="border-left border-top" align="right"><strong>Total&nbsp;</strong></td>
                            <td class="border-left border-top" align="right"><fmt:formatNumber value='${totalRoll}' pattern=',##0.00'/>&nbsp;</td>
                            <td class="border-left border-right border-top"align="right"><fmt:formatNumber value='${totalQty}' pattern=',##0.00'/>&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="6" class="border-top">&nbsp;</td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr><td colspan="10">&nbsp;</td></tr>
            <table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
                <tr>
                    <td style="background: black" width="1%">&nbsp;</td>
                    <td width="1%">&nbsp;</td>
                    <td width="96%">&nbsp;</td>
                    <td width="1%">&nbsp;</td>
                    <td style="background: black" width="1%">&nbsp;</td>
                <tr>
            </table>
        </table>
    </div>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>