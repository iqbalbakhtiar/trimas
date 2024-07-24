<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title }</title>
    <%@ include file="/common/sirius-header.jsp"%>
    	<style type="text/css" media="screen">
    	@import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/css/jquery-ui-1.8.2.custom.css'/>");
    </style>
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
			<td width="60%">${breadcrumb }</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">						
						<h1 class="page-title">${pageTitle }</h1>						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/customermasterview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="customer_add" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="34%" align="right"><spring:message code="customer.type"/></td>
                                    <td width="1%" align="center">:</td>
							  		<td width="64%">
			  					  		<form:select path="type" class="combobox">
											<form:option value="group" label='ORGANIZATION/GROUPS'/>
											<form:option value="personal" label='PERSON/PERSONAL'/>
										</form:select>
									</td>
							  	  	<td width="1%">&nbsp;</td>
 								</tr>
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="customer.company"/></td>
                                    <td>:</td>
                                    <td>
                                        <form:select id="org" path="organization" cssClass="combobox-ext">
                                        </form:select>
                                        &nbsp;
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                                    </td>
                                </tr>
 								<tr>
 									<td align="right"><spring:message code="customer.code"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input value="Auto Number" disabled/></td>
									<td><form:errors path="code"/></td>
 								</tr>
 								<tr>
 									<td align="right"><spring:message code="customer.name.first"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input cssClass='check_field' id='firstName' path="firstName" size='30'/></td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.name.middle"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input cssClass='check_field' id='middleName' path="middleName" size='30'/></td>
									<td><form:errors path="middleName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.name.last"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input cssClass='check_field' id='lastName' path="lastName" size='30'/></td>
									<td><form:errors path="lastName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.taxcode"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="taxCode"  /></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.salutation"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="salutation"  /></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
	 							<tr>
 									<td align="right"><spring:message code="customer.birth"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input id="birthDate" name="birthDate" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/></td>
									<td>&nbsp;</td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="customer.picture"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input type="file" name="file"/></td>
									<td>&nbsp;</td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="sirius.note"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:textarea path="note" rows="6" cols="45"/></td>
									<td>&nbsp;</td>
 								</tr> 
 								</table>
 							</sesform:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
  	<div style="clear:both;height:0px">&nbsp;</div>
		<%@ include file="/common/sirius-footer.jsp"%>
	<div style="clear:both;height:20px">&nbsp;</div>
</div>
</body>
</html>
<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$first = $('#firstName');
		$middle = $('#middleName');
		$last = $('#lastName');
	
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Customer',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			if(!$first.val() && !$middle.val() && !$last.val())
			{
				alert('First Name/Middle Name/Last Name cannot be empty!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/customeradd.htm'/>",
				data:$('#addForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/customermasterpreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
		
		/**
		$('.check_field').change(function(){
			if($first.val())
			{
				$.ajax({
					url: "<c:url value='/page/partyisexistjsonview.htm'/>",
					dataType: "json",
					data: {firstName: $first.val(),middleName:$middle.val(),lastName:$last.val()},
					success: function( data ) {
						if(data.exist)
						{
							alert('Customer with name '+$first.val()+' '+$middle.val()+' '+$last.val()+' already exist!');
							
							$first.val('');
							$middle.val('');
							$last.val('');
							
							return;
						}
					}
				});
			}
		});
		*/
	});
</script>