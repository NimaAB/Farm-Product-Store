package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {

    @FXML private Label melding;
    @FXML private Button logButton;
    @FXML private CheckBox adminBox;

    @FXML
    void loginCheck(ActionEvent event) throws IOException {


        if(!adminBox.isSelected()){
        Parent customerView= FXMLLoader.load(getClass().getResource("views/costumerScene.fxml"));
        Scene customerScene = new Scene (customerView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(customerScene);
        window.setTitle("Customer");
        window.show(); }
        else {
            Parent customerView= FXMLLoader.load(getClass().getResource("views/adminScene.fxml"));
            Scene adminScene = new Scene (customerView);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(adminScene);
            window.setTitle("Administration");
            window.show(); }


    }
}