<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
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
							<a class="item-button-list" href="<c:url value='/page/productcategoryview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test="${access.add}">
								<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
							</c:if>
						</div>
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="category_add">
								<table style="border:none" width="100%">
									<tr>
										<td width="23%" align="right"><spring:message code="productcategory.id"/> :</td>
									  	<td width="30%"><input class="inputbox input-disabled" value="Auto Generated" disabled/></td>
								  	</tr>
									<tr>
										<td align="right"><spring:message code="productcategory"/> :</td>
										<td><form:input id='name' path="name" maxlength="50"/></td>
										<td><form:errors path="name"/></td>							
									</tr>
									<tr>
										<td align="right"><spring:message code="sirius.type"/> :</td>
										<td>
											<form:select path="type" cssClass="combobox">
												<form:option value="STOCK">STOCK</form:option>
												<form:option value="NONSTOCK">NON STOCK</form:option>
											</form:select>
										</td>		
                                        <td><form:errors path="type"/></td>					
									</tr>
									<tr>
										<td align="right"><spring:message code="sirius.note"/> :</td>
										<td><form:textarea path="note" cols='55' rows='6'/></td>
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
	
 	<%@ include file="/common/sirius-footer.jsp"%>

</div>

</body>
</html>
<script type="text/javascript">
	$(function(){
		$('#code').focus();
	
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});	
		$('.item-button-save').click(function(){
			if(!$('#name').val())
			{
				alert('Name cannot be empty!');
				return;
			}

			$.get("<c:url value='/page/productcategorycheck.htm'/>",
				{code : $('#code').val(),name : $('#name').val()},
				function(json){
					if(json)
					{
						if(json.code)
						{
							alert('Code has been used !!!');
							return;
						}

						if(json.name)
						{
							alert('Name has been used !!!');
							return;
						}

						save();
					}
				});
		});
	});

	function save()
	{
		$.ajax({
			url:"<c:url value='/page/productcategoryadd.htm'/>",
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
						window.location="<c:url value='/page/productcategoryview.htm'/>";
					}
					else
					{
						$dialog.empty();
						$dialog.html('Proccess fail,reason :'+json.message);
					}
				}
			},
			error : function(xhr) {
				console.log(xhr.responseText);
			},
		});	
	}
</script>