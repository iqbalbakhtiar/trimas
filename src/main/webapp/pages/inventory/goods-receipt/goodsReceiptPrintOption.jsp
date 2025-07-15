<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/goodsreceiptpreedit.htm?id=${goodsReceipt_edit.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:printMode();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
	<form name="formOption" method="POST">
		<c:set var="printUrl" value="/page/goodsreceiptprint.htm?id=${goodsReceipt_edit.id}" />
		<table style="border:none" width="100%">
			<tr>
				<td width="40%"></td>
			  	<td width="2%"></td>
			  	<td width="92%">&nbsp;</td>
			</tr>
			<tr>
                <td align="right"><spring:message code="contactmechanism.department"/>&nbsp;</td>
                <td align="center">:</td>
                <td >
                    <select id="container" name="container">
                        <c:forEach var="container" items="${containers}">
                        	<c:if test="${not empty container}">
                                  <option value="${container.id}"><c:out value='${container.name}'/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
		</table>
	</form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
function printMode()
{
	document.formOption.action = '<c:url value="${printUrl}" />';
	document.formOption.submit();
}
</script>