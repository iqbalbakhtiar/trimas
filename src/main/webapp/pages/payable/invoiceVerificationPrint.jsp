<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
    <a class="item-button-back" href="<c:url value='/page/invoiceverificationpreedit.htm?id=${verification_edit.id}'/>"><span><spring:message code="sirius.back"/></span></a>
    <a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
    <a class="item-button-export-xls" download="invoiceverification.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Invoice Verification');"><span>Export</span></a>
</div>

<div class="main-box">
    <div class="main_container">
        <table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
            <tr>
                <td style="background: black" width="1%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td width="96%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td style="background: black" width="1%">&nbsp;</td>
            <tr>
        </table>
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="2%" colspan="2">&nbsp;</td>
                <td width="52%" colspan="3" align="left"><strong><c:out value=' ${verification_edit.organization.initial} ${verification_edit.organization.fullName}'/></strong><br/>
                    <c:forEach items="${verification_edit.organization.postalAddresses}" var="postal">
                        <c:forEach items="${postal.addressTypes}" var="types">
                            <c:if test='${types.type == "TAX" and types.enabled}'>
                                <c:out value="${types.postalAddress.address}"/><br/>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </td>
                <td width="44%" colspan="3" align="right" class="CSS3" valign="top"><strong>VERIFIKASI</strong></td>
                <td width="2%" colspan="2">&nbsp;</td>
            </tr>
            <tr><td colspan="10" height="20">&nbsp;</td></tr>
        </table>
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="2%" colspan="2">&nbsp;</td>
                <td width="48%" colspan="3" valign="top">
                    <table width="100%" cellpadding="0" cellspacing="0" align="left">
                        <tr>
                            <td width="20%" valign="top">ID</td>
                            <td width="3%" valign="top">:</td>
                            <td width="77%"><c:out value=' ${verification_edit.code}'/></td>
                        </tr>
                        <tr>
                            <td valign="top">Pemasok</td>
                            <td valign="top">:</td>
                            <td>
                                <c:out value='${verification_edit.supplier.fullName} '/><br/>
                                <c:forEach items="${verification_edit.supplier.postalAddresses}" var="postal">
                                    <c:forEach items="${postal.addressTypes}" var="types">
                                        <c:if test='${types.type == "TAX" and types.enabled}'>
                                            <c:out value="${types.postalAddress.address}"/><br/>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </td>
                        </tr>
                        <c:if test="${not empty verification_edit.note}">
                            <tr>
                                <td valign="top">Ket</td>
                                <td valign="top">:</td>
                                <td>
                                    <font color='red'>
                                        <c:out value=' ${verification_edit.note}'/>
                                    </font>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </td>
                <td width="48%" colspan="3" valign="top">
                    <table width="100%" cellpadding="0" cellspacing="0" align="left">
                        <tr>
                            <td width="35%">No. Invoice</td>
                            <td width="3%">:</td>
                            <td width="62%"><c:out value='${verification_edit.invoice}'/></td>
                        </tr>
                        <tr>
                            <td>Tanggal Invoice</td>
                            <td>:</td>
                            <td><fmt:formatDate value='${verification_edit.date}' pattern='dd - MM - yyyy'/></td>
                        </tr>
                        <tr>
                            <td>Tanggal Jatuh Tempo</td>
                            <td>:</td>
                            <td><fmt:formatDate value='${verification_edit.dueDate}' pattern='dd - MM - yyyy'/></td>
                        </tr>
                    </table>
                </td>
                <td width="2%" colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="10">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="6">
                    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <th align="left" class="bordered">&nbsp;No.</th>
                            <th align="center" class="border-right border-top border-bottom">Nama Barang</th>
                            <th colspan="2" align="center" class="border-right border-top border-bottom">Jumlah</th>
                            <th align="center" class="border-right border-top border-bottom">Harga Satuan</th>
                            <th align="center" class="border-right border-top border-bottom">Total</th>
                        </tr>
                        <c:forEach items="${verification_edit.items}" var="item" varStatus='status'>
                            <tr>
                                <td width="2%"  align="center" class="border-bottom border-right border-left" style="padding-left:5px;"><c:out value="${status.count}"/></td>
                                <td width="47%" class="border-bottom border-right" style="padding-left:5px;"><c:out value='${item.product.code}'/> <c:out value='${item.product.name}'/></td>
                                <td colspan="2" width="15%" class="border-bottom border-right" align="center" style="padding-right:5px;"><fmt:formatNumber value='${item.quantity}' pattern=',##0'/></td>
                                <td width="15%" class="border-bottom border-right" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/></td>
                                <td width="19%" class="border-bottom border-right" align="right" style="padding-right:5px;"><fmt:formatNumber value='${(item.money.amount * item.quantity)}' pattern=',##0.00'/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="3" align="left" class="border-right">Sub Total</td>
                            <td align="right" class="border-right" style="padding-right:5px;">
                                <fmt:formatNumber value='${adapter.subTotal}' pattern=',##0.00'/>
                            </td>
                        </tr>
                        <c:if test='${adapter.taxAmount > 0}'>
                            <tr>
                                <td colspan="2">&nbsp;</td>
                                <td colspan="3" align="left" class="border-right">Pajak Pertambahan Nilai (PPN)</td>
                                <td align="right" class="border-top border-right" style="padding-right:5px;"><fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="3" align="left" class="border-right">Total</td>
                            <td align="right" class="border-top border-bottom border-right" style="padding-right:5px;">
                                <fmt:formatNumber value='${adapter.totalAfterTax}' pattern=',##0.00'/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                            <td width="10%" valign="top" align="left">Terbilang </td>
                            <td width="2%" valign="top" align="center">&nbsp;:&nbsp;</td>
                            <td valign="top" colspan="2" align="left">
                                <c:set var="string" value="${said_id} ${verification_edit.money.currency.alias}"/>
                                <c:out value='${string}'/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="10">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="6"></td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="10">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="6">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td colspan="2"><b>Lampiran</b></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&#9744;&nbsp;&nbsp; Lainnya</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="10">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="5">&nbsp;</td>
                <td align="center">Hormat kami,</td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td colspan="5" height="88" width="55%">&nbsp;</td>
                <td align="center" width="45%" valign="bottom">(______________________)</td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="10">&nbsp;</td>
            </tr>
        </table>
        <table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
            <tr>
                <td colspan="2">&nbsp;</td>
                <td class="CSS5"></td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td align="right" class="CSS5"><i>Form MS-01 Rev 16/09/11</i></td>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr><td colspan="10" height="22">&nbsp;</td></tr>
            <tr>
                <td style="background: black;" width="1%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td width="94%">&nbsp;</td>
                <td width="1%">&nbsp;</td>
                <td style="background: black" width="1%">&nbsp;</td>
            <tr>
        </table>
    </div>

    <%@include file="invoiceVerificationExcel.jsp"%>
</div>

<%@ include file="/common/sirius-general-bottom.jsp"%>