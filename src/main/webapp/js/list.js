/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

var List = new Object();

List.get = function(gen, id, value)
{
    $obj = $(gen);
    $obj.attr('id', id);

    var start = id.indexOf('[');
    var idex = id.substring(start+1, id.length-1);
    var pram = id.substring(0, start);

    if(start > 0) {
        if(!$obj.attr('disabled'))
            $obj.attr('name', 'items['+idex+'].'+pram);
        
        $obj.attr('index', idex);
        $obj.attr('next', pram);
    }

    if(value)
        $obj.attr('value', value);

    return $obj;
}

List.img = function(title,index,event)
{
    $a = $('<a/>');
    $a.attr('class',"item-popup");
    $a.attr('title',title);
    $a.attr('index',index);
    $a.attr('onclick',event);

    return $a;
}

List.del = function(title)
{
    $a = $('<a/>');
    $a.attr('style',"cursor:pointer;");
    $a.attr('class','item-button-delete');
    $a.attr('title',title);
    $a.click(function(){
        $(this).parent().parent().remove();
    });

    return $a;
}

List.col = function(obj, css, style)
{
    $td = $('<td nowrap="nowrap"/>');
    
    if(css)
        $td.attr('class', css)
    
    if(style)
        $td.attr('style', style)

    $.each(obj, function(index, value) {
        $td.append(value);
    });

    return $td;
}