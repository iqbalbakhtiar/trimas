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
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp" %>
    	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<script type="text/javascript">
		
						
		
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
							<a class="item-button-list" href="<c:url value='/page/gridroleview.htm'/>"><span><spring:message code='sirius.list'/></span></a>
							<c:if test='${access.add}'>
                            	<a class="item-button-save" style="cursor: pointer;"><span><spring:message code='sirius.save'/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="role_edit">
								<table width="100%" style="border:none">
                                <tr>
									<td width="20%" nowrap="nowrap" align="right"><spring:message code='sirius.name'/> :</td>
								  <td width="80%"><form:input path='name' size="48"/></td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code='billing.organization'/> :</td>
									<td>
										<form:select id="org" path="organization" cssClass="combobox-ext">
                                           	<c:if test='${not empty role_edit.organization}'>
                                               	<form:option value='${role_edit.organization.id}' label='${role_edit.organization.fullName}' />
                                            </c:if>
										</form:select>
									</td>
								</tr>
							</table>
							<br/>
                             <div id="detail" dojoType="TabContainer" style="width:100% ; height: 450px;">
                                    <div id="sourceItem" dojoType="ContentPane" label="<spring:message code='grid.source'/>" class="tab-pages" refreshOnShow="true">
			                            <div class="toolbar-clean">
                                            <a class="item-button-new" value="srcs" ><span><spring:message code='sirius.row.new'/></span></a>
                                            <a class="item-button-delete" value="srcs" ><span><spring:message code='sirius.row.delete'/></span></a>
										</div>
			                            <table class="table-list" cellspacing="0" cellpadding="0" align="center" width="100%">
			                            <thead>
			                            <tr>
			                              	<th width="2%"><input id ="check" kind="srcs" class="checkall" type="checkbox"/></th>
			                              	<th nowrap="nowrap" width="13%"><spring:message code='grid.grid.source'/></th>
			                              	<th nowrap="nowrap" width="17%"><spring:message code='grid.facility.source'/></th>
			                            </tr>
			                            </thead>
									    	<c:forEach items='${role_edit.sources}' var='detail' varStatus='status'>
			                           		 <tr>
				                            	<td class="tools">
													<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/gridroledetaildelete.htm?role=${role_edit.id}&id=${detail.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
										   		</td>
				                           		<td >
				                           			<form:select id="source${status.index}" path="sources[${status.index}].source" cssClass="combobox">
                                                        <option value="${detail.source.id}"><c:out value='${detail.source.name}'/></option>
                                                     </form:select>&nbsp;<a class="item-popup" onclick="javascript:opengrid('source${status.index}');"  title="Grid Source" />
				                            	</td>
				                            	<td >
				                           			<select id="fac${status.index}" class="combobox-ext">
                                                        <option value="${detail.source.facility.id}"><c:out value='${detail.source.facility.name}'/></option>
                                                     </select>
				                            	</td>
									    	</tr>
									    	</c:forEach>
									    <tbody id="srcs">
			                            </tbody>
			                            <tfoot>
			                            <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
			                            </tfoot>
			                            </table>
			                        </div>
			                         <div id="destinationItem" dojoType="ContentPane" label="<spring:message code='grid.destination'/>" class="tab-pages" refreshOnShow="true">
			                            <div class="toolbar-clean">
                                            <a class="item-button-new" value="dests" ><span><spring:message code='sirius.row.new'/></span></a>
                                            <a class="item-button-delete" value="dests" ><span><spring:message code='sirius.row.delete'/></span></a>
										</div>
			                            <table class="table-list" cellspacing="0" cellpadding="0" align="center" width="100%">
			                            <thead>
			                            <tr>
			                              	<th width="2%"><input id ="check" kind="dests" class="checkall" type="checkbox"/></th>
			                                <th nowrap="nowrap" width="13%"><spring:message code='grid.grid.destination'/></th>
			                                <th nowrap="nowrap" width="17%"><spring:message code='grid.facility.destination'/></th>
			                            </tr>
			                            </thead>
									    	<c:forEach items='${role_edit.destinations}' var='detail' varStatus='status'>
			                           		 <tr>
				                            	<td class="tools">
													<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/gridroledetaildelete.htm?role=${role_edit.id}&id=${detail.id}'/>');" title="Delete"><span>Delete</span></a>
										   		</td>
				                            	<td >
				                           			<form:select id="destination${status.index}" path="destinations[${status.index}].destination" cssClass="combobox">
                                                        <option value="${detail.destination.id}"><c:out value='${detail.destination.name}'/></option>
                                                     </form:select>&nbsp;<a class="item-popup" onclick="javascript:opengrid('destination${status.index}');"  title="Grid Destination" />
				                            	</td>
				                            	<td >
				                           			<select id="fac${status.index}" class="combobox-ext">
                                                        <option value="${detail.destination.facility.id}"><c:out value='${detail.destination.facility.name}'/></option>
                                                     </select>
				                            	</td>
									    	</tr>
									    	</c:forEach>
									    <tbody id="dests">
			                            </tbody>
			                            <tfoot>
			                            <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
			                            </tfoot>
			                            </table>
			                        </div>
			                     </div>
						  </sesform:form>
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
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Grid Role',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});	
		$('.item-button-save').click(function(){
			
			if(validation())
			{
				$.ajax({
					url:"<c:url value='/page/gridroleedit.htm'/>",
					data:$('#addForm').serialize(),	
					type : 'POST',
					dataType : 'json',
					beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$dialog.dialog('close');
								window.location="<c:url value='/page/gridroleview.htm'/>";
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					},
					error : function(xhr) {
						console.log(xhr.responseText);
					},
				});
			}
			
		});

		$index = 0;

		$('.item-button-new').click(function () {

			$org = $('#org').val();
			$value =$(this).attr('value');

			if(!$org) {
				alert('Please select Organization first !');
				return;
			}

			$tbody = $('#'+$value);
			$tr = $('<tr/>');
			$td1 = $('<td/>');
			$td2 = $('<td/>');
			$td3 = $('<td/>');

			$td1.append(getCheck($index, $value));
			$td2.append(getSelect($index,$value,'',''));
			$td2.append('&nbsp;');
			$td2.append(getImg($value,$index));
			$td3.append(getSelect($index,'facs','',''));
			
			$tr.append($td1);
			$tr.append($td2);
			$tr.append($td3);
	
			$tbody.append($tr);

			$index++;
		});
	});

	function validation()
	{
		$status = true;

		if(!$('#org').val())
		{
			alert('Please select Organization first !!!');
			return $status = false;
		}
		
		return $status;
	}

	function opengrid(target, index)
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupgridview.htm?target='/>"+target+"&organization="+org.value+"&index="+index);
	}

	function getImg(target, idx)
	{
		$img = $('<img/>');
		$img.attr('src',"assets/icons/list_extensions.gif");
		$img.attr('title',"Grid");
		$img.attr('style',"cursor:pointer;");
		$img.attr('index',idx);
		$img.click(function(){
			$idx = $(this).attr('index');
			
			opengrid(target.slice(0, -1)+'['+$idx+']', $idx);
		});

		return $img;
	}

	function getSelect(idx, name, value, label)
	{
		$select = $('<select/>');
		$select.attr('id', name.slice(0, -1)+"["+idx+"]");
		$select.attr('name', name);
		
		$opt = $('<option></option>');
		$opt.attr('value',value);
		$opt.html(label);
		
		$opt.appendTo($select);

		return $select;
	}
	
	function getCheck(index, type)
	{
		$check = $('<input/>');
		$check.attr('type',"checkbox");
		$check.attr('index',index);
		$check.attr('class',type);
		$check.attr('value',false);
		
		return $check;
	}

	$('.checkall').click(function () {
		$('.'+$(this).attr('kind')).prop("checked", this.checked).trigger("change");
	});
	
	$('.item-button-delete').click(function () {
		$('.'+$(this).attr('value')).each(function(){
			if(this.checked){
			    this.checked = false;

			    $(this).parent().parent().remove();
			}
		});
		
		$('[kind='+$(this).attr('value')+']').prop("checked", false);    
	});
</script>