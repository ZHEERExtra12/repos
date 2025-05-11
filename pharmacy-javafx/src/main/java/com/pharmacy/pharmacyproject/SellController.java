package com.pharmacy.pharmacyproject;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellController {

    @FXML
    private TableView<ItemsClass> itemsTable;

    @FXML
    private TableColumn<ItemsClass, Integer> idColumn;
    @FXML
    private TableColumn<ItemsClass, String> nameColumn;
    @FXML
    private TableColumn<ItemsClass, Integer> quantityColumn;
    @FXML
    private TableColumn<ItemsClass, String> barcodeColumn;
    @FXML
    private TableColumn<ItemsClass, Double> priceColumn;
    @FXML
    private TableColumn<ItemsClass, Double> profitColumn;
    @FXML
    private TableColumn<ItemsClass, String> expiryColumn;

    @FXML
    private TextField getFromBarcode;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(itemsTable.getItems().indexOf(cellData.getValue()) + 1));
        idColumn.setCellFactory(new Callback<TableColumn<ItemsClass, Integer>, TableCell<ItemsClass, Integer>>() {
            @Override
            public TableCell<ItemsClass, Integer> call(TableColumn<ItemsClass, Integer> param) {
                return new TableCell<ItemsClass, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getIndex() >= itemsTable.getItems().size()) {
                            setText(null);
                        } else {
                            setText(String.valueOf(getTableRow().getIndex() + 1));
                        }
                    }
                };
            }
        });

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getBarcode());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        profitColumn.setCellValueFactory(cellData -> cellData.getValue().getProfit().asObject());
        expiryColumn.setCellValueFactory(cellData -> cellData.getValue().getDateOfExpiry());
        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void fetchItem() {
        String barcode = getFromBarcode.getText();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "");
            String sql = "SELECT * FROM items WHERE barcode = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ItemsClass item = new ItemsClass(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("profitAdd"),
                        rs.getString("expiray")
                );

                try {
                    if (!itemsTable.getItems().contains(item)) {
                        itemsTable.getItems().add(item);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("دەرمان نەدۆزرایەوە!");
                alert.setHeaderText(null);
                alert.setContentText("هیج دەرمانێک بۆ باڕکۆدی نوسراو نەدۆزرایەوە.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sellItem() {
        var selectedItems = FXCollections.observableArrayList(itemsTable.getSelectionModel().getSelectedItems());

        if (selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ئاگاداری");
            alert.setHeaderText(null);
            alert.setContentText("هیچ دەرمانێک هەلبژێردراو نیە بۆ فرۆشتن.");
            alert.showAndWait();
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uni_project", "root", "")) {
            String sql = "INSERT INTO Sold_items (name, barcode, sold_date, buyPrice, sellPrice) VALUES (?, ?, CURRENT_DATE, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            for (ItemsClass item : selectedItems) {
                ps.setString(1, item.getName().get());
                ps.setString(2, item.getBarcode().get());
                ps.setDouble(3, item.getPrice().get());
                ps.setDouble(4, item.getPrice().get() + item.getProfit().get());
                ps.addBatch();
            }

            ps.executeBatch();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("فرۆشتن");
            alert.setHeaderText(null);
            alert.setContentText("دەرمانە هەلبژێردراوەکان بەسەرکەوتوویی فرۆشران.");
            alert.showAndWait();

            itemsTable.getItems().removeAll(selectedItems);

        } catch (Exception e) {
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
