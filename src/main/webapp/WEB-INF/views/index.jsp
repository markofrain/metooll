<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p><a href="login">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="register">注册</a></p><br/>

    <c:if test="${sessionScope.get('user')!=null}">
        登录成功，欢迎您!&nbsp;${sessionScope.get("user").username}<li>
        <ul>
            <li><a href="center/userinfo">个人中心</a></li>
            <li><a href="logout">注销</a></li>
        </ul>
        <script type="text/javascript">
            document.getElementsByTagName("p")[0].remove();
        </script>
    </c:if>
</body>
</html>
