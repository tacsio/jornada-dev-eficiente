package io.tacsio.apipagamentos.validator.util;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Thread local implementation to store database results used in bean validation requests.
 * When bean validation search for entities, first it finds in thread local context for, if it isn't found the supplier
 * method is called and the result is stored in a {@link ValidationContext}.
 * <p>
 * When, in same thread the search data is needed, it is possible to retrieve in validation context decreasing
 * data base queries.
 */
@Repository
public class ValidationContext {

    private static ThreadLocal<Map> context = new ThreadLocal<>();

    /**
     * Get result from validation context.
     * It is possible to return null values
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> clazz, String id) {
        String key = String.format("%s.%s", clazz.getName(), id);
        Object value = ValidationContext.context.get().get(key);

        return (T) value;
    }

    /**
     * Find for a value in validation context, if the value isn't found, the result is retrieved by supplier.
     *
     * @param clazz
     * @param id
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> T find(Class<T> clazz, String id, Supplier<T> supplier) {
        Map values = ValidationContext.context.get();

        if (values == null) {
            ValidationContext.context.set(new ConcurrentHashMap());
        }

        String key = String.format("%s.%s", clazz.getName(), id);
        Object value = ValidationContext.context.get().get(key);

        if (value == null) {
            T suppliedValue = supplier.get();
            this.put(key, suppliedValue);
            return suppliedValue;
        }

        return (T) value;
    }

    private <T> void put(String key, T order) {
        Map<String, Object> data = ValidationContext.context.get();
        data.put(key, order);
    }
}
