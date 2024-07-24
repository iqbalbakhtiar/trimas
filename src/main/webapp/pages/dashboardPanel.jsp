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
	<title>Dashboard</title>
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<%@ include file="/common/sirius-header.jsp"%><!-- /top menu -->
	<script type="text/javascript" src="assets/trees.js"></script>
	<script type="text/javascript">
		function displayContent(url)
		{
			var listPane = dojo.widget.getWidgetById("listPane");
			listPane.setUrl(url);
		}

		function redirectTo(url)
		{
			window.location = url;
		}		
	</script>	
	<!-- meta tags here -->
</head>
<!-- BEGIN OF BODY -->
<body>
<!-- top menu -->


<!-- rounded -->
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<!-- /rounded -->

<!-- main containers -->
<div id="se-containers">
	<!-- main menu -->
	<%@ include file="/common/sirius-menu.jsp"%>
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">Dashboard Report for Sales, Procurement, Delivery Management, Cash Management and AR</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<!-- start content-->
							<div dojoType="SplitContainer" style="width: 100%; height:460px" orientation="horizontal" sizerWidth="5" activeSizing="0" layoutAlign="client" doLayout="true">
								<div id="treePane" dojoType="Tree" toggle="wipe" toggleDuration="500" sizeMin="10" sizeShare="3">
									<div dojoType="TreeNode" title="Dashboard" childIconSrc="assets/icons/coa-parent.gif">
										<div dojoType="TreeNode" onTitleClick='displayContent("page/profitlossconsecutivechart.htm");' title="Sales" childIconSrc="assets/icons/group.gif"></div>
										<div dojoType="TreeNode" onTitleClick='displayContent("");' title="Procurement" childIconSrc="assets/icons/group.gif"></div>
										<div dojoType="TreeNode" onTitleClick='displayContent("page/overdueARReport.htm");' title="Account Receiveables" childIconSrc="assets/icons/group.gif"></div>
										<div dojoType="TreeNode" onTitleClick='displayContent("page/todayDelivery.htm");' title="Delivery Management" childIconSrc="assets/icons/group.gif"></div>
										<div dojoType="TreeNode" onTitleClick='displayContent("");' title="Cash Management" childIconSrc="assets/icons/group.gif"></div>
									</div>
								</div>
								<c:if test='${NEXT != null }'>
								<div id="listPane" dojoType="ContentPane" href="<c:url value='/page/${NEXT}'/>" style="padding: 5px; overflow: scroll;" refreshOnShow="true" >
								
								</div>
								</c:if>
							</div>
						<!-- end of content-->
					</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

	<!-- /footer -->
</div><!-- /main containers -->



</body>
<!-- END OF BODY -->
</html>
