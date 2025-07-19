<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
  	<a class="item-button-list" href="<c:url value='/page/fundapplicationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-next" href="javascript:next();"><span><spring:message code="sirius.generate"/></span></a>
</div>

<div class="main-box">
    <sesform:form name="addForm" id="addForm" method="post" modelAttribute="filterCriteria">
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
            <tr>
                <td width="24%" align="right"><spring:message code="currencymanagement"/> :</td>
  				<td>
                    <form:select id='currency' path='currencyId'>
                        <c:forEach items='${currencys}' var='currency'>
                            <form:option value='${currency.id}' label='${currency.symbol}'/>
                        </c:forEach>
                    </form:select>
                    <form:input id="trxrate" path='rate' cssClass="input-number" value="1" size="10"/>
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

        submit("addForm","<c:url value='/page/fundapplicationpreadd2.htm'/>");
    }

</script>