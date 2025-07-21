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
	<title>Sirius ERP - Shun Hui Zhiye</title>
	<%@include file="/common/resources.jsp" %>
	<style type="text/css" media="screen">@import url("<c:url value='/assets/sirius.css'/>");</style>
	<!-- meta tags here -->
</head>
<!-- BEGIN OF BODY -->
<body>
<!-- main containers -->
<div class="lg-r00">
	<div class="lg-r01">
		<div class="lg-r02">
			<div class="lg-r03">
				<div class="lg-r04">
					<div class="lg-form">
						<form method="post" name="loginForm" id="loginForm" action="<c:url value='/j_spring_security_check'/>">
							<div class="lg-formarea">
									<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td colspan="2">
											<div class="lg-back"></div>
											<div class="lg-title"><b>Welcome! to ERP System</b></div>
										</td>
									</tr>
									<tr>
										<td><div class="form-label-tiny">Username :</div></td>
										<td><div class="form-value"><input type="text" name="j_username" class="inputbox" style="width:120px" /></div></td>
									</tr>
									<tr>
										<td><div class="form-label-tiny">Password :</div></td>
										<td><div class="form-value"><input type="password" name="j_password" class="inputbox" style="width:120px" /></div></td>
									</tr>
									<tr>
										<td colspan="2" align="center"><br/>
												<input type="submit" name="s1" value="Login" class="lg-button" />
						  						<input type="reset" name="r1" value="Reset" class="lg-button" />
										</td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
								  </table>
							</div>
							<div class="notes">
								v2.0 &copy;2020 <br />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="left:50;bottom:0;position: absolute;">
<br/>
<a href="<c:url value='/plugins/printpdf.xpi'/>">Plugin Print PDF(Firefox)</a>
</div>


</body>
<!-- END OF BODY -->
</html>