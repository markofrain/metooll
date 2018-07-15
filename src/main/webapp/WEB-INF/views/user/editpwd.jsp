<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2018/7/15
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jquery-1.8.1.js"></script>

</head>
<body>
<div id="editpwd">
    <a href="/center/userinfo">返回个人信息</a>
    <label id="einfo">${message}</label>
    <form id="cpwd" action="${pageContext.request.contextPath}/center/edit-pwd/deal" method="post">
        <label>原密码:</label><input type="password" name="ypwd"/><br/>
        <label>新密码:</label><input type="password" name="npwd"/><br/>
        <label>再次密码:</label><input type="password" name="npwd2"/><br/>
        <input type="button" value="修改"/>
    </form>
</div>
<script type="text/javascript">
    $(function(){

        $("input[type=button]").click(function(){
            var n = $("input[name=npwd]").val();
            var n2 = $("input[name=npwd2]").val();
            if(n!=n2){
                $("#einfo").text("两次密码不一致!");
                return false;
            }
            $("#cpwd").submit();
        });


    });

</script>
</body>
</html>
