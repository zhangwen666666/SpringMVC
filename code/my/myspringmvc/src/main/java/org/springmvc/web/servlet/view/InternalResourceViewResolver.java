package org.springmvc.web.servlet.view;

import org.springmvc.web.servlet.View;
import org.springmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * 内部资源的视图解析器，可以解析JSP
 */
public class InternalResourceViewResolver implements ViewResolver {
    @Override
    public View resolverViewName(String viewName, Locale locale) throws Exception {
        return null;
    }
}
