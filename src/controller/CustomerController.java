package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.Customer;

public class CustomerController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtCustomerSalary;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerId;

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        System.out.println("Save btn click");
        System.out.println("ID: " + txtCustomerId.getText());
        System.out.println("Name: " + txtCustomerName.getText());
        System.out.println("Address: " + txtCustomerAddress.getText());
        System.out.println("Salary: " + txtCustomerSalary.getText());

        int id = Integer.parseInt(txtCustomerId.getText());
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        double salary = Double.parseDouble(txtCustomerSalary.getText());

        Customer customer = new Customer(id, name, address, salary);
        System.out.println(customer);

        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
        statement.setObject(1, customer.getCustomerId());
        statement.setObject(2, customer.getName());
        statement.setObject(3, customer.getAddress());
        statement.setObject(4, customer.getSalary());

        int rows = statement.executeUpdate();
        if (rows > 0) {
            System.out.println("Customer Saved!!");
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!!").show();
        } else {
            System.out.println("Customer Not Saved!!!  Error!!!");
            new Alert(Alert.AlertType.ERROR, "Customer Not Saved!!!  Error!!!").show();
        }
    }
}
