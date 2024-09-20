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
	</style>
	<script type="text/javascript">
		function setclient(id,name,code,initial,type)
		{
			if(id && name) {
				var _code = self.opener.document.getElementById('categoryCode[${param.index}]');
				if(_code)
					_code.value = code;
				
				var _client = self.opener.document.getElementById('${param.target}');
				if(_client) {
					_client.remove(_client.selectedIndex);
					
					var _opt = document.createElement('option');
					_opt.value = id;
					_opt.text = name;
					_opt.setAttribute('code', code);
					
					_client.appendChild(_opt);

					var event = new Event('change');
					_client.dispatchEvent(event);
				}
				
				var _initial = self.opener.document.getElementById('initial[${param.index}]');
				if(_initial)
					_initial.value = initial;
				
				var _type = self.opener.document.getElementById('type[${param.index}]');
				if(_type)
					_type.value = type;
			}
			
			window.close();
		}
	</script>
</head>

<body>
<div id="se-containers_pick">
	<div id="se-r00">
		<div id="se-r01a">&nbsp;</div>
		<div id="se-r03">&nbsp;</div>
	</div>
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<form id="popupForm" name="popupForm" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
									<tr>
										<td width="25%" align="right"><spring:message code="sirius.code"/></td>
									  	<td width="5%" align="center">:</td>
								  	  <td width="65%" height="28" align="left"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right"><spring:message code="sirius.name"/></td>
									  	<td align="center">:</td>
										<td width="65%" height="28" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
									</tr>
									<tr>
										<td align="right" ><spring:message code="sirius.type"/>&nbsp;</td>
										<td width="5%" align="center">:</td>
										<td >
											<select id="type" name="type" class="combobox-ext">
												<option value=""><spring:message code="sirius.all"/></option>
							                    <option value="STOCK" ${not empty filterCriteria.type and filterCriteria.type == "STOCK"? "selected" : ""}>STOCK</option>
												<option value="NONSTOCK" ${not empty filterCriteria.type and filterCriteria.type != "STOCK"? "selected" : ""}>NON STOCK</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td align="left"><%@ include file="/common/filterandpaging.jsp"%></td>									
									</tr>
									</table>
								</form>
							</td>
						</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
						
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%">&nbsp;</th>
					  		<th width="20%"><spring:message code="sirius.code"/></th>
				  		  	<th width="30%"><spring:message code="sirius.name"/></th>
				  		  	<th width="30%"><spring:message code="sirius.type"/></th>
				  		</tr>
						<c:forEach items="${categorys}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}','${com.name}','${com.code}', '${com.type}')"  title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					  		</td>
							<td>${com.code}</td> 
							<td>${com.name}</td>
							<td>${com.type}</td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
					  	</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
				    <%@ include file="/common/sirius-general-bottom.jsp"%> 