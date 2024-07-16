package controller;

import java.io.IOException;
//import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;

public class MainController {
    @FXML
    private AnchorPane root;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        System.out.println("btnCustomerOnAction");
        /*
         * //load new stage
         * URL resource = this.getClass().getResource("../view/Customer.fxml");
         * Parent root = FXMLLoader.load(resource);
         * 
         * Stage stage = new Stage();
         * stage.setScene(new Scene(root));
         * stage.show();
         * stage.setTitle("Customer Form");
         */

        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("../view/Customer.fxml"));
        this.root.getChildren().add(node);
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        System.out.println("btnItemOnAction");

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        System.out.println("btnOrderOnAction");

    }
}
