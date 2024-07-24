/***********************************************
 *
 *
 * siriusERP - List Form Dynamic Table
 *             (c) 2007 Konsep.NET
 *
 * File Name      : listform.css
 * Depedency      : main.css
 *
 * Media          : Table List
 *
 *
 **(coded by amarullz@yahoo.com)**/
 
var cacheListFormRows=new Array();
function ListForm_Rebuild(prefix, focused){
	// Get Variables with prefix	
	eval("var vStyles  ="+prefix+"_CssInput;");
	eval("var vNaming ="+prefix+"_Naming;");
	eval("var vTitle  ="+prefix+"_Title;");
	eval("var vType   ="+prefix+"_Type;");
	eval("var field   ="+prefix+"_Field;");
	
	var rows=cacheListFormRows[prefix];
	if (!cacheListFormRows[prefix])
		eval("rows    ="+prefix+"_Rows;");
	cacheListFormRows[prefix]=rows;
	
	// Table List
	buf ='<table class="table-list" cellspacing="0" cellpadding="0">';

	// Buat Header
	buf+='<tr>';
	buf+='<th>&nbsp;</th>';
	for (i=0;i<vTitle.length;i++)
		buf+='<th>'+(vTitle[i])+'</th>';
	buf+='</tr>';
	
	// Buat Row List
	for (i=0;i<rows.length;i++){
		buf+='<tr>';
		buf+='<td><input type="checkbox" id="ListForm_ID_'+prefix+'_'+i+'" value="1" /></td>';
		
		// Buat Form
		for (j=0;j<vType.length;j++){
			var idname="ListForm_FIELDS_"+prefix+"_"+i+"_"+j;
			
			if ((vType[j][0]!=null)&&(vType[j][0].length==2)){
				buf+='<td><select id="'+idname+'" name="'+(vNaming[j])+'['+i+']" class="selectbox" style="'+vStyles[j]+'">';
				for (k=0;k<vType[j].length;k++){
					buf+='<option '+((rows[i][j]==vType[j][k][0])?'selected="selected"':'')+
					     ' value="'+(vType[j][k][0])+'">'+
					     (vType[j][k][1])+'</option>';
				}
				buf+='</select></td>';
			}
			else if (vType[j]=='password'||vType[j]=='text'){
				buf+='<td><input id="'+idname+'" type="'+((vType[j]=='password')?'password':'text')+
				     '" value="'+rows[i][j]+'" name="'+(vNaming[j])+'['+i+']" class="inputbox" style="'+vStyles[j]+'"/></td>';
			}
		}
			
		buf+='</tr>';
	}
	
	// End of buffers
	buf+='<tr class="end-table"><td colspan="'+(vNaming.length+1)+'">&nbsp;</td></tr></table>';

	// Flush the buffers
	field.innerHTML=buf;
	
	if (focused>0)
		getID('ListForm_ID_'+prefix+'_'+(focused-1)).focus();
}
// Update Caching
function ListForm_UpdateCache(prefix){
	var rows=cacheListFormRows[prefix];
	if (!cacheListFormRows[prefix])
		eval("rows    ="+prefix+"_Rows;");
	eval("var vType   ="+prefix+"_Type;");
	
	for (i=0;i<rows.length;i++){
		for (j=0;j<vType.length;j++){
			var idname="ListForm_FIELDS_"+prefix+"_"+i+"_"+j;
			cacheListFormRows[prefix][i][j]=getID(idname).value;
		}
	}
}
// Add New Row...
function ListForm_AddNew(prefix){
	ListForm_UpdateCache(prefix);
	cacheListFormRows[prefix][cacheListFormRows[prefix].length]=new Array('','','','');
	ListForm_Rebuild(prefix,cacheListFormRows[prefix].length);
}

function ListForm_DeleteRows(prefix){
	ListForm_UpdateCache(prefix);
	var rows=cacheListFormRows[prefix];
	if (!cacheListFormRows[prefix])
		eval("rows ="+prefix+"_Rows;");
	
	var selCnt=0;
	cacheListFormRows[prefix]=new Array();
	for (i=0;i<rows.length;i++){
		if (!getID('ListForm_ID_'+prefix+'_'+i).checked){
			cacheListFormRows[prefix][selCnt++]=rows[i];
		}
	}
	ListForm_Rebuild(prefix,false);
}
function ListForm_ResetList(prefix){
	var rows=null;
	eval("rows ="+prefix+"_Rows;");
	cacheListFormRows[prefix]=new Array();
	var selCnt=0;
	for (i=0;i<rows.length;i++){
		if (rows[i]!=null)
			cacheListFormRows[prefix][selCnt++]=rows[i];
	}
	ListForm_Rebuild(prefix,false);
}