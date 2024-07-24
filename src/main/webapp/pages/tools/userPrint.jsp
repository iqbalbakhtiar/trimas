<%@ include file="/common/tld-common.jsp"%>
	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">				
						<tr>
							<th width="4%">&nbsp;</th>
							<th><span><spring:message code="user.id"/></th>
						  	<th><span><spring:message code="user.name"/></th>
						  	<th><span><spring:message code="user.role"/></th>
						</tr>
						<c:forEach items="${users}" var="user">
						<tr>
							<td class="tools">
						  		<a class="item-button-edit" href="<c:url value='/page/userpreedit.htm?id=${user.id}'/>"><span><span><spring:message code="sirius.edit"/></span></a>
						  		<a class="item-button-delete" id="tes" name="tes" href="javascript:showDialog('<c:url value='/page/userdelete.htm?id=${user.id}'/>');" title="<span><spring:message code="sirius.delete"/>"><span><span><spring:message code="sirius.delete"/></span></a>
							</td>
							<td>${user.username}</td>
						  	<td>${user.person.fullName}</td>
						  	<td>${user.role.name}</td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
						</table>