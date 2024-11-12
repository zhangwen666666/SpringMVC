package org.springmvc.web.servlet;

import org.springmvc.ui.ModelMap;

/**
 * 模型与视图对象
 */
public class ModelAndView {
    private Object view;
    private ModelMap modelMap;

    public ModelAndView() {
    }

    public ModelAndView(Object view, ModelMap modelMap) {
        this.view = view;
        this.modelMap = modelMap;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public ModelMap getModelMap() {
        return modelMap;
    }

    public void setModelMap(ModelMap modelMap) {
        this.modelMap = modelMap;
    }
}
