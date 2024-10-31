/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

var List = new Object();

List.get = function(gen, id, value)
{
    const dom = $(gen);
    dom.attr('id', id);

    const start = id.indexOf('[');
    const index = id.substring(start+1, id.length-1);
    const parameter = id.substring(0, start);

    if(start > 0) {
        if(!dom.attr('disabled'))
        dom.attr('name', 'items['+index+'].'+parameter);
        
        dom.attr('index', index);
        dom.attr('next', parameter);
    }

    if(value)
        dom.attr('value', value);

    return dom;
}

List.img = function(title,index,event,css)
{
    $a = $('<a/>');
    $a.attr('title',title);
    $a.attr('index',index);
    $a.attr('data-index',index);
    $a.attr('onclick',event);
    
    if(css)
        $a.attr('class',css);
    else
        $a.attr('class',"item-popup");

    return $a
}

List.del = function(title)
{
    const del = $('<a/>');
    del.attr('class', 'item-button-delete');
    del.attr('title', title);
    del.click(function(){
        $(this).parent().parent().remove();
    });

    return del;
}

List.col = function(...objs)
{
    return List.colCSS('', objs);
}

List.colCSS = function(css, ...objs)
{
    const td = $('<td nowrap="nowrap"/>');
    
    if(css)
        td.attr('class', css)

    $.each(objs, function(index, obj) {
        td.append(obj);
    });

    return td;
}

List.addLine = function(id, ...cols) 
{
    const table = $('#' + id);
    const tr = $('<tr/>');

    $.each(cols, function(index, col) {
        tr.append(col);
    });

    table.append(tr);
}

List.addRow = function(td)
{
	let tr = document.createElement('tr');

	new Array(td).fill(0).forEach(function(){
		tr.appendChild(document.createElement('td'));	
	});

	return tr;
}