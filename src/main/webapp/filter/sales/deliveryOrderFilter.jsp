<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 25%; height: 250px; left: 300px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
				<td width="4">:&nbsp;</td>
				<td width="310" height="28"><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
			</tr>
			<tr>
				<td align="right" style="WIDTH:130px;"><spring:message code="facility"/>&nbsp;&nbsp;</td>
				<td width="4">:&nbsp;</td>
				<td width="310" height="28"><input type="text" name="facility" id="facility" class="inputbox" value="<c:out value='${filterCriteria.facility}'/>"/></td>
			</tr>
			<tr>
				<td align="right" style="WIDTH:130px;"><spring:message code="customer"/>&nbsp;&nbsp;</td>
				<td width="4">:&nbsp;</td>
				<td width="310" height="28"><input type="text" name="customer" id="customer" class="inputbox" value="<c:out value='${filterCriteria.customer}'/>"/></td>
			</tr>
			<tr>
				<td align="right" style="WIDTH:130px;"><spring:message code="deliveryorder.shippingaddress"/>&nbsp;&nbsp;</td>
				<td width="4">:&nbsp;</td>
				<td width="310" height="28"><input type="text" name="shippingAddress" id="shippingAddress" class="inputbox" value="<c:out value='${filterCriteria.shippingAddress}'/>"/></td>
			</tr>
			<tr>
				<td align="right" style="WIDTH:130px;"><spring:message code="deliveryorder.status"/>&nbsp;&nbsp;</td>
				<td width="4">:&nbsp;</td>
				<td >
					<select id="status" name="status">
						<option value=""><spring:message code="sirius.all"/></option>
						<%@ page import="com.siriuserp.sales.dm.SOStatus" %>
						<c:forEach var="status" items="${SOStatus.values()}">
							<option value="${status}"
								${not empty filterCriteria.status and filterCriteria.status == status ? "selected" : ""}>
									${status}
							</option>
						</c:forEach>
					</select>
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
