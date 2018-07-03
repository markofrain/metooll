# 项目
一个根据登录注册实现的小小项目，没有任何的前端渲染页面,所以会比较low点
通过简单部分的spring form库+事务管理+前后端数据校验+aop和注解的日志系统
此程序用于登录注册的模型，后续继续更改和拓展。

### 登录
index，有登录，注册按钮，点击登录进入登录页面，登录成功进入index后隐藏或删除两个按钮并显示显示用户名，失败则返回login页面，显示错误信息

依次创建Users实体类，Dao接口UsersMapper，UsersService及UsersController。

login页面中：
使用spring form标签库的form表单，modelAttribute指定请求域的对象示例名称，path指定对象属性名。input标签中添加autocomplete属性为off，以免被浏览器文本框保存输入过的数据。
通过button按钮的点击事件，提交表单，并禁用该按钮，以防止二次重复提交。

后端：
因为form表单需要一个对象实例，所以在/login请求路径方法中通过model对象添加Users对象，但是如果对象直接放在方法参数上则不行。然后在方法中获得login页面可能需要的数据，进入到login页面
login登录请求成功后，/login/deal路径方法实现登录。在登陆情况中，分为三种：用户不存在，用户登录成功和登陆失败。

- 因此首先需要通过isExists验证用户名是否存在，如果不存在直接添加错误信息返回到login页面，并显示错误信息。
- 如果存在，则通过请求对象的账号密码执行登录方法操作，如若账号密码正确登录成功，则通过用户名获得用户信息设置到session(可不直接获得全部信息，可以到个人信息页在设置，这里只设置用户名就可行)中并重定向到index页面显示用户名。
- 否则登录失败，则设置用户名到model对象中，跳转到login页面，并显示错误信息。

```
String ret="login", loginInfo="用户不存在";
        if(usersService.isExist(users.getUsername())){
            boolean flag = usersService.login(users.getUsername(),users.getPassword());
            if(flag){
                ret = "redirect:/index.jsp";
                session.setAttribute("user",usersService.getUser(users.getUsername()));
            }else{
                ret = "login";
                loginInfo = "账号或密码错误，登录失败";
                model.addAttribute("username",users.getUsername());
            }
        }
        model.addAttribute("loginInfo",loginInfo);
        return ret;
```

所以，如果登录成功可能需要2个或3个访问底层数据库方法，登录失败的话需要2个访问数据库底层方法，不存在访问1次。

### 注册
通过index页的注册按钮进入到注册页面

register页面：
form表单的modelAttribute属性设置对象实例
文本框和登录框都设置autocomplete属性并设置size属性和maxlength属性，并在文本框后描述用户名密码注册规则，验证码则通过一个jsp页面中引入awt包生成一个图片，并将图片中的文字放入session中。通过设置src路径请求到validatecode.jsp获得图片，onclick属性单击事件，每次单击重新将img的src路径设置为validatecode.jsp

```
<img id="checkcode" src="${pageContext.request.contextPath }/validatecode.jsp?'+Math.random()" onclick="javascript:document.getElementById('checkcode').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();" />
```

首先需要用户名校验，设置一个方法，用于失焦的时候触发，方法中首先获得文本值并验证是否为空，则直接退出，然后通过正则表达式验证文本值是否满足规则，如果不满足则将文本框边框标志于红色，如果满足则调用用户名的检测重复方法，ajax请求到后端，返回前台一个值，可用或不可用都将后面的提示信息更改。最后在文本框的onfocus属性的焦时，取消文本框边框颜色。
密码框同样失焦进行非空判断，然后进行正则表达式判断，不符合规范则边框标红，否则什么都不做。onfocus属性讲边框改为无色。
验证码失焦，依然是非空判断，然后通过ajax请求到后端验证，错误则文本信息设置为验证码错误，并标红，然后重新设置img的src属性的路径，最后通过onfocus取消文本信息显示。
button按钮单击事件，在提交之前保证每个验证判断都是无误的，所以需要通过一个变量来判断，是否可以提交。所以声明全局属性值为true，每个验证只要有错则设置为false，验证成功则设置为true。那么么提交表单之前验证全局属性的bool值再进行提交。

