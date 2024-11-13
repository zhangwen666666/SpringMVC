package org.springmvc.web.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springmvc.web.constant.Const;
import org.springmvc.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

/**
 * 前端控制器
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * 视图解析器
     */
    private ViewResolver viewResolver;

    /**
     * 处理器映射器
     */
    private HandlerMapping handlerMapping;

    /**
     * 处理器适配器
     */
    private HandlerAdapter handlerAdapter;

    @Override
    public void init() throws ServletException {
        // 找到springmvc.xml文件
        ServletConfig servletConfig = this.getServletConfig();
        String contextConfigLocation = servletConfig.getInitParameter(Const.CONTEXT_CONFIG_LOCATION);
        // System.out.println("springmvc.xml文件：" + contextConfigLocation);
        String path = null;
        if (contextConfigLocation.trim().startsWith(Const.PREFIX_CLASSPATH)) {
            String configName = contextConfigLocation.substring(Const.PREFIX_CLASSPATH.length());
            path = this.getClass().getClassLoader().getResource(configName).getPath();
            // System.out.println(path);
        }

        // 初始化Spring Web容器
        WebApplicationContext webApplicationContext = new WebApplicationContext(path, this.getServletContext());
        this.getServletContext().setAttribute(Const.WEB_APPLICATION_CONTEXT, webApplicationContext);

        // 初始化处理器映射器
        this.handlerMapping = (HandlerMapping) webApplicationContext.getBean(Const.HANDLER_MAPPING);
        // 初始化处理器适配器
        this.handlerAdapter = (HandlerAdapter) webApplicationContext.getBean(Const.HANDLER_ADAPTER);
        // 初始化视图解析器
        this.viewResolver = (ViewResolver) webApplicationContext.getBean(Const.VIEW_RESOLVER);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }


    /**
     * 前端控制器中最核心的方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 根据请求对象获取处理器执行链
        try {
            HandlerExecutionChain mappedHandler = handlerMapping.getHandler(request);
            // System.out.println(mappedHandler);

            // 2. 根据处理器方法获取对应的处理器适配器对象
            HandlerAdapter ha = this.handlerAdapter;

            // 3. 执行拦截器中的preHandle
            if (!mappedHandler.applyPreHandle(request, response)) {
                return;
            }

            // 4. 执行处理器方法，返回ModelAndView
            ModelAndView mv = ha.handler(request, response, mappedHandler.getHandler());

            // 5. 执行拦截器中的postHandle方法
            mappedHandler.applyPostHandler(request, response, mv);

            // 6. 响应
            View view = viewResolver.resolverViewName(mv.getView().toString(), Locale.CHINA);
            view.render(mv.getModel(), request, response);

            // 7. 执行拦截器中的afterCompletion方法
            mappedHandler.triggerAfterCompletion(request, response, null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
