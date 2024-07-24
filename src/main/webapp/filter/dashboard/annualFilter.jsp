<script type="text/javascript">
	var url=window.location.pathname;
	function generate() {
		document.filterForm.action = url;
		document.filterForm.submit();
	}

	function resets() {
		window.location = url;
	}
	function _indexChange(id,display){
		$("#"+id+"Index").css("display",display);
		$("#"+id+"Hidden").val("");
		$("#"+id+"Value").val("");
	}
	function indexChange(val){
		switch(val){
		case "PERSON_INDEX":
			_indexChange("company","none");
			_indexChange("city","none");
			_indexChange("product","none");
			_indexChange("person","");
			break;
		case "COMPANY_INDEX":
			_indexChange("company","");
			_indexChange("city","none");
			_indexChange("product","none");
			_indexChange("person","none");
			break;
		case "CITY_INDEX":
			_indexChange("company","none");
			_indexChange("city","");
			_indexChange("product","none");
			_indexChange("person","none");
			break;
		case "PRODUCT_INDEX":
			_indexChange("company","none");
			_indexChange("city","none");
			_indexChange("product","");
			_indexChange("person","none");
			break;
		}
	}
	function changeValue(id){
		$("#"+id+"Hidden").val($("#"+id+"Value").val());
	}
</script>
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 48%; height: auto; left: 300px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<input type="hidden" id="currentIndex" value="${filter.indexName}"/>
		<input type="hidden" name="personIndex" id="personHidden" value="${filter.personIndex}"/>
		<input type="hidden" name="companyIndex" id="companyHidden" value="${filter.companyIndex}"/>
		<input type="hidden" name="cityIndex" id="cityHidden" value="${filter.cityIndex}"/>
		<input type="hidden" name="productIndex" id="productHidden" value="${filter.productIndex}"/>

		<table>
			<tr>
				<td>Index</td>
				<td>
					<select name="indexName" id="indexName" onchange="indexChange(this.value)">
						<option value="PERSON_INDEX" ${filter.indexName=="PERSON_INDEX"?"selected":""}>PERSON</option>
						<option value="COMPANY_INDEX" ${filter.indexName=="COMPANY_INDEX"?"selected":""}>COMPANY</option>
						<option value="CITY_INDEX" ${filter.indexName=="CITY_INDEX"?"selected":""}>LOCATION</option>
						<option value="PRODUCT_INDEX" ${filter.indexName=="PRODUCT_INDEX"?"selected":""}>PRODUCT</option>
					</select>
				</td>
			</tr>
			<tr id="personIndex" style="display:${filter.indexName=="PERSON_INDEX"?"auto":"none"}">
				<td>Person</td>
				<td><input type="text" id="personValue" value="${filter.personIndex}" onkeyup="changeValue('person');"/></td>
			</tr>
			<tr id="companyIndex" style="display:${filter.indexName=="COMPANY_INDEX"?"auto":"none"}">
				<td>Company</td>
				<td><input type="text" id="companyValue" value="${filter.companyIndex}" onkeyup="changeValue('company');"/></td>
			</tr>
			<tr id="cityIndex" style="display:${filter.indexName=="CITY_INDEX"?"auto":"none"}">
				<td>Location</td>
				<td><input type="text" id="cityValue" value="${filter.cityIndex}" onkeyup="changeValue('city');"/></td>
			</tr>
			<tr id="productIndex" style="display:${filter.indexName=="PRODUCT_INDEX"?"auto":"none"}">
				<td>Product</td>
				<td><input type="text" id="productValue" value="${filter.productIndex}" onkeyup="changeValue('product');"/></td>
			</tr>
			<tr>
				<td>Year</td>
				<td>
					<select name="year" id="year">
						<c:forEach begin="2006" end="2050" step="1" var="year">
							<option ${year==filter.year?"selected":""}>${year}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr><td></td></tr>
			<tr>
				<td>
					<input type="button" value="Generate" style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="generate();" class="btn"/>
					<input type="button" value="Reset"  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
				</td>
			</tr>
		</table>
	</form>
</div>