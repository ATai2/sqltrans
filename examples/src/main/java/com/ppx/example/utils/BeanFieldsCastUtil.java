package com.ppx.example.utils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanFieldsCastUtil {
    private static final String PATTERN = "(\\$([a-zA-Z]*)\\$)";

    public static boolean castFields(String str, Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Pattern compile = Pattern.compile(PATTERN);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            String fieldName = matcher.group(2);
            System.out.println(fieldName);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String name = field.getName();
                if (name.equalsIgnoreCase(fieldName)) {
                    try {
                        field.setAccessible(true);
                        Object o = field.get(object);
                        if (o == null) {
                            o = "";
                        }
                        str = str.replace(matcher.group(1), o.toString());
                        System.out.println(str);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println(str);
        return false;
    }

}
