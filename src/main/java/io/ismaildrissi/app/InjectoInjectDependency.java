package io.ismaildrissi.app;

import io.ismaildrissi.app.annotations.Inject;
import io.ismaildrissi.app.annotations.InjectValue;
import io.ismaildrissi.app.exceptions.NotValidClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class InjectoInjectDependency {

    private final Map<String, Class<?>> classes;

    public InjectoInjectDependency(Map<String, Class<?>> classes) {
        this.classes = classes;
    }

    public Object getStandardBean(String beanName) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(beanName);
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Object getBean(String beanName) throws NotValidClass {
        Class<?> clazz = classes.get(beanName);
        if (clazz == null) {
            throw new NotValidClass("No class found for bean: " + beanName);
        }

        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                boolean accessible = field.canAccess(instance);
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fieldType = field.getType();

                    // Try to find a matching class in the map
                    Object dependency = null;
                    if(classes.containsKey(fieldType.getName())) {
                        dependency = getBean(fieldType.getName());
                    } else {
                        dependency = getStandardBean(fieldType.getName());
                    }

                    if (dependency == null) {
                        throw new NotValidClass("No suitable class found to inject into " + field.getName());
                    }

                    field.setAccessible(true);
                    field.set(instance, dependency);
                    field.setAccessible(accessible);
                } else if (field.isAnnotationPresent(InjectValue.class)) {
                    Class<?> fieldType = field.getType();

                    Object dependency = null;

                    Class<?> clazz_field = Class.forName(beanName);
                    InjectValue annotation = field.getAnnotation(InjectValue.class);
                    String value = annotation.val();

                    dependency = convert(fieldType, value);

                    if (dependency == null) {
                        throw new NotValidClass("No suitable class found to inject into " + field.getName());
                    }

                    field.setAccessible(true);
                    field.set(instance, dependency);
                    field.setAccessible(accessible);
                }
            }

            return instance;
        } catch (Exception e) {
            throw new NotValidClass("Failed to create bean: " + beanName + ", due to: " + e.getMessage());
        }


    }

    private Object convert(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == float.class || type == Float.class) return Float.parseFloat(value);
        if (type == short.class || type == Short.class) return Short.parseShort(value);
        if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
        if (type == char.class || type == Character.class) return value.charAt(0);
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
