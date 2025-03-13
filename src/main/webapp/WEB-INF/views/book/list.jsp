<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="Book List" />
    <jsp:param name="searchType" value="${searchType}" />
    <jsp:param name="searchTerm" value="${searchTerm}" />
</jsp:include>

<h2>Book List</h2>

<c:if test="${not empty searchTerm}">
    <div class="alert alert-info">
        Search results for: <strong>${searchTerm}</strong> in <strong>${searchType}</strong>
        <a href="${pageContext.request.contextPath}/books/list" class="ms-2 btn btn-sm btn-outline-secondary">Clear Search</a>
    </div>
</c:if>

<div class="d-flex justify-content-between align-items-center mb-3">
    <div>
        <c:choose>
            <c:when test="${totalBooks == 0}">
                <p class="text-muted">No books found</p>
            </c:when>
            <c:when test="${totalBooks == 1}">
                <p class="text-muted">Showing 1 book</p>
            </c:when>
            <c:otherwise>
                <p class="text-muted">Showing ${(currentPage - 1) * booksPerPage + 1} - 
                    ${(currentPage * booksPerPage) > totalBooks ? totalBooks : (currentPage * booksPerPage)} 
                    of ${totalBooks} books</p>
            </c:otherwise>
        </c:choose>
    </div>
    
    <c:if test="${not empty sessionScope.user && sessionScope.user.username eq 'admin'}">
        <a href="${pageContext.request.contextPath}/books/new" class="btn btn-primary">Add New Book</a>
    </c:if>
</div>

<c:choose>
    <c:when test="${empty books}">
        <div class="alert alert-warning">
            No books found.
        </div>
    </c:when>
    <c:otherwise>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach var="book" items="${books}">
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${book.title}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">by ${book.author}</h6>
                            <p class="card-text">
                                <span class="badge bg-secondary">${book.genre}</span>
                            </p>
                            <p class="card-text">
                                <strong>Price:</strong> $<fmt:formatNumber value="${book.price}" pattern="#,##0.00" />
                            </p>
                            <div class="d-flex justify-content-between align-items-center">
                                <a href="${pageContext.request.contextPath}/books/view?id=${book.bookId}" class="btn btn-primary">View Details</a>
                                
                                <c:if test="${not empty sessionScope.user && sessionScope.user.username eq 'admin'}">
                                    <div>
                                        <a href="${pageContext.request.contextPath}/books/edit?id=${book.bookId}" class="btn btn-sm btn-outline-secondary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/books/delete?id=${book.bookId}" 
                                           class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Previous button -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <c:choose>
                            <c:when test="${empty searchTerm}">
                                <a class="page-link" href="${pageContext.request.contextPath}/books/list?page=${currentPage - 1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="${pageContext.request.contextPath}/search?type=${searchType}&term=${searchTerm}&page=${currentPage - 1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    
                    <!-- Page numbers -->
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <c:choose>
                                <c:when test="${empty searchTerm}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/books/list?page=${i}">${i}</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link" href="${pageContext.request.contextPath}/search?type=${searchType}&term=${searchTerm}&page=${i}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                    
                    <!-- Next button -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <c:choose>
                            <c:when test="${empty searchTerm}">
                                <a class="page-link" href="${pageContext.request.contextPath}/books/list?page=${currentPage + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="${pageContext.request.contextPath}/search?type=${searchType}&term=${searchTerm}&page=${currentPage + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </nav>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />