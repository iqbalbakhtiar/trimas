<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/barcodegroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-next" ><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <form id="addForm" name="addForm" method="post" modelAttribute="barcode_add">
        <table style="border:none" width="100%">
            <tr>
                <td align="right"><spring:message code="goodsreceipt.organization"/> :</td>
                <td>
                    <form:select id="org" path="barcode_add.organization" cssClass="combobox-ext">
                        <c:if test='${not empty barcode_add.organization}'>
                            <form:option value='${barcode_add.organization.id}' label='${barcode_add.organization.fullName}' />
                        </c:if>
                    </form:select>
                    <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure"></a>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
                <td>
                    <input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker" size="10"/>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.facility"/> :</td>
                <td>
                    <form:select id="facility" path="barcode_add.facility" cssClass="combobox-ext">
                        <c:if test='${not empty barcode_add.facility}'>
                            <form:option value='${barcode_add.facility.id}' label='${barcode_add.facility.name}' />
                        </c:if>
                    </form:select>
                    <a class="item-popup" onclick="openfacility();"  title="Facility"></a>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.type"/> :</td>
                <td>
                    <select id="barcodeGroupType" name="barcodeGroupType" class="combobox">
                        <c:forEach var="type" items="${types}">
                            <option value="${type}" label="${type.normalizedName}"></option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>

<script type="text/javascript">
    $(function(){
        var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
        $('.item-button-next').click(function(){
            if(!$('#org').val())
            {
                alert('<spring:message code="goodsreceipt.organization"/> <spring:message code="notif.empty"/> !');
                return;
            }

            if(!$('#facility').val())
            {
                alert('<spring:message code="sirius.facility"/> <spring:message code="notif.empty"/> !');
                return;
            }

            document.addForm.action = "<c:url value='/page/barcodegrouppreadd2.htm'/>";
            document.addForm.submit();
        });
    });

    function openfacility()
    {
        var org = document.getElementById('org');
        if(org.value == '')
        {
            alert('<spring:message code="goodsreceipt.organization"/> <spring:message code="notif.empty"/> !');
            return;
        }

        openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&active=true&organization='/>"+org.value);
    }
</script>