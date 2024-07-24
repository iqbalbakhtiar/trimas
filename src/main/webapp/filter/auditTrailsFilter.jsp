<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 58%; height: 230px; left: 300px; display:none;" toggle="explode" bg>
	<sesform:form id="filterForm" name="filterForm" method="post" modelAttribute="filterCriteria">
    <table width="100%" cellspacing="0" cellpadding="0" align="right">
    <tr>
        <td align="right" style="WIDTH:130px;"><spring:message code="sirius.menu"/>&nbsp;&nbsp;</td>
        <td width="7">:&nbsp;</td>
        <td width="316" height="28"><form:input id="menu" path="menu"/></td>
    </tr>
    <tr>
        <td align="right"><spring:message code="sirius.id"/>&nbsp;&nbsp;</td>
        <td width="7">:&nbsp;</td>
        <td width="316" height="28"><form:input id="id" path="id"/></td>
    </tr>
    <tr>
        <td align="right"><spring:message code="history.action"/>&nbsp;&nbsp;</td>
        <td width="7">:&nbsp;</td>
        <td width="316" height="28">
            <form:select path="action" id="action" cssClass="combobox">
                <form:option value='' label=''/>
                <form:option value="CREATE" label='CREATE'/>
                <form:option value="UPDATE" label='UPDATE'/>
                <form:option value="DELETE" label='DELETE'/>
            </form:select>
        </td>
    </tr>
    <tr>
        <td align="right"><spring:message code="history.person"/> &nbsp;&nbsp;</td>
        <td width="7">:&nbsp;</td>
        <td width="316" height="28">
        	<form:select path="person" id="person" cssClass="combobox-ext">
        	</form:select>
            &nbsp;
            <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popuppersonview.htm?target=person'/>');"  title="Person" />
        </td>
    </tr>
    <tr>
        <td align="right"><spring:message code="sirius.datefrom"/> &nbsp;&nbsp;</td>
        <td>:&nbsp;</td>
        <td>
            <input id="dateFrom" name="dateFrom" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
            <input id="dateTo" name="dateTo" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
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
	</sesform:form>
</div>
