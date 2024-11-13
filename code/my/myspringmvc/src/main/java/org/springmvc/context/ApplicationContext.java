package org.springmvc.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springmvc.stereotype.Controller;
import org.springmvc.web.bind.annotation.RequestMapping;
import org.springmvc.web.bind.annotation.RequestMethod;
import org.springmvc.web.constant.Const;
import org.springmvc.web.method.HandlerMethod;
import org.springmvc.web.servlet.HandlerAdapter;
import org.springmvc.web.servlet.HandlerInterceptor;
import org.springmvc.web.servlet.HandlerMapping;
import org.springmvc.web.servlet.mvc.RequestMappingInfo;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private Map<String, Object> beanMap = new HashMap<>();

    public ApplicationContext(String xmlPath) {
        try {
            // 解析xml文件
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(xmlPath));

            // 组件扫描
            Element componentScanElement = (Element) document.selectSingleNode("/beans/component-scan");
            Map<RequestMappingInfo, HandlerMethod> map = componentScan(componentScanElement);

            // 创建视图解析器
            Element viewResolverElt = (Element) document.selectSingleNode("/beans/bean");
            createViewResolver(viewResolverElt);
            // System.out.println(beanMap);

            // 创建拦截器
            Element interceptorsElt = (Element) document.selectSingleNode("/beans/interceptors");
            createInterceptors(interceptorsElt);
            // System.out.println(beanMap);

            // 创建所有的HandlerMapping
            createHandlerMapping(Const.DEFAULT_PACKAGE, map);

            // 创建所有的HandlerAdapter
            createHandlerAdapter(Const.DEFAULT_PACKAGE);
            // System.out.println(beanMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createHandlerAdapter(String defaultPackage) {
        // System.out.println(defaultPackage);
        String defaultPath = defaultPackage.replace('.', '/');
        // System.out.println(defaultPath);
        String absolutePath = Thread.currentThread().getContextClassLoader().getResource(defaultPath).getPath();
        // System.out.println(absolutePath);
        File file = new File(absolutePath);
        File[] files = file.listFiles();
        for (File f : files) {
            String classFileName = f.getName();
            String simpleClassName = classFileName.substring(0, classFileName.lastIndexOf("."));
            // System.out.println(simpleClassName);
            String className = defaultPackage + "." + simpleClassName;
            // System.out.println(className);
            try {
                Class<?> clazz = Class.forName(className);
                if (HandlerAdapter.class.isAssignableFrom(clazz)) {
                    Object bean = clazz.newInstance();
                    beanMap.put(Const.HANDLER_ADAPTER, bean);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createHandlerMapping(String defaultPackage, Map<RequestMappingInfo, HandlerMethod> map) {
        // System.out.println(defaultPackage);
        String defaultPath = defaultPackage.replace('.', '/');
        // System.out.println(defaultPath);
        String absolutePath = Thread.currentThread().getContextClassLoader().getResource(defaultPath).getPath();
        // System.out.println(absolutePath);
        File file = new File(absolutePath);
        File[] files = file.listFiles();
        for (File f : files) {
            String classFileName = f.getName();
            String simpleClassName = classFileName.substring(0, classFileName.lastIndexOf("."));
            // System.out.println(simpleClassName);
            String className = defaultPackage + "." + simpleClassName;
            // System.out.println(className);
            try {
                Class<?> clazz = Class.forName(className);
                if (HandlerMapping.class.isAssignableFrom(clazz)) {
                    // Object bean = clazz.newInstance();
                    Constructor<?> constructor = clazz.getDeclaredConstructor(Map.class);
                    Object bean = constructor.newInstance(map);
                    beanMap.put(Const.HANDLER_MAPPING, bean);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createInterceptors(Element interceptorsElt) {
        // 准备一个List集合存储拦截器对象
        List<HandlerInterceptor> interceptors = new ArrayList<>();
        List<Element> beans = interceptorsElt.elements("bean");
        for (Element beanElt : beans) {
            String className = beanElt.attributeValue(Const.BEAN_TAG_CLASS_ATTRIBUTE);
            // System.out.println(className);
            try {
                Class<?> clazz = Class.forName(className);
                Object interceptor = clazz.newInstance();
                interceptors.add((HandlerInterceptor) interceptor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        beanMap.put(Const.INTERCEPTORS, interceptors);
    }

    private void createViewResolver(Element viewResolverElt) {
        String className = viewResolverElt.attributeValue(Const.BEAN_TAG_CLASS_ATTRIBUTE);
        // System.out.println(className);
        // 通过反射机制创建对象
        try {
            Class<?> clazz = Class.forName(className);
            Object bean = clazz.newInstance();
            // 获取当前节点的子节点
            List<Element> propertyElts = viewResolverElt.elements(Const.PROPERTY_TAG_NAME);
            for (Element propertyElt : propertyElts) {
                // 属性名
                String fieldName = propertyElt.attributeValue(Const.PROPERTY_NAME);
                // 属性值
                String fieldValue = propertyElt.attributeValue(Const.PROPERTY_VALUE);
                // System.out.println(fieldName + "=" + fieldValue);
                // 将属性名转换为set方法名
                String setMethodName = fieldNameToSetterName(fieldName);
                // System.out.println(setMethodName);
                Method setMethod = clazz.getDeclaredMethod(setMethodName, String.class);
                setMethod.invoke(bean, fieldValue);
            }
            // beanMap.put(firstCharLowerCase(clazz.getSimpleName()), bean);
            beanMap.put(Const.VIEW_RESOLVER, bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据属性名获取set方法的方法名
     *
     * @param fieldName
     * @return
     */
    private String fieldNameToSetterName(String fieldName) {
        return "set" + firstCharUpperCase(fieldName);
    }

    /**
     * 将字符串的首字母变大写
     *
     * @param fieldName
     * @return
     */
    private String firstCharUpperCase(String fieldName) {
        return fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
    }

    /**
     * 组件扫描
     *
     * @param componentScanElement
     * @return
     */
    private Map<RequestMappingInfo, HandlerMethod> componentScan(Element componentScanElement) {
        // 创建处理器映射器Map
        Map<RequestMappingInfo, HandlerMethod> map = new HashMap<>();
        String packageName = componentScanElement.attributeValue(Const.BASE_PACKAGE);
        // System.out.println("组件扫描的包：" + packageName);
        String basePath = packageName.replace('.', '/');
        String absolutePath = Thread.currentThread().getContextClassLoader().getResource(basePath).getPath();
        // System.out.println(absolutePath);
        File file = new File(absolutePath);
        File[] files = file.listFiles();
        for (File f : files) {
            String fileName = f.getName();
            // System.out.println(fileName);
            if (fileName.endsWith(Const.SUFFIX_CLASS)) {
                String simpleClassName = fileName.substring(0, fileName.lastIndexOf("."));
                // System.out.println(simpleClassName);
                String className = packageName + "." + simpleClassName;
                // System.out.println(className);
                // 实例化对象（有@Controller注解的），并存储到IoC容器中
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        Object bean = clazz.newInstance();
                        // 存储到Map集合IoC容器中
                        beanMap.put(firstCharLowerCase(simpleClassName), bean);
                        // 将所有的HandlerMethod创建出来，存放到map集合中
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            // 判断当前的方法是否有@RequestMapping注解标注
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                // 创建requestMappingInfo对象
                                RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
                                // 获取@RequestMapping注解
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                // 获取@RequestMapping注解的value属性，即请求的URI
                                String uri = requestMapping.value()[0];
                                // 获取@RequestMapping注解的method属性，即请求方式
                                String requestMethod = requestMapping.method().toString();
                                // 设置requestMappingInfo对象的uri和请求方式
                                requestMappingInfo.setRequestURI(uri);
                                requestMappingInfo.setMethod(requestMethod);
                                // 创建HandlerMethod对象
                                HandlerMethod handlerMethod = new HandlerMethod(bean, method);
                                // 存放到map集合中
                                map.put(requestMappingInfo,handlerMethod);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // System.out.println(beanMap);
        return map;
    }

    /**
     * 将一个串的首字母变成小写
     *
     * @param simpleClassName
     * @return
     */
    private String firstCharLowerCase(String simpleClassName) {
        return simpleClassName.toLowerCase().charAt(0) + simpleClassName.substring(1);
    }


    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
