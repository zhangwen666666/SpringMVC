Spring MVC框架：课堂笔记（只记重点）

1. 第一个Spring MVC程序的开发流程
    1.1 创建Maven模块
        第一步：创建一个空的工程springmvc
        第二步：设置JDK版本
        第三步：设置Maven
        第四步：创建Maven模块（我这里创建的是一个普通的Maven模块）
        第五步：在pom文件中设置打包方式：war
        第六步：引入依赖：
            springmvc依赖
            logback依赖
            thymeleaf和spring6整合依赖
            servlet依赖(scope设置为provided，表示这个依赖最终由第三方容器来提供。)

    1.2 给Maven模块添加web支持
        在模块下的src\main目录下新建 webapp目录（默认是带有小蓝点的，没有小蓝点，自己添加Web支持就有小蓝点了。）
        另外，在添加web支持的时候，需要添加web.xml文件，注意添加的路径。

    1.3 在web.xml文件中配置：前端控制器(SpringMVC框架内置的一个类：DispatcherServlet)，所有的请求都应该经过这个DispatcherServlet的处理。
        重点：<url-pattern>/</url-pattern>
        这里的 / 表示：除xx.jsp结尾的请求路径之外的所有请求路径。
        也就是说，只要不是JSP请求路径，那么一定会走DispatcherServlet。

    1.4 编写FirstController，在类上标注 @Controller 注解，纳入IoC容器的管理。
        当然，也可以采用 @Component注解进行标注。 @Controller 只是 @Component 注解的别名。

    1.5 配置/编写 SpringMVC框架自己的配置文件：
        这个配置文件有默认的名字：<servlet-name>-servlet.xml
        这个配置文件有默认的存放位置：WEB-INF 目录下。
        两个配置：
            第一个：配置组件扫描
            第二个：配置视图解析器

    1.6 提供视图
        在/WEB-INF/templates目录下新建 first.thymeleaf 文件
        在该文件中编写符合 Thymeleaf 语法格式的字符串（编写Thymeleaf的模板语句）

    1.7 提供请求映射
        @RequestMapping("/test")
        public String hehe(){
            // 处理业务逻辑....
            // 返回一个逻辑视图名称
            return "first";
        }

        最终会将逻辑视图名称转换为物理视图名称：
        逻辑视图名称：first
        物理视图名称：前缀 + first + 后缀
        最终路径是：/WEB-INF/templates/first.thymeleaf

        使用Thymeleaf模板引擎，将/WEB-INF/templates/first.thymeleaf转换成html代码，最终响应给浏览器。

    1.8 测试
        配置Tomcat服务器
        解决Tomcat服务器控制台日志乱码问题
        启动Tomcat服务器，在浏览器地址栏上直接发送请求：http://localhost:8080/springmvc/test

2. Spring MVC中的配置文件，名字是可以指定的，位置也是可以指定的，怎么指定？
    设置DispatcherServlet的初始化参数：
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--指定了Spring MVC配置文件的名字是: springmvc.xml-->
            <!--指定了Spring MVC配置文件存放的路径是：类的根路径-->
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>

    建议，在web服务器启动的时候，初始化DispatcherServlet，这样用户第一次发送请求时，效率较高。体验好。
    <load-on-startup>1</load-on-startup>

3. @RequestMapping注解可以出现在类上，也可以出现在方法上，例如：

    @Controller
    @RequestMapping("/a")
    public class UserController{
        @RequestMapping("/b")
        public String index(){
            return "index";
        }
    }

    前端浏览器发送的请求路径是 /a/b 的时候，则执行UserController#index()方法。

4. 关于 @RequestMapping 注解的value属性
    value属性本身是一个 String[] 字符串数组，说明多个请求路径可以映射同一个处理器方法。
    如果注解的属性是数组，并且在使用注解的时候，该数组中只有一个元素，大括号可以省略。
    如果使用某个注解的时候，如果只使用了一个value属性，那么value也是可以省略的。
    value属性的别名是path。
    path属性的别名是value。

