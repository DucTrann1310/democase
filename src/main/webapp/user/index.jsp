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
        <h3 class="text-center">Management User</h3>
        <c:if test="${message != null}">
            <h6 class="d-none" id="message">${message}</h6>
        </c:if>

        <div style="display: flex; align-items: center;">
            <div style="margin-right: auto;">
                <a href="/user?action=create" class="btn btn-primary">Create</a>
                <a href="/user?action=restore" class="btn btn-primary">Restore</a>

            </div>
            <div style="margin-right: 10px;">
                <form action="/user?page=${page.currentPage}">
                    <input type="text" id="searchUser" value="${search}" name="search" class="form-control d-flex" style="width: 85%" placeholder="Search User">
                    <button id="searchButton" class="btn btn-primary">Search</button>
                </form>
            </div>
        </div>

        <table class="table table-striped">
            <tr>
                <td>Id</td>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Username</td>
                <td>Email</td>
                <td>Date of Birth</td>
                <td>Role</td>
                <td>Gender</td>
                <td>Action</td>
            </tr>
            <c:forEach var="user" items="${page.content}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.dob}</td>
                    <td>${user.role.name}</td>
                    <td>${user.gender}</td>
                    <td>
                        <a href="/user?action=edit&id=${user.getId()}" class="btn btn-warning mb-2">Edit</a>
                        <button class="btn btn-danger mb-2" onclick="showConfirmation('${user.id}', '${user.firstName}')">Delete</button>
                    </td>
                </tr>
            </c:forEach>

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
        var searchInput = document.getElementById("searchUser").value;
        var searchUrl = "/user?action=search&result=" + encodeURIComponent(searchInput);
        window.location.href = searchUrl;
    });

    function showConfirmation(userId, userName) {
        const confirmation = confirm("Are you sure you want to delete user: " + userName + "?");
        if (confirmation) {
            // Nếu người dùng nhấn OK trong hộp xác nhận, chuyển hướng đến URL xóa sinh viên
            window.location.href = "/user?action=delete&id=" + userId;
        }
    }

</script>
</body>
</html>
