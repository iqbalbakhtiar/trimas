<div dojoType="FloatingPane" id="create" title='<spring:message code="directsalesorder"/>' constrainToContainer="true" style="width: 30%; height: 100px; left: 300px; display:none;" toggle="explode" bg>
	<form id="createForm" name="createForm" method="post">
		<fieldset>
			<table width="100%" cellspacing="0" cellpadding="0" align="right">
			<tr>
			    <td align="right"><spring:message code="sirius.month"/> :</td>
			    <td>
					<select id="month" name="month">
						<c:forEach items="${months}" var="month">
							<option value="${month}"><spring:message code="month.${fn:toLowerCase(month)}"/></option>
						</c:forEach>
					</select>
					<select id="year" name="year">
					    <c:forEach var="year" begin="${years-10}" end="${years}">
						<option value="${(years*2)-year-10}">${(years*2)-year-10}</option>
					    </c:forEach>
					</select>
			    </td>
				<td>
					<input type="button" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.proceed"/>' onclick="generate();"/>
				</td>
			</tr>
			</table>
		</fieldset>
	</form>
</div>
<script type="text/javascript">
	function generate()
	{
		const confirmDialog = $('<div><spring:message code="notif.rejournal"/> ?</div>').dialog(
		{
			autoOpen: false, title: 'Rejournal', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');

					var url = "<c:url value='/page/goodsreceiptrecalculatejournal.htm'/>";

					document.createForm.action = url;
					document.createForm.submit();	
				},
				'<spring:message code="sirius.no"/>': function() {
					$(this).dialog('close');
				}
			}
		});

		confirmDialog.dialog('open');
	}
</script>