5. RequestMapping的value属性支持Ant风格的，支持模糊匹配的路径
    ?   表示任意一个字符。（除 / ? 之外的其它字符），注意：一定是一个字符哦。不能空着。
    *   表示0到N个任意字符。（除 / ? 之外的其它字符）。
    **  表示0到N个任意字符。并且路径中可以出现 /
    但是 ** 在使用的时候需要注意，** 左边只能是 /。

    注意：
        如果使用Spring5以及之前的版本，这样写是没问题的：@RequestMapping(value = "/**/testAntValue")
        如果使用Spring6以及之后的版本，这样写是报错的：@RequestMapping(value = "/**/testAntValue")
        在Spring6当中，** 通配符只能作为路径的末尾出现。

6. 关于@RequestMapping注解的value属性上的占位符（重点）

    传统的URL：
        /springmvc/login?username=admin&password=123

    现在的开发比较流行使用RESTFul风格的URL：
        /springmvc/login/admin/123

    在SpringMVC当中，如果请求的URL使用的是RESTFul风格的，那么这个数据应该在java程序中如何获取呢？使用占位符方式。

        @RequestMapping(value = "/login/{a}/{b}")
        public String testRESTFulURL(
                @PathVariable("a")
                String username,
                @PathVariable("b")
                String password){
            System.out.println("用户名：" + username + "，密码：" + password);
            return "ok";
        }

7. 关于@RequestMapping注解的method属性，通过该属性可以限制前端发送的请求方式。如果前端发送的请求方式与后端的处理方式不同，则出现405错误
    @Controller
    public class UserController{
        @RequestMapping(value="/user/login", method=RequestMethod.POST)
        public String userLogin(){
            return "ok";
        }
    }

    表示：当前端发送的请求路径是 /user/login，并且请求方式是POST的时候，才能映射到 UserController#userLogin()方法上。
    只要有一个不满足，则无法映射。例如：请求路径对应不上，或者请求方式对应不上，都是无法映射的。

8.衍生Mapping
    @PostMapping 注解代替的是：@RequestMapping(value="", method=RequestMethod.POST)
    @GetMapping 注解代替的是：@RequestMapping(value="", method=RequestMethod.GET)
    ....
    @PutMapping
    @DeleteMapping
    @PatchMapping

9.web的请求方式
    比较常用的：
        GET POST PUT DELETE HEAD
        GET：适合查询
        POST：适合新增
        PUT：适合修改
        DELETE：适合删除
        HEAD：适合获取响应头信息。

    注意：使用form表单提交时，如果method设置为 put delete head，对不起，发送的请求还是get请求。
    如果要发送put delete head请求，请发送ajax请求才可以。

10. 关于 RequestMapping注解的 params 属性

    @RequestMapping(value="/testParams", params={"username", "password"})
    public String testParams(){
        return "ok";
    }

    当RequestMapping注解中添加了params，则表示又添加了新的约束条件。
    当请求路径是 /testParams，并且请求携带的参数有 username 和 password的时候，才能映射成功！

    关于thymeleaf中怎么发送请求的时候携带数据：
        <a th:href="/testParams?name=value&name=value"></a>
        <a th:href="/testParams(name='admin',password='1234')"></a>

11. 关于RequestMapping注解的headers属性：
    也是一个数组。用来设置请求头的映射。

    @RequestMapping(value="/login", headers={"Referer", "Host"})
    public String testHeaders(){
        return "ok";
    }

    当请求路径是 /login，并且请求头中包含 Referer，也包含Host的时候，映射成功。

