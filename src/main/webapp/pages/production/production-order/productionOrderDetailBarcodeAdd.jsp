<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<c:if test="${not empty productionOrderDetail}">
		<a class="item-button-back" href="<c:url value='/page/productionorderdetailpreedit.htm?id=${productionOrderDetail.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	</c:if>
	<c:if test="${empty productionOrderDetail}">
		<a class="item-button-list" href="<c:url value='/page/productionorderdetailbarcodeview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	</c:if>
	<a class="item-button-next" ><span><spring:message code="sirius.next"/></span></a>
</div>
<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="barcode_add" enctype="multipart/form-data">
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
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorderdetail"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="productionOrderDetail" name="productionOrderDetail" class="combobox-ext">
									<c:if test="${not empty productionOrderDetail}">
										<option value="${productionOrderDetail.id}" label="${productionOrderDetail.code}"/>
									</c:if>
								</select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupproductionorderdetail.htm?target=productionOrderDetail'/>');" title="<spring:message code="productionorderdetail"/>" />
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.output"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="product" name="product" class="combobox-ext">
								</select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupproductview.htm?target=product'/>');" title="<spring:message code="product"/>" />
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.conemark"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="coneMark" path="coneMark"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="barcoding.qty"/></td>
							<td width="1%" align="center">:</td>
							<td><form:input id="quantity" path="quantity" cssClass="input-number"/></td>
						</tr>
						<tr>
							<td align="right">PIC</td>
							<td width="1%" align="center">:</td>
							<td><form:input id="pic" path="pic"/></td>
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
		$('.item-button-next').click(function(){
			if (validate())
			{
				document.addForm.action = '<c:url value="/page/productionorderdetailbarcodepreadd2.htm"/>';
	            document.addForm.submit();
			}
        });
	});
	
	function validate()
	{
		if(!$("#productionOrderDetail").val())
		{
			alert('<spring:message code="productionorderdetail"/> <spring:message code="notif.empty"/> !');
			return false;
		}
		
		if(!$("#product").val())
		{
			alert('<spring:message code="barcoding.output"/> <spring:message code="notif.empty"/> !');
			return false;
		}
		
		if(!$("#coneMark").val())
		{
			alert('<spring:message code="barcoding.conemark"/> <spring:message code="notif.empty"/> !');
			return false;
		}
		
		if($("#quantity").val().toNumber() < 1)
		{
			alert('<spring:message code="barcoding.qty"/> <spring:message code="notif.zero"/> !');
			return false;
		}
		
		if(!$("#pic").val())
		{
			alert('PIC <spring:message code="notif.empty"/> !');
			return false;
		}
		
		return true;
	}
</script>