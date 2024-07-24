//<![CDATA[
/***********************************************
 *
 *
 * POPUP - CALENDER INPUT
 *
 * File Name           : calendar.css
 * Depedency           : none
 * Function depedency  : getID
 * Media               : FORM
 * 
 *
 * HOW TO USE:
 *   1. Create <div id="calender_place"></div>
 *   2. Add Script after the div like this:
        |
				| <script type="text/javascript">
				|	  //<![CDATA[
				|  		calender_write('calender_place','form_elemen_name',4,5,2007,'class_name','width:80px;white-spacing:nowrap');
				|		//]]>
				|	</script>
				|
 *
 * DOCUMENTATION PUBLIC FUNCTION:
 *
 * [return none] calender_write (
 *       element_id,
 *       form_input_name,
 *       day, 
 *       month,
 *       year,
 *       css_classes,
 *       css_custom_css
 * );
 *
 * element_id      = ID for calendar input place (ex: <div id="calendar"></div> so this value should "calendar")
 * form_input_name = Form input name which will send via form submit
 * year,month,day  = First value
 * css_classes     = CSS clases for input calendar if any, (set '' if not set)
 * css_custom_css  = CSS style for input calendar if any, (set '' if not set)
 *
 **(coded by amarullz@yahoo.com)**/
