/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

//create some variables
var $confirmDialog, $dialog;

//import widget dojo
dojo.require("dojo.widget.*");

var format = {currency : "#,##0.####", decimal : "#,##0.##", number : "#,##0"};

function setInputDynamic(){
	$('.input-dynamic').each(function(idx, obj){
		var decimal = obj.getAttribute('decimal');

		$(obj).removeClass('input-number');
		$(obj).removeClass('input-decimal');
		
		if(decimal == 'false' || decimal == null)
			$(obj).addClass('input-number');
		else
			$(obj).addClass('input-decimal');
	});
	
	$(".input-number").bind(inputFormat);
	$(".input-decimal").bind(inputFormat);
	$(".inputbox").bind(inputBoxNext);
	$(".inputbox-small").bind(inputBoxNext);
}

function getFormat(obj) {
	if($(obj).hasClass('input-currency'))
		return format['currency'];

	if($(obj).hasClass('input-decimal'))
		return format['decimal'];

	return format['number'];
}

var inputBoxNext = {
	keyup : function(event) {
		if(event.keyCode==13){
			$nextrow = $(this).parents("tr").next().find($('[next='+$(this).attr("next")+']'));
			$nextrow.focus();
			$nextrow.select();
		}
	}
};

var inputFormat = {
	keydown : function(event) {
         if (((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) &&
        	!(event.keyCode == 46 || event.keyCode == 110 || event.keyCode == 13 || event.keyCode == 8 
        		|| event.keyCode == 109 ||  event.keyCode == 173 || event.keyCode == 9 
        			|| (event.keyCode > 187 && event.keyCode < 191))){
            event.preventDefault();
        }
	},
	keyup : function(event) {
		if(event.keyCode==188){
			if(!$(this).val().indexOf(".")===1)
				$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
		}else if(event.keyCode==13){
			$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
			$nextrow = $(this).parents("tr").next().find($('[next='+$(this).attr("next")+']'));
			$nextrow.focus();
			//$nextrow.val('');
			$nextrow.select();
		}
		else
			if(!$(this).val().indexOf(".")===1)
				$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
	},
	blur : function() {
		if($(this).val()!=""){
			$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
		}else{
			$(this).val(0);
		}
	}
};

const debounce = (fn, delay) => {
  let t = null;
  return function(...args) {
    clearTimeout(t);
    t = setTimeout(() => {
      fn(...args);
    }, delay);
  }
};

let handleClick = (a) => {
	if(a.dataset && a.dataset.ref)
		window.location = a.dataset.ref;
};

let rightClick = (a) => {
	if(a.dataset && a.dataset.ref) {
		a.setAttribute("href", a.dataset.ref);
		removeClick(a);
	}
};

let removeClick = (a) => {
	a.removeAttribute("href");
}

handleClick = debounce(handleClick, 200);
removeClick = debounce(removeClick, 200);

const revents = {
	click: handleClick,
	auxclick: rightClick,
	contextmenu: rightClick
}

//jquery function for key binding
$(document).ready(function() {
	generate_menu();
	
	if (!(/Mobi|Android/i.test(navigator.userAgent)))
	  prevent_multi_click();
	
	initialize_widget();
});

function prevent_multi_click() {
	document.querySelectorAll('a:not( [href^="#"], [href*="javascript"] )').forEach(a => {
		a.dataset.ref = a.getAttribute("href") || '';
	  	a.removeAttribute("href");
	  	
	  	Object.keys(revents).forEach((key) => {
		  a.addEventListener(key, () => revents[key](a));
		});
	});
}

function initialize_widget(){	
	const $ctrl = function(key, callback, args) {
	    $(document).keydown(function(e) {
	        if(!args) args=[]; // IE barks when args is null
	        if(e.keyCode == key.charCodeAt(0) && e.ctrlKey) {
	            callback.apply(this, args);
	            return false;
	        }
	    });
	};

	//bind hotkey on document
	$(document).keydown(function(e){if(e.which==20)warning('CAPSLOCK has been pressed');});

	$ctrl("S",function(){
		if($(".item-button-save").length>0){
			$(".item-button-save").click();
		}
		if($(".item-button-update").length>0){
			$(".item-button-update").click();
		}
	});

	//bind click Top link
	$('#se-top-link').click(function(event) {
		event.preventDefault();
		$('body,html').animate({
			scrollTop : 0
		}, 800);
	}).attr("title","Click for auto-scroll to top");

	//bind top search box
	$('#searchMenu').bind({
		keydown : function() {
			if (this.value == "Menu...") {
				$(this).val("");
				$(this).css("color", "black");
				$(this).css("font-style", "normal");
			}
		},
		blur : function() {
			if (this.value == "") {
				$(this).val("Menu...");
				$(this).css("color", "#cdcdcd");
				$(this).css("font-style", "italic");
			}
		}
	});

	/**bind input-number class
	*190=.
	*188=,
	*/
	$(".input-number").bind(inputFormat);
	$(".input-decimal").bind(inputFormat);
	$(".input-currency").bind(inputFormat);
	
	//initialize tab component
	$("#tabs").tabs();

	//create information dialog
	$dialog = $('<div></div>').dialog({
		autoOpen : false,
		title : 'Information',
		modal : true,
		buttons : {
			Close : function() {
				$(this).dialog('close');
			}
		}
	});

	$(".datepicker").datepicker({
		showOn: "button",
		buttonImage: base_url+"assets/icons/datepicker.gif",
		buttonImageOnly:true,
		buttonText:"Open Datepicker",
		dateFormat: 'dd-mm-yy'
	});

	$.fn.parseDatepicker=function(){
		let datepick=$(this);

		datepick.wrapAll("<div class='datepicker_wrapper'>");
		datepick.datepicker({
			showOn: "button",
			buttonImage: base_url+"assets/icons/datepicker.gif",
			buttonImageOnly:true,
			buttonText:"Open Datepicker",
			dateFormat: 'dd-mm-yy'
		});
	}
	//resize the content body
	resizeContent();
	
	$(".clockpicker").clockpicker({
		placement: 'bottom', // clock popover placement
		align: 'left',       // popover arrow align
		donetext: 'Done',     // done button text
		autoclose: true    // auto close when minute is selected
	});
}
//DUPLICATE METHOD WITH dialog.js
/*function showDialog(html){
	$dialog.empty();
	$dialog.html(html);
	$dialog.dialog('open');
}*/

function generate_menu() {
	if(!$popupid) {
		Object.entries(submenu || {}).forEach(([key, value]) => {
			const menus = value.split('>');
			const root = menus[0].trim().replace(/ /g,"_");
			const length = menus.length-1;

			let Rootli = $('#'+root);
			if(!Rootli.length) {
				Rootli = $('<li id="'+root+'"/>');
				Rootli.append($('<a>'+menus[0]+'</a>'));
				Rootli.append($('<ul class="'+root+' dropdown"/>'));

				$('.menuzord-menu').append(Rootli);
			}
			
			for (let i = 1; i < length; i++) {
				const menu = menus[i].trim().replace(/ /g,"_");
				const parent = menus[i-1].trim().replace(/ /g,"_");

				let Objli = $('<li id="'+menu+'"/>');
				let Obja = $('<a/>');

				Obja.text(menus[i].trim());
				Objli.append(Obja);

				if((i+1) == length) {
					let Objul = $('.'+parent);
					Objul.append(Objli);

					Obja.attr('href',menus[length].trim().substring(1));
					Object.keys(revents).forEach((key) => {
					  Obja[0].addEventListener(key, () => revents[key](Obja[0]));
					});
				} else {
					let Objul = $('.'+menu);
					if(!Objul.length) 
						Objul = $('<ul class="'+menu+' dropdown"/>');

					let ObjliPrev = $('#'+menu);
					if(ObjliPrev.length)
						ObjliPrev.append(Objul);
					else
						Objli.append(Objul);

					let ObjulParent = $('.'+parent);
					if(!ObjliPrev.length)
						ObjulParent.append(Objli);
				}
			}
		});
		
		//fix menu
		$("#menuzord").menuzord({
			align: "right"
		});
		
		bind_form();
	}
}

//bind session form
function bind_form() {
	const $form = document.querySelectorAll('form[class="session"]');
	const last = $form.length -1;
	
	if(last > -1) {
		let $in_c = $('<input/>').attr("type", "hidden");
		$in_c.attr("name", $form[last].getAttribute('data'));
		$in_c.attr("value", $form[last].getAttribute('value'));
	
		$in_c.appendTo($form[last]);
	}
}

function beforeSend($dialog, $text){
	$dialog.empty();
	$dialog.html($text);
	$dialog.dialog('open');
	
	$('.item-button-save').hide();
}

function afterFail($dialog, $text){
	$dialog.empty();
	$dialog.html($text);
	
	$('.item-button-save').show();
}

function warning(text){
	if($("#notif-message").length>0)
		setTimeout(function(){$("#notif-message").fadeOut('slow');$("#notif-message").remove();},3000);
	$warn=$("<div id='notif-message' class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info'>&nbsp;</span></div>");
	$warn.append(text);
	$("body").append($warn);
	$warn.click(function(){$warn.remove();});
	setTimeout(function(){$("#notif-message").fadeOut('slow');$warn.remove();},3000);
}

function alert(text){
	if($dialog!=null)
		$dialog.empty();
	else{
		$dialog = $('<div></div>').dialog({
			autoOpen : false,
			title : 'Information',
			modal : true,
			buttons : {
				Close : function() {
					$(this).dialog('close');
				}
			}
		});
	}
	$dialog.html(text);
	$dialog.dialog('open');
}

//show dialog function and go to url after
function deleteDialog(url,message){
	if(message==null)
		message="Do you really want to delete the data ?";
	$confirmDialog = $("<div><span>"+message+"</span></div>").dialog({
		title : 'Information',
		modal : true,
		buttons : {
			"Delete" :{
				text:"Delete",
				id:"btn-dialog-delete",
				click:function() {
					$(this).dialog("close");
					window.location = url;
				}
			},
			"Cancel" :{
				text:"Cancel",
				id:"btn-dialog-cancel",
				click:function() {
					$(this).dialog("close");
				}
			}
		}
	});
	$confirmDialog.dialog("open");
}
//
function confirmDialog(message,yesCallback,noCallback) {
	if (message == null || message == "")
		message = "Are you sure?";
	$confirmDialog = $("<div><span>" + message + "</span></div>").dialog({
		autoOpen : true,
		title : 'Confirmation',
		modal : true,
		buttons : {
			"Yes" : {
				text : "Yes",
				id : "btn-dialog-delete",
				click : function() {
					if(yesCallback!=null)
						yesCallback();
					$(this).dialog("close");
				}
			},
			"No" : {
				text : "No",
				id : "btn-dialog-cancel",
				click : function() {
					if(noCallback!=null)
						noCallback();
					$(this).dialog("close");
				}
			}
		}
	});
}

//set value to title
function emptyValue(elem) {
	elem.value = "";
	elem.onblur = function() {
		if (elem.value == "")
			elem.value = elem.getAttribute("title");
	}
}

//resize the content body
function resizeContent() {
	var subtractHeight = 180;
	var size = $(window).height() - subtractHeight;
	var height = $('#se-contents').css('height');
	var width = $('#se-contents').css('width');
	if($('#se-contents').length>0)
	{
		height = height.substring(0, height.length - 2);
		width = width.substring(0, width.length - 2);

		// Check if the predefined height less than window size
		if (height < size) {
			$('#se-contents').css('min-height', ($(window).height() - subtractHeight) + 'px');
			$('#se-contents').css('height', '100%');
			var height = $('#se-contents').css('height');
			height = height.substring(0, height.length - 2);
			$('.main-box').css('min-height', (height - 50) + 'px');
			$('.main-box').css('height', '100%');
		}

		// uncomment this line if the resolution doesn't set maximum and comment the
		// 3 line bellow
		// $('#se-top').css('width', (parseInt(width)+62)+'px');
		$('#se-top-container').css('width', '100%');
		$('#se-containers').css('width', '100%');
		$('#footer').css('width', '100%');
	}
}

function remove() {
	window.location = document.getElementById("deletedId").value;
}

function deleted() {
	window.location = document.getElementById("deletedId").value;
}

function hideseek(id) {
	let $passwd = $('#'+id);
	if($passwd.hasClass('masking'))
	{
		$passwd.removeClass('masking');
		$('#'+id+'img').attr('class', 'item-hide-password');
	}
	else
	{
		$passwd.attr('class', 'masking inputbox-medium');
		$('#'+id+'img').attr('class', 'item-show-password');
	}
}

function submit(id, url) {
	$('#'+id).attr('action', url)[0].submit();
}

/**
 * Membuat URL lengkap dari base URL dan objek parameter.
 * @param {string} baseUrl - URL dasar tanpa parameter query.
 * @param {Object} params - Objek yang berisi pasangan kunci-nilai untuk parameter query.
 * @returns {string} URL lengkap dengan parameter query.
 *
 * @example
 * // Contoh penggunaan:
 * const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
 * const params = {
 *     target: 'customer',
 *     organization: '5',
 *     fromRoleType: 4,
 *     toRoleType: 5,
 *     relationshipType: 3,
 *     base: false
 * };
 * const fullUrl = buildUrl(baseUrl, params);
 */
function buildUrl(baseUrl, params) {
	const queryString = Object.keys(params)
		.map(key => key + '=' + params[key])
		.join('&');
	return `${baseUrl}?${queryString}`;
}