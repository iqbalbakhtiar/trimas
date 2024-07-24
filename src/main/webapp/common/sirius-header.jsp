<%@include file="/common/resources.jsp" %>
<%@include file="/common/popup.jsp" %>
<div id="se-top">
	<a class="logo"><span>siriusERP</span></a>
	<table border="0" cellpadding="0" cellspacing="0" style="width:20%; text-align:left;" align="right">
	 <tr valign="top">
	 	<td align="right" valign="top"><a class="go-to">Menu Code : </a></td>
	 	<td style="width:10%" valign="top"><form action="page/goTo.htm"><input type="text" name="code" id="txtMenuCode" size="10" class="search"/></form></td>
		<td><a class="logout" href="<c:url value='/page/signout.htm' />">Logout</a></td>
		<td><a class="home" href="<c:url value='/page/dashboard.htm'/>"><span>Home</span></a></td>
		<td><a class="message" href="<c:url value='/page/messaginglist.htm'/>"><span>Message</span>[<c:out value="${msgCount}"/>]</a></td>
	 </tr>
	</table>
</div>