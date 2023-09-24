<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 9/21/2023
  Time: 8:22 AM
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
    <h3 class="text-center">Create Role</h3>
    <form action="/role?action=create" method="post">
      <div class="mb-3">
        <label for="title" class="form-label">Name</label>
        <input type="text" class="form-control" id="title" name="name">
      </div>
      <a href="/role" class="btn btn-dark mb-2">Cancel</a>
      <button type="submit" class="btn btn-primary mb-2">Submit</button>
    </form>
  </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous">

</script>

<script>
  const message = document.getElementById('message');
  if (message !== null && message.innerHTML) {
    toastr.success(message.innerHTML);
  }
</script>
</body>
</html>
