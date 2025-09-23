<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/${billing_edit.billing.billingType.url}?id=${billing_edit.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:printMode();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
	<form name="bilingPrintOptionForm" method="POST">
		<table style="border:none" width="100%">
			<tr>
				<td width="4%"></td>
			  	<td width="3%"></td>
			  	<td width="92%">&nbsp;</td>
			</tr>
			<tr>
			  	<td colspan="3">&nbsp;&nbsp;&nbsp;Please select print out document : </td>
			</tr>
			<tr>
			  	<td></td>
			  	<td valign="middle"><input name="invType" type="radio" value="1" checked></td>
			  	<td valign="middle" align="left">Invoice 1</td>
			  	<td width="1%" align="left">&nbsp;</td>
			</tr>
			<tr>
			  	<td></td>
			  	<td valign="middle"><input name="invType" type="radio" value="4"></td>
			  	<td valign="middle" align="left">Invoice 2</td>
			  	<td width="1%" align="left">&nbsp;</td>
			</tr>
			<tr>
			  	<td></td>
			  	<td valign="middle"><input name="invType" type="radio" value="2"></td>
			  	<td valign="middle" align="left">Receipt</td>
			  	<td width="1%" align="left">&nbsp;</td>
			</tr>
			<tr>
			  	<td></td>
			  	<td valign="middle"><input name="invType" type="radio" value="3"></td>
			  	<td valign="middle" align="left">Tanda Terima</td>
			  	<td width="1%" align="left">&nbsp;</td>
			</tr>
		</table>
	</form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>

<c:choose>
	<c:when test="${billing_edit.billing.billingType.code == 'MANUAL'}">
		<c:set var="printUrl" value="/page/billingmanualprint.htm?id=${billing_edit.billing.id}" />
	</c:when>
	<c:otherwise>
		<c:set var="printUrl" value="/page/billingprint.htm?id=${billing_edit.billing.id}" />
	</c:otherwise>
</c:choose>

<script type="text/javascript">
function printMode()
{
	document.bilingPrintOptionForm.action = '<c:url value="${printUrl}" />';
	document.bilingPrintOptionForm.submit();
}
</script>