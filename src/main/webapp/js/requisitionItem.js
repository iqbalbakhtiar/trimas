var RequisitionItem = new Object();
RequisitionItem.data = null;

RequisitionItem.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popuppurchaserequisitionitemjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.requisitionItem))
					RequisitionItem.data = json.requisitionItem;
			}
		}
	});
};