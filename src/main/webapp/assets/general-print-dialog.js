var dialog;
function init(e)
{
	dialog = dojo.widget.byId("DialogContent");
	var cancel = document.getElementById("cancel");
	dialog.setCloseControl(cancel);
}
dojo.addOnLoad(init);

function showDialog(url)
{
	var printURL = document.getElementById("printURL");
	if(printURL == null)
		return;

	printURL.value = url;
	dialog.show();
}

function showPrint()
{
	document.printForm.action = document.getElementById("printURL").value;
	document.printForm.submit();
}