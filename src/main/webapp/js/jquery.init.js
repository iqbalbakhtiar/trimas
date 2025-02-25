/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

//create some variables
var $confirmDialog, $dialog;

//import widget dojo
dojo.require("dojo.widget.*");

var ctrlDown = false;
var format = {decimal : "#,##0.00000", number : "#,##0.00"};
var keydownEvent = {negative : function(event) {
         if (((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) &&
        	!(event.keyCode == 46 || event.keyCode == 110 || event.keyCode == 13 || event.keyCode == 8 
        		|| event.keyCode == 9 || event.keyCode == 173 || event.keyCode == 109 || (event.keyCode > 187 && event.keyCode < 191))){
            event.preventDefault();
        }
	}, whole : function(event) {
		
		if (event.keyCode == 17 || event.keyCode == 91) ctrlDown = true;
		
        if (((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) &&
        	!(event.keyCode == 46 || event.keyCode == 110 || event.keyCode == 13 || event.keyCode == 8 
        		|| event.keyCode == 9 || event.keyCode == 188 || event.keyCode == 190 || (ctrlDown && (event.keyCode == 67)) || (ctrlDown && (event.keyCode == 86)))){
            event.preventDefault();
        }
	}};

function getEvent(obj) {
	if($(obj).hasClass('negative'))
		return keydownEvent['negative'];
	
	return keydownEvent['whole'];
}

function getFormat(obj) {
	if($(obj).hasClass('input-currency'))
		return format['currency'];

	if($(obj).hasClass('input-decimal'))
		return format['decimal'];

	return format['number'];
}

var inputNumber = {
	keydown : function(event){
		getEvent(this)(event);
	},
	keyup : function(event) {
		if(event.keyCode==188){
			if(!$(this).val().indexOf(".")===1)
				$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
		}else if(event.keyCode==13){
			$(this).val(Number.parse($(this).val()).numberFormat(getFormat(this)));
			$(this).parents("tr").next().find($('[name='+$(this).attr("name")+']')).focus();
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

	//bind session form
	const $form = $('.session');

	let $in_c = $('<input/>').attr("type", "hidden");
	$in_c.attr("name", $form.attr('data'));
	$in_c.attr("value", $form.attr('value'));

	$in_c.appendTo($form);
	
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
	$(".input-number").bind(inputNumber);
	$(".input-decimal").bind(inputNumber);
	
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
		var id=this.selector;
		id=id.replace("[","\\[");
		id=id.replace("]","\\]");
		$("#"+id).wrapAll("<div class='datepicker_wrapper'>");
		$("#"+id).datepicker({
			showOn: "button",
			buttonImage: base_url+"assets/icons/datepicker.gif",
			buttonImageOnly:true,
			buttonText:"Open Datepicker",
			dateFormat: 'dd-mm-yy'
		});
	}
	
	//resize the content body
	resizeContent();
}

function generate_menu() {
	Object.entries(submenu).forEach(([key, value]) => {
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

			let Objli = $('<li id="'+menu+'"></li>');
			let Obja = $('<a></a>');

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
					Objul = $('<ul class="'+menu+' dropdown"></ul>');

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

function cancelDialog(url,message){
	if(message==null)
		message="Do you really want to Cancel this data ?";
	$confirmDialog = $("<div><span>"+message+"</span></div>").dialog({
		title : 'Information',
		modal : true,
		buttons : {
			"Yes" :{
				text:"Yes",
				id:"btn-dialog-delete",
				click:function() {
					$(this).dialog("close");
					window.location = url;
				}
			},
			"No" :{
				text:"No",
				id:"btn-dialog-cancel",
				click:function() {
					$(this).dialog("close");
				}
			}
		}
	});
	$confirmDialog.dialog("open");
}

//set value to title
function emptyValue(elem) {
	elem.value = "";
	elem.onblur = function() {
		if (elem.value == "")
			elem.value = elem.getAttribute("title");
	}
}

//go to with location
function go_to(url) {
	window.location = url;
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

/**
 * Objek helper untuk manipulasi tanggal dengan format dd-MM-yyyy.
 */
const DateHelper = {
	/**
	 * Mengubah string tanggal dengan format dd-MM-yyyy menjadi objek Date.
	 * @param {string} dateStr - Tanggal dalam format dd-MM-yyyy.
	 * @returns {Date} Objek Date yang sesuai.
	 *
	 * @example
	 * const dateObj = DateHelper.parseDate("10-02-2025");
	 */
	parseDate: function(dateStr) {
		let parts = dateStr.split('-');
		let day = parseInt(parts[0], 10);
		let month = parseInt(parts[1], 10) - 1; // Bulan 0-indexed
		let year = parseInt(parts[2], 10);
		return new Date(year, month, day);
	},

	/**
	 * Memformat objek Date menjadi string dengan format dd-MM-yyyy.
	 * @param {Date} date - Objek Date yang akan diformat.
	 * @returns {string} Tanggal dalam format dd-MM-yyyy.
	 *
	 * @example
	 * const formatted = DateHelper.formatDate(new Date(2025, 1, 10));
	 * console.log(formatted); // Output: "10-02-2025"
	 */
	formatDate: function(date) {
		let day = date.getDate();
		let month = date.getMonth() + 1;
		let year = date.getFullYear();
		day = (day < 10 ? '0' : '') + day;
		month = (month < 10 ? '0' : '') + month;
		return day + '-' + month + '-' + year;
	},

	/**
	 * Menambahkan sejumlah hari ke tanggal yang diberikan.
	 * @param {string} dateStr - Tanggal awal dalam format dd-MM-yyyy.
	 * @param {number} days - Jumlah hari yang akan ditambahkan.
	 * @returns {string} Tanggal baru dalam format dd-MM-yyyy setelah penambahan hari.
	 *
	 * @example
	 * const newDate = DateHelper.plusDays("10-02-2025", 60);
	 * console.log(newDate); // Output: tanggal 60 hari setelah 10-02-2025
	 */
	plusDays: function(dateStr, days) {
		let date = this.parseDate(dateStr);
		date.setDate(date.getDate() + days);
		return this.formatDate(date);
	},

	/**
	 * Mengurangkan sejumlah hari dari tanggal yang diberikan.
	 * @param {string} dateStr - Tanggal awal dalam format dd-MM-yyyy.
	 * @param {number} days - Jumlah hari yang akan dikurangkan.
	 * @returns {string} Tanggal baru dalam format dd-MM-yyyy setelah pengurangan hari.
	 *
	 * @example
	 * const newDate = DateHelper.minusDays("10-02-2025", 15);
	 * console.log(newDate); // Output: tanggal 15 hari sebelum 10-02-2025
	 */
	minusDays: function(dateStr, days) {
		return this.plusDays(dateStr, -days);
	},

	/**
	 * Menambahkan sejumlah bulan ke tanggal yang diberikan.
	 * @param {string} dateStr - Tanggal awal dalam format dd-MM-yyyy.
	 * @param {number} months - Jumlah bulan yang akan ditambahkan.
	 * @returns {string} Tanggal baru dalam format dd-MM-yyyy setelah penambahan bulan.
	 *
	 * @example
	 * const newDate = DateHelper.plusMonths("10-02-2025", 2);
	 * console.log(newDate); // Output: tanggal 2 bulan setelah 10-02-2025
	 */
	plusMonths: function(dateStr, months) {
		let date = this.parseDate(dateStr);
		date.setMonth(date.getMonth() + months);
		return this.formatDate(date);
	},

	/**
	 * Mengurangkan sejumlah bulan dari tanggal yang diberikan.
	 * @param {string} dateStr - Tanggal awal dalam format dd-MM-yyyy.
	 * @param {number} months - Jumlah bulan yang akan dikurangkan.
	 * @returns {string} Tanggal baru dalam format dd-MM-yyyy setelah pengurangan bulan.
	 *
	 * @example
	 * const newDate = DateHelper.minusMonths("10-02-2025", 1);
	 * console.log(newDate); // Output: tanggal 1 bulan sebelum 10-02-2025
	 */
	minusMonths: function(dateStr, months) {
		return this.plusMonths(dateStr, -months);
	}
};
