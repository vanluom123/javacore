package management;

import constants.AppConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class MenuManagement {
    private List<Menu> menuList;

    public MenuManagement() {
        menuList = new ArrayList<>();
    }

    public void showMenu() {
        var lstMenu = OpenCsvReader.getInstance()
                .parseToObject(Menu.class, AppConstants.MENU_CSV_PATH);
        lstMenu.forEach(System.out::println);
    }

    public boolean createOrUpdateMenu(Menu menu) {

        if (!Validation.getInstance().isTypeValid(menu))
            return false;

        if (Validation.getInstance().isInvalidId(menu))
            return false;

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
        return true;
    }

    public Menu getMenuById(String id) {
        try {
            var lstMenu = OpenCsvReader.getInstance().parseToObject(Menu.class, AppConstants.MENU_CSV_PATH);
            var opt = lstMenu.stream()
                    .filter(m -> m.getId().equals(id))
                    .findAny();
            return opt.orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public List<Menu> getAllMenus() {
        try {
            return OpenCsvReader.getInstance().parseToObject(Menu.class, AppConstants.MENU_CSV_PATH);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public void deleteById(String id) {
        try {
            var lstMenu = OpenCsvReader.getInstance().parseToObject(Menu.class, AppConstants.MENU_CSV_PATH);
            // delete
            lstMenu.removeIf(menu -> menu.getId().equals(id));
            // update
            OpenCsvWriter.getInstance().importToCsvFiles(lstMenu, AppConstants.MENU_CSV_PATH);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    public boolean createOrUpdateItem(Item item, String menuId) {
        if (!Validation.getInstance().isTypeValid(item))
            return false;

        var menu = getMenuById(menuId);
        if (menu == null)
            return false;

        if (Validation.getInstance().isInvalidId(item, menu))
            return false;

        var items = menu.getMenuItems();
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
        return true;
    }
}
