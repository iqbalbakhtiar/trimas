<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-list" href="<c:url value='/page/barcodegroupview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
    <a class="item-button-next" ><span><spring:message code="sirius.next"/></span></a>
</div>

<div class="main-box">
    <form id="addForm" name="addForm" method="post" modelAttribute="barcode_form">
        <table style="border:none" width="100%">
        	<c:set var="refClass" value=""/>
           	<c:if test="${not empty barcode_form.barcodeGroupType}"><c:set var="refClass" value="input-disabled"/></c:if>
            <tr>
                <td align="right"><spring:message code="goodsreceipt.organization"/> :</td>
                <td>
                    <form:select id="org" path="barcode_form.organization" cssClass="combobox-ext ${refClass}">
                        <c:if test='${not empty barcode_form.organization}'>
                            <form:option value='${barcode_form.organization.id}' label='${barcode_form.organization.fullName}' />
                        </c:if>
                    </form:select>
                	<c:if test="${empty barcode_form.barcodeGroupType}">
                    	<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure"></a>
                    </c:if>
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
                    <form:select id="facility" path="barcode_form.facility" cssClass="combobox-ext ${refClass}">
                        <c:if test='${not empty barcode_form.facility}'>
                            <form:option value='${barcode_form.facility.id}' label='${barcode_form.facility.name}' />
                        </c:if>
                    </form:select>
                	<c:if test="${empty barcode_form.barcodeGroupType}">
                    	<a class="item-popup" onclick="openfacility();"  title="Facility"></a>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td align="right"><spring:message code="sirius.type"/> :</td>
                <td>
                	<c:if test="${empty barcode_form.barcodeGroupType}">
	                    <select id="barcodeGroupType" name="barcodeGroupType" class="combobox">
                            <option value="STOCK_ADJUSTMENT" label="Stock Adjustment"></option>
	                    </select>
                    </c:if>
                    <c:if test="${not empty barcode_form.barcodeGroupType}">
	                    <select id="barcodeGroupType" name="barcodeGroupType" class="combobox input-disabled">
	                    	<option value="${barcode_form.barcodeGroupType}">${barcode_form.barcodeGroupType.normalizedName}</option>
	                    </select>
                    </c:if>
                </td>
            </tr>
            <c:if test="${not empty barcode_form.barcodeGroupType}">
            <tr>
                <td align="right"><spring:message code="sirius.reference"/> :</td>
                <td>
                	<c:if test="${not empty barcode_form.purchaseOrder}"><a href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${barcode_form.purchaseOrder.id}'/>"><c:out value="${barcode_form.purchaseOrder.code}"></c:out></a></c:if>
                </td>
            </tr>
            </c:if>
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