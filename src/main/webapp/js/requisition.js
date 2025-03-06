var RequisitionItem = new Object();
RequisitionItem.data = null;

RequisitionItem.load = async function($id)
{
	const result = await fetch($('base').attr('href') + '/page/popuppurchaserequisitionitemjson.htm?' + new URLSearchParams(
	{
		id : $id
	}), 
	{
        method: 'GET',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        }
    });
	
	const json = await result.json();
	
	if(json.status == "OK")
	{	
		if(!$.isEmptyObject(json.requisitionItem))
			return json.requisitionItem;
	}
	else
		alert(json.message);
};