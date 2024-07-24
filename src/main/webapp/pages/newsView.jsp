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
		<title>News Update Panel</title>
			<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
			<%@ include file="/common/sirius-header.jsp"%>
			<script type="text/javascript" src="assets/main.js"></script>

			<script type="text/javascript" src="tinymce/jscripts/tiny_mce/tiny_mce_src.js"></script>
			<script type="text/javascript">
			tinyMCE.init({
				mode : "textareas",
				theme : "advanced",
				editor_selector : "mceEditor",		
				theme_advanced_buttons1 : "bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright, justifyfull,bullist,numlist,undo,redo,link,unlink",
				theme_advanced_buttons2 : "",
				theme_advanced_buttons3 : "",
				theme_advanced_toolbar_location : "top",
				theme_advanced_toolbar_align : "center",		
				extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]"
			});
			</script>	

			<script type="text/javascript">
			function save()
			{
				document.expeditionAddForm.action = "<c:url value='/page/newsUpdate.htm?id='/><c:out value='${data.news.id}'/>";
				document.expeditionAddForm.submit();
			}
		</script>		
			
			
	</head>
<!-- BEGIN OF BODY -->
<body>

<!-- top  menu -->
	
<!-- /top menu -->


<!-- rounded -->
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<!-- /rounded -->

<!-- main containers -->
<div id="se-containers">

	<!-- main menu -->
		<%@ include file="/common/sirius-menu.jsp"%>
	<!-- /main menu -->

	<!-- tips & page navigator -->
	<div id="se-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">
				Tools > News > View
			</td>
			<td width="40%" align="right">
				<%@ include file="/common/welcome.jsp"%>
			</td>
		</tr>
	</table>
	
	</div>
	<!-- /tips & page navigator -->
	
	<!-- rounded -->
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
					<!-- main contents goes here -->
						
						<h1 class="page-title">T40 - News </h1>
						
						<!-- Top toolbar -->
					  <div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/newsView.htm'/>"><span>List</span></a>
					  </div>
					  

<div class="main-box">
<div class="area" dojoType="Container" id="quick_link_container">
<form class="edit-form" id="expeditionAddForm" name="expeditionAddForm" method="post">
			 <table width="100%" style="border:none">
				<tr>
					<td></td>
					<td></td>			
					<td></td>
				</tr>
				<tr>
					<td width="12%"></td>
					<td width="50%"><fmt:formatDate value='${data.news.date}' pattern='dd MMM yyyy'/>&nbsp;-&nbsp;<c:out value='${data.news.title}'/></td>		
					<td width="38%" align="left"></td>
				</tr>	
				<tr>
					<td></td>
					<td><em>Posted by : <c:out value='${data.news.createdBy}'/></em></td>			
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">
						${data.news.content}
					</td>		
				</tr>
				<tr>
					<td></td>
					<td></td>			
					<td></td>
				</tr>
			</table>
	<br>
 </form>
 </div>
</div>
</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<span>&copy; 2007 siriusERP v1.0GA&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

	<!-- /footer -->
</div><!-- /main containers -->



</body>
<!-- END OF BODY -->
</html>
