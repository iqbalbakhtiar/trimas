/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
var Facility = new Object();
Facility.data = null;

Facility.load = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupfacilityjson.htm",
		data:{id:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.facility))
					Facility.data = json.facility;
			}
		}
	});
}

Facility.alias = function($plugin)
{
	$.ajax({
		url:$('base').attr('href')+"page/popupfacilityaliasjson.htm",
		data:{alias:$plugin},
		method : 'POST',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if(!$.isEmptyObject(json.facility))
					Facility.data = json.facility;
			}
		}
	});
}

Facility.init = function($plugin)
{	
	$warehouse = $('#warehouse');
	if($warehouse.length==0){
		$warehouse=$(".warehouse");
	}
	$org = $('#org');

	$org.focus(function(e){
		if(e.target.value)
		{
			$warehouse.empty();

			$impl = "";
			$sales = $('#salesType');
			if($sales)
			{
				if($sales.val() == 'PO_REF')
					$impl = 'VIRTUAL';
				else if($sales.val() == 'STANDARD')
					$impl = 'REAL';
			}

			$.ajax({
				url:$('base').attr('href')+"page/popupfacilityjsonview.htm?organization="+e.target.value,
				data:{organization:$org.val(),implementation:$impl},
				method : 'GET',
				dataType : 'json',
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$warehouse.empty();
							
							$.each(json.facilitys,function(idx,obj){
								$opt = $('<option></option>');
								$opt.attr('value',obj.facilityId);
								$opt.html(obj.facilityName+" - "+obj.facilityAddress);
								
								$opt.appendTo($warehouse);
							});
							
							if($plugin)
								$plugin.execute();
							
							e.target.blur();
						}
					}
				}
			});
		}
	});
}