function getID(field){
	return document.getElementById ? document.getElementById(field) : document.all(field);
}
var calender_hidden_timer=new Array();
var calender_current_date=new Array();
function calender_visible(fieldname){
    clearTimeout(calender_hidden_timer[fieldname]);
    getID('cldr-'+fieldname+'-div').style.visibility='';
    getID('cldr-'+fieldname+'-btn').style.backgroundColor='#aaa';
}
function calender_hidden (fieldname){
    calender_hidden_timer[fieldname]=setTimeout("getID('cldr-"+fieldname+"-div').style.visibility='hidden';"+
        "getID('cldr-"+fieldname+"-btn').style.backgroundColor='#ccc';",200);
}
function calender_nBulan(t){
	/* Fix Kabisat error Y2K */
  var Kabisat  =(((2004-t+4)%4)==0)?29:28;
	return new Array(31,Kabisat,31,30,31,30,31,31,30,31,30,31);;
}
function calender_interval(b,t){
    var q=calender_nBulan(t);
    var r=0;
    if (2004<=t){
        for (var i=0;i<(b-1);i++)
            r+=q[i];
        for (var i=2004;i<t;i++)
            r+=(((2004-i)%4)==0)?366:365;
        r+=4;
        r=r%7;
    }
    else{
        for (var i=2003;i>=t;i--)
            r+=(((2004+2004-i)%4)==0)?366:365;
        var tt=0;
        for (var i=0;i<(b-1);i++)
            r-=q[i];
        r=6-((r+2)%7);
    }
    return r;
}
function calender_show(fieldname,currentDate){
    /******** aCalender Style Sheet ********/
    calender_current_date[fieldname]=currentDate;
    var sT='color:#000;background:#bbb';
    var sD='color:#000;background:#fff';
    var styleDayT=new Array('color:#fff;background:#900',sT,sT,sT,sT,sT,sT);
    var styleDays=new Array('color:#fff;background:#900',sD,sD,sD,sD,sD,sD);
    var styleDayNow='color:#004;background:#cdf';
    var styleBln ='background:#444';
    var styleList='font-family:arial,tahoma,verdana,sans-serif;font-size:11px';
    var styleCldr='background:#ddd;border:1px solid #444;padding:1px';
    var styleDay ='font-family:arial,tahoma,verdana,sans-serif;font-size:11px;padding:3px';
    var colorDayBorder          ='#ddd';
    var colorDayBorder_select   ='#009';
    var colorDayBorder_hover    ='#090';
    /***** end of aCalender Style Sheet *****/
    
    
    field=getID(fieldname);dfield=getID('cldr-'+fieldname+'-div');var m='';
    var t_s=currentDate.substring(6,10);
    var b_s=currentDate.substring(3,5);
    var h_s=currentDate.substring(0,2);
    
    var t=Number(t_s);
    var b=Number(b_s);
    var h=Number(h_s);
    var curval=field.value;
    var cv_t_s=curval.substring(6,10);
    var cv_b_s=curval.substring(3,5);
    var cv_h_s=curval.substring(0,2);
    
    var cv_t=Number(cv_t_s);var cv_b=Number(cv_b_s);var cv_h=Number(cv_h_s);
    
    var namaHari =new Array('Sun','Mon','Tue','Wed','Thru','Fri','Sat');
    var namaBulan=new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
    
    m+='<div style="'+styleCldr+'">';m+='<table cellspacing="0" cellpadding="2">';m+='<tr><td colspan="7" style="text-align:center;'+styleBln+'">';
    var blur_focus='onfocus="calender_visible(\''+fieldname+'\')" '+'onblur="calender_hidden(\''+fieldname+'\')"';
    
    // Tulis Bulan
    m+='<select id="cldr-'+fieldname+'-bulan" '+blur_focus+' onchange="calender_show(\''+fieldname+'\',\''+h_s+'-\'+this.value+\'-'+t_s+'\')" style="margin-right:3px;'+styleList+'">';
		for (var i=1;i<=12;i++){
		  var vb='0'+i; vb=vb.substring(vb.length-2,vb.length);
			m+='<option '+((i==b)?'selected="selected"':'')+' value="'+vb+'">'+namaBulan[i-1]+'</option>';
		}
		m+='</select>';
		
		// Tulis Tahun
		m+='<select '+blur_focus+' onchange="calender_show(\''+fieldname+'\',\''+h_s+'-'+b_s+'-\'+this.value)" style="'+styleList+'">';
		for (var i=1900;i<=2099;i++)
			m+='<option '+((i==t)?'selected="selected"':'')+' value="'+i+'">'+i+'</option>';
		m+="</select>";
		
    // Tulis Title Hari
    m+='</tr></tr><tr>';
    for (var i=0;i<7;i++){
        m+='<th style="text-align:center;cursor:help;font-weight:bold;border:1px solid '+colorDayBorder+';'+styleDay+';'+styleDayT[i]+'" title="'+namaHari[i]+'">'+namaHari[i].substring(0,1)+'</th>';
    }
    m+='</tr><tr>';
    var startDay=calender_interval(b,t);
    var jBulan  =calender_nBulan(t);
    // Tulis Tanggal
    if (startDay){
        m+='<td colspan="'+(startDay)+'"></td>';
    }
    var dtNew=new Date();
    var n_t=dtNew.getFullYear()+'-'+(dtNew.getMonth()+1)+'-'+dtNew.getDate();
    
    for (var i=1;i<=jBulan[b-1];i++){
        var c_t=t_s+'-'+b+'-'+i;
        var vb='0'+i; vb=vb.substring(vb.length-2,vb.length);
        var n=(i-1+startDay)%7;
        var bord=(c_t==cv_t_s+'-'+cv_b+'-'+cv_h)?colorDayBorder_select:colorDayBorder;
        if (n==0)
            m+='</tr><tr>';
        m+='<td '+
            'onmouseover="this.style.borderColor=\''+colorDayBorder_hover+'\';" '+
            'onmouseout="this.style.borderColor=\''+bord+'\';" '+
            'onmousedown="getID(\''+fieldname+'\').value=\''+vb+'-'+b_s+'-'+t_s+'\';" '+
            'style="text-align:right;cursor:pointer;border:1px solid '+
            bord+';'+styleDay+';'+((n_t==c_t)?styleDayNow:styleDays[n])+'">'+i+'</td>';
    }
    m+='</tr>';
    m+='</table>';        
    m+='</div>';
    dfield.innerHTML=m;
    calender_visible(fieldname);
    getID('cldr-'+fieldname+'-bulan').focus();
}
function calender_write(field,fieldname,h,b,t,nameclass,inputstyle){
    field=getID(field);
    var appd='';
    var m='';
    d=new Date();
    t=(t?t:d.getFullYear());
    b=(b?b:d.getMonth()+1);
    h=(h?h:d.getDate());
    if (nameclass)
        appd+='class="'+nameclass+'" ';
    if (inputstyle)
        appd+='style="float:left;'+inputstyle+'" ';
    t=(t>75)?('19'+t):('20'+t);
    t=t.substring(t.length-4,t.length);
    b='0'+b; b=b.substring(b.length-2,b.length);
    h='0'+h; h=h.substring(h.length-2,h.length);
    m   +='<div id="cldr-'+fieldname+'-div" style="position:absolute;visibility:hidden">&nbsp;</div>';
    m   +='<div><input onfocus="getID(\'cldr-'+fieldname+'-btn\').focus();" readonly="readonly" type="text" name="'+fieldname+'" id="'+fieldname+'" value="'+h+'-'+b+'-'+t+'" '+appd+'/>';
    m   +='<input type="button" '+
            'onmouseover="if (this.style.backgroundColor!=\'#000\') this.style.color=\'#f00\';" onmouseout="this.style.color=\'#000\';" '+
            'onfocus="this.style.color=\'#000\';calender_show(\''+fieldname+'\',calender_current_date[\''+fieldname+'\'])" '+
            'value="&equiv;" id="cldr-'+fieldname+'-btn" title="Calender" style="float:left;cursor:pointer;padding:0px;margin:0px;border:none;background-color:#e2e2e2;width:22px;margin-left:1px;font-weight:bold;font-size:14px;" /></div>';
    field.innerHTML=m;
    ifield=getID(fieldname);
    calender_current_date[fieldname]=ifield.value;
    bfield=getID('cldr-'+fieldname+'-btn');
    dfield=getID('cldr-'+fieldname+'-div');
    bfield.style.color='#000';
    dfield.style.marginTop=field.style.height=bfield.style.height=ifield.offsetHeight+'px';
    field.style.width=(bfield.offsetWidth+ifield.offsetWidth+1)+'px';
}
//]]>