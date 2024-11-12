package org.springmvc.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 前端控制器
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        // 初始化Spring Web容器

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }


    /**
     * 前端控制器中最核心的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
