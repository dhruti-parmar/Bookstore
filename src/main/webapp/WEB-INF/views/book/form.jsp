<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${empty book ? 'Add New Book' : 'Edit Book'}" />
</jsp:include>

<h2>${empty book ? 'Add New Book' : 'Edit Book'}</h2>

<div class="row">
    <div class="col-md-8">
        <div class="card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/books/${empty book ? 'insert' : 'update'}" method="post">
                    <c:if test="${not empty book}">
                        <input type="hidden" name="id" value="${book.bookId}">
                    </c:if>
                    
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title" value="${book.title}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="author" class="form-label">Author</label>
                        <input type="text" class="form-control" id="author" name="author" value="${book.author}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" class="form-control" id="price" name="price" value="${book.price}" step="0.01" min="0" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="genre" class="form-label">Genre</label>
                        <input type="text" class="form-control" id="genre" name="genre" value="${book.genre}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="5">${book.description}</textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label for="isbn" class="form-label">ISBN</label>
                        <input type="text" class="form-control" id="isbn" name="isbn" value="${book.isbn}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="publisher" class="form-label">Publisher</label>
                        <input type="text" class="form-control" id="publisher" name="publisher" value="${book.publisher}">
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <button type="submit" class="btn btn-primary">${empty book ? 'Add Book' : 'Update Book'}</button>
                        <a href="${pageContext.request.contextPath}/books/list" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />