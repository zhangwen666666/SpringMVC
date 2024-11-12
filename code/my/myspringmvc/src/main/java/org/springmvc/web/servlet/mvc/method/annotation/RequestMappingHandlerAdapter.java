package org.springmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springmvc.web.servlet.HandlerAdapter;
import org.springmvc.web.servlet.ModelAndView;

/**
 * 处理器适配器，专门为 @RequestMapping准备的处理器适配器
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return null;
    }
}
