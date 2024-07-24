<%@ include file="/pages/include.jsp"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<base href="<%=basePath%>">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Sirius ERP</title>

<script type="text/javascript">

	var djConfig = {isDebug: false};

</script>
<script type="text/javascript" src="scripts/dojo/dojo.js"></script>
<script language="JavaScript" type="text/javascript">

	dojo.require("dojo.widget.FloatingPane");
	dojo.require("dojo.widget.ContentPane");
	dojo.require("dojo.widget.LayoutPane");
	dojo.require("dojo.widget.SplitPane");
	dojo.require("dojo.widget.ResizeHandle");
	dojo.require("dojo.widget.Tree");
	dojo.require("dojo.widget.Accordion");

</script>
<style>
html, body {
	/* apparently important for floating pane resizing performance */
	height: 100%;
	width: 100%;
}

div.statusBar {
	background-color: ThreeDFace;
	height: 28px;
	padding: 1px;
}

div.statusPanel {
	background-color: ThreeDFace;
	border: 1px solid;
	border-color: ThreeDShadow ThreeDHighlight ThreeDHighlight ThreeDShadow;
	margin: 1px;
	padding: 2px 6px;
}

.dojoFloatingPaneClient {
	margin: 0 !important;
	padding: 0 !important;
	border: 0 !important;
}

body .dojoTree {
	margin: 4px !important;
}

.mainClient {
	border: 1px solid;
	border-color: ThreeDShadow ThreeDHighlight ThreeDHighlight ThreeDShadow;
	margin: 2px 2px 0px 2px;
	background-color: ThreeDFace;
}

</style>
</head>
<body>

<div dojoType="FloatingPane"
	widgetId="testWindow"
	title="Sirius ERP"
	constrainToContainer="1"
	style="width: 600px; height: 500px; left: 50px; top: 50px;"
	contentWrapper="layout"
>
	<div dojoType="SplitPane"
		orientation="horizontal"
		sizerWidth="5"
		activeSizing="1"
		layoutAlign="client"
		debugName="split pane 1"
		class="mainClient"
		minHeight="100"
	>
		<div dojoType="Accordion"
			orientation="vertical"
			activeSizing="1"
			style="border: none; float: left; margin-right: 1px;  width: 40px; height: 500px;"
		>
			<div dojoType="AccordionPanel" >
				<div dojoType="Label">
					Administration
				</div>
				<div>	==	Empty == </div>
				
			
			</div>
			<div dojoType="AccordionPanel" 
				label="Financial Accounting"
				open="true">
				<div>
				   <p><a href="<c:url value='/page/coaPanel.htm'/>">Chart Of Account Setting</a></p>
				   <p><a href="<c:url value='/page/accPeriodGroupPanel.htm' />">Accounting Period Setting</a></p>
				   <p><a href="<c:url value='/page/journalEntryPanel.htm' />">Journal Entry</a></p>
				
				</div>
			</div>
			<div dojoType="AccordionPanel">
							<div dojoType="Label">
								Sales
							</div>
							
						<div>	==	Empty == </div>
							
						
			</div>
			
                      </div>

		<div dojoType="SplitPane"
			orientation="vertical"
			sizerWidth="5"
			activeSizing="1"
			layoutAlign="client"
			debugName="split pane 2"
			sizeMin="400" sizeShare="3" 
		>

			<div dojoType="ContentPane" sizeMin="30" sizeShare="30">
				Quick
			</div>

			<div dojoType="LayoutPane" 
				sizeMin="400" sizeShare="70"
				style="margin: 0; padding: 0;"
			>

				<div dojoType="ContentPane" layoutAlign="top" style="background-color: #b39b86; padding: 8px;">
					panel top<br />go here
				</div>

				<div dojoType="ContentPane" layoutAlign="client" style="background-color: #f5ffbf; padding: 10px;">
					panel middle<br />
				</div>

			</div>
		</div>  <!-- end vertical split pane -->
	</div>  <!-- end horizontal split pane -->

	<div dojoType="LayoutPane" layoutAlign="bottom" class="statusBar">

		<div dojoType="ContentPane" layoutAlign="left" class="statusPanel">
			panel 1
		</div>
		<div dojoType="ContentPane" layoutAlign="left" class="statusPanel">
			panel 2
		</div>
		<div dojoType="ContentPane" layoutAlign="client" class="statusPanel" style="padding-right: 0px; z-index: 1;" minWidth="100">
			panel 3
		</div>
		<div dojoType="ResizeHandle" targetElmId="testWindow"></div>
	</div>

</div>


</body>
</html>
