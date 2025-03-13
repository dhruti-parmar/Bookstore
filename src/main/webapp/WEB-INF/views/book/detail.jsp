<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${book.title}" />
</jsp:include>

<div class="row">
    <div class="col-md-8">
        <h2>${book.title}</h2>
        <h5 class="text-muted">by ${book.author}</h5>
        
        <div class="mb-3">
            <span class="badge bg-secondary">${book.genre}</span>
            <c:if test="${not empty book.publisher}">
                <span class="ms-2">Publisher: ${book.publisher}</span>
            </c:if>
            <c:if test="${not empty book.isbn}">
                <span class="ms-2">ISBN: ${book.isbn}</span>
            </c:if>
        </div>
        
        <div class="mb-3">
            <h4>$<fmt:formatNumber value="${book.price}" pattern="#,##0.00" /></h4>
        </div>
        
        <div class="mb-4">
            <h5>Description</h5>
            <p>${book.description}</p>
        </div>
        
        <c:if test="${not empty sessionScope.user && sessionScope.user.username eq 'admin'}">
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/books/edit?id=${book.bookId}" class="btn btn-primary">Edit Book</a>
                <a href="${pageContext.request.contextPath}/books/delete?id=${book.bookId}" 
                   class="btn btn-danger ms-2"
                   class="btn btn-danger ms-2"
                   onclick="return confirm('Are you sure you want to delete this book?')">Delete Book</a>
            </div>
        </c:if>
    </div>
    
    <div class="col-md-4">
        <div class="card">
            <div class="card-header">
                <h5>Reviews</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty book.reviews}">
                        <p class="text-muted">No reviews yet.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="mb-3">
                            <h6>Average Rating: 
                                <span class="text-warning">
                                    <fmt:formatNumber value="${book.averageRating}" pattern="#.#" /> / 5
                                </span>
                            </h6>
                        </div>
                        
                        <c:forEach var="review" items="${book.reviews}">
                            <div class="border-bottom mb-3 pb-3">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <strong>${review.user.username}</strong>
                                        <div class="text-warning">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= review.rating}">★</c:when>
                                                    <c:otherwise>☆</c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    
                                    <c:if test="${sessionScope.user.userId eq review.user.userId}">
                                        <a href="${pageContext.request.contextPath}/review/delete?id=${review.reviewId}&bookId=${book.bookId}" 
                                           class="text-danger"
                                           onclick="return confirm('Delete this review?')">
                                            <small>Delete</small>
                                        </a>
                                    </c:if>
                                </div>
                                <p class="mt-2">${review.comment}</p>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                
                <c:if test="${not empty sessionScope.user}">
                    <div class="mt-4">
                        <h6>Add Your Review</h6>
                        <form action="${pageContext.request.contextPath}/review/add" method="post">
                            <input type="hidden" name="bookId" value="${book.bookId}">
                            
                            <div class="mb-3">
                                <label for="rating" class="form-label">Rating</label>
                                <select class="form-select" id="rating" name="rating" required>
                                    <option value="5">5 - Excellent</option>
                                    <option value="4">4 - Very Good</option>
                                    <option value="3">3 - Good</option>
                                    <option value="2">2 - Fair</option>
                                    <option value="1">1 - Poor</option>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="comment" class="form-label">Comment</label>
                                <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Submit Review</button>
                        </form>
                    </div>
                </c:if>
                
                <c:if test="${empty sessionScope.user}">
                    <div class="alert alert-info mt-3">
                        <a href="${pageContext.request.contextPath}/user/login">Login</a> to leave a review.
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />