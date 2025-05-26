module com.example.tpfinalpoo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires org.controlsfx.controls;
    requires java.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports modele;
    exports Observer;
    exports Services;
    opens com.example.tpfinalpoo2 to javafx.fxml;
    exports com.example.tpfinalpoo2;
}