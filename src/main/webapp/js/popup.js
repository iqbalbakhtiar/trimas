function closepopup() {
	let id = $expop.get($expop.size);	

	$('.ui-widget-overlay').remove();
	$("#" + id).parent().remove();
	$("#" + id).remove();
	
	$expop.delete($expop.size);
}

function openpopup(uri) {
	let url = encodeURI(uri);
	let id = "popup" + Math.floor(Math.random());

	$("body").append($('<div id="' + id + '"></div>'));
	let $openup = $("#" + id);
	$openup.attr("url", url);
	$openup.dialog({
		title : $('title').html().replace('&gt;', '>'),
		stack : false,
		autoOpen : false,
		modal : true,
		width : 850,
		height : 620,
		close : function() {
			closepopup();
		}
	}).dialogExtend({
		"maximize" : false,
		"dblclick" : "maximize",
		"icons" : {
			"maximize" : "ui-icon-circle-plus",
			"restore" : "ui-icon-bullet"
		}
	}).html("<img src='"
		+ base_url
		+ "/assets/images/loading.gif' style='position:relative;left:"
		+ ((850 / 2) - 60) + "px;top:" + ((620 / 2) - 150)
		+ "px;'/>")
		.load(url + "&popupid=" + id, function() {
			initialize_widget();
			$("#ui-dialog-title-" + id).text($("#" + id).find("title").text());
			$("#" + id).find('#se-top, #welcome, .item-button-list, .save-and-new').hide();
	}).focus();

	$openup.dialog("open");
	$openup.css("z-index","10");

	$expop.set(($expop.size + 1),id);
}