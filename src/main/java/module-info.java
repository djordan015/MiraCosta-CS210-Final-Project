module cs210.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires json;


    opens cs210.javafxproject to javafx.fxml;
    exports cs210.javafxproject;
}