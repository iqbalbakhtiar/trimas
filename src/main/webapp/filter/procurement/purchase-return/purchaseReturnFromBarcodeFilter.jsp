<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 300px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td width="300" align="right"><spring:message code='sirius.code'/>&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.facility"/>&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28">
				<select id="facility" name="facilityId" class="combobox">
					<c:if test='${not empty filterCriteria.facilityId}'>
						<option value='${filterCriteria.facilityId}' label='${facility.name}' />
					</c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=facility&active=true'/>');"  title="Facility" />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="supplier"/>&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="supplierName" name="supplierName" value="${filterCriteria.supplierName}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="purchaseorder"/>&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="purchaseOrderCode" name="purchaseOrderCode" value="${filterCriteria.purchaseOrderCode}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="goodsissue"/>&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code='sirius.datefrom'/> &nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code='sirius.dateto'/> &nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<input type="button" value='<spring:message code="sirius.search"/>' style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
				<input type="button" value='<spring:message code="sirius.reset"/>'  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
			</td>
		</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/purchasereturnfrombarcodeview.htm?mode=search'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/purchasereturnfrombarcodeview.htm'/>";
	}
			
	function step(action)
	{
		var page="1";
				
		if(action == "next")
			page="${filterCriteria.nextPage}";
		else if(action == "prev")
			page="${filterCriteria.prevPage}";
		else if(action == "last")
			page="${filterCriteria.totalPage}";					
			
		document.filterForm.action = "<c:url value='/page/purchasereturnfrombarcodeview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>