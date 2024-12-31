<%@ include file="/common/tld-common.jsp"%>			

					<table width="481">
                        <tr>
                            <td width="100"><spring:message code='sirius.period'/></td>
                            <td width="10">:</td>
                            <td width="355">
                                <c:forEach items='${criteria.accountingPeriods}' var='period'>
                                    <c:out value="${period.name},"/>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>			  
                            <td><spring:message code='organization'/></td>
                            <td>:</td>
                            <td>
                                <c:forEach items='${criteria.organizations}' var='org'>
                                   <c:out value="${org.firstName} ${org.middleName} ${org.lastName},"/>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>			  
                            <td><spring:message code='currencymanagement'/></td>
                            <td>:</td>
                            <td><c:out value='${currency.symbol}'/></td>
                        </tr>
                        </table>
                        <div class="clears">&nbsp;</div>
                        <table width="100%" cellpadding="3" cellspacing="0">
                        <thead>
                        <tr>
                            <th width="5%" align="center"><spring:message code='work.account'/></th>
                       	  	<th width="1%" align="center">&nbsp;</th>	
               	  	  	  	<th align="center" colspan="2"><spring:message code='accreport.referenceno'/></th>
                          	<th width="15%" align="right"><spring:message code='sirius.debit'/></th>	
                          	<th width="15%" align="right"><spring:message code='sirius.credit'/></th>
                          	<th width="15%" align="right"><spring:message code='accreport.balance'/> </th>
                        </tr>
                        </thead>
                        
                        <c:forEach items="${reports}" var="report" >
                        
                        <c:if test="${adapter.accounts[0] == report.account.id}">
	                        <c:set var='closingdebet' value='${0}'/>
	                        <c:set var='closingcredit' value='${0}'/>
	                        <c:set var='name' value='${report.openingdebet-report.openingcredit}'/>
	                        <tr>
	                            <td colspan="7" valign="top"><strong><c:out value='${report.account.code} - ${report.account.name}'/></strong></td>
	                        </tr>
	                        <tr>
	                            <td colspan="2">&nbsp;</td>
	                            <td colspan="2" valign="top"><strong><spring:message code='accreport.opening'/></strong></td>	
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingdebet}"  groupingUsed='true'  pattern=',##0.00'/></strong></td>	
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingcredit}"  groupingUsed='true'  pattern=',##0.00'/></strong></td>
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingdebet - report.openingcredit}" groupingUsed='true' pattern=',##0.00'/></strong></td>
	                        </tr>
	                        <c:forEach items='${report.entrys}' var='entry'>
	                        <tr>
	                            <td  colspan="2" align="left" valign="top">	
	                                &nbsp;<fmt:formatDate value="${entry.journalEntry.entryDate}" pattern="dd-MM-yyyy"/>
	                            </td>
	                            <td width="15%"  valign="top">	
	                                &nbsp;
	                               <a href="<c:url value='/page/journalentrypreview.htm?id=${entry.journalEntry.id}'/>"><c:out value="${entry.journalEntry.code}" /></a>
	                          	</td>
	                          	<td width="34%"  valign="top"><c:out value="${entry.note}" /></td>
								<td   align="right">
	                                <c:if test="${entry.postingType == 'DEBET'}">
	                                    <fmt:formatNumber value="${entry.amount}"  groupingUsed='true'  pattern=',##0.00'/>
	
	                                    <c:set var='name' value='${name+entry.amount}'/>
	                                    <c:set var='closingdebet' value='${closingdebet+entry.amount}'/>
	                                </c:if>    
	                            </td>
	                            <td   align="right" valign="top">
	                                <c:if test="${entry.postingType == 'CREDIT'}">
	                                  <fmt:formatNumber value="${entry.amount}" groupingUsed='true' pattern=',##0.00'/>
	                                  <c:set var='name' value='${name-entry.amount}'/>
	                                  <c:set var='closingcredit' value='${closingcredit+entry.amount}'/>
	                                </c:if> 
	                            </td>
	                            <td   align="right" valign="top">
	                                <c:if test="${name < 0}">
	                                    <c:if test="${report.account.postingType == 'DEBET'}">(</c:if>
	                                        <fmt:formatNumber value="${name*-1}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">)</c:if>
	                                </c:if>
	                                <c:if test='${name >= 0}'>
	                                	<c:if test="${report.account.postingType == 'CREDIT'}">(</c:if>
	                                    <fmt:formatNumber value="${name}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'CREDIT'}">)</c:if>
	                                </c:if>
	                            </td>
	                        </tr>
	                        </c:forEach>
	                        <tr >
	                        	<td colspan="4">&nbsp;</td>
	                            <td colspan="3"><hr/></td>
	                        </tr>
	                        <tr >
	                            <td >&nbsp;	</td>
	                            <td >&nbsp;</td>	
	                            <td colspan="2" valign="top"><strong><spring:message code='accreport.closing'/></strong></td>
	                            <td  align="right" valign="top">
	                                <strong><fmt:formatNumber value="${closingdebet}" groupingUsed="true" pattern=',##0.00'/></strong>
	                            </td>	
	                            <td  align="right" valign="top">
	                                <strong><fmt:formatNumber value="${closingcredit}" groupingUsed="true" pattern=',##0.00'/></strong>
	                            </td>
	                            <td   align="right" valign="top">
	                            	<strong>
	                                <c:if test='${name < 0}'>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">(</c:if>
	                                        <fmt:formatNumber value="${name*-1}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">)</c:if>
	                                </c:if>
	                                <c:if test='${name >= 0}'>
	                                    <fmt:formatNumber value="${name}" groupingUsed='true' pattern=',##0.00'/>
	                                </c:if>
	                                </strong>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td colspan="7">&nbsp;</td>
	                        </tr>
	                        <c:remove var='closingdebet'/>
	                        <c:remove var='closingcredit'/>
	                        <c:remove var='name'/>
                        </c:if>
                        
                        <c:if test="${empty adapter.accounts}">
	                        <c:set var='closingdebet' value='${0}'/>
	                        <c:set var='closingcredit' value='${0}'/>
	                        <c:set var='name' value='${report.openingdebet-report.openingcredit}'/>
	                        <tr>
	                            <td colspan="7" valign="top"><strong><c:out value='${report.account.code} - ${report.account.name}'/></strong></td>
	                        </tr>
	                        <tr>
	                            <td colspan="2">&nbsp;</td>
	                            <td colspan="2" valign="top"><strong>Opening Balance</strong></td>	
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingdebet}"  groupingUsed='true'  pattern=',##0.00'/></strong></td>	
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingcredit}"  groupingUsed='true'  pattern=',##0.00'/></strong></td>
	                            <td align="right" valign="top"><strong><fmt:formatNumber value="${report.openingdebet - report.openingcredit}" groupingUsed='true' pattern=',##0.00'/></strong></td>
	                        </tr>
	                        <c:forEach items='${report.entrys}' var='entry'>
	                        <tr>
	                            <td  colspan="2" align="left" valign="top">	
	                                &nbsp;<fmt:formatDate value="${entry.journalEntry.entryDate}" pattern="dd-MM-yyyy"/>
	                            </td>
	                            <td width="15%"  valign="top">	
	                                &nbsp;
	                               <a href="<c:url value='/page/journalentrypreview.htm?id=${entry.journalEntry.id}'/>"><c:out value="${entry.journalEntry.code}" /></a>
	                          	</td>
	                          	<td width="34%"  valign="top"><c:out value="${entry.note}" /></td>
								<td   align="right">
	                                <c:if test="${entry.postingType == 'DEBET'}">
	                                    <fmt:formatNumber value="${entry.amount}"  groupingUsed='true' pattern=',##0.00'/>
	
	                                    <c:set var='name' value='${name+entry.amount}'/>
	                                    <c:set var='closingdebet' value='${closingdebet+entry.amount}'/>
	                                </c:if>    
	                            </td>
	                            <td   align="right" valign="top">
	                                <c:if test="${entry.postingType == 'CREDIT'}">
	                                  <fmt:formatNumber value="${entry.amount}" groupingUsed='true' pattern=',##0.00'/>
	                                  <c:set var='name' value='${name-entry.amount}'/>
	                                  <c:set var='closingcredit' value='${closingcredit+entry.amount}'/>
	                                </c:if> 
	                            </td>
	                            <td   align="right" valign="top">
	                                <c:if test="${name < 0}">
	                                    <c:if test="${report.account.postingType == 'DEBET'}">(</c:if>
	                                        <fmt:formatNumber value="${name*-1}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">)</c:if>
	                                </c:if>
	                                <c:if test='${name >= 0}'>
	                                	<c:if test="${report.account.postingType == 'CREDIT'}">(</c:if>
	                                    <fmt:formatNumber value="${name}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'CREDIT'}">)</c:if>
	                                </c:if>
	                            </td>
	                        </tr>
	                        </c:forEach>
	                        <tr >
	                        	<td colspan="4">&nbsp;</td>
	                            <td colspan="3"><hr/></td>
	                        </tr>
	                        <tr >
	                            <td >&nbsp;	</td>
	                            <td >&nbsp;</td>	
	                            <td colspan="2" valign="top"><strong>Closing Balance</strong></td>
	                            <td  align="right" valign="top">
	                                <strong><fmt:formatNumber value="${closingdebet}" groupingUsed="true" pattern=',##0.00'/></strong>
	                            </td>	
	                            <td  align="right" valign="top">
	                                <strong><fmt:formatNumber value="${closingcredit}" groupingUsed="true" pattern=',##0.00'/></strong>
	                            </td>
	                            <td   align="right" valign="top">
	                            	<strong>
	                                <c:if test='${name < 0}'>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">(</c:if>
	                                        <fmt:formatNumber value="${name*-1}" groupingUsed='true' pattern=',##0.00'/>
	                                    <c:if test="${report.account.postingType == 'DEBET'}">)</c:if>
	                                </c:if>
	                                <c:if test='${name >= 0}'>
	                                    <fmt:formatNumber value="${name}" groupingUsed='true' pattern=',##0.00'/>
	                                </c:if>
	                                </strong>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td colspan="7">&nbsp;</td>
	                        </tr>
	                        <c:remove var='closingdebet'/>
	                        <c:remove var='closingcredit'/>
	                        <c:remove var='name'/>
                        </c:if>
                        
                        </c:forEach >
                        </table>