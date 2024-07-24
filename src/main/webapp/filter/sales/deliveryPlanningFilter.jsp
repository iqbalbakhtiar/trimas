<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 40%; height: 320px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right"><spring:message code="deliveryplanning.code"/></td>
			<td>:</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right" width="15%"><spring:message code="deliveryplanning.organization"/></td>
			<td width="4%">:</td>
			<td width="35%" height="28">
				<select id="orgto" name="organization" class="combobox-ext">
                <c:if test='${not empty filterCriteria.organization}'>
                  	<option value="${filterCriteria.organization}">${org.fullName}</option>
                </c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryplanning.salesorder"/></td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="sales" name="sales" value="${filterCriteria.sales}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code="deliveryplanning.customer"/></td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="customer" name="customer" value="${filterCriteria.customer}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:</td>
            <td><input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.dateto"/></td>
            <td>:</td>
            <td><input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<!--<tr>
             <td align="right"><spring:message code="deliveryplanning.dorcreated"/></td>
             <td>:</td>
             <td>
	             <select id="delivery" name="delivery" class="combobox-min">
	                <option value="">&nbsp;</option>
	              	<option value="true" ${not empty filterCriteria.delivery and filterCriteria.delivery ? 'selected' : ''}><spring:message code="sirius.yes"/></option>
	   				<option value="false" ${not empty filterCriteria.delivery and !filterCriteria.delivery ? 'selected' : ''}><spring:message code="sirius.no"/></option>
	          	 </select>
             </td>
         </tr>-->
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