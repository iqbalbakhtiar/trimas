<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
	<style type="text/css" media="print">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
	<script type="text/javascript">
		function printPage()
		{
			print();
		}
	</script>
</head>
<body>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>
	</div>

	<div id="se-navigator">
	<div class="area" dojoType="Container" id="quick_link_container">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb}</td>
			<td width="40%" align="right">
				<%@ include file="/common/welcome.jsp"%>
			</td>
		</tr>
		</table>
	</div>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">

						<div class="area" dojoType="Container" id="quick_link_container">

							<h1 class="page-title">${pageTitle}</h1>

							<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/journalBatchingList.htm'/>"><span><spring:message code="sirius.list"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
							</div>
						</div>
						<div class="main-box">
							<div class="main_container" >
								<div class="pageTitle"><strong class="margin-left">${pageTitle}</strong></div>
								<br/>
								<br/>
                                <table width="100%" border="0" cellpadding="1" cellspacing="0" class="margin-left" >
                                <tr class="CSS">
                                    <td height="28px" align="center" class="border-top border-left border-bottom"><spring:message code="journalentry.date"/></td>
                                    <td align="left" class="border-top border-left border-bottom"><spring:message code="journalentry.name"/> / <spring:message code="journaldetail.account"/></td>
                                    <td align="center" class="border-top border-left border-bottom"><spring:message code="journalentry.debet"/></td>
                                    <td align="center" class="border-top border-left border-bottom"><spring:message code="journalentry.credit"/></td>
                                    <td align="center" class="border-top border-left border-bottom border-right"><spring:message code="journalentry.currency"/></td>
                                </tr>
                                <c:set var="credit" value="0"/>
                                <c:set var="debit" value="0"/>
                                <c:forEach items='${journalEntrys}' var='journalBatching'>
                                <tr class="CSS1">
                                    <td height="29" nowrap class="widthPrint border-left">
                                        <fmt:formatDate value='${journalBatching.entryDate}' pattern='dd-MM-yyyy'/>
                                    </td>
                                    <td height="29" nowrap class="widthPrint border-left">
                                        <c:out value='${journalBatching.code} - ${journalBatching.name}'/>
                                    </td>
                                    <td align="right" class="border-left"></td>
                                    <td align="right" class="border-left"></td>
                                    <td align="right" class="border-left border-right"></td>
                                </tr>
                                <c:forEach items='${journalBatching.details}' var='item'>
                                <tr>
                                    <td class="border-left"></td>
                                    <td class="border-left">
                                        <c:out value='${item.account.code}'/>-<c:out value='${item.account.name}'/>
                                    </td>
                                    <td align="right" class="border-left">
                                        <c:if test="${item.postingType == 'DEBET'}">
                                            <fmt:formatNumber value='${item.amount}' pattern=',#00.00'/>
                                            <c:set var="debit" value="${item.amount+debit}"/>
                                        </c:if>&nbsp;
                                    </td>
                                    <td align="right" class="border-left">
                                        <c:if test="${item.postingType == 'CREDIT'}">
                                            <fmt:formatNumber value='${item.amount}' pattern=',#00.00'/>
                                            <c:set var="credit" value="${item.amount+credit}"/>
                                        </c:if>&nbsp;
                                    </td>
                                    <td width="5%" align="center" class="border-left border-right"><c:out value='${journalBatching.currency.symbol}'/></td>
                                </tr>
                                </c:forEach>
                                </c:forEach>
                                <tr class="CSS1" class="border-top">
                                    <td colspan="2" height="29" align="right" class="border-top"><strong><spring:message code="journalentry.total"/> :</strong>&nbsp;&nbsp;</td>
                                    <td align="right" class="border-top">
                                        <fmt:formatNumber value="${debit}" groupingUsed='true' pattern=',##0.00'/>&nbsp;&nbsp;
                                    </td>
                                    <td  align="right" class="border-top">
                                        <fmt:formatNumber value="${credit}" groupingUsed='true' pattern=',##0.00'/>&nbsp;&nbsp;
                                    </td>
                                    <td class="border-top"></td>
                                </tr>
                                </table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
