package org.springmvc.web.servlet.view;

import org.springmvc.web.servlet.View;
import org.springmvc.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * 内部资源的视图解析器，可以解析JSP
 */
public class InternalResourceViewResolver implements ViewResolver {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public InternalResourceViewResolver(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public InternalResourceViewResolver() {
    }

    @Override
    public View resolverViewName(String viewName, Locale locale) throws Exception {
        // 创建视图对象
        return new InternalResourceView("text/html;charset=UTF-8", prefix + viewName + suffix);
    }
}
