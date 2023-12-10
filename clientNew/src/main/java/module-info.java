module pl.pwr.ite.clientNew {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens pl.pwr.ite.clientNew to javafx.fxml;
    exports pl.pwr.ite.clientNew;
    exports pl.pwr.ite.clientNew.view.controller;
    exports pl.pwr.ite.clientNew.model;
    opens pl.pwr.ite.clientNew.view.controller to javafx.fxml;
}