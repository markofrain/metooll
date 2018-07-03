<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jquery.min.js"></script>
    <style type="text/css">
        #regTable .prompt{
            /*display: none;*/
            font-size: 13px;
            color: #666;
            text-indent: 8px;
        }
    </style>
</head>
<body>
    <form:form id="regForm" modelAttribute="user" action="${pageContext.request.contextPath}/register/deal" method="post">
        <form:errors path="username"></form:errors>
        <form:errors path="password"></form:errors>
        <table id="regTable">
            <tr>
                <td><form:label path="username">用户名</form:label></td>
                <td colspan="2"><form:input path="username" autocomplete="off" size="20" maxlength="20" onblur="blurName(this)" onfocus="this.style.borderColor='';"/></td>
                <td class="prompt">*长度介于6-20位之间，只允许字母数字</td>
            </tr>
            <tr>
                <td><form:label path="username">密&nbsp;&nbsp;&nbsp;码</form:label></td>
                <td colspan="2"><form:password path="password" autocomplete="off"  size="20" maxlength="20" onblur="patPwd()" onfocus="this.style.borderColor='';"/></td>
                <td class="prompt">*长度介于6-20位之间，仅包括字母、数字和下划线</td>
            </tr>
            <tr >
                <td>验证码:</td>
                <td><input name="checkcode" type="text" autocomplete="off" size="7" onblur="validatecode(this)" onfocus="document.getElementsByClassName('prompt')[2].innerHTML=''"/></td>
                <td><img id="checkcode" src="${pageContext.request.contextPath }/validatecode.jsp?'+Math.random()" onclick="javascript:document.getElementById('checkcode').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();" /></td>
                <td class="prompt"></td>
            </tr>
            <tr>
                <td colspan="4"><input type="button" value="Register"></td>
            </tr>
        </table>
    </form:form>
<script src="${pageContext.request.contextPath}/statics/register.js" type="text/javascript"></script>
</body>
</html>
