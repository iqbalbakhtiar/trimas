<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title }</title>
	<style type="text/css" media="screen">
		<!-- @import url("assets/sirius.css"); -->
		<!-- @import url("css/report_format.css"); -->
		@import url("<c:url value='/css/jquery-ui-1.8.2.custom.css'/>");
    </style>
    
    <style type="text/css" media="print">
		<!-- @import url("css/print_format.css"); -->
    </style>
    <script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script> 
    <script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script> 
    <script src="<c:url value='/js/Facility.js'/>"></script> 
    <script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/page/dwrService/engine.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/page/dwrService/interface.js'/>"></script>
	<script type="text/javascript">	
		function printPage(){print();}

		function genAccount()
		{
			var check = document.getElementById("accounts");
			
			return check.value;
		}
		$(function(){
			var $this = $(this);

			$('#accounts').change(function(e){
				var $this = $(this);
				$('#filterAccount').attr('href',$('#beginFilterAccount').attr('href')+$('#accounts').val());
				$('#filterAccountPrint').attr('href',$('#beginFilterAccount').attr('href')+$('#accounts').val());
			});

		});
	</script>
</head>
<!-- BEGIN OF BODY -->
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp"%>
</div>


<!-- rounded -->
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>

		<div id="se-navigator">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="60%">${breadcrumb }</td>
				<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
			</tr>
			</table>
		</div>
	</div>	

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
					<div class="area" dojoType="Container" id="quick_link_container">
						<h1 class="page-title">${pageTitle }</h1>
						
						<div class="toolbar">
									<a class="item-button-back" href="<c:url value='/page/glregisterreportpagingpre.htm'/>"><span><spring:message code='sirius.back'/></span></a>
									<a class="item-button-print" href="javascript:window.print();"><span><spring:message code='sirius.print'/></span></a>
									<a id="filterAccountPrint" class="item-button-export-xls" href="<c:url value='/page/glregisterreportpagingexcelview.xls'/>"><span><spring:message code='sirius.export'/></span></a>

									<select id="accounts" name="accounts" class="combobox-ext">
											<option value='${selected.id}'>${selected.code} ${selected.name}</option>
											<option value=''>-</option>
                                             <c:forEach items="${reports}" var="accounts" >
                                            	<option value='${accounts.account.id}'>${accounts.account.code} ${accounts.account.name}</option>
                                            </c:forEach>
									</select>
									<a  id="filterAccount" class="item-button-generate-report" href="<c:url value='/page/glregisterreportpagingviews.htm?glRegisterFilterCriteria=${criteria}&accounts='/>"><span><spring:message code='sirius.generate'/></span></a>
									<a  id="beginFilterAccount" style="visibility: hidden" href="<c:url value='/page/glregisterreportpagingviews.htm?glRegisterFilterCriteria=${criteria}&accounts='/>"></a>
						</div>
					</div>

					<div class="main-box">
	 					<div class="pageTitle"><strong>GL Register<br/></strong></div><br/>
	 						<%@include file="glRegisterPagingReportPrint.jsp" %>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
  	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>

</body>

</html>