后端：
验证用户名重复和验证码一致性，分别两个方法，都要加上@ResponseBody注解。通过isExists验证用户名是否存在。通过HttpSession获得session域中的key值与失焦传来的验证码比较。最后都通过google的Gson对象toJson方法返回。
/register路径请求方法携带Users对象到注册页面。
/register/deal路径请求方法实现注册，addUser方法注册，成功则设置session并重定向到index，失败则携带错误信息到register页面。


### 404,500等错误页面配置
添加静态错误页面，并在web.xml中配置errorpage

```
<error-page>
    <error-code>404</error-code>
    <location>/statics/error/404.html</location>
  </error-page>
  <error-page>
    <error-code>500</error-code>
    <location>/statics/error/500.html</location>
  </error-page>
  <error-page>
    <error-code>405</error-code>
    <location>/statics/error/405.html</location>
  </error-page>
```

### 事务管理

配置事务bean

```
<!-- 事务配置 -->
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 配置事务增强处理 -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED" rollback-for="Exception"/>
        </tx:attributes>
    </tx:advice>
```

并为每个serviceimpl类添加@Transactional注解

### 服务端的数据校验
为实体类属性上添加NotBlank,@Length,@Email等注解，并设置message值为{xxx.xx.xx}，通过配置文件显示错误信息。
配置ValidationMessage.properties文件，设置键和值对应
配置数据校验

```
<bean id="conversion-Service" class="org.springframework.format.support.FormattingConversionService"></bean>
    <bean id="validateMessageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
        <property name="basename" value="classpath:ValidationMessages"/>
        <property name="fileEncodings" value="utf-8"/>
        <property name="cacheSeconds" value="120"/>
    </bean>
    <bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
        <property name="providerClass" value="org.hibernate.validator.HibernateValidator"/>
        <property name="validationMessageSource" ref="validateMessageSource"/>
    </bean>

    <mvc:annotation-driven conversion-service="conversion-Service" validator="validator" />
```
basename中，属性值为classpath:文件名，不需要添加文件后缀

随后在/login/deal和/regiser/deal路径请求方法参数中在对象Users前添加@Valid注解来验证对象属性，并在其后紧跟BindingResult对象，然后可以设置Errors对象，用来捕获是否存在错误，在最开始判断，如果存在，则直接返回到login页面或register页面，并在对应页面中添加form-errors标签显示错误信息。


### 注解与AOP切面配置日志存储

创建数据库表，以及dao，mapper，service类
RecordLog表的type属性用于存储固定的String字符串值，所以需要设置一个枚举类型，然后将值设置到注解的属性中。
注解创建

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggsType {
    /**
     * 日志枚举类型
     * @return 枚举值
     */
    LoggsTypeE type();
}
```

编写aop切面类
配置织入点，包含除日志service以外的其他service类

```
@Pointcut("execution(* com.fsats.mianshi.service.*.*(..)) && !execution(* com.fsats.mianshi.service.RecordLogService.*(..))")
    public void pointuct(){}
```

设置环绕增强
创建RecordLog实体类，
获得session中的userId

```
HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
```
获得方法名
记录日期
获得ip地址：request.getRemoteAddr()
获得注解中的type值

```
//获得方法参数Class类型的数组
Class[] parameterType = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
//通过方法名和参数列表获得唯一的Method
Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(),parameterType);
```
因为需要获得Method对象，然后判断该方法是否有注解，如果有则获得注解获得type属性值再geetName获得最终值。否则直接设置为null
在catch中设置异常码(时间戳+随机生成)，设置错误消息
最后在finally中执行添加对象。
另配置中开启aop扫描注解和自动配置

	<aop:aspectj-autoproxy/>

