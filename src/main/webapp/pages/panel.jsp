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

<title>Accounting Main Panel</title>


<script type="text/javascript">

	var djConfig = {isDebug: true};
</script>
<script type="text/javascript" src="scripts/dojo/dojo.js"></script>

<script language="JavaScript" type="text/javascript">
    dojo.require("dojo.widget.TabPane");
	dojo.require("dojo.widget.ContentPane");
</script>
<style>

div.main_container {
	background-color: Azure;
	border: 2px solid black;
	padding: 8px;
	margin: 4px;
}

div.inner_container{
    border: none
    background-color: white;
    padding: 8px;
    margin: 4px;


}

div.statusPanel {
	border: none;
	margin: 1px;
	padding: 1px;
}

.toolbar {
	margin: 4px;
	padding: 2px;
}


</style>
</head>
<body>

<div class="main_container" dojoType="Container"> Header

	<div id="parentTabPane" dojoType="TabPane" style="width: 600px; height: 400px;">
		<div id="firstPane" dojoType="ContentPane" label="First Pane">
			Hi from the first Content Pane!
		</div>
	</div>

	<div class="inner_container" dojoType="Container" id="upper_container">
		<a href="/AccountingService/page/coaPanel.htm">Chart Of Account Panel</a>
    </div> 
	
    <div id="lower_container" dojoType="TabPane" style="border:none;height:20em;background-color:Azure" selectedTab="transfer_clearing_tab">

		<div class="anu" id="cash_receipt_tab" dojoType="ContentPane" label="Cash Receipt">
			Cash Receipt:&nbsp;<input id="cash_receipt_input" />
		</div>
		<div id="transfer_clearing_tab" dojoType="ContentPane" label="Transfer/Clearing receipts">
			Transfer:&nbsp;<input id="transfer_input" />
		</div>
		<div id="receipt_app_tab" dojoType="ContentPane" label="Receipt Application">
			Reciept:&nbsp;<input id="receipt_input" />
		</div>

      </div>

Footer

</div>


</body>
</html>
