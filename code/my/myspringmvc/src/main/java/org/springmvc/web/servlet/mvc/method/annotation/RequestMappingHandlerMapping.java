package org.springmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.springmvc.web.constant.Const;
import org.springmvc.web.context.WebApplicationContext;
import org.springmvc.web.method.HandlerMethod;
import org.springmvc.web.servlet.HandlerExecutionChain;
import org.springmvc.web.servlet.HandlerInterceptor;
import org.springmvc.web.servlet.HandlerMapping;
import org.springmvc.web.servlet.mvc.RequestMappingInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专门为@RequestMapping注解服务的处理器映射器
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    /**
     * 处理器映射器主要就是通过以下Map集合进行映射
     * key是请求信息，value是其对应的处理器方法
     */
    private Map<RequestMappingInfo, HandlerMethod> map = new HashMap<>();

    public RequestMappingHandlerMapping(Map<RequestMappingInfo, HandlerMethod> map) {
        this.map = map;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        // 通过request对象获取请求路径和请求方式，封装为RequestMappingInfo对象
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getServletPath(), request.getMethod());
        // 创建处理器执行链
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain();
        // 获取请求对应的HandlerMethod
        HandlerMethod handler = map.get(requestMappingInfo);
        // 给执行链设置HandlerMethod
        handlerExecutionChain.setHandler(handler);
        // 获取拦截器
        WebApplicationContext webApplicationContext = (WebApplicationContext) request.getServletContext().getAttribute(Const.WEB_APPLICATION_CONTEXT);
        List<HandlerInterceptor> interceptors = (List<HandlerInterceptor>) webApplicationContext.getBean(Const.INTERCEPTORS);
        // 给执行链设置拦截器
        handlerExecutionChain.setInterceptorList(interceptors);
        return handlerExecutionChain;
    }
}
