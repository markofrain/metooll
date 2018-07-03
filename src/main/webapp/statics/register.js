$(function(){
    var cknp=true;

    //用户名失焦判断并验证规则
    window.blurName=function(){
        var name = $("input[name='username']").val();
        if(name==""||name==null||name==undefined){
            cknp=false;
            return;
        }
        var npat = /^([0-9]|[a-z]|[A-Z]){6,20}$/;
        if(!npat.test(name)){
            $(".prompt:first").val("*用户名长度介于6-20位之间，只允许字母数字");
            $("input[name='username']").css("border-color","red");
            cknp=false;
        }else{
            checkname(name);
        }
    }
    //密码失焦判断
    window.patPwd=function(){
        var pwd = $("input[name='password']").val();
        if(pwd==""||pwd==null||pwd==undefined){
            cknp=false
            return;
        }
        var ppat = /^([0-9]|[a-z]|[A-Z]|_){6,20}$/;
        if(!ppat.test(pwd)){
            document.getElementsByClassName("prompt")[1].innerText="*密码长度介于6-20位之间，仅包括字母、数字和下划线";
            $("input[name='password']").css("borderColor","red");
            cknp=false;
        }else{
            cknp=true;
        }
    }


    //用户名是否重复检测
    function checkname(name){
        $.post("/mianshi/register/checkname",{username:name},function(data){
            if(data=="false"){//不存在
                document.getElementsByClassName("prompt")[0].innerText="该用户名可用";
                cknp=true;
            }else{
                document.getElementsByClassName("prompt")[0].innerText="该用户名已使用";
                cknp=false;
            }
        });
    };


    //验证码判断
    window.validatecode=function(e){
        var code = e.value;
        if(code==""||code==null||code==undefined){
            cknp=false;
        }
        $.ajax({
            url:"register/checkcode",
            type:"POST",
            data:{validateCode:code},
            dataType:"json",
            success:function(data){
                if (data=="false"){//错误
                    document.getElementsByClassName("prompt")[2].innerText="验证码错误";
                    document.getElementsByClassName("prompt")[2].style.color="red";
                    //重新设置验证码
                    $("#checkcode").attr("src",'/mianshi/validatecode.jsp?'+Math.random());
                    cknp=false;
                }else{
                    cknp=true;
                }
            },error:function(){
                alert("错误");
            }
        });
    };

    $("input[type='button']").click(function(){
        if(cknp){
            $("#regForm").submit();
            $("input[type='button']").attr("disabled","disabled");
        }
    });

});