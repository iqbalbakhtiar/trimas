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
	<%@ include file="/common/filterandpaging.jsp"%>
	<script type="text/javascript">
		function setclient(id,name,code,uom)
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
					_client.dispatchEvent(event); // Trigger onchange event from selected element.
				}
			}

			// Uom
			if(uom) {
				var _uom = self.opener.document.getElementById('uom[${param.index}]');
				if(_uom) {
					_uom.value = uom;
				}
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
							<tr><td>
								<form id="filterPopup" name="filterPopup" method="post">
									<table width="100%" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.code"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.name"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="sirius.uom"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="unitOfMeasure" name="unitOfMeasure" value="${filterCriteria.unitOfMeasure}" size="35" class="inputbox"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="sirius.type"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<select id="type" name="type">
													<option value=""><spring:message code="sirius.all"/></option>
													<option value="GOODS" ${not empty filterCriteria.type and filterCriteria.type == "GOODS" ? "selected" : ""}>GOODS</option>
													<option value="SERVICE" ${not empty filterCriteria.type and filterCriteria.type == "SERVICE" ? "selected" : ""}>SERVICE</option>
													<option value="ASSET" ${not empty filterCriteria.type and filterCriteria.type == "ASSET" ? "selected" : ""}>ASSET</option>
													<option value="NON_ASSET" ${not empty filterCriteria.type and filterCriteria.type == "NON_ASSET" ? "selected" : ""}>NON ASSET</option>
												</select>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="productcategory"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="productCategory" name="productCategory" class="inputbox" value="${filterCriteria.productCategory}"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="sirius.status"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<select id="status" name="status">
													<option value=""><spring:message code="sirius.all"/></option>
													<option value="true" ${not empty filterCriteria.status and filterCriteria.status ? "selected" : ""}><spring:message code="sirius.active"/></option>
													<option value="false" ${not empty filterCriteria.status and !filterCriteria.status ? "selected" : ""}><spring:message code="sirius.inactive"/></option>
												</select>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.origin"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="origin" name="origin" class="inputbox" value="${filterCriteria.origin}"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.brand"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="brand" name="brand" class="inputbox" value="${filterCriteria.brand}"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.grade"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="grade" name="grade" class="inputbox" value="${filterCriteria.grade}"/>
											</td>
										</tr>
										<tr>
											<td width="130" align="right" style="WIDTH:130px;"><spring:message code="product.part"/></td>
											<td width="7">&nbsp;:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="part" name="part" class="inputbox" value="${filterCriteria.part}"/>
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td><%@ include file="/common/button.jsp"%></td>
										</tr>
									</table>
								</form>
							</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="34%" height="30" align="left" valign="middle"></td>
								<td width="66%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>

						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
							<tr>
								<th width="3%">&nbsp;</th>
								<th width="3%"><spring:message code="product.code"/></th>
								<th width="5%"><spring:message code="product.name"/></th>
								<th width="2%"><spring:message code="sirius.uom"/></th>
								<th width="2%"><spring:message code="sirius.type"/></th>
								<th width="4%"><spring:message code="productcategory"/></th>
								<th width="3%"><spring:message code="sirius.status"/></th>
								<th width="3%"><spring:message code="product.origin"/></th>
								<th width="3%"><spring:message code="product.brand"/></th>
								<th width="3%"><spring:message code="product.grade"/></th>
								<th width="3%"><spring:message code="product.part"/></th>
							</tr>
							<c:forEach items="${products}" var="product">
								<tr>
									<td class="tools">
										<a class="item-button-add-row" href="javascript:setclient('${product.id}','${product.name}','${product.code}', '${product.unitOfMeasure.measureId}')"  title="Edit"><span><spring:message code="sirius.edit"/></span></a>
									</td>
									<td nowrap="nowrap">${product.code}</td>
									<td nowrap="nowrap">${product.name}</td>
									<td nowrap="nowrap">${product.unitOfMeasure.measureId}</td>
									<td nowrap="nowrap">${product.type}</td>
									<td nowrap="nowrap">${product.productCategory.name}</td>
									<td nowrap="nowrap"><spring:message code="sirius.${product.status ? 'active' : 'inactive'}"/></td>
									<td nowrap="nowrap">${product.origin}</td>
									<td nowrap="nowrap">${product.brand}</td>
									<td nowrap="nowrap">${product.grade}</td>
									<td nowrap="nowrap">${product.part}</td>
								</tr>
							</c:forEach>
							<tr class="end-table"><td colspan="11">&nbsp;</td></tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
							<tr>
								<td align="right"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
	<%@ include file="/common/sirius-footer.jsp"%>

	<!-- /footer -->
</div><!-- /main containers -->
</body>
<!-- END OF BODY -->
</html>