<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2018/7/14
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>编辑用户信息</title>
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/statics/jeDate/test/jeDate-test.css">--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/statics/jeDate/jedate.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jeDate/jedate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jquery-1.8.1.js"></script>
    <script type="text/javascript">
        $(function(){
            //名称测试
            window.testrname=function(){
                var val = $("input[name=realName]").val();
                if (val.length>20){
                    $("label[name=rmessage]").text("请输入较短名称");
                    return false;
                }
                return true;
            }
            window.testemail=function(){
                var val = $("input[name=email]").val();
                if(val.length>30){
                    $("label[name=emessage]").text("请输入较短邮箱");
                    return false;
                }
                var test = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
                if(!test.test(val)){
                    $("label[name=emessage]").text("邮箱格式不正确");
                    return false;
                }
                return true;
            }
            window.testphone=function(){
                var val = $("input[name=phone]").val();
                var test = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
                if(!test.test(val)){
                    $("label[name=pmessage]").text("手机格式不正确");
                    return false;
                }
                return true;
            }

            $("input[type=button]").click(function(){
                if(testrname()&&testemail()&&testphone()){
                    $("#edituser").submit();
                    $("#edituser").attr("disable","disable");
                }
            });

        });
    </script>
</head>
<body>
    <div id="edit">
        <c:if test="${message!=null}">
            <label name="infomessage" style="color: lawngreen;">${message}</label>
        </c:if>
        <form id="edituser" action="${pageContext.request.contextPath}/center/edit-user/deal" method="post">
            <label>真实姓名</label>
            <input name="realName" value="${user.realName}" autocomplete="off" onfocus="document.getElementsByName('rmessage')[0].innerHTML='';"/><label name="rmessage"></label><br/>
            <label>电子邮件</label>
            <input name="email" value="${user.email}" autocomplete="off" onfocus="document.getElementsByName('emessage')[0].innerHTML='';"/><label name="emessage"></label><br/>
            <label>联系电话</label>
            <input name="phone" value="${user.phone}" autocomplete="off" onfocus="document.getElementsByName('pmessage')[0].innerHTML='';"/><label name="pmessage"></label><br/>
            <div class="jebody">
                <div class="jewarp">
                    <div class="content">
                        <div class="jeitem">
                            <div class="jeinpbox"><input type="text" name="dateOfBirth" value="<fmt:formatDate value="${user.dateOfBirth}"/>" class="jeinput" id="enYMD" placeholder="YYYY-MM-DD"></div>
                        </div>

                    </div>
                </div>
            </div>
            <input type="button" value="保存">
        </form>
    </div>



    <script type="text/javascript">
        setTimeout(function(){
            document.getElementsByName("infomessage")[0].innerHTML="";
        },3000);
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/jeDate/test.js"></script>
</body>
</html>
