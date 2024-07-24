var Bank = new Object();
Bank.data = null;

Bank.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupbankaccountjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{
				if(!$.isEmptyObject(json.bank))
					Bank.data = json.bank;
			}
		}
	});
};