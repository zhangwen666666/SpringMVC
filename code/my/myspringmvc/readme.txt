SpringMVC流程中重要的接口和类都有哪些？

1. 类 DispatcherServlet extends HttpServlet
    所有的Servlet都要实现Servlet接口，或者直接继承HttpServlet，这是JavaWeb规范中的
    重写service(HttpServletRequest request, HttpServletResponse response)

2. HandlerExecutionChain 类
    根据请求获取处理器执行链，因此需要处理器执行链，这里核心的属性是handler处理器、
    拦截器集合interceptorList、拦截器下标interceptorIndex。

3. HandlerMapping 处理器映射器接口 ，根据请求的URI找到对应的Controller,
    即通过URI找到要执行的HandlerMethod

4. HandlerMapping 接口的实现类有很多，其中专门为
   @RequestMapping注解服务的处理器映射器：RequestMappingHandlerMapping

5. RequestMappingHandlerMapping 类

6. HandlerMethod 类 ，处理器方法
    handler处理器实际上是一个HandlerMethod对象。

7. HandlerInterceptor 拦截器接口

8. HandlerAdapter 处理器适配器接口
    这个接口下有很多实现类，其中有一个实现类，是专门给 @RequestMapping 注解使用的。

9. HandlerAdapter 接口实现类 RequestMappingHandlerAdapter

10. ModelAndView 类
    通过ha(HandlerAdapter接口实现类)调用handler方法，返回一个ModelAndView对象

11. ViewResolver 接口
    JSP模版引擎对应的ViewResolver接口的实现类：InternalResourceViewResolver

12. InternalResourceViewResolver 类

13. View 接口
    JSP模版引擎对应的View接口的实现类：InternalResourceView

14. InternalResourceView 类

15. @Controller注解

15. @RequestMapping注解