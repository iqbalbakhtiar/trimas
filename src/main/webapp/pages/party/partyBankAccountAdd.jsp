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
	<%@ include file="/common/sirius-header.jsp"%>
    	<script type="text/javascript" language="javascript">	
		function save()
		{
			if(document.getElementById('account').value == null || document.getElementById('account').value == '')
			{
				alert('Cash/Bank Account cant be empty!');
				return;
			}
			
			document.addForm.action="<c:url value='/page/partybankaccountadd.htm'/>";
			document.addForm.submit();
		}
		
		function openaccount()
		{
			openpopup("<c:url value='/page/popupbankaccountallview.htm?target=account'/>");
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
            <td width="60%">${breadcrumb}</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<h1 class="page-title">${pageTitle}</h1>
						
						<div class="toolbar">
                            <a class="item-button-list" href="<c:url value='/page/partypreedit.htm?id=${account.party.id}'/>"><span>List</span></a>
                            <c:if test='${access.add}'>
                            	<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
                            </c:if>
                        </div>
						
                        <div class="main-box">			
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute='account'>
                            	<table width="100%" style="border:none">
                                <tr>
                                    <td width="24%" align="right"> Company : </td>
                                    <td>
                                        <form:select id="org" path="party" cssClass="combobox-ext" disabled='true'>
                                            <c:if test='${not empty account.party}'>
                                                <form:option value='${account.party.id}' label='${account.party.firstName} ${account.party.lastName} ${account.party.middleName}' />
                                            </c:if>
                                        </form:select>
                                    </td>					
                                </tr>                                
                                <tr>
                                    <td align="right">Bank Account : </td>
                                    <td>
                                        <form:select id='account' path='bankAccount' cssClass='combobox-ext'>
                                            <c:if test='${not empty account.bankAccount}'>
                                                <form:option value='${account.bankAccount.id}' label='${account.bankAccount.code}' />
                                            </c:if>
                                        </form:select>
                                        <a class="item-popup" onclick="openaccount();"  title="Bank Account" />
                                    </td>
                                </tr>
                                <tr>
                                    <td width="24%" align="right">Status : </td>
                                    <td>
                                        <form:radiobutton path='enabled' value='true' label='Active'/>
                                        <form:radiobutton path='enabled' value='false' label='Inactive'/>
                                    </td>					
                                </tr>
                                <tr>
                                    <td width="24%" align="right">Default Account : </td>
                                    <td>
                                        <form:radiobutton path='used' value='true' label='On'/>
                                        <form:radiobutton path='used' value='false' label='Off'/>
                                    </td>					
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
			<span>&copy; 2007 siriusERP v1.0GA</span>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>
</div>
</body>
</html>
