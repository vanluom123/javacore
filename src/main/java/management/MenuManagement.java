package management;

import constants.AppConstants;
import model.Item;
import model.Menu;
import lombok.Getter;
import lombok.Setter;
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
        menuList.forEach(System.out::println);
    }

    public void createOrUpdateMenu(Menu menu) {

        if (!Validation.getInstance().isTypeValid(menu))
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
                if (!Validation.getInstance().isDuplicateType(menuList, menu))
                    menuList.add(menu);
            }
        }
        OpenCsvWriter.getInstance().writeMenuToCsv(menuList);
    }

    public Menu getMenuById(String id) {
        var opt = menuList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
        return opt.orElse(null);
    }

    public List<Menu> getAllMenus() {
        return OpenCsvReader.getInstance().parseCsvToMenu(AppConstants.MENU_CSV_PATH);
    }

    public void deleteById(String id) {
        menuList.removeIf(menu -> menu.getId().equals(id));
    }

    public void createOrUpdateItem(Item item, String menuId) {
        var menu = getMenuById(menuId);
        var items = menu.getMenuItems();

        if (!Validation.getInstance().isItemTypeValid(item))
            return;

        if (items == null || items.isEmpty()) {
            if (items == null)
                items = new HashSet<>();
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
                if (!Validation.getInstance().isDuplicateItemType(items, item))
                    items.add(item);
            }
        }
        OpenCsvWriter.getInstance().writeItemToCsv(items);
    }
}
