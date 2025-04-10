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
	<%--<%@ include file="/filter/sales/deliveryOrderFilter.jsp"%>--%>
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
					  	<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
								<tr>
									<td width="35%" height="30" align="left" valign="middle">
									<div class="toolbar-clean">
										<a class="item-button-list" href="<c:url value='/page/deliveryorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
										<a class="item-button-next" ><span><spring:message code="sirius.next"/></span></a>
										<%-- <a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a> --%>
									</div>
									</td>
									<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
								</tr>
							</table>
					  	</div>
  						<sesform:form id="addForm" name="addForm" method="post" modelAttribute="deliveryOrder_form">
						<input type="hidden" value="0" id="customerId"/>
						<input type="hidden" value="0" id="taxId"/>
					  	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
						<thead>
						<tr>
							<th width="1%"><div style="width: 20px;">&nbsp;</div></th>
							<th colspan="2"><spring:message code="deliveryorder.reference"/> / <spring:message code="sirius.item"/></th>
							<th width="5%"><spring:message code="sirius.qty"/></th>
							<th width="8%"><spring:message code="sirius.date"/></th>
							<th width="12%"><spring:message code="facility"/></th>
							<th width="8%"><spring:message code="sirius.tax"/></th>
							<th width="12%"><spring:message code="customer"/></th>
							<th width="14%"><spring:message code="sirius.reference.type"/></th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${deliveryReferences}" var="item" varStatus="status">
							<c:if test='${deliveryReferences[status.index-1].referenceId != item.referenceId}'>
							<tr class="strong">	
								<td class="tools">
									<input id="check[${status.index}]" type="checkbox" class="check" index="${status.index}" indexClass="${item.referenceType.messageName}${item.referenceId}" onclick="checkValidation('${status.index}','${item.tax.id}','${item.customer.id}')"/>
								</td>
								<td colspan="3">
									<a href="<c:url value='/page/${item.uri}preedit.htm?id=${item.referenceId}'/>"><c:out value='${item.code}'/></a>
								</td>
								<td><fmt:formatDate value='${item.date}' pattern='dd-MM-yyyy'/></td>
								<td><c:out value='${item.facility.name}'/></td>
								<td><c:out value='${item.tax.taxName}'/></td>
								<td><c:out value='${item.customer.fullName}'/></td>
								<td><spring:message code='deliveryorder.reference.${item.referenceType.messageName}'/></td>
							</tr>
							</c:if>
							<tr>
								<td>&nbsp;</td>
								<td width="3%">&nbsp;</td>
								<td width="20%"><c:out value='${item.product.name}'/></td> 
								<td align="right">
									<fmt:formatNumber value='${item.quantity}' pattern=',##0'/>
									<c:out value='${item.product.unitOfMeasure.measureId}'/>
								</td>
								<td colspan="6">
									<form:checkbox hidden="hidden" disabled="true" cssClass="${item.referenceType.messageName}${item.referenceId}" path='items[${status.index}].enabled'/>
									<form:input hidden="hidden" disabled="true" cssClass="${item.referenceType.messageName}${item.referenceId}" path='items[${status.index}].deliveryReferenceItem' value="${item.id}"/>
								</td>
							</tr>
							<c:if test='${deliveryReferences[status.index+1].referenceId != item.referenceId}'>			
								<tr><td colspan="9">&nbsp;</td></tr>
							</c:if>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
						</tfoot>
						</table>
						</sesform:form>
						<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>
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
	$(document).ready(function()
	{
		$(".item-button-next").click(function()
		{
			let total = $("[class='check']:checked").length;

			if(total < 1)
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="deliveryorder.reference"/> <spring:message code="notif.select2"/> !');
				return;
			}

			let $dialog_confirm = $('<div title="${notif.confirmation}"></div>')
			.html('<spring:message code="notif.selected1"/> '+total+' <spring:message code="notif.selected2"/>, <spring:message code="notif.selected3"/> ?');

			$dialog_confirm.dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Yes": function()
					{
						$( this ).dialog( "close" );
						submit("addForm","<c:url value='/page/deliveryorderpreadd2.htm'/>");
					},
					"No": function()
					{
						$( this ).dialog( "close" );
					}
				}
			});
		});

		$('.check').change(function () {
			$('.'+$(this).attr('indexClass')).prop("checked",this.checked);
			
			if(this.checked)
				$('.'+$(this).attr('indexClass')).removeAttr("disabled");
			else
				$('.'+$(this).attr('indexClass')).attr("disabled",true);
		});
	});
	
	function checkValidation(index, taxId, customerId)
	{
		var customer = document.getElementById('customerId');
		var tax = document.getElementById('taxId');

		if(document.getElementById("check["+index+"]").checked == true)
		{
			if(customer.value == '0')
				customer.value = customerId;
			else
			{
				if(customerId != customer.value)
				{
					alert('<spring:message code="notif.selected.different1"/> <spring:message code="customer"/> <spring:message code="notif.selected.different2"/> !!!');
					$.each(function(idx,elem)
					{
						var i=elem.attr('index');
						$("#check\\["+i+"\\]").removeAttr("checked");
					});
					
					clearValidation();
					return;
				}
			}

			if(tax.value == '0')
				tax.value = taxId;
			else {
				if(taxId != tax.value) {
					alert('<spring:message code="notif.selected.different1"/> <spring:message code="sirius.tax"/> <spring:message code="notif.selected.different2"/> !!!');
					$('.check').each(function()
					{
			            if(this.checked)
			                this.checked = false;
			        });
					
					clearValidation();
					return;
				}
			}
		} else {
			var countCheck = 0;
			$('.check').each(function(){
	            if(this.checked)
	                countCheck++;
	        });
			
			if(countCheck == 0)
				clearValidation();
		}
	}

	function clearValidation() {
		$('#customerId').val(0);
		$('#taxId').val(0);
		$('.check').change();
	}
</script>