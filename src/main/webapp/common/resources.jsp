<!-- STYLESHEET RESOURCE -->
<fmt:setLocale value="en_US" scope="session"/>
<link rel="icon" type="image/png" href="<c:url value='/assets/images/title-logo.png'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui/jquery-ui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui/jquery-ui.theme.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui/jquery-ui.structure.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/menuzord/menuzord.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/sirius.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/sirius-jquery-fixes.css'/>">
<style type="text/css" media="screen">
	@import url("<c:url value='/assets/sirius.css'/>");
	@import url("<c:url value='/assets/sirius-print.css'/>");
</style>
<style type="text/css" media="print">
	@import url("<c:url value='/assets/sirius-print.css'/>");
</style>
<style type="text/css">
	.dojoMenuBar2 {
		border-top:1px solid #d4d4d4;
	}
	.monthLabelContainer,.yearLabel,.calendarBodyContainer thead tr td,.calendarBodyContainer tbody tr td {
		font:bold 10px Helvetica, Arial, Verdana, sans-serif !important;
	}
</style>

<!-- JAVASCRIPT RESOURCE -->
<script type="text/javascript">
	var djConfig = {isDebug: false,extraLocale: ['en-us']};
	var base_url = "<%=basePath%>";
	const $popupid = '${param.popupid}';
	const submenu = ${jsonUtil:toJson(menu)};
</script>

<script type="text/javascript" src="<c:url value='/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.form.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.hotkeys.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.init.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.dialogextend.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/menuzord.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/format.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/currency.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/product.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/barcode.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/clock.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/table.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/dialog.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/list.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/excellentexport.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/PostalAddress.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/container.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/facility.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/party.js'/>"></script>
<script type="text/javascript">
	Clock.init("<fmt:formatDate value='${now}' pattern='dd-MM-yyyy|HH:mm:ss'/>");

	var $expop;

	if(!$expop)
		$expop = new Map();
</script>
