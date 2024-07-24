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
	<title>Customer > Person In Charge > Edit</title>
	<%@ include file="/common/sirius-header.jsp"%>
    	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script> 
	<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>
	<script src="<c:url value='/js/Facility.js'/>"></script> 
	<script type="text/javascript" src="assets/dialog.js"></script>
	<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/page/dwrService/engine.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/page/dwrService/interface.js'/>"></script> 
	<script type="text/javascript">
		function save()
		{
			var person = document.getElementById('person');
			if(person.value == '')
			{
				alert('Please select person first!');
				return;
			}
			if($("#toRole option").val() == null){
				alert('All role is already taken !');
				return;
			}
			
			document.editForm.action = "<c:url value='/page/partyinchargeadd.htm'/>";
			document.editForm.submit();
		}
		
		function showrole()
		{
			var party = document.getElementById('org');
			if(party.value == null || party.value == '')
			{
				alert("Please select Party first!");
				return;
			}
			
			openpopup("<c:url value='/page/popuppartyroleview.htm?target=toRole&party='/>"+party.value);
		}

		function remove(){
			$send = false;
			$send = confirm('Are you sure want to delete this data ?');
			if($send){
				document.editForm.action = "<c:url value='/page/partyinchargedelete.htm?id=${charge.id}'/>";
				document.editForm.submit();
			}	
		}

		function opensales(target)
		{
			var org = document.getElementById('org');
			if(org.value == '')
			{
				alert('Please select Company first!');
				return;
			}

			var toRole = document.getElementById('toRole');
			if(toRole.value == '')
			{
				alert('Please select role first!');
				return;
			}
				
			openpopup("<c:url value='/page/popupsalespersoninchargeview.htm?target="+target+"&organization="+org.value+"&charge='/>"+toRole.value);
		}
		
		function accReset(){
			$("#person").html("");	
		}
		
	

	</script>
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
			<td width="60%">Company Administration > Customer > Person In Charge > Edit</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
					<h1 class="page-title">A02 - Person In Charge</h1>
						
					<div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/customerpreedit.htm?id=${party.id}'/>"><span>List</span></a>
						<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
						<a class="item-button-delete" href="javascript:remove();"><span>Delete</span></a>
					</div>					  
					  
					<div class="main-box">
						<sesform:form  name="editForm" method="post" modelAttribute="partyRelationship">
						<table width="100%">
 						<tr><td colspan="3">&nbsp;</td></tr>
 						<tr> 	
 							<td width="296" align="right">Customer :</td>
					  	  	<td width="707">
					  	  		<select id="customer" name="customer"  class="selectbox-ext">
					  	  			<option value="${party.id}">
											<c:out value="${party.firstName} ${party.middleName} ${party.lastName}"></c:out>
									</option>
					  	  		</select>
					  	  	</td>
					  	  	<td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">Company :</td>
							<td width="707">
  				  				<select id="org" name="organization"  class="selectbox-ext">
										<option value="${organization.id}" >
											<c:out value="${organization.firstName} ${organization.middleName} ${organization.lastName}"></c:out>
										</option>
 								</select>
							</td>
						  <td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">To Role :</td>
							<td width="707">
							  <select id="toRole" name="toRole" class="selectbox">
										<option value="${role.id}">
											<c:out value="${role.name}"></c:out>
										</option>
 							  </select>
							</td>
						  <td width="225">&nbsp;</td>
 						</tr>
 						<tr> 	
 							<td align="right">People In Charge :</td>
							<td width="707">
                           	  <select id="person" name="person" class="combobox-ext">
									<option value="${person.id}" >
											<c:out value="${person.firstName} ${person.middleName} ${person.lastName}"></c:out>
									</option>
							  </select>
							</td>
						  <td width="225">&nbsp;</td>
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
	<div id="footer">
		<div>
			<span>&copy; 2012 siriusERP v1.5&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>
</div>
</body>

</html>
