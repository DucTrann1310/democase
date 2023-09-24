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
        <h3 class="text-center">Management Role</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>

        <div style="display: flex; align-items: center;">
            <div style="margin-right: auto;">
            </div>
            <div style="margin-right: 10px;">
                <form action="/role?page=${page.currentPage}" method="get">
                    <input type="hidden" name="action" value="searchDeleted">
                    <input type="text" id="searchRole" value="${search}" name="searchDeleted" class="form-control d-flex" style="width: 85%" placeholder="Search Role Name">
                    <button id="searchButton" class="btn btn-primary">Search</button>
                </form>
            </div>
        </div>
        <form action = "/role?action=restore" method="post">
            <table class="table table-striped">
                <tr>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Action</td>
                </tr>
                <c:forEach var="role" items="${page.content}">
                    <tr>
                        <td>${role.id}</td>
                        <td>${role.name}</td>
                        <td>
                            <input type="checkbox" name="restoredRole" value="${role.id}">
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${page.content.size() > 1}" >
                    <tr>
                        <td></td>
                        <td>Select All </td>
                        <td><input type="checkbox" id="selectAllCheckbox"> <!-- Checkbox to select all --></td>
                    </tr>
                </c:if>
            </table>
            <div>
                <a href="/role" class="btn btn-dark mb-2">Back</a>
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

    document.getElementById("searchButton").addEventListener("click", function() {
        var searchInput = document.getElementById("searchBook").value;
        var searchUrl = "/role?action=search&result=" + encodeURIComponent(searchInput);
        window.location.href = searchUrl;
    });

    const selectAllCheckbox = document.getElementById('selectAllCheckbox');
    const checkboxes = document.getElementsByName('restoredRole');

    selectAllCheckbox.addEventListener('change', function () {
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });

</script>
</body>
</html>
