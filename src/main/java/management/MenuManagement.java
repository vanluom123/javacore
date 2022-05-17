package management;

import constants.AppConstants;
import lombok.Getter;
import lombok.Setter;
import model.Item;
import model.Menu;
import utils.OpenCsvReader;
import utils.OpenCsvWriter;
import validate.Validation;

import java.util.ArrayList;
import java.util.HashSet;
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
        var lstMenu = OpenCsvReader.getInstance()
                .parseCsvToObjectUsingAnnotation(Menu.class, AppConstants.MENU_CSV_PATH);
        lstMenu.forEach(System.out::println);
    }

    public void createOrUpdateMenu(Menu menu) {

        if (!Validation.getInstance().isTypeValid(menu))
            return;

        if(Validation.getInstance().isInvalidId(menu))
            return;

        if (menuList.isEmpty()) {
            menuList.add(menu);
        } else {
            if (menuList.contains(menu)) {
                // update menu
                for (var menu1 : menuList) {
                    if (Objects.equals(menu1.getId(), menu.getId())) {
                        menu1.setType(menu.getType());
                    }
                }
            } else {
                // create
                if (Validation.getInstance().isNotDuplicationType(menuList, menu))
                    menuList.add(menu);
            }
        }
        OpenCsvWriter.getInstance().importToCsvFiles(menuList, AppConstants.MENU_CSV_PATH);
    }

    public Menu getMenuById(String id) {
        var opt = menuList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
        return opt.orElse(null);
    }

    public List<Menu> getAllMenus() {
        return OpenCsvReader.getInstance().parseCsvToObjectUsingAnnotation(Menu.class, AppConstants.MENU_CSV_PATH);
    }

    public void deleteById(String id) {
        var lstMenu = OpenCsvReader.getInstance().parseCsvToObjectUsingAnnotation(Menu.class, AppConstants.MENU_CSV_PATH);
        // delete
        lstMenu.removeIf(menu -> menu.getId().equals(id));
        // update
        OpenCsvWriter.getInstance().importToCsvFiles(lstMenu, AppConstants.MENU_CSV_PATH);
    }

    public void createOrUpdateItem(Item item, String menuId) {
        if (Validation.getInstance().isTypeValid(item))
            return;

        var menu = getMenuById(menuId);
        if (menu == null) return;

        var items = menu.getMenuItems();
        if (Validation.getInstance().isInvalidId(item, menu))
            return;

        if (items == null || items.isEmpty()) {
            if (items == null)
                items = new HashSet<>();
            item.setMenuId(menuId);
            items.add(item);
        } else {
            if (items.contains(item)) {
                // update menu
                for (var o : items) {
                    if (Objects.equals(o.getId(), item.getId())) {
                        o.setMenuId(menu.getId());
                        o.setName(item.getName());
                        o.setNote(item.getNote());
                        o.setPrice(item.getPrice());
                        o.setQuality(item.getQuality());
                        o.setDescription(item.getDescription());
                    }
                }
            } else {
                // create
                if (Validation.getInstance().isNotDuplicationType(items, item)) {
                    item.setMenuId(menuId);
                    items.add(item);
                }
            }
        }
        OpenCsvWriter.getInstance().importToCsvFiles(items, AppConstants.ITEM_CSV_PATH);
    }
}
