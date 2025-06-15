<%@ include file="/common/tld-common.jsp"%>
<table id="salesdet" style="border:none;" width="100%" cellspacing="0" cellpadding="3">
	<thead>
 	<tr>
 		<td colspan="3"><strong>SALES DETAIL</strong></td>
 	</tr>
 	<tr>
 		<td>&nbsp;</td>
 	</tr>
	<tr>
 		<td><spring:message code="organization"/></td>
     	<td colspan="2">: <c:out value='${organization.fullName}'/></td>
	</tr>
	<tr>
 		<td><spring:message code="customer"/></td>
     	<td colspan="2">: 
     		<c:if test="${not empty customer}"><c:out value='${customer.fullName}'/></c:if>
     		<c:if test="${empty customer}"><spring:message code='sirius.all'/></c:if>
     	</td>
	</tr>
	<tr>
		<td><spring:message code="sirius.period"/></td>
   		<td colspan="2">: <fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy'/> <spring:message code="sirius.to2"/> <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy'/></td>
	</tr>
 	<tr>
 		<td>&nbsp;</td>
 	</tr>
	</thead>
	<thead>
	<tr style="height: 30px">
		<th width="1%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesreport.no"/></th>
		<th width="5%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.date"/></th>
		<th width="10%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="salesorder.contract.no"/></th>
		<th width="20%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="customer"/></th>
		<th width="15%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="product"/></th>
		<th width="10%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.qty"/></th>
		<th width="3%" align="left" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.uom"/></th>
		<c:if test="${criteria.showBale}">
			<th width="8%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;">Bale</th>
		</c:if>
		<th width="10%"align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.price"/></th>
		<th width="15%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;">Sub <spring:message code="sirius.total"/></th>
		<th width="15%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.tax"/></th>
		<th width="25%" align="right" style="border-top:solid 1px black;border-bottom:solid 1px black;"><spring:message code="sirius.total"/></th>
	</tr>
	</thead>
	<tbody>
 	<c:set var='no' value='0'/>
 	<c:set var='t_quantity' value='0'/>
 	<c:set var='t_quantityBale' value='0'/>
 	<c:set var='t_amount' value='0'/>
 	<c:set var='t_tax' value='0'/>
 	<c:set var='t_total' value='0'/>
	<c:forEach items='${reports}' var='repo' varStatus="status">
		<c:set var='no' value='${no + 1}'/>
		<c:forEach items='${repo.items}' var='item' varStatus="detStatus">
		<tr>
			<td align="left" nowrap="nowrap"><c:out value='${no})'/></td>
			<td align="left" nowrap="nowrap"><fmt:formatDate value='${repo.salesOrder.date}' pattern='dd MMM yyyy'/></td>
			<td align="left" nowrap="nowrap"><a href="<c:url value='/page/salesorderpreedit.htm?id=${repo.salesOrder.id}'/>">${repo.salesOrder.code}</a></td>
			<td align="left" nowrap="nowrap"><c:out value='${repo.salesOrder.customer.fullName}'/></td>
			<td align="left" nowrap="nowrap"><c:out value='${item.product.name}'/></td>
			<td align="right"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
			<td align="left" nowrap="nowrap"><c:out value='${item.product.unitOfMeasure.measureId}'/></td>
			<c:if test="${criteria.showBale}">
				<td align="right"><fmt:formatNumber value='${item.quantity / 181.44}' pattern=',##0.00'/></td>
			</c:if>
	  		<td align="right"><fmt:formatNumber value='${item.amount}' pattern=',##0.00'/></td>
	  		<td align="right"><fmt:formatNumber value='${item.subTotal}' pattern=',##0.00'/></td>
	  		<td align="right"><fmt:formatNumber value='${item.taxAmount}' pattern=',##0.00'/></td>
	  		<td align="right"><fmt:formatNumber value='${item.total}' pattern=',##0.00'/></td>
		</tr>
		<c:set var='t_quantity' value='${t_quantity + item.quantity}'/>
		<c:set var='t_quantityBale' value='${t_quantityBale + (item.quantity / 181.44)}'/>
		<c:set var='t_amount' value='${t_amount + item.subTotal}'/>
		<c:set var="t_tax" value="${t_tax + item.taxAmount}"/>
		<c:set var="t_total" value="${t_total + item.total}"/>
		</c:forEach>
	</c:forEach>
	</tbody>
	<tfoot>
	<tr style="height: 30px">
		<td colspan="5" style="border-top:solid 1px black;border-bottom:solid 1px black;"><strong><spring:message code="sirius.total"/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_quantity}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong>&nbsp;</strong></td>
		<c:if test="${criteria.showBale}">
			<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_quantityBale}' pattern=',##0.00'/></strong></td>
		</c:if>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong>&nbsp;</strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_amount}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_tax}' pattern=',##0.00'/></strong></td>
		<td style="border-top:solid 1px black;border-bottom:solid 1px black;" align="right"><strong><fmt:formatNumber value='${t_total}' pattern=',##0.00'/></strong></td>
	</tr>
	</tfoot>
</table>
