<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 45%; height: 580px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="1" align="right">
	<tr>
		<td width="35%" align="right"><spring:message code="sirius.code"/>&nbsp;</td>
		<td width="5%" align="center">:</td>
		<td width="60%"><input name="code" id="code" class="inputbox" value="${filterCriteria.code}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.name"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="name" name="name" class="inputbox" value="${filterCriteria.name}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.uom"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="uom" name="uom" class="inputbox" value="${filterCriteria.unitOfMeasure}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="productcategory"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="productCategory" name="productCategory" class="inputbox" value="${filterCriteria.productCategory}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.type"/>&nbsp;</td>
		<td >:</td>
		<td >
			<select id="type" name="type">
				<option value=""><spring:message code="sirius.all"/></option>
				<option value="GOODS" ${not empty filterCriteria.type and filterCriteria.type == "GOODS" ? "selected" : ""}>GOODS</option>
				<option value="SERVICE" ${not empty filterCriteria.type and filterCriteria.type == "SERVICE" ? "selected" : ""}>SERVICE</option>
				<option value="ASSET" ${not empty filterCriteria.type and filterCriteria.type == "ASSET" ? "selected" : ""}>ASSET</option>
				<option value="NON_ASSET" ${not empty filterCriteria.type and filterCriteria.type == "NON_ASSET" ? "selected" : ""}>NON ASSET</option>
			</select>       
        </td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.status"/>&nbsp;</td>
		<td align="center">:</td>
		<td >
			<select id="status" name="status">
				<option value=""><spring:message code="sirius.all"/></option>
				<option value="true" ${not empty filterCriteria.status and filterCriteria.status ? "selected" : ""}><spring:message code="sirius.active"/></option>
				<option value="false" ${not empty filterCriteria.status and !filterCriteria.status ? "selected" : ""}><spring:message code="sirius.inactive"/></option>
			</select>
        </td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.origin"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="origin" name="origin" class="inputbox" value="${filterCriteria.origin}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.brand"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="brand" name="brand" class="inputbox" value="${filterCriteria.brand}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.grade"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="grade" name="grade" class="inputbox" value="${filterCriteria.grade}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="product.part"/>&nbsp;</td>
		<td align="center">:</td>
		<td ><input type="text" id="part" name="part" class="inputbox" value="${filterCriteria.part}"/></td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td colspan="2">&nbsp;</td>
		<td align="left">
			<%@ include file="/common/filter.jsp"%>
		</td>
	</tr>
	</table>
	</form>
</div>
