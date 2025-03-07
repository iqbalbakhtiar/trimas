<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span>Reset</span></a>
    <a class="item-button-generate-report" href="javascript:generate();"><span>Generate</span></a>
</div>

<div class="main-box">
    <sesform:form name="reportForm" id="reportForm" method="post" modelAttribute="criteria">
        <table width="100%" style="border:none">
            <tr>
                <td><div align="right">Organization :</div></td>
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
                <td nowrap="nowrap" align="right">Supplier :</td>
                <td>
                    <form:select id="supplier" path="supplier" cssClass="combobox-ext">
                        <c:if test='${not empty criteria.supplier}'>
                            <form:option value='${criteria.supplier.id}' label='${criteria.supplier.fullName}' />
                        </c:if>
                    </form:select>
                    <img src="assets/icons/list_extensions.gif" onclick="openSupplier();" style="CURSOR:pointer;" title="Supplier" />
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right">Currency :</td>
                <td>
                    <form:select id="currency" path="currency" cssClass="combobox-ext">
                        <form:option value="" label="" selected="true"/>
                        <form:option value="1" label="RP" />
                    </form:select>
                </td>
            </tr>
            <tr>
                <td align="right">Date :</td>
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

        document.reportForm.action = "<c:url value='/page/apageingdetailview.htm'/>";
        document.reportForm.submit();
    }

    function resetform()
    {
        window.location="<c:url value='/page/apageingdetailpre.htm'/>";
    }

    function openSupplier() {
        if (!$('#org').val()) {
            alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
            return;
        }

        const orgId = $('#org').val();
        const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
        const params = {
            target: 'supplier', // Id Dropdown (Select) element
            organization: orgId, // Org (PartyTo)
            fromRoleType: 5, // Supplier
            toRoleType: 4, // Customer
            relationshipType: 4 // Supplier Relationship
        };

        openpopup(buildUrl(baseUrl, params));
    }
</script>