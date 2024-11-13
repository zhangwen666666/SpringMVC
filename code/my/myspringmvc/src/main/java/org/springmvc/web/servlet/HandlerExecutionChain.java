package org.springmvc.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 处理器执行链
 */
public class HandlerExecutionChain {
    /**
     * 处理器方法：实际上底层对象是 HandlerMethod对象
     */
    private Object handler;

    /**
     * 本次请求需要执行的拦截器
     */
    private List<HandlerInterceptor> interceptorList;

    private int interceptorIndex = -1;

    public HandlerExecutionChain() {
    }

    public HandlerExecutionChain(Object handler, List<HandlerInterceptor> interceptorList) {
        this.handler = handler;
        this.interceptorList = interceptorList;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public List<HandlerInterceptor> getInterceptorList() {
        return interceptorList;
    }

    public void setInterceptorList(List<HandlerInterceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }

    public int getInterceptorIndex() {
        return interceptorIndex;
    }

    public void setInterceptorIndex(int interceptorIndex) {
        this.interceptorIndex = interceptorIndex;
    }

    /**
     * 执行所有的preHandle方法
     *
     * @param request
     * @param response
     * @return
     */
    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) {
        for (int i = 0; i < interceptorList.size(); i++) {
            HandlerInterceptor handlerInterceptor = interceptorList.get(i);
            try {
                boolean result = handlerInterceptor.preHandle(request, response, handler);
                if (!result) {
                    // 执行拦截器的afterCompletion
                    triggerAfterCompletion(request,response,null);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            interceptorIndex = i;
        }
        return true;
    }

    public void applyPostHandler(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
        for (int i = interceptorList.size() - 1; i >= 0; i--) {
            HandlerInterceptor handlerInterceptor = interceptorList.get(i);
            try {
                handlerInterceptor.postHandle(request, response, handler, mv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object o) {
        for (int i = interceptorIndex; i >= 0; i--){
            HandlerInterceptor handlerInterceptor = interceptorList.get(i);
            try {
                handlerInterceptor.afterCompletion(request,response,handler,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
