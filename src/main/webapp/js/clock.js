var clockID = 0;
var h = 0;
var m = 0;
var s = 0;
var dd=0;
var ff=0;
var yy=0;
var Clock=new Object();
Clock.update=function () {
	if (clockID) {
		clearTimeout(clockID);
		clockID = 0;
	}
	h = parseInt(h);
	m = parseInt(m);
	s = parseInt(s);
	if (s > 59) {
		s = 0;
		m++;
	}
	if(m>59){
		m=0;
		h++;
	}
	if(h>23){
		h=0;
	}
	if (h < 10)
		h = "0" + h;
	if (m < 10)
		m = "0" + m;
	if (s < 10)
		s = "0" + s;
	$("#theClock").html(h + ":" + m + ":" + s);
	s++;
	clockID = setTimeout("Clock.update()", 1000);
}

Clock.start=function() {
	clockID = setTimeout("Clock.update()", 500);
}

Clock.kill=function() {
	if (clockID) {
		clearTimeout(clockID);
		clockID = 0;
	}
}
Clock.init=function(time){
	var _dd=time.substring(0,2);
	var _ff=time.substring(3,5);
	var _yy=time.substring(6,10)
	var _hh=time.substring(11,13);
	var _mm=time.substring(14,16);
	var _ss=time.substring(17,19);
	dd=_dd;ff=_ff;yy=_yy;h=_hh;m=_mm;s=_ss;this.start();
}