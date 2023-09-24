<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/18/2023
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">Management Product</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>

        <div style="display: flex; align-items: center;">
            <div style="margin-right: auto;">
                <a href="/product?action=create" class="btn btn-primary">Create</a>
                <a href="/product?action=restore" class="btn btn-primary">Restore</a>
                <button type="submit" class="btn btn-primary">Delete</button>

            </div>
            <div style="margin-right: 10px;">
                <input type="text" id="searchProduct" name="productName" class="form-control" placeholder="Search Product Name">
            </div>
            <div>
                <a href="#" id="searchButton" class="btn btn-primary">Search</a>
            </div>
        </div>

        <table class="table table-striped">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Price</td>
                <td>Description</td>
                <td>Category</td>
                <td>Action</td>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.description}</td>
                    <td>${product.category.name}</td>
                    <td>
                        <a href="/product?action=edit&id=${product.getId()}" class="btn btn-warning mb-2">Edit</a>
                        <label for="checkbox-${product.id}" class="btn btn-danger mb-2">
                            <input id="checkbox-${product.id}" type="checkbox" name="deletedProduct" value="${product.id}">Delete
                        </label>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <nav aria-label="...">
            <ul class="pagination">
                <li class="page-item <c:if test="${page.currentPage == 1}">disabled</c:if>">
                    <a class="page-link"  href="/product?page=${page.currentPage - 1}" tabindex="-1" aria-disabled="true">Previous</a>
                </li>
                <c:forEach var="number" begin="1" end="${page.totalPage}">
                    <c:if test="${number == page.currentPage}">
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="/product?page=${number}">${number}</a>
                        </li>
                    </c:if>
                    <c:if test="${number != page.currentPage}">
                        <li class="page-item">
                            <a class="page-link" href="/product?page=${number}">${number}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <li class="page-item <c:if test="${page.currentPage == page.totalPage}">disabled</c:if>">
                    <a class="page-link" href="/product?page=${page.currentPage + 1}">Next</a>
                </li>
            </ul>
        </nav>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script>
    const message = document.getElementById('message');
    if (message !== null && message.innerHTML) {
        toastr.success(message.innerHTML);
    }

    document.getElementById("searchButton").addEventListener("click", function() {
        var searchInput = document.getElementById("searchProduct").value;
        var searchUrl = "/product?action=search&result=" + encodeURIComponent(searchInput);
        window.location.href = searchUrl;
    });

</script>
</body>
</html>