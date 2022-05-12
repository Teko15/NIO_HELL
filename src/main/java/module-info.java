module ournio.nio_hell {
    requires javafx.controls;
    requires javafx.fxml;


    opens ournio to javafx.fxml;
    exports ournio;
}