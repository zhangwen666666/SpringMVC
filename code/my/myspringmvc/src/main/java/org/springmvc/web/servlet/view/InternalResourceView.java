package org.springmvc.web.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springmvc.web.servlet.View;

import java.util.Map;

/**
 * 视图接口的实现类
 */
public class InternalResourceView implements View {
    private String contentType;
    private String path;

    public InternalResourceView() {
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InternalResourceView(String contentType, String path) {
        this.contentType = contentType;
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置相应的内容类型
        response.setContentType(contentType);
        // 将model数据存储到request域当中
        model.forEach(request::setAttribute);
        // 转发
        request.getRequestDispatcher(path).forward(request, response);
    }
}
