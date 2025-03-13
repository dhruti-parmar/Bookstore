<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="Error" />
</jsp:include>

<div class="alert alert-danger">
    <h3>Oops! Something went wrong.</h3>
    <p>We're sorry, but an error occurred while processing your request.</p>
    <p>Please try again later or contact the administrator if the problem persists.</p>

    <!-- Display full exception details -->
    <c:if test="${not empty pageContext.exception}">
        <div class="mt-3">
            <p><strong>Error Details:</strong></p>
            <pre><%= exception != null ? exception : "No exception details available." %></pre>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
