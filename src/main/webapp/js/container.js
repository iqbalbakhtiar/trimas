var Container = new Object();
Container.data = null;

Container.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupcontainerjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.container))
					Container.data = json.container;
			}
		}
	});
}

Container.loadDefault = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupcontainerdefaultjson.htm",
		data:{facility:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.container))
					Container.data = json.container;
				else
					Container.data = null;
			}
		}
	});
}