package management;

import model.Item;
import model.Menu;
import lombok.Getter;
import lombok.Setter;
import utils.OpenCsvReader;
import utils.OpenCsvWriter;
import validate.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class MenuManagement {
    private List<Menu> menuList;

    public MenuManagement() {
        menuList = new ArrayList<>();
    }

    public void showMenu() {
        menuList.forEach(System.out::println);
    }

    public void createOrUpdateMenu(Menu menu) {

        if (!Validation.getInstance().isTypeValid(menu))
            return;

        if(menuList.isEmpty())
        {
            menuList.add(menu);
        }
        else
        {
            if (menuList.contains(menu)) {
                // update menu
                for (var menu1 : menuList) {
                    if (Objects.equals(menu1.getId(), menu.getId())) {
                        menu1.setType(menu.getType());
                    }
                }
            } else {
                // create
                if (!Validation.getInstance().isDuplicateType(menuList, menu))
                    menuList.add(menu);
            }
        }
        OpenCsvWriter.writeToCsvFrom(menuList.get(0));
    }

    public Menu getMenuById(String id) {
        return menuList.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .get();
    }

    public List<Menu> getAllMenus() {
        var menus = OpenCsvReader.readCsvToObject("csv/menu.csv");
        return menus;
    }

    public void deleteById(String id) {
        menuList.removeIf(menu -> menu.getId() == id);
    }


    public void createOrUpdateItem(Item item) {

    }
}
