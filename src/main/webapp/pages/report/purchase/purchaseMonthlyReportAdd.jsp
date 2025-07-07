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
				<td align="right"><spring:message code="sirius.period"/>:</td>
				<td>
					<select id="month" name="month">
						<c:forEach var="month" items="${months}">
							<option value="${month}"
									<c:if test="${month == criteria.month}">selected</c:if>>
									${month}
							</option>
						</c:forEach>
					</select>
					&nbsp;
					<select id="year" name="year">
						<c:forEach var="year" begin="${years-10}" end="${years}">
							<option value="${(years*2)-year-10}">${(years*2)-year-10}</option>
						</c:forEach>
					</select>
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

        document.reportForm.action = "<c:url value='/page/purchasemonthlyreportview.htm'/>";
        document.reportForm.submit();
    }

    function resetform()
    {
        window.location = "<c:url value='/page/wastereportpre.htm'/>";
    }
</script>