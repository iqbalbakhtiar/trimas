<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 230px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right" width="200"><spring:message code="product.code"/>&nbsp;&nbsp;</td>
			<td width="5">:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.name"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.category"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="productCategory" name="productCategory" value="${filterCriteria.productCategory}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.uom"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="uom" name="uom" value="${filterCriteria.uom}" size="35" class="inputbox"/></td>
		</tr>
		<tr style="display: none;">
			<td align="right"><spring:message code="container"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" name="container" value="${filterCriteria.container}" size="35" class="inputbox"/></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<%@ include file="/pages/includes/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
