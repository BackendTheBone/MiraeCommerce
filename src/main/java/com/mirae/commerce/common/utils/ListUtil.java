package com.mirae.commerce.common.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {
    private ListUtil(){};

    public static <T, U> List<U> extractPropertyList(List<T> list, Function<T, U> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }

    public static <T, U> List<U> applyFunctionList(List<T> list, Function<T, U> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }

}
