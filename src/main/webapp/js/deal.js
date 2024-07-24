var Deal = new Object();
Deal.rate = 1.03;
Deal.plus = 0.5;
Deal.mod = 0.96;
Deal.subsidy = 0.05;
Deal.percent = 100;
Deal.max = 0.975;
Deal.min = 0.95;
Deal.fac = 0.9;
Deal.work = 16.5;
Deal.disc = 0.0105;

//must import uom.js first
Deal.calculate = function($plugin)
{
	$trxrate = Format.toFloat($("#trxrate").val());
	$outboxMain = parseFloat($("#outbox").val());
	
	$kubikasi = Format.toFloat($("#kubikasi").val());
	$cubicplus = parseFloat((parseFloat($("#cubic").val()) + Deal.plus).toFixed(2));

	$biayasni = Format.toFloat($("#biayasni").val());
	$beamasuk = Format.toFloat($("#beamasuk").val());
	
	$amountExtDef = Format.toFloat($("#amountExtDef").val());
	$amountInt = Format.toFloat($("#amountIn").val());

	$packagePrice = Format.toFloat($("#packagePrice").val());
	$ongkoslakban = Format.toFloat($("#ongkoslakban option:selected").text());
	$ongkoslistrik = Format.toFloat($("#ongkoslistrik option:selected").text());
	$estimasibs = parseFloat($("#estimasibs option:selected").text());
	$workCost = parseFloat($("#workCost").val());
	$otherCost = parseFloat($("#otherCost").val());
	
	$contcost = ($beamasuk + $biayasni + $amountExtDef + $amountInt) / $kubikasi;
	$oriprice =  Format.toFloat($("#purchasePrice").val()) * Cubication.dozen;
	$price = $oriprice * $trxrate * Deal.rate;
	$modori = $contcost * $cubicplus * Cubication.dozen / $outboxMain;
	$modplus =  Math.round(($price + $modori) / Deal.mod);

	$('#contcost').val($contcost.numberFormat('#,#.##'));
	$('#oriprice').val($oriprice.numberFormat('#,#.0000'));
	$('#price').val($price.numberFormat('#,#.##'));
	$('#modori').val($modori.numberFormat('#,#.##'));
	$('#cubicori').val($cubicplus.numberFormat('#.00'));
	
	$('#modoriginal').val($modplus.numberFormat('#,#.00'));
	
	Deal.component();
	
	$cubicunload = Format.toFloat($("#cubicunload").val());
	$cubicdiscre = $cubicplus - $cubicunload;
	$modunl = $contcost * $cubicunload * Cubication.dozen / $outboxMain;
	$modpack = ($packagePrice + $ongkoslakban) * Cubication.dozen / $outboxMain; 
	$subsidy =($price + $modunl) * ($estimasibs / Deal.percent);
	$other = ($workCost + $ongkoslistrik + $otherCost) * Cubication.dozen;

	$production = $modpack + $subsidy + $other;
	$mix = $modunl + $production;
	
	$('#modunl').val($modunl.numberFormat('#,#.##'));
	
	$('#cubicdiscre').val($cubicdiscre.numberFormat('#.00'));
	$('#cubicpercent').val(($cubicdiscre / $cubicplus * Deal.percent).numberFormat('#.00'));
	$('#modpack').val($modpack.numberFormat('#,#.##'));
	$('#subsidy').val($subsidy.numberFormat('#,#.##'));
	$('#other').val($other.numberFormat('#,#.##'));
	$('#production').val($production.numberFormat('#,#.##'));
	$('#mix').val($mix.numberFormat('#,#.##'));
	
	$modunload = Math.round(($price + $modunl + $production) / Deal.mod);
	$moddiscre = $modplus - $modunload;

	if(!$modunload)
	   $modunload = 0;
	
	$('#modunloading').val($modunload.numberFormat('#,#.00'));
	$('#moddiscre').val($moddiscre.numberFormat('#.00'));
	$('#modpercent').val(($moddiscre / $modplus * Deal.percent).numberFormat('#.00'));
	
	$sellprice = Format.toFloat($('#normalPrice').val()) * Cubication.dozen;
	$maxprice = $sellprice * Deal.max;
	$minprice = $sellprice * Deal.min;
	$distributorprice = Format.toFloat($('#distributorPrice').val()) * Cubication.dozen;
	$oriselldufan = Math.round($modplus * Deal.mod / Deal.fac);
	$unlselldufan = Math.round($modunload * Deal.mod / Deal.fac);

	$('#orisellpricelist').val($sellprice.numberFormat('#,#.00'));
	$('#unlsellpricelist').val($sellprice.numberFormat('#,#.00'));
	
	$('#orisellmax').val($maxprice.numberFormat('#,#.00'));
	$('#unlsellmax').val($maxprice.numberFormat('#,#.00'));
	
	$('#orisellmin').val($minprice.numberFormat('#,#.00'));
	$('#unlsellmin').val($minprice.numberFormat('#,#.00'));
	
	$('#oriselldistributor').val($distributorprice.numberFormat('#,#.00'));
	$('#unlselldistributor').val($distributorprice.numberFormat('#,#.00'));
	
	$('#oriselldufan').val($oriselldufan.numberFormat('#,#.00'));
	$('#unlselldufan').val($unlselldufan.numberFormat('#,#.00'));
	
	$(".orisell").each(function () {
		$value = Format.toFloat($('#orisell'+$(this).attr('kind')).val());

		$(this).val($value.numberFormat('#,#.00'));
	});
	
	$(".unlsell").each(function () {
		$value = Format.toFloat($('#unlsell'+$(this).attr('kind')).val());
		
		$(this).val($value.numberFormat('#,#.00'));
	});
	
	$(".orimargin").each(function () {
		$value = Math.round(Format.toFloat($('#orisell'+$(this).attr('kind')).val()) - $modplus);
		
		$(this).val($value.numberFormat('#,#.00'));
	});
	
	$(".unlmargin").each(function () {
		$value = Math.round(Format.toFloat($('#unlsell'+$(this).attr('kind')).val()) - $modunload);
		
		$(this).val($value.numberFormat('#,#.00'));
	});
	
	$(".oripercent").each(function () {
		$ori = Format.toFloat($('#orisell'+$(this).attr('kind')).val())
		$value = $ori != 0 ? Math.round(Format.toFloat($('#orimargin'+$(this).attr('kind')).val()) / $ori * Deal.percent) : 0;

		$(this).val($value.numberFormat('#.00'));
	});
	
	$(".unlpercent").each(function () {
		$unl = Format.toFloat($('#unlsell'+$(this).attr('kind')).val());
		$value = $unl != 0 ? Math.round(Format.toFloat($('#unlmargin'+$(this).attr('kind')).val()) / $unl * Deal.percent) : 0;

		$(this).val($value.numberFormat('#.00'));
	});
}

