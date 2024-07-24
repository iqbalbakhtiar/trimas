<style type="text/css" media="screen">
	.dojoFloatingPaneClient
	{
		 background-color:white;
		 border-color:white;
	}
</style>	
<script type="text/javascript">
	$(function(){
		$('.floating').click(function(e){
			$('#sub').remove();
			$('#toggle').click();
		});
		
		$('.item-button-save').click(function(e){
			var $dialog_confirm = $('<div title="DO Loading Confirmation"></div>')
			.html('You have selected '+($('#sub tr').length)+' item(s), proceed ?');

			$dialog_confirm.dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Yes": function()
					{
						$( this ).dialog( "close" );
						
						document.addForm.action="<c:url value='/page/doloadingadd.htm'/>";
						document.addForm.submit();
						
						$('#toggle').click();
					},
					"No": function()
					{
						$( this ).dialog( "close" );
					}
				}
			});
		});
	});
</script>
<div dojoType="FloatingPane" id="review" title="Review" constrainToContainer="true" style="width: 88%; height: 80%; left: 2%; display:none;" toggle="explode" bg>
	<div class="toolbar">
		<a class="item-button-back floating" ><span>Back</span></a>
		<c:if test='${access.add}'>
			<a class="item-button-save"><span>Save</span></a>
		</c:if>
	</div>
	<br/>
	<div id="main" class="main-box">
		<br/>
		<div id="line" dojoType="ContentPane" label="Line Item" class="tab-pages" refreshOnShow="true">
			<table id="itemView" width="100%" cellpadding="0" cellspacing="0" class="table-list">
				<thead>
				<tr>
					<th width="25%">Product</th>
					<th width="8%">UoM</th>
					<th width="12%">Quantity</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
