<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 9/21/2023
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Student</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/toastr@2.1.4/build/toastr.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">Update Book</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>
        <form action="/book?action=edit" method="post">
            <input type="hidden" name="id" value="${book.id}">
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" value="${book.title}">
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <input type="text" class="form-control" name="description" id="description" value="${book.description}">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price</label>
                <input type="text" class="form-control" name="price" id="price" value="${book.price}">
            </div>
            <div class="mb-3">
                <label for="publishDate" class="form-label">PublishDate</label>
                <input type="date" class="form-control" name="publishDate" id="publishDate" value="${book.publishDate}">
            </div>
            <div class="mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-control" name="category" id="category">
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}" ${category.id == book.category.id?"selected":""}>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Author</label>
                <c:forEach var="author" items="${authors}">
                    <div class="form-check">
                        <c:set var="checkAuthor" value="0"/>
                        <c:forEach var="bookAuthor" items="${book.getBookAuthors()}">
                            <c:if test="${bookAuthor.author.id == author.id}">
                                <c:set var="checkAuthor" value="1"/>
                            </c:if>
                        </c:forEach>
                        <input type="checkbox" class="form-check-input" name="author" value="${author.id}" id="author-${author.id}"
                               <c:if test="${checkAuthor == '1'}">checked</c:if>
                        >
                        <label class="form-check-label" for="author-${author.id}">${author.name}</label>
                    </div>
                </c:forEach>
            </div>
            <a href="/book" class="btn btn-dark mb-2">Cancel</a>
            <button type="submit" class="btn btn-primary mb-2">Submit</button>
        </form>
    </div>

</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script>
    const message = document.getElementById('message');
    if (message !== null && message.innerHTML) {
        toastr.success(message.innerHTML);
    }
</script>
</html>
