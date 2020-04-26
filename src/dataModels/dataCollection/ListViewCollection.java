package dataModels.dataCollection;

import app.Save;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import validations.Alerts;
import java.util.ArrayList;

public class ListViewCollection {
    private static final ObservableList<Components> components = TableViewCollection.getComponents();
    private static final ObservableList<Components> selectedItems = FXCollections.observableArrayList();
    private static final ObservableList<ConfigurationItems> configItems = FXCollections.observableArrayList();
    private static boolean modified = false;
    private static boolean open = false;
    private static String openedFile;

    /** Viser alle komponent kategorier i en comboBox */
    public static void fillCategoryComboBox(ComboBox<String> comboBox ){
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("All");
        for(Components item : components){
            if(!categories.contains(item.getComponentCategory())){
                categories.add(item.getComponentCategory());
            }
        }
        comboBox.setValue("Velg Kategori");
        comboBox.setItems(categories);
    }

    /** Viser komponenter i tabellen basert på kategori */
    public static void selectedTable (String categoryName, TableView<Components> tableView ){
        ObservableList<Components> selectedCatogries = FXCollections.observableArrayList();
        for (Components obj : components){
            if (obj.getComponentCategory().equals(categoryName)){
                selectedCatogries.add(obj);
            }
        }
        if(categoryName.equals("All")){
            selectedCatogries = components;
        }
        SortedList<Components> sortedList = new SortedList<>(selectedCatogries);
        tableView.setItems(sortedList);
    }

    /** Viser valgte ConfigItems i listview */
    public static void setListView(ListView<ConfigurationItems> shoppingCart){ shoppingCart.setItems(configItems); }

    /** Legger ConfigItems i listview */
    public static void addToShoppingCart(){
        for(Components c : components) {
            if (c.getCheckBox().isSelected()) {
                if (selectedItems.contains(c)) {
                    boolean response = Alerts.confirm("\"" + c.getComponentName() + "\" finnes allerede i kurven.\nVil du legge en til?");
                    if (response) {
                        selectedItems.add(c);
                        c.getCheckBox().setSelected(false);

                        int nr = c.getComponentNr();
                        String navn = c.getComponentName();
                        double pris = c.getComponentPrice();
                        configItems.add(new ConfigurationItems(nr, navn, pris));
                    }
                } else {
                    selectedItems.add(c);
                    c.getCheckBox().setSelected(false);

                    int nr = c.getComponentNr();
                    String navn = c.getComponentName();
                    double pris = c.getComponentPrice();
                    configItems.add(new ConfigurationItems(nr, navn, pris));
                }
                modified = true;
            }
        }
    }

    /** Lagrer konfigurasjoner ved logg ut og når programmen slutter */
    public static void saveConfig() {
        if(isModified()){
            ArrayList<ConfigurationItems> toSave = new ArrayList<>(configItems);
            if(openedFile == null){
                try{
                    openedFile = Save.pathDialog("DataFraApp");
                    SaveCSV<ConfigurationItems> saveCSV = new SaveCSV<>(toSave,openedFile);
                    saveCSV.call();
                }catch (Exception e){
                    Alerts.warning("Lagring gikk feil, Grunn: " + e.getCause());
                }
            }else {
                SaveCSV<ConfigurationItems> saveCSV = new SaveCSV<>(toSave, openedFile);
                saveCSV.call();
                modified = false;
            }
        }
    }

    /** Sjekker om configItems er tom eller ikke */
    public static void loadingConfig(ArrayList<ConfigurationItems> items){
        clearList();
        configItems.addAll(items);
        for(ConfigurationItems item: items){
            for(Components c:components){
                if(c.getComponentNr() == item.getNr()){
                    selectedItems.add(c);
                }
            }
        }
    }

    /** Viser total prisen til alle ConfigItems */
    public static void showTotalPrice(Label totalPriceLbl){
        double totalPrice = ConfigurationItems.totalPrice(configItems);
        totalPriceLbl.setText(Double.toString(totalPrice));
    }

    /** Resetter listview for en ny konfigurasjon */
    public static void clearList(){
        selectedItems.clear();
        configItems.clear();
        modified = false;
    }

    /** Sletter ConfigItems fra listview */
    public static void  deleteItemList (ObservableList<ConfigurationItems> items, Label totalPriceLbl){
        for(ConfigurationItems item : items){
            selectedItems.removeIf(c -> c.getComponentNr() == item.getNr());
            configItems.remove(item);
        }
        showTotalPrice(totalPriceLbl);
        modified = true;
    }

    /** Getter og Setter methods */
    public static ObservableList<ConfigurationItems> getConfigItems() { return configItems; }
    public static String getOpenedFile() { return openedFile; }
    public static boolean isModified() { return modified; }
    public static boolean isOpen() { return open; }

    public static void setModified(boolean modified) { ListViewCollection.modified = modified; }
    public static void setOpen(boolean open) { ListViewCollection.open = open; }
    public static void setOpenedFile(String file) { openedFile = file; }
}
