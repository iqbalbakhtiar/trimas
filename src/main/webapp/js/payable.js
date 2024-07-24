var Payable = new Object();
Payable.data = null;

Payable.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupinvoicejson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.payable))
					Payable.data = json.payable;
			}
		}
	});
};