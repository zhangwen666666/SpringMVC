package org.springmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 处理器适配器接口
 */
public interface HandlerAdapter {
    /**
     * 调用处理器方法
     * @param request
     * @param response
     * @param handler
     * @return 数据和视图对象
     * @throws Exception
     */
    ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
