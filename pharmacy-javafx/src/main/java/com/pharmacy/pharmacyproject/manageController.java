package com.pharmacy.pharmacyproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class manageController {

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private TableColumn<ObservableList<String>, String> username;

    @FXML
    private TableColumn<ObservableList<String>, String> password;

    @FXML
    private TableColumn<ObservableList<String>, String> fullName;

    @FXML
    private TableColumn<ObservableList<String>, String> role;

    @FXML
    private TableColumn<ObservableList<String>, String> mobleNo;

    @FXML
    private TableColumn<ObservableList<String>, Void> delete;

    private final ObservableList<ObservableList<String>> people = FXCollections.observableArrayList();

    public void initialize() {
        username.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        password.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        fullName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        role.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        mobleNo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

        username.setReorderable(false);
        password.setReorderable(false);
        fullName.setReorderable(false);
        role.setReorderable(false);
        mobleNo.setReorderable(false);

        loadUserData();
        addDeleteButtonToTable();
    }

    private void loadUserData() {
        people.clear();
        String sql = "SELECT * FROM users";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("username"));
                row.add(rs.getString("password"));
                row.add(rs.getString("fullname"));
                row.add(rs.getString("role"));
                row.add(rs.getString("phoneNo"));

                people.add(row);
            }

            table.setItems(people);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addUser.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New User");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDeleteButtonToTable() {
        delete.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                deleteBtn.setOnAction(event -> {
                    ObservableList<String> item = getTableView().getItems().get(getIndex());
                    String usernameValue = item.get(0);

                    deleteUserFromDatabase(usernameValue);
                    people.remove(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void deleteUserFromDatabase(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
