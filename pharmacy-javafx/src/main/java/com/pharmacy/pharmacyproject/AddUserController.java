package com.pharmacy.pharmacyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddUserController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField phoneNo;

    @FXML
    private ChoiceBox<String> roles;

    @FXML
    private TextField username;

    private  String role;

    private String[] roleChoise = {"admin", "regular"};

    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        roles.getItems().addAll(roleChoise);
        roles.setOnAction(e -> getRole());
    }

    public void getRole() {
        String getRole = roles.getValue();
        role = getRole;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
    }

    @FXML
    void AddUser(ActionEvent event) {
       try{
           String sql = "INSERT INTO users (fullName,username,phoneNo,role,password) VALUES (?,?,?,?,?)";
           Connection con = getConnection();
           java.sql.PreparedStatement ps = con.prepareStatement(sql);
           ps.setString(1,name.getText());
           ps.setString(2,username.getText());
           ps.setString(3,phoneNo.getText());
           ps.setString(4,role);
           ps.setString(5,password.getText());
           ps.executeUpdate();

           System.out.println("User added successfully");
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }



}
