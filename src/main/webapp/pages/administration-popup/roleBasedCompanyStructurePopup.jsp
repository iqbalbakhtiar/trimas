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
						<form id="filterPopup" name="filterPopup" method="post">
							<table width="100%" cellspacing="0" cellpadding="1" align="center">
							<tr>
								<td width="15%" align="right" nowrap="nowrap"><spring:message code="organization.code"/> / <spring:message code="organization.name"/></td>
							  	<td width="5%" align="center">:</td>
						  	  	<td width="75%" align="left"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td align="left"><%@ include file="/common/button.jsp"%></td>
							</tr>
							</table>
						</form>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="34%" height="30" align="left" valign="middle"></td>
							<td width="66%" align="right" height="20">
								<%@ include file="/common/navigate.jsp"%>
							</td>
						</tr>
						</table>
						<table class="table-list" cellspacing="0" cellpadding="0" width="80%">
					  	<tr>
					  		<th width="6%">&nbsp;</th>
					  		<th width="20%"><spring:message code="organization.code"/></th>
				  		  	<th width="74%"><spring:message code="organization.name"/></th>
				  		</tr>
				  		<c:forEach items="${companyfacilitys}" var="com">
						<tr>
					  		<td class="tools">
					  			<a class="item-button-add-row" href="javascript:setclient('${com.id}')"  title="Edit"><span>Edit</span></a>
					  		</td>
							<td nowrap="nowrap">${com.code}</td>
							<td>${com.fullName}</td>
					  		</tr>
						</c:forEach>
					  	<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
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
		Party.load(id);
		var organization = Party.data;

		if(!jQuery.isEmptyObject(organization))
		{
			var _client = self.opener.document.getElementById('${param.target}');
			if(_client)
			{
				for(var idx=_client.options.length;idx>=0;idx--)
				_client.remove(_client.selectedIndex);

				var _opt = document.createElement('option');
				_opt.value = organization.partyId;
				_opt.text = organization.partyName;

				_client.appendChild(_opt);
			}

			var _taxAddress = self.opener.document.getElementById('taxAddress');
			if(_taxAddress != null)
			{
				for(var idx=_taxAddress.options.length;idx>=0;idx--)
				_taxAddress.remove(_taxAddress.selectedIndex);

				if(!jQuery.isEmptyObject(organization.partyTaxAddress))
				{
					var _opt = document.createElement('option');
					_opt.value = organization.partyTaxAddress.postalId;
					_opt.text = organization.partyTaxAddress.postalAddress;
					_taxAddress.appendChild(_opt);
				}
				
			}

			var _billAddress = self.opener.document.getElementById('billTo');
			if(_billAddress != null)
			{
				for(var idx=_billAddress.options.length;idx>=0;idx--)
				_billAddress.remove(_billAddress.selectedIndex);

				if(!jQuery.isEmptyObject(organization.partyBillAddress))
				{
					var _opt = document.createElement('option');
					_opt.value = organization.partyBillAddress.postalId;
					_opt.text = organization.partyBillAddress.postalAddress;
					_billAddress.appendChild(_opt);
				}
			}

			var _shipAddress = self.opener.document.getElementById('shipTo');
			if(_shipAddress != null)
			{
				for(var idx=_shipAddress.options.length;idx>=0;idx--)
				_shipAddress.remove(_shipAddress.selectedIndex);

				if(!jQuery.isEmptyObject(organization.partyShipAddress))
				{
					var _opt = document.createElement('option');
					_opt.value = organization.partyShipAddress.postalId;
					_opt.text = organization.partyShipAddress.postalAddress;
					_shipAddress.appendChild(_opt);
				}
			}

		/*	var _facility = self.opener.document.getElementById('facility');
			if(_facility != null)
			{
				for(var idx=_facility.options.length;idx>=0;idx--)
					_facility.remove(_facility.selectedIndex);

				$.each(organization.partyFacilites, function(idx, obj){
					var _opt = document.createElement('option');
					_opt.value = obj.facility.facilityId;
					_opt.text = obj.facility.facilityName + " - " + obj.facility.facilityAddress;
					_facility.appendChild(_opt);
				});

			}
		*/	
			
		}

		window.close();
	}
</script>