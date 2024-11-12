package org.springmvc.web.method;

import java.lang.reflect.Method;

/**
 * 处理器方法
 */
public class HandlerMethod {
    /**
     * 真正的处理器对象
     */
    private Object handler;

    /**
     * 处理器方法
     */
    private Method method;

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HandlerMethod(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public HandlerMethod() {
    }
}
