<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
</head>

<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

		<%@ include file="/common/sirius-menu.jsp"%>
	
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb}</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>
	
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle}</h1>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/exchangemanagementview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save btn_save" ><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  			

						<div class="main-box">
 							<sesform:form cssClass="edit-form" id="addForm" name="addForm" modelAttribute="exchange_add" method="post">
 							<table width="100%" style="border:none">
 							<tr>
 								<td align="right"><spring:message code="currencymanagement.from"/> <spring:message code="currencymanagement"/> : </td>
								<td>
									<form:select path="from" cssClass="combobox rate">
										<form:options items="${currencys}" itemValue="id" itemLabel="symbol"/>
									</form:select>
								</td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="currencymanagement.to"/> <spring:message code="currencymanagement"/> : </td>
								<td>
									<form:select path="to" cssClass="combobox rate">
										<form:options items="${currencys}" itemValue="id" itemLabel="symbol"/>
									</form:select>
								</td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="currencymanagement.type"/> : </td>
								<td>
									<form:select path="type" cssClass="combobox rate">
										<form:option value="SPOT" label="SPOT"/>
										<form:option value="MIDDLE" label="MIDDLE"/>
										<form:option value="TAX" label="TAX"/>
									</form:select>
								</td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="currencymanagement.rate"/> : </td>
								<td><form:input path="rate" size="25" value="0.000" class="input-decimal"/></td>
 							</tr>
 							<tr>
 								<td align="right"><spring:message code="currencymanagement.validfrom"/> : </td>
								<td><input id="validFrom" name="validFrom" value='<fmt:formatDate value="${now}" pattern="dd-MM-yyyy"/>' class="datepicker"/></td>
 							</tr>
 							</table>
							</sesform:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

  	<%@ include file="/common/sirius-footer.jsp"%>

</div>

</body>
</html>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="currencymanagement"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

		var date = new Date();

		$('.btn_save').click(function(e)
		{
			if($('#rate').val().toNumber() <= 0)
			{
				alert('<spring:message code="currencymanagement.rate"/> <spring:message code="notif.greater.zero"/> !!!');
				return;
			}

			$.get("<c:url value='/page/exchangemanagementloadrate.htm'/>", 
				{from:$('#from').val(), to:$('#to').val(), type:$('#type').val(), date:$('#validFrom').val(), operator:'='}, 
				function(json){

					if(json.status == 'OK')
					{
						if(json.exchange != null)
						{
							alert('<spring:message code="currencymanagement"/> <spring:message code="notif.exist"/> !!!');
							return;
						}
						else
							save();

					}
					else
						alert(json.message);

				});
			
		});

		/*$('.rate').change(function(){
			$.get("<c:url value='/page/exchangemanagementloadrate.htm'/>",
				{from:$('#from').val(), to:$('#to').val(), type:$('#type').val()},
				function(json){

					if(!jQuery.isEmptyObject(json.exchange))
						date = new Date(json.exchange.validFrom);
					else
						date = new Date();

					$('#validFrom').datepicker('setDate',date);
				})
		});

		$('.rate').first().change();

		$("#validFrom").datepicker().change(function(){
			if(date > $('#validFrom').datepicker('getDate'))
				$('#validFrom').datepicker('setDate',date);
		});*/
	});

	function save()
	{
		$.ajax({
			url:"<c:url value='/page/exchangemanagementadd.htm'/>",
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
					if(json.status == 'OK')
					{
						$dialog.dialog('close');
						window.location="<c:url value='/page/exchangemanagementview.htm'/>";
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