12. 获取请求提交的数据
    12.1 第一种方式使用原生的Servlet API
        在处理器的方法参数上提供：HttpServletRequest
        SpringMVC框架会自动将Tomcat服务器创建request对象传递给处理器方法。
        我们直接在处理器方法中使用request对象即可。
        当然，HttpServletResponse，HttpSession有需要的话，也可以采用这种方式注入。

    12.2 第二种方式：使用SpringMVC框架提供的一个注解: @RequestParam（请求参数）
        @RequestParam注解中的属性：
            value属性：value属性可以使用name属性代替
            name属性：name属性可以已使用value属性代替
            required属性：用来设置该参数是否为必须的。默认值是true。默认情况下这个参数是必须要传递过来的。如果前端没有提交这个参数，报错：400错误。
                        这个属性有点类似于 @RequestMapping 注解中的 params 属性的作用。
                        @RequestMapping(value="/testParams", params={"username", "password"})
                        public String testParams(){
                            return "ok";
                        }

                        required属性可以设置为false，这样这个参数就不是必须的了。如果前端没有提供，则不会报400错误。但是由于前端没有提供这个数据，因此程序中的变量值为null
            defaultValue属性：通过defaultValue属性可以给参数赋默认值。如果前端没有提供这样的参数，参数的默认值就起作用了。

    12.3 第三种方式：依靠控制器方法上的形参名来接收
        如果 请求参数名 和 控制器方法上的形参名 保持一致，那么 @RequestParam注解可以省略。

        如果你使用的是Spring6+版本，则需要在pom.xml文件中添加如下编译配置：（Spring5以及之前的版本不需要。）
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>3.12.1</version>
                        <configuration>
                            <source>21</source>
                            <target>21</target>
                            <compilerArgs>
                                <arg>-parameters</arg>
                            </compilerArgs>
                        </configuration>
                    </plugin>
                </plugins>
            </build>

        注意：如果  控制器方法上的形参名   和   请求参数名    没有对应上，那么控制器方法中的形参默认值是null。

    12.4 第四种方式：使用POJO类/JavaBean接收请求参数(这是最常用的)
        底层实现原理：反射机制。
        不过，使用这种方式的前提是：POJO类的属性名必须和请求参数的参数名保持一致
        实现原理是什么？
            假设提交了一个请求，参数名是 username，那么要求POJO类当中必须有一个属性名也叫做：username
            Class clazz = Class.forName("com.powernode.springmvc.pojo.User");
            User user = (User)clazz.newInstance();
            String fieldName = "username";
            String setMethodName = "setUsername";
            Method setMethod = clazz.getDeclaredMethod(setMethodName, ....);
            setMethod.invoke(user, "zhaoliu");

        重点：底层通过反射机制调用set方法给属性赋值的。所以set方法的方法名非常重要。
        如果前端提交了参数是： username=zhangsan
        那么必须保证POJO类当中有一个方法名叫做：setUsername

        如果前端提交了参数是： email=zhangsan@powernode.com
        那么必须保证POJO类当中有一个方法名叫做：setEmail

        如果没有对应的set方法，将无法给对应的属性赋值。

13. 获取请求头信息？
    使用 @RequestHeader 注解，它用来标注 形参。作用是：将  请求头信息  映射到   控制器方法的形参 上。
        @PostMapping("/user/reg")
        public String register(User user,
                               @RequestHeader(value = "Referer", required = false, defaultValue = "")
                               String referer){
            System.out.println(user);
            System.out.println(referer);
            return "ok";
        }

14. 获取客户端提交的Cookie？
    使用 @CookieValue 注解标注控制器方法上的形参。
        @RequestMapping("/user/reg")
        public String register(User user,
                               @CookieValue(value = "id", required = false, defaultValue = "")
                               String id){
            System.out.println("客户端提交过来的cookie，它的值是：" + id);
            return "ok";
        }

15. 关于javaweb项目中，get请求的乱码问题？

    get请求，提交的数据是在浏览器的地址栏上回显。在请求行上提交数据，例如：/springmvc/login?username=张三&password=123

    怎么解决get请求乱码问题呢？
        对URI进行编码设置，在哪儿可以设置URI的编码方式呢？在Tomcat服务器的配置CATALINA_HOME/conf/server.xml文件中：
            <Connector port="8080" protocol="HTTP/1.1"
                       connectionTimeout="20000"
                       redirectPort="8443"
                       maxParameterCount="1000"
                       URIEncoding="UTF-8"
                       />

    对于Tomcat10和Tomcat9来说：get请求没有乱码。也就是说Tomcat10或者Tomcat9已经自动对URI进行编码，并且默认的编码方式就是UTF-8
    但是对于Tomcat8来说，URIEncoding的默认值是ISO-8859-1编码方式，所以在Tomcat8中，get请求是存在中文乱码问题的，怎么解决？如上所描述。

