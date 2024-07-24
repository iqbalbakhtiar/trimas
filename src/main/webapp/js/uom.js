var Cubication = new Object();
Cubication.cubic = 0.00003532;
Cubication.dozen = 12;

Cubication.cube = function($plugin)
{	
	$length = $("#length").val();
	$width = $("#width").val();
	$height = $("#height").val();
	$cubic = $("#cubic").val();
	$area = parseFloat($length * $width * $height);
	
	if($area > 0)
		$cubic = parseFloat($area * Cubication.cubic);
	
	$('.cubic').val(parseFloat($cubic).numberFormat('#.000'));
}

Cubication.uncube = function($plugin)
{	
	$cubic = $('#cubic').val();
	
	$alllength = $(".length");
	$allwidth = $(".width");
	$allheight = $(".height");
	$allcubic = $('.cubic');
	
	$side = 0;
	
	$alllength.val($side.numberFormat('#.00'));
	$allwidth.val($side.numberFormat('#.00'));
	$allheight.val($side.numberFormat('#.00'));

	$allcubic.val(parseFloat($cubic).numberFormat('#.000'));
}

Cubication.doze = function($plugin)
{	
	$outbox = $("#outbox");
	$inbox = $("#inbox");
	$outlook = $(".outlook");
	$inlook = $(".inlook");
	
	$outlook.val(parseFloat($outbox.val()) / parseFloat(Cubication.dozen));
	$inlook.val(parseFloat($inbox.val()) / parseFloat(Cubication.dozen));
}	