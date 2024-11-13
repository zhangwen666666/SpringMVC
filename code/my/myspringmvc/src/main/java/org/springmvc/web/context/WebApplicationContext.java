package org.springmvc.web.context;

import jakarta.servlet.ServletContext;
import org.springmvc.context.ApplicationContext;

public class WebApplicationContext extends ApplicationContext {
    private ServletContext servletContext;
    private String springMvcConfigPath;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getSpringMvcConfigPath() {
        return springMvcConfigPath;
    }

    public void setSpringMvcConfigPath(String springMvcConfigPath) {
        this.springMvcConfigPath = springMvcConfigPath;
    }

    public WebApplicationContext(String springMvcConfigPath, ServletContext servletContext) {
        super(springMvcConfigPath);
        this.servletContext = servletContext;
    }
}
