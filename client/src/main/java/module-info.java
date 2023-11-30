module pl.pwr.ite.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens pl.pwr.ite.client to javafx.fxml;
    exports pl.pwr.ite.client;
    exports pl.pwr.ite.client.view.controller;
    opens pl.pwr.ite.client.view.controller to javafx.fxml;
}