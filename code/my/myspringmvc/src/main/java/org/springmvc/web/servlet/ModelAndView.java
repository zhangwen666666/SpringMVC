package org.springmvc.web.servlet;

import org.springmvc.ui.ModelMap;

/**
 * 模型与视图对象
 */
public class ModelAndView {
    private Object view;
    private ModelMap model;

    public ModelAndView() {
    }

    public ModelAndView(Object view, ModelMap model) {
        this.view = view;
        this.model = model;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public ModelMap getModel() {
        return model;
    }

    public void setModel(ModelMap model) {
        this.model = model;
    }

    public void setViewName(String viewName){
        setView(viewName);
    }
}
