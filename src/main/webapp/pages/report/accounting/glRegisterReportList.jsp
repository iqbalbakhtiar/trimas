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
	<link rel="icon" type="image/png" href="<c:url value='/assets/images/title-logo.png'/>">
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/assets/sirius-print.css'/>");
    </style>
    
    <style type="text/css" media="print">
		@import url("<c:url value='/assets/sirius-print.css'/>");
    </style>
	<script type="text/javascript">	
		function printPage()
		{
	   		print();
		}
	</script>
</head>
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp"%>
</div>
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
				<td width="60%">${breadcrumb}</td>
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
							<h1 class="page-title">${pageTitle}</h1>
							<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/glregisterreportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
								<a class="item-button-export-xls" download="glregisterreportexcelview.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'GL Register');"><span><spring:message code="sirius.export"/></span></a>
							</div>
						</div>
						<div class="main-box">
							<div id="progressbar" style="display:none;"><div class="progress-label"></div></div>
							<div class="main_container">
								<%@include file="glRegisterReportPrint.jsp" %>
							</div>
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
<script src="<c:url value='/js/loading.js'/>"></script>
<script type="text/javascript" language="javascript">
	$(function()
	{
		$(window).load(function()
		{
			const orgs = document.getElementsByClassName('organizations');
			let arrayOrgs = [];
			
			for(const org of orgs)
				arrayOrgs.push(org.value);
			
			const prds = document.getElementsByClassName('periods');
			let arrayPrds = [];
			
			for(const prd of prds)
				arrayPrds.push(prd.value);
			
			$('.glreg').each(function()
			{
				const glRegId = this.id;

				if(glRegId)
				{
					$.ajax({
						url:"<c:url value='/page/glregisterreportjson.htm?organizationIds='/>"+Array.from(new Set(arrayOrgs))+"&accountingPeriodIds="+Array.from(new Set(arrayPrds)),
						data:{account:glRegId},
						type : 'POST',
						async: true,
						beforeSend : function() {
							// should place loading bar but not accurately loading? strange behaviour
						},
						success : function(json) {
						    $tbody = $(document.getElementById(json.id));

						    var opening = '<tr><td colspan="7"><strong>{{account}}</strong></td></tr><tr>'+
						        '<td>&nbsp;</td>'+
						        '<td colspan="3"><strong>Opening Balance</strong></td>'+
						        '<td align="right"><strong></strong></td>'+
						        '<td align="right"><strong></strong></td>'+
						        '<td align="right"><strong>{{balance}}</strong></td>'+
						    '</tr>';

						    var detail = '<tr valign="top">'+
						        '<td align="left" nowrap="nowrap">{{date}}</td>'+
						        '<td><a href="<c:url value='/page/journalentrypreview.htm?id={{id}}'/>">{{code}}</a></td>'+
						        '<td colspan="2">{{note}}</td>'+
						        '<td align="right">{{debet}}</td>'+
						        '<td align="right">{{credit}}</td>'+
						        '<td align="right">{{balance}}</td>'+
						    '</tr>';

						    var closing = '<tr>'+
						        '<td>&nbsp;</td>'+
						        '<td colspan="3"><strong>Closing Balance</strong></td>'+
						        '<td align="right" class="border-top"><strong>{{debet}}</strong></td>'+
						        '<td align="right" class="border-top"><strong>{{credit}}</strong></td>'+
						        '<td align="right" class="border-top"><strong>{{balance}}</strong></td>'+
						    '</tr>'+
						    '<tr colspan="7"><td>&nbsp;</td></tr>';

						    $balance = json.balance;
						    $debet = 0;
						    $credit = 0;

						    $tbody.append(opening.compose({
						        'account': json.account,
						        'balance': $balance < 0 ? '('+($balance*-1).numberFormat('#,#.00')+')' : $balance.numberFormat('#,#.00')
						    }));

						    if (!jQuery.isEmptyObject(json.details)) {
						        $.each(json.details, function(idx, obj) {
						            $balance = $balance + obj.debetRp - obj.creditRp;
						            $debet = $debet + obj.debetRp;
						            $credit = $credit + obj.creditRp;

						            $tbody.append(detail.compose({
						                'id': obj.id,
						                'date': obj.dateReport,
						                'code': obj.code,
						                'note':  obj.note,
						                'debet': obj.debetRp > 0 ? obj.debetRp.numberFormat('#,#.00') : '',
						                'credit': obj.creditRp > 0 ? obj.creditRp.numberFormat('#,#.00') : '',
						                'balance': $balance < 0 ? '('+($balance*-1).numberFormat('#,#.00')+')' : $balance.numberFormat('#,#.00')
						            }));
						        });
						    }

						    $tbody.append(closing.compose({
						        'debet': $debet.numberFormat('#,#.00'),
						        'credit': $credit.numberFormat('#,#.00'),
						        'balance': $balance < 0 ? '('+($balance*-1).numberFormat('#,#.00')+')' : $balance.numberFormat('#,#.00')
						    }));
						},
						complete : function() {
							// should place loading bar but not accurately loading? strange behaviour
							Loading.init();
						}
					});
				}
			});
		});
	});
</script>
