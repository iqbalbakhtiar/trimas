<%@ include file="/common/tld-common.jsp"%>
	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">				
						<tr>
							<th><div style="WIDTH:40px;"></div></th>
							<th>User ID</th>
						  	<th>Employee Name</th>
						  	<th>Role Name</th>
						</tr>
						<c:forEach items="${users}" var="user">
						<tr>
							<td><c:out value="'${user.username}"></c:out></td>
						  	<td>${user.person.firstName} ${user.person.middleName} ${user.person.lastName}</td>
						  	<td>${user.role.name}</td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
						</table>