16. 关于javaweb项目中，post请求的乱码问题？

    post请求乱码如何解决？
        request.setCharacterEncoding("UTF-8");
        但是有一个前提： request.setCharacterEncoding("UTF-8"); 这一行代码必须在 String username = request.getParameter("username"); 方法执行之前执行，才有效。

    在Tomcat10当中，我们是不需要考虑post请求乱码问题，因为Tomcat10，已经自动帮助我们执行了： request.setCharacterEncoding("UTF-8");
    在哪里可以看到呢？
        在CATALINA_HOME/conf/web.xml文件中有这样的配置：
            <request-character-encoding>UTF-8</request-character-encoding>
            <response-character-encoding>UTF-8</response-character-encoding>
            这个配置信息表示：请求体采用UTF-8的方式，另外响应的时候也采用UTF-8的方式，所以POST请求无乱码，响应也没有乱码。

    注意了：对于Tomcat9以及之前的版本来说，没有以上的配置。POST请求乱码问题，响应的乱码问题都需要自行解决。

    那么如果遇到Tomcat9- 版本，那么POST请求乱码应该怎么解决呢？对于SpringMVC来说，有什么好的办法吗？
        在 request.getParameter() 方法执行之前，执行: request.setCharacterEncoding("UTF-8"); ，这样问题就能解决。
        第一种方案：自己编写一个过滤器！！！！过滤器Filter在Servlet执行之前执行。
        第二种方案：使用SpringMVC框架内置的字符编码过滤器即可：CharacterEncodingFilter。

17. request域：
    第一种方式：在SpringMVC中使用原生的Servlet API可以完成request域数据共享：
        在处理器方法上添加 HttpServletRequest参数即可。

    第二种方式：在SpringMVC的处理器方法的参数上添加一个接口类型：Model
        @RequestMapping("/testModel")
        public String testModel(Model model){
            // 向request域当中存储数据
            model.addAttribute("name", value);
            // 转发
            return "ok";
        }

        MVC架构模式：
            M: Model（模型，本质就是数据）
            V: View
            C: Controller
    第三种方式：在SpringMVC的处理器方法的参数上添加一个接口类型：Map
        @RequestMapping("/testMap")
        public String testMap(Map<String, Object> map){
            // 向request域当中存储数据
            map.put("testRequestScope", "在SpringMVC当中使用Map接口完成request域数据共享");
            // 转发
            return "ok";
        }

    第四种方式：在SpringMVC的处理器方法的参数上添加一个类型：ModelMap
        @RequestMapping("/testModelMap")
        public String testModelMap(ModelMap modelMap){
            // 向request域当中存储数据
            modelMap.addAttribute("testRequestScope", "在SpringMVC当中使用ModelMap类完成request域数据共享");
            // 转发
            return "ok";
        }

    研究一下：Model接口、Map接口、ModelMap类，三者之间的关系？
        表面上使用的是不同的接口和不同的类。实际上底层都使用了同一个对象：org.springframework.validation.support.BindingAwareModelMap

        BindingAwareModelMap继承了ExtendedModelMap类
        ExtendedModelMap继承了ModelMap类
        ExtendedModelMap实现了Model接口
        ModelMap类继承了LinkedHashMap继承了HashMap实现了Map接口

    第五种方式：使用Spring MVC框架提供的ModelAndView类完成request域数据共享。
        @RequestMapping("/testModelAndView")
        public ModelAndView testModelAndView(){
            // 创建 模型视图 对象
            ModelAndView mav = new ModelAndView();
            // 给 模型视图对象 绑定数据
            mav.addObject("testRequestScope", "在SpringMVC当中使用ModelAndView类完成request域数据共享");
            // 给 模型视图对象 绑定视图（绑定逻辑视图名称）
            mav.setViewName("ok");
            // 返回 模型视图对象
            return mav;
        }

    我们聊一个真相：
        对于处理器方法来说，不管你使用的参数是Model接口，还是Map接口，还是ModelMap类，还是ModelAndView类，最终处理器方法执行结束之后，
        返回的都是ModelAndView对象。这个返回的ModelAndView对象给DispatcherServlet类了。

        当请求路径不是JSP的时候，都会走前端控制器DispatcherServlet。
        DispatcherServlet中有一个核心方法 doDispatch()，这个方法用来通过请求路径找到对应的 处理器方法
        然后调用 处理器方法，处理器方法返回一个逻辑视图名称（可能也会直接返回一个ModelAndView对象）,底层会
        将逻辑视图名称转换为View对象，然后将View对象结合Model对象，封装一个ModelAndView对象，然后将该对象
        返回给DispatcherServlet类了。

