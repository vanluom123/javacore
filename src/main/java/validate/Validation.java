package validate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.reflect.MethodUtils;
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

    public <T> boolean isTypeValid(T model) {
        String[] types = null;
        if (model.getClass().getName().contains("Item"))
            types = this.typeOfItems;
        else if (model.getClass().getName().contains("Menu"))
            types = this.types;

        try {
            var object = MethodUtils.invokeMethod(model, "getType");
            return Arrays
                    .stream(types)
                    .anyMatch(s -> s.equalsIgnoreCase(String.valueOf(object)));
        } catch (ReflectiveOperationException | NullPointerException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    @SafeVarargs
    public final <T> boolean isInvalidId(T... models) {
        return Stream.of(models)
                .anyMatch(model -> {
                    var methods = model.getClass().getDeclaredMethods();
                    var methods2 = Stream.of(methods)
                            .filter(method -> {
                                if (MethodExtensions.isGetter(method)) {
                                    var upperCase = method.getName()
                                            .toUpperCase()
                                            .substring(3);
                                    return upperCase.contains("ID");
                                }
                                return false;
                            }).collect(Collectors.toList());

                    var objects = methods2.stream()
                            .map(method -> {
                                try {
                                    return method.invoke(model);
                                } catch (ReflectiveOperationException e) {
                                    throw new RuntimeException(e);
                                }
                            }).collect(Collectors.toList());
                    return objects.stream().anyMatch(Objects::isNull);
                });
    }

    public <T> boolean isNotDuplicationType(Collection<? super T> collection, T object) {
        return collection.stream()
                .noneMatch(c -> {
                    try {
                        var object1 = MethodUtils.invokeMethod(c, "getType");
                        var object2 = MethodUtils.invokeMethod(object, "getType");
                        return object1.equals(object2);
                    } catch (ReflectiveOperationException e) {
                        log.error(e.getMessage(), e.getCause());
                        return false;
                    }
                });
    }
}
