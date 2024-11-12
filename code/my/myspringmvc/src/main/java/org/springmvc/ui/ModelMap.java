package org.springmvc.ui;

import java.util.LinkedHashMap;

public class ModelMap extends LinkedHashMap<String, Object> {
    public ModelMap() {
    }

    /**
     * 向域当中存数据
     * @param name
     * @param value
     * @return
     */
    public ModelMap addAttribute(String name, Object value) {
        this.put(name, value);
        return this;
    }
}
