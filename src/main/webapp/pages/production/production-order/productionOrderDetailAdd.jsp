<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/productionorderpreedit.htm?id=${detail_add.productionOrder.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="detail_add" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="productionOrder" path="productionOrder.code" cssClass="inputbox input-disabled" disabled="true"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="organization"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="org" path="productionOrder.organization" cssClass="combobox-ext input-disabled" disabled="true">
									<c:if test='${not empty detail_add.productionOrder.organization}'>
										<form:option value='${detail_add.productionOrder.organization.id}' label='${detail_add.productionOrder.organization.fullName}'/>
									</c:if>
								</form:select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.threadtype"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="product" class="combobox-ext input-disabled" disabled="true">
									<option value='${detail_add.productionOrder.product.id}' label='${detail_add.productionOrder.product.name}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.lotnumber"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="lotNumber" path="productionOrder.lotNumber" cssClass="input-disabled" disabled="true"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.description"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="description" path="description"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.note"/></td>
							<td width="1%" align="center">:</td>
							<td><form:textarea path="note" rows="6" cols="45"/></td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
				<td width="40%" valign="top">
				</td>
	    	</tr>
	    </table>
    	<br/>
	</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function(){
			$.ajax({
			    url:"<c:url value='/page/productionorderdetailadd.htm'/>",
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
			          		window.location="<c:url value='/page/productionorderdetailpreedit.htm?id='/>"+json.id;
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