/***********************************************
 *
 *
 * siriusERP - Main Template Javascript
 *             (c) 2007 Konsep.NET
 *
 * File Name      : main.js
 * Depedency      : styles.css
 *
 * Media          : Screen - Events
 *
 *
 **(coded by amarullz@yahoo.com)**/

function getID(field){
	return document.getElementById ? document.getElementById(field) : document.all(field);
}
function PopupOver(LinkSelf, fieldID){
	getID(fieldID).onmouseout=LinkSelf.onmouseout;
	getID(fieldID).onmouseover=LinkSelf.onmouseover;
	getID(fieldID).style.display='block';
}
function PopupOut(fieldID){
	getID(fieldID).style.display='none';
}

/*
 *
 *  Tab Pages!
 *
 *
 */
var sirius_tabpages=new Array();
function ShowTabPage(field,indextab,idpage){
	tmppage=sirius_tabpages[indextab][0];
	tmptitle=sirius_tabpages[indextab][1];
	
	sirius_tabpages[indextab][0]=idpage;
	sirius_tabpages[indextab][1]=field.id;
	
	field.className='tab-title-active';
	getID(tmppage).className='tab-pages-hidden';
	getID(tmppage).style.display='none';
	getID(tmptitle).className='tab-title';
	getID(idpage).className='tab-pages';
	getID(idpage).style.display='';
}