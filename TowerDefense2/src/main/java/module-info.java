module com.example.towerd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.towerd to javafx.fxml;
    exports com.example.towerd;
    exports com.example.towerd.controleur;
    opens com.example.towerd.controleur to javafx.fxml;
    exports com.example.towerd.modele;
    opens com.example.towerd.modele to javafx.fxml;
    exports com.example.towerd.vue;
    opens com.example.towerd.vue to javafx.fxml;
}