Deal.component = function($plugin)
{	
	$unload = 0;
	$convers = 0;
	$line = 0;
	$cubication = 0
	$inspect = true;

	$(".component").each(function (idx) {

		$index = $(this).attr('index');
		$idt = $(this).attr('id');
		
		$quantity = parseFloat($('#quantity'+$index).val());
		$length = parseFloat($('#length'+$index).val());
		$width = parseFloat($('#width'+$index).val());
		$height = parseFloat($('#height'+$index).val());
		$outbox = parseFloat($('#outbox'+$index).val());
		$convert = parseFloat($('#convert'+$index).val());

		if($('#decision').val() != 'ORIGINAL' && $inspect && idx == 0) {
			$('#outspec').val(parseFloat($outbox / parseFloat(Cubication.dozen)));
			$inspect = false;
			$line = $outbox;
		}

		$cubic = $length * $width * $height * Cubication.cubic;
		$cbced = $cubic * $quantity * $line / $outbox;
		$cbc = $cubic * $convert  / $outbox;

		$('#cubic'+$index).val($cubic.numberFormat('#.000'));
		$('#cbced'+$index).val($cbced.numberFormat('#.000'));
		$('#cbc'+$index).val($cbc.numberFormat('#.000'));
	});
	
	$(".cbc").each(function () {
		$value = parseFloat($(this).val());
		$conv = parseFloat($('#cbced'+$(this).attr('index')).val());
		$cube = parseFloat($('#cubic'+$(this).attr('index')).val());

		$convers = $convers + $conv;
		$unload  = $unload + $value;
		$cubication = $cubication + $cube;
	});
	
	$("#totalCubic").html($cubication.numberFormat('#.000'));
	$("#totalCbd").html($convers.numberFormat('#.000'));
	$("#totalCbc").html($unload.numberFormat('#.000'));

	if($unload > 0)
		$unload = $unload + Deal.plus;
		
	$('#cubiconvert').val(parseFloat($convers).toFixed(3));
	$('#cubicunload').val(parseFloat($unload).toFixed(3));
}

Deal.adjust = function($plugin)
{
	$('.adjust').each(function() {
		$value = parseFloat($(this).val());
		$id = $(this).attr('id').substring(0,3);

		$marginadjust = Math.round($value - ($id == "ori" ? $modplus : $modunload));
		$percentadjust = $value != 0 ? Math.round($marginadjust / $value * Deal.percent) : 0;
			
		$('#'+$id+'marginadjust').val($marginadjust.numberFormat('#,#.00'));
		$('#'+$id+'percentadjust').val($percentadjust.numberFormat('#.00'));
	});
}

