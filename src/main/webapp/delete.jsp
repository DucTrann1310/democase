<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">
        <h3 class="text-center">Delete Student</h3>
        <form action="/student?action=delete" method="post">
            <input type="hidden" name="id" value="${student.id}">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <label for="name" value="${student.name}"></label>
                <input type="text" class="form-control" id="name" name="name" value="${student.name}" readonly>
            </div>
            <div class="mb-3">
                <label for="dob" class="form-label">Date Of Birth</label>
                <input type="date" class="form-control" name="dob" id="dob" value="${student.DOB}" readonly>
            </div>
            <div class="mb-3">
                <label for="gender" class="form-label">Gender</label>
                <input type="text" class="form-control" name="gender" id="gender" value="${student.gender}" readonly>
<%--                <select class="form-control" name="gender" id="gender">--%>
<%--                    <c:forEach var="gender" items="${genders}">--%>
<%--                        <option value="${gender}">${gender}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
            </div>
            <a href="/student" class="btn btn-dark mb-2">Cancel</a>
            <button type="submit" class="btn btn-primary mb-2">Delete</button>
        </form>
    </div>

</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</html>