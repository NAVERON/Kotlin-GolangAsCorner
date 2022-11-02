module org.eron.fxkotlin {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens org.eron.fxkotlin to javafx.fxml;
    exports org.eron.fxkotlin;
}