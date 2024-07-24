<%@ include file="/common/sirius-general-denied.jsp"%>  
<div class="main-box" style="height:400px">
    <p> 
        <font color="#FF0000"><strong>SYSTEM RESPONSE MESSAGE:</strong><br/></font>
        <c:if test='${not empty em}'>${em}<br/></c:if>
        <c:if test='${empty em}'>
            Sorry,The system cannot process your request.<br/>
            Please try again latter.<br/>           
        </c:if>
        <a href="<c:url value='/page/exceptionviewerview.htm'/>">Show exception detail</a>
    </p>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>