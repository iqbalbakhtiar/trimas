<%@ include file="/common/tld-common.jsp"%>	
<table width="100%" >
<tr>
  <td colspan="3" class="pageTitle"><strong>${reportTitle}</strong></td>
</tr>
<tr>
  <td colspan="3" class="pageTitle">&nbsp;</td>
</tr>
<tr>
  <td width="15%">Period</td>
	<td width="10%">:</td>
	<td width="75%">
		<c:forEach items='${criteria.accountingPeriods}' var='period'>
    		<c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${period.name}"/>
    	</c:forEach>
	</td>
</tr>
<tr >			  
	<td>Company</td>
	<td>:</td>
	<td>
		<c:forEach items='${criteria.organizations}' var='org'>
    		<c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${org.fullName}"/>
    	</c:forEach>
   	</td>
</tr>
 </table>
  <br/>
  <table width="100%;" cellpadding="4" cellspacing="0" class="report-table">
  <c:set var='balance' value='0'/>
<c:forEach items='${adapter.grouped}' var='grup'>
<c:if test="${not empty grup.head}">
    <tr>
      <td width="40%">
        <strong>${grup.head}</strong>
      </td>
      <td width="20%" align="right">
        &nbsp;
      </td>
       <td width="20%" align="right">
        &nbsp;
      </td>
      <td width="20%" align="right">
        &nbsp;
      </td>
    </tr>  
    <tr>
          <td colspan="4">&nbsp;</td>
    </tr>                                    
  </c:if>
    <c:forEach items='${grup.parents}' var='parent'>
      <tr>
        <td>
        <c:if test="${not empty parent.value.accounts}">
           <strong><c:out value='${parent.value.parent.code} - ${parent.value.parent.name}'/><strong>
          </c:if>
        </td>
      </tr>
        <c:forEach items='${parent.value.accounts}' var='account'>
        <tr>
            <td>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account.value.account.code} - '/>
                    ${account.value.account.name}
            </td>
            <td align="right">
                <c:if test='${account.value.amount> 0}'>
                    <fmt:formatNumber value='${account.value.amount}' pattern=',##0.00'/>
                </c:if>
                <c:if test='${account.value.amount < 0}'>
                    (<fmt:formatNumber value='${account.value.amount * -1}' pattern=',##0.00'/>)
                </c:if>
           </td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
        </tr>
        </c:forEach>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td align="right"><strong>
            <c:if test='${parent.value.sum> 0}'>
              <fmt:formatNumber value='${parent.value.sum}' pattern=',##0.00'/>
           </c:if>
           <c:if test='${parent.value.sum < 0}'>
              (<fmt:formatNumber value='${parent.value.sum * -1}' pattern=',##0.00'/>)
           </c:if></strong>
          </td>
          <td>&nbsp;</td>
        </tr>
    </c:forEach>
    <c:if test="${not empty grup.foot}">
    <tr>
     <c:if test="${grup.foot=='Total Assets'}">
      <td>&nbsp;</td>
      <td colspan='3'><hr/></td>
    </c:if>
    <c:if test="${grup.foot=='Total Liabilities'}">
      <td>&nbsp;</td>
      <td colspan='2'><hr/></td>
       <td>&nbsp;</td>
    </c:if>                   
    </tr>
     <c:if test="${grup.foot=='Total Equities'}">
     <tr>
      <td colspan="4">&nbsp;</td>
     </tr>
    <tr height="11">
      <td align="right"><strong>Net Income :</strong></td>
      <td>&nbsp;</td>
      <td align="right">
          <strong>
            <c:if test='${adapter.grand > 0}'>
                <fmt:formatNumber value='${adapter.grand}' pattern=',##0.00'/>
            </c:if>
            <c:if test='${adapter.grand < 0}'>
                (<fmt:formatNumber value='${adapter.grand * -1}' pattern=',##0.00'/>)
            </c:if>
          </strong>
      </td>
       <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td colspan='2'><hr/></td>
       <td>&nbsp;</td>
    </tr>
    </c:if>
    <tr height="11">
      <td align="right"><strong>${grup.foot} : </strong></td>
        <td>&nbsp;</td>
        <td align="right">
          <c:if test="${grup.foot=='Total Liabilities'}">
          <strong>
              <c:if test='${grup.total < 0}'>
                  <fmt:formatNumber value='${grup.total * -1}' pattern=',##0.00'/>
              </c:if>
              <c:if test='${grup.total > 0}'>
                  (<fmt:formatNumber value='${grup.total}' pattern=',##0.00'/>)
              </c:if>
              <c:set var='balance' value='${balance + grup.total}'/>
          </strong>
          </c:if>
          <c:if test="${grup.foot=='Total Equities'}">
          <strong>
              <c:if test='${grup.total < 0}'>
                  <fmt:formatNumber value='${(grup.total - adapter.grand) * -1}' pattern=',##0.00'/>
              </c:if>
              <c:if test='${grup.total > 0}'>
                  (<fmt:formatNumber value='${grup.total - adapter.grand}' pattern=',##0.00'/>)
              </c:if>
              <c:set var='balance' value='${balance + grup.total - adapter.grand}'/>
          </strong>
          </c:if>
        </td>
         <td align="right">
         <c:if test="${grup.foot=='Total Assets'}">
          <strong>
              <c:if test='${grup.total > 0}'>
                  <fmt:formatNumber value='${grup.total}' pattern=',##0.00'/>
              </c:if>
              <c:if test='${grup.total < 0}'>
                  (<fmt:formatNumber value='${grup.total * -1}' pattern=',##0.00'/>)
              </c:if>
          </strong>
          </c:if>
         </td>
    </tr>
   <tr>
    <c:if test="${grup.foot=='Total Assets'}">
      <td colspan='4'><hr/></td>
    </c:if>
    <c:if test="${grup.foot=='Total Liabilities'}">
      <td colspan="4">&nbsp;</td>
    </c:if>                              
    </tr>
  </c:if>     
</c:forEach> 
<tr>
      <td>&nbsp;</td>
      <td colspan='3'><hr/></td>
</tr>
<tr>
   <td align="right"><strong>Total Liabilities + Equities : </strong></td>
    <td colspan='2'>&nbsp;</td>
    <td align="right">
    <strong>
            <c:if test='${balance < 0}'>
                <fmt:formatNumber value='${balance * -1}' pattern=',##0.00'/>
            </c:if>
            <c:if test='${balance > 0}'>
                (<fmt:formatNumber value='${balance}' pattern=',##0.00'/>)
            </c:if>
     </strong>
     </td>
 </tr>
</table>