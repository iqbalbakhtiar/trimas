<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<c:if test="${access.delete && deleteable}">
		<a class="item-button-delete" href="javascript:window.location = 'page/glaccountdelete.htm?id=${account.id}';"><span><spring:message code="sirius.delete"/></span></a>
	</c:if>
	<a class="item-button-save" href="javascript:document.editForm.submit();"><span><spring:message code="sirius.save"/></span></a>
	<c:if test="${account.level == 'ACCOUNTGROUP'}">
		<a class="item-button-add-gl-child" href="javascript:displayContent('page/glaccountpreadd.htm?coa=${chartOfAccount}&parent=${account.id}');"><span><spring:message code="glaccount.new"/></span></a>
	</c:if>
</div>

<div class="main-box" style="height:420px;overflow:scroll">
	<sesform:form id="editForm" name="editForm" action="page/glaccountedit.htm" method="post" modelAttribute="account">
		<table style="border:none">
		<tr>
			<td width="150"><div align="right"><spring:message code="glaccount.code"/> :</div></td>
			<td width="378"><form:input path="code" size="10"/></td>
			<tr>
				<td><div align="right"><spring:message code="glaccount.name"/> :</div></td>
				<td><form:input path="name" size="50"/></td>
			</tr>
            <tr>
				<td><div align="right"><spring:message code="glaccount.alias"/> :</div></td>
				<td><form:input path="alias" size="50"/></td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.cashtype"/> :</div></td>
				<td>
					<form:select path="cashType">
						<form:option value="CASH"><spring:message code="glaccount.cash"/></form:option>
						<form:option value="NONCASH"><spring:message code="glaccount.noncash"/></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.closingtype"/> :</div></td>
				<td>
					<form:select path="closingType">
						<form:option value="TEMPORARY"><spring:message code="glaccount.temporary"/></form:option>
						<form:option value="PERMANENT"><spring:message code="glaccount.permanent"/></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.postingtype"/> :</div></td>
				<td>
					<form:select path="postingType">
						<form:option value="DEBET"><spring:message code="glaccount.debet"/></form:option>
						<form:option value="CREDIT"><spring:message code="glaccount.credit"/></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.level"/> :</div></td>
				<td>
					<form:select path="level">
						<form:option value="ACCOUNTGROUP"><spring:message code="glaccount.accountgroup"/></form:option>
						<form:option value="ACCOUNT"><spring:message code="glaccount.account"/></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.type"/> :</div></td>
				<td>
					<form:select path="accountType">
						<form:options items="${types}" itemLabel="name" itemValue="id"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.parent"/> :</div></td>
				<td>
					<select class="combobox-ext">
						<option>${account.parent.code} ${account.parent.name}</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="coa"/> :</div></td>
				<td>
					<select class="combobox-ext">
						<option>${account.coa.code} ${account.coa.name}</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.nullwarning"/> :</div></td>
				<td>
					<form:radiobutton value="true" path="warning"/><spring:message code="sirius.yes"/>
					<form:radiobutton value="false" path="warning"/><spring:message code="sirius.no"/>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="glaccount.note"/> :</div></td>
				<td><form:textarea path="note" cols="50" rows="6"/> </td>
			</tr>
			</table>
	</sesform:form>
</div>