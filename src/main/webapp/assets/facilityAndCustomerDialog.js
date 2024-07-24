var dialog;
function init(e)
{
	dialog = dojo.widget.byId("FacilityAndCustomerDialog");
	var btn = document.getElementById("close");
	dialog.setCloseControl(btn);
}
dojo.addOnLoad(init);

function showAddDialog(url)
{
	document.getElementById("url").value=url;
	dialog.show();
}