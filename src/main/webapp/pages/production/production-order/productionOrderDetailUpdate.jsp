<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/productionorderpreedit.htm?id=${detail_edit.productionOrder.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<c:if test="${access.edit}">
		<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
	</c:if>
</div>
<div class="main-box">
	<sesform:form id="editForm" name="editForm" method="post" modelAttribute="detail_edit" enctype="multipart/form-data">
	    <table width="100%" border="0">
			<tr>
				<td width="60%">
					<table style="border:none" width="100%">
						<tr>
							<td width="34%" align="right"><spring:message code="sirius.id"/></td>
							<td width="1%" align="center">:</td>
							<td width="64%"><input class="inputbox input-disabled" value="${detail_edit.code}" disabled/></td>
							<td width="1%"><form:errors path="code"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="organization"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<select id="org" class="combobox-ext input-disabled" disabled="true">
									<option value='${detail_edit.productionOrder.organization.id}' label='${detail_edit.productionOrder.organization.fullName}'/>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.date"/></td>
							<td width="1%" align="center">:</td>
							<td><input id="date" class="input-disabled" size="8" value="<fmt:formatDate value='${detail_edit.date}' pattern='dd-MM-yyyy'/>" disabled/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="productionorder.lotnumber"/></td>
							<td width="1%" align="center">:</td>
							<td>
								<form:input id="lotNumber" path="productionOrder.lotNumber" cssClass="input-disabled" disabled="true"/>
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
												<td width="42%" align="right"><h1 style="color: green;">${detail_edit.status.normalizedName}</h1></td>
											</tr>
										</table>
								</fieldset>
                             </td>
                         </tr>
                         <tr>
                        	<td>
                            	<fieldset>
										<legend><strong><spring:message code="sirius.reference"/></strong></legend>
										<table width="100%" border="0" cellpadding="0">
											<tr></tr>
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
								<th width="8%" nowrap="nowrap"><spring:message code="costgroup.workallocation"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="uom"/></th>
							</tr>
						</thead>
						<tbody id="costCenterGroupItem">
							<c:forEach items="${detail_edit.productionDetailCostCenterGroups}" var="costCenter">
								<tr>
									<td>&nbsp;</td>
									<td><c:out value="${costCenter.costCenterGroup.code}"/></td>
									<td><c:out value="${costCenter.costCenterGroup.name}"/></td>
									<td><fmt:formatNumber value="${costCenter.workAllocation}" pattern=",##0.00"/></td>
									<td><c:out value="${costCenter.unitOfMeasure.measureId}"/></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="inputMaterialLine" dojoType="ContentPane" label="<spring:message code="productionorder.inputmaterial"/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<table class="table-list" id="wipTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
						<thead>
							<tr>
								<th width="1%" nowrap="nowrap">&nbsp;</th>
								<th width="15%" nowrap="nowrap"><spring:message code="sirius.type"/></th>
								<th width="15%" nowrap="nowrap"><spring:message code="product.id"/></th>
								<th width="20%" nowrap="nowrap"><spring:message code="product.name"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
								<th width="10%" nowrap="nowrap"><spring:message code="productionorderdetail.usage"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="uom"/></th>
							</tr>
						</thead>
						<tbody id="wipItem">
							<c:forEach items="${detail_edit.items}" var="item">
								<c:if test="${item.materialType eq 'INPUT'}">
									<tr>
										<td>&nbsp;</td>
										<td><c:out value="${item.materialSource}"/></td>
										<td><c:out value="${item.product.code}"/></td>
										<td><c:out value="${item.product.name}"/></td>
										<td><fmt:formatNumber value="${item.quantity}" pattern=",##0.00"/></td>
										<td><fmt:formatNumber value="${item.usageQuantity}" pattern=",##0.00"/></td>
										<td><c:out value="${item.product.unitOfMeasure.measureId}"/></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
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
								<th width="10%" nowrap="nowrap"><spring:message code="product.quantity"/></th>
								<th width="5%" nowrap="nowrap"><spring:message code="uom"/></th>
							</tr>
						</thead>
						<tbody id="outputItem">
							<c:forEach items="${detail_edit.items}" var="item">
								<c:if test="${item.materialType eq 'OUTPUT'}">
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
			<div id="issueLine" dojoType="ContentPane" label="<spring:message code="workissue"/>" class="tab-pages" refreshOnShow="true" selected="true">
				<div class="toolbar-clean">
					<a class="item-button-new" href="<c:url value='/page/workissuepreadd.htm?id=${detail_edit.id}'/>"><span><spring:message code="workissue.new"/></span></a>
					<div class="item-navigator">&nbsp;</div>
				</div>
				<table class="table-list" id="detailTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
					<thead>
						<tr>
							<th width="3%" nowrap="nowrap">&nbsp;</th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.code"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="workissue.reporteddate"/></th>
							<th width="20%" nowrap="nowrap"><spring:message code="workissue.issue"/></th>
							<th width="20%" nowrap="nowrap"><spring:message code="workissue.solution"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="workissue.resolveddate"/></th>
						</tr>
					</thead>
					<tbody id="wipItem">
						<c:forEach items="${detail_edit.workIssues}" var="issue">
							<tr>
								<td class="tools">
									<c:if test="${access.edit}">
										<a class="item-button-edit" href="<c:url value='/page/workissuepreedit.htm?id=${issue.id}'/>" title="<spring:message code="sirius.edit"/>"><span><spring:message code="sirius.edit"/></span></a>
									</c:if>
									<c:if test="${access.delete}">
										<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/workissuedelete.htm?id=${issue.id}'/>');" title="<spring:message code="sirius.delete"/>"><span><spring:message code="sirius.delete"/></span></a>
									</c:if>
								</td>
								<td nowrap="nowrap"><c:out value="${issue.code}"/></td>
								<td><fmt:formatDate value="${issue.reportedDate}" pattern="dd-MM-yyyy"/> <c:out value="${issue.reportedTime}"/></td>
								<td><c:out value="${issue.issue}"/></td>
								<td><c:out value="${issue.solution}"/></td>
								<td><fmt:formatDate value="${issue.resolvedDate}" pattern="dd-MM-yyyy"/> <c:out value="${issue.resolvedTime}"/></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
					</tfoot>
				</table>
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
	});
	
	function save()
	{
		$.ajax({
		    url:"<c:url value='/page/productionorderdetailedit.htm'/>",
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
	}
</script>