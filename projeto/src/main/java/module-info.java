module com.example.projeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projeto to javafx.fxml;
    exports com.example.projeto;
    opens com.example.projeto.model.entities to javafx.base;
}