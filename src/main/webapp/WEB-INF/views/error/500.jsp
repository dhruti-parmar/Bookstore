<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Internal Server Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="alert alert-danger">
            <h3>Oops! Something went wrong.</h3>
            <p>We're sorry, but an internal server error occurred while processing your request.</p>
            <p>Our technical team has been notified and is working on resolving the issue.</p>
            
            <div class="mt-3">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Home Page</a>
            </div>
        </div>
    </div>
</body>
</html>

