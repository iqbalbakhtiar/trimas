<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 35%; height: 280px; left: 350px; display:none;" toggle="explode" bg>
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
				<td ><input type="text" id="categoryName" name="categoryName" class="inputbox" value="${filterCriteria.categoryName}"/></td>
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
				<td>
					<label>
						<input type="radio" id="statusAll" name="status" value="" ${empty filterCriteria.status ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.all"/>
					</label>
					<label>
						<input type="radio" id="statusActive" name="status" value="true" ${not empty filterCriteria.status and filterCriteria.status ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.active"/>
					</label>
					<label>
						<input type="radio" id="statusInactive" name="status" value="false" ${not empty filterCriteria.status and !filterCriteria.status ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.inactive"/>
					</label>
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="product.serial"/>&nbsp;</td>
				<td align="center">:</td>
				<td>
					<label>
						<input type="radio" id="serialAll" name="serial" value=""
						${empty filterCriteria.serial ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.all"/>
					</label>
					<label>
						<input type="radio" id="serialYes" name="serial" value="true"
						${not empty filterCriteria.serial and filterCriteria.serial ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.yes"/>
					</label>
					<label>
						<input type="radio" id="serialNo" name="serial" value="false"
						${not empty filterCriteria.serial and !filterCriteria.serial ? 'checked="checked"' : ''}/>
						<spring:message code="sirius.no"/>
					</label>
				</td>
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
