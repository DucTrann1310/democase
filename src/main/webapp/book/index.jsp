<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 9/21/2023
  Time: 8:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <h3 class="text-center">Management Book</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>

        <div style="display: flex; align-items: center;">
            <div style="margin-right: auto;">
                <a href="/book?action=create" class="btn btn-primary">Create</a>
                <a href="/book?action=restore" class="btn btn-primary">Restore</a>
                <a href="#" id="delete" class="btn btn-primary">Delete</a>

            </div>
            <div style="margin-right: 10px;">
                <input type="text" id="searchBook" name="bookName" class="form-control" placeholder="Search Book Title">
            </div>
            <div>
                <a href="#" id="searchButton" class="btn btn-primary">Search</a>
            </div>
        </div>

        <table class="table table-striped">
            <tr>
                <td>Id</td>
                <td>Title</td>
                <td>Price</td>
                <td>Description</td>
                <td>Publish Date</td>
                <td>Category</td>
                <td>Authors</td>
                <td>Action</td>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td><fmt:formatNumber value="${book.price}" pattern="#,##0.###" /> VND</td>
                    <td>${book.description}</td>
                    <td>${book.publishDate}</td>
                    <td>${book.category.name}</td>
                    <td>${book.getAuthors()}</td>
                    <td>
                        <a href="/book?action=edit&id=${book.getId()}" class="btn btn-warning mb-2">Edit</a>
                        <label for="checkbox-${book.id}" class="btn btn-danger mb-2">
                            <input id="checkbox-${book.id}" type="checkbox" name="deletedBook" value="${book.id}">Delete
                        </label>
                    </td>
                </tr>
            </c:forEach>

            <c:set var="deletedBookCount" value="0" />
            <c:forEach var="book" items="${books}">
                <c:if test="${!book.isDeleted()}">
                    <c:set var="deletedBookCount" value="${deletedBookCount + 1}" />
                </c:if>
            </c:forEach>

            <c:if test="${deletedBookCount == fn:length(books) && deletedBookCount != 0 && deletedBookCount > 1}">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>Select All <input type="checkbox" id="selectAllCheckbox"> <!-- Checkbox to select all --></td>
                </tr>
            </c:if>

        </table>
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
        var searchInput = document.getElementById("searchBook").value;
        var searchUrl = "/book?action=search&result=" + encodeURIComponent(searchInput);
        window.location.href = searchUrl;
    });

    document.getElementById("delete").addEventListener("click", function() {
        // Lấy danh sách các checkbox đã chọn
        var checkboxes = document.getElementsByName("deletedBook");
        var deletedCheckboxIds = [];

        // Lặp qua danh sách các checkbox và lưu các checkbox đã được chọn vào mảng deletedCheckboxIds
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                deletedCheckboxIds.push(checkboxes[i].value);
            }
        }

        // Kiểm tra nếu có ít nhất một checkbox được chọn
        if (deletedCheckboxIds.length > 0) {
            // Tạo URL cho yêu cầu xóa
            var deleteUrl = "/book?action=delete&deletedBooks=" + encodeURIComponent(deletedCheckboxIds.join(","));

            // Chuyển hướng đến Servlet để xóa
            window.location.href = deleteUrl;
        }
    });

    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.getElementsByName('deletedBook');

    selectAllCheckbox.addEventListener('change', function () {
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
</script>
</body>
</html>
