/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
var PostalAddress = new Object();
PostalAddress.data = null;

PostalAddress.init = function($plugin)
{
	$party = $plugin;
	$party.focus(function() {

		$.ajax({
			url:$('base').attr('href')+'page/popuppostaladdressjsonview.htm',
			data:{party:$party.val()},
			method : 'GET',
			dataType : 'json',
			success : function(json) {
				if(json)
				{
					if(json.status == 'OK')
					{
						$bill = $('#billTo');
						if($bill)
						{	
							$bill.empty().prepend("<option value=''/>");
							
							$.each(json.addresses,function(idx,obj){
								$.each(obj.postalTypes,function(tIdx,tObj){
									if(tObj.type == 'OFFICE' && $bill.val() != obj.postalId)
									{
										$opt = $('<option></option>');

										if(obj.isDefault)
											$opt.attr('selected','selected');

										$opt.attr('value',obj.postalId);
										$opt.html(obj.postalAddress);
										$opt.appendTo($bill);
									}
								});
							});
						}

						$ship = $('#shipTo');
						if($ship)
						{
							$ship.empty().prepend("<option value=''/>");
							
							$.each(json.addresses,function(idx,obj){
								$.each(obj.postalTypes,function(tIdx,tObj){
									if(tObj.type == 'SHIPPING' && $ship.val() != obj.postalId)
									{
										$opt = $('<option></option>');

										if(obj.isDefault)
											$opt.attr('selected','selected');

										$opt.attr('value',obj.postalId);
										$opt.html(obj.postalAddress);									
										$opt.appendTo($ship);
									}
								});
							});
						}

						$tax = $('#taxTo');
						if($tax)
						{
							$tax.empty().prepend("<option value=''/>");
							
							$.each(json.addresses,function(idx,obj){
								$.each(obj.postalTypes,function(tIdx,tObj){
									if(tObj.type == 'TAX' && $tax.val() != obj.postalId)
									{
										$opt = $('<option></option>');
										
										if(obj.isDefault)
											$opt.attr('selected','selected');

										$opt.attr('value',obj.postalId);
										$opt.html(obj.postalAddress);									
										$opt.appendTo($tax);
									}
								});
							});
						}
						
						$party.change();
					}
				}
			}
		});
	});
}

PostalAddress.load = function(id) {
    $.ajax({
        url: $('base').attr('href') + 'page/popuppostaladdressjson.htm',
        data: { id: id },
        method: 'GET',
        dataType: 'json',
        async: false,
        success: function(json) {
            if (json) {
                if (json.status === 'OK') {
                    PostalAddress.data = json.postalAddress;
                } else {
                    alert('Error: ' + json.message);
                }
            } else {
                alert('No data received.');
            }
        },
        error: function(xhr, status, error) {
            alert('AJAX error: ' + error);
        }
    });
};