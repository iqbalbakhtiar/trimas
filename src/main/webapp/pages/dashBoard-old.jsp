<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ include file="/common/tld-common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>SiriusERP-1.5 GA</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<%@ include file="/filter/dashboardProfitLossFilter.jsp"%>
	<style type="text/css" media="screen"><!-- @import url("<c:url value='/assets/sirius.css'/>"); --></style>
	<script type="text/javascript" src="assets/main.js"></script>
	<!-- meta tags here -->
	<script language="JavaScript" type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>

	<style type="text/css" >
	</style>

	<style>
		html, body {
			height: 100%;
			width: 100%;
			overflow: hidden;
		}
		#main {
			height: 90%;
			width: 95%;
			left: 1%;
			top: 1%;
			position: relative; overflow:auto;
		}
	</style>
</head>
<!-- BEGIN OF BODY -->
<body>

<!-- top menu -->

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
			<td width="60%" style="font-family: Verdana, Arial, Helvetica, sans-serif; font-style:italic; color:#999999; font-size:11px;">Click menu button for more options!</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<table border="0" cellpadding="0" cellspacing="0" width="931">
				  		<tr>
				   			<td width="691" height="370" align="left" valign="top">
								<table border="0" cellpadding="0" cellspacing="0" width="691">
						  		<tr>
						   			<td width="10" height="370" align="left" valign="top"></td>
						   			<td width="681" height="370" align="left" valign="top">
										<table border="0" cellpadding="0" cellspacing="0" width="658">
										<tr>
								   			<td width="658" height="21" align="left" valign="top"><img src="<c:url value='/images/company_news.gif'/>"></td>
										</tr>
							   			<tr>
								   			<td width="658" height="320" align="left" valign="top">
												<div dojoType="LayoutContainer"	layoutChildPriority='none' style="border: 0px solid white; padding: 10px; height:318px; width:675px;" id="main">
													<div dojoType="ContentPane" layoutAlign="top" padding:"10px; margin: 5px;">
								  						<c:forEach items='${data.latesNews}' var='news'>
                                                        	<table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                            <tr>
                                                              	<td width="3%" class="border_right_bottom">&nbsp;</td>
                                                              	<td width="13%" align="center" valign="middle" class="border_top_right" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#ff0000; padding:0px 4px 0px 4px;" nowrap><fmt:formatDate value='${news.date}' pattern='dd MMM yyyy'/></td>
                                                              	<td width="80%" class="border_bottom" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#999999; padding:0px 4px 0px 4px;"><strong><c:out value='${news.title}'/></strong></td>
                                                              	<td width="4%">&nbsp;</td>
                                                            </tr>
                                                            <tr>
                                                              	<td colspan="3" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:black; padding:10px 4px 0px 4px; text-align:justify;">${news.content}<br></td>
                                                              	<td>&nbsp;</td>
                                                            </tr>
                                                            <tr>
                                                             	<td align="right" valign="top" colspan="3" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:ff0000; padding:0px 4px 0px 4px;"><em>&copy;Posted By&nbsp;:</em>&nbsp;<c:out value='${news.createdBy.firstName} ${news.createdBy.middleName} ${news.createdBy.lastName}'/></td>
                                                            	<td>&nbsp;</td>
                                                            </tr>
                                                            </table><br/>
														</c:forEach>
							  						</div>
												</div>
											</td>
								  		</tr>
								  		<tr>
								   			<td width="658" height="29" align="left" valign="top"></td>
								  		</tr>
										</table>
						  			</td>
						  		</tr>
								</table>
							</td>
				   			<td width="2" height="370" align="left" valign="top"></td>
				   			<td width="238" height="370" align="left" valign="top">
						<table cellpadding="0" cellspacing="0" border="0" width="238">
						<tr>
							<td height="21" align="left" valign="top"><img src="<c:url value='/images/dashboard.gif'/>"></td>
						</tr>
						<tr>
							<td height="236" align="left" valign="top">
								<table border="0" cellpadding="0" cellspacing="0" width="238">
								  <tr>
								   <td width="238" height="10" align="left" valign="top"></td>
								  </tr>
								  <tr>
								   <td width="238" height="32" align="left" valign="top">
										<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="23" height="32"></td>
											<td width="32" height="32"><img src="<c:url value='/images/purchase_s.gif'/>"></td>
											<td width="147" align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000;">
												<a href="<c:url value='/page/profitlossconsecutivechart.htm'/>">&nbsp;NET INCOME</a>
											</td>
										  <td width="36"></td>
										</tr>
										</table>
									</td>
								  </tr>
								  <tr>
								   <td width="238" height="10" align="left" valign="top"></td>
								  </tr>
								  <tr>
								   <td width="238" height="32" align="left" valign="top">
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="23" height="32"></td>
												<td width="32" height="32"><img src="<c:url value='/images/purchase_s.gif'/>"></td>
												<td width="147" align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000;">&nbsp;<a href="<c:url value='/page/totalincomechart.htm'/>">TOTAL INCOME</a></td>
												<td width="35"></td>
											</tr>
										</table>
								   </td>
								  </tr>
								  <tr>
								   <td width="238" height="10" align="left" valign="top"></td>
								  </tr>
								</table>
							</td>
						</tr>
	
						<tr>
							<td height="113" align="left" valign="top">
							<table border="0" cellpadding="0" cellspacing="0" width="238">
							  <tr>
							   <td width="238" height="26" align="left" valign="top"><img src="<c:url value='/images/myapproval.gif'/>"></td>
							  </tr>
							  <tr>
							   <td width="238" height="42" align="left" valign="top">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr height="42">
										<td width="32" height="42">&nbsp;</td>
										<td width="32" height="42"><img src="<c:url value='/images/purchase_r.gif'/>"></td>
										<td width="183" height="42" align="left" valign="middle">
											<table width="172" border="0" cellpadding="0" cellspacing="0">
											<tr>
											<td width="172" align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000;">&nbsp;MY APPROVAL</td>
											</tr>
											<tr>
											<c:if test="${requested == 0}">
												<td align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:9px; color:#ff0000;">&nbsp;&nbsp;&nbsp;&nbsp;No transaction to be approved.</td>
											</c:if>
											<c:if test="${requested != 0}">
												<td align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:10px; color:#ff0000;">&nbsp;
												<a href="<c:url value='/page/approvalview.htm'/>">
													<c:out value="${requested}"/>&nbsp;Approval to be approved.
												</a>
												</td>
											</c:if>
											
											</tr>
										</table>
										</td>
										<td width="10" height="42"></td>
									</tr>
								</table>
							   </td>
							  </tr>
							  <tr>
							  
							   <td width="238" height="45" align="left" valign="top">
									<table border="0" cellpadding="0" cellspacing="0">
										<!-- 
										<tr height="42">
											<td width="32" height="42">&nbsp;</td>
											<td width="32" height="42"><img src="<c:url value='/images/purchase_s.gif'/>"></td>
											<td width="185" height="42" align="left" valign="middle">
										
												<table width="172" border="0" cellpadding="0" cellspacing="0">
													<tr>
													<td width="172" align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000;">&nbsp;PURCHASE REQUISITION</td>
													</tr>
													<tr>
													<td align="left" valign="middle" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size:9px; color:#ff0000;">&nbsp;&nbsp;&nbsp;&nbsp;No Requisition to be approved.</td>
													</tr>
												</table>
											 
											</td>
											<td width="7" height="42"></td>
										</tr>
										-->
									</table>
							   </td>
							  </tr>
							</table>
							</td>
						</tr>

						</table>
					</td>
				  </tr>
				</table>
				  </div>
				<div>
					<div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/address.jsp"%>

	<%@ include file="/common/sirius-footer.jsp"%>

</div>



</body>

</html>