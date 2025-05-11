package com.pharmacy.pharmacyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DashboardController {

    @FXML
    private Button bten_maneg, btn_add, btn_debt, btn_profit, btn_sell, btn_sold, btn_storage;

    @FXML
    private Label date, user;

    @FXML
    private ImageView icon1 , icon2,icon5;


    private static String savedUsername;

    private static String savedRole;

    public void setUsername(String username) {
        savedUsername = username;
        user.setText(savedUsername);
    }


    public void setRole(String role) {
        savedRole = role;
        if (savedRole.equals("regular")) {
            Image image = new Image(getClass().getResourceAsStream("/images/lock-1.png"));
            bten_maneg.setDisable(true);

            btn_storage.setDisable(true);
            btn_sold.setDisable(true);


            icon1.setImage(image);
            icon2.setImage(image);

            icon5.setImage(image);
        }
    }

    public void setDate(LocalDate dte) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dte.format(formatter);

        date.setText(formattedDate);
    }

    public void initialize() {
        setDate(LocalDate.now());
        if (savedUsername != null) {
            user.setText(savedUsername);
        }
        if(savedRole != null){
            setRole(savedRole);
        }

    }


    @FXML
    void additionForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_items.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Item");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void manegmentForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manege.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    void sellForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sell_items.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Sell Item");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void soldForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sold_items.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Sell Item");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void storageForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("storage.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Sell Item");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






    @FXML
    void logout(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("log-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
