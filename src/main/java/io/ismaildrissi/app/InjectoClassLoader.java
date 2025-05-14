package io.ismaildrissi.app;

import java.util.*;

public class InjectoClassLoader {

    private final Map<String, Class<?>> classes = new HashMap<>();

    public InjectoClassLoader(Set<String> classNames) throws ClassNotFoundException {
        for(String className: classNames) {
            Class<?> clazz = Class.forName(className);
            classes.put(className, clazz);
        }
    }

    public Map<String, Class<?>> getClasses() {
        return classes;
    }

    public Class<?> getClass(String className) {
        return classes.get(className);
    }
}
