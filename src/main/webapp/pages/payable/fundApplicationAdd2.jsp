<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-back" href="<c:url value='/page/fundapplicationpreadd1.htm'/>"><span><spring:message code="sirius.back"/></span></a>
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
		            <td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
		        </tr>
		        <tr>
		            <td align="right"><spring:message code="organization"/></td>
		            <td width="1%" align="center">:</td>
		            <td>
		                <form:select id="org" path="organization" cssClass="combobox-ext">
		                    <c:if test='${not empty organization}'>
		                        <form:option value='${organization.id}' label='${organization.fullName}'/>
		                    </c:if>
		                </form:select>
		            </td>
		        </tr>
		        <tr>
		            <td align="right"><spring:message code="sirius.date"/></td>
		            <td width="1%" align="center">:</td>
		            <td><input id="date" name="date" class="input-disabled" readonly="true" size="10" value="<fmt:formatDate value='${criteria.date}' pattern='dd-MM-yyyy'/>"/></td>
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
    <table width="100%"  cellpadding="5" cellspacing="0">
    <thead>
    <tr>
		<th width="1%" style="border-bottom:1px solid black;border-top:1px solid black;"><input class="checkall" type="checkbox" index=""/></th>
		<th width="50%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="supplier"/></th>
   	  	<th align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.unpaid"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items='${reports}' var='report' varStatus="stat">
    <tr>
		<td><input id="check${stat.index}" type="checkbox" class="check" onchange="checkSelected(this);" index="${stat.index}"/></td>
        <td align="left"><c:out value='${report.supplier.fullName}'/></a></td>
        <td align="right"><fmt:formatNumber value='${report.amount}' pattern=',##0.00'/></td>
        <td style="display: none;">
        	<input id="enabled${stat.index}" name="items[${stat.index}].enabled" value="false" class="enables" readonly="true"/>
        	<input id="reference${stat.index}" name="items[${stat.index}].reference" value="${report.supplier.id}" readonly="true"/>
        	<input id="amount${stat.index}" name="items[${stat.index}].amount" value="${report.amount}" readonly="true"/>
        </td>
    </tr>
    <c:set var='totalAmount' value='${totalAmount+report.amount}'/>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
      	<td colspan="2" align="left" style="border-top:1px solid black;border-bottom:1px solid black;"><strong><spring:message code="sirius.total"/></strong></td>
        <td align="right" style="border-top:1px solid black;border-bottom:1px solid black;">
            <strong>
            <fmt:formatNumber value='${totalAmount}' pattern=',##0.00'/>
            <c:remove var='totalAmount'/>
          </strong>
        </td>
    </tr>
    <tr>
      	<td colspan="2" align="left" style="border-bottom:1px solid black;"><strong><spring:message code="sirius.total"/>&nbsp;<spring:message code="fundapplication"/></strong></td>
        <td align="right" style="border-bottom:1px solid black;">
            <strong>
            <div id="totalFund"><fmt:formatNumber value='${0}' pattern=',##0.00'/></div>
          </strong>
        </td>
    </tr>
    </tfoot>
    </table>
	</sesform:form>

<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function(){
			validation();
		});
		
		$('.checkall').click(function () {
	        $('.check').prop("checked", this.checked);
	        
	        if(this.checked)
	        	$('.enables').val(true);
	        else
	        	$('.enables').val(false);
	        
	        calculateFund();
	    });
	});
	
	function checkSelected(element) {
		var idx = element.getAttribute('index');
		document.getElementById('enabled'+idx).value = element.checked;
		calculateFund();
	}
	
	function calculateFund() {
		var total = 0.00;
		$('.check').each(function(){
			if(this.checked) {
				var idx = this.getAttribute('index');
				total = total + document.getElementById('amount'+idx).value.toNumber();
			}
        });
		
		$('#totalFund').text(total.numberFormat('#,##0.00'));
	}
	
	function validation() {
		var totalCheck = 0;
		$('.check').each(function(){
			if(this.checked)
				totalCheck++;
        });
		
		if(totalCheck > 0)
			save();
		else {
			alert('<spring:message code="notif.select1"/> <spring:message code="fundapplication"/> <spring:message code="notif.select2"/> !');
	        return;
		}
	}

    function save() {
        $.ajax({
            url:"<c:url value='/page/fundapplicationadd.htm'/>",
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
    }

</script>