<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2018/7/14
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="sessionScope.get('user')==null">
    <script>
        location.href="index";
    </script>
</c:if>
    <a href="${pageContext.request.contextPath}/center/edit-user">编辑个人信息</a>
    <a href="${pageContext.request.contextPath}/center/edit-pwd">修改密码</a>
    <br/><br/>
    <ul>
        <li>用户名:${sessionScope.get("user").username}</li>
        <li>电子邮箱:${sessionScope.get("user").email}</li>
        <li>真实姓名:${sessionScope.get("user").realName}</li>
        <li>联系电话:${sessionScope.get("user").phone}</li>
        <li>生日:<fmt:formatDate value="${sessionScope.get('user').dateOfBirth}" pattern="yyyy年MM月dd日"/></li>

    </ul>
</body>
</html>
