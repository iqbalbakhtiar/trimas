<%@ include file="/common/tld-common.jsp"%>		
				                <table cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td colspan="3"><strong>INVENTORY LEDGER SUMMARY</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="109">Company </td>
                                        <td width="10">&nbsp;:</td>
                                      	<td width="1110"><c:out value='${organization.fullName}'/></td>
                                    </tr>
                                    <tr>
                                        <td>Period </td>
                                        <td>&nbsp;:</td>
                                        <td><c:out value='${period}'/></td>
                                    </tr>
                                     <tr>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                </table>
                                <c:forEach items='${reports}' var='repo'>
                                <table border="0" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th width="9%" align="left">Warehouse </th>
                                  	<th width="1%">&nbsp;:</th>
                                  	<th width="90%" align="left"><c:out value='${repo.facilityName}'/></th>
                                </tr>
                                </thead>
                                </table>
                                <table style="border-top:solid 1px black;" cellpadding="3" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                	<th width="13%" style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"> Grid</th>
                                    <th width="13%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">Container</th>
                               	  	<th width="9%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">Code</th>
                               	  	<th width="23%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">Name</th>
                       	  	  	  	<th width="9%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">UoM</th>
                           	  	  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">Opening</th>
                           	  	  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">In</th>
                                  	<th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">Out</th>
                                    <th width="6%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right">Closing</th>
                                </tr>                   
                                </thead>
                                <tbody>
                                <c:forEach items='${repo.adapters}' var='adapter'>
	                                <c:if test='${adapter.quantity > 0 || adapter.in > 0 || adapter.out > 0 || adapter.sum > 0}'>
		                                <tr>
		                                	<td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><c:out value='${adapter.gridName}'/></td>
		                                	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><c:out value='${adapter.containerName}'/></td>
	                                        </td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">&nbsp;<c:out value='${adapter.productCode}'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;">&nbsp;<c:out value='${adapter.productName}'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="center"><c:out value='${adapter.uom}'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.quantity}' pattern=',##0.00'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.in}' pattern=',##0.00'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.out}' pattern=',##0.00'/></td>
	                                        <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${adapter.sum}' pattern=',##0.00'/></td>
		                                </tr>
	                               </c:if>
                                </c:forEach>
                                <tr>
                                    <td colspan="9">&nbsp;</td>
                                </tr>
                                </tbody>
                                </table>
                            </c:forEach>