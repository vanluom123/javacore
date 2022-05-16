package validate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Menu;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validation {
    private String[] types = {"Food", "Drink"};

    public static Validation getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final Validation INSTANCE = new Validation();
    }

    public boolean isTypeValid(Menu menu) {
        for (var type : types) {
            if (type.equalsIgnoreCase(menu.getType()))
                return true;
        }
        return false;
    }

    public boolean isDuplicateType(Collection<Menu> collection, Menu menu) {
        return collection.stream()
                .anyMatch(c -> c.getType() == menu.getType());
    }
}
