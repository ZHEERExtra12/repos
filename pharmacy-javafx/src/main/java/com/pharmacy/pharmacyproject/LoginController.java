package com.pharmacy.pharmacyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class LoginController {


    @FXML
    private Button btn_login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;


    @FXML
    void btnLogin(ActionEvent event) {

        String uname = username.getText();
        String pass = password.getText();

        if(uname.equals("") && pass.equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
        }

        else {

            try{

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project","root","");


                String sql = "SELECT * FROM users WHERE username=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,uname);


                ResultSet rs = ps.executeQuery();

                if(rs.next()) { // this rs.next() is checking if any results coming back

                    String dbUsername = rs.getString("username"); // this gets the username from the db
                    String dbPassword = rs.getString("password");

                    if(dbUsername.equals(uname) && dbPassword.equals(pass)) {

                        try {
                            // Load the Dashboard FXML
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                            Parent root = fxmlLoader.load();

                            // Create a new Stage for the Dashboard
                            Stage stage = new Stage();
                            stage.setTitle("Dashboard");
                            stage.setScene(new Scene(root));
                            stage.show();
                            stage.setResizable(false);

                            // Close the Login Window
                            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                }

                else {
                    JOptionPane.showMessageDialog(null, " user is not exits");
                }

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }



}