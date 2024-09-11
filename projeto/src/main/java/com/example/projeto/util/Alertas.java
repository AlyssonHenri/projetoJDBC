package com.example.projeto.util;

import javafx.scene.control.Alert;

public class Alertas {
    public static void mostrarAlerta(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

}
