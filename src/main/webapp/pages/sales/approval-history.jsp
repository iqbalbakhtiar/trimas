<!--<table width="100%" style="border:none">
<tr>
<td>-->
<fieldset>
    <legend><strong>Approval History </strong></legend>
    <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
        <tr>
            <th width="3%">No</th>
            <th width="27%"><spring:message code="approval.approver"/></th>
            <th width="22%"><spring:message code="sirius.status"/></th>
            <th width="26%"><spring:message code="sirius.date"/></th>
            <th width="22%">Remark</th>
        </tr>
        <c:forEach items='${approvalDecision.histories}' var='history'>
            <tr>
                <td class="small-font"><c:out value='${history.sequence}'/></td>
                <td class="small-font"><c:out value='${history.approver.fullName}'/></td>
                <td class="small-font"><c:out value="${history.approvalDecisionStatus.normalizedName}"></c:out></td>
                <td class="small-font"><fmt:formatDate value='${history.dateTime}' pattern='dd/MM/yyyy HH:mm:dd'/></td>
                <td class="small-font"><c:out value='${history.remark}'/></td>
            </tr>
        </c:forEach>
    </table>
</fieldset>
<!--</td>
</tr>
</table>-->
