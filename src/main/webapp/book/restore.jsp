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
        <h3 class="text-center">Restore Book</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>
        <form  action="/book?action=restore" method="post">
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
                        <td>${book.getAuthors()}</td>
                        <td>${book.category.name}</td>
                        <td>
                            <input type="checkbox" name="restoredBook" value="${book.id}"> <!-- Checkbox -->
                        </td>
                    </tr>
                </c:forEach>

                <c:set var="deletedBookCount" value="0" />
                <c:forEach var="book" items="${books}">
                    <c:if test="${book.isDeleted()}">
                        <c:set var="deletedBookCount" value="${deletedBookCount + 1}" />
                    </c:if>
                </c:forEach>

                <c:if test="${deletedBookCount == fn:length(books) && deletedBookCount != 0 && deletedBookCount > 1}" >
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>Select All </td>
                        <td><input type="checkbox" id="selectAllCheckbox"> <!-- Checkbox to select all --></td>
                    </tr>
                </c:if>
            </table>
            <div>
                <a href="/book" class="btn btn-dark mb-2">Cancel</a>
                <button type="submit" class="btn btn-primary mb-2">Restore</button>
            </div>
        </form>



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

    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.getElementsByName('restoredBook');

    selectAllCheckbox.addEventListener('change', function () {
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
</script>
</body>
</html>