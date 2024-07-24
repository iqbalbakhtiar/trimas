var Barcode = new Object();
Barcode.data = null;

Barcode.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupbarcodejson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.barcode))
					Barcode.data = json.barcode;
			}
		}
	});
};