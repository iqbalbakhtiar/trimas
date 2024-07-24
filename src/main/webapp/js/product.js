var Product = new Object();
Product.data = null;

Product.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupproductjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.product))
					Product.data = json.product;
			}
		}
	});
};

Product.inventory = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupinventoryitemjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.inventory))
					Product.data = json.inventory;
			}
		}
	});
};

Product.price = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupproductpricejson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.price))
					Product.data = json.price;
			}
		}
	});
};
