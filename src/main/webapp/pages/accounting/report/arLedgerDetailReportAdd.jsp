<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/></span></a>
    <a class="item-button-generate-report" href="javascript:generate();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="ledgerCriteria">
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
                <td align="right">Date  :</td>
                <td>
                    <input id="date" name="date" class="datepicker"/>
                </td>
            </tr>
            <tr>
                <td><div align="right">Customer :</div></td>
                <td>
                    <select id="customer" name="customer" class="combobox-ext">
                    </select>
                    <a class="item-popup" onclick="opencustomer();" title="<spring:message code="customer"/>" />
                </td>
            </tr>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    function generate()
    {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        submit("reportForm","<c:url value='/page/ledgerdetailreportview.htm'/>");
    }

    function opencustomer()
    {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
        const params = {
            target: 'customer', // Id Dropdown (Select) element
            organization: orgId, // Org (PartyTo)
            fromRoleType: 4, // Customer
            toRoleType: 5, // Supplier
            relationshipType: 3, // Customer Relationship
            // base: false // Uncomment this if want to ignore customer group
        };

        openpopup(buildUrl(baseUrl, params));
    }
</script>