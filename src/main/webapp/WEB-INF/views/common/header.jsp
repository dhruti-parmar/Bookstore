<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookstore - ${param.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Bookstore</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/books/list">Books</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/books/datatable">Books (DataTable)</a>
                    </li>
                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.username eq 'admin'}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/books/new">Add Book</a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
                
                <form class="d-flex me-2" action="${pageContext.request.contextPath}/search" method="get">
                    <select name="type" class="form-select me-2" style="width: 120px;">
                        <option value="title" ${param.searchType eq 'title' ? 'selected' : ''}>Title</option>
                        <option value="author" ${param.searchType eq 'author' ? 'selected' : ''}>Author</option>
                        <option value="genre" ${param.searchType eq 'genre' ? 'selected' : ''}>Genre</option>
                    </select>
                    <input class="form-control me-2" type="search" name="term" placeholder="Search" value="${param.searchTerm}">
                    <button class="btn btn-outline-light" type="submit">Search</button>
                </form>
                
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/user/login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/user/register">Register</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                    ${sessionScope.user.username}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">