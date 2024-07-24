  $org = $('#org');
		$org.focus(function(){
			$.ajax({
				url:"<c:url value='/page/popupsalespersonjsonview.htm'/>",
				data:{organization:$org.val()},
				method : 'GET',
				dataType : 'json',
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$bill = $('#salesPerson');
							$bill.empty();
							
							$tax = $('#secondarySales');
							$tax.empty();

							$.each(json.partys,function(idx,obj){
											$opt = $('<option></option>');
											$opt.attr('value',obj.id);
											$opt.html(obj.fullName);
											
                      $opt.appendTo($bill);
                      $opt.appendTo($tax);
								});
						}
					}
				}
    	});
    });