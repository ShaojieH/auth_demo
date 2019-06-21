package com.example.auth_demo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, String >getObjectStringFieldMap(Object o)throws Exception{
        Map<String, String> map = new HashMap<>();

        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().equals(String.class)){
                map.put(field.getName(), (String) field.get(o));
            }
        }
        return map;
    }
}
