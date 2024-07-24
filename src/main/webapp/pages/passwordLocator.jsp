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
	<title>Bhumidana ERP System - Login</title>
	<style type="text/css" media="screen"><!-- @import url("<c:url value='/assets/sirius.css'/>"); --></style>
	<!-- meta tags here -->

	<script language="javascript" type="text/javascript">
		function locatePassword()
		{
			var userName = document.getElementById("userID")
			if(userName.value == "")
			{
				alert("User ID can not be empty !");
				return;
			}
			var url = "<c:url value='/page/locatePassword.htm'/>";
			document.passwordLocatorForm.action = url;
			document.passwordLocatorForm.submit();
		
		}
		
		function cancelLocate()
		{
			var url = "<c:url value='/page/signin.htm'/>";
			document.passwordLocatorForm.action = url;
			document.passwordLocatorForm.submit();
		}
	</script>

</head>
<!-- BEGIN OF BODY -->
<body>
<!-- main containers -->
<div class="lg-r00">
	<div class="lg-r01">
		<div class="lg-r02">
			<div class="lg-r03">
				<div class="lg-r04">

					<div class="lg-about">
						<div>
							<b>Welcome!</b>
							to ERP System<br />
							Version 1.0<br />
							&copy;2007
						</div>
					</div>
					
					<div class="lg-form">
						<form method="post" name="passwordLocatorForm" id="passwordLocatorForm">

							<div class="notes">
								Please enter<br />
								your ID and Email :
							</div>
							
							<div class="lg-formarea">
								<table width="211" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><div class="form-label-tiny">User ID :</div></td>
									<td><div class="form-value"><input type="text" name="userID" id="userID" class="inputbox" style="width:120px" /></div></td>
								</tr>
								<tr>
									<td><div class="form-label-tiny">Email :</div></td>
									<td><div class="form-value"><input type="password" name="email" id="email" class="inputbox" style="width:120px" /></div></td>
								</tr>
								<tr>
									<td colspan="2" align="center"><br/>
											<input type="submit" name="s1" value="OK" class="lg-button"  onclick="locatePassword();" />
					  						<input type="reset" name="r1" value="Cancel" class="lg-button" onclick="cancelLocate();" />
					  						<br /><br />
									</td>
								</tr>
							  </table>
							</div>
									
						</form>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>



</body>
<!-- END OF BODY -->
</html>