<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jquery-1.8.1.js"></script>
    <script type="text/javascript">
            /*function loginIn() {
                var name = document.getElementsByName("username")[0].value;
                var pass = document.getElementsByName("password")[0].value;
                location.href="login?username=" + name + "&password=" + pass;
            }*/

            $(function(){
                $("#loginBtn").click(function(){
                    $("#login").submit();
                    $("#loginBtn").attr("disabled","disabled");
                });
                $("*").click(function(){
                   $("#loginInfo").hide();
                });

            })
    </script>
</head>
<body>

    <c:if test="${loginInfo!=null}">
        <p id="loginInfo">${loginInfo}</p>
    </c:if>
    <br/>
    <form:form id="login" modelAttribute="users" action="${pageContext.request.contextPath}/login/deal" method="post">
        <form:errors path="username"></form:errors><br/>
        <form:errors path="password"></form:errors><br/>
        <%--<input type="hidden" name="token" value="${token}" />
        <input type="text" name="name" value="admin" style="position: absolute;z-index: -1;" disabled autocomplete = "off"/>
        <input type="password" name="pass" value=" " style="position: absolute;z-index: -1;" disabled autocomplete = "off"/>--%>
        <form:input path="username" autocomplete="off"/><br/>
        <form:password path="password" autocomplete="off"/>
        <input type="button" id="loginBtn" name="Login In" value="Login In" />
    </form:form>
</body>

</html>