18. session域

    第一种方式：使用原生的Servlet API实现。（在处理器方法的参数上添加一个 HttpSession 参数，SpringMVC会自动将session对象传递给这个参数。）
    第二种方式：使用@SessionAttributes注解标注Controller

19. application域
    这个域使用较少，如果使用的话，一般是采用ServletAPI的方式使用。
    @RequestMapping("/testApplicationScope")
    public String testApplicationScope(HttpServletRequest request){
        ServletContext application = request.getServletContext();
        application.setAttribute("testApplicationScope", "在SpringMVC中使用ServletAPI实现application域共享");
        return "ok";
    }

20. SpringMVC中常用的视图：
    InternalResourceView：内部资源视图（是SpringMVC内置的，专门负责解析JSP模板语法的，另外也负责 转发forward 功能的实现）
    RedirectView：重定向视图（是SpringMVC内置的，专门负责 重定向redirect 功能的实现）
    ThymeleafView：Thymeleaf视图（是第三方的，专门负责解析Thymeleaf模板语法的）
    ......

21. 实现视图机制的核心类与核心接口
    1. DispatcherServlet：前端控制器
        负责接收前端的请求 （/login）
        根据请求路径找到对应的处理器方法 （UserController#login()）
        执行处理器方法 （执行 UserController#login()）
        并且最终返回ModelAndView对象。
        再往下就是处理视图。
    2. ViewResolver接口：视图解析器接口 （ThymeleafViewResolver实现了ViewResolver接口、InternalResourceViewResolver也是实现了ViewResolver接口....）
        这个接口做什么事儿？
            这个接口作用是将 逻辑视图名称 转换为 物理视图名称。
            并且最终返回一个View接口对象。
        核心方法是什么？
            View resolveViewName(String viewName, Locale locale) throws Exception;
    3. View接口：视图接口 （ThymeleafView实现了View接口、InternalResourceView也实现了View接口.....）
        这个接口做什么事儿？
            这个接口负责将模板语法的字符串转换成html代码，并且将html代码响应给浏览器。（渲染。）
        核心方法是什么？
            void render(@Nullable Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

22. 在SpringMVC中是怎么通过代码完成转发的？怎么完成重定向的？
    @RequestMapping("/a")
    public String toA(){
        // 返回的是一个逻辑视图名称
        return "a";
    }
    注意：当 return "a"; 的时候，返回了一个逻辑视图名称。这种方式跳转到视图，默认采用的就是 forward 方式跳转过去的。只不过这个底层创建的视图对象：ThymeleafView

    怎么转发？语法格式是什么呢？
        return "forward:/b"; 转发到 /b，这是一次请求，底层创建的视图对象是：InternalResourceView对象。

        "forward:/b" 这个已经不是逻辑视图名称了。是以转发的方式跳转，是一个资源的路径。不能随便写，以 forward: 开始。

    怎么重定向？语法格式是什么呢？
        return "redirect:/b"; 转发到 /b，这是两次请求，底层创建的视图对象是：RedirectView对象。
        注意语法：必须以 redirect: 开始。

    总结：
        转发： return "forward:/b" 底层创建的是InternalResourceView对象
        重定向： return "redirect:/b" 底层创建的是RedirectView对象

23. <mvc:view-controller>
    这个配置信息，可以在springmvc.xml文件中进行配置，作用是什么？
        如果一个Controller上的方法只是为了完成视图跳转，没有任何业务代码，那么这个Controller可以不写。
        直接在 springmvc.xml 文件中添加 <mvc:view-controller> 即可。

        <mvc:view-controller path="/test" view-name="test"/>
        表示发送的请求路径是 /test，跳转的视图页面是： 前缀+test+后缀

24. <mvc:annotation-driven/>
    这个配置信息叫做开启注解驱动。在springmvc.xml文件中配置。
    当你使用了 <mvc:view-controller> 配置，会让你整个项目中所有的注解全部失效，你需要使用以上的配置来再次开启注解。

    <mvc:annotation-driven/>

25. 关于静态资源处理：
    假设我们在webapp目录下有static目录，static目录下有touxiang.jpeg图片。
    我们可以在浏览器地址栏上直接访问：http://localhost:8080/springmvc/static/img/touxiang.jpeg 吗？不行。
    因为会走DispatcherServlet，导致发生404错误。

    怎么办？两种解决方案：
        第一种解决方案：开启默认的Servlet处理
            在Tomcat服务器中提供了一个默认的Servlet，叫做：org.apache.catalina.servlets.DefaultServlet
            在CATALINA_HOME/conf/web.xml文件中，有这个默认的Servlet的相关配置。
            不过，这个默认的Servlet默认情况下是不开启的。
            你需要在springmvc.xml文件中使用以下配置进行开启：
                <mvc:default-servlet-handler/>
                <mvc:annotation-driven/>
            开启之后的作用是，当你访问 http://localhost:8080/springmvc/static/img/touxiang.jpeg的时候，
            默认先走 DispatcherServlet，如果发生404错误的话，会自动走DefaultServlet，然后DefaultServlet
            帮你定位静态资源。

        第二种解决方案：配置静态资源处理，在springmvc.xml文件中添加如下配置：
            <mvc:resources mapping="/static/**" location="/static/" />
            <mvc:annotation-driven/>

            当请求路径符合 /static/** 的时候，去 /static/ 位置找资源。

26. 什么是RESTFul？
    RESTful是对WEB服务接口的一种设计风格，提供了一套约束，可以让WEB服务接口更加简洁、易于理解。
    REST对请求方式的约束是这样的：
        查询get
        新增POST
        删除delete
        修改put
    REST对URL的约束是这样的：
        GET /user/1 查一个
        GET /user 查所有
        POST /user 新增
        PUT /user 修改
        DELETE /user/1 删除

    RESTful：是表述性状态转移。
    本质上：通过 URL + 请求方式 来控制服务器端数据的变化。

27. RESTful编程风格中要求，修改的时候，必须提交PUT请求，怎么提交PUT请求呢？
    第一步：要想发送PUT请求，首先你必须是一个POST请求。（POST请求是大前提）
    第二步：在POST请求的表单中添加隐藏域：
        <!--隐藏域-->
        <input type="hidden" name="_method" value="put">
        强调：name必须是 _method，value必须是put/PUT。
        如果你要发送delete请求的话,value写delete即可。
    第三步：添加一个过滤器
        <!--添加一个过滤器，这个过滤器是springmvc提前写好的，直接用就行了，这个过滤器可以帮助你将请求POST转换成PUT请求/DELETE请求-->
        <filter>
            <filter-name>hiddenHttpMethodFilter</filter-name>
            <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>hiddenHttpMethodFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>

28. 在SpringMVC中如何使用原生ServletAPI完成AJAX请求的响应？
        @GetMapping("/ajax")
        public void ajax(HttpServletResponse response) throws IOException {
            PrintWriter out = response.getWriter();
            out.print("hello ajax, my name is Spring MVC2!");
        }

        @GetMapping("/ajax")
        public String ajax(HttpServletResponse response) throws IOException {
            PrintWriter out = response.getWriter();
            out.print("hello ajax, my name is Spring MVC!");
            return null;
        }

29. @ResponseBody 注解（非常重要，使用非常多，因为以后大部分的请求都是AJAX请求）
    @GetMapping("/ajax")
    @ResponseBody
    public String ajax(){
        return "hello ajax, my name is Spring MVC!";
    }

    重点：一旦处理器方法上添加了 @ResponseBody 注解，那么 return 返回语句，返回的将不是一个 “逻辑视图名称” 了。而是直接将返回结果采用字符串的形式响应给浏览器。
    底层实现原理实际上代替的就是：
        PrintWriter out = response.getWriter();
        out.print("hello ajax, my name is Spring MVC!");

    以上程序使用的HTTP消息转换器是：StringHttpMessageConverter。


    @GetMapping("/ajax")
    @ResponseBody
    public User ajax() {
        User user = new User(111222L, "zhangsan", "123");
        return user;
    }
    当一个处理器方法上面有 @ResponseBody注解，并且返回的是一个java对象，例如user，那么springmvc框架，会自动将user对象转换成json格式的字符串，响应给浏览器。
    当然，你必须要在pom.xml文件中引入一个可以处理json的依赖，例如jackson：
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.0</version>
        </dependency>

    以上程序中使用的消息转换器是：MappingJackson2HttpMessageConverter

30. 非常好用的注解：@RestController
    它出现在类上。等于 @Controller + @ResponseBody
    @RestController 它是一个复合注解。
    当一个类上添加 @RestController 注解之后，表示该类上自动添加了 @Controller注解，并且该类中所有的方法上都会自动添加 @ResponseBody 注解。

31. 关于 @RequestBody 注解
    * 该注解只能使用在处理器方法的形参上。
    * 这个注解的作用是直接将请求体传递给Java程序，在Java程序中可以直接使用一个String类型的变量接收这个请求体的内容。
    * 底层使用的HTTP消息转换器是：FormHttpMessageConverter

    关于@RequestBody 注解的重要用法：如果前端请求体当中提交的数据是JSON格式，那么 @RequestBody 可以将提交的JSON格式的字符串转换成java对象。
    注意：同样需要使用jackson的依赖。
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.0</version>
        </dependency>
    然后，要注意@RequestBody标注在处理器方法的形参上，也就是说形参只要准备一个user对象就行了，前端提交一个json字符串，直接将其转换成java对象user
    以上前端请求体提交JSON格式的字符串，那么后端直接将json格式字符串转换成java对象，这里使用的消息转换器是：MappingJackson2HttpMessageConverter

32. RequestEntity
    这个类的实例封装了整个请求协议。
    SpringMVC自动创建好，传递给处理器方法的参数上。
    你只需要在处理器方法的参数上加上: (RequestEntity requestEntity)即可，SpringMVC自动创建好该对象，传递到处理器方法的参数上。
    通过它可以获取请求协议中任何信息，包括：请求方法、请求头、请求体。

33. ResponseEntity
    ResponseEntity不是注解，是一个类。用该类的实例可以封装响应协议，包括：状态行、响应头、响应体。也就是说：如果你想定制属于自己的响应协议，可以使用该类。
    注意：如果你要定制属于自己的响应协议，那么处理器方法的返回值类型必须是：ResponseEntity<User>，泛型为什么是User，因为响应体中的内容是user信息。

34. 文件上传
    文件上传必须是post请求。
    文件上传的form标签中必须使用 enctype="multipart/form-data"
    enctype是用来设置请求头的内容类型的。默认值是：enctype="application/x-www-form-urlencoded"
    文件上传的组件是：<input type="file" name="fileName">
    注意：如果你用的是spring6，那么需要在web.xml文件的DispatcherServlet中进入如下的配置：
        <multipart-config>
            <!--设置单个支持最大文件的大小-->
            <max-file-size>102400</max-file-size>
            <!--设置整个表单所有文件上传的最大值-->
            <max-request-size>102400</max-request-size>
            <!--设置最小上传文件大小-->
            <file-size-threshold>0</file-size-threshold>
        </multipart-config>

    文件上传：浏览器端向服务器端发送文件，最终服务器将文件保存到服务器上。（本质上还是IO流，读文件和写文件。）

    SpringMVC专门为文件上传准备了一个类：MultipartFile multipartFile.
    这个类怎么理解？这个类就代表你从客户端传过来的那个文件。

    multipartFile.getName(); 获取请求参数的name
    multipartFile.getOriginalFilename(); 获取文件的真实名字

35. 文件下载，代码非常固定
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws IOException {
        File file = new File(request.getServletContext().getRealPath("/upload") + "/touxiang.jpeg");
        // 创建响应头对象
        HttpHeaders headers = new HttpHeaders();
        // 设置响应内容类型
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 设置下载文件的名称
        headers.setContentDispositionFormData("attachment", file.getName());

        // 下载文件
        return new ResponseEntity<byte[]>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);
    }
