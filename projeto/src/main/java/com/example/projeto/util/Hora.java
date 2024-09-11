package com.example.projeto.util;

public class Hora {

    public static String formatarHora(String horaInput) {
        if (horaInput.matches("\\d{1}")) {
            return "0" + horaInput + ":00:00";
        } else if (horaInput.matches("\\d{2}")) {
            return horaInput + ":00:00";
        } else if (horaInput.matches("\\d{2}:\\d{2}")) {
            return horaInput + ":00";
        } else if (horaInput.matches("\\d{2}:\\d{2}:\\d{2}")) {
            return horaInput;
        } else {
            throw new IllegalArgumentException("Formato de hora inválido. Use o formato HH:MM ou HH:MM:SS.");
        }
    }
}