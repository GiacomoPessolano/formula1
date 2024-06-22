module it.unicam.cs.giacomopessolano.formula1.ui {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unicam.cs.giacomopessolano.formula1.ui to javafx.fxml;
    exports it.unicam.cs.giacomopessolano.formula1.ui;
}