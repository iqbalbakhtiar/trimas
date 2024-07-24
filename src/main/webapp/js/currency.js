var Currency = new Object();

Currency.init = function($plugin)
{	
	$dateval = $('#date').val();
	if(!$dateval)
	{
		alert('<spring:message code="sirius.date"/> <spring:message code="notif.empty"/> !!!');
		return;
	}

	$exchange = $('#exchange');
	$trxrate = $('#trxrate');

	$.ajax({
		url:$('base').attr('href')+"page/exchangemanagementloadrate.htm",
		data:{from:$('#currency').val(),to:$('#default').val(),date:$dateval,type:$('#type').val()},
		method : 'GET',
		async: false,
		dataType : 'json',
		success : function(json) {
			if(json.status == "OK")
			{	
				if($exchange && json.exchange)
					$exchange.val(json.exchange.id);
					
				$trxrate.val("");
				if($trxrate && json.exchange)
					$trxrate.val(json.exchange.rate.numberFormat('#,##0.000'));
					
				if($plugin)
					$plugin.execute();
			}
		}
	});
	
	$('#dynamic').html($('#currency :selected').text());
}		