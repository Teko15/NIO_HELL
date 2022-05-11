module ournio.nio_hell {
    requires javafx.controls;
    requires javafx.fxml;


    opens ournio.nio_hell to javafx.fxml;
    exports ournio.nio_hell;
}