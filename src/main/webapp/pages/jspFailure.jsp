<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
	<head>
		<title>Error Page</title>
	<%@ include file="/common/sirius-header.jsp"%>
		<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	</head>
<!-- BEGIN OF BODY -->
<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">

						<div class="toolbar">
							<a class="item-button-back" href="<c:url value='/page/dashboard.htm'/>"><span>Back</span></a>
						</div>
						
						<div class="main-box" style="height:400px">
						<p>	
							<font color="blue"><strong>PAGE/JSP ERROR</strong><br/></font>
							Sorry,The system cannot process your request.<br/>
							Please try again latter.	
						</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
