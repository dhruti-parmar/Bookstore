<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>

<div class="jumbotron bg-light p-5 rounded">
    <h1 class="display-4">Welcome to Bookstore</h1>
    <p class="lead">Discover your next favorite book from our extensive collection.</p>
    <hr class="my-4">
    <p>Browse through our catalog or search for specific titles, authors, or genres.</p>
    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/books/list" role="button">Browse Books</a>
</div>

<div class="row mt-5">
    <div class="col-md-4">
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Extensive Collection</h5>
                <p class="card-text">Our bookstore offers a wide range of books across various genres, from fiction to non-fiction, academic to leisure reading.</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">User Reviews</h5>
                <p class="card-text">Read authentic reviews from other readers to help you make informed decisions about your next book purchase.</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Easy Navigation</h5>
                <p class="card-text">Our user-friendly interface allows you to easily search and browse through our collection to find exactly what you're looking for.</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />