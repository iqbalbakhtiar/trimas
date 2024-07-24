var Loading = new Object();
Loading.val = 0;
Loading.acc = 0;
Loading.total = $('#size').attr('value');
Loading.inc = (1 / parseFloat(Loading.total) *100);

Loading.init = function($plugin)
{	
	$progessbar = $("#progressbar");
	$progresslabel = $(".progress-label");
	
	$progessbar.show();
	
	Loading.val = Loading.val + Loading.inc;

	$progessbar.progressbar({
		value: Loading.val,
		change: function() {
			$progresslabel.text(Loading.acc + " / " + Loading.total);
		}
	});
			
	Loading.acc = Loading.acc + 1;
	
	if(parseFloat(Loading.val) >= 99 || parseFloat(Loading.total) == 0)
		$progessbar.hide();
}