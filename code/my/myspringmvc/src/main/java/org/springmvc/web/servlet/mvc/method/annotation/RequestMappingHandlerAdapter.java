package org.springmvc.web.servlet.mvc.method.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springmvc.ui.ModelMap;
import org.springmvc.web.method.HandlerMethod;
import org.springmvc.web.servlet.HandlerAdapter;
import org.springmvc.web.servlet.ModelAndView;

import java.lang.reflect.Method;

/**
 * 处理器适配器，专门为 @RequestMapping准备的处理器适配器
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 调用处理器方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取处理器方法的Controller对象
        Object controller = handlerMethod.getHandler();
        // 获取要调用的方法
        Method method = handlerMethod.getMethod();
        // 调用该方法(我们规定该方法必须有一个ModelMap参数，返回值必须是String)
        ModelMap modelMap = new ModelMap();
        String viewName = (String) method.invoke(controller,modelMap);
        // 封装ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        modelAndView.setModel(modelMap);


        /*ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("username","zhangsan");
        modelAndView.setModel(modelMap);
        modelAndView.setViewName("index");*/
        return modelAndView;
    }
}
