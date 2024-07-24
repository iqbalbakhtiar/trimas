/*$(document).ready(function() {

	 MAIN MENU 
	$('#main-menu > li:has(ul.sub-menu)').addClass('parent');
	$('ul.sub-menu > li:has(ul.sub-menu) > a').addClass('parent');

	$('#menu-toggle').click(function() {
		$('#main-menu').slideToggle(300);
		return false;
	});

	$(window).resize(function() {
		if ($(window).width() > 700) {
			$('#main-menu').removeAttr('style');
		}
	});
});*/

$(window).load(function(){
    $("#main-menu > li").on('mouseenter mouseleave', function (e) {
    	
    	$('#main-menu > li:has(ul.sub-menu)').addClass('parent');
    	$('ul.sub-menu > li:has(ul.sub-menu) > a').addClass('parent');

        var elm = $('ul:first', this);
        var off = elm .offset();
        var l = off.left;
        var w = elm.width();
        var docH = $("#se-main-menu").height();
        var docW = $("#se-main-menu").width();
         var isEntirelyVisible = (l+ w <= docW);
                 
        if ( ! isEntirelyVisible ) {
            $(this).addClass('edge');
        } else {
            $(this).removeClass('edge');
        }
        
        
        
       
    });
});