Deal.calculator = function($plugin)
{	
	$currentmodal = $('#currentmodal');
	$decision = $('#decision');
	$key = $decision.val().substring(0,3).toLowerCase();
	
	if($decision.val() == 'ORIGINAL')
		$currentmodal.val($modplus.numberFormat('#,#.00'));
	else
		$currentmodal.val($modunload.numberFormat('#,#.00'));

	$outbox = parseFloat($(".outlook").val());
	$current = Format.toFloat($currentmodal.val());
	
	$totalquantity = 0;
	$totalmodal = 0;
	
	$totalamount = 0;
	$totalamounxt = 0;
	
	$totalmargin = 0;
	$totalmarginxt = 0;

	$(".quantity").each(function () {
		$sellprice = $('#sellprice'+$(this).attr('kind'));
		
		$qty = parseFloat($(this).val());
		$adjust = Format.toFloat($('#'+$key+'selladjust').val());
		$undjust = Format.toFloat($('#'+$key+'sell'+$(this).attr('kind')).val());

		$value = $sellprice.attr('flag')=='true' && $adjust > 0 ? $adjust : $undjust;
		$valuext = $value-($value*Deal.disc); 
		
		$amount = Math.round($qty * $outbox * $value);
		$amounxt = Math.round($qty * $outbox * $valuext);
		
		$modal = Math.round($qty * $outbox * $current);
		
		$margin = $amount - $modal;
		$marginxt = $amounxt - $modal;
		
		$percent = $margin / $amount * Deal.percent;
		$percentxt = $marginxt / $amounxt * Deal.percent;
		
		$sellprice.val($value.numberFormat('#,#.00'));
		$('#amount'+$(this).attr('kind')).val($amount.numberFormat('#,#.00'));
		$('#modal'+$(this).attr('kind')).val($modal.numberFormat('#,#.00'));
		$('#margin'+$(this).attr('kind')).val($margin.numberFormat('#,#.00'));
		$('#percent'+$(this).attr('kind')).val($percent.numberFormat('#,#.00'));
		
		$('#sellprice'+$(this).attr('kind')+'ext').val($valuext.numberFormat('#,#.00'));
		$('#amount'+$(this).attr('kind')+'ext').val($amounxt.numberFormat('#,#.00'));
		$('#margin'+$(this).attr('kind')+'ext').val($marginxt.numberFormat('#,#.00'));
		$('#percent'+$(this).attr('kind')+'ext').val($percentxt.numberFormat('#,#.00'));
		
		$totalquantity = $totalquantity + $qty;
		$totalmodal = $totalmodal + $modal;
		
		$totalamount = $totalamount + $amount;		
		$totalmargin = $totalmargin + $margin;
		
		$totalamounxt = $totalamounxt + $amounxt;
		$totalmarginxt = $totalmarginxt + $marginxt;
	});
	
	$plan = $totalquantity;
	
	if($('#decision').val() != 'ORIGINAL')
		$plan = $totalquantity * $outbox / parseFloat($('#outspec').val());
	
	$('#totalquantity').val($totalquantity.numberFormat('#,#.00'));
	$('#totalmodal').val($totalmodal.numberFormat('#,#.00'));
	
	$('#totalamount').val($totalamount.numberFormat('#,#.00'));
	$('#totalamountxt').val($totalamounxt.numberFormat('#,#.00'));

	$('#totalmargin').val($totalmargin.numberFormat('#,#.00'));
	$('#totalmarginxt').val($totalmarginxt.numberFormat('#,#.00'));
	
	$('#totalpercent').val(($totalmargin / $totalamount * Deal.percent).numberFormat('#.00'));
	$('#totalpercentxt').val(($totalmarginxt / $totalamounxt * Deal.percent).numberFormat('#.00'));
	
	$('#plan').val($plan.numberFormat('#.00'));
}

Deal.place = function($plugin)
{	
	$plan = parseFloat($('#plan').val());
	
	$totalquantity = 0;
	
	$(".delivery").each(function () {
		$qty = parseFloat($(this).val());
		
		$totalquantity = $totalquantity + $qty;
	});
	
	$('#left').val(($plan-$totalquantity).numberFormat('#,#.00'));
}

Deal.cost = function($plugin)
{
	$('#otherCost').val(parseFloat($('#workCost').val() * Deal.work / Deal.percent).numberFormat('#.00'));
}