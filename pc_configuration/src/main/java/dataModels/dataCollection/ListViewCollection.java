package dataModels.dataCollection;

import dataModels.data.ConfigItem;
import io.FileClient;
import org.app.PathDialogBox;
import dataModels.data.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import validations.Alerts;
import java.util.ArrayList;

/**
 * klassen samler date til ListView(PC-Configuration) og metodene gir muligehet for å behandle data.
 * */

public class ListViewCollection {
    private static final ObservableList<Component> COMPONENTS = TableViewCollection.getComponents();
    private static final ObservableList<Component> selectedItems = FXCollections.observableArrayList();
    private static final ObservableList<ConfigItem> configItems = FXCollections.observableArrayList();
    private static boolean modified = false;
    private static boolean open = false;
    private static String openedFile;

    /** Viser alle komponent kategorier i en dropdown */
    public static void fillCategoryComboBox(ComboBox<String> comboBox ){
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("All");
        for(Component item : COMPONENTS){
            if(!categories.contains(item.getComponentCategory())){
                categories.add(item.getComponentCategory());
            }
        }
        comboBox.setValue("Velg Kategori");
        comboBox.setItems(categories);
    }

    /** Viser komponenter i tabellen basert på kategori som er valgt i dropdown */
    public static void selectedTable (String categoryName, TableView<Component> tableView ){
        ObservableList<Component> selectedCatogries = FXCollections.observableArrayList();
        for (Component obj : COMPONENTS){
            if (obj.getComponentCategory().equals(categoryName)){
                selectedCatogries.add(obj);
            }
        }
        if(categoryName.equals("All")){
            selectedCatogries = COMPONENTS;
        }
        SortedList<Component> sortedList = new SortedList<>(selectedCatogries);
        tableView.setItems(sortedList);
    }

    /** Viser valgte ConfigItems i listview */
    public static void setListView(ListView<ConfigItem> shoppingCart){ shoppingCart.setItems(configItems); }

    /** Legger ConfigItems i listview */
    public static void addToShoppingCart(){
        for(Component c : COMPONENTS) {
            if (c.getCheckBox().isSelected()) {
                if (selectedItems.contains(c)) {
                    boolean response = Alerts.confirm("\"" + c.getComponentName() + "\" finnes allerede i kurven.\nVil du legge en til?");
                    if (response) {
                        selectedItems.add(c);
                        c.getCheckBox().setSelected(false);
                        configItems.add(new ConfigItem(c));
                    }
                } else {
                    selectedItems.add(c);
                    c.getCheckBox().setSelected(false);
                    configItems.add(new ConfigItem(c));
                }
                modified = true;
            }
        }
    }

    /** Lagrer konfigurasjoner ved logg ut og når programmen slutter */
    public static void saveConfig() {
        if(isModified()){
            ArrayList<Component> toSave = new ArrayList<>(configItems);
            FileClient<Component> file;
            if(openedFile == null){
                openedFile = new PathDialogBox().getPathToSave();
                file = new FileClient<>(toSave,openedFile);
            } else {
                file = new FileClient<>(toSave,openedFile);
                modified = false;
            }
            file.save();
        }
    }

    /** Sjekker om configItems er tom eller ikke */
    public static void loadingConfig(ArrayList<ConfigItem> items){
        clearList();
        configItems.addAll(items);
        for(Component item: items){
            for(Component c: COMPONENTS){
                if(c.getComponentNr() == item.getComponentNr()){
                    selectedItems.add(c);
                }
            }
        }
        modified = false;
    }

    /** Viser total prisen til alle ConfigItems */
    public static void showTotalPrice(Label totalPriceLbl){
        double totalPrice = ConfigItem.totalPrice(configItems);
        totalPriceLbl.setText(Double.toString(totalPrice));
    }

    /** Resetter listview for en ny konfigurasjon */
    public static void clearList(){
        selectedItems.clear();
        configItems.clear();
        modified = true;
    }

    /** Sletter ConfigItems fra listview */
    public static void  deleteItemList (ObservableList<ConfigItem> items, Label totalPriceLbl){
        for(Component el : items){
            selectedItems.removeIf(c -> c.getComponentNr() == el.getComponentNr());
        }
        configItems.removeAll(items);
        showTotalPrice(totalPriceLbl);
        modified = !configItems.isEmpty();
    }

    /** Getter og Setter methods */
    public static ObservableList<ConfigItem> getConfigItems() { return configItems; }
    public static boolean isModified() { return modified; }
    public static boolean isOpen() { return open; }
    public static void setModified(boolean modified) { ListViewCollection.modified = modified; }
    public static void setOpen(boolean open) { ListViewCollection.open = open; }
    public static void setOpenedFile(String file) { openedFile = file; }
}
