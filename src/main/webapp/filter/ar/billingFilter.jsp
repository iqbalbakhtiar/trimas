<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 33%; height: 230px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right" width="15%"><spring:message code="billing"/>&nbsp;(<spring:message code="billing.code"/>)</td>
			<td width="3%">:&nbsp;</td>
			<td width="45%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code="billing.customer"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="cust" name="cust" value="${filterCriteria.cust}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		 <tr>
			<td align="right"><spring:message code="sirius.reference"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="35" class="inputbox"/></td>
		</tr>
		<!--
		<tr>
			<td align="right"><spring:message code="billing.organization"/></td>
			<td width="4">:&nbsp;</td>
			<td>
				<select id="orgto" name="organization" class="combobox-ext">
					<c:if test='${not empty filterCriteria.organization}'>
					  <option value="${filterCriteria.organization}">${org.fullName}</option>
					</c:if>
				</select>
				&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title='<spring:message code="organization.structure"/>' />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.status"/></td>
			<td>:&nbsp;</td>
			<td>
				<select id="status" name="status" class="combobox">
					<option value=""></option>
					<c:forEach items='${statuses}' var='type'>
                        <spring:message var="src" code="sirius.${type.message}"/>
                   		<option ${type == filterCriteria.status ? 'selected' : ''} value='${type}'>${fn:toUpperCase(src)}</option>
                    </c:forEach>
				</select>
			</td>
				
		</tr>
		<tr>
			<td align="right"><spring:message code="billing.invoicetax"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="taxinvoice" name="taxinvoice" class="combobox">
					<option value=''><spring:message code="sirius.all"/></option>
					<option value='true'><spring:message code="sirius.yes"/></option>
					<option value='false'><spring:message code="sirius.no"/></option>
				</select>
			</td>
				
		</tr>
		-->
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
