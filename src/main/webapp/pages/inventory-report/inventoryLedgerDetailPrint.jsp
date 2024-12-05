<%@ include file="/common/tld-common.jsp"%>				
        <table cellpadding="0" cellspacing="0" width="100%">
         <tr>
              <td colspan="3"><strong>INVENTORY LEDGER DETAIL</strong></td>
          </tr>
          <tr>
               <td colspan="3">&nbsp;</td>
           </tr>
           <tr>
               <td width="109"><spring:message code="sirius.organization"/> </td>
               <td width="10">&nbsp;:</td>
                <td width="1110"><c:out value='${organization.fullName}'/></td>
           </tr>
           <tr>
               <td width="109"><spring:message code="sirius.facility"/> </td>
               <td width="10">&nbsp;:</td>
                <td width="1110"><c:out value='${facility.name}'/></td>
           </tr>
          <tr>
              <td><spring:message code="sirius.datefrom"/></td>
              <td>&nbsp;:</td>
              <td><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/>&nbsp;<spring:message code="sirius.to"/>&nbsp;<fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/></td>
          </tr>
           <tr>
                <td colspan="3">&nbsp;</td>
           </tr>
          </table>
    <c:forEach items='${reports}' var='report'>
  	   <c:if test="${report.sum > 0 || report.opening > 0 || report.in > 0 || report.out > 0}">
  	      <table border="0" cellspacing="0" width="100%">
  	      <thead>
  	      <tr>
  	          <th width="10%" align="left"><spring:message code="product"/></th>
  	        	<th width="62%" align="left" >&nbsp;:&nbsp;
  	     	    <c:out value='${report.code}'/> - <c:out value='${report.product}'/></th>
  	        	<th width="7%" align="left"><spring:message code="container"/></th>
  	        	<th width="1%">&nbsp;:</th>
  	        	<th width="20%" align="left"><c:out value='${report.container} - ${report.facility}'/></th>
  	      </tr>
	      </c:if>
      <tr>
          <td colspan="5">&nbsp;</td>
      </tr>
      </thead>
      </table>
      <table style="border-top:solid 1px black;" cellpadding="3" cellspacing="0" width="100%">
      <thead>
      <tr>
          	<th width="11%" align="left" style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;">Date</th>
	  	  	<th width="12%" align="left" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" ><spring:message code="sirius.description"/></th>
  			<th width="15%" align="left" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="goodsreceipt.refdoc"/></th>
 	  		<th width="40%" align="left" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><spring:message code="sirius.note"/></th>
  	  		<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.in"/></th>
	  	  	<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.out"/></th>
     	  	<th width="8%" style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;"  align="center"><spring:message code="sirius.qty"/></th>
      </tr>                   
      </thead>
      <tbody>
      <tr>
      		<td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><fmt:formatDate value='${criteria.dateFrom}' pattern='dd-MM-yyyy'/></td>
          	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" colspan='5'><spring:message code="accreport.opening"/></td>
          	<td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${report.opening}' pattern=',##0.00'/></td>
      </tr>
      <c:forEach items='${report.transactions}' var='trx'>
      <tr>
          <td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="left"><fmt:formatDate value='${trx.date}' pattern='dd-MM-yyyy'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="left"><c:out value='${trx.reftype}'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="left"><a href="<c:url value='/page/${trx.refuri}?id=${trx.refid}'/>" ><span><c:out value='${trx.reference}'/></span></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="left"><c:out value='${trx.note}'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${trx.in}' pattern=',##0.00'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${trx.out}' pattern=',##0.00'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${trx.sum}' pattern=',##0.00'/></td>
      </tr>
      </c:forEach>
        <tr>
      	<td style="border-left:solid 1px #000000;border-bottom:solid 1px #000000;border-right:solid 1px #000000;"><fmt:formatDate value='${criteria.dateTo}' pattern='dd-MM-yyyy'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" colspan='3'><spring:message code="accreport.closing"/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${report.in}' pattern=',##0.00'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${report.out}' pattern=',##0.00'/></td>
          <td style="border-bottom:solid 1px #000000;border-right:solid 1px #000000;" align="right"><fmt:formatNumber value='${report.sum}' pattern=',##0.00'/></td>
      </tr>
      <tr>
          <td colspan="7">&nbsp;</td>
      </tr>
      </tbody>
      <tfoot>
      <tr>
          <td align="right" colspan='4'><spring:message code="sirius.qty"/>&nbsp;<spring:message code="product.reserved"/> : </td>
          <td align="right"><fmt:formatNumber value='${report.reserved}' pattern=',##0.00'/></td>
          <td align="right">Total : </td>
          <td align="right"><fmt:formatNumber value='${report.sum - report.reserved}' pattern=',##0.00'/></td>
      </tr>
      <tr>
          <td colspan="7">&nbsp;</td>
      </tr>
      </tfoot>
      
      </table>
</c:forEach>