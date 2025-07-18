<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/fundapplicationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="fundApplication_form">
	<table width="100%" border="0">
    <tr>
        <td width="40%" style="text-align: left;">
		    <table style="border:none" width="100%">
		        <tr>
		            <td width="34%" align="right"><spring:message code="sirius.code"/></td>
		            <td width="1%" align="center">:</td>
		            <td width="64%"><input class="inputbox input-disabled" value="${fundApplication_edit.code}" disabled/></td>
		        </tr>
		        <tr>
		            <td align="right"><spring:message code="organization"/></td>
		            <td width="1%" align="center">:</td>
		            <td>
		                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
	                        <form:option value='${fundApplication_edit.organization.id}' label='${fundApplication_edit.organization.fullName}'/>
		                </form:select>
		            </td>
		        </tr>
		        <tr>
		            <td align="right"><spring:message code="sirius.date"/></td>
		            <td width="1%" align="center">:</td>
		            <td><input id="date" name="date" class="input-disabled" disabled size="8" value="<fmt:formatDate value='${fundApplication_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
		        </tr>
		        <tr>
		            <td align="right"><spring:message code="sirius.note"/></td>
		            <td width="1%" align="center">:</td>
		            <td><form:textarea path="note" rows="6" cols="45"/></td>
		        </tr>
		    </table>
	    </td>
        <td width="60%" style="text-align: left;"></td>
    </tr>
    </table>
    <div>&nbsp;</div>
	<c:set var='totalAmount' value='${0}'/>
    <table class="table-list" width="100%" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
		<th width="1%" style="border-bottom:1px solid black;border-top:1px solid black;">&nbsp;</th>
		<th width="50%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="supplier"/></th>
   	  	<th style="border-bottom:1px solid black;border-top:1px solid black;text-align: right;"><spring:message code="sirius.unpaid"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items='${fundApplication_edit.items}' var='item' varStatus="stat">
    <tr>
    	<td class="tools">
			<a class="item-button-print" href="<c:url value='/page/fundapplicationprint.htm?id=${item.id}&invType=2'/>" title="<spring:message code='sirius.print'/>&nbsp;<spring:message code='billing'/>"><span><spring:message code="sirius.print"/>&nbsp;<spring:message code="billing"/></span></a>
		</td>
        <td align="left"><c:out value='${item.supplier.fullName}'/></a></td>
        <td style="text-align: right;"><fmt:formatNumber value='${item.amount}' pattern=',##0.00'/></td>
    </tr>
    <c:set var='totalAmount' value='${totalAmount+item.amount}'/>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
		<td>&nbsp;</td>
      	<td align="left" style="border-top:1px solid black;border-bottom:1px solid black;"><strong><spring:message code="sirius.total"/></strong></td>
        <td style="border-top:1px solid black;border-bottom:1px solid black;text-align: right;">
            <strong>
            <fmt:formatNumber value='${totalAmount}' pattern=',##0.00'/>
            <c:remove var='totalAmount'/>
          </strong>
        </td>
    </tr>
    <tr><td colspan="3">&nbsp;</td></tr>
    </tfoot>
    </table>
	</sesform:form>
	</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${fundApplication_form.createdBy.fullName}'/> (<fmt:formatDate value='${fundApplication_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${fundApplication_form.updatedBy.fullName}'/> (<fmt:formatDate value='${fundApplication_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function(){
			$.ajax({
	            url:"<c:url value='/page/fundapplicationedit.htm'/>",
	            data:$('#addForm').serialize(),
	            type : 'POST',
	            dataType : 'json',
	            beforeSend:function()
	            {
	                $dialog.empty();
	                $dialog.html('<spring:message code="notif.saving"/>');
	                $dialog.dialog('open');
	            },
	            success : function(json) {
	                if(json)
	                {
	                    if(json.status === 'OK')
	                    {
	                        $dialog.dialog('close');
	                        window.location="<c:url value='/page/fundapplicationpreedit.htm?id='/>"+json.id;
	                    }
	                    else
	                    {
	                        $dialog.empty();
	                        $dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
	                    }
	                }
	            }
	        });
		});
	});
</script>