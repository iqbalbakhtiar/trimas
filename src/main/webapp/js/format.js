var Format = new Object();

Format.format = function Comma(SS) 
{
    var T = "";
    var S = String(SS);
    var L = S.length - 1;
    var C;
    var j;
    
    for (j = 0; j <= L; j++) 
    {
        T += C = S.charAt(j);
        
        if (j < L && (L - j) % 3 == 0 && C != "-") 
        {
            T += ",";
        }
    }
    
    return T;
}

Format.toFloat = function(str)
{
	var x = String(str).split(/,|\./);
	
	if(x.length == 1)
		return parseFloat(x);
	
	var x2 = x[x.length-1];
	var x3 = x.join('').replace(new RegExp(x2+'$'),'.'+x2);
	
	return parseFloat(x3);
	//  x2 is for clarity, could be omitted:
	//=>x.join('').replace(new RegExp(x[x.length-1]+'$'),'.'+x[x.length-1])
} 

//Compose template string
String.prototype.compose = (function (){
	var re = /\{{(.+?)\}}/g;
	return function (o){
		return this.replace(re, function (_, k){
			return typeof o[k] != 'undefined' ? o[k] : '';
		});
	}
}());