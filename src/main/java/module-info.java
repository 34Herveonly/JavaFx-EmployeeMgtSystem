module org.mavenproject1.employeemgtapp_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens org.mavenproject1.employeemgtapp_javafx to javafx.fxml;
    exports org.mavenproject1.employeemgtapp_javafx;
}