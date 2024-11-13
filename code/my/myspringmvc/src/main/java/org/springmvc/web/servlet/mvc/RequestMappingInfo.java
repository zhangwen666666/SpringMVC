package org.springmvc.web.servlet.mvc;

import java.util.Objects;

/**
 * 请求映射信息，包含请求路径，请求方式
 */
public class RequestMappingInfo {
    private String requestURI;
    private String method;

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RequestMappingInfo() {
    }

    public RequestMappingInfo(String requestURI, String method) {
        this.requestURI = requestURI;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(requestURI, that.requestURI) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestURI, method);
    }
}
