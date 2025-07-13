<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-next" href="javascript:next();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="addForm" id="addForm" method="post" modelAttribute="ledgerCriteria">
        <table width="100%" style="border:none">
            <tr>
                <td width="22%" align="right"><spring:message code="organization"/></td>
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
                <td align="right"><spring:message code="sirius.date"/> : </td>
                <td>
                    <input id="date" name="date" class="datepicker"/>
                </td>
            </tr>
        </table>
    </sesform:form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
    function next()
    {
   	  	var date = $('#date').val();
   	  	if (date == null || date === "") {
   	    	alert('<spring:message code="sirius.date"/> <spring:message code="notif.empty"/> !');
   	    	return false;
   	  	}

        submit("addForm","<c:url value='/page/fundapplicationadd2.htm'/>");
    }

</script>