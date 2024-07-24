<script type="text/javascript">
	var url=window.location.pathname;
	function generate() {
		document.filterForm.action = url;
		document.filterForm.submit();
	}

	function resets() {
		window.location = url;
	}
</script>
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 48%; height: auto; left: 300px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<input type="hidden" name="personId" id="personId" value="${filter.personId}"/>
		<table>
			<tr>
				<td>Sorting</td>
				<td>
					<select name="sorting">
						<option value="s.first_name" ${filter.sorting=="c.first_name"?"selected":""}>Person</option>
						<option value="org_company_index" ${filter.sorting=="o.first_name"?"selected":""}>Company</option>
						<option value="(d.total_amount-d.total_cogs)"  ${filter.sorting=="(d.total_amount-d.total_cogs)"?"selected":""}>Gross Profit</option>
					</select>
					<select name="sortingMode">
						<option value="ASC">Ascending</option>
						<option value="DESC">Descending</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Index</td>
				<td>
					<select name="indexName" id="indexName">
						<option value="ALL">-INDEX-</option>
						<option value="PERSON_INDEX" ${filter.indexName=="PERSON_INDEX"?"selected":""}>PERSON</option>
						<option value="COMPANY_INDEX" ${filter.indexName=="COMPANY_INDEX"?"selected":""}>COMPANY</option>
						<option value="CITY_INDEX" ${filter.indexName=="CITY_INDEX"?"selected":""}>LOCATION</option>
						<option value="PRODUCT_INDEX" ${filter.indexName=="PRODUCT_INDEX"?"selected":""}>PRODUCT</option>
					</select>
				</td>
			</tr>
			<tr><td>Company</td><td><input type="text" id="companyIndex" name="companyIndex" value="${filter.companyIndex}"/></td></tr>
			<tr><td>Person</td><td><input type="text" id="personIndex" name="personIndex" value="${filter.personIndex}"/></td></tr>
			<tr><td>Location </td><td><input type="text" id="cityIndex" name="cityIndex" value="${filter.cityIndex}"/></td></tr>
			<tr><td>Product</td><td><input type="text" id="productIndex" name="productIndex" value="${filter.productIndex}"/></td></tr>
			<tr>
				<td>From</td>
				<td>
				<input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filter.dateFrom}'/>" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" />
				&nbsp;To&nbsp;
				<input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filter.dateTo}'/>" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" />
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