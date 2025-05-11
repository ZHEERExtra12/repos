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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.*;

public class StorageController {

    @FXML
    private TableColumn<ObservableList<String>, String> name;
    @FXML
    private TableColumn<ObservableList<String>, String> barcode;
    @FXML
    private TableColumn<ObservableList<String>, String> quantity;
    @FXML
    private TableColumn<ObservableList<String>, String> date;
    @FXML
    private TableColumn<ObservableList<String>, String> buyPrice;
    @FXML
    private TableColumn<ObservableList<String>, String> sellPrice;
    @FXML
    private TableColumn<ObservableList<String>, String> profit;
    @FXML
    private TableColumn<ObservableList<String>, String> expairy;
    @FXML
    private TableColumn<ObservableList<String>, Void> delete;
    @FXML
    private TableView<ObservableList<String>> table;

    private final ObservableList<ObservableList<String>> items = FXCollections.observableArrayList();

    public void initialize() {
        name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        barcode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        quantity.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        buyPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        sellPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        expairy.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        profit.setCellValueFactory(data -> {
            try {
                int buy = Integer.parseInt(data.getValue().get(4));
                int sell = Integer.parseInt(data.getValue().get(5));
                return new SimpleStringProperty(String.valueOf(sell - buy));
            } catch (NumberFormatException e) {
                return new SimpleStringProperty("0");
            }
        });

        name.setReorderable(false);
        barcode.setReorderable(false);
        quantity.setReorderable(false);
        buyPrice.setReorderable(false);
        sellPrice.setReorderable(false);
        profit.setReorderable(false);
        date.setReorderable(false);
        expairy.setReorderable(false);

        loadData();
        addDeleteButtonToTable();
    }

    private void loadData() {
        items.clear();
        String sql = "SELECT name, barcode, quantity, dateOfAdd, price, finalPrice, expiray FROM items";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("name"));
                row.add(rs.getString("barcode"));
                row.add(rs.getString("quantity"));
                row.add(rs.getString("dateOfAdd"));
                row.add(rs.getString("price"));
                row.add(rs.getString("finalPrice"));
                row.add(rs.getString("expiray"));
                items.add(row);
            }

            table.setItems(items);

        } catch (SQLException e) {
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
                    String barcodeValue = item.get(1); // Barcode is at index 1

                    deleteItemFromDatabase(barcodeValue);
                    items.remove(item); // Remove from UI
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

    private void deleteItemFromDatabase(String barcode) {
        String sql = "DELETE FROM items WHERE barcode = ?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, barcode);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sell Item");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
