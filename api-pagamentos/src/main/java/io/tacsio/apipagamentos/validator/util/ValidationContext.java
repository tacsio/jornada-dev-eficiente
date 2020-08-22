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

    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    /**
     * Get result from validation context.
     * It is possible to return null values
     *
     * @param clazz Supplied object class
     * @param id    Supplied id
     * @param <T>   Type of supplied object
     * @return T
     */
    public <T> T get(Class<T> clazz, Object id) {
        String key = String.format("%s.%s", clazz.getName(), id.toString());
        Object value = ValidationContext.context.get().get(key);

        return (T) value;
    }

    /**
     * Find for a value in validation context, if the value isn't found, the result is retrieved by supplier.
     *
     * @param clazz Supplied object class
     * @param id    Supplied id
     * @param <T>   Type of supplied object
     * @return
     */
    public <T> T find(Class<T> clazz, Object id, Supplier<T> supplier) {
        Map<String, Object> values = ValidationContext.context.get();

        if (values == null) {
            ValidationContext.context.set(new ConcurrentHashMap<>());
        }

        Object value = this.get(clazz, id);
        if (value == null) {
            String key = String.format("%s.%s", clazz.getName(), id.toString());
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
