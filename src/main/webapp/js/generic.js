/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
let Generic = new Object();

Generic.load = async function($pid, $id)
{
    const result = await fetch(base_url + '/page/genericremoteload.json?' + new URLSearchParams(
        {
            name : $pid,
            value : $id
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
        if(!$.isEmptyObject(json.generic))
            return json.generic;
    }
    else
        alert(json.message);
};

Generic.uniqe = async function($pid, $param, $id)
{
    const params = new URLSearchParams(
        {
            name : $pid,
            value : $id
        });

    if($param)
        params.set("param", $param);

    const result = await fetch(base_url + '/page/genericremoteload.json?' + params,
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
        if(!$.isEmptyObject(json.generic))
            return json.generic;
    }
    else
        alert(json.message);
};

/**
 * Generic.list, untuk mendapatkan list dari object
 * Kirim POST ke /page/genericremotelist.json
 * (operator selalu “=”, tipe: long | boolean | string)
 *
 * @param {string}   className  FQN entity, mis. "com.siriuserp.sdk.dm.Grid"
 * @param {string[]} keys       array field
 * @param {string[]} types      array tipe ("long" | "boolean" | "string")
 * @param {string[]} values     array nilai String
 * @returns {Promise<Array>}    hasil list
 */
Generic.list = async function (className, keys = [], types = [], values = []) {
    const body = new URLSearchParams();
    body.append('name', className);
    keys.forEach((k, i) => {
        body.append('key',   k);
        body.append('type',  types[i]);
        body.append('value', values[i]);
    });

    const resp = await fetch(base_url + '/page/genericremotelist.json', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: body.toString()
    });

    if (!resp.ok) throw new Error(`${resp.status} ${resp.statusText}`);
    const json = await resp.json();
    if (json.status !== 'OK') throw new Error(json.message);
    return json.list;          // array hasil DAO
};