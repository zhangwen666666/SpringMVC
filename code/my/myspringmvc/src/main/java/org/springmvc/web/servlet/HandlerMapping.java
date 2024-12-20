package org.springmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 处理器映射器接口
 */
public interface HandlerMapping {
    /**
     * 根据请求（请求路径和请求凡事）返回处理器执行链对象
     * @param request
     * @return
     * @throws Exception
     */
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
