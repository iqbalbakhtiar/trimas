<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 48%; height: 250px; left: 350px; display:none;" toggle="explode">
    <form id="filterForm" name="filterForm" method="post">
        <table width="100%" cellspacing="0" cellpadding="1" align="right">
            <tr>
                <td align="right"><spring:message code="sirius.code"/> &nbsp;</td>
                <td align="center">:</td>
                <td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
            </tr>
            <tr>
                <td align="right" ><spring:message code="organization"/>&nbsp;</td>
                <td width="5%" align="center">:</td>
                <td width="60%">
                    <select id="org" name="organization" class="combobox-ext">
                    </select>
                    &nbsp;
                    <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.datefrom"/> &nbsp;</td>
                <td align="center">:</td>
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
    </form>
</div>
