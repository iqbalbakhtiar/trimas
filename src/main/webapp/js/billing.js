var Billing = new Object();
Billing.data = null;

Billing.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupbillingjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.billing))
					Billing.data = json.billing;
			}
		}
	});
};