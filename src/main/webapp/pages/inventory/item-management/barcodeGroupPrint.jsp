<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/barcodegrouppreedit.htm?id=${data.groupId}'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <table width="245" border="0" cellpadding="0" cellspacing="0">
            <c:forEach items="${data.list}" var="item" varStatus="index">
                <c:out value="<tr style='page-break-after:always;'>"  escapeXml="false"/>
                <td width="185" >
                    <div align="center">
                        <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td align="left"><img style="color:#000000" src="barcode4j?type=Code39&msg=<c:out value='${item.code}' />" class="tblprint" /></td>
                            </tr>
                        </table>
                    </div>
                </td>
                <td><font color="#FFFFFF"></font></td>
                <c:out value="</tr>"  escapeXml="false"/>
            </c:forEach>
        </table>
    </div>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>