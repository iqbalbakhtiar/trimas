<%@ include file="/common/sirius-general-top.jsp" %>

<div class="toolbar">
    <a class="item-button-cancel" href="javascript:resetform();"><span><spring:message code="sirius.reset"/>&nbsp;</span></a>
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
                            <option value='${organization.id}'>${organization.fullName}</option>
                        </c:if>
                    </select>
                    <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.document.type"/>&nbsp;:</td>
                <td>
                    <form:select id="reference" path="reference" cssClass="combobox-min">
                        <option value=""><spring:message code="sirius.all"/></option>
                        <c:forEach var="docType" items="${documentTypes}">
                            <form:option value="${docType}">${docType.normalizedName}</form:option>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="supplier"/> :</td>
                <td>
                    <select id="supplier" name="supplier" class="combobox-ext">
                    </select>
                    <a class="item-popup" onclick="openSupplier();" title="Supplier" />
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.datefrom2"/> :</td>
                <td>
                    <input name="dateFrom" id='dateFrom' class="datepicker"/>
                    &nbsp;<spring:message code="sirius.dateto2"/>
                    <input name="dateTo" id='dateTo' class="datepicker"/>
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

        document.reportForm.action = "<c:url value='/page/goodsreceiptreportview.htm'/>";
        document.reportForm.submit();
    }

    function resetform()
    {
        window.location = "<c:url value='/page/wastereportpre.htm'/>";
    }

    function openfacility(target)
    {
        var org = document.getElementById('org');
        if(org.value == '')
        {
            alert('Please select company first!');
            return;
        }

        openpopup("<c:url value='/page/popupfacilityview.htm?type=1&organization='/>"+org.value+"&target="+target);
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