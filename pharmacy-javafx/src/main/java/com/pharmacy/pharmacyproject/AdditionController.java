package com.pharmacy.pharmacyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdditionController {

    @FXML
    private TextField barcode;





    @FXML
    private DatePicker date_expiray;

    @FXML
    private TextField nameItem;

    @FXML
    private TextField price;

    @FXML
    private TextField profit_add;

    @FXML
    private TextField quantity;

    @FXML
    void add(ActionEvent event) {
        if (barcode.getText().isEmpty() || nameItem.getText().isEmpty() || price.getText().isEmpty()
                || quantity.getText().isEmpty() || date_expiray.getValue() == null ||
                profit_add.getText().isEmpty()) {
            System.out.println("The fields cannot be empty");
            return;
        }

        try {

            double finalPrice = Double.parseDouble(price.getText()) + Double.parseDouble(profit_add.getText());

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
            String sql = "SELECT * FROM items WHERE barcode=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, barcode.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");


                Label messageLabel = new Label("ئەم دەرمانە بوونی هەیە!");
                messageLabel.setTextAlignment(TextAlignment.CENTER);
                messageLabel.setFont(Font.font("NRT BOLD", 18));

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(messageLabel);


                alert.getDialogPane().setContent(stackPane);

                alert.getDialogPane().setMinWidth(300);
                alert.getDialogPane().setMinHeight(200);


                alert.showAndWait();
                return;
            }
            String insertItems = "INSERT INTO items (name, barcode, quantity, expiray, price, profitAdd, finalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(insertItems);
            ps2.setString(1, nameItem.getText());
            ps2.setString(2, barcode.getText());
            ps2.setString(3, quantity.getText());
            ps2.setString(4, date_expiray.getValue().toString());
            ps2.setString(5, price.getText());
            ps2.setString(6, profit_add.getText());
            ps2.setDouble(7, finalPrice);

            ps2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Item added successfully", "message", JOptionPane.OK_OPTION);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
