<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 40%; height: 380px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td width="15%" align="right"><spring:message code="standardsalesorder.code"/></td>
			<td width="4%">:</td>
			<td width="45%" height="28"><input type="text" id="entryNo" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.organization"/></td>
			<td>:</td>
			<td>
				<%--<select id="org" name="organization" class="combobox-ext">
				<c:if test='${not empty filterCriteria.organization}'>
              		<option value="${filterCriteria.organization}">${org.fullName}</option>
        		</c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title='<spring:message code="organization.structure"/>' />--%>
				<input type="text" id="organizationName" name="organizationName" value="${filterCriteria.organizationName}" size="35" class="inputbox"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.customer"/></td>
			<td>:</td>
			<td>
				<%--<select id="customer" name="customer" class="combobox-ext">
					<c:if test='${not empty filterCriteria.customer}'>
                  		<option value="${filterCriteria.customer}">${customer.fullName}</option>
                	</c:if>
				</select>
				<a class="item-popup" onclick="opencustomer();"  title="Customer" />--%>
				<input type="text" id="customerName" name="customerName" value="${filterCriteria.customerName}" size="35" class="inputbox"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.salesperson"/></td>
			<td>:</td>
			<td>
				<%--<select id="salesPerson" name="salesPerson" class="combobox-ext">
					<c:if test='${not empty filterCriteria.salesPerson}'>
                  		<option value="${filterCriteria.salesPerson}">${sales.fullName}</option>
                	</c:if>
				</select>
				<a class="item-popup" onclick="opensalesperson();" title='<spring:message code="standardsalesorder.salesperson"/>' />--%>
				<input type="text" id="salesPersonName" name="salesPersonName" value="${filterCriteria.salesPersonName}" size="35" class="inputbox"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.approvalstatus"/></td>
			<td>:</td>
			<td>
				<select id="approval" name="approval" class="combobox-ext">
					<option value=""></option>
					<c:forEach items='${decisions}' var='type'>
                        <spring:message var="src" code="approval.${type.message}"/>
                   		<option ${type == filterCriteria.approval ? 'selected' : ''} value='${type}'>${fn:toUpperCase(src)}</option>
                    </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.datefrom"/></td>
			<td>:</td>
			<td>
				<input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
				<spring:message code="sirius.dateto"/>
				<input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.docreated"/></td>
			<td>:</td>
			<td>
				<select id="doCreated" name="doCreated" class="combobox-min">
					<option value=''></option>
					<option value='true'  <c:out value="${not empty filterCriteria.doCreated && filterCriteria.doCreated ? 'selected' : ''}"/>><spring:message code="sirius.yes"/></option>
					<option value='false' <c:out value="${not empty filterCriteria.doCreated && !filterCriteria.doCreated ? 'selected' : ''}"/>><spring:message code="sirius.no"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.dorcreated"/></td>
			<td>:</td>
			<td>
				<select id="dorCreated" name="dorCreated" class="combobox-min">
					<option value=''></option>
					<option value='true' <c:out value="${not empty filterCriteria.dorCreated && filterCriteria.dorCreated ? 'selected' : ''}"/>><spring:message code="sirius.yes"/></option>
					<option value='false' <c:out value="${not empty filterCriteria.dorCreated && !filterCriteria.dorCreated ? 'selected' : ''}"/>><spring:message code="sirius.no"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.billingcreated"/></td>
			<td >:</td>
			<td>
				<select id="billingCreated" name="billingCreated" class="combobox-min">
					<option value=''></option>
					<option value='true' <c:out value="${not empty filterCriteria.billingCreated && filterCriteria.billingCreated ? 'selected' : ''}"/>><spring:message code="sirius.yes"/></option>
					<option value='false' <c:out value="${not empty filterCriteria.billingCreated && !filterCriteria.billingCreated ? 'selected' : ''}"/>><spring:message code="sirius.no"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="standardsalesorder.note"/></td>
			<td>:</td>
			<td><input type="text" id="note" name="note" value="${filterCriteria.note}" size="30" class="inputbox"/></td>
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
<script type="text/javascript">
	function opensalesperson()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert("<spring:message code='notif.select1'/> <spring:message code='sirius.organization'/> <spring:message code='notif.select2'/>!");
			return;
		}

		openpopup("<c:url value='/page/popupsalespersonview.htm?target=salesPerson&organization='/>"+org.value);
	}
	
	function opencustomer()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert("<spring:message code='notif.select1'/> <spring:message code='sirius.organization'/> <spring:message code='notif.select2'/>!");
			return;
		}
		
		openpopup("<c:url value='/page/popupcustomerview.htm?target=customer&organization='/>"+org.value);
	}
</script>