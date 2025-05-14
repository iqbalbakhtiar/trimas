<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/>&nbsp;</span></a>
    <a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="mutationCriteria">
        <table width="100%" style="border:none">
            <tr>
                <td width="22%"><div align="right"><spring:message code="sirius.organization"/> :</div></td>
                <td width="78%">
                    <select id="org" name="organization" class="combobox-ext">
                        <c:if test='${not empty organization}'>
                            <option value="${organization.id}"><c:out value='${organization.fullName}'/></option>
                        </c:if>
                    </select>
                    <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/>&nbsp;:</td>
                <td>
                    <input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
<%--                    &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;--%>
<%--                    <input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>--%>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="contactmechanism.department"/> :</td>
                <td nowrap="nowrap">
                    <form:input id='department' path="department" size="43"/>
                </td>
            </tr>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp" %>
<script type="text/javascript">
    function generate()
    {
        var org = document.getElementById('org');
        if(org.value == '')
        {
            alert('Please select company first!');
            return;
        }

        var dateFrom = document.getElementById('dateFrom');
        if (!dateFrom || dateFrom.value.trim() === '') {
            alert('Please select a date first!');
            dateFrom && dateFrom.focus();
            return;
        }

        document.reportForm.action = "<c:url value='/page/mutationreportview.htm'/>";
        document.reportForm.submit();
    }

    function resetform()
    {
        var org = document.getElementById('org');

        for(var idx=org.options.length;idx>=0;idx--)
            org.remove(org.selectedIndex);

        var dateFrom = document.getElementById('dateFrom');
        if(dateFrom) dateFrom.value = '';

        var dateTo = document.getElementById('dateTo');
        if(dateTo) dateTo.value = '';

        var dept = document.getElementById('department');
        if(dept) dept.value = '';

        document.reportForm.reset();
    }
</script>