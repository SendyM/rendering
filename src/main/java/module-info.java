module com.example.rendering {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens test.CircleLS to javafx.fxml;
    exports test.CircleLS;

}