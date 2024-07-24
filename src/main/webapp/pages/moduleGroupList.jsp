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
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
	</style>
	<%@ include file="/common/sirius-header.jsp"%>
	<script type="text/javascript">
		function deletes(id)
		{
			var confirmation =confirm("Do you really want to delete Module Group?");
			if(confirmation==true)
			{
				var url = "<c:url value='/page/modulegroupdelete.htm'/>?id="+id;
				window.location=url;
			}
		}
		
		function displayGroup(value)
		{
			var listPane = dojo.widget.getWidgetById("listPane");
			listPane.setUrl("page/modulegrouppreedit.htm?id="+value);
		}
		
		function displayContent(value)
		{
			var listPane = dojo.widget.getWidgetById("listPane");
			listPane.setUrl(value);
		}
		
		function save()
		{
			var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Module (Group)',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		
			$value = $('#editForm').attr('action');
			
			$.ajax({
				url:"<c:url value='"+$value+"'/>",
				data:$('#editForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							displayContent(json.value);
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});	
		}
		
		$(document).ready(function() {
			$(".subgroups").each(function() {
				$groups = $(this).attr('id').split('>');
				$value = $(this).attr('value');

				$parent = $groups[0].trim().replace(/ /g,"_");
				$group = $groups[1].trim().replace(/ /g,"_");
				
				$Objpr = $('#'+$parent);
				if(!$Objpr.length)
					$Objpr = genGroup($parent, $groups[0].trim().substring(2), $value)
				
				$Objgr = $('#'+$group);
				if(!$Objgr.length)
					$Objgr = genGroup($group, $groups[1].trim().substring(2), $value)
					
				$Objpr.append($Objgr);
			});
			
			function genGroup(id, title, value)
			{
				$div = $('<div/>');
				$div.attr('dojoType','TreeNode');
				$div.attr('id',id);
				$div.attr('title',title);
				$div.attr('childIconSrc','assets/icons/group.gif');
				$div.attr('onTitleClick','displayGroup('+value+')');

				return $div.get(0);
			}
		});
	</script>	
	<!-- meta tags here -->
</head>
<!-- BEGIN OF BODY -->
<body>
<!-- top menu -->


<!-- rounded -->
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
						<div dojoType="SplitContainer" style="width: 100%; height: 430px;" orientation="horizontal" sizerWidth="5" activeSizing="0"	layoutAlign="client">
								<div style="display:none">
									<table>
										<c:forEach items="${alls}" var="sub">
											<tbody class="subgroups" value="${sub.id}" id="${sub.fullName}"/>
										</c:forEach>
									</table>
								</div>
								<div id="treePane" dojoType="Tree" widgetId="treePane" toggle="wipe" toggleDuration="500" sizeMin="20" sizeShare="3">
									<div id="G_Module_Group" dojoType="TreeNode" title="Module Group" expandLevel="2" onTitleClick='window.location = "page/modulegroupview.htm";' childIconSrc="assets/icons/coa-parent.gif">
									</div>
								</div>
								<div id="listPane" dojoType="ContentPane" style="padding: 5px; width:100% ; height:430px;overflow:scroll" refreshOnShow="true" doLayout="true">
								<div class="toolbar">
										<a class="item-button-add-gl-child" href="javascript:displayContent('page/modulegrouppreadd.htm');"><span>New Group</span></a>
									</div>					  
									<div class="main-box" style="height: 370px;">
										<form id="deleteForm" name="deleteForm" method="post">
											<table width="968" cellpadding="0" cellspacing="0" class="table-list" align="center">
											<tr>
												<th width="41">&nbsp;</th>
											  <th width="211">Name</th>
											  <th width="714">Note</th>
											</tr>
											<c:forEach items="${groups}" var="group">
											<tr>
												<td>
													<a href="javascript:showDialog('<c:url value='/page/modulegroupdelete.htm?id=${group.id}'/>')"><img src="<c:url value='/images/delete.gif'/>" width="16" height="16" align="left" border="0"></a>
												</td>
												<td>${group.name}</td>
												<td>${group.note}</td>
											</tr>
											</c:forEach>
											<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
				  							</table>
				  						</form>
									</div>
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
<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
</html>