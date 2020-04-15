package app.controllers;

import app.Load;
import dataModels.data.Categories;
import dataModels.data.Components;
import dataModels.data.DataCollection;
import database.RequestDatabase;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.MyAlerts;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, category, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> optCategories;
    @FXML private ComboBox<String> optFilterBy;
    @FXML private TableView<Components> tableview;
    private final String FILE_DATABASE = "src/database/componentsDB/dbComponents.bin";
    private ArrayList<Components> componentsList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Categories.categoryOnChange(optCategories,category);
        DataCollection.loadComponents(FILE_DATABASE); // TRÅDEN SKJER HER: DEN LESER BINÆR FILEN
        DataCollection.setTableView(tableview);
        DataCollection.filterOnChange(optFilterBy);
        DataCollection.filterTableView(tableview,txtFilter);
    }

    @FXML void opprettKomponent(ActionEvent event){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = this.category.getText();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Components component = new Components(nr,name,category,specs,price, b);
            RequestDatabase.toSaveComponent(component);
            componentsList.add(component);

            MyAlerts.successAlert("Component Created");
        } catch (IllegalArgumentException e) {
            MyAlerts.warningAlert(e.getMessage());
        }
    }

    @FXML void slett(ActionEvent event){
        RequestDatabase.toDeleteSelectedComponents();
        componentsList.removeIf(components -> components.getCHECKBOX().isSelected());
        tableview.refresh();
    }
    private OpenCSV<Components> openCSV;
    @FXML
    void open(){
        String melding = "last opp en fil til programmet fra denne plasering: src\\database\\lagringsPlass " +
                "\nSkriv navnet til fil du vil laste opp: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 =pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass"+"\\"+pathStr1;
        if(!pathStr.isEmpty()){
            openCSV = new OpenCSV<>(totalPathStr);
            openCSV.setOnSucceeded(this::readingDone);
            openCSV.setOnFailed(this::readingFaild);
            Thread th = new Thread(openCSV);
            th.setDaemon(true);
            adminPane.setDisable(true);
            th.start();
        }else{
            MyAlerts.warningAlert("Ingen fil er valgt");
        }
    }
    private void readingDone(WorkerStateEvent e){
        componentsList = openCSV.call();
        tableview.setItems(FXCollections.observableArrayList(componentsList));
        adminPane.setDisable(false);
    }
    private void readingFaild(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        MyAlerts.warningAlert("Thread Faild: " + e.getMessage());
        adminPane.setDisable(false);
    }

    SaveCSV<Components> saveCSV;
    @FXML
    void save(){
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass" +
                "\ngi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 =pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass"+"\\"+pathStr1;
        if(!pathStr.isEmpty()){
            saveCSV = new SaveCSV<>(componentsList,totalPathStr);
            saveCSV.setOnSucceeded(this::writingDone);
            saveCSV.setOnFailed(this::writingFaild);
            Thread th = new Thread(saveCSV);
            th.setDaemon(true);
            adminPane.setDisable(true);
            th.start();
        }else{
            MyAlerts.warningAlert("Ingen fil er valgt");
        }
    }
    private void writingDone(WorkerStateEvent e){
        saveCSV.call();
        adminPane.setDisable(false);
    }
    private void writingFaild(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        MyAlerts.warningAlert("Thread Faild: " + e.getMessage());
        adminPane.setDisable(false);
    }

    @FXML void loggUt(ActionEvent event){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
        RequestDatabase.toUpdateDatabase(FILE_DATABASE); // TRÅDEN SKJER HER: DEN SKRIVER PÅ BINÆR FILEN
    }
}
