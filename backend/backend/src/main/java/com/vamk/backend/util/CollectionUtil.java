package com.vamk.backend.util;

import java.util.Collection;
import java.util.function.Supplier;

public class CollectionUtil {

    private CollectionUtil() {}

    public static <T, C extends Collection<T>> C fromIterable(Iterable<T> iterable, Supplier<C> generator) {
        C result = generator.get();
        iterable.forEach(result::add);
        return result;
    }
}
