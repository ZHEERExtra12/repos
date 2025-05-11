package com.pharmacy.pharmacyproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;


import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/NRT-Bd.ttf"), 14);
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("log-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pharmacy");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

// #A0C878