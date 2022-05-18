package validate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import utils.MethodExtensions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class Validation {
    private String[] types = {"Food", "Drink"};
    private String[] typeOfItems = {"breakfast", "launch", "dinner"};

    public static Validation getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final Validation INSTANCE = new Validation();
    }

    public <T> boolean isTypeValid(T entity) {
        try {
            String[] types = null;
            if (entity.getClass().getName().contains("Item"))
                types = this.typeOfItems;
            else if (entity.getClass().getName().contains("Menu"))
                types = this.types;

            var method = entity.getClass().getMethod("getType");
            var object = method.invoke(entity);
            return Arrays
                    .stream(types)
                    .anyMatch(s -> s.equalsIgnoreCase(String.valueOf(object)));
        } catch (ReflectiveOperationException | NullPointerException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    @SafeVarargs
    public final <T> boolean isInvalidId(T... entities) {
        return Stream.of(entities)
                .anyMatch(entity -> {
                    var methods = entity.getClass().getDeclaredMethods();
                    var gettingIdMethods = Stream.of(methods)
                            .filter(method -> {
                                if (MethodExtensions.isGetter(method)) {
                                    var upperCase = method.getName()
                                            .toUpperCase()
                                            .substring(3);
                                    return upperCase.contains("ID");
                                }
                                return false;
                            }).collect(Collectors.toList());

                    var objects = gettingIdMethods.stream()
                            .map(method -> {
                                try {
                                    return method.invoke(entity);
                                } catch (ReflectiveOperationException e) {
                                    log.error(e.getMessage(), e.getCause());
                                    return null;
                                }
                            }).collect(Collectors.toList());
                    return objects.stream().anyMatch(Objects::isNull);
                });
    }

    public <T> boolean isNotDuplicationType(Collection<? super T> collection, T entity) {
        return collection.stream()
                .noneMatch(c -> {
                    try {
                        var method1 = c.getClass().getMethod("getType");
                        var method2 = entity.getClass().getMethod("getType");
                        var object1 = method1.invoke(c);
                        var object2 = method2.invoke(entity);
                        return object1.equals(object2);
                    } catch (ReflectiveOperationException e) {
                        log.error(e.getMessage(), e.getCause());
                        return false;
                    }
                });
    }
}
