<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="Book List" />
</jsp:include>

<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.5.0/css/responsive.bootstrap5.min.css"/>

<h2>Book List</h2>

<div class="card mb-4">
    <div class="card-body">
        <c:if test="${not empty sessionScope.user && sessionScope.user.username eq 'admin'}">
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/books/new" class="btn btn-primary">Add New Book</a>
            </div>
        </c:if>
        
        <table id="bookTable" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Data will be loaded via AJAX -->
            </tbody>
        </table>
    </div>
</div>

<!-- DataTables JS -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.5.0/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.5.0/js/responsive.bootstrap5.min.js"></script>

<script>
$(document).ready(function() {
    $('#bookTable').DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "${pageContext.request.contextPath}/api/books",
            type: "POST"
        },
        columns: [
            { data: 0, visible: false }, // ID column (hidden)
            { data: 1 }, // Title
            { data: 2 }, // Author
            { data: 3 }, // Genre
            { data: 4 }, // Price
            { 
                data: 5, 
                orderable: false, 
                searchable: false,
                className: "text-center" 
            } // Actions
        ],
        responsive: true,
        language: {
            emptyTable: "No books available",
            zeroRecords: "No matching books found",
            info: "Showing _START_ to _END_ of _TOTAL_ books",
            infoEmpty: "Showing 0 to 0 of 0 books",
            infoFiltered: "(filtered from _MAX_ total books)",
            search: "Search:",
            paginate: {
                first: "First",
                last: "Last",
                next: "Next",
                previous: "Previous"
            }
        },
        lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
        pageLength: 10
    });
});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />