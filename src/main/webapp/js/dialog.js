var dlg;
function init(e)
{
	dlg = dojo.widget.byId("DialogContent");
	var btn = document.getElementById("close");
	if(btn!=null)
		dlg.setCloseControl(btn);
}
dojo.addOnLoad(init);

/*DEPRECATED
function showDialog(url)
{
	var deleted = document.getElementById("deletedId");
	if(deleted == null)
		return;

	deleted.value = url;
	dlg.show();
}*/
function showDialog(url, message){
	deleteDialog(url, message);
}
