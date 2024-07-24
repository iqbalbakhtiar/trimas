<font id="welcome" color="#990000" style="font-family:Arial, Helvetica, sans-serif; font-size:11px;">
<spring:message code="sirius.wide.welcome"/> 
	&nbsp;<a href="<c:url value='/page/userprofilepreedit.htm'/>"><strong>${person.fullName}</strong></a>
	| <fmt:formatDate value="${now}" pattern="dd MMMM yyyy"/> &nbsp;<span id="theClock"></span><br/>
	<c:out value="${activerole}"/> | <c:out value="${profile.organization.fullName}"/>	
</font>
