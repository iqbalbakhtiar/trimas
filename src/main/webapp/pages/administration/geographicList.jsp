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
	<%@ include file="/filter/administration/geographicFilter.jsp"%>
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
			<td width="5%"></td>
			<td width="35%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>
	</div>
	
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
					
						<h1 class="page-title">${pageTitle}</h1>
				  		
                        <div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td width="35%" height="30" align="left" valign="middle">
									<div class="toolbar-clean">
										<c:if test='${access.add}'>
						  					<a class="item-button-new" href="<c:url value='/page/geographicpreadd.htm' />"><span><spring:message code="geographic.new"/></span></a>
	                           		 	</c:if>
							  			<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
										</div>
							   		</div>
								</td>
								<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
								</tr>
							</table>
					  	</div>
				  		<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
				  		<tr>
				  			<th width="1%"><div style="width:45px"></div></th>
				  			<th width="10%"><spring:message code="geographic.code"/></th>
		  			  	  	<th width="25%"><spring:message code="geographic.name"/></th>
		  			  	 	<th width="10%"><spring:message code="geographic.type"/></th>
                            <th width="50%"><spring:message code="geographic.parent"/></th>
				  		</tr>
						<c:forEach items="${geographics}" var="geo">
						<tr>
				  			<td class="tools">
                            	<c:if test='${access.edit}'>
				  					<a class="item-button-edit" href="<c:url value='/page/geographicpreedit.htm?id=${geo.geographic.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
                                </c:if>
                                <c:if test='${access.delete}'>
				  					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/geographicdelete.htm?id=${geo.geographic.id}'/>', '<spring:message code="notif.delete"/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
                                </c:if>
				  			</td>
				  			<td nowrap="nowrap">${geo.geographic.code}</td> 
							<td nowrap="nowrap">${geo.geographic.name}</td>
							<td nowrap="nowrap">
								<c:if test="${geo.geographic.geographicType.name == 'Country'}"><spring:message code="geographic.country"/></c:if>
								<c:if test="${geo.geographic.geographicType.name == 'Province'}"><spring:message code="geographic.province"/></c:if>
								<c:if test="${geo.geographic.geographicType.name == 'City'}"><spring:message code="geographic.city"/></c:if>
								<c:if test="${geo.geographic.geographicType.name == 'District'}"><spring:message code="geographic.district"/></c:if>
							</td>
                            <td>${geo.geographic.parent.name}</td>
				  		</tr>
				  		</c:forEach>
				  		<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				  		</table>
                    	<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
						<tr>
							<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>