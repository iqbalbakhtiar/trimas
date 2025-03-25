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
	<%@ include file="/common/sirius-header.jsp" %>
        
	<script type="text/javascript">
		function save()
		{
			document.addForm.action = "<c:url value='/page/deliveryplanningadd.htm'/>";
			document.addForm.submit();
		}

		function error()
		{
			<c:if test='${not empty message}'>
				alert('${message}');
			</c:if>
		}
	</script>
</head>

<body onload="error();">

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
							<a class="item-button-list" href="<c:url value='/page/deliveryplanningview.htm'/>"><span>List</span></a>
							<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
					  	</div>

						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="planning_add">
								<table width="100%" style="border:none">
                                <tr>
                                	<td width="58%">
                                   	  <table width="100%" style="border:none">
                                        <tr>
                                            <td width="20%" nowrap="nowrap" align="right">ID :</td>
                                          <td width="80%"><input value="Auto Generated" class='input-disabled' size='25'/></td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right">Company :</td>
                                            <td>
                                                <select class="combobox-ext" disabled='true'>
                                                    <option><c:out value='${planning_add.planable.organization.firstName} ${planning_add.planable.organization.lastName} ${planning_add.planable.organization.middleName}'/></option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right">Date :</td>
                                            <td><input id="date" name="date" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/></td>
                                        </tr>
                                        <tr>
                                            <td width="20%" nowrap="nowrap" align="right">Customer :</td>
                                          <td width="80%"><input value="${planning_add.planable.customer.firstName} ${planning_add.planable.customer.middleName} ${planning_add.planable.customer.lastName}" class='input-disabled' size='45'/></td>
                                        </tr>
                                        <tr>
                                            <td width="20%" nowrap="nowrap" align="right">Sales Order :</td>
                                          <td width="80%"><a href="<c:url value='/page/${planning.planable.uri}?id=${planning_add.planable.id}'/>"><c:out value='${planning_add.planable.code}'/></a></td>
                                        </tr>
                                        </table>
                                    </td>
                                    <td width="42%" valign="top">
                                    	<fieldset>
                                        	<legend>Product Information</legend>
                                            <table width="100%" cellpadding="0" cellspacing="0" class="table-list">
                                            <tr>
                                                <th width="39%">Product</th>
                                                <th width="15%">Order</th>
                                                <th width="16%">Assign</th>
                                                <th width="14%">Unasign</th>
                                                <th width="14%">Resend</th>
                                                <th width="16%">Canceled</th>
                                            </tr>
                                            <c:forEach items='${planning_add.planable.sequenceables}' var='able'>
                                            <tr>
                                            	<td><c:out value='${able.product.name}'/></td>
                                                <td><fmt:formatNumber value='${able.quantity}' pattern=',##0.00'/></td>
                                                <td><fmt:formatNumber value='${able.assigned}' pattern=',##0.00'/></td>
                                                <td><fmt:formatNumber value='${able.quantity-able.assigned-able.resend}' pattern=',##0.00'/></td>
                                                <td><fmt:formatNumber value='${able.resend}' pattern=',##0.00'/></td>
                                                <td><fmt:formatNumber value='${able.returned}' pattern=',##0.00'/></td>
                                            </tr>
                                            </c:forEach>
                                            <tr class="end-table"><td colspan="6">&nbsp;</td></tr>
                                            </table>
                                        </fieldset>
                                    </td>
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
	<div id="footer"><div><span>&copy; 2007 siriusERP v1.0GA</span></div></div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>
</body>
</html>