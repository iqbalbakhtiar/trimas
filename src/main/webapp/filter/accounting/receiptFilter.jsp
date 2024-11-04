<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 250px; left: 50%; top: 50%; transform: translate(-50%, -50%); display:none;" toggle="explode" bg>
  <form id="filterForm" name="filterForm" method="post">
    <table width="100%" cellspacing="0" cellpadding="1" align="right">
      <tr>
        <td width="35%" align="right"><spring:message code="sirius.code"/>&nbsp;</td>
        <td width="5%" align="center">:</td>
        <td width="60%"><input name="code" id="code" class="inputbox" value="${filterCriteria.code}"/></td>
      </tr>
      <tr>
        <td align="right"><spring:message code="sirius.datefrom"/> &nbsp;</td>
        <td align="center">:</td>
        <td>
          <input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
          &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
          <input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>
        </td>
      </tr>
      <tr>
        <td align="right"><spring:message code="customer"/>&nbsp;</td>
        <td align="center">:</td>
        <td ><input type="text" id="customerName" name="customerName" class="inputbox" value="${filterCriteria.customerName}"/></td>
      </tr>
      <tr>
        <td align="right"><spring:message code="sirius.status"/>&nbsp;</td>
        <td align="center">:</td>
        <td >
          <select id="type" name="type">
            <option value=""><spring:message code="sirius.all"/></option>
            <option value="CASH" ${not empty filterCriteria.type and filterCriteria.type eq 'CASH' ? "selected" : ""}><spring:message code="receipt.payment.cash"/></option>
            <option value="TRANSFER" ${not empty filterCriteria.type and filterCriteria.type eq 'TRANSFER' ? "selected" : ""}><spring:message code="receipt.payment.transfer"/></option>
          </select>
        </td>
      </tr>
      <tr><td colspan="3">&nbsp;</td></tr>
      <tr>
        <td colspan="2">&nbsp;</td>
        <td align="left">
          <%@ include file="/common/filter.jsp"%>
        </td>
      </tr>
    </table>
  </form>
</div>
