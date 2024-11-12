package org.springmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import org.springmvc.web.servlet.HandlerExecutionChain;
import org.springmvc.web.servlet.HandlerMapping;

/**
 * 专门为@RequestMapping注解服务的处理器映射器
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return null;
    }
}
