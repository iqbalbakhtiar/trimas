<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/></span></a>
    <a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="ledgerCriteria">
        <table width="100%" style="border:none">
            <tr>
                <td width="22%"><div align="right">Organization :</div></td>
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
                <td align="right">Date  :</td>
                <td>
                    <input id="date" name="date" class="datepicker"/>
                </td>
            </tr>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    function generate()
    {
        const date = document.getElementById('date');
        if(!date.value)
        {
            alert('Please select Date first!!!');
            return;
        }

        submit("reportForm","<c:url value='/page/ledgersummaryreportview.htm'/>");
    }

</script>