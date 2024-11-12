package org.springmvc.web.servlet;

import java.util.Locale;

/**
 * 视图解析器接口
 */
public interface ViewResolver {
    /**
     * 根据逻辑视图名转换为物理视图名，并且返回视图对象
     * @param viewName
     * @param locale
     * @return
     * @throws Exception
     */
    View resolverViewName(String viewName, Locale locale) throws Exception;
}
