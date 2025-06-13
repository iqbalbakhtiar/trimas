<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<link rel="icon" type="image/png" href="<c:url value='/assets/images/title-logo.png'/>">
</head>
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp"%>
</div>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>
        <div id="se-navigator">
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
                                <a class="item-button-back" href="<c:url value='/page/inventoryledgersummarypre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
                                <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
                                <a class="item-button-export-xls" href="<c:url value='/page/inventoryledgersummaryexcell.xls?organization=${criteria.organization}&facility=${criteria.facility}'/>"><span><spring:message code="sirius.export"/></span></a>
                                <a class="item-button-rprev" href="javascript:prev();"><span><spring:message code="sirius.prev"/></span></a>
                                <a class="item-button-rnext" href="javascript:next();"><span><spring:message code="sirius.next"/></span></a>
                            </div>
                        </div>

                        <div class="main-box">
                            <div id="main_container">
                                <%@include file="inventoryLedgerSummaryPrint.jsp" %>
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
<script type="text/javascript">	
	function printPage(){
   		print();
	}

	function prev()
	{
		window.location = "<c:url value='/page/inventoryledgersummaryview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&lotCode=${criteria.lotCode}&productCategory=${criteria.productCategory}&product=${criteria.product}&showBale=${criteria.showBale}&dateFrom='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>";
	}
	
	function next()
	{
		window.location = "<c:url value='/page/inventoryledgersummaryview.htm?organization=${organization.id}&facility=${criteria.facility}&container=${criteria.container}&lotCode=${criteria.lotCode}&productCategory=${criteria.productCategory}&product=${criteria.product}&showBale=${criteria.showBale}&dateFrom='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>";
	}
</script>
