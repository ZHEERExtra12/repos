module com.pharmacy.pharmacyproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;

    opens com.pharmacy.pharmacyproject to javafx.fxml;
//    exports com.pharmacy.pharmacyproject.forms;
//    exports com.pharmacy.pharmacyproject.forms;
//    opens com.pharmacy.pharmacyproject.forms to javafx.fxml;
//    exports com.pharmacy.pharmacyproject.controllers;
//    opens com.pharmacy.pharmacyproject.controllers to javafx.fxml;
//    exports com.pharmacy.pharmacyproject;
    exports com.pharmacy.pharmacyproject;
}