<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/accounting/journalEntryBatchingFilter.jsp"%>
						<div class="item-navigator">
							<div class="area" dojoType="Container" id="quick_link_container">
								<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
									<tr>
										<td width="30%" height="30" align="left" valign="middle">
											<div class="toolbar-clean">
												<c:if test='${access.add}'>
													<a class="item-button-ok" href="javascript:post();"><span><spring:message code="sirius.post"/></span></a>
												</c:if>
												<div dojoType="Toggler" targetId="filter">
													<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
												</div>
												<a class="item-button-print" href="javascript:cetak();"><span><spring:message code="sirius.print"/></span></a>
											</div>
										</td>
										<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
									</tr>
								</table>
							</div>
					  	</div>
						<form id="addForm" name="addForm" method="post" >
							<table class="table-list" cellspacing="0" cellpadding="0" width="100%" id="lineItemTable">
							<tr>
                              	<th width="1%" align="center"><input type="checkbox" name="checkMaster" id="checkMaster" onclick="clickAll();"/></th>
                              	<th align="center" width="8%"><spring:message code="journalentry.date"/></th>
                                <th align="center" width="8%"><spring:message code="accountingperiod"/></th>
                                <th width="12%" align="center"><spring:message code="journalentry.organization"/> / <spring:message code="journaldetail.account"/></th>
                              	<th width="15%" align="center"><spring:message code="journalentry.name"/></th>
                              	<th align="center" width="8%"><spring:message code="journalentry.debet"/></th>
                                <th align="center" width="8%"><spring:message code="journalentry.credit"/></th>
                                <th align="center" width="45%"><spring:message code="journalentry.currency"/></th>
							</tr>
							<c:forEach items="${journalEntrys}" var="journalBatching">
							<tr>
							  	<td nowrap="nowrap" align="center"><input type="checkbox" id="ids" name="ids" value="${journalBatching.id}"/></td>
								<td nowrap="nowrap" valign="middle"><fmt:formatDate value='${journalBatching.entryDate}' pattern='dd-MM-yyyy'/>&nbsp;&nbsp;</td>
						  	  	<td nowrap="nowrap" valign="middle"><c:out value='${journalBatching.accountingPeriod.code}'/>&nbsp;&nbsp;</td>
						  	 	<td nowrap="nowrap" align="left"><c:out value='${journalBatching.organization.firstName}'/> <c:out value='${journalBatching.organization.middleName}'/> <c:out value='${journalBatching.organization.lastName}'/>&nbsp;&nbsp;</td>
								<td nowrap="nowrap" align="left" colspan="4"><c:out value='${journalBatching.code} - ${journalBatching.name}'/>&nbsp;&nbsp;</td>
							</tr>
							<c:forEach items='${journalBatching.details}' var='item'>
							<tr>
								<div class="area" dojoType="Container" id="quick_link_container">
									<td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								</div>
								<td colspan="2" nowrap="nowrap">
									&emsp;<c:out value='${item.account.code}'/>-<c:out value='${item.account.name}'/>
									<c:if test="${not empty item.note}">&emsp;<c:out value='${item.note}'/></c:if>
								</td>
						  		<td nowrap="nowrap">
								<c:if test="${item.postingType == 'DEBET'}">
                                   	<fmt:formatNumber value='${item.amount}' pattern=',#00.00'/>
                               	</c:if>
                           		</td>
                                <td nowrap="nowrap">
   	 							<c:if test="${item.postingType == 'CREDIT'}">
                                	<fmt:formatNumber value='${item.amount}' pattern=',#00.00'/>
                           	  	</c:if>
                           		</td>
						  		<td nowrap="nowrap"><c:out value='${journalBatching.currency.symbol}'/></td>
							</tr>
							</c:forEach>
							</c:forEach>
							<tr class="end-table"><td colspan="9">&nbsp;</td></tr>
							</table>

                            <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                                <tr>
                                    <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                                </tr>
                            </table>
						</form>
					<%@ include file="/common/sirius-general-bottom.jsp"%>
<script language="javascript" type="text/javascript">
	function post()
	{
		var count=$("#ids:checked").size();
		var msg="<spring:message code='notif.selected1'/> "+count+" <spring:message code='journalentry'/>"
		if(count>0){
			confirmDialog(msg+",<spring:message code='notif.continue'/>?",function(){
				document.addForm.action = "<c:url value='/page/journalBatchingUpdate.htm'/>";
				document.addForm.submit();
			});
		}else{
			alert(msg);
		}
	}

	function clickAll()
	{
		var checked = false;
		if(document.getElementById("checkMaster").checked == true)
			checked = true;

		var tbl = document.getElementById("lineItemTable");
		var rowLen = tbl.rows.length;
		for(var idx=1;idx<rowLen;idx++)
		{
			var row = tbl.rows[idx];
			var cell = row.cells[0];
			var node = cell.lastChild;

			node.checked = checked;
		}
	}

	function cetak()
	{
		document.filterForm.action = "<c:url value='/page/journalBatchingPrint.htm'/>";
		document.filterForm.submit();
	}
</script>