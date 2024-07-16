package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import model.Customer;
import tm.CustomerTM;

public class CustomerController {
    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtCustomerSalary;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TableColumn<CustomerTM, Button> colAction;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, Integer> colId;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, Double> colSalary;

    public void initialize() throws ClassNotFoundException, SQLException {
        // System.out.println("Calling First");
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        getAllCustomers();
    }

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

    @FXML
    void btnLoadAllCustomerOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        // System.out.println("Load All Customer btn clicked");
        getAllCustomers();
    }

    public void getAllCustomers() throws ClassNotFoundException, SQLException {
        PreparedStatement statement = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM customer");
        ResultSet customerSet = statement.executeQuery();

        ArrayList<Customer> customerList = new ArrayList<Customer>();

        while (customerSet.next()) {
            Customer customer = new Customer(
                    customerSet.getInt(1),
                    customerSet.getString(2),
                    customerSet.getString(3),
                    customerSet.getDouble(4));
            // System.out.println(customer);
            customerList.add(customer);
        }
        // System.out.println("Customer List: " + customerList);

        ObservableList<CustomerTM> customerTMList = FXCollections.observableArrayList();
        for (Customer cust : customerList) {
            CustomerTM customerTM = new CustomerTM(
                    cust.getCustomerId(),
                    cust.getName(),
                    cust.getAddress(),
                    cust.getSalary(),
                    new Button("Delete"));
            customerTMList.add(customerTM);
        }
        System.out.println(customerTMList);

        tblCustomer.setItems(customerTMList);
    }
}
