var Party = new Object();
Party.data = null;

Party.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupcompanystructurerolebasedjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.party))
					Party.data = json.party;
			}
		}
	});
}

Party.relation = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popuppartyrelationshipjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.relation))
					Party.data = json.relation;
			}
		}
	});
}
