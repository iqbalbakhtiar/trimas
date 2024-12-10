<c:if test="${empty submited}">
    <c:set var="submited" value="${approvalDecision.approvable.submit}"/>
</c:if>
<script type="text/javascript">
    function changeStatus_${apprIdx}() {
        let dcs = $('#decision_${apprIdx}');
        let fwr = $('#forwardTo${apprIdx}');
        let rmk = $('#remark_${apprIdx}');

        let decision = dcs.val();
        let forwardTo = fwr.val();
        let remark = rmk.val();

        if (decision == '') {
            alert('You need to choose Decision first!');
            return;
        }

        if (decision != 'APPROVE_AND_FINISH' && decision != 'REJECTED' && forwardTo == '') {
            alert('You need to choose next Approver first!');
            return;
        }

        if (forwardTo == '${approvalDecision.forwardTo.id}') {
            alert('Cannot forward document to same person.');
            if (fwr)
                fwr.empty();

            return;
        }

        let $itemapp = $('#itemapp');
        if ($itemapp)
            $itemapp.val('${apprIdx}');

        dcs.attr('name', 'approvalDecisionStatus');
        fwr.attr('name', 'forwardTo');
        rmk.attr('name', 'remark');

        approve${apprIdx}();
    }

    function checkStatus_${apprIdx}() {
        let decision = document.getElementById("decision_${apprIdx}").value;
        if (decision == "APPROVE_AND_FINISH" || decision == "REJECTED" || decision == "")
            on_${apprIdx}();
        else
            off_${apprIdx}();
    }

    function off_${apprIdx}() {
        document.getElementById('forwardTo${apprIdx}').disabled = false;
        $("#forwardTo${apprIdx}").attr("class", "combobox");
        $('#f_img_${apprIdx}').show();
    }

    function on_${apprIdx}() {
        document.getElementById('forwardTo${apprIdx}').disabled = true;
        $("#forwardTo${apprIdx}").attr("class", "combobox input-disabled");
        $('#f_img_${apprIdx}').hide();
    }

    // Override this function on original JSP
    function openapprover${apprIdx}(){
        alert("Popup not implmented yet, function code position is wrong(put function on bottom)");
    }

    //Can be redeclare for custom notification, validation, or else
    function approve${apprIdx}() {
        submitted${apprIdx}();
    }

    function submitted${apprIdx}() {
        $.ajax({
            url: "<c:url value='/page/${submited}'/>",
            data: $('#addForm').serialize(),
            type: 'POST',
            dataType: 'json',
            beforeSend: function () {
                $dialog.empty();
                $dialog.html('Approval Data......');
                $dialog.dialog('open');
            },
            success: function (json) {
                if (json) {
                    if (json.status == 'OK') {
                        $dialog.dialog('close');
                        window.location = "<c:url value='/page/${approvalDecision.approvable.uri}?id='/>" + json.id;
                    } else {
                        $dialog.empty();
                        $dialog.html(json.message);
                        $dialog.dialog({
                            buttons: {
                                Close: function () {
                                    window.location.reload();
                                }
                            }
                        });
                    }
                }
            },
            error: function (xhr) {
                console.log(xhr.responseText);
            },
        });
    }
</script>
<table width="100%" style="border:none">
    <c:if test="${empty approvalDecision}">
        <tr>
            <td align="center" style="color:red;font-weight:bold;">approvalDecision not found</td>
        </tr>
    </c:if>
    <c:if test="${not empty person and person.id == approvalDecision.forwardTo.id and approvalDecision.approvalDecisionStatus != 'APPROVE_AND_FINISH' and approvalDecision.approvalDecisionStatus != 'REJECTED'}">
        <tr>
            <td>
                <fieldset>
                    <legend><strong>Approval Decision </strong></legend>
                    <table width="100%" style="border:none">
                        <tr>
                            <td align="right">Decision</td>
                            <td>:</td>
                            <td>
                                <select id="decision_${apprIdx}" class="combobox" onchange="checkStatus_${apprIdx}()">
                                    <option value="">--Decision--</option>
                                    <c:forEach items="${approvalDecisionStatuses}" var="status">
                                        <option value="${status}">${status.normalizedName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">Forward To</td>
                            <td>:</td>
                            <td>
                                <select id="forwardTo${apprIdx}" class="combobox input-disabled" disabled>
                                </select>
                                &nbsp;
                                <img id="f_img_${apprIdx}" style="cursor: pointer; display: none;"
                                     src="assets/icons/list_extensions.gif" onclick="openapprover${apprIdx}();"
                                     title="<spring:message code="approver"/>"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">Remark</td>
                            <td>:</td>
                            <td valign="top"><textarea id="remark_${apprIdx}" cols="45" rows="3"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td><img style="cursor: pointer;" onclick="changeStatus_${apprIdx}();"
                                     src="<c:url value='/assets/images/btn_process.gif'/>"/></td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
    </c:if>
</table>
