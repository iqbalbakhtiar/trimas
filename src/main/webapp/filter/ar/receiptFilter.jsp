<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 35%; height: 240px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right" width="15%"><spring:message code="sirius.code"/></td>
			<td width="3%">:&nbsp;</td>
			<td width="45%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<%-- <tr>
			<td align="right"><spring:message code="organization"/></td>
			<td>:&nbsp;</td>
			<td>
				<select id="orgto" name="organization" class="combobox">
					<c:if test='${not empty filterCriteria.organization}'>
						<option value="${filterCriteria.organization}">${org.fullName}</option>
					</c:if>
				</select>
            	&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title="Company Structure" />
			</td>
		</tr> --%>
        <tr>
			<td align="right"><spring:message code="customer"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="customer" name="customer" value="${filterCriteria.customer}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
             <td align="right"><spring:message code="receipt.payment.type"/></td>
             <td>:&nbsp;</td>
             <td>
	            <select id="paymentMethodType" name="paymentMethodType" class="combobox-min">
	            	<option value="" selected>ALL</option>
			       	<c:forEach items='${types}' var='type' varStatus="status">
                        <option ${type == filterCriteria.paymentMethodType ? 'selected' : ''} value='${type}'>${type}</option>>
                    </c:forEach>
	            </select>
             </td>
         </tr>
	  	<%-- <tr>
			<td align="right"><spring:message code="sirius.status"/></td>
			<td>:&nbsp;</td>
			<td>
				<select id="type" name="type" class="combobox-min">
					<option value="" selected>&nbsp;</option>
					<option value="true" ${not empty filterCriteria.type and filterCriteria.type ? 'selected' : ''}><spring:message var="clearing" code="receipt.clear"/>${fn:toUpperCase(clearing)}</option>
					<option value="false" ${not empty filterCriteria.type and !filterCriteria.type ? 'selected' : ''}><spring:message var="unclear" code="sirius.unclear"/>${fn:toUpperCase(unclear)}</option>
				</select>
			</td>
        </tr> --%>
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
