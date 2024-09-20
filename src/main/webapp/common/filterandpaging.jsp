<script type="text/javascript">
	$("form").submit(function (e){
		e.preventDefault();
	})

	$("button").bind("click", function(event) {
		event.preventDefault();
	});

	var self = new Object();
	self.opener = window;
	window.close = function() {
		closepopup("${param.popupid}");
	};

	function getPopUrl(page)
	{
		let url = '${url}';
		if(page)
			url += "&page="+page;

		let [path, params] = url.split("?");
		const urls = new URLSearchParams(params);

		const defs = {}
		for(const key of urls.keys()) {
			defs[key] = urls.getAll(key);
		}

		let submits = Object.fromEntries(new URLSearchParams($('#filterPopup').serialize()));

		let result = Object.keys(defs).filter(key => !Object.keys(submits).includes(key)).reduce((obj, key) => {
		    obj[key] = defs[key];
		    return obj;
		}, {});

		return base_url + path + '?' + new URLSearchParams(result).toString();
	}
	
	function searchPopup() {
		$.ajax({
			url : getPopUrl(),
			data : $('#filterPopup').serialize(),
			type : 'POST',
			cache : false,
			success : function(resp) {
				$popup = $("#${param.popupid}");
				if($popup)
					$popup.html(resp)
			}
		});
	}

	function reloadPopup() {
		$popup = $("#${param.popupid}");
		if($popup)
			$popup.load("<c:url value='${url}'/>");
	}

	function step(action, url) {
		var page = "1";

		if (action == "next")
			page = "${filterCriteria.nextPage}";
		else if (action == "prev")
			page = "${filterCriteria.prevPage}";
		else if (action == "last")
			page = "${filterCriteria.totalPage}";

		$.ajax({
			url : getPopUrl(page),
			data : $('#filterPopup').serialize(),
			type : 'POST',
			cache : false,
			success : function(resp) {
				$("#${param.popupid}").html(resp);
			}
		});
	}
	
	function searchPopup()
	{
		$.ajax({
			url : getPopUrl(),
			data : $('#popupForm').serialize(),
			type : 'POST',
			cache : false,
			success : function(resp) {
				$("#${param.popupid}").html(resp)
			}
		});
	}

	function reloadPopup()
	{
		$("#${param.popupid}").load("<c:url value='${url}'/>");
	}
</script>
<input type="submit" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.search"/>' onclick="searchPopup();"/>
<input type="button" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.reset"/>' onclick="reloadPopup();"/>