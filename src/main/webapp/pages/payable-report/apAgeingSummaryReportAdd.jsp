<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/></span></a>
    <a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="criteria">
        <table width="100%" style="border:none">
            <tr>
                <td><div align="right"><spring:message code="organization"/> :</div></td>
                <td>
                    <select id="org" name="organization" class="combobox-ext">
                        <c:if test='${not empty organization}'>
                            <option value="${organization.id}"><c:out value='${organization.fullName}'/></option>
                        </c:if>
                    </select>
                    <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.date"/> :</td>
                <td>
                    <input id="dateFrom" name="date" class="datepicker" />
                </td>
            </tr>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    function generate()
    {
        var from = document.getElementById('dateFrom');
        if(from.value == '')
        {
            alert('Date cannot be empty!');
            return;
        }

        document.reportForm.action = "<c:url value='/page/apageingsummaryview.htm'/>";
        document.reportForm.submit();
    }

    function resetform()
    {
        window.location="<c:url value='/page/apageingsummarypre.htm'/>";
    }
</script>