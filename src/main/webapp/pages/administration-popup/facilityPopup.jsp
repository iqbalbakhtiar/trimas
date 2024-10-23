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
											<td width="130" align="right" style="WIDTH:130px;">Code / Name&nbsp;&nbsp;</td>
											<td width="7">:&nbsp;</td>
											<td width="295" height="28" align="left">
												<input type="text" id="approver" name="approver" value="${filterCriteria.name}" size="35" class="inputbox"/>
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
						<br/>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="4%">&nbsp;</th>
					  		<th width="40%"><spring:message code="facility.code"/></th>
							<th width="30%"><spring:message code="facility.type"/></th>
				  		  	<th width="30%"><spring:message code="facility.address"/></th>
				  		</tr>
						<c:forEach items="${facilitys}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td>${com.name}</td> 
							<td>${com.facilityType.name}</td>
							<td>${com.postalAddress.address}</td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
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
	</div>
	
   	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
<script type="text/javascript">
	function setclient(id)
	{
		Facility.load(id);
		var facility = Facility.data;

		if(!jQuery.isEmptyObject(facility))
		{
			var duplicate = false;
			
			var _destination = self.opener.document.getElementById('destination');
			var _source = self.opener.document.getElementById('source');
			if (_destination && _source)
			{
				if (_destination.value != '')
				{
					if (_source.value != '')
					{
						if (facility.facilityId == _source.value || facility.facilityId == _destination.value)
						{
							duplicate = true;
							alert('<spring:message code="facility"/> <spring:message code="notif.duplicate"/> !! ');
							return;
						}
					}
					else
					{
						if (facility.facilityId == _destination.value)
						{
							duplicate = true;
							alert('<spring:message code="facility"/> <spring:message code="notif.duplicate"/> !! ');
							return;
						}
					}
				}
				else
				{
					if (_source.value != '')
					{
						if (facility.facilityId == _source.value)
						{
							duplicate = true;
							alert('<spring:message code="facility"/> <spring:message code="notif.duplicate"/> !! ');
							return;
						}
					}
				}
			}
			
			if (!duplicate)
			{
				var _client = self.opener.document.getElementById('${param.target}');
				if(_client)
				{
					_client.remove(_client.selectedIndex);
					
					var _opt = document.createElement('option');
					_opt.setAttribute("alias", facility.facilityAlias);
					_opt.value = facility.facilityId;
					_opt.text = facility.facilityName;
					
					_client.appendChild(_opt);
					
					_client.focus();
	
					var event = new Event('change');
					_client.dispatchEvent(event);
				}
	
				var _address = self.opener.document.getElementById('facilityAddress');
				if(_address)
					_address.value = facility.address;
	
				var _container = self.opener.document.getElementById('container');
				if(_container)
				{
					Container.loadDefault(id);
					var container = Container.data;
					
					if(!jQuery.isEmptyObject(container))
					{
						_container.remove(_container.selectedIndex);
						
						var _ctr = document.createElement('option');
						_ctr.value = container.containerId;
						_ctr.text = container.containerName;
	
						_container.appendChild(_ctr);
					}
					else
					{
						_container.remove(_container.selectedIndex);
					}
				}
			}
		}
		
		window.close();
	}
</script>