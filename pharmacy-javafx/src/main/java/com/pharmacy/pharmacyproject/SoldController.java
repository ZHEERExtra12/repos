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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.*;

public class SoldController {

    private ObservableList<ObservableList<String>> soldItems = FXCollections.observableArrayList();

    @FXML
    private TableView<ObservableList<String>> table;

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
    public void initialize() {
        name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        barcode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        quantity.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        buyPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        sellPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        profit.setCellValueFactory(data -> {
            try {
                int buy = Integer.parseInt(data.getValue().get(4));
                int sell = Integer.parseInt(data.getValue().get(5));
                return new SimpleStringProperty(String.valueOf(sell - buy));
            } catch (Exception e) {
                return new SimpleStringProperty("0");
            }
        });

        name.setReorderable(false);
        barcode.setReorderable(false);
        quantity.setReorderable(false);
        buyPrice.setReorderable(false);
        sellPrice.setReorderable(false);
        profit.setReorderable(false);

        loadSoldData();
    }

    private void loadSoldData() {
        soldItems.clear();
        String sql = "SELECT * FROM sold_items";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("name"));
                row.add(rs.getString("barcode"));
                row.add(rs.getString("quantity"));
                row.add(rs.getString("sold_date"));
                row.add(rs.getString("buyPrice"));
                row.add(rs.getString("sellPrice"));

                soldItems.add(row);
            }

            table.setItems(soldItems);

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
            stage.show();
            stage.setResizable(false);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
