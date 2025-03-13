</div>
    
    <footer class="bg-dark text-white mt-5 py-3">
        <div class="container text-center">
            <p>&copy; 2025 Bookstore. All rights reserved.</p>
        </div>
    </footer>
    
    <!-- Only include Bootstrap JS if jQuery isn't already loaded by DataTables -->
    <script>
        if (typeof jQuery === 'undefined') {
            document.write('<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"><\/script>');
            document.write('<script src="${pageContext.request.contextPath}/js/script.js"><\/script>');
        } else {
            // If jQuery is already loaded (by DataTables), just load Bootstrap's JS
            document.write('<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"><\/script>');
        }
    </script>
</body>
</html>

