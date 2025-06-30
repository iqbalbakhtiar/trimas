<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/productionorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<c:if test="${access.edit}">
		<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
	</c:if>
	<c:if test="${order_edit.status eq 'OPEN'}">
		<a class="item-button-doc"><span>Finish PO</span></a>
	</c:if>
</div>
<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="order_edit" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="${order_edit.code}" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="organization"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:select id="org" path="organization" cssClass="combobox-ext">
									<form:option value='${order_edit.organization.id}' label='${order_edit.organization.fullName}'/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" class="input-disabled" size="8" value="<fmt:formatDate value='${order_edit.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.threadtype"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="product" name="productionOrder.product" class="combobox-ext">
									<option value="${order_edit.product.id}" label="${order_edit.product.name}"/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.lotnumber"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:input id="lotNumber" path="lotNumber"/>
								<form:input type="hidden" id="status" path="status"/>
							</td>
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
					<table width="100%">
                        <tr>
                        	<td>
                            	<fieldset>
										<legend><strong><spring:message code="sirius.status"/></strong></legend>
										<table width="100%" border="0" cellpadding="0">
                                                <tr>
													<td width="57%" align="right"></td>
													<td width="42%" align="right"><h1 style="color: green;">${order_edit.status}</h1></td>
												</tr>
										</table>
										
								</fieldset>
                             </td>
                         </tr>
					</table>
				</td>
	    	</tr>
	    </table>
    	<br/>
    	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
			<div id="costCenterGroupLine" dojoType="ContentPane" label="<spring:message code='costgroup'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<table class="table-list" id="costCenterGroupTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
								<th width="15%" nowrap="nowrap"><spring:message code="costgroup.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="costgroup.name"/></th>
							</tr>
						</thead>
						<tbody id="costCenterGroupItem">
							<c:forEach items="${order_edit.productionCostCenterGroups}" var="costCenter">
								<tr>
									<td>&nbsp;</td>
									<td><c:out value="${costCenter.costCenterGroup.code}"/></td>
									<td><c:out value="${costCenter.costCenterGroup.name}"/></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="outputMaterialLine" dojoType="ContentPane" label="<spring:message code='productionorder.outputmaterial'/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<table class="table-list" id="outputMaterialTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
								<th width="15%" nowrap="nowrap"><spring:message code="product.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="product.name"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="productionorder.cogsweight"/></th>
							</tr>
						</thead>
						<tbody id="outputItem">
							<c:forEach items="${order_edit.items}" var="item">
								<c:if test="${item.materialType eq 'OUTPUT'}">
									<tr>
										<td>&nbsp;</td>
										<td><c:out value="${item.product.code}"/></td>
										<td><c:out value="${item.product.name}"/></td>
										<td><fmt:formatNumber value="${item.cogsWeight}" pattern=",##0.00"/></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="wipLine" dojoType="ContentPane" label="WIP" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<table class="table-list" id="wipTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
								<th width="15%" nowrap="nowrap"><spring:message code="product.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="product.name"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="uom"/></th>
							</tr>
						</thead>
						<tbody id="wipItem">
							<c:forEach items="${order_edit.items}" var="item">
								<c:if test="${item.materialType eq 'WIP'}">
									<tr>
										<td>&nbsp;</td>
										<td><c:out value="${item.product.code}"/></td>
										<td><c:out value="${item.product.name}"/></td>
										<td><fmt:formatNumber value="${item.quantity}" pattern=",##0.00"/></td>
										<td><c:out value="${item.product.unitOfMeasure.measureId}"/></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.item-button-save').click(function(){
			save();
		});
		
		$(".item-button-doc").click(() => {
			confirmDialog("<spring:message code="notif.proceed"/> (<spring:message code="sirius.close"/> <spring:message code="productionorder"/>) ?", 
			function() {
		        $('#status').val("FINISHED");
		        save();
			}, 
			function() {
		        return false;
			});
		});
	});
	
	function save()
	{
		$.ajax({
		    url:"<c:url value='/page/productionorderedit.htm'/>",
		    data:$('#editForm').serialize(),
		    type : 'POST',
		    dataType : 'json',
		    beforeSend:function()
		    {
		      $dialog.empty();
		      $dialog.html('<spring:message code="notif.updating"/>');
		      $dialog.dialog('open');
		    },
		    success : function(json) {
		    	if(json)
		    	{
		        	if(json.status === 'OK')
		        	{
		          		$dialog.dialog('close');
		          		window.location="<c:url value='/page/productionorderpreedit.htm?id='/>"+json.id;
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