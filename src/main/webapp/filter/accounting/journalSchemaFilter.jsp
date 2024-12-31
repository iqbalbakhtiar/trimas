<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 39%; height: 220px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
        <table width="100%" cellspacing="0" cellpadding="1" align="right">
        <tr>
            <td align="right" style="WIDTH:130px;"><spring:message code="journalschema.code"/></td>
            <td width="4">:</td>
            <td width="310" height="28"><input name="code" value="${filterCriteria.code}"/></td>
        </tr>
        <tr>
            <td align="right" style="WIDTH:130px;"><spring:message code="journalschema.name"/></td>
            <td width="4">:</td>
            <td width="310" height="28"><input name="name" value="${filterCriteria.name}"/></td>
        </tr>
        <tr>
            <td align="right" style="WIDTH:130px;"><spring:message code="journalschema.organization"/></td>
            <td width="4">:</td>
            <td width="310" height="28">
            	<select id="org" name="organization" class="combobox-ext">
            		<option value='${organization.id}'><c:out value='${organization.firstName} ${organization.middleName} ${organization.lastName}'/></option>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
            <td align="left">
				<%@ include file="/common/filter.jsp"%>
            </td>
        </tr>
        </table>
	</form